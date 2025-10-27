# Guía Completa para Aprobar los Parciales - Orientación a Objetos 1

## 📋 Contenido del Examen

Los parciales evalúan principalmente **aspectos prácticos** e incluyen:
- Diseño orientado a objetos
- Implementación en Java
- Tests automatizados
- Modelado UML
- Aplicación de conceptos teóricos

---

## 🎯 Conceptos Fundamentales a Dominar

### 1. Principios Básicos de POO

#### Clase vs Instancia/Objeto
- **Clase**: Plantilla o molde que define atributos y comportamiento
- **Objeto/Instancia**: Materialización concreta de una clase
- Cada objeto tiene **identidad** propia (≠ igualdad de valores)

#### Encapsulamiento y Ocultamiento
- Ocultar detalles internos de implementación
- Exponer solo interfaz pública necesaria
- Variables de instancia **privadas**
- Métodos de acceso cuando sea necesario (getters/setters)

#### Mensaje vs Método
- **Mensaje**: Solicitud enviada a un objeto
- **Método**: Código que se ejecuta en respuesta al mensaje
- **Binding dinámico**: El método se resuelve en tiempo de ejecución

#### Variables de Instancia
- Decidir qué variables son necesarias
- Evaluar alternativas (¿derivable vs almacenado?)
- Inicialización apropiada (constructor, valores por defecto)

---

### 2. Relaciones entre Objetos

#### Composición (tiene-un)
- Un objeto contiene referencias a otros objetos
- Relaciones uno-a-uno o uno-a-muchos
- Usar colecciones (`List<T>`, `Set<T>`, `Map<K,V>`) para uno-a-muchos

#### Herencia (es-un)
- Reutilización de código y comportamiento
- **Generalización**: Extraer comportamiento común a superclase
- **Especialización**: Agregar/modificar en subclases
- Uso de `super` para reutilizar comportamiento heredado
- Uso de `this` para delegación interna

#### Delegación
- Estrategia clave de diseño OO
- Enviar mensajes a otros objetos para realizar tareas
- Evita duplicación de código

---

### 3. Polimorfismo

#### Definición
- Mismo mensaje, diferentes comportamientos según el objeto receptor
- Fundamental para diseño flexible y extensible

#### Tipos de Polimorfismo
- **Entre clases relacionadas por herencia**: Misma interfaz, implementaciones diferentes
- **Entre objetos en colecciones heterogéneas**: Colección de tipo general contiene subtipos específicos
- **Interfaces**: Contrato que garantiza comportamiento común

#### Aplicación
- Permite tratar objetos de diferentes clases de manera uniforme
- Reduce acoplamiento entre componentes

---

### 4. Clases Abstractas e Interfaces

#### Clases Abstractas
```java
public abstract class Cuenta {
    protected double saldo;
    
    // Método concreto (implementado)
    public void depositar(double monto) {
        this.saldo += monto;
    }
    
    // Método abstracto (debe ser implementado por subclases)
    protected abstract boolean puedeExtraer(double monto);
}
```
- No se pueden instanciar directamente
- Pueden tener métodos concretos y abstractos
- Útiles para compartir código común entre subclases

#### Interfaces
```java
public interface DateLapse {
    LocalDate getFrom();
    LocalDate getTo();
    int sizeInDays();
    boolean includesDate(LocalDate date);
}
```
- Solo definen contratos (qué, no cómo)
- Una clase puede implementar múltiples interfaces
- Útiles para polimorfismo sin herencia

---

## 🔧 Aspectos de Implementación Java

### Colecciones Esenciales

#### ArrayList
```java
List<Producto> productos = new ArrayList<>();
productos.add(new Producto("Pan", 100));
productos.size();
productos.get(0);
```
- Lista ordenada, permite duplicados
- Acceso por índice

#### HashSet
```java
Set<String> nombres = new HashSet<>();
nombres.add("Juan");
nombres.add("Juan"); // No se agrega (sin duplicados)
```
- Sin orden, sin duplicados
- Búsqueda rápida

#### HashMap
```java
Map<String, Integer> goles = new HashMap<>();
goles.put("Messi", 111);
goles.get("Messi"); // Retorna 111
goles.containsKey("Messi"); // true
```
- Pares clave-valor
- Claves únicas

