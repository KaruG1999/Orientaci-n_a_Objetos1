# Colecciones en Java - Apunte Práctico

## 1. Las Colecciones como Estrategia para Implementar el Uno a Muchos

### ¿Qué son las Colecciones?
- **Objetos contenedores** que almacenan referencias a otros objetos
- **Frameworks/Librerías** que proporcionan estructuras de datos reutilizables
- **Estrategia para relaciones 1:N**: Un cliente tiene muchas facturas, una empresa tiene muchos empleados
- Buscan: **contenido heterogéneo** (diferentes tipos) y **comportamiento homogéneo** (mismas operaciones)

### Organización del Framework
- **Interfaces**: Definen contratos (List, Set, Map, Queue)
- **Clases abstractas**: Implementaciones parciales
- **Clases concretas**: ArrayList, LinkedList, HashSet, etc.
- **Algoritmos útiles**: Collections.sort(), shuffle(), etc.

### Tipos Populares (Interfaces)

#### List - Lista Ordenada
**Qué es:** Colección ordenada que permite duplicados y acceso por posición
**Cuándo usar:** Cuando necesitas mantener orden de inserción y acceder por índice
```java
List<String> nombres = new ArrayList<>();  // Implementación con array dinámico
List<String> numeros = new Vector<>();     // Implementación thread-safe
nombres.add("Juan");      // Agregar elemento
nombres.get(0);          // Acceso por índice
nombres.size();          // Cantidad de elementos
nombres.contains("Ana"); // ¿Contiene elemento?
```

#### Set - Conjunto Sin Duplicados
**Qué es:** Colección que no permite elementos duplicados
**Cuándo usar:** Cuando quieres garantizar unicidad de elementos
```java
Set<Integer> unicos = new HashSet<>();  // Implementación con hash table
unicos.add(5);
unicos.add(5);           // No se duplica, sigue teniendo un solo 5
unicos.contains(5);      // true - verificar existencia
unicos.size();           // 1
```

#### Map - Mapeo Clave-Valor
**Qué es:** Asocia claves únicas con valores (como diccionario)
**Cuándo usar:** Para busquedas rápidas por clave, representar relaciones
```java
Map<String, Integer> edades = new HashMap<>();  // Clave String, Valor Integer
edades.put("Ana", 25);       // Asociar clave-valor
edades.get("Ana");           // Retorna 25 - buscar por clave
edades.containsKey("Pedro"); // ¿Existe la clave?
edades.keySet();             // Obtener todas las claves
edades.values();             // Obtener todos los valores
```

#### Queue - Cola FIFO
**Qué es:** Colección que procesa elementos en orden FIFO (First In, First Out)
**Cuándo usar:** Para procesar tareas en orden, sistemas de turnos
```java
Queue<String> cola = new LinkedList<>();
cola.offer("primero");   // Agregar al final (encolar)
cola.poll();            // Extraer del inicio (desencolar)
cola.peek();            // Ver el próximo sin extraer
```

### Usos en la Materia
1. **Mantener relaciones entre objetos**
2. **Como repositorios** (almacenar datos)

---

## 2. Comparator e Iterator - Encapsular lo que nos Molesta

### Principio Clave
> **Escribir código lo más independiente posible de la colección utilizada**

### Ordenar

#### Con Comparator - Encapsula la Lógica de Comparación
**Qué es:** Objeto que define cómo comparar dos elementos
**Por qué usar:** Encapsula la lógica de ordenamiento, permite múltiples criterios
```java
import java.util.Comparator;

List<Cliente> clientes = new ArrayList<>();
// Comparator.comparing() - crea comparator basado en un método
clientes.sort(Comparator.comparing(Cliente::getEdad));

// comparing() con lambda - más explícito
clientes.sort(Comparator.comparing(cliente -> cliente.getNombre()));

// compare() manual - mayor control
clientes.sort((c1, c2) -> c1.getNombre().compareTo(c2.getNombre()));

// Comparators compuestos - múltiples criterios
clientes.sort(Comparator.comparing(Cliente::getCiudad)
             .thenComparing(Cliente::getEdad));
```

