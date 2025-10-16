# Síntesis: Conceptos Fundamentales de Diseño Orientado a Objetos

## 1. Entity vs. Value Object

### ¿Qué son y por qué los necesitamos?

En el diseño orientado a objetos, no todas las clases tienen el mismo propósito. Algunas representan **cosas que tienen identidad propia** (Entidades), mientras que otras representan **valores o características** (Value Objects).

### Entidades (Entity)

**¿Qué son?**
Son objetos del dominio que tienen una **identidad única** que los distingue, incluso si todos sus atributos son iguales a los de otra entidad.

**Características:**
- Tienen un **identificador único** (ID, DNI, número de cuenta, etc.)
- Son **modificables** a lo largo de su ciclo de vida (sus atributos pueden cambiar)
- Se comparan por **identidad**, no por contenido
- **Persisten independientemente** en la base de datos

**Ejemplo conceptual:**
Imagina dos personas gemelas idénticas llamadas Juan. Aunque tengan el mismo nombre, edad, altura, etc., son dos personas diferentes porque cada una tiene su propio DNI. Si Juan cambia de dirección, sigue siendo el mismo Juan.

**Representación en UML:**
```
┌─────────────────┐
│    Persona      │
├─────────────────┤
│ - dni: String   │ ← Identificador único
│ - nombre: String│
│ - edad: int     │
│ - direccion     │ ← Referencia a Value Object
├─────────────────┤
│ + cambiarDireccion()│
│ + cumplirAnios()│
└─────────────────┘
```

**Código en Java:**
```java
public class Persona {
    private String dni;  // Identificador - NO cambia
    private String nombre;
    private int edad;
    private Direccion direccion;  // Value Object
    
    public Persona(String dni, String nombre, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
    }
    
    // Tiene setters - ES MODIFICABLE
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void cambiarDireccion(Direccion nuevaDireccion) {
        this.direccion = nuevaDireccion;  // Reemplaza el Value Object completo
    }
    
    // Se compara por identidad (DNI)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Persona)) return false;
        Persona otra = (Persona) obj;
        return this.dni.equals(otra.dni);  // Solo compara el ID
    }
}
```

### Value Objects

**¿Qué son?**
Son objetos que representan **valores o conceptos** que se definen completamente por sus atributos. No tienen identidad propia, solo importa lo que contienen.

**Características:**
- **NO tienen identificador único**
- Son **inmutables** (no se pueden modificar después de crearlos)
- Se comparan por **contenido** (igualdad estructural)
- No viven independientemente, necesitan estar asociados a una Entidad
- Son **intercambiables** (dos Value Objects con el mismo contenido son equivalentes)
- Persisten **adjuntos a su entidad base**

**Ejemplo conceptual:**
Un billete de $100 es intercambiable por otro billete de $100. No importa el número de serie, lo que importa es su valor. Una dirección "Calle 50 N°123" es la misma sin importar quién la use, se define por sus componentes (calle, número, ciudad).

**¿Cuándo usar Value Objects?**
Cuando necesitas modelar: Moneda, Fecha, Dirección, Coordenadas, Rango de valores, Color, Teléfono, Email, etc.

**Representación en UML:**
```
┌─────────────────┐         ┌──────────────────────┐
│    Persona      │         │  <<value object>>    │
├─────────────────┤         │     Direccion        │
│ - dni: String   │         ├──────────────────────┤
│ - nombre: String│ ◆──────>│ - calle: String      │
│ - direccion     │         │ - numero: int        │
└─────────────────┘         │ - ciudad: String     │
                            │ - codigoPostal: String│
                            ├──────────────────────┤
                            │ + getDireccionCompleta()│
                            └──────────────────────┘
                            
Nota: El rombo relleno (◆) indica composición
```

**Código en Java:**
```java
public class Direccion {
    private final String calle;      // final = inmutable
    private final int numero;
    private final String ciudad;
    private final String codigoPostal;
    
    // Constructor que inicializa TODOS los valores
    public Direccion(String calle, int numero, String ciudad, String codigoPostal) {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }
    
    // Solo getters - NO SETTERS (inmutable)
    public String getCalle() { return calle; }
    public int getNumero() { return numero; }
    public String getCiudad() { return ciudad; }
    public String getCodigoPostal() { return codigoPostal; }
    
    // Puede tener comportamiento
    public String getDireccionCompleta() {
        return calle + " " + numero + ", " + ciudad + " (" + codigoPostal + ")";
    }
    
    // Se compara por CONTENIDO (todos sus atributos)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Direccion)) return false;
        Direccion otra = (Direccion) obj;
        return this.numero == otra.numero &&
               this.calle.equals(otra.calle) &&
               this.ciudad.equals(otra.ciudad) &&
               this.codigoPostal.equals(otra.codigoPostal);
    }
}
```

