# UML — Diagramas

UML (Unified Modeling Language) es un lenguaje visual para representar el diseño de software orientado a objetos. En esta materia usamos principalmente dos tipos: **Diagramas de Clases** y **Diagramas de Secuencia**.

---

## Notación básica de una clase

```
┌──────────────────────────────────┐
│         NombreClase              │  ← Nombre (UpperCamelCase)
├──────────────────────────────────┤
│ - atributo1 : Tipo               │  ← Atributos (variables de instancia)
│ - atributo2 : Tipo               │
├──────────────────────────────────┤
│ + metodo1(param: Tipo) : Retorno │  ← Métodos (comportamiento)
│ + metodo2() : void               │
└──────────────────────────────────┘
```

### Visibilidad

| Símbolo | Significado | Cuándo usarlo |
|---------|-------------|---------------|
| `-` | privado | Variables de instancia siempre |
| `+` | público | Métodos de la interfaz pública |
| `#` | protegido | Métodos accesibles solo por subclases |

### Constructores

Los constructores se representan como métodos con el mismo nombre de la clase, sin tipo de retorno:

```
+ create(nombre: String, edad: Integer)
```

> En algunos estilos se usa `<<create>>` o simplemente el nombre de la clase.

---

## Clases especiales

### Clase abstracta

```
┌──────────────────────────────────┐
│         <<abstract>>             │
│         NombreClase              │
├──────────────────────────────────┤
│ - atributo : Tipo                │
├──────────────────────────────────┤
│ + metodoConcreto() : Tipo        │
│ + <<abstract>> metodoAbstracto() │  ← método sin implementación
└──────────────────────────────────┘
```

Alternativa: el nombre de la clase y los métodos abstractos en *cursiva*.

### Interfaz

```
┌──────────────────────────────────┐
│         <<interface>>            │
│         NombreInterfaz           │
├──────────────────────────────────┤
│ + metodo1() : Tipo               │
│ + metodo2() : void               │
└──────────────────────────────────┘
```

---

## Relaciones entre clases

### 1. Asociación (una clase conoce a otra)

Línea simple con flecha de navegabilidad. Indica que un objeto tiene una referencia a otro.

```
Empresa ──────────────────→ Empleado
         empleados
         0..*
```

- La **flecha** indica hacia quién apunta la referencia
- El **nombre** del extremo es el rol o el nombre de la variable
- La **multiplicidad** indica cuántos objetos hay en esa punta

### 2. Composición (parte de, ciclo de vida dependiente)

Rombo **relleno** del lado del "todo". Si el todo se destruye, las partes también.

```
Pedido ◆──────────────────→ ItemPedido
        items
        1..*
```

### 3. Agregación (parte de, ciclo de vida independiente)

Rombo **vacío** del lado del "todo". Las partes pueden existir sin el todo.

```
Carrito ◇──────────────────→ Producto
         productos
         0..*
```

> **Nota práctica:** En esta materia la distinción agregación/composición es menos crítica que la asociación. Lo importante es la multiplicidad y el rol.

### 4. Herencia (es-un)

Flecha con triángulo **vacío** apuntando a la superclase.

```
        Cuenta
           △
           │
     ┌─────┴────────┐
     │              │
CajaDeAhorro   CuentaCorriente
```

### 5. Implementación de interfaz

Flecha con triángulo vacío y **línea punteada** apuntando a la interfaz.

```
<<interface>>
  Volador
     △
   ┄┄┄┄
     │
   Pato
```

---

## Multiplicidades

Indican cuántos objetos puede haber en cada extremo de la relación.

| Notación | Significado |
|----------|-------------|
| `1` | exactamente uno |
| `0..1` | cero o uno (opcional) |
| `*` o `0..*` | cero o muchos |
| `1..*` | uno o muchos (al menos uno) |
| `2..5` | entre dos y cinco |

```
Cliente ──── 1..*  ────→ Compra
  1
  │
  ▼
Dirección
(0..1)
```

---

## Roles y navegabilidad

Los **roles** nombran la relación desde el punto de vista de cada extremo:

```
Empresa ────────────────── Persona
  1       empleador  empleado  0..*
```

La **navegabilidad** (flecha) indica quién conoce a quién. Si hay flecha de A → B, entonces A conoce a B, pero B no necesariamente conoce a A.

---

## Modelo de dominio vs Diagrama de clases de diseño

Estos son dos tipos de diagramas con propósitos distintos. Es frecuente confundirlos.

### Modelo de dominio

**Para qué:** Representar los conceptos del mundo real del problema. Es el resultado del **análisis**.

**Qué incluye:**
- Clases conceptuales con sus nombres
- Atributos (pueden ir sin tipo)
- Asociaciones con multiplicidad y roles
- **NO incluye métodos**
- **NO incluye comportamiento**
- **NO incluye detalles de implementación**

```
Ejemplo — Modelo de dominio de una librería:

        Cliente ──── 1..* ──→ Compra ──── 1..* ──→ Libro
        - nombre               - fecha              - titulo
        - email                                     - precio
                                                    - isbn
```

### Diagrama de clases de diseño