#### Con TreeSet (ordenamiento automático)
**Qué es:** Set que mantiene elementos ordenados automáticamente
```java
Set<Cliente> clientesOrdenados = new TreeSet<>(Comparator.comparing(Cliente::getNombre));
// Los elementos se insertan ya ordenados
```

### Recorrer Colecciones

#### Problema del for tradicional
**Por qué es problemático:** Solo funciona con colecciones indexadas (List), propenso a errores
```java
// PROBLEMÁTICO - solo para colecciones indexadas, riesgo de IndexOutOfBoundsException
for(int i = 0; i < lista.size(); i++) {
    // ¿Qué pasa si lista cambia de tamaño durante iteración?
}
```

#### Solución: Iterator - Encapsula el Recorrido
**Qué es:** Objeto que encapsula la lógica de recorrido de cualquier colección
**Ventajas:** Polimórfico (funciona con todas), encapsula complejidad, eliminación segura
```java
// Iterator - funciona con TODAS las colecciones
Iterator<Cliente> it = clientes.iterator();
while(it.hasNext()) {              // hasNext() - ¿hay siguiente?
    Cliente cliente = it.next();   // next() - obtener siguiente
    // Procesar cliente
    if(cliente.esMoroso()) {
        it.remove();               // remove() - eliminación SEGURA
    }
}

// forEachRemaining() - procesar elementos restantes
it.forEachRemaining(cliente -> System.out.println(cliente));

// Método alternativo moderno
clientes.forEach(cliente -> System.out.println(cliente));
```

### ⚠️ Precaución Importante
- **No modificar colecciones obtenidas de otros objetos**
- Cada objeto es responsable de mantener sus invariantes
- Solo el dueño de la colección puede modificarla
- Una colección puede cambiar después de obtenerla

---

## 3. Streams - Polimorfismo al Extremo

### ¿Qué son las Expresiones Lambda?
**Definición:** Funciones anónimas que representan un bloque de código ejecutable
**Objetivo:** Escribir código más conciso y funcional
```java
// Sintaxis básica: parámetro -> expresión
cliente -> cliente.esMoroso()

// Equivalente a método tradicional:
public boolean esMoroso(Cliente cliente) {
    return cliente.esMoroso();
}

// Con múltiples parámetros: (param1, param2) -> expresión
(c1, c2) -> c1.getEdad().compareTo(c2.getEdad())
```

### Características de Streams
- **No almacenan datos**: Solo proveen acceso a una fuente
- **No modifican la fuente**: Cada operación produce nuevo resultado
- **Pueden ser infinitos**: Generación bajo demanda
- **Consumibles**: Cada elemento se visita una sola vez

### Sintaxis Básica
```java
List<Cliente> resultado = clientes.stream()
    .operacionIntermedia1()
    .operacionIntermedia2()
    .operacionTerminal();
```

### Stream Pipeline
Encadenamiento de mensajes donde la **operación terminal guía todo el proceso**.

### Operaciones Principales

#### filter() - Filtrar Elementos
**Qué hace:** Selecciona elementos que cumplen una condición
**Retorna:** Stream con elementos filtrados
```java
// Calcular deuda total de clientes morosos
double deudaMorosa = clientes.stream()
    .filter(cli -> cli.esMoroso())        // Mantiene solo morosos
    .mapToDouble(cli -> cli.getDeuda())   // Convierte a números
    .sum();                               // Suma todo
```

#### map() - Transformar Elementos
**Qué hace:** Transforma cada elemento aplicando una función
**Retorna:** Stream con elementos transformados
```java
// Generar facturas para clientes morosos
List<Factura> facturasMorosas = clientes.stream()
    .filter(cli -> cli.esMoroso())
    .map(cli -> this.facturarDeuda(cli))  // Transforma Cliente -> Factura
    .collect(Collectors.toList());
```

