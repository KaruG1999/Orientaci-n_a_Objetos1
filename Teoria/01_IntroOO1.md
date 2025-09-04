# Síntesis - Introducción a Orientación a Objetos 1

## Motivación de la Orientación a Objetos

### Evolución de la Programación

La programación ha evolucionado significativamente desde los años 50. Hoy en día, programar implica:

- Producir código que resuelve una parte específica
- Crear software conectado a otros sistemas (pide y ofrece servicios)
- Desarrollar para variedad de dispositivos
- Trabajar en ecosistemas tecnológicos cambiantes
- Colaborar de forma ágil
- Atender clientes/usuarios más involucrados y demandantes
- Manejar requerimientos volátiles y cada vez más sofisticados
- Entender y capturar el dominio/negocio

### Necesidades Actuales 

Necesitamos métodos y herramientas que:

- **Escalen** adecuadamente
- Sean **expresivos** y **fáciles de aprender y comunicar**
- **Manejen complejidad**
- Aporten **calidad**, **flexibilidad a cambios** y **reutilización**
- **Reduzcan costos** durante todo el ciclo de vida

### Aportes del Paradigma OO

- **Poder de abstracción** utilizando el dominio como guía
- **Unión de datos y comportamiento**
- **Buena organización** del programa
- **Alta cohesión**: cada unidad tiene un foco claro
- **Bajo acoplamiento**: minimizando dependencias entre unidades
- **Localidad de cambios**:
  - Extender el sistema agregando sin tocar lo que ya funciona
  - Mejorar/corregir tocando lo mínimo posible

### Arquitectura en Capas

```
Lógica de presentación (Interfaz Web, Móvil, API)
↓
Lógica de aplicación (Modelo del dominio) ← Foco OO1
↓
Lógica de persistencia
↓
Base de datos
```

Cada capa tiene:
- Rol y responsabilidad claros
- Dependencia solo de la capa inmediata inferior
- Conocimiento mínimo indispensable de la capa inferior

## Fundamentos de la Orientación a Objetos

### Sistema Orientado a Objetos

Un sistema OO es una **red (grafo) de objetos** donde:

- Cada objeto **encapsula** propiedades y operaciones
- **Todo cómputo ocurre en algún objeto**
- **No hay un objeto "main"**
- **Cómputo y datos ya no se piensan por separado**

### Cambio de Paradigma Mental

En lugar de jerarquía de "procedimientos y sub-procedimientos", tenemos:

- Una **red de objetos**
- Posibles **jerarquías de composición**
- Posibles **jerarquías de subclasificación**

Pensamos en:
- Qué **"cosas"** hay en nuestro software (los objetos)
- Cómo se **comunican/relacionan** entre sí

### ¿Qué es un Objeto?

Un objeto es una **abstracción de una entidad del dominio del problema**.

**Ejemplos del dominio**: Persona, Producto, Cuenta Bancaria, Auto, Plan de Estudios

**Ejemplos del espacio de solución**: estructuras de datos, tipos básicos, archivos, ventanas, conexiones, iconos, adaptadores

### Características de un Objeto

Todo objeto tiene:

#### 1. **Conocimiento (Estado Interno)**
- Basado en relaciones con otros objetos y su estado interno
- Es **privado** del objeto
- Se mantiene en **variables de instancia**
- Solo se accede en las operaciones del objeto

#### 2. **Comportamiento**
- Conjunto de **mensajes que un objeto sabe responder**
- Otros objetos le envían mensajes para que haga algo o para obtener algo
- Cuando recibe un mensaje, **ejecuta un método**

#### 3. **Identidad**
- Para distinguir un objeto de otro (independiente de sus propiedades)
- Se define en el momento de creación
- Todas las propiedades pueden cambiar, pero no la identidad

### Conceptos Fundamentales

#### Encapsulamiento y Ocultamiento

