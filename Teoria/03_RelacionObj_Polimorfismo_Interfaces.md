# Clase 3: Relaciones Objetosas
## Identidad, Igualdad, Polimorfismo, Interfaces y Delegación

---

## 📚 Relaciones entre Objetos
*"Los objetos no viven solos, se relacionan para colaborar"*

### ¿Cuándo un objeto conoce a otro?

**Un objeto conoce a otro porque:**
- **Es su responsabilidad mantenerlo** en el sistema (relación "tiene un" o "conoce a")
  - *"La cuenta debe recordar quién es su titular"*
- **Necesita delegarle trabajo** (enviarle mensajes)
  - *"Para transferir dinero, necesito hablar con la cuenta destino"*

**Un objeto conoce a otro cuando:**
- Tiene una **referencia en variable de instancia** (relación duradera)
  - *"Mi auto siempre conoce a su dueño"*
- Le llega una **referencia como parámetro** (relación temporal)
  - *"Te paso este cliente para que lo atiendas"*
- Lo **crea** (relación temporal/duradera)
  - *"La fábrica conoce al producto que acaba de crear"*
- Lo **obtiene enviando mensajes** a otros que conoce (relación temporal)
  - *"Pregunto al banco cuál es mi cuenta y ahora la conozco"*

```java
public class CuentaBancaria {
    private Cliente titular;        // Relación duradera: "siempre conozco mi titular"
    private double saldo;
    
    // Relación temporal por parámetro: "conozco el destino solo durante la transferencia"
    public void transferir(double monto, CuentaBancaria destino) {
        this.debitar(monto);
        destino.acreditar(monto);  // "Le delego el trabajo de acreditar"
    }
    
    // Relación temporal por creación: "creo y conozco el movimiento"
    public void debitar(double monto) {
        MovimientoBancario mov = new MovimientoBancario("DEBITO", monto);
        this.registrarMovimiento(mov);
    }
}
```

---

## 🔄 this (Un objeto que habla solo)
*"Soy yo, hablando de mí mismo"*

### Características de `this`
- Es una **pseudo-variable** (no se le puede asignar valor)
- Toma valor automáticamente cuando un objeto ejecuta un método
- Hace referencia al **objeto que ejecuta el método** (receptor del mensaje)
- *"this siempre apunta a quien está ejecutando el código"*

### Usos de `this`
- **Descomponer métodos largos** (refinamiento top-down)
  - *"Divido mi trabajo en pasos más pequeños"*
- **Reutilizar comportamiento** repetido en varios métodos
  - *"No repito código, me envío mensajes a mí mismo"*
- **Aprovechar comportamiento heredado**
- **Pasar una referencia** para que otros puedan enviarnos mensajes
  - *"Te doy mi referencia para que me puedas llamar después"*
- En Java: puede obviarse (implícito), pero en OO1 preferimos no hacerlo
- **Desambiguar** referencias a variables de instancia

```java
public class CuentaBancaria {
    private double saldo;
    private Cliente titular;
    
    public void transferir(double monto, CuentaBancaria destino) {
        if (this.puedeDebitar(monto)) {        // "Me pregunto a mí mismo si puedo"
            this.debitar(monto);               // "Me ordeno debitar"
            destino.acreditar(monto);
            this.notificarTransferencia();     // "Me ordeno notificar"
        }
    }
    
    // Descomposición: métodos privados para organizar
    private boolean puedeDebitar(double monto) {
        return this.saldo >= monto;            // "Consulto mi propio saldo"
    }
    
    private void debitar(double monto) {
        this.saldo -= monto;                   // "Modifico mi propio saldo"
    }
    
    // Pasar referencia: "Dame mi referencia para que me puedas avisar"
    public void configurarNotificaciones(ServicioNotificaciones servicio) {
        servicio.suscribir(this);  // "Me suscribo pasando mi referencia"
    }
    
    // Desambiguar: cuando parámetro y variable tienen el mismo nombre
    public void setSaldo(double saldo) {
        this.saldo = saldo;  // "Mi saldo = el parámetro saldo"
    }
}
```

---

## 🆔 Identidad / el operador ==
*"¿Son el mismo objeto en memoria?"*

- Las **variables son punteros** a objetos
- Más de una variable pueden apuntar al **mismo objeto**
- `==` verifica si dos variables **apuntan al mismo objeto**
- `==` es un **operador que NO puede redefinirse**
- *"¿Es exactamente el mismo objeto en memoria?"*