**¿Cómo se modifican los Value Objects?**
NO se modifican. Si necesitas cambiar algo, creas uno nuevo:

```java
// En la clase Persona
public void cambiarDireccion(String nuevaCalle, int nuevoNumero, 
                             String nuevaCiudad, String nuevoCP) {
    // No modificas la dirección existente, creas una nueva
    this.direccion = new Direccion(nuevaCalle, nuevoNumero, nuevaCiudad, nuevoCP);
}
```

### Comparación práctica

| Aspecto | Entity | Value Object |
|---------|--------|--------------|
| **Identidad** | Sí (ID único) | No |
| **Mutabilidad** | Modificable | Inmutable |
| **Comparación** | Por ID | Por contenido |
| **Persistencia** | Independiente | Adjunto a Entity |
| **Ejemplo** | Persona, Cuenta, Pedido | Dirección, Moneda, Fecha |

---

## 2. Heurísticas para Asignación de Responsabilidades (HAR)

### Polimorfismo
**Descripción:** Cuando el comportamiento varía según el tipo, asignar la responsabilidad a los tipos/clases para las que varía el comportamiento.

**Ejemplo práctico:**
```java
// ❌ MAL: Usar condicionales
public class ProcesadorPago {
    public double calcularBonificacion(TarjetaCredito tarjeta) {
        if (tarjeta.getTipo().equals("Gold")) {
            return tarjeta.getMonto() * 0.05;
        } else if (tarjeta.getTipo().equals("Platinum")) {
            return tarjeta.getMonto() * 0.10;
        } else {
            return 0;
        }
    }
}

// ✅ BIEN: Usar polimorfismo
public interface TarjetaCredito {
    double calcularBonificacion();
}

public class TarjetaGold implements TarjetaCredito {
    private double monto;
    
    public double calcularBonificacion() {
        return monto * 0.05;
    }
}

public class TarjetaPlatinum implements TarjetaCredito {
    private double monto;
    
    public double calcularBonificacion() {
        return monto * 0.10;
    }
}
```

### "No hables con extraños" (Law of Demeter)
**Descripción:** Evitar diseñar objetos que recorren largos caminos de estructura para enviar mensajes a objetos distantes.

**Dentro de un método solo pueden enviarse mensajes a:**
- `self/this`
- Un parámetro del método
- Un objeto asociado a `self/this`
- Un miembro de una colección atributo de `self/this`
- Un objeto creado dentro del método

**Ejemplo de violación:**
```java
// ❌ MAL: Librería habla con Pago (un extraño)
public class Libreria {
    public void procesarCompra(Compra compra) {
        double monto = compra.getPago().getMontoEntregado(); // Cadena larga
    }
}
```

**Solución correcta:**
```java
// ✅ BIEN: Librería solo habla con Compra (conocido)
public class Libreria {
    public void procesarCompra(Compra compra) {
        double monto = compra.getMontoEntregado(); // Compra delega internamente
    }
}

public class Compra {
    private Pago pago;
    
    public double getMontoEntregado() {
        return pago.getMontoEntregado(); // Compra maneja su estructura interna
    }
}
```

---

## 3. Herencia vs. Composición

### Herencia de Clases
- **Reutilización de Caja Blanca:** Debes conocer todo el código heredado
- Los cambios en la superclase se propagan automáticamente
- Útil para extender funcionalidad del dominio
- **Acoplamiento fuerte:** Cambios en la superclase afectan directamente a las subclases

### Composición de Objetos
- **Reutilización de Caja Negra:** Los objetos se reutilizan a través de su interfaz
- Composición dinámica en tiempo de ejecución
- Permite delegar responsabilidades
- **Acoplamiento débil:** Se pueden cambiar componentes sin afectar el contenedor

### Ejemplo: Stack mal diseñado (Herencia incorrecta)