### Manejo de Fechas
```java
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

LocalDate fecha1 = LocalDate.of(1972, 9, 15);
LocalDate hoy = LocalDate.now();

// Comparación
fecha1.isBefore(hoy);
fecha1.isAfter(hoy);

// Cálculo de diferencias
long dias = ChronoUnit.DAYS.between(fecha1, hoy);
long meses = ChronoUnit.MONTHS.between(fecha1, hoy);
```

### Operaciones con Streams
```java
// Filtrar
productos.stream()
    .filter(p -> p.getPrecio() > 100)
    .collect(Collectors.toList());

// Sumar
double total = productos.stream()
    .mapToDouble(p -> p.getPrecio())
    .sum();

// Contar
long cantidad = productos.stream()
    .filter(p -> p.getStock() > 0)
    .count();
```

---

## 📐 Modelado UML

### Diagrama de Clases

#### Componentes Básicos
```
┌─────────────────┐
│   NombreClase   │
├─────────────────┤
│ - atributo1     │
│ - atributo2     │
├─────────────────┤
│ + metodo1()     │
│ + metodo2()     │
└─────────────────┘
```

#### Visibilidad
- `+` público
- `-` privado
- `#` protegido

#### Relaciones
- **Asociación**: Línea simple (→)
- **Composición**: Rombo negro (◆→)
- **Agregación**: Rombo blanco (◇→)
- **Herencia**: Flecha hueca (△→)
- **Implementación**: Flecha hueca discontinua (△┄→)

#### Multiplicidad
- `1` : exactamente uno
- `0..1` : cero o uno
- `*` o `0..*` : cero o muchos
- `1..*` : uno o muchos
- `n..m` : entre n y m

### Ejemplo Completo
```
        ┌──────────────┐
        │   Usuario    │
        ├──────────────┤
        │ - nombre     │
        │ - dni        │
        └──────────────┘
                △
                │ (herencia)
      ┌─────────┴─────────┐
      │                   │
┌─────────┐         ┌─────────┐
│ Persona │         │Corporativo│
│ Física  │         │          │
└─────────┘         └─────────┘
      │
      │ 1..*
      │ realiza
      ▼
┌──────────┐
│  Envío   │◆───── 1..* ────▶ Producto
└──────────┘      contiene
```

---

## 🏗️ Proceso de Diseño

### Metodología Paso a Paso

#### 1. Identificar Conceptos Candidatos
- Leer la especificación del problema
- Extraer **sustantivos** → posibles clases
- Extraer **verbos** → posibles métodos/responsabilidades

#### 2. Clasificar Conceptos
- **Clases del dominio**: Entidades principales
- **Atributos**: Datos simples de las clases
- **Asociaciones**: Relaciones entre clases
- **Descartar**: Conceptos redundantes o fuera del alcance

#### 3. Construir Modelo de Dominio
- Diagrama UML con clases identificadas
- Agregar atributos (sin tipos por ahora)
- Establecer asociaciones con multiplicidad

#### 4. Definir Responsabilidades
- Para cada caso de uso, identificar qué clase debe realizar qué operación
- Aplicar principio de **Experto en Información**: asignar responsabilidad a la clase que tiene los datos necesarios
- Considerar **Alta Cohesión** y **Bajo Acoplamiento**

#### 5. Refinar el Diseño
- Decidir tipos de datos para atributos
- Definir signatures de métodos
- Identificar herencia o interfaces si hay comportamiento común
- Considerar polimorfismo para variabilidad

#### 6. Implementar
- Escribir clases en Java
- Implementar constructores
- Implementar métodos públicos
- Implementar métodos auxiliares privados si es necesario

#### 7. Testear
- Escribir tests JUnit para cada funcionalidad
- Considerar valores de borde y particiones equivalentes

---

## ✅ Tests Automatizados (JUnit)

### Estructura Básica
```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CuentaTest {
    private Cuenta cuenta;
    
    @BeforeEach
    void setUp() {
        cuenta = new CajaDeAhorro();
    }
    
    @Test
    void testDepositar() {
        cuenta.depositar(1000);
        assertEquals(1000, cuenta.getSaldo());
    }
    
    @Test
    void testExtraer() {
        cuenta.depositar(1000);
        assertTrue(cuenta.extraer(500));
        assertEquals(500, cuenta.getSaldo());
    }
}
```

