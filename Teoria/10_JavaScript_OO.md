# JavaScript — Orientación a Objetos basada en Prototipos

> JavaScript es un lenguaje OO pero con un modelo distinto al de Java: no está basado en clases sino en **prototipos**. Estudiarlo nos ayuda a entender qué aspectos de OO son del paradigma y cuáles son propios de Java.

---

## Diferencias fundamentales con Java

| Aspecto | Java | JavaScript |
|---------|------|------------|
| Tipado | Estático y fuerte | Dinámico y débil |
| Clases | Obligatorias, estructuran todo | Opcionales (syntax sugar en ES6) |
| Herencia | Jerárquica, basada en clases | Basada en prototipos |
| Chequeo de tipos | En compilación | En ejecución |
| Variables | Deben declarar su tipo | Pueden tener cualquier tipo |

---

## Todo es un objeto (incluso las funciones)

En JavaScript, prácticamente todo es un objeto: números, strings, arrays, funciones. No hay una separación entre tipos primitivos y objetos como en Java.

```javascript
// Una función es un objeto
function saludar(nombre) {
    return "Hola " + nombre;
}

saludar.descripcion = "Esta función saluda";  // se le puede agregar propiedades
console.log(saludar.descripcion);             // "Esta función saluda"
```

---

## Tipado dinámico

En JavaScript no se declara el tipo de las variables. Una variable puede cambiar de tipo en tiempo de ejecución. El chequeo de compatibilidad ocurre cuando se ejecuta el código, no antes.

```javascript
let x = 42;       // x es número
x = "hola";       // ahora x es string — válido en JS
x = true;         // ahora x es booleano — válido en JS

// En Java esto no compila:
// int x = 42;
// x = "hola";  // ❌ error de compilación
```

**Implicancia:** No hay garantía en tiempo de compilación de que un objeto entienda un mensaje. Si enviás un mensaje que el objeto no entiende, el error ocurre en **tiempo de ejecución**.

---

## Objetos literales — sin clase previa

En JavaScript podés crear un objeto directamente, sin definir una clase:

```javascript
// Crear un objeto sin definir una clase
const persona = {
    nombre: "Ana",
    edad: 30,
    saludar: function() {
        return "Hola, soy " + this.nombre;
    }
};

console.log(persona.saludar());   // "Hola, soy Ana"
console.log(persona.nombre);      // "Ana"

// Agregar propiedades en tiempo de ejecución
persona.email = "ana@email.com";  // válido, el objeto crece dinámicamente
```

---

## Prototipos — la base de la herencia en JavaScript

En lugar de clases, JavaScript usa **prototipos**. Todo objeto tiene un prototipo (otro objeto) del cual puede "heredar" propiedades y métodos. Cuando se busca una propiedad en un objeto y no se encuentra, la búsqueda continúa en su prototipo.

```javascript
// Objeto base (el "prototipo")
const animal = {
    tipo: "animal",
    respirar: function() {
        return this.tipo + " respira";
    }
};

// Crear un objeto que usa animal como prototipo
const perro = Object.create(animal);
perro.nombre = "Rex";
perro.ladrar = function() {
    return this.nombre + " dice guau";
};

// perro no tiene respirar(), pero su prototipo sí
console.log(perro.respirar());    // "animal respira" — encontrado en el prototipo
console.log(perro.ladrar());      // "Rex dice guau" — definido en perro
console.log(perro.tipo);          // "animal" — encontrado en el prototipo
```

### Cadena de prototipos (prototype chain)

La búsqueda de propiedades sube por la cadena:

```
perro ──→ animal ──→ Object.prototype ──→ null
```

Esto es análogo al **method lookup con herencia en Java**, pero en lugar de subir por clases, sube por prototipos.

---

## Funciones constructoras (estilo clásico, ES5)

Antes de ES6, se usaban funciones como constructoras para crear objetos similares:

```javascript
// Función constructora (por convención, nombre con mayúscula)
function Persona(nombre, edad) {
    this.nombre = nombre;    // this apunta al nuevo objeto
    this.edad = edad;
}

// Métodos se agregan al prototipo (compartidos por todas las instancias)
Persona.prototype.saludar = function() {
    return "Hola, soy " + this.nombre;
};

Persona.prototype.cumplirAnios = function() {
    this.edad++;
};

// Crear instancias con new
const ana = new Persona("Ana", 30);
const bob = new Persona("Bob", 25);

console.log(ana.saludar());   // "Hola, soy Ana"
console.log(bob.saludar());   // "Hola, soy Bob"
```