**Problema:**
```java
public class Stack<T> extends ArrayList<T> {
    public void push(T object) { this.add(object); }
    public T pop() { return this.remove(this.size() - 1); }
}
```

**Errores:**
- **Semántico:** "Una pila es una ArrayList" no es cierto (viola Is-a)
- **Mecánico:** Expone toda la interfaz de ArrayList (add, remove, clear, etc.) cuando solo debería tener push/pop
- Viola el encapsulamiento

### Solución correcta con Composición

```java
public class Stack<T> {
    private ArrayList<T> elementos; // Composición
    
    public void push(T object) { 
        elementos.add(object); 
    }
    
    public T pop() { 
        return elementos.remove(elementos.size() - 1); 
    }
}
```

**Ventajas:**
- Oculta los detalles de implementación
- Interfaz pública controlada (solo push/pop)
- Permite cambiar la implementación interna sin afectar clientes

---

## 4. Principios SOLID

### S - Single Responsibility Principle (SRP)
**"Una clase debería cambiar por una sola razón"**

Una clase debe ser responsable de una única tarea y tener alta cohesión.

**Ejemplo:**
```java
// ❌ MAL: Múltiples responsabilidades
public class Usuario {
    private String nombre;
    
    public void guardarEnBD() { /* acceso a BD */ }
    public void enviarEmail() { /* lógica de email */ }
    public String generarReporte() { /* lógica de reportes */ }
}

// ✅ BIEN: Una sola responsabilidad por clase
public class Usuario {
    private String nombre;
    // Solo datos y comportamiento del usuario
}

public class UsuarioRepository {
    public void guardar(Usuario usuario) { /* acceso a BD */ }
}

public class EmailService {
    public void enviar(Usuario usuario) { /* lógica de email */ }
}
```

### O - Open/Closed Principle (OCP)
**"Abierto para extensión, cerrado para modificación"**

**Ejemplo:**
```java
// ❌ MAL: Hay que modificar la clase para agregar nuevos tipos
public class Mensaje {
    private String tipo; // "normal", "censurado", "encriptado"
    
    public String procesar() {
        if (tipo.equals("censurado")) {
            // lógica de censura
        } else if (tipo.equals("encriptado")) {
            // lógica de encriptación
        }
        // ¿Agregar nuevo tipo? ¡Modificar esta clase!
    }
}

// ✅ BIEN: Extensible sin modificar código existente
public interface ProcesadorMensaje {
    String procesar(String contenido);
}

public class ProcesadorNormal implements ProcesadorMensaje {
    public String procesar(String contenido) { return contenido; }
}

public class ProcesadorCensurado implements ProcesadorMensaje {
    public String procesar(String contenido) { /* censura */ }
}

// Agregar nuevo tipo: solo crear nueva clase, sin modificar las existentes
public class ProcesadorEncriptado implements ProcesadorMensaje {
    public String procesar(String contenido) { /* encripta */ }
}
```

### L - Liskov Substitution Principle (LSP)
**"Los objetos deben ser intercambiables por sus subtipos"**

**Ejemplo:**
```java
// ✅ BIEN: Pato puede ser reemplazado por cualquier subtipo
public class Pato {
    public void nadar() { System.out.println("Nadando..."); }
}

public class PatoReal extends Pato {
    @Override
    public void nadar() { System.out.println("Pato real nadando..."); }
}

public class PatoGoma extends Pato {
    @Override
    public void nadar() { System.out.println("Pato de goma flotando..."); }
}

// El código cliente funciona con cualquier tipo de Pato
public void hacerNadar(Pato pato) {
    pato.nadar(); // Funciona con PatoReal, PatoGoma, etc.
}
```

### I - Interface Segregation Principle (ISP)
**"Las clases no deben depender de interfaces que no utilizan"**

**Ejemplo:**
```java
// ❌ MAL: Interfaz muy grande
public interface Trabajador {
    void trabajar();
    void comer();
    void dormir();
    void cobrarSalario();
}

// Robot debe implementar dormir() y comer() aunque no los use
public class Robot implements Trabajador {
    public void dormir() { /* No hace nada */ }
    public void comer() { /* No hace nada */ }
}

// ✅ BIEN: Interfaces segregadas
public interface Trabajable {
    void trabajar();
}

public interface Necesidades {
    void comer();
    void dormir();
}

public class Robot implements Trabajable {
    public void trabajar() { /* trabaja */ }
}

public class Humano implements Trabajable, Necesidades {
    public void trabajar() { /* trabaja */ }
    public void comer() { /* come */ }
    public void dormir() { /* duerme */ }
}
```

