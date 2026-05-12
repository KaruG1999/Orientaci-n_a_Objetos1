# Ejercicio 16 — DateLapse (Intervalo de tiempo)

## Enunciado

Implementar la clase `DateLapse`, que representa un lapso de tiempo entre dos fechas.

```
DateLapse lapse = new DateLapse(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31));
lapse.sizeInDays()           // → 30
lapse.includesDate(fecha)    // → true si la fecha está dentro del rango
lapse.overlaps(otroLapse)    // → true si los dos períodos se superponen
```

Métodos requeridos:

| Método | Descripción |
|--------|-------------|
| `getFrom()` | Retorna la fecha de inicio |
| `getTo()` | Retorna la fecha de fin |
| `sizeInDays()` | Cantidad de días entre from y to |
| `includesDate(LocalDate)` | True si la fecha está entre from y to (inclusive en ambos extremos) |
| `overlaps(DateLapse)` | True si el período del receptor se superpone con el recibido |

---

## Objetivo de aprendizaje

Manejar `LocalDate` de Java (la API moderna de fechas), pensar en **valores de borde** (bordes del intervalo, períodos que se tocan), y producir una clase bien encapsulada que será reutilizada en los ejercicios 19 y 20.

---

## Concepto OOP: encapsulamiento de representación

`DateLapse` esconde cómo representa el intervalo. Quien usa el objeto solo habla de `from`, `to`, `sizeInDays()` y `overlaps()`. Si mañana cambiás la representación interna (por ejemplo, guardar `from + cantidad de días` en vez de `from + to`), los tests no se enteran — solo cambia la implementación.

Eso es encapsulamiento real: el contrato (los métodos públicos) no cambia aunque cambie lo de adentro.

---

## API de LocalDate que necesitás

```java
// Crear fechas
LocalDate fecha = LocalDate.of(2026, 1, 15);
LocalDate hoy   = LocalDate.now();

// Comparar
fecha.isBefore(otraFecha)  // true si fecha < otraFecha
fecha.isAfter(otraFecha)   // true si fecha > otraFecha

// Calcular diferencia
ChronoUnit.DAYS.between(from, to)  // retorna long (cantidad de días)
// Importar: import java.time.temporal.ChronoUnit;
```

---

## Por dónde empezar — guía paso a paso

### Paso 1: `sizeInDays()` — el más simple

```java
public int sizeInDays() {
    return (int) ChronoUnit.DAYS.between(from, to);
}
```

Correlo con el test `sizeInDaysRetornaLaCantidadDeDias()` antes de seguir.

### Paso 2: `includesDate()` — pensar el borde

La fecha está incluida si **no es anterior a `from`** y **no es posterior a `to`**:
```java
public boolean includesDate(LocalDate date) {
    return !date.isBefore(from) && !date.isAfter(to);
}
```

Fijate por qué se escribe así y no con `>=` y `<=`:  
`LocalDate` no implementa `>=`. Con `!isBefore` obtenés "mayor o igual".

### Paso 3: `overlaps()` — el más interesante

Dos períodos **no** se solapan si uno termina antes de que el otro empiece:
```
Caso NO solapa:   [-----A-----]
                               [-----B-----]
                  A.to < B.from

Caso NO solapa:   [-----B-----]
                               [-----A-----]
                  B.to < A.from
```

Se solapan si **ninguna** de esas condiciones se cumple:
```java
public boolean overlaps(DateLapse other) {
    return !this.to.isBefore(other.from) && !this.from.isAfter(other.to);
}
```

Verificá los bordes con el test `overlapsBordeExacto()`: dos períodos que se tocan en un día exacto **sí** solapan.

### Paso 4: correr todos los tests

```bash
cd Practica/Ejercicios/16_DateLapse
mvn test
```

Deben pasar los 9 tests. Si alguno falla, leé el nombre del test — el nombre describe exactamente qué caso está fallando.

---

## Preguntas para reflexionar

1. ¿Qué pasaría si `includesDate()` usara `isAfter(from)` en vez de `!isBefore(from)`? ¿Qué caso fallaría?
2. ¿`overlaps()` es simétrico? Es decir, ¿`A.overlaps(B)` da siempre el mismo resultado que `B.overlaps(A)`?
3. ¿Qué pasa si `from` es igual a `to`? ¿`sizeInDays()` retorna 0? ¿`includesDate(from)` retorna true?
