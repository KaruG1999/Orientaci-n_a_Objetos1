# Apunte: Análisis y Diseño Orientado a Objetos

## 📋 Contratos de Operaciones

### ¿Qué son?
Los contratos son una forma de describir detalladamente el comportamiento de una operación en el sistema. Funcionan como una especificación formal que define qué debe cumplirse antes y después de ejecutar una operación.

### Secciones de un Contrato

#### 1. Operación
Define el nombre de la operación y sus parámetros.

**Ejemplo:**
```
contratar servicio por única vez(c: Cliente, fecha: Date, s: Servicio)
```

#### 2. Pre-condiciones
Son suposiciones sobre el estado del sistema **antes** de ejecutar la operación. Estas condiciones:
- No se validan dentro de la operación
- Se asumen como válidas
- Deben ser no triviales

**Ejemplo:**
- La fecha es una fecha válida para el contexto
- La cantidad de días de contratación de un servicio prolongado es mayor a 1

#### 3. Post-condiciones
Describen el estado del sistema **después** de ejecutar la operación. Pueden incluir:
- Modificación de valores de atributos
- Creación o eliminación de instancias
- Creación o ruptura de asociaciones

Son **declarativas**: afirman lo que debe ser verdadero tras la ejecución.

**Ejemplo:**
- El cliente posee una contratación por única vez para el servicio s y la fecha indicada

### Relación con Test de Unidad
Los contratos son una antesala natural a los tests:
- **Pre-condiciones** → Dan idea del **fixture** del test (preparación)
- **Post-condiciones** → Dan idea de las **verificaciones** (asserts)

---

## 🎯 Heurísticas para Asignación de Responsabilidades (HAR)

### ¿Qué son las Responsabilidades?

Los objetos tienen dos tipos de responsabilidades:

#### Hacer
- Hacer algo por sí mismo
- Iniciar una acción en otros objetos
- Controlar o coordinar actividades de otros objetos

#### Conocer
- Conocer sus datos privados encapsulados
- Conocer sus objetos relacionados
- Conocer cosas derivables o calculables

---

## 🔑 Las Cuatro Heurísticas Principales

### 1. 👨‍🎓 Experto en Información

**Principio:** Asignar una responsabilidad al experto en información (la clase que tiene la información necesaria para realizar la responsabilidad).

**Intuición:** Los objetos hacen cosas relacionadas con la información que tienen.

**Ejemplo:**
```
¿Quién debe calcular el monto total a pagar de todos los servicios contratados?
→ El Cliente (porque conoce sus contrataciones)

El Cliente delega a cada Contratación el cálculo de su monto,
y luego suma todos los resultados.
```

**Nota importante:** A veces la información está dispersa en varios expertos parciales, por lo que pueden colaborar entre sí.

---

### 2. 🏗️ Creador

**Principio:** Asignar a la clase B la responsabilidad de crear una instancia de A si:

- B usa a objetos A en forma exclusiva
- B contiene objetos A (composición/agregación fuerte)
- B tiene los datos para inicializar objetos A (sin necesidad de recibirlos por parámetro)

**Ejemplos:**

```
✓ Una Cola de Impresión crea Posicionadores
✓ Una clase instancia su propia colección de elementos
✓ Cliente crea sus Contrataciones (porque las contiene y tiene los datos)
```

**⚠️ Importante:** No es simplemente "conocer" el objeto, debe ser una relación fuerte de composición.

---

### 3. 🔗 Bajo Acoplamiento

**Principio:** Asignar responsabilidades de manera que el acoplamiento se mantenga lo más bajo posible.

**¿Qué es el acoplamiento?**
Es una medida de dependencia entre objetos. Es bajo cuando un objeto mantiene pocas relaciones con otros.

**Problema del alto acoplamiento:**
- Dificulta el entendimiento
- Complica la propagación de cambios
- Reduce la reutilización

**Ejemplo visual:**

```
❌ Alto Acoplamiento:
Manager → Asistente → Fichero
         (Manager conoce a Fichero indirectamente)

✓ Bajo Acoplamiento:
Manager → Asistente
         (Manager solo conoce a Asistente,
          quien maneja internamente a Fichero)
```

---

### 4. 💪 Alta Cohesión

**Principio:** Asignar responsabilidades de manera que la cohesión se mantenga lo más fuerte posible.

**¿Qué es la cohesión?**
Es una medida de qué tan relacionadas están las responsabilidades de un objeto entre sí.

**Ventajas de alta cohesión:**
- Clases más fáciles de mantener
- Más fáciles de entender
- Más reutilizables

**Ejemplo:**
```
❌ Baja cohesión: Una clase "Manager" que maneja empleados,
                  calcula impuestos y genera reportes

✓ Alta cohesión: Clase "Manager" solo maneja empleados
                Clase "CalculadorImpuestos" calcula impuestos
                Clase "GeneradorReportes" genera reportes
```

---

## 🔄 Del Análisis al Diseño

### Flujo del Proceso

```
ANÁLISIS                          DISEÑO
┌─────────────────┐              ┌──────────────────┐
│ Casos de Uso    │─────────────→│ Diagramas de     │
│                 │              │ Secuencia        │
├─────────────────┤              │                  │
│ DSS             │─────────────→│                  │
│                 │    HAR       │                  │
├─────────────────┤   aplican    ├──────────────────┤
│ Modelo del      │─────────────→│ Diagrama de      │
│ Dominio         │              │ Clases           │
│                 │              │                  │
├─────────────────┤              └──────────────────┘
│ Contratos       │
└─────────────────┘
```