### Assertions Comunes
```java
assertEquals(esperado, actual);
assertNotEquals(noEsperado, actual);
assertTrue(condicion);
assertFalse(condicion);
assertNull(objeto);
assertNotNull(objeto);
assertThrows(ExcepcionEsperada.class, () -> codigo);
```

### Diseño de Casos de Prueba

#### Valores de Borde
Probar límites donde el comportamiento cambia:
```java
// Si el descuento aplica para compras > 100
@Test void testSinDescuento99() { ... }   // 99 (justo antes)
@Test void testSinDescuento100() { ... }  // 100 (límite)
@Test void testConDescuento101() { ... }  // 101 (justo después)
```

#### Particiones Equivalentes
Agrupar entradas que producen comportamiento similar:
```java
// Para categorías de emails por tamaño
@Test void testEmailPequeno() { ... }    // 0-300
@Test void testEmailMediano() { ... }    // 301-500
@Test void testEmailGrande() { ... }     // 501+
```

---

## 🎨 Patrones y Heurísticas de Diseño

### Principios GRASP

#### 1. Experto en Información
- Asignar responsabilidad a la clase que tiene la información necesaria
- Ejemplo: `Producto` calcula su propio peso, no la `Balanza`

#### 2. Creador
- Asignar a B la responsabilidad de crear A si:
  - B contiene/agrega A
  - B registra A
  - B usa intensamente A
  - B tiene datos de inicialización de A

#### 3. Bajo Acoplamiento
- Minimizar dependencias entre clases
- Usar interfaces para desacoplar
- Evitar cadenas largas de dependencias

#### 4. Alta Cohesión
- Una clase debe tener responsabilidades relacionadas
- Evitar clases "todologo" que hacen de todo
- Dividir en clases más específicas si es necesario

#### 5. Controlador
- Objeto que maneja eventos del sistema
- Primera capa después de la UI
- Coordina operaciones del dominio

### Heurísticas Prácticas

#### Naming Conventions
```java
// Clases: sustantivos en UpperCamelCase
class Producto, CuentaCorriente, JobScheduler

// Métodos: verbos en lowerCamelCase
depositar(), calcularPrecio(), esAptoCeliaco()

// Variables: sustantivos en lowerCamelCase
saldo, fechaCreacion, productos

// Constantes: mayúsculas con guiones bajos
MAX_DESCUBIERTO, PRECIO_BASE
```

#### Evitar Code Smells
- **Secuencia de ifs/switch**: Usar polimorfismo
- **Código duplicado**: Extraer método común o usar herencia
- **Métodos muy largos**: Dividir en submétodos privados
- **Clases muy grandes**: Dividir responsabilidades
- **Uso excesivo de getters**: Delegar comportamiento en lugar de exponer datos

---

## 📝 Checklist para el Examen

### Antes de Empezar
- [ ] Leer el enunciado completo
- [ ] Identificar los casos de uso/funcionalidades requeridas
- [ ] Subrayar sustantivos (clases) y verbos (métodos)
- [ ] Identificar relaciones entre conceptos

