# Smalltalk — Orientación a Objetos pura

> Smalltalk es el lenguaje OO "puro" por excelencia: **absolutamente todo es un objeto**, incluso las clases, los números y las estructuras de control. Estudiarlo muestra el paradigma OO en su forma más esencial, sin concesiones a la programación estructural.

---

## Filosofía central: todo es un objeto, todo es un mensaje

En Java conviven objetos con tipos primitivos (`int`, `boolean`) y estructuras de control (`if`, `for`, `while`). En Smalltalk, no existe nada fuera del modelo de objetos:

| Cosa | En Java | En Smalltalk |
|------|---------|--------------|
| Número entero | tipo primitivo `int` (no es objeto) | objeto, instancia de `Integer` |
| `true` / `false` | tipos primitivos | objetos, instancias de `True` y `False` |
| Un `if` | keyword del lenguaje | mensaje enviado a un objeto booleano |
| Un `while` | keyword del lenguaje | mensaje enviado a un bloque |
| Una clase | describe objetos | es en sí misma un objeto |

---

## Sintaxis básica

### Envío de mensajes

La sintaxis básica es siempre: `receptor mensaje` o `receptor mensaje: argumento`

```smalltalk
"Comentarios van entre comillas dobles"

"Mensaje unario (sin argumentos)"
5 factorial           "retorna 120"
'hola' size           "retorna 4 — el tamaño del string"
miColeccion isEmpty   "retorna true si la colección está vacía"

"Mensaje binario (operadores)"
3 + 4                 "retorna 7 — ¡+ es un mensaje enviado al objeto 3!"
5 > 3                 "retorna true"
'ho' , 'la'           "retorna 'hola' — , es concatenación"

"Mensaje keyword (con argumentos nombrados)"
miArray at: 2                    "obtener elemento en posición 2"
miArray at: 2 put: 'nuevo'       "asignar en posición 2"
coleccion add: unObjeto          "agregar un objeto"
```

### Variables y asignación

```smalltalk
"Declaración y asignación"
| nombre edad |           "declaración de variables locales"
nombre := 'Ana'.          "asignación — se usa := no ="
edad := 30.
```

---

## Todo lo que en Java son keywords, en Smalltalk son mensajes

### El `if` es un mensaje

```java
// Java
if (saldo >= monto) {
    saldo = saldo - monto;
} else {
    System.out.println("Saldo insuficiente");
}
```

```smalltalk
"Smalltalk — ifTrue:ifFalse: es un mensaje enviado a un booleano"
(saldo >= monto)
    ifTrue: [ saldo := saldo - monto ]
    ifFalse: [ Transcript show: 'Saldo insuficiente' ].
```

`(saldo >= monto)` retorna un objeto `True` o `False`. A ese objeto se le envía el mensaje `ifTrue:ifFalse:` con dos bloques como argumento. El objeto `True` ejecuta el primer bloque; `False` ejecuta el segundo.

### El `while` es un mensaje

```java
// Java
int i = 0;
while (i < 10) {
    System.out.println(i);
    i++;
}
```

```smalltalk
"Smalltalk — whileTrue: es un mensaje enviado a un bloque"
| i |
i := 0.
[ i < 10 ] whileTrue: [
    Transcript show: i printString.
    i := i + 1
].
```

`[ i < 10 ]` es un bloque (un objeto). El mensaje `whileTrue:` evalúa el bloque receptor; si retorna `true`, ejecuta el bloque argumento y repite.

---

## Bloques — objetos que representan código

Un bloque `[ ... ]` es un objeto que encapsula código pero no lo ejecuta inmediatamente. Se puede:
- Guardar en una variable
- Pasar como parámetro
- Ejecutar cuando se desee (enviándole el mensaje `value`)

```smalltalk
"Crear un bloque"
| miBloque |
miBloque := [ Transcript show: 'Ejecutando!' ].

"Ejecutar el bloque"
miBloque value.   "imprime 'Ejecutando!'"

"Bloque con parámetro"
| sumar |
sumar := [:a :b | a + b].
sumar value: 3 value: 4.   "retorna 7"
```

Los bloques son la base de iteración, callbacks y estructuras de control en Smalltalk.

---

## Clases y objetos

### Definir una clase

```smalltalk
Object subclass: #CuentaBancaria
    instanceVariableNames: 'saldo titular'
    classVariableNames: ''
    poolDictionaries: ''
    category: 'Finanzas'
```

### Definir métodos

```smalltalk
"Método para depositar"
CuentaBancaria >> depositar: monto
    saldo := saldo + monto

"Método para extraer"
CuentaBancaria >> extraer: monto
    (saldo >= monto)
        ifTrue: [ saldo := saldo - monto ]
        ifFalse: [ self error: 'Saldo insuficiente' ]

"Método para consultar saldo"
CuentaBancaria >> saldo
    ^ saldo    "^ es return en Smalltalk"
```

