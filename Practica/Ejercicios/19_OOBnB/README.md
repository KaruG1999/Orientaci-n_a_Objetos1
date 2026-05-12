# Ejercicio 19 — OOBnB (Alquiler de propiedades)

## Enunciado

Diseñar e implementar una plataforma de gestión de reservas de propiedades.

**Reglas del dominio:**

- Un `Usuario` tiene propiedades y puede hacer reservas en propiedades ajenas.
- Una `Propiedad` tiene nombre, dirección y precio por noche.
- Una `Reserva` tiene un período (`DateLapse`), un inquilino y una propiedad.

**Funcionalidad a implementar:**

| Comportamiento | Descripción |
|----------------|-------------|
| `propiedad.estaDisponible(periodo)` | True si no hay ninguna reserva que solape con ese período |
| `usuario.reservar(propiedad, periodo)` | Crea la reserva si la propiedad está disponible. Si no, retorna `null`. |
| `reserva.getPrecioTotal()` | Noches × precio por noche de la propiedad |
| `reserva.cancelar()` | Cancela la reserva **solo si el período aún no comenzó**. Libera la propiedad. |
| `propiedad.calcularIngresosEnPeriodo(periodo)` | 75% de la suma de precios de reservas incluidas en ese período |

---

## Objetivo de aprendizaje

Modelar un dominio completo con **múltiples objetos que colaboran**, aplicar el principio **Experto en Información** para decidir qué clase tiene cada responsabilidad, y testear con particiones equivalentes.

---

## Concepto OOP: Experto en Información

La pregunta clave para cada comportamiento es: **¿quién tiene los datos para hacerlo?**

```
¿Quién calcula el precio total de la reserva?
  → Reserva: tiene el período (sizeInDays) y conoce a la propiedad (getPrecioPorNoche)
  → NO lo calcula Propiedad ni Usuario

¿Quién sabe si una propiedad está disponible?
  → Propiedad: tiene la lista de reservas existentes
  → NO lo calcula Reserva ni Usuario

¿Quién puede cancelar una reserva?
  → Reserva: sabe su período (puede comparar con LocalDate.now())
  → Delega a Propiedad para que la quite de su lista
```

---

## Relación entre clases

```
Usuario ──────────────────────────────────────> Propiedad
  - nombre                                         - nombre
  - dni                                            - precioPorNoche
  - propiedades: List<Propiedad>                   - reservas: List<Reserva>
  + reservar(propiedad, periodo): Reserva          + estaDisponible(periodo): boolean
                                                   + reservar(inquilino, periodo): Reserva
                                                   + cancelarReserva(reserva)
                                                   + calcularIngresosEnPeriodo(periodo): double

                          Reserva
                            - inquilino: Usuario
                            - propiedad: Propiedad
                            - periodo: DateLapse
                            + getPrecioTotal(): double
                            + cancelar(): boolean
```

**Ojo:** `Propiedad` tiene la lista de `Reserva`. Cuando se cancela una reserva, `Reserva` le dice a `Propiedad` que la saque de esa lista — no es `Propiedad` quien decide cuándo cancelar.

---

## Por dónde empezar — guía paso a paso

Implementar en este orden. Cada clase es más simple si la anterior ya funciona:

### Paso 1: `Reserva` — sin lógica de lista

`getPrecioTotal()` es trivial:
```java
return (double) periodo.sizeInDays() * propiedad.getPrecioPorNoche();
```

`cancelar()` compara con la fecha de hoy:
```java
if (LocalDate.now().isBefore(periodo.getFrom())) {
    propiedad.cancelarReserva(this);
    return true;
}
return false;
```

### Paso 2: `Propiedad.estaDisponible()`

Recorre la lista de reservas y verifica que ninguna solape:
```java
return reservas.stream()
    .noneMatch(r -> r.getPeriodo().overlaps(periodo));
```

### Paso 3: `Propiedad.reservar()`

```java
if (!estaDisponible(periodo)) return null;
Reserva nueva = new Reserva(inquilino, this, periodo);
reservas.add(nueva);
return nueva;
```

### Paso 4: `Propiedad.calcularIngresosEnPeriodo()`

Filtrar reservas cuyo período **está contenido** dentro del período dado, sumar precios, y retornar el 75%:
```java
double suma = reservas.stream()
    .filter(r -> periodo.includesDate(r.getPeriodo().getFrom())
              && periodo.includesDate(r.getPeriodo().getTo()))
    .mapToDouble(Reserva::getPrecioTotal)
    .sum();
return suma * 0.75;
```

### Paso 5: `Usuario.reservar()` — simple delegación

```java
return propiedad.reservar(this, periodo);
```

---

## Correr los tests

```bash
cd Practica/Ejercicios/19_OOBnB
mvn test
```

**Ir test a test**: si tenés 3 tests rojos, hacé pasar el primero antes de mirar el segundo.

---

## Preguntas para reflexionar

1. ¿Por qué `cancelarReserva()` tiene visibilidad de paquete (`void cancelarReserva(...)`, sin `public`)? ¿Quién debería poder llamarla?
2. Si el usuario `A` reserva y luego el usuario `B` intenta reservar el mismo período, ¿el sistema los aísla correctamente?
3. ¿Qué pasa si `calcularIngresosEnPeriodo()` usa `overlaps()` en vez de `includesDate()`? ¿Sería más estricto o más permisivo?
4. ¿Por qué el propietario recibe solo el 75% y no el 100%? (en producción esto sería la comisión de la plataforma — como en Despegar)