#### collect() - Recolectar Resultados
**Qué hace:** Operación terminal que materializa el resultado en una colección
**Cuándo usar:** Al final del pipeline para obtener resultado concreto
```java
// Collectors más usados:
.collect(Collectors.toList())           // A List
.collect(Collectors.toSet())            // A Set
.collect(Collectors.counting())         // Contar elementos

// Agrupar clientes por ciudad
Map<String, List<Cliente>> porCiudad = clientes.stream()
    .collect(Collectors.groupingBy(Cliente::getCiudad));

// Unir strings
String nombres = clientes.stream()
    .map(Cliente::getNombre)
    .collect(Collectors.joining(", "));
```

#### Operaciones de Reducción
**Qué hacen:** Combinan elementos para producir un resultado único
```java
// max() - encontrar máximo
Cliente deudorMayor = clientes.stream()
    .filter(cli -> cli.esMoroso())
    .max(Comparator.comparing(Cliente::getDeuda))
    .orElse(null);                        // orElse() - valor por defecto

// min() - encontrar mínimo  
// sum() - sumar elementos
// count() - contar elementos
long cantidadMorosos = clientes.stream()
    .filter(Cliente::esMoroso)
    .count();
```

### Otros Collectors Útiles
```java
// Convertir a Set
.collect(Collectors.toSet())

// Unir strings
.collect(Collectors.joining(", "))

// Sumar valores
.collect(Collectors.summingDouble(Cliente::getDeuda))

// Agrupar y contar
.collect(Collectors.groupingBy(Cliente::getCiudad, Collectors.counting()))
```

### Ejemplos Prácticos Completos

#### Ejemplo 1: Procesar Deudas
```java
// Calcular total de deuda morosa (código tradicional vs streams)
// Tradicional:
double deudaMorosa = 0;
for (Cliente cli : clientes) {
    if (cli.esMoroso()) {
        deudaMorosa += cli.getDeuda();
    }
}

// Con Streams:
double deudaMorosa = clientes.stream()
    .filter(Cliente::esMoroso)
    .mapToDouble(Cliente::getDeuda)
    .sum();
```

#### Ejemplo 2: Generar Reportes
```java
// Generar lista de clientes VIP con deuda alta
List<String> clientesVIP = clientes.stream()
    .filter(cli -> cli.getDeuda() > 10000)
    .filter(Cliente::esVIP)
    .map(Cliente::getNombre)
    .sorted()
    .collect(Collectors.toList());
```

## Streams Adicionales a Investigar (Más Usados)

### Operaciones de Búsqueda y Verificación
```java
// findFirst() - encuentra el primer elemento que cumple criterio
Optional<Cliente> primerMoroso = clientes.stream()
    .filter(Cliente::esMoroso)
    .findFirst();  // Retorna Optional<Cliente>

// anyMatch() - ¿algún elemento cumple la condición?
boolean hayMorosos = clientes.stream()
    .anyMatch(Cliente::esMoroso);  // Retorna boolean

// allMatch() - ¿todos los elementos cumplen la condición?
boolean todosPagaron = clientes.stream()
    .allMatch(cli -> cli.getDeuda() == 0);
```

### Operaciones de Transformación y Control
```java
// distinct() - elimina duplicados
List<String> ciudadesUnicas = clientes.stream()
    .map(Cliente::getCiudad)
    .distinct()  // Elimina ciudades repetidas
    .collect(Collectors.toList());

// limit() - limita cantidad de elementos
List<Cliente> primerosCinco = clientes.stream()
    .limit(5)  // Solo los primeros 5
    .collect(Collectors.toList());

// sorted() - ordena elementos
List<Cliente> ordenados = clientes.stream()
    .sorted(Comparator.comparing(Cliente::getNombre))
    .collect(Collectors.toList());
```

## ⚠️ Consideraciones de Diseño
- **Encapsular lo que varía**: Usar interfaces en lugar de clases concretas
- **Romper encapsulamiento**: Cuidado al retornar colecciones directas
- **Defensive copy**: Considerar retornar copias de colecciones internas
- **Immutable collections**: Usar `Collections.unmodifiableList()` cuando sea apropiado