### D - Dependency Inversion Principle (DIP)
**"Depender de abstracciones, no de implementaciones concretas"**

**Ejemplo:**
```java
// ❌ MAL: Alto nivel depende de bajo nivel
public class ServicioUsuario {
    private MySQLDatabase db = new MySQLDatabase(); // Dependencia concreta
    
    public void guardar(Usuario usuario) {
        db.insert(usuario);
    }
}

// ✅ BIEN: Ambos dependen de abstracción
public interface Database {
    void insert(Usuario usuario);
}

public class MySQLDatabase implements Database {
    public void insert(Usuario usuario) { /* MySQL */ }
}

public class MongoDatabase implements Database {
    public void insert(Usuario usuario) { /* MongoDB */ }
}

public class ServicioUsuario {
    private Database db; // Depende de abstracción
    
    public ServicioUsuario(Database db) {
        this.db = db; // Se inyecta la dependencia
    }
    
    public void guardar(Usuario usuario) {
        db.insert(usuario); // Funciona con cualquier implementación
    }
}
```

---

## 5. Arquitectura en Capas y Ubicación en el Sistema

### ¿Dónde estoy ubicado en el diseño de software?

El diagrama de la diapositiva muestra una **arquitectura en capas** típica de aplicaciones empresariales:

```
┌─────────────────────────────────────────────────────────┐
│                    CLIENTE WEB                          │
│                  (Navegador del usuario)                │
└────────────────────┬────────────────────────────────────┘
                     │ HTTP/HTML
                     ▼
┌─────────────────────────────────────────────────────────┐
│          SERVIDOR DE LA APLICACIÓN                      │
│                                                          │
│  ┌────────────────────────────────────────────────┐    │
│  │  CAPA DE PRESENTACIÓN                          │    │
│  │  - Controles (botones, formularios)            │    │
│  │  - Vistas (páginas, pantallas)                 │    │
│  └────────────────────────────────────────────────┘    │
│                     ▼                                    │
│  ┌────────────────────────────────────────────────┐    │
│  │  CAPA DE LÓGICA DE NEGOCIO                     │    │
│  │  - Servicios                                    │    │
│  │  - Modelo de Dominio ← AQUÍ ESTÁN TUS ENTITIES │    │
│  │                        Y VALUE OBJECTS          │    │
│  └────────────────────────────────────────────────┘    │
│                     ▼                                    │
│  ┌────────────────────────────────────────────────┐    │
│  │  CAPA DE PERSISTENCIA                          │    │
│  │  - Base de Datos (acceso y mapeo)              │    │
│  └────────────────────────────────────────────────┘    │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│          SERVIDOR DE BASE DE DATOS                      │
│          (MySQL, PostgreSQL, MongoDB, etc.)             │
└─────────────────────────────────────────────────────────┘
```

### ¿Para qué es necesaria esta separación?

#### 1. **Capa de Presentación**
- **Responsabilidad:** Interactuar con el usuario
- **Contiene:** Vistas HTML, controladores web, validación de formularios
- **Principio aplicado:** SRP - solo se ocupa de la interfaz de usuario
- **Cambios típicos:** Diseño visual, nueva funcionalidad en pantalla

#### 2. **Capa de Lógica de Negocio (Modelo de Dominio)**
- **Responsabilidad:** Las reglas del negocio, la lógica core de tu aplicación
- **Contiene:** 
  - **Entities:** Persona, Cuenta, Pedido, Cliente
  - **Value Objects:** Dirección, Moneda, Email
  - **Servicios:** Procesos complejos que coordinan entidades
- **Principio aplicado:** SRP + DIP - no depende de detalles de presentación ni persistencia
- **Cambios típicos:** Nuevas reglas de negocio, cambios en procesos

**⭐ ESTA ES TU CAPA PRINCIPAL - Donde aplicas Entity, Value Object, HAR y SOLID**

#### 3. **Capa de Persistencia**
- **Responsabilidad:** Guardar y recuperar datos
- **Contiene:** Repositorios, DAOs, mapeo a tablas
- **Principio aplicado:** DIP - implementa interfaces definidas por la capa de negocio
- **Cambios típicos:** Cambio de base de datos, optimización de consultas

