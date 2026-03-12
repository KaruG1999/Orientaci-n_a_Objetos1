# Resumen de Teoría — OO1 Redictado 2026

Índice compacto de todos los temas del cuatrimestre. Para cada tema se indica el archivo de referencia, los conceptos clave y las preguntas típicas que aparecen en los ejercicios y parciales.

---

## Mapa del temario

| # | Tema | Archivo |
|---|------|---------|
| 01 | Fundamentos OO: objetos, mensajes, encapsulamiento | `01_IntroOO1.md` |
| 02 | Modelo de Dominio: clases conceptuales, UML, asociaciones | `02_Intro_AnalisisyDiseñoOO.md` |
| 03 | Relaciones entre objetos, polimorfismo, interfaces | `03_RelacionObj_Polimorfismo_Interfaces.md` |
| 03+ | Colecciones: List, Set, Map, Queue, Streams | `03_Extra_Colecciones.md` |
| 04 | Herencia, method lookup, Template Method | `04_Herencia.md` |
| 05 | HAR: asignación de responsabilidades, contratos | `05_Intro_Analisis_y_Diseño.md` |
| 06 | Testing con JUnit 5 | `06_Testing.md` |
| 07 | SOLID, Entity vs Value Object | `07_AnalisisDiseño_SOLID.md` |
| 09 | UML: diagramas de clases y secuencia | `09_UML_Diagramas.md` |
| 10 | OO en JavaScript (tipado dinámico, prototipos) | `10_JavaScript_OO.md` |
| 11 | OO en Smalltalk (todo es objeto, todo es mensaje) | `11_Smalltalk_OO.md` |

---

## 01 — Fundamentos OO

**Conceptos clave:**
- Un **objeto** encapsula datos (atributos) y comportamiento (métodos)
- La comunicación entre objetos es mediante **mensajes** (llamadas a métodos)
- **Encapsulamiento:** los atributos son `private`, el acceso es solo por getters/setters o por los propios métodos del objeto
- **Binding dinámico:** el método que se ejecuta se decide en runtime según el tipo real del objeto, no el tipo de la variable
- `==` compara referencias (¿son el mismo objeto?); `equals()` compara contenido

**Preguntas típicas:**
- ¿Por qué los atributos deben ser privados?
- ¿Qué diferencia hay entre `==` y `equals()`?
- ¿Por qué un objeto puede pedirse cosas a sí mismo con `this`?

---

## 02 — Modelo de Dominio

**Conceptos clave:**
- El **modelo de dominio** representa conceptos del mundo real, NO implementación
- Se construye identificando **clases conceptuales** (sustantivos), **atributos** simples y **asociaciones** entre clases
- Multiplicidades: `1`, `0..1`, `*` (o `0..*`), `1..*`
- **Navegabilidad:** la flecha indica quién conoce a quién (`A → B` significa A tiene referencia a B)
- Los **atributos** son datos simples (String, int, LocalDate); si "necesita secciones o tiene operaciones propias", es una clase

**Diferencia crítica:**
```
Modelo de dominio (análisis)   Diagrama de diseño
→ SIN métodos                  → CON métodos y firmas completas
→ SIN tipos Java               → CON tipos Java y visibilidades
→ SIN herencia                 → CON herencia e interfaces
```

**Preguntas típicas:**
- Dado un enunciado en texto, ¿cuáles son las clases conceptuales?
- ¿Qué multiplicidad tiene esta relación?
- ¿Esto es un atributo o una clase?

---

## 03 — Relaciones, Polimorfismo, Interfaces

**Conceptos clave:**
- **Delegación:** un objeto le pide a otro que haga algo, en lugar de hacerlo él mismo
- **`this`:** dentro de un método, referencia al objeto receptor del mensaje
- **Polimorfismo:** distintos objetos responden al mismo mensaje con comportamiento diferente
- **Interfaz:** contrato de lo que puede hacer un objeto; sin implementación
- **Composición vs herencia:** "tiene un" vs "es un"
- **Identidad vs igualdad:** dos objetos pueden tener los mismos valores pero ser objetos distintos

**Cuándo usar interfaz vs clase abstracta:**
```
Interfaz             → cuando no comparten implementación, solo el contrato
Clase abstracta      → cuando comparten implementación parcial + comportamiento abstracto
```

---

## 03+ — Colecciones

**Conceptos clave:**
- `List`: orden garantizado, permite duplicados → `ArrayList`
- `Set`: sin duplicados, sin orden garantizado → `HashSet`
- `Map`: clave-valor → `HashMap`
- Inicializar SIEMPRE en el constructor o en la declaración: `new ArrayList<>()`
- **Streams:** `filter()`, `map()`, `mapToDouble()`, `reduce()`, `collect()` — procesar colecciones sin loops
- **No exponer colecciones internas:** retornar `Collections.unmodifiableList()` o una copia

---

## 04 — Herencia y Template Method

**Conceptos clave:**
- `extends`: la subclase hereda atributos y métodos de la superclase
- **Method Lookup:** se busca el método empezando por el tipo real del objeto, subiendo en la jerarquía
- `super.metodo()`: busca en la superclase de la clase donde está escrito el código, pero `this` sigue siendo el objeto original
- **Template Method:** método concreto en la superclase que define el algoritmo y llama a métodos abstractos (ganchos) que implementan las subclases

**Regla de oro del method lookup:**
```
El método que se ejecuta = el del TIPO REAL del objeto
La variable es irrelevante para decidir qué método se llama

Empleado e = new Gerente();
e.montoBasico()  →  ejecuta Gerente.montoBasico()  (NO Empleado.montoBasico())
```