```java
// IDENTIDAD: Mismo objeto
Persona juan1 = new Persona("12345678", "Juan Pérez");
Persona referencia = juan1;  // "referencia apunta al mismo Juan"

System.out.println(juan1 == referencia);  // TRUE - "¿Es el mismo objeto?"
```

---

## ⚖️ Igualdad / el método equals()
*"¿Son objetos distintos pero equivalentes para mi negocio?"*

- Dos objetos pueden ser **iguales según el dominio**
- La igualdad se define mediante el método `equals()`
- Se puede **redefinir** según las necesidades del dominio

```java
public class Persona {
    private String dni;
    private String nombre;
    
    // Igualdad: dos personas son iguales si tienen el mismo DNI
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Persona) {
            Persona otra = (Persona) obj;
            return this.dni.equals(otra.dni);  // "¿Tenemos el mismo DNI?"
        }
        return false;
    }
}

// Ejemplo práctico
Persona juan1 = new Persona("12345678", "Juan Pérez");
Persona juan2 = new Persona("12345678", "Juan Pérez");  // Nuevo objeto, mismo DNI

System.out.println(juan1 == juan2);      // FALSE - "¿Son el mismo objeto?" NO
System.out.println(juan1.equals(juan2)); // TRUE  - "¿Son iguales?" SÍ (mismo DNI)
```

---

## 🎨 Igualdad e identidad (ejemplo con Colores)

```java
public class Color {
    private int rojo, verde, azul;
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Color) {
            Color otro = (Color) obj;
            return this.rojo == otro.rojo && 
                   this.verde == otro.verde && 
                   this.azul == otro.azul;
        }
        return false;
    }
}

// Ejemplo
Color rojo1 = new Color(255, 0, 0);
Color rojo2 = new Color(255, 0, 0);
Color azul = new Color(0, 0, 255);

System.out.println(rojo1 == rojo2);      // FALSE - "Objetos distintos"
System.out.println(rojo1.equals(rojo2)); // TRUE  - "Mismo color RGB"
System.out.println(rojo1.equals(azul));  // FALSE - "Colores diferentes"
```

---

## 🔧 Relaciones entre objetos y chequeo de tipos
*"El compilador es tu amigo: te avisa de errores antes de ejecutar"*

- Java es un lenguaje **estáticamente, fuertemente tipado**
- Debemos **indicar el tipo** de todas las variables (relaciones entre objetos)
- El compilador **chequea la correctitud** de nuestro programa respecto a tipos
- Se asegura de que no enviamos mensajes a objetos que no los entienden
- Cuando declaramos el tipo de una variable, el compilador controla que solo "enviemos a esa variable" mensajes acordes a ese tipo
- Cuando asignamos un objeto a una variable, el compilador controla que su clase sea "compatible" con el tipo de la variable

---

## 📋 Tipos en lenguajes OO (simplificado)
*"Un tipo es un contrato: promete qué mensajes puede recibir"*

### ¿Qué es un Tipo?
- **Tipo = Conjunto de firmas** de operaciones/métodos (nombre, orden y tipos de argumentos)
- Algunos lenguajes diferencian entre **tipos primitivos** y **tipos de referencias** (objetos)
- Cada **clase en Java define explícitamente un tipo**
- Puedo utilizar **clases para dar tipo a las variables**
- Asignar un objeto a una variable **no afecta al objeto** (no cambia su clase)
- La **clase de un objeto** se establece cuando se crea, y **no cambia más**
- Pero... **las clases no son la única forma de definir tipos**

---

## 🎯 El ejemplo conductor
*"¿Nos interesa decir de qué clase es 'destino' o qué mensajes entiende?"*

Un monitor de eventos que escribe en algún lugar (destino):
- Objetos de varias clases que el monitor podría usar como destino
- ¿Cómo "tipamos" la relación entre el monitor y el destino?

```java
public class MonitorEventos {
    private ???? destino;  // ¿Qué tipo usar aquí?
    
    public void registrarEvento(String evento) {
        destino.escribir(evento);  // Solo necesito que sepa escribir
    }
}
```

---

## 🔌 Interfaces
*"Una interfaz dice 'QUÉ' hacer, la clase dice 'CÓMO' hacerlo"*

