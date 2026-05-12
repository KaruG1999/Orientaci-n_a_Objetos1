# Ejercicio 12 — Job Scheduler

## Enunciado

El `JobScheduler` es un objeto cuya responsabilidad es determinar qué trabajo debe resolverse a continuación.

- `schedule(job)` → agrega un trabajo al final de la lista de pendientes.
- `next()` → determina cuál es el siguiente trabajo, lo quita de la lista y lo retorna.

La implementación actual del método `next()` utiliza una cadena de `if/else` (o `switch`) para decidir cuál job elegir según una variable `strategy` (un String o un entero). **Eso es un problema de diseño.**

Hay 3 estrategias posibles:
- **FIFO**: retorna el trabajo que llegó primero.
- **Prioridad**: retorna el trabajo con mayor prioridad (número más alto).
- **SJF** (Shortest Job First): retorna el trabajo con menor tiempo estimado.

**Tu tarea:** eliminar el if/switch y reemplazarlo con polimorfismo.

---

## Objetivo de aprendizaje

Reconocer cuándo una cadena `if/else` sobre un "tipo" es la señal de que falta polimorfismo, y saber cómo resolverlo con el **Strategy pattern**.

---

## Concepto OOP central: Strategy Pattern

El patrón Strategy dice:

> Si tenés comportamiento que varía según una "estrategia", ese comportamiento no vive en el objeto que lo usa — vive en una clase separada que lo encapsula.

```
                   ┌──────────────────────┐
                   │  EstrategiaSeleccion │  ← interfaz
                   │  + seleccionarSiguiente(jobs) │
                   └──────────────────────┘
                         ▲        ▲        ▲
              ┌──────────┘   ┌────┘   ┌────┘
              │              │        │
   ┌──────────────┐  ┌─────────────┐  ┌──────────┐
   │ EstrategiaFIFO│  │ EstrategiaPrioridad│  │EstrategiaSJF│
   └──────────────┘  └─────────────┘  └──────────┘

   JobScheduler tiene UNA EstrategiaSeleccion (recibida por constructor)
   y llama a estrategia.seleccionarSiguiente(jobs) — sin saber el tipo concreto.
```

La clave: `JobScheduler` no cambia si agregás una cuarta estrategia. Solo agregás una nueva clase.

---

## Por dónde empezar — guía paso a paso

### Paso 1: implementar las estrategias simples primero

Empezá por `EstrategiaFIFO` — es la más fácil:
```java
public JobDescription seleccionarSiguiente(List<JobDescription> jobs) {
    return jobs.get(0); // el primero que entró
}
```

Corré los tests de FIFO. Cuando pasen, seguí con `EstrategiaSJF` y `EstrategiaPrioridad`.

Para SJF y Prioridad podés iterar manualmente o usar streams:
```java
// Opción A: loop manual (más legible si estás empezando)
JobDescription mejor = jobs.get(0);
for (JobDescription j : jobs) {
    if (j.getPriority() > mejor.getPriority()) {
        mejor = j;
    }
}
return mejor;

// Opción B: stream (más conciso)
return jobs.stream()
    .max(Comparator.comparingInt(JobDescription::getPriority))
    .get();
```

### Paso 2: implementar `JobScheduler`

```java
public JobDescription next() {
    JobDescription elegido = estrategia.seleccionarSiguiente(jobs);
    jobs.remove(elegido); // lo quita de la lista
    return elegido;
}
```

### Paso 3: verificar que el scheduler NO cambia entre estrategias

Notá que `JobScheduler.next()` es **idéntico** sin importar la estrategia. Eso es el objetivo.

---

## Preguntas para reflexionar

1. ¿Qué hubiera pasado si agregaban una 4ta estrategia con el if/switch original? ¿Cuántas clases habría que modificar?
2. Con polimorfismo, ¿cuántas clases hay que modificar para agregar una nueva estrategia?
3. ¿La estrategia podría cambiarse después de crear el scheduler? ¿Sería bueno o malo? (En este ejercicio se asume que **no** puede cambiarse.)
4. ¿Qué diferencia hay entre Strategy y Template Method? ¿Cuándo usarías cada uno?

---

## Errores comunes a evitar

- **No dejes el `if/else` en `JobScheduler`** — si la solución todavía tiene un condicional sobre el tipo de estrategia, no resolviste el problema.
- **No hagas que `EstrategiaFIFO` sepa de `JobScheduler`** — la estrategia recibe la lista como parámetro, no tiene referencia al scheduler.
- **Quitá el job de la lista en `next()`**, no en la estrategia — la estrategia solo elige, no elimina.
