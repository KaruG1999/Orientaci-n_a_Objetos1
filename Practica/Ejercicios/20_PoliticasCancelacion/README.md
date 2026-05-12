# Ejercicio 20 — Políticas de Cancelación

## Enunciado

Extiende el ejercicio 19 (OOBnB). Al cancelar una reserva, el sistema debe calcular cuánto se le reembolsa al inquilino. Ese cálculo depende de la **política de cancelación** que tenga la propiedad.

Hay 3 políticas:

| Política | Comportamiento |
|----------|---------------|
| **Flexible** | Reembolsa el **100%** del precio sin importar cuándo se cancela |
| **Moderada** | > 7 días antes → **100%** \| entre 2 y 7 días → **50%** \| ≤ 2 días → **0%** |
| **Estricta** | Reembolsa **0%** siempre |

Reglas adicionales:
- La cancelación siempre debe ser antes de que el período comience (igual que en Ej 19).
- La política de una propiedad **puede cambiarse** en cualquier momento (a diferencia del scheduler del Ej 12).

Nuevo método en `Propiedad`:
```java
public double cancelarConReembolso(Reserva reserva, LocalDate fechaCancelacion)
// Cancela la reserva y retorna el monto a reembolsar según la política
```

---

## Objetivo de aprendizaje

Aplicar el **Strategy pattern** sobre un dominio real (reservas de viajes), prestando especial atención a los **valores de borde** de la política moderada (exactamente 7 días y exactamente 2 días).

---

## Concepto OOP: Strategy pattern (con objeto mutable)

Este ejercicio usa Strategy igual que el Ej 12, pero con una diferencia importante:

| | Ej 12 — Job Scheduler | Ej 20 — Políticas |
|--|----------------------|-------------------|
| ¿Puede cambiarse la estrategia? | **No** (se pasa en el constructor, final) | **Sí** (tiene setter) |
| ¿Por qué? | Una vez creado el scheduler, su comportamiento no debe cambiar | Un propietario puede cambiar la política de su propiedad en cualquier momento |

```
PoliticaCancelacion          ← interfaz
  + calcularReembolso(reserva, fechaCancelacion): double
       ▲            ▲               ▲
       │            │               │
PoliticaFlexible  PoliticaModerada  PoliticaEstricta
```

`Propiedad` tiene una referencia a `PoliticaCancelacion`. Al cancelar, delega:
```java
double reembolso = politica.calcularReembolso(reserva, fechaCancelacion);
```

Sin saber si es flexible, moderada o estricta. Ese es el punto.

---

## Diagrama de decisión de PoliticaModerada

```
            fechaCancelacion
                  │
                  ▼
         dias = DAYS.between(fechaCancelacion, reserva.getPeriodo().getFrom())
                  │
         ┌────────┴────────┐
         │   dias > 7?     │
         └────────┬────────┘
            Sí ───┼─── No
            │     │     │
          100%    │   dias > 2?
                  │     │
               Sí ┼  No │
               │  │     │
              50% │     0%
```

**Borde 7 días:** `dias > 7` → con exactamente 7 días, entra en la franja del 50%.  
**Borde 2 días:** `dias > 2` → con exactamente 2 días, entra en la franja del 0%.

Estos bordes son lo que más se evalúa. Tené un test para cada uno.

---

## Por dónde empezar — guía paso a paso

### Paso 1: políticas simple primero

`PoliticaFlexible` y `PoliticaEstricta` son triviales — empezá por ellas:
```java
// Flexible:
return reserva.getPrecioTotal();

// Estricta:
return 0.0;
```

### Paso 2: `PoliticaModerada` con los bordes exactos

```java
long dias = ChronoUnit.DAYS.between(fechaCancelacion, reserva.getPeriodo().getFrom());

if (dias > 7) return reserva.getPrecioTotal();          // 100%
if (dias > 2) return reserva.getPrecioTotal() * 0.5;   // 50%
return 0.0;                                             // 0%
```

### Paso 3: reimplementar `Propiedad` con política

`Propiedad` ahora recibe la política en el constructor:
```java
public Propiedad(String nombre, String direccion, double precioPorNoche, PoliticaCancelacion politica)
```

El método nuevo:
```java
public double cancelarConReembolso(Reserva reserva, LocalDate fechaCancelacion) {
    double reembolso = politica.calcularReembolso(reserva, fechaCancelacion);
    cancelarReserva(reserva);
    return reembolso;
}
```

### Paso 4: verificar que la política puede cambiarse

```java
propiedad.setPolitica(new PoliticaFlexible()); // debe funcionar
```

---

## Correr los tests

```bash
cd Practica/Ejercicios/20_PoliticasCancelacion
mvn test
```

Los tests de `PoliticaModerada` incluyen los 5 casos: > 7 días, = 7 días, entre 2 y 7, = 2 días, < 2 días.

---

## Preguntas para reflexionar

1. En el Ej 12 la estrategia es inmutable (se pasa al constructor y no hay setter). Acá tiene setter. ¿Qué consecuencia tendría si en el Ej 12 también se pudiera cambiar?
2. ¿Qué pasaría si la política fuera `null`? ¿Cómo protegerías el código de eso?
3. Si mañana piden agregar una política "semi-estricta" (30% de reembolso siempre), ¿cuántas clases hay que modificar? ¿Y cuántas habría que modificar si `calcularReembolso()` estuviera dentro de `Propiedad` con un if?
4. ¿Qué significa que la política sea una **interfaz** y no una **clase abstracta**? ¿Cuándo usarías una u otra?