- Una **clase define un tipo Y también implementa** los métodos correspondientes
- Una variable tipada con una clase solo "acepta" instancias de esa clase
- Una **interfaz nos permite declarar tipos sin tener que ofrecer implementación** (desacopla tipo e implementación)
- Puedo utilizar **Interfaces como tipos de variables**
- Las clases deben **declarar explícitamente** que interfaces implementan
- Una clase puede **implementar varias interfaces**
- El compilador chequea que la clase implemente las interfaces que declara (salvo que sea una clase abstracta)

```java
// Solución al ejemplo conductor
interface Destino {
    void escribir(String mensaje);  // "Prometo que sabes escribir"
}

// Diferentes implementaciones del "cómo"
class ArchivoLog implements Destino {
    private String rutaArchivo;
    
    @Override
    public void escribir(String mensaje) {
        // "YO escribo guardando en un archivo"
        try (FileWriter writer = new FileWriter(rutaArchivo, true)) {
            writer.write(mensaje + "\n");
        }
    }
}

class ConsoleLog implements Destino {
    @Override
    public void escribir(String mensaje) {
        // "YO escribo mostrando en consola"
        System.out.println("[LOG] " + mensaje);
    }
}

// El monitor ahora puede usar cualquier destino
public class MonitorEventos {
    private Destino destino;  // "Solo sé que puede escribir"
    
    public MonitorEventos(Destino destino) {
        this.destino = destino;  // "Acepto cualquiera que sepa escribir"
    }
    
    public void registrarEvento(String evento) {
        String mensaje = LocalDateTime.now() + ": " + evento;
        destino.escribir(mensaje);  // "No me importa cómo, solo que escriba"
    }
}
```

---

## 👥 Un objeto que conoce a muchos...
*"No conozco a muchos, conozco a UNO que contiene muchos"*

- Las relaciones de **un objeto a muchos** se implementan con **colecciones**
- Decimos que un objeto conoce a muchos, pero en realidad **conoce a una colección**, que tiene referencias a esos muchos
- Para modificar y explorar la relación, **envío mensajes a la colección**

```java
public class Empresa {
    private String nombre;
    private List<Empleado> empleados;      // "Conozco UNA lista, no muchos empleados"
    
    public Empresa(String nombre) {
        this.nombre = nombre;
        this.empleados = new ArrayList<>();  // "Creo mi colección vacía"
    }
    
    // Agregar: "Le digo a mi lista que agregue"
    public void contratar(Empleado empleado) {
        empleados.add(empleado);            // "Lista, agregá este empleado"
        empleado.setEmpresa(this);          // "Empleado, tu empresa soy yo"
    }
    
    // Remover: "Le digo a mi lista que remueva"
    public void despedir(Empleado empleado) {
        empleados.remove(empleado);         // "Lista, sacá este empleado"
        empleado.setEmpresa(null);          // "Empleado, ya no tenés empresa"
    }
    
    // Consultar: "Le pregunto a mi lista"
    public int cantidadEmpleados() {
        return empleados.size();            // "Lista, ¿cuántos tenés?"
    }
    
    // Delegar a cada uno: "Lista, aplicá esto a cada empleado"
    public double calcularTotalSueldos() {
        return empleados.stream()
                .mapToDouble(Empleado::getSueldo)    // "Cada empleado, dame tu sueldo"
                .sum();                              // "Sumo todos los sueldos"
    }
}
```

---

## 🤔 ¿Envidia o delegación?
*"¿Cómo implementarían getPrecio() en la clase oferta?"*

### Envidia (❌ Mal diseño)
*"Soy envidioso: quiero hacer el trabajo de otros"*

- Una clase **Oferta envidiosa y egoísta** que quiere hacer todo
- **Responsabilidades poco repartidas** (Producto es solo datos)
- Clases **más acopladas** y poco cohesivas

```java
// ❌ CLASE ENVIDIOSA - MAL DISEÑO
public class OfertaEnvidiosa {
    private Producto producto;
    private double porcentajeDescuento;
    
    // ENVIDIA ❌: "Yo hago TODO, no confío en el producto"
    public double getPrecio() {
        // "Le saco los datos al producto y calculo yo"
        double precioBase = producto.getPrecioBase();
        double descuento = precioBase * (porcentajeDescuento / 100.0);
        return precioBase - descuento;
        
        // Problemas:
        // 1. Si cambia el cálculo de precio, hay que modificar AQUÍ
        // 2. Esta clase conoce detalles internos del Producto
        // 3. El Producto es solo un "contenedor de datos"
    }
}
```