### Pasos para crear Diagramas de Secuencia

1. Crear un diagrama por cada operación del caso de uso
2. Si es complejo, separar por escenarios
3. Usar el contrato como punto de partida
4. Pensar en objetos que colaboran (del modelo del dominio)
5. Aplicar HAR para obtener mejor diseño

---

## 📊 Ejemplo Completo: Contratar Servicio

### Contrato
```
Operación: contratarPorUnicaVez(c: Cliente, fecha: Date, s: Servicio)

Pre-condición:
- La fecha es válida para el contexto

Post-condición:
- El cliente posee una contratación por única vez 
  para el servicio s y la fecha indicada
```

### Diagrama de Secuencia
```
contratarPorUnicaVez(s, f)
        │
        ▼
    Cliente ──create(s,f)──→ ContrataciónUnica
        │                           │
        │◄──────con─────────────────┘
        │
        │──agregarContrato(con)──→ [colección]
```

**Aplicando HAR:**
- **Creador**: Cliente crea ContrataciónUnica (la contiene y tiene los datos)
- **Experto**: Cliente agrega la contratación (conoce su colección)

---

## 🏛️ Creación del Diagrama de Clases

### Proceso
1. Identificar clases de los diagramas de interacción y modelo conceptual
2. Graficarlas en un diagrama de clases
3. Colocar atributos del modelo conceptual
4. Agregar métodos analizando diagramas de secuencia
5. Agregar tipos y visibilidad
6. Agregar asociaciones necesarias
7. Agregar roles, navegabilidad y multiplicidad

### Ejemplo Final

```
┌─────────────────────────────────┐
│         Cliente                 │
├─────────────────────────────────┤
│ - nombreYApellido: String       │
│ - direccion: String             │
├─────────────────────────────────┤
│ + contratarPorUnicaVez()        │
│ + montoAPagar(): Integer        │
└──────────┬──────────────────────┘
           │ contrataciones *
           │
           ▼
┌─────────────────────────────────┐
│      Contratación               │
├─────────────────────────────────┤
│ - fechaDeContratacion: Date     │
├─────────────────────────────────┤
│ + montoAPagar()                 │
└──────────┬──────────────────────┘
           │
     ┌─────┴─────┐
     │           │
     ▼           ▼
┌──────────┐  ┌──────────────────┐
│Única Vez │  │Prolongada        │
└──────────┘  └──────────────────┘
```

---

## 🎭 Polimorfismo: Evitando Preguntar por Tipos

### ❌ Mal Diseño
```java
if (tarjeta == "oro") {
    // bonificación 3%
} else if (tarjeta == "platino") {
    // bonificación 5%
} else if (tarjeta == "clasica") {
    // sin bonificación
}
```

### ✓ Buen Diseño
```
         Tarjeta
            │
      ┌─────┼─────┐
      │     │     │
   Clásica Oro Platino
      │     │     │
      └─────┴─────┘
         │
    bonificación()
```

Cada subclase implementa su propia versión de `bonificación()` → **Polimorfismo**

**Principio:** Descubrir nuevas clases para evitar preguntar por el tipo o valor de un atributo.

---

## 🔄 Estados vs Clasificación

### ❌ Incorrecto: Clasificar objetos por su estado
```
      Pago
       │
   ┌───┴───┐
   │       │
PagoAutorizado  PagoNoAutorizado
```

Si el pago cambia de estado, ¿cambiamos el objeto?

### ✓ Correcto: Clasificar estados
```
Pago ──estado──→ Estado
                    │
                ┌───┴───┐
                │       │
           Autorizado  NoAutorizado
```

El objeto Pago permanece, solo cambia su estado interno.

---

## 💻 Mapeo a Código

### Del Diseño al Código
```
DISEÑO                    CÓDIGO
───────────────────────────────────────
Clases           →        Clases
Atributos        →        Variables
Asociaciones     →        Referencias
Métodos          →        Métodos
Multiobjetos     →        Collections
```

### Ejemplo
```java
public class Cliente {
    private String nombreYApellido;
    private String direccion;
    private List<Contratacion> contrataciones;
    
    public void contratarPorUnicaVez(Servicio s, Date f) {
        Contratacion con = new ContratacionUnica(s, f);
        this.contrataciones.add(con);
    }
    
    public int montoAPagar() {
        int total = 0;
        for (Contratacion c : contrataciones) {
            total += c.montoAPagar();
        }
        return total;
    }
}
```

---

## 📝 Resumen de Conceptos Clave

| Concepto | ¿Qué hace? | ¿Cuándo usar? |
|----------|------------|---------------|
| **Experto** | Asigna responsabilidad al que tiene la información | Siempre que necesites decidir quién hace algo |
| **Creador** | Define quién crea objetos | Cuando necesites crear instancias |
| **Bajo Acoplamiento** | Reduce dependencias | Al evaluar alternativas de diseño |
| **Alta Cohesión** | Agrupa responsabilidades relacionadas | Al definir qué hace cada clase |
| **Polimorfismo** | Evita preguntar por tipos | Cuando tengas múltiples comportamientos similares |

---

## 🎓 Tips Importantes

1. **Las HAR no se aplican aisladamente**: Siempre considera varias heurísticas al asignar responsabilidades
2. **Itera el diseño**: No esperes que la primera versión sea perfecta
3. **Los diagramas de secuencia guían el diseño de clases**: Primero interacciones, luego estructura
4. **Evita preguntar por tipos**: Usa polimorfismo en su lugar
5. **Los contratos ayudan con los tests**: Son la base para escribir buenos tests unitarios