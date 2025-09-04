# Introducción al Análisis y Diseño Orientado a Objetos

## Modelo de Dominio 

El modelo de dominio es fundamental para:
- Describir de qué trata el problema
- Está dentro de la **capa de la lógica de negocio** (hay distintas capas: Presentación, Lógica de Negocio, Persistencia)

### Definición
Un **Modelo del Dominio** es una representación visual de las clases conceptuales del mundo real en un dominio de interés. Utilizamos **Diagramas de Clases UML**.

## Identificación de Clases Conceptuales

**Objetivo**: Identificar clases conceptuales relacionadas al problema con el que trabajamos.

### Consejos importantes:
- Usar nombres del **dominio del problema**, no de la solución
- Omitir detalles irrelevantes
- No inventar nuevos conceptos (evitar sinónimos)
- Descubrir conceptos del **mundo real**

> **Regla de oro**: Es mejor especificar en exceso un modelo del dominio con muchas clases conceptuales de grano fino que especificar por defecto en forma general.

### Estrategias para identificar clases:

#### 1. Utilización de lista de categorías de clases conceptuales

| Categoría de Clase Conceptual | Ejemplos |
|-------------------------------|----------|
| Objeto físico o tangible | Libro impreso, Máquina |
| Especificación de una cosa | Especificación del producto, descripción |
| Lugar | Tienda, Oficina |
| Transacción | Compra, pago, cancelación, contratación |
| Roles de la gente | Cliente, empleado |
| Contenedor de cosas | Catálogo de libros, carrito, Avión |
| Cosas en un contenedor | Libro, Artículo, Pasajero |
| Otros sistemas | Sistema de facturación de AFIP |
| Hechos | Cancelación, venta, pago |
| Reglas y políticas | Política de cancelación |
| Registros financieros/laborales | Factura, Recibo de compra |
| Manuales, documentos | Reglas de cancelación, cambios de categoría |

#### 2. Identificar frases nominales

**¿Qué son?** Conjunto de palabras en una oración que funciona como un sustantivo.

**Ejemplos:**
- "El **cliente** selecciona un **libro** para agregarlo al **carrito**"
- "El **carrito** agrega el **libro** y se presenta el **precio** y la **suma** del carrito"

⚠️ **Atención**: Algunas frases nominales son clases conceptuales candidatas, algunas podrían hacer referencia a clases conceptuales y algunas podrían ser atributos de las clases conceptuales.

## Construcción del Modelo de Dominio

### Proceso paso a paso:

1. **Listar los conceptos** (clases y atributos) candidatos
2. **Graficarlos** en Modelo de dominio (UML)
3. **Agregar atributos** a los conceptos
4. **Agregar asociaciones** entre los conceptos
5. **Iterar** el proceso

## UML - Diagramas de Clases

### Estructura de una clase UML:
Rectángulo dividido en 3 secciones:

1. **Nombre de la clase**
2. **Atributos**: `visibilidad nombreAtributo : tipoDato`
   - `-` privado 
   - `+` público
3. **Métodos**: `visibilidad nombreMetodo(parámetros) : tipoRetorno`

## Agregar Atributos

### Reglas para atributos:
- Se identifican los atributos necesarios para satisfacer los requerimientos de información de los casos de uso
- Los atributos deberían ser preferiblemente **atributos simples** o **tipos de datos primitivos**:
  - Boolean
  - String  
  - Números
  - Temporales (Date, LocalDate, etc.)

### ¿Cuándo un atributo debe ser una clase conceptual?

Un atributo pensado inicialmente debe convertirse en clase conceptual si:
- Está compuesto de secciones separadas
- Tiene operaciones asociadas
- Tiene otros atributos
- Es una cantidad con una unidad
- Es una abstracción de uno o más tipos

### Atributos como claves - ❌ Evitar
**No usar atributos para relacionar clases conceptuales.**
La mejor manera de expresar que un concepto utiliza a otro es con una **asociación**, no con un atributo como clave.

## Agregar Asociaciones entre Conceptos

### Principio fundamental:
Céntrese en aquellas asociaciones para las que se necesita **conservar el conocimiento de la relación durante algún tiempo** (asociaciones "necesito-conocer").

### Categorías de asociaciones:

| Categoría | Ejemplo |
|-----------|---------|
| A es una parte física de B | Avión – Butaca/Asiento |
| A es una parte lógica de B | Etapa de vuelo - Vuelo |
| A está físicamente contenido en B | Pasajero - Avión |
| A está lógicamente contenido en B | Libro - Catálogo |
| A es una descripción para B | EspecificaciónDeProducto - Libro |
| A es un miembro de B | Piloto - Aerolínea |
| A usa o maneja a B | Cliente - Carrito |
| A se comunica con B | Cliente - Librería |
| A está relacionado con la transacción B | Cliente - Pago, Cliente - Agregar al carrito |
| A es una transacción relacionada con otra transacción B | Pago - Compra |
| A es dueño de B | Cliente - Carrito |

### Consejos para asociaciones:
- Es más importante identificar clases conceptuales que identificar asociaciones
- Demasiadas asociaciones tienden a confundir el modelo
- Evitar mostrar asociaciones redundantes o derivadas

### Clases de especificación
Agregue clases conceptuales de especificación cuando necesite la descripción de un artículo o servicio, o si al eliminar las instancias que describen, se pierde información.

---

## Ejercicio en Clase - Empresa de Mantenimiento de Viviendas

### 1) Conceptos candidatos identificados:

**Clases principales:**
- **Cliente** (nombre, apellido, dirección)
- **Servicio** (clase abstracta/superclase)
  - **ServicioLimpieza** (precio por hora, cantidad de horas, tarifa mínima)
  - **ServicioParquizacion** (precio por hora, cantidad de horas, cantidad máquinas, costo mantenimiento)
- **Contratación** (fecha de contratación)
  - **ContratacionUnica** 
  - **ContratacionProlongada** (cantidad de días)

**Conceptos adicionales:**
- Máquinas
- Monto a pagar (método/acción)
- Recargo 15% (fin de semana)
- Descuento 10% (más de 5 días)

### 2) Relaciones identificadas:
- Cliente **contrata** Servicio (a través de Contratación)
- Contratación **relaciona** Cliente y Servicio
- Herencia: ServicioLimpieza y ServicioParquizacion **extienden** Servicio
- Herencia: ContratacionUnica y ContratacionProlongada **extienden** Contratacion

---

## Mapeo del Modelo UML a Java

### Ejemplo: Implementación de clases básicas

```java
// Clase Cliente
public class Cliente {
    private String nombreYApellido;
    private String direccion;
    private ArrayList<Contratacion> contrataciones; // Asociación con cardinalidad múltiple
    
    // Constructores, getters, setters...
}
```

### Implementación de asociaciones con cardinalidad

```java
import java.util.ArrayList;

public class Cliente {
    private String nombreYApellido;
    private String direccion;
    private ArrayList<Contratacion> contrataciones; // Lista para cardinalidad 1..*
}
```

### Implementación de herencia

```java
import java.time.LocalDate;

// Clase padre
public class Contratacion {
    private LocalDate fechaDeContratacion; // Atributo común
}

// Clases hijas
public class ContratacionUnica extends Contratacion {
    // Hereda fechaDeContratacion automáticamente
}

public class ContratacionProlongada extends Contratacion {
    private int cantidadDeDias; // Atributo específico
    // También hereda fechaDeContratacion
}
```

### Herencia de estructura - Puntos importantes:

1. **Las subclases heredan automáticamente** los atributos de la superclase
2. **No deben redeclararse** los atributos heredados en las subclases
3. **Instancia de ContratacionUnica** posee: `fechaDeContratacion`
4. **Instancia de ContratacionProlongada** posee: `fechaDeContratacion` + `cantidadDeDias`

### Factorización de atributos comunes:
Las subclases que comparten atributos comunes deben factorizarlos en la superclase para evitar duplicación de código.

---

## Resumen del Proceso Completo

1. **Identificar clases conceptuales** usando categorías y frases nominales
2. **Listar conceptos candidatos**
3. **Crear diagrama UML** con las clases seleccionadas
4. **Agregar atributos primitivos** a cada clase
5. **Definir asociaciones** entre clases (evitar atributos como claves)
6. **Implementar en Java** respetando la herencia y asociaciones
7. **Iterar y refinar** el modelo según necesidades

> **Recordatorio**: El modelo de dominio es una representación del mundo real del problema, no de la solución técnica.