### Delegación (✅ Buen diseño)
*"Cada uno hace lo que sabe hacer mejor"*

- El **cálculo del precio** de un producto está **con los datos requeridos**
- Oferta **"delega"** y se despreocupa de cómo se hace el cálculo
- Clases **más desacopladas** y más cohesivas

```java
public class Producto {
    private String nombre;
    private double precioBase;
    
    // DELEGACIÓN ✅: "Yo sé calcular mi precio con descuento"
    public double getPrecioConDescuento(double porcentajeDescuento) {
        double descuento = precioBase * (porcentajeDescuento / 100.0);
        return precioBase - descuento;
    }
}

// ✅ CLASE QUE DELEGA - BUEN DISEÑO  
public class OfertaDelegadora {
    private Producto producto;
    private double porcentajeDescuento;
    
    // DELEGACIÓN ✅: "Cada uno hace lo suyo"
    public double getPrecio() {
        return producto.getPrecioConDescuento(porcentajeDescuento);
    }
    
    // Ventajas:
    // 1. Si cambia cálculo de precio, se modifica solo en Producto
    // 2. Esta clase NO conoce detalles internos del Producto
    // 3. Cada clase tiene responsabilidades claras
}
```

---

## 🔍 Method Lookup (recordamos)
*"Cada objeto busca en su 'libreta' cómo responder al mensaje"*

- Cuando un objeto recibe un mensaje, se busca en su clase un **método cuya firma se corresponda** con el mensaje
- En un lenguaje dinámico, podría no encontrarlo (error en tiempo de ejecución)
- En un lenguaje con **tipado estático** sabemos que lo entenderá (aunque no sabemos lo que hará)

---

## 🎭 Polimorfismo
*"Mismo mensaje, diferentes interpretaciones"*

### Definición
- **Objetos de distintas clases** son polimórficos con respecto a un mensaje, si **todos lo entienden**, aun cuando cada uno lo implemente de un modo diferente
- *"Todos hablan el mismo idioma, pero cada uno a su manera"*

### Características del Polimorfismo
- Un **mismo mensaje** se puede enviar a objetos de distinta clase
- Objetos de distinta clase **"podrían" ejecutar métodos diferentes** en respuesta a un mismo mensaje
- Cuando dos clases Java **implementan una interfaz**, se vuelven **polimórficas respecto a los métodos** de la interfaz
- *"No me importa qué eres, me importa qué sabes hacer"*

---

## 🎨 Figuras polimórficas...
*"¿Qué hago con cada figura? Pueden ser de distintas clases. ¿Necesito saber de qué clase es?"*

```java
interface Figura {
    double area();          // "Prometo que sé calcular mi área"
    void dibujar();         // "Prometo que me sé dibujar"
}

class Circulo implements Figura {
    private double radio;
    
    @Override
    public double area() {
        return Math.PI * radio * radio;     // "YO calculo área como πr²"
    }
    
    @Override
    public void dibujar() {
        System.out.println("Dibujando un círculo de radio " + radio);
    }
}

class Rectangulo implements Figura {
    private double ancho, alto;
    
    @Override
    public double area() {
        return ancho * alto;                // "YO calculo área como base×altura"
    }
    
    @Override
    public void dibujar() {
        System.out.println("Dibujando un rectángulo " + ancho + "x" + alto);
    }
}

// POLIMORFISMO EN ACCIÓN
public class CalculadoraFiguras {
    // "Me da igual qué tipo de figura sea, todas saben calcular su área"
    public double calcularAreaTotal(List<Figura> figuras) {
        double total = 0;
        for (Figura figura : figuras) {
            total += figura.area();     // POLIMORFISMO: cada una calcula diferente
        }
        return total;
    }
    
    // "Me da igual qué tipo de figura sea, todas se saben dibujar"
    public void dibujarTodasLasFiguras(List<Figura> figuras) {
        for (Figura figura : figuras) {
            figura.dibujar();           // POLIMORFISMO: cada una se dibuja diferente
        }
    }
}
```

### Comparemos con... (código SIN polimorfismo ❌)

