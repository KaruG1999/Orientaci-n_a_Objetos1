# Qué replantear del cuatrimestre anterior — y por qué

Este archivo documenta los problemas de diseño y de práctica detectados en los ejercicios del cuatrimestre 2025, con foco en entender **qué estuvo mal, por qué estuvo mal, y qué concepto hay que reforzar** para hacerlo bien en el redictado.

El objetivo no es copiar correcciones: es entender el error para no repetirlo.

---

## Estado de los ejercicios

| # | Ejercicio | Problema principal | Prioridad para rehacer |
|---|-----------|-------------------|:----------------------:|
| 01 | WallPost | Detalle menor de visibilidad | 🟢 baja |
| 02 | Balanza Electrónica | Envidia de datos + test roto | 🟡 media |
| 03 | Presupuestos | Colección interna expuesta | 🟢 baja |
| 04 | Balanza Mejorada | Estado redundante | 🟡 media |
| 05 | Inversores | Sin tests | 🔴 alta |
| 06 | Distribuidora | Sin tests | 🔴 alta |
| 07 | Figuras y Cuerpos | Constructor deja `null` | 🟡 media |
| 08 | Genealogía | `Date` viejo, riesgo ciclos | 🟢 baja |
| 09 | Red Alumbrado | Colección expuesta | 🟢 baja |
| 10 | Method Lookup | Sin tests, magic numbers | 🔴 alta |
| 11 | Cuenta con Ganchos | Sin tests, template mal usado en CajaDeAhorro | 🔴 alta |

---

## Errores que se repiten en varios ejercicios

### 1. Exponer colecciones internas (Ej 03, 04, 05, 09)

**El problema:**
```java
public List<Item> getItems() { return this.items; }
// Quien llama puede hacer: presupuesto.getItems().clear() → corrompe el estado
```

**Por qué está mal:** rompe el encapsulamiento. El objeto pierde el control sobre sus propios datos.

**La corrección:**
```java
// Opción A: lista de solo lectura
public List<Item> getItems() { return Collections.unmodifiableList(this.items); }

// Opción B: copia defensiva (independiente del original)
public List<Item> getItems() { return new ArrayList<>(this.items); }
```

**Qué aprender:** el encapsulamiento no es solo poner `private` en los atributos; también aplica a las colecciones que retornan los métodos.

---

### 2. Tests faltantes en los ejercicios más importantes (Ej 05, 06, 10, 11)

**El problema:** los ejercicios con lógica más compleja (polimorfismo, method lookup, template method, cálculos financieros) no tienen ningún test.

**Por qué está mal:** sin tests no podés saber si el código funciona. En OO, los tests también documentan qué se espera de cada clase.

**Qué aprender:** los tests se escriben con el código, no después. Para cada método con lógica de negocio:
1. Identificar las particiones (casos verdadero, falso, borde)
2. Crear un fixture conocido (valores que podés calcular a mano)
3. Escribir un test por partición

> Los tests de Ej10 y Ej11 ya están escritos en el redictado como punto de partida.

---

### 3. Estado redundante (Ej 04)

**El problema:** `Balanza` mantiene `cantidadDeProductos`, `precioTotal` y `pesoTotal` como atributos separados, además de la `listaProd`. Los primeros tres son calculables desde la lista en cualquier momento.

**Por qué está mal:** si en algún momento la lista y los contadores difieren, el objeto está en un estado inconsistente. No hay una sola fuente de verdad.

**Qué aprender:** si un dato se puede calcular desde otros datos que ya tenés, no lo guardes como atributo separado. Calculalo cuando se necesite:
```java
public int getCantidadDeProductos() { return listaProd.size(); }
public double getPrecioTotal() { return listaProd.stream().mapToDouble(...).sum(); }
```

---

### 4. Envidia de datos — el cálculo está en el lugar equivocado (Ej 02)

**El problema:** `Balanza.agregarProducto()` hace `p.getPeso() * p.getPrecioPorKilo()`. Está usando los datos de `Producto` para calcular algo que `Producto` debería calcular por sí mismo.