- **Encapsulamiento**: Agrupar en un mismo módulo (objeto) los datos y el comportamiento que opera sobre esos datos
- **Ocultamiento de información**: Proteger decisiones de diseño que puedan cambiar tras interfaces estables

#### Instancias vs Clases

- **Al diseñar/programar**: describimos clases
- **Al ejecutar**: tenemos objetos (instancias)
- Los objetos:
  - Se crean dinámicamente durante la ejecución
  - Reciben mensajes y ejecutan métodos
  - En los métodos, se envían mensajes a otros objetos

#### Formas de Conocimiento

Un objeto **solo puede enviar mensajes a otros que conoce**:

1. **Conocimiento Interno**: Variables de instancia / estado del objeto
2. **Conocimiento Externo**: Parámetros
3. **Conocimiento Temporal**: Variables temporales
4. **Pseudo-variables especiales**: "this"/"self" y "super"

#### Métodos

Un método es:
- Una **operación definida en el contexto de una clase**
- Lo ejecuta un objeto al **recibir un mensaje**
- Puede recibir parámetros y devolver algo (o nada)
- Puede hacer uso de las variables del objeto receptor
- Puede modificar al objeto que lo ejecuta
- Puede enviar mensajes (incluso al propio objeto)

#### Envío de Mensajes

Estructura básica: `receptor.mensaje(parámetros)`

Ejemplo: `cuentaSueldo.transferir(cuentaDeAhorros, 1000)`

- **No sabemos** qué método ejecuta el objeto
- **No nos importa** la implementación específica

#### Binding Dinámico

- Cuando un objeto recibe un mensaje, se busca un método con la firma correspondiente
- Si se encuentra, se ejecuta "en el contexto del objeto receptor"
- Si no se encuentra, tendremos un error en tiempo de ejecución
- Permite desacoplar "lo que quiero hacer" de las "formas de hacerlo"

#### Instanciación

- Mecanismo de **creación de objetos**
- Los objetos se instancian a partir de un **molde (clase)**
- Todo objeto es **instancia de una clase**
- Un objeto **nunca cambia de clase**

Todas las instancias de una misma clase:
- Tienen la misma estructura interna (mismas variables de instancia)
- Responden al mismo protocolo de la misma manera

#### Identidad vs Igualdad

- **Identidad**: Para saber si dos variables apuntan al mismo objeto (`==`)
- **Igualdad**: Dos objetos pueden ser iguales (`equals()`)
- La igualdad se define en función del dominio
- `equals()` puede redefinirse en cada clase

**Implicancias**:
- Dos objetos pueden ser iguales, pero no pueden ser idénticos
- Dos variables pueden apuntar al mismo objeto o a objetos diferentes (iguales o no)
- No necesitamos identificadores únicos para mantener relaciones

## Objetivos del Curso

### Contenidos

- Reconocer aspectos de OO que aportan a hacer software mantenible, entendible, escalable, reusable, integrable
- Utilizar el dominio como guía para modularizar en términos de objetos
- Modelar sistemas en objetos, independiente del lenguaje
- Programar en Java reconociendo sus aportes
- Evaluar críticamente diseños e implementaciones
- Utilizar tests unitarios como especificación y herramienta de pensamiento
- **"Pensar en objetos"**

### Prerrequisitos

- Escritura de algoritmos (programación estructurada)
- Estrategias de refinamiento sucesivo
- Variables como punteros
- Manejo de IDE
- Conocimientos básicos de objetos, clases, métodos, herencia
- Sintaxis de Java

## Reflexión sobre IA y Diseño de Software

Aunque tengamos IA disponible, seguimos necesitando:

- Programar y que el código sea claro
- Diseñar y entender diseños
- Módulos cohesivos y poco acoplados

**Recordatorio importante**: La IA puede equivocarse, aunque suene segura. Para darnos cuenta, debemos saber hacer las cosas bien nosotros mismos. El objetivo es **aprender**, no solo obtener soluciones.