```java
// SIN POLIMORFISMO (código rígido y difícil de mantener) ❌
class CalculadoraSinPolimorfismo {
    public double calcularAreaTotal(List<Object> figuras) {
        double total = 0;
        for (Object figura : figuras) {
            if (figura instanceof Circulo) {
                Circulo c = (Circulo) figura;
                total += Math.PI * c.getRadio() * c.getRadio();
            } else if (figura instanceof Rectangulo) {
                Rectangulo r = (Rectangulo) figura;
                total += r.getAncho() * r.getAlto();
            }
            // ¿Nueva figura? ¡Tengo que modificar AQUÍ! ❌
        }
        return total;
    }
}
```

---

## ✨ Polimorfismo bien aplicado

### Beneficios
- Permite **repartir mejor las responsabilidades** (delegar)
- **Desacopla objetos** y mejora la cohesión (cada cual hace lo suyo)
- **Concentra cambios** (reduce el impacto de los cambios)
- Permite **extender sin modificar** (agregando nuevos objetos)
- Lleva a **código más genérico** y objetos reusables
- Nos permite **programar por protocolo**, no por implementación
- *"Escribo código una vez y funciona con muchos tipos diferentes"*

### Ejemplo práctico de uso

```java
public class EjemploPolimorfismo {
    public static void main(String[] args) {
        // FIGURAS POLIMÓRFICAS
        List<Figura> figuras = Arrays.asList(
            new Circulo(5),                    // "Soy un círculo"
            new Rectangulo(4, 6),              // "Soy un rectángulo"
            new Circulo(2)                     // "Soy otro círculo"
        );
        
        CalculadoraFiguras calc = new CalculadoraFiguras();
        
        // POLIMORFISMO: mismo método, diferentes cálculos
        System.out.println("Área total: " + calc.calcularAreaTotal(figuras));
        
        // POLIMORFISMO: mismo método, diferentes dibujos
        calc.dibujarTodasLasFiguras(figuras);
        
        // Output:
        // Área total: 102.11...
        // Dibujando un círculo de radio 5.0
        // Dibujando un rectángulo 4.0x6.0  
        // Dibujando un círculo de radio 2.0
    }
}
```

---

## 🎯 Conceptos Clave para Recordar

### 🧠 Frases para memorizar conceptos

1. **Relaciones entre objetos**
   - *"Los objetos colaboran, no viven aislados"*
   - *"Conozco porque necesito o porque es mi responsabilidad"*

2. **this** 
   - *"Soy yo hablando de mí mismo"*
   - *"Me envío mensajes para organizarme mejor"*

3. **Identidad vs Igualdad**
   - *"Identidad pregunta: ¿Es el mismo? Igualdad pregunta: ¿Son equivalentes?"*
   - *"== para identidad, equals() para igualdad de negocio"*

4. **Interfaces**
   - *"Interfaz dice QUÉ, clase dice CÓMO"*
   - *"Un contrato que promete qué mensajes entiendes"*

5. **Colecciones (Uno a muchos)**
   - *"No conozco a muchos, conozco a UNO que contiene muchos"*
   - *"Le hablo a la colección, ella maneja los elementos"*

6. **Delegación**
   - *"Cada uno hace lo que sabe hacer mejor"*
   - *"No seas envidioso: pide ayuda a quien corresponde"*

7. **Polimorfismo**
   - *"Mismo mensaje, diferentes interpretaciones"*
   - *"No me importa qué eres, me importa qué sabes hacer"*
   - *"Escribo una vez, funciona con muchos tipos"*

### 💡 Consejos prácticos

- **Usa `this` siempre** en OO1, aunque sea opcional
- **Implementa `equals()`** pensando en tu dominio de negocio
- **Prefiere interfaces** a clases concretas para tipear variables
- **Delega responsabilidades** en lugar de ser envidioso
- **Busca polimorfismo** cuando veas código con muchos `instanceof` o `if-else`
- **Las colecciones** son tu herramienta para relaciones uno-a-muchos

### 🔍 Señales de mal diseño a evitar

- **Envidia**: Una clase que accede a muchos datos de otra
- **Código rígido**: Muchos `instanceof` en lugar de polimorfismo  
- **Responsabilidades confusas**: No está claro quién debe hacer qué
- **Acoplamiento alto**: Cambiar una clase obliga a cambiar muchas otras