**Por qué está mal:** viola el principio Experto en Información. Quien tiene los datos hace el cálculo. Si `Producto` cambia cómo calcula su precio, hay que modificar `Balanza` también.

**Qué aprender:**
- Si un método accede a varios atributos de OTRO objeto, ese método probablemente pertenece al otro objeto.
- Preguntar: ¿quién tiene los datos para hacer esto?

---

### 5. Template Method mal aprovechado (Ej 11)

**El problema:** `CajaDeAhorro` sobreescribe `extraer()` y `transferirACuenta()` completos, en lugar de aprovechar el template de `Cuenta`. Duplica lógica que ya está en la superclase.

**Por qué está mal:** el Template Method existe exactamente para no tener que repetir el algoritmo en cada subclase. Si cada subclase reescribe todo, el patrón no aporta nada.

**Qué aprender:** en Template Method, las subclases SOLO implementan el gancho (la parte que varía). El algoritmo principal vive en la superclase y no se toca.

```
❌ Subclase reescribe extraer() completo → duplicación
✅ Subclase solo define puedeExtraer() → el template hace el resto
```

> Nota: la implementación actual funciona, pero el patrón está incompleto. Entender POR QUÉ es el objetivo.

---

### 6. Constructor que deja estado inválido (Ej 07)

**El problema:** `Cuerpo3D` tiene un constructor que no recibe `caraBasal`, dejándola en `null`. Los métodos `getVolumen()` y `getSuperficieExterior()` usan `caraBasal` sin verificar que no sea null → `NullPointerException` garantizado.

**Por qué está mal:** un objeto debe ser válido desde el momento en que se crea. El constructor es el lugar para garantizar ese estado inicial.

**Qué aprender:**
- Si un atributo es obligatorio para que el objeto funcione, debe ser parámetro del constructor.
- Alternativa: si hay un setter posterior, validar en los métodos que lo usan antes de usarlo.

---

## Cosas bien hechas que conviene seguir haciendo

Estos son patrones correctos que ya están en los ejercicios — identificarlos te ayuda a reconocerlos en ejercicios futuros:

- **Ej 02 — Ticket:** copia defensiva de la lista en el constructor → el Ticket no muta aunque la Balanza cambie después
- **Ej 05 — Inversor:** `stream().mapToDouble(Inversion::valorActual).sum()` → delega a cada inversión su cálculo sin saber el tipo concreto
- **Ej 07 — Cuerpo3D:** tiene una `Figura2D` genérica (interfaz) en vez de `Circulo` o `Cuadrado` → puede usarse con cualquier figura
- **Ej 08 — Mamifero:** `if (unMamifero == null) return false` antes de recursar → null check en el caso base correcto
- **Ej 09 — Farola:** `if (!encendida)` como guard clause → evita recursión infinita en la red
- **Ej 11 — Cuenta:** `puedeExtraer()` como `protected abstract` → el gancho correcto para el Template Method

---

## Orden de trabajo para el redictado

### Prioridad 1 — Tests faltantes (hacerlos primero, son la base)
1. **Ej 05** Inversores → tests de `Accion`, `PlazoFijo`, `Inversor`
2. **Ej 06** Distribuidora → tests con las 3 particiones del FPE
3. ~~Ej 10 y 11~~ → ya tienen tests escritos en el redictado

### Prioridad 2 — Correcciones de diseño (hacer y entender el porqué)
4. **Ej 02** → mover cálculo `peso × precio` a `Producto`; arreglar test roto
5. **Ej 04** → eliminar contadores redundantes, calcular desde la lista
6. **Ej 07** → agregar `caraBasal` al constructor de `Cuerpo3D`

### Prioridad 3 — Mejoras menores (cuando haya tiempo)
7. **Ej 03** → proteger lista interna con `unmodifiableList`
8. **Ej 08** → cambiar `Date` por `LocalDate`
9. **Ej 01** → agregar `public` a `getLikes()`