### Crear objetos

```smalltalk
| cuenta |
cuenta := CuentaBancaria new.   "new es un mensaje enviado a la clase"
cuenta depositar: 1000.
cuenta extraer: 200.
Transcript show: cuenta saldo printString.   "imprime 800"
```

---

## Herencia y polimorfismo

```smalltalk
"CuentaCorriente hereda de CuentaBancaria"
CuentaBancaria subclass: #CuentaCorriente
    instanceVariableNames: 'descubierto'
    ...

"Redefinir extraer:"
CuentaCorriente >> extraer: monto
    (saldo + descubierto >= monto)
        ifTrue: [ saldo := saldo - monto ]
        ifFalse: [ self error: 'Límite excedido' ]
```

El polimorfismo funciona igual que en Java: si `CajaDeAhorro` y `CuentaCorriente` ambas entienden el mensaje `extraer:`, son polimórficas respecto a ese mensaje.

---

## `self` y `super`

En Smalltalk, `self` es el equivalente de `this` en Java, y `super` funciona igual:

```smalltalk
"self — el objeto receptor del mensaje"
CuentaBancaria >> depositar: monto
    saldo := saldo + monto.
    self notificar.   "envía el mensaje notificar al objeto receptor"

"super — busca en la superclase"
CuentaCorriente >> depositar: monto
    super depositar: monto.   "ejecuta el depositar de CuentaBancaria"
    self verificarLimite.     "agrega comportamiento extra"
```

---

## Colecciones

Smalltalk tiene un framework de colecciones muy completo. Las operaciones son mensajes:

```smalltalk
| coleccion |
coleccion := OrderedCollection new.   "equivalente a ArrayList"
coleccion add: 'primero'.
coleccion add: 'segundo'.
coleccion size.                       "retorna 2"

"Iteración con do:"
coleccion do: [:elemento |
    Transcript show: elemento
].

"Selección (equivalente a filter)"
| pares |
pares := #(1 2 3 4 5 6) select: [:n | n \\ 2 = 0].
"pares = (2 4 6)"

"Transformación (equivalente a map)"
| cuadrados |
cuadrados := #(1 2 3 4) collect: [:n | n * n].
"cuadrados = (1 4 9 16)"

"Reducción (equivalente a reduce/sum)"
| suma |
suma := #(1 2 3 4 5) inject: 0 into: [:acc :n | acc + n].
"suma = 15"
```

---

## Las clases son objetos

En Smalltalk, las clases son objetos como cualquier otro. Se les pueden enviar mensajes:

```smalltalk
CuentaBancaria name.          "retorna 'CuentaBancaria'"
CuentaBancaria superclass.    "retorna Object (o la superclase)"
CuentaBancaria new.           "crea una instancia — new es un mensaje"
```

Esto tiene implicancias profundas: los métodos de clase (equivalentes a `static` en Java) son simplemente métodos definidos en el objeto-clase.

---

## Comparación: Java vs JavaScript vs Smalltalk

| Concepto | Java | JavaScript | Smalltalk |
|----------|------|------------|-----------|
| Base del paradigma | Clases | Prototipos | Mensajes |
| Tipado | Estático | Dinámico | Dinámico |
| Control de flujo | Keywords (`if`, `for`) | Keywords | Mensajes a objetos |
| `true` / `false` | Primitivos | Primitivos | Objetos |
| Números | Primitivos + wrappers | Objetos | Objetos |
| Las clases son... | Descriptores de tipos | Funciones (ES5) / syntax sugar (ES6) | Objetos |
| Chequeo de tipos | Compilación | Ejecución | Ejecución |

---

## ¿Qué aporta Smalltalk al aprendizaje de OO?

1. **Pureza del modelo:** Cuando todo es objeto y todo es mensaje, no hay "atajos" estructurales. Se program verdaderamente en objetos.

2. **El paradigma es independiente del lenguaje:** Los mismos conceptos (encapsulamiento, polimorfismo, herencia, delegación) existen en Java, JavaScript y Smalltalk, aunque con sintaxis muy diferente.

3. **Los mensajes son la unidad fundamental:** En Smalltalk queda claro que lo esencial de OO es el **envío de mensajes**, no las clases ni la sintaxis.

4. **Duck typing llevado al extremo:** No importa la clase de un objeto, importa si entiende el mensaje. El polimorfismo no requiere declaración explícita.

> **Reflexión:** Si podés programar en OO sin clases (JavaScript), sin tipos declarados (Smalltalk), sin keywords de control (Smalltalk), entonces ninguna de esas cosas es la esencia del paradigma. La esencia es: **objetos que se envían mensajes, encapsulan estado y colaboran para resolver problemas.**