### Qué hace `new` en JavaScript

1. Crea un nuevo objeto vacío `{}`
2. Establece el prototipo del objeto a `Persona.prototype`
3. Ejecuta la función constructora con `this` apuntando al nuevo objeto
4. Retorna el nuevo objeto

---

## Clases en ES6 — syntax sugar

ES6 introdujo la sintaxis `class`, que es más familiar para quienes vienen de Java. **No cambia el modelo de prototipos**, solo es una forma más clara de escribir lo mismo:

```javascript
class Persona {
    constructor(nombre, edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    saludar() {
        return "Hola, soy " + this.nombre;
    }

    cumplirAnios() {
        this.edad++;
    }
}

// Herencia con extends
class Empleado extends Persona {
    constructor(nombre, edad, empresa) {
        super(nombre, edad);         // llama al constructor de Persona
        this.empresa = empresa;
    }

    presentarse() {
        return this.saludar() + ", trabajo en " + this.empresa;
    }
}

const emp = new Empleado("Carlos", 28, "UNLP");
console.log(emp.presentarse());   // "Hola, soy Carlos, trabajo en UNLP"
console.log(emp.saludar());       // "Hola, soy Carlos" — heredado
```

> ⚠️ Aunque parece idéntico a Java, por debajo sigue siendo prototipos. La diferencia es conceptual y de semántica, no solo de sintaxis.

---

## Polimorfismo en JavaScript

Al ser dinámicamente tipado, el polimorfismo funciona sin interfaces ni clases abstractas. Si dos objetos responden al mismo mensaje, son polimórficos — sin necesidad de declararlo:

```javascript
const circulo = {
    radio: 5,
    area: function() { return 3.14 * this.radio * this.radio; }
};

const rectangulo = {
    base: 4,
    altura: 6,
    area: function() { return this.base * this.altura; }
};

// Polimorfismo "de hecho": ambos responden a area()
// No hay una interfaz Figura, no hace falta declararla
function calcularAreaTotal(figuras) {
    let total = 0;
    for (const figura of figuras) {
        total += figura.area();   // funciona para cualquier objeto que tenga area()
    }
    return total;
}

console.log(calcularAreaTotal([circulo, rectangulo]));  // 102.5
```

**Comparación con Java:** En Java necesitaríamos una interfaz `Figura` declarada explícitamente. En JavaScript el polimorfismo surge automáticamente si los objetos responden al mismo mensaje (duck typing: *"si camina como un pato y grazna como un pato, es un pato"*).

---

## `this` en JavaScript — diferencia importante con Java

En Java, `this` siempre apunta al objeto receptor del mensaje. En JavaScript, `this` depende de **cómo se llama la función**, lo que puede generar confusión:

```javascript
const persona = {
    nombre: "Ana",
    saludar: function() {
        return "Hola, soy " + this.nombre;
    }
};

persona.saludar();          // "Hola, soy Ana" — this es persona ✅

const fn = persona.saludar;
fn();                       // "Hola, soy undefined" — this es global/undefined ⚠️
```

Las **arrow functions** (`=>`) capturan el `this` del contexto en que fueron definidas:

```javascript
class Temporizador {
    constructor() {
        this.segundos = 0;
    }

    iniciar() {
        // Arrow function: this captura el contexto de iniciar()
        setInterval(() => {
            this.segundos++;   // this es el objeto Temporizador ✅
        }, 1000);
    }
}
```

---

## Resumen: qué aporta estudiar JavaScript OO

| Concepto OO | En Java | En JavaScript |
|-------------|---------|---------------|
| Objetos | Instancias de clases | Literales, prototipos, instancias de clases ES6 |
| Polimorfismo | Requiere interfaz/herencia declarada | Automático si responde al mensaje (duck typing) |
| Herencia | Por clases, declarada | Por prototipos, dinámica |
| Tipado | El compilador verifica | Se verifica al ejecutar |
| Clases | Obligatorias | Opcionales (son syntax sugar) |

> **Reflexión clave:** JavaScript demuestra que el paradigma OO (encapsulamiento, polimorfismo, herencia) no depende de las clases. Las clases son una herramienta para implementar OO, no la esencia del paradigma.