**Template Method:**
```java
// Superclase: define el esqueleto
public boolean extraer(double monto) {   // concreto
    if (this.puedeExtraer(monto)) {      // gancho abstracto
        this.extraerSinControlar(monto);
        return true;
    }
    return false;
}
protected abstract boolean puedeExtraer(double monto);  // gancho

// Subclase: solo implementa el gancho
@Override
protected boolean puedeExtraer(double monto) {
    return getSaldo() >= monto;
}
```

---

## 05 — HAR: Asignación de Responsabilidades

**Las 5 heurísticas:**

| Heurística | Principio |
|------------|-----------|
| **Experto en Información** | La responsabilidad va a quien tiene los datos para cumplirla |
| **Creador** | B crea A si B contiene A, usa A en forma exclusiva, o tiene los datos para inicializarla |
| **Bajo Acoplamiento** | Minimizar las dependencias entre clases |
| **Alta Cohesión** | Cada clase tiene responsabilidades fuertemente relacionadas entre sí |
| **Law of Demeter** | Un objeto solo habla con sus conocidos directos — no navegar cadenas |

**Law of Demeter:**
```java
// ❌ MAL: Libreria conoce a Compra y usa a Pago (un "extraño")
compra.getPago().getMontoEntregado()

// ✅ BIEN: Libreria solo conoce a Compra; Compra delega internamente
compra.getMontoEntregado()
```

**Contratos de operaciones:**
- **Pre-condiciones:** qué se asume que es verdadero antes de la operación (no se validan, se asumen)
- **Post-condiciones:** qué debe ser verdadero después de la operación (lo que se crea, modifica o destruye)
- Los contratos son la base para escribir tests: pre → fixture, post → asserts

---

## 06 — Testing

**Estructura básica JUnit 5:**
```java
@BeforeEach
void setUp() { /* crear objetos del fixture */ }

@Test
void nombreDescriptivo() {
    // Arrange (preparar si falta algo)
    // Act (ejecutar la operación)
    // Assert (verificar resultado)
}
```

**Particiones equivalentes:** identificar grupos de datos que el sistema trata igual
- Caso "dentro del rango válido"
- Caso "fuera del rango" (arriba y abajo)
- **Caso borde:** el valor límite exacto

**Assertions más usadas:**
```java
assertEquals(esperado, real)
assertEquals(esperado, real, delta)  // para doubles
assertTrue(condicion)
assertFalse(condicion)
assertNotNull(objeto)
```

---

## 07 — SOLID y Entity/Value Object

**SOLID resumido:**

| Principio | Enunciado simple |
|-----------|-----------------|
| **S** — SRP | Una clase, una razón para cambiar |
| **O** — OCP | Abierta para extensión, cerrada para modificación |
| **L** — LSP | Una subclase puede reemplazar a su superclase sin romper nada |
| **I** — ISP | Interfaces pequeñas y específicas, no una grande y general |
| **D** — DIP | Depender de abstracciones (interfaces), no de implementaciones |

**Entity vs Value Object:**
```
Entity (Entidad):           Value Object (Objeto Valor):
→ tiene identidad propia    → definido por sus atributos
→ importa QUIÉN es          → importa QUÉ vale
→ mutable                   → inmutable (no cambia)
→ persiste en el tiempo     → se puede reemplazar
Ejemplo: Cliente, Pedido    Ejemplo: Dirección, Dinero, Color
```

---

## 09 — UML: Diagramas

**Relaciones (de más débil a más fuerte):**
```
Asociación    A ──────────→ B     (A conoce a B)
Agregación    A ◇──────────→ B    (B puede existir sin A)
Composición   A ◆──────────→ B    (B no existe sin A)
Herencia      A △──────────  B    (A es subclase de B, línea sólida)
Interfaz      A △- - - - -  B    (A implementa B, línea punteada)
```

**Diagramas de secuencia:** muestran cómo colaboran los objetos en el tiempo
- Línea de vida vertical bajo cada objeto
- Flechas horizontales = mensajes
- Flecha punteada = retorno
- Caja sobre la línea = objeto en ejecución

---

## 10 — OO en JavaScript

**Diferencias clave con Java:**
- **Tipado dinámico:** no se declara el tipo de la variable
- **Duck typing:** un objeto es "compatible" si tiene el método que se pide, sin importar su clase
- **Prototype chain:** equivalente al method lookup de Java, pero dinámico
- Las clases ES6 son syntax sugar sobre prototipos

```javascript
// Polimorfismo sin declarar interfaz:
function hacerSonar(animal) {
    animal.sonido();  // funciona si animal tiene el método, sin importar su clase
}
```

---

## 11 — OO en Smalltalk

**Filosofía:**
- **Todo es un objeto** (incluidos números, booleanos, clases)
- **Todo es un mensaje** (incluido el control de flujo)

```smalltalk
"if" en Smalltalk es un mensaje a un booleano:
condicion ifTrue: [ ... ] ifFalse: [ ... ]

"while" es un mensaje a un bloque:
[ condicion ] whileTrue: [ ... ]

"for" es un mensaje a una colección:
coleccion do: [ :elemento | ... ]
```

---

## Lo que más sale en los parciales

1. **Modelo de dominio** desde texto (identificar clases, asociaciones, multiplicidades)
2. **Rediseño con polimorfismo** — eliminar `if` por tipo con herencia/interfaz
3. **Method lookup** — ¿qué método se ejecuta con esta jerarquía y estas variables?
4. **Implementación + tests** — escribir una clase desde cero con tests JUnit 5