**Para qué:** Representar cómo se va a implementar el sistema. Es el resultado del **diseño**.

**Qué incluye:**
- Clases con visibilidades
- Atributos con tipos Java
- **Métodos con firmas completas** (parámetros y tipo de retorno)
- Constructores
- Herencia e interfaces (con estereotipos)
- Multiplicidades y roles
- Tipos de colecciones si aplica

```
Ejemplo — Diagrama de diseño de la misma librería:

        Cliente                           Compra
        - nombre: String     1    1..*    - fecha: LocalDate
        - email: String   ◆──────────────
        + comprar(libro: Libro): void     + calcularTotal(): double
        + getCompras(): List<Compra>
```

### Comparación rápida

| Aspecto | Modelo de dominio | Diagrama de diseño |
|---------|:-:|:-:|
| Fase | Análisis | Diseño |
| Métodos | ❌ No | ✅ Sí |
| Tipos Java | ❌ No | ✅ Sí |
| Herencia/Interfaces | ❌ Generalmente no | ✅ Sí |
| Visibilidades (+/-/#) | ❌ No | ✅ Sí |
| Constructores | ❌ No | ✅ Sí |
| Objetivo | Entender el dominio | Guiar la implementación |

---

## Ejemplo completo: Diagrama de diseño

Sistema de alquileres donde un usuario puede tener varios alquileres, cada uno con servicios adicionales de distintos tipos:

```
        Usuario
        - nombre: String
        + agregarAlquiler(a: Alquiler): void
        + totalGastado(): double
             │ alquileres
             │ 0..*
             ▼
          Alquiler
          - fechaInicio: LocalDate
          - cantidadDias: Integer
          - costoPorDia: double
          + costoBase(): double
             │ servicios
             │ 0..*
             ▼
       <<abstract>>
         Servicio
         - fechaContratada: LocalDate
         + <<abstract>> costo(): double
              △
         ┌────┴──────────────┐
         │                   │
      Limpieza           Traslado
      - horas: Integer   - distanciaKm: Integer
      - precioPorHora    - precioPorKm: double
      + costo(): double  + costo(): double
```

---

## Errores comunes al diagramar

### ❌ Poner métodos en el modelo de dominio
El modelo de dominio representa conceptos del mundo real, no implementación.

### ❌ Olvidar la multiplicidad
Siempre indicar cuántos objetos hay en cada extremo de la relación.

### ❌ Flechas en la dirección incorrecta
La flecha va hacia el objeto que es "conocido". Si Pedido tiene una lista de Items, la flecha va de Pedido → Item.

### ❌ Usar atributos como claves en lugar de asociaciones
```
// ❌ MAL — atributo como clave
Cliente
- idEmpleado: Integer    ← esto referencia a otro objeto por ID

// ✅ BIEN — asociación
Cliente ──────→ Empleado
```

### ❌ No distinguir herencia de implementación
- Superclase → subclase: flecha con triángulo, **línea sólida**
- Interfaz → implementador: flecha con triángulo, **línea punteada**

---

## Diagramas de Secuencia

Los diagramas de secuencia muestran **cómo colaboran los objetos** para realizar una operación, a lo largo del tiempo. Son útiles para diseñar y documentar el flujo de mensajes.

### Elementos básicos

```
      :Cliente        :Cuenta          :Movimiento
          │               │                 │
          │  extraer(500) │                 │
          │──────────────>│                 │
          │               │ new Movimiento  │
          │               │────────────────>│
          │               │                 │
          │               │<────────────────│
          │               │  mov            │
          │               │                 │
          │     true      │                 │
          │<──────────────│                 │
          │               │                 │
```

| Elemento | Representación | Significado |
|----------|----------------|-------------|
| **Objeto/Actor** | `:NombreClase` arriba de una línea vertical | Participante en la interacción |
| **Línea de vida** | Línea vertical punteada bajo el objeto | El tiempo transcurre hacia abajo |
| **Activación** | Rectángulo sobre la línea de vida | El objeto está ejecutando un método |
| **Mensaje** | Flecha horizontal con nombre del mensaje | Envío de un mensaje a otro objeto |
| **Retorno** | Flecha horizontal punteada | Valor de retorno (opcional) |
| **Creación** | Flecha hacia el encabezado del objeto | `new` |

### Ejemplo completo: transferencia bancaria

```
   :CuentaOrigen        :CuentaDestino
        │                     │
        │ transferir(500, dest)│
   ─────┤                     │
        │                     │
        │  puedeDebitar(500)  │
   ┌────┤                     │
   │    │                     │
   └───>│ true                │
        │                     │
        │   acreditar(500)    │
        │────────────────────>│
        │                     ├──────┐
        │                     │      │ saldo += 500
        │                     │<─────┘
        │                     │
   ─────┤ debitar(500)        │
   ┌────┤                     │
   │    │ saldo -= 500        │
   └───>│                     │
        │                     │
```

### Relación con el código

Cada mensaje en el diagrama de secuencia corresponde a una llamada a método en el código. El diagrama ayuda a identificar:
- Qué objetos necesitan conocerse (asociaciones)
- Qué métodos hay que implementar
- Quién es responsable de cada operación (HAR)