### ¿Por qué es crucial esta arquitectura?

#### **Separación de Responsabilidades**
Cada capa tiene una única razón para cambiar (SRP a nivel arquitectónico):
- Cambio en la UI → Solo tocas Presentación
- Cambio en regla de negocio → Solo tocas Modelo de Dominio
- Cambio de BD → Solo tocas Persistencia

#### **Bajo Acoplamiento**
Las capas se comunican a través de interfaces:
```java
// Capa de Negocio define la interfaz (abstracción)
public interface UsuarioRepository {
    void guardar(Usuario usuario);
    Usuario buscarPorId(String id);
}

// Capa de Persistencia implementa los detalles
public class UsuarioRepositoryMySQL implements UsuarioRepository {
    public void guardar(Usuario usuario) { /* SQL específico */ }
}

// Capa de Presentación usa la abstracción
public class UsuarioController {
    private UsuarioRepository repo; // DIP aplicado
    
    public void registrarUsuario(String nombre, String email) {
        Usuario nuevo = new Usuario(nombre, new Email(email)); // Value Object
        repo.guardar(nuevo);
    }
}
```

#### **Facilita el Testing**
Puedes probar cada capa independientemente:
```java
// Test de la lógica de negocio SIN base de datos
public class UsuarioServiceTest {
    @Test
    public void testCrearUsuario() {
        // Mock de la capa de persistencia
        UsuarioRepository repoMock = new UsuarioRepositoryMock();
        UsuarioService service = new UsuarioService(repoMock);
        
        Usuario usuario = service.crear("Juan", "juan@email.com");
        
        assertTrue(usuario.getEmail().esValido());
    }
}
```

#### **Permite Cambios sin Romper Todo**
- Cambiar de MySQL a MongoDB: Solo modificas la Capa de Persistencia
- Agregar una app móvil: Agregas nueva Capa de Presentación, reutilizas Lógica de Negocio
- Cambiar diseño web: Solo tocas la Capa de Presentación

### Violaciones comunes que el diagrama te ayuda a evitar

❌ **Presentación accede directo a Base de Datos**
```java
// MAL: Controller habla directo con BD
public class UsuarioController {
    public void registrar() {
        Connection conn = DriverManager.getConnection("jdbc:mysql://...");
        // Lógica de negocio mezclada con SQL
    }
}
```

❌ **Modelo de Dominio conoce detalles de Presentación**
```java
// MAL: Entity conoce HTTP
public class Usuario {
    public void guardar(HttpServletRequest request) {
        // ¡Una entidad NO debe saber de web!
    }
}
```

✅ **Flujo correcto respetando capas**
```
Usuario hace clic → Presentación → Servicio (Negocio) → Repository (Persistencia) → BD
                         ↓              ↓                    ↓
                    Valida form    Aplica reglas       Ejecuta SQL
```

---

## Conclusiones Clave

### Sobre Entities y Value Objects
- **Entity:** Tiene identidad (ID), es modificable, vive independientemente
- **Value Object:** Sin ID, inmutable, se define por su contenido, vive adjunto a una Entity
- En UML: Usar `<<value object>>` y composición (rombo relleno)
- En código: Entities con ID y setters, Value Objects con `final` y sin setters

### Sobre Diseño
1. **Preferir composición sobre herencia** para mantener bajo acoplamiento
2. **Aplicar polimorfismo** en lugar de condicionales para variaciones de comportamiento
3. **"No hables con extraños"** para evitar cadenas largas de dependencias

### Sobre SOLID
- **SRP:** Una clase, una responsabilidad
- **OCP:** Extender sin modificar (usa interfaces y polimorfismo)
- **LSP:** Los subtipos deben ser intercambiables
- **ISP:** Interfaces pequeñas y específicas
- **DIP:** Depende de abstracciones, no de detalles

### Sobre Arquitectura en Capas
- **Separa responsabilidades:** Presentación, Negocio, Persistencia
- **Tu foco principal:** Capa de Lógica de Negocio (Entities, Value Objects, Servicios)
- **Beneficios:** Bajo acoplamiento, fácil testing, cambios localizados
- **Aplica DIP:** Capas superiores definen interfaces, capas inferiores las implementan

**Todos estos principios trabajan juntos para crear sistemas mantenibles, extensibles y robustos.**