### Durante el Diseño
- [ ] Diagrama de clases con todas las clases identificadas
- [ ] Atributos con tipos apropiados
- [ ] Métodos públicos con signatures completas
- [ ] Asociaciones con multiplicidad correcta
- [ ] Herencia/interfaces donde corresponda
- [ ] Visibilidades correctas (+, -, #)

### Durante la Implementación
- [ ] Todas las clases implementadas
- [ ] Constructores que inicializan correctamente
- [ ] Variables de instancia privadas
- [ ] Métodos públicos según especificación
- [ ] Delegación apropiada
- [ ] Sin código duplicado
- [ ] Naming conventions consistentes

### Testing
- [ ] Tests para cada funcionalidad principal
- [ ] Casos de valores de borde
- [ ] Casos de particiones equivalentes
- [ ] Tests que verifican pre y post condiciones
- [ ] Todos los tests pasan (verde)

### Revisión Final
- [ ] El diseño cumple con los requerimientos
- [ ] Alta cohesión en cada clase
- [ ] Bajo acoplamiento entre clases
- [ ] Polimorfismo aplicado donde es útil
- [ ] Código legible y bien organizado
- [ ] No hay warnings del compilador

---

## 🔄 Patrones de Diseño Comunes

### Template Method (Método Plantilla)
```java
public abstract class Cuenta {
    // Método plantilla (concreto)
    public boolean extraer(double monto) {
        if (puedeExtraer(monto)) {
            extraerSinControlar(monto);
            return true;
        }
        return false;
    }
    
    // Gancho (abstracto) - subclases definen la política
    protected abstract boolean puedeExtraer(double monto);
    
    // Método auxiliar (concreto)
    protected void extraerSinControlar(double monto) {
        this.saldo -= monto;
    }
}

public class CajaDeAhorro extends Cuenta {
    @Override
    protected boolean puedeExtraer(double monto) {
        return this.saldo >= monto * 1.02; // incluye 2% de costo
    }
}
```

### Strategy (Estrategia)
```java
public interface PoliticaCancelacion {
    double calcularReembolso(Reserva reserva, LocalDate fechaCancelacion);
}

public class PoliticaFlexible implements PoliticaCancelacion {
    public double calcularReembolso(Reserva reserva, LocalDate fechaCancelacion) {
        return reserva.calcularPrecio();
    }
}

public class Propiedad {
    private PoliticaCancelacion politica;
    
    public void setPolitica(PoliticaCancelacion politica) {
        this.politica = politica;
    }
    
    public double calcularReembolso(Reserva reserva, LocalDate fecha) {
        return politica.calcularReembolso(reserva, fecha);
    }
}
```

### Composite (para estructuras recursivas)
```java
// Para modelar genealogía
public class Mamifero {
    private Mamifero padre;
    private Mamifero madre;
    
    public boolean tieneComoAncestroA(Mamifero ancestro) {
        if (this.padre == ancestro || this.madre == ancestro) {
            return true;
        }
        boolean result = false;
        if (this.padre != null) {
            result = this.padre.tieneComoAncestroA(ancestro);
        }
        if (!result && this.madre != null) {
            result = this.madre.tieneComoAncestroA(ancestro);
        }
        return result;
    }
}
```

---

## 💡 Errores Comunes a Evitar

### En Diseño
❌ **No identificar todas las clases necesarias**
✅ Listar todos los sustantivos relevantes del dominio

❌ **Poner toda la lógica en una sola clase**
✅ Distribuir responsabilidades según el principio del Experto

❌ **Usar herencia cuando debería ser composición**
✅ Preguntarse: ¿realmente "es-un" o solo "tiene-un"?

❌ **Exponer todo con getters/setters**
✅ Delegar comportamiento, no exponer datos internos

### En Implementación
❌ **Variables de instancia públicas**
✅ Siempre privadas, acceder por métodos

❌ **No inicializar colecciones en el constructor**
```java
// Mal
private List<Producto> productos; // null!

// Bien
private List<Producto> productos = new ArrayList<>();
```

❌ **Olvidar el `this` cuando es necesario**
```java
// Ambiguo
public void setNombre(String nombre) {
    nombre = nombre; // ¿cuál es cuál?
}

// Claro
public void setNombre(String nombre) {
    this.nombre = nombre;
}
```

❌ **Comparar objetos con `==` en lugar de `.equals()`**
```java
// Mal (compara referencias)
if (fecha1 == fecha2) { ... }

// Bien (compara contenido)
if (fecha1.equals(fecha2)) { ... }
```

### En Testing
❌ **No testear casos límite**
✅ Siempre probar: vacío, uno, muchos, máximo, mínimo

❌ **Tests que dependen de otros tests**
✅ Cada test debe ser independiente (usar `@BeforeEach`)

❌ **No testear casos de error**
✅ Verificar que se manejan correctamente situaciones inválidas

---

## 📚 Ejercicios Clave por Tema

### Conceptos Básicos
- **Ejercicio 1 (WallPost)**: Clase simple, atributos, métodos básicos
- **Ejercicio 2 (Balanza)**: Composición, trabajar con objetos relacionados

### Colecciones
- **Ejercicio 3 (Presupuestos)**: Colecciones uno-a-muchos, iteración
- **Ejercicio 4 (Balanza mejorada)**: Mantener colecciones, calcular sobre ellas

### Herencia y Polimorfismo
- **Ejercicio 11 (Cuenta con ganchos)**: Template method, clases abstractas
- **Ejercicio 12 (Job Scheduler)**: Eliminar ifs con polimorfismo
- **Ejercicio 14 (Sólidos)**: Jerarquía de clases, método abstracto

### Diseño Completo
- **Ejercicio 19 (OOBnB)**: Modelado completo desde especificación
- **Ejercicio 20 (Políticas)**: Strategy pattern, cambiar comportamiento
- **Ejercicio 21 (Envíos)**: Herencia con múltiples variantes

### Colecciones Avanzadas
- **Ejercicio 18 (Filtered Set)**: Implementar interfaces de colecciones
- **Ejercicio 25 (Bag y Map)**: Usar Map, implementar colección personalizada

---

## 🎓 Estrategia de Estudio

### Semana 1-2: Fundamentos
1. Repasar ejercicios 1-4
2. Asegurarse de entender: clases, objetos, mensajes, colecciones básicas
3. Practicar con tests simples

### Semana 3-4: Herencia y Polimorfismo
1. Estudiar ejercicios 10-12, 14
2. Entender method lookup y binding dinámico
3. Identificar cuándo usar herencia vs composición

### Semana 5-6: Diseño Completo
1. Practicar ejercicios 19-22
2. Seguir la metodología: conceptos → modelo → implementación → tests
3. Hacer diagramas UML completos

### Antes del Parcial
1. Repasar checklist de este documento
2. Hacer un ejercicio completo sin mirar la solución
3. Revisar errores comunes
4. Repasar sintaxis de Java (colecciones, fechas, streams)

---

## 🚀 Consejos para el Día del Examen

1. **Leer todo el enunciado** antes de empezar a escribir
2. **Hacer un boceto** del diagrama de clases en papel
3. **Implementar de a poco**: una clase a la vez, testear, continuar
4. **No optimizar prematuramente**: primero que funcione, luego mejorar
5. **Nombrar bien**: nombres claros valen más que comentarios
6. **Testear frecuentemente**: no esperar a terminar todo
7. **Administrar el tiempo**: si algo no sale, continuar y volver después
8. **Revisar antes de entregar**: ¿cumple todos los requerimientos?

---

## 📖 Recursos Adicionales

### Documentación Java
- `java.util.List`, `ArrayList`
- `java.util.Set`, `HashSet`
- `java.util.Map`, `HashMap`
- `java.time.LocalDate`
- `java.util.stream.Stream`

### Comandos Maven Útiles
```bash
mvn clean compile    # Compilar
mvn test            # Ejecutar tests
```

### Atajos IDE
- `Ctrl+Space`: Autocompletar
- `Ctrl+1`: Quick fix
- `Ctrl+Shift+O`: Organizar imports
- `Alt+Shift+R`: Rename
- `Ctrl+Shift+F`: Formatear código

---

## 📋 Resumen de Conceptos Clave

### El Proceso Completo en 7 Pasos

1. **Leer y entender** el problema
2. **Identificar conceptos** (sustantivos = clases, verbos = métodos)
3. **Modelar** con UML (clases, atributos, relaciones)
4. **Asignar responsabilidades** (¿quién sabe? → Experto)
5. **Implementar** en Java (de a poco, testeando)
6. **Testear** (valores borde, particiones equivalentes)
7. **Refactorizar** (eliminar duplicación, mejorar nombres)

### Las 3 Preguntas Mágicas

1. **¿Quién tiene la información?** → Ahí va la responsabilidad
2. **¿Es realmente "es-un"?** → Usar herencia; sino composición
3. **¿Hay comportamiento común variable?** → Polimorfismo

### Reglas de Oro

- **Alta cohesión**: Cada clase hace una cosa bien
- **Bajo acoplamiento**: Pocas dependencias entre clases
- **No exponer datos**: Delegar comportamiento
- **DRY (Don't Repeat Yourself)**: Sin código duplicado
- **KISS (Keep It Simple)**: La solución más simple que funcione

---

**¡Éxitos en el parcial! 🎉**