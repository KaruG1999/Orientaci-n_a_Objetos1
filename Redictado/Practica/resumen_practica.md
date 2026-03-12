# Resumen de Práctica — OO1 Redictado 2026

Qué implementa cada ejercicio, qué concepto principal practica y cuál es el estado actual.
Para el detalle de qué replantear y por qué, ver `evaluacion_ejercicios.md`.

---

## Estado general

| # | Ejercicio | Concepto principal | Tests | Pendiente |
|---|-----------|-------------------|:-----:|-----------|
| 01 | WallPost | Clase básica, atributos, métodos | ✅ | `getLikes()` sin `public` |
| 02 | Balanza Electrónica | Colaboración entre objetos, Ticket | ✅ | Envidia de datos, test roto |
| 03 | Presupuestos | Colecciones + LocalDate | ✅ | `getItems()` expone lista interna |
| 04 | Balanza Mejorada | Lista de objetos, colección con estado | ✅ | Estado redundante (contadores + lista) |
| 05 | Inversores | Polimorfismo, clase abstracta | ❌ | Tests faltantes |
| 06 | Distribuidora Eléctrica | Delegación, particiones equivalentes | ❌ | Tests faltantes |
| 07 | Figuras y Cuerpos | Interfaz, composición polimórfica | ✅ | `caraBasal` puede quedar `null` |
| 08 | Genealogía Salvaje | Recursión, relaciones self-referencing | ✅ | `Date` deprecated, riesgo de ciclos |
| 09 | Red de Alumbrado | Grafo, propagación recursiva, guard clause | ✅ | `getNeighbors()` expone lista |
| 10 | Method Lookup Empleados | Herencia, method lookup, `super` | ✅* | Tests recién agregados |
| 11 | Cuenta con Ganchos | Template Method, clases abstractas | ✅* | Tests recién agregados |

> `✅*` = tests escritos en el redictado (no existían antes)

---

## Ejercicio 01 — WallPost

**Qué implementa:** clase `WallPost` con texto, likes y flag de destacado.

**Concepto que practica:**
- Constructor con valores iniciales definidos
- Getters/setters, visibilidad de métodos
- Testing básico: `@BeforeEach`, `assertEquals`, `assertTrue`

**Qué aprender de este ejercicio:**
- Cómo estructurar una clase básica en Java
- La importancia de la consistencia en la visibilidad (`public` en todos los métodos de interfaz)
- Cómo escribir tests que verifiquen el estado inicial y las transiciones

---

## Ejercicio 02 — Balanza Electrónica

**Qué implementa:** `Producto` con peso y precio, `Balanza` que acumula productos y emite un `Ticket`.

**Concepto que practica:**
- Colaboración entre objetos: Balanza conoce a Producto
- `Ticket` como objeto inmutable que "captura" el estado en un momento (copia defensiva)

**Qué aprender de este ejercicio:**
- **Experto en Información:** el cálculo `peso × precio` le pertenece a `Producto`, no a `Balanza`
- **Copia defensiva:** `Ticket` hace `new ArrayList<>(lista)` para no depender de cambios futuros en Balanza
- Por qué no conviene calcular cosas sobre los datos de otro objeto sin delegarle el cálculo

---

## Ejercicio 03 — Presupuestos

**Qué implementa:** `Presupuesto` con cliente, fecha y lista de `Item`. Calcula el total.

**Concepto que practica:**
- Manejo de `LocalDate` (fechas)
- Colecciones: agregar ítems, calcular con `for` o Streams
- Testing de cálculos con múltiples objetos

**Qué aprender de este ejercicio:**
- Por qué no exponer la lista interna: `getItems()` debería retornar una vista no modificable
- `Collections.unmodifiableList()` para proteger el estado del objeto

---

## Ejercicio 04 — Balanza Mejorada

**Qué implementa:** evolución del Ej 02: ahora la Balanza recuerda todos los productos en una lista.

**Concepto que practica:**
- Lista como atributo de instancia
- Cálculo de totales iterando sobre la colección

**Qué aprender de este ejercicio:**
- **No mantener estado redundante:** si tenés una lista y también contadores de totales, los contadores se pueden calcular de la lista. Dos fuentes de verdad = inconsistencias potenciales
- `listaProd.clear()` vs `listaProd = new ArrayList<>()`: la segunda rompe referencias externas

---

## Ejercicio 05 — Inversores

**Qué implementa:** `Inversor` con lista de `Inversion` (abstracta). `Accion` y `PlazoFijo` la implementan.

**Concepto que practica:**
- **Polimorfismo:** `Inversor` llama `valorActual()` sin saber si es `Accion` o `PlazoFijo`
- **Clase abstracta:** `Inversion` define el contrato, no la implementación
- Streams: `mapToDouble(Inversion::valorActual).sum()`

**Qué aprender de este ejercicio:**
- Cómo diseñar una jerarquía polimórfica desde el enunciado
- Cómo manejar fechas con `LocalDate` y calcular diferencias con `ChronoUnit.DAYS.between()`
- Por qué es importante testear: los cálculos financieros son exactos y un bug silencioso es grave

**Pendiente — escribir tests:**
```java
// Patrón para PlazoFijo: usar fecha conocida para controlar la cantidad de días
LocalDate hace10Dias = LocalDate.now().minusDays(10);
PlazoFijo pf = new PlazoFijo(hace10Dias, 1000.0, 1.0); // 1% diario
assertEquals(1100.0, pf.valorActual(), 0.001);           // 1000 + 10 días * 10
```

---

## Ejercicio 06 — Distribuidora Eléctrica

**Qué implementa:** `Consumo` con energía activa/reactiva, `CuadroTarifario` con precio/kWh, `Factura` que aplica bonificación según FPE.

**Concepto que practica:**
- Delegación: `Factura` delega el cálculo del FPE a `Consumo`
- **Particiones equivalentes:** la bonificación depende de si FPE > 0.8, = 0.8, o < 0.8 → tres casos de test distintos

**Qué aprender de este ejercicio:**
- Cómo identificar particiones de equivalencia para testear condiciones
- El borde exacto: FPE = 0.8 NO tiene bonificación (la condición es `> 0.8`, no `>=`)
- Cómo diseñar valores de test que caigan en cada partición:

```
FPE > 0.8:   energiaActiva=5, energiaReactiva=3  → FPE = 5/√34 ≈ 0.857
FPE = 0.8:   energiaActiva=4, energiaReactiva=3  → FPE = 4/5 = 0.8  (borde exacto)
FPE < 0.8:   energiaActiva=3, energiaReactiva=4  → FPE = 3/5 = 0.6
```

---

## Ejercicio 07 — Figuras y Cuerpos

**Qué implementa:** `Figura2D` (interfaz), `Circulo` y `Cuadrado` la implementan. `Cuerpo3D` tiene una `Figura2D` como base.

**Concepto que practica:**
- **Interfaz:** `Figura2D` define `getArea()` y `getPerimetro()`
- **Composición polimórfica:** `Cuerpo3D` tiene una `Figura2D` genérica, no un tipo concreto → puede usarse con cualquier figura

**Qué aprender de este ejercicio:**
- Un constructor no debería dejar atributos en `null` si los métodos los usan — si `caraBasal` es obligatoria, debe estar en el constructor
- `assertNull` vs `assertThrows` para documentar qué pasa en casos inválidos

---

## Ejercicio 08 — Genealogía Salvaje

**Qué implementa:** `Mamifero` con padre y madre (self-referencing). `tieneComoAncestroA()` recursivo.

**Concepto que practica:**
- **Recursión:** un método que se llama a sí mismo con casos base claros
- **Null checks:** antes de recursar, verificar que el nodo existe
- Relación reflexiva: un `Mamifero` tiene `padre: Mamifero` y `madre: Mamifero`

**Qué aprender de este ejercicio:**
- Cómo diseñar recursión sin llegar al stack overflow (guard clauses, casos base)
- El riesgo de los ciclos: si A.padre = B y B.padre = A → recursión infinita
- `Date` está deprecated desde Java 8; usar `LocalDate` en su lugar

---

## Ejercicio 09 — Red de Alumbrado

**Qué implementa:** `Farola` que conoce a sus vecinas. `turnOn()` y `turnOff()` se propagan recursivamente.

**Concepto que practica:**
- **Grafo de objetos:** cada objeto tiene referencias a otros del mismo tipo
- **Guard clause:** `if (!encendida) { encendida = true; /* propagar */ }` evita la recursión infinita en ciclos
- **Relación bidireccional:** `pairWithNeighbor()` agrega a ambos lados

**Qué aprender de este ejercicio:**
- Cómo manejar recursión en grafos con riesgo de ciclos (la guard clause es clave)
- La diferencia entre establecer relaciones unidireccionales vs bidireccionales

---

## Ejercicio 10 — Method Lookup con Empleados ⭐

**Qué implementa:** jerarquía `Empleado → EmpleadoJerarquico → Gerente`. Cada uno sobreescribe `montoBasico()` y potencialmente `aportes()`.

**Concepto que practica:**
- **Method lookup:** qué método se ejecuta depende del tipo real del objeto
- **`super`:** cómo delegar parte del cálculo a la superclase, manteniendo `this` como el objeto real
- Binding dinámico con variable de tipo superclase

**Traza del method lookup (para estudiar):**
```
Gerente g = new Gerente("Carlos")
g.sueldoBasico() →
  EmpleadoJerarquico.sueldoBasico():
    super.sueldoBasico() →
      Empleado.sueldoBasico():
        this.montoBasico()  → Gerente.montoBasico() = 57000   ← dispatch dinámico!
        this.aportes()      → Gerente.aportes() = 57000*0.05 = 2850
        return 59850
    + this.bonoPorCategoria() → 8000
    return 67850

Empleado e = new Gerente("Carlos")  ← variable de tipo superclase
e.sueldoBasico()  → MISMO RESULTADO: 67850 (dispatch es por tipo real, no por tipo de variable)
```

> Tests escritos en el redictado: ver `src/test/java/.../EmpleadoTest.java`

---

## Ejercicio 11 — Cuenta con Ganchos (Template Method) ⭐

**Qué implementa:** `Cuenta` (abstracta) con template `extraer()`. `CajaDeAhorro` y `CuentaCorriente` implementan el gancho `puedeExtraer()`.

**Concepto que practica:**
- **Template Method:** el algoritmo vive en la superclase; la variación (el gancho) en las subclases
- Las subclases NO reimplementan el algoritmo completo — solo definen la parte variable

**Cómo funciona:**
```
Cuenta.extraer(monto):               ← template: el algoritmo
  1. ¿puedeExtraer(monto)?           ← gancho: dispatch a la subclase
  2. Si sí → extraerSinControlar()
  3. return resultado

CajaDeAhorro.puedeExtraer(monto):    ← gancho: la política específica
  return getSaldo() >= monto * 1.02

CuentaCorriente.puedeExtraer(monto): ← gancho: otra política
  return (getSaldo() - monto) >= -limiteDescubierto
```

**Qué aprender de este ejercicio:**
- Reconocer cuándo usar Template Method: cuando el algoritmo es el mismo pero algún paso varía entre subclases
- La diferencia entre sobreescribir el método completo vs sobreescribir solo el gancho
- Por qué el patrón reduce duplicación: la lógica de extraer está en UN lugar

> Tests escritos en el redictado: ver `src/test/java/.../CuentaTest.java`

---

## Patrones de diseño practicados (Ej 01–11)

| Patrón | Ejercicios que lo usan |
|--------|------------------------|
| Experto en Información | 02, 04, 05, 06 |
| Creador | 02 (Balanza crea Ticket), 11 (Cliente crea Contratación) |
| Polimorfismo | 05, 07, 09, 10, 11 |
| Template Method | 11 |
| Guard clause (evitar recursión) | 08, 09 |
| Copia defensiva | 02 (Ticket), 03, 04 |
| Particiones equivalentes (testing) | 06 |

---

## Ejercicios 12–26 — Pendientes de implementar

Estos ejercicios nunca fueron implementados. El enunciado completo está en `GuiaEjercicios.md`.

### Estado

| # | Ejercicio | Concepto principal | Depende de | Prioridad |
|---|-----------|-------------------|-----------|:---------:|
| 12 | Job Scheduler | Polimorfismo para reemplazar if/switch | — | 🔴 alta |
| 13 | Inversores (impl) | Clase abstracta + polimorfismo + tests | Ej 05 | 🔴 alta |
| 14 | Volumen y superficie de sólidos | Clase abstracta + fórmulas geométricas | Ej 07 | 🟡 media |
| 15 | Cliente de Correo | Composición, colecciones, búsqueda | — | 🟡 media |
| 16 | DateLapse | `LocalDate`, rango de fechas | — | 🟡 media |
| 17 | DateLapse alternativo | Misma interfaz, representación interna diferente | Ej 16 | 🟡 media |
| 18 | FilteredSet / EvenNumberSet | `Set<Integer>`, herencia vs composición | — | 🟢 baja |
| 19 | OOBnB (alquiler propiedades) | Modelo de dominio completo, disponibilidad | Ej 16 | 🔴 alta |
| 20 | Políticas de cancelación | Strategy pattern, polimorfismo | Ej 19 | 🔴 alta |
| 21 | Servicio de envíos | Polimorfismo en cálculo de costos, particiones | — | 🟡 media |
| 22 | Sistema de pedidos | Diagrama de dominio (solo UML, sin código) | — | 🟢 baja |
| 23 | Poolcar | Diagrama de dominio (solo UML, sin código) | — | 🟢 baja |
| 24 | GreenHome | Composición, órdenes con descuento | — | 🟡 media |
| 25 | Bag | `Map<K,V>`, composición, interfaz genérica | — | 🟢 baja |
| 26 | Estadísticas de Correo | Particiones equivalentes por tamaño | Ej 15 | 🟡 media |

---

### Ejercicio 12 — Job Scheduler

**Qué implementa:** `JobScheduler` elige el siguiente trabajo según una estrategia. La implementación actual usa `if/switch` sobre una variable `strategy`.

**Concepto que practica:**
- **Polimorfismo para eliminar if/switch:** cada estrategia se convierte en una clase concreta con el mismo mensaje `selectNext(jobs)`
- Strategy pattern avant la lettre

**Qué aprender:**
- Cuándo una cadena de `if/else` sobre un tipo es señal de que falta polimorfismo
- Cómo pasar de condicional → clases polimórficas sin cambiar los tests

**Plan de implementación:**
```
Clase abstracta/interfaz SchedulerStrategy
  → FIFOStrategy.selectNext()    → retorna el primero
  → PriorityStrategy.selectNext() → retorna el de mayor prioridad
  → SJFStrategy.selectNext()     → retorna el de menor duración estimada

JobScheduler recibe la estrategia por constructor (inmutable)
```

---

### Ejercicio 13 — Inversores (implementación completa)

**Qué implementa:** implementar en Java el diseño del Ej 05 + tests completos.

**Concepto que practica:**
- Llevar un modelo UML a código Java paso a paso
- Tests con `LocalDate` y fechas conocidas

**Qué aprender:**
- Mapeo UML → Java: clase abstracta, herencia, tipos de retorno
- Cómo testear `PlazoFijo` con fechas relativas:

```java
LocalDate hace10Dias = LocalDate.now().minusDays(10);
PlazoFijo pf = new PlazoFijo(hace10Dias, 1000.0, 1.0); // 1% diario
assertEquals(1100.0, pf.valorActual(), 0.001); // 1000 + 10 días * 10
```

- Casos de test: `Accion` (precio actual * cantidad), `PlazoFijo` (capital + interes*dias), `Inversor` (suma total)

---

### Ejercicio 14 — Volumen y superficie de sólidos

**Qué implementa:** clase abstracta `Pieza` con subclases `Cilindro`, `Esfera`, `PrismaRectangular`. `ReporteDeConstruccion` agrega piezas y calcula totales por material/color.

**Concepto que practica:**
- Clase abstracta con métodos `getVolumen()` y `getSuperficieExterior()` abstractos
- Polimorfismo para sumar sin `instanceof`

**Fórmulas clave:**
```
Cilindro:  V = π·r²·h     S = 2π·r·h + 2π·r²
Esfera:    V = 4/3·π·r³   S = 4π·r²
Prisma:    V = a·b·h       S = 2·(a·b + a·h + b·h)
```

**Diferencia con Ej 07:** acá no hay composición (cada sólido calcula solo). En Ej 07 `Cuerpo3D` delega en `Figura2D`.

---

### Ejercicio 15 — Cliente de Correo

**Qué implementa:** `ClienteDeCorreo` con `Carpeta`s (inbox + otras). Recibe, mueve, busca emails y calcula espacio.

**Concepto que practica:**
- Composición: cliente tiene carpetas, carpetas tienen emails, emails tienen adjuntos
- Streams para buscar y sumar

**Qué aprender:**
- `stream().filter().findFirst()` para buscar por condición
- Tamaño de email = título.length() + cuerpo.length() + sum(adjuntos.map(nombre.length()))
- Particiones para tests: email sin adjuntos / con adjuntos, búsqueda que encuentra / no encuentra

---

### Ejercicio 16 — DateLapse (intervalo de tiempo)

**Qué implementa:** clase `DateLapse` con `from` y `to`. Métodos: `sizeInDays()`, `includesDate(LocalDate)`.

**Concepto que practica:**
- Manejo de `LocalDate` y `ChronoUnit`
- Valores de borde: fecha en el límite exacto (¿incluida o no?)

**Qué aprender:**
```java
ChronoUnit.DAYS.between(from, to) // cantidad de días
date.isAfter(from) && date.isBefore(to) // ¿o incluir los bordes?
```

**Importante para Ej 19:** necesita el método `overlaps(DateLapse other)`:
```java
public boolean overlaps(DateLapse other) {
    return !this.to.isBefore(other.from) && !this.from.isAfter(other.to);
}
```

---

### Ejercicio 17 — DateLapse alternativo

**Qué implementa:** misma interfaz que Ej 16 pero representación interna = `from` + `sizeInDays` (sin `to`).

**Concepto que practica:**
- **Encapsulamiento:** el cambio de representación interna no afecta a quienes usan el objeto
- Interfaz Java compartida: `IDateLapse` que ambas implementan

**Qué aprender:**
- Los tests del Ej 16 deben pasar sin cambios: solo actualizar el `setUp()` para usar la nueva clase
- Por qué la interfaz es el contrato que estabiliza el sistema

---

### Ejercicio 18 — FilteredSet / EvenNumberSet

**Qué implementa:** `EvenNumberSet` que implementa `Set<Integer>` y solo acepta números pares.

**Concepto que practica:**
- Implementar una interfaz Java estándar (`Set<Integer>`)
- Dos enfoques: herencia de `AbstractSet` vs composición delegando a un `HashSet`

**Qué aprender:**
```java
// Herencia:
class EvenNumberSet extends AbstractSet<Integer> { ... }

// Composición:
class EvenNumberSet implements Set<Integer> {
    private Set<Integer> inner = new HashSet<>();
    public boolean add(Integer n) {
        if (n % 2 != 0) return false;
        return inner.add(n);
    }
}
```
Comparar ventajas: herencia es más corta pero acopla; composición es más flexible.

---

### Ejercicio 19 — OOBnB (alquiler de propiedades)

**Qué implementa:** `Usuario`, `Propiedad`, `Reserva`, `DateLapse`. Disponibilidad, precio, cancelación, ingresos del propietario.

**Concepto que practica:**
- Modelo de dominio completo: varios objetos que colaboran
- Uso de `DateLapse.overlaps()` para verificar disponibilidad
- Cancela solo si la reserva no está en curso (fecha actual < fecha inicio)

**Particiones para tests:**
```
Disponibilidad: sin reservas | con reserva que no solapa | con reserva que solapa
Precio: noches * precioNoche (sin particiones de borde aquí)
Cancelación: período no iniciado (permitido) | período en curso (no permitido)
Ingresos: sin reservas en período | con reservas | borde exacto de fecha
```

---

### Ejercicio 20 — Políticas de cancelación

**Qué implementa:** extiende Ej 19 con `PoliticaDeCancelacion` (flexible / moderada / estricta). La propiedad conoce su política.

**Concepto que practica:**
- **Strategy pattern:** la política es un objeto que implementa un método `calcularReembolso(reserva, fechaCancelacion)`
- La política puede cambiar en cualquier momento (a diferencia del Job Scheduler del Ej 12)

**Qué aprender:**
```
Flexible:  reembolso = 100% siempre
Moderada:  > 7 días antes → 100% | > 2 días antes → 50% | <= 2 días → 0%
Estricta:  reembolso = 0% siempre
```
Particiones de borde para `Moderada`: exactamente 7 días, exactamente 2 días.

---

### Ejercicio 21 — Servicio de envíos

**Qué implementa:** `Envio` (abstracta) con subclases `EnvioLocal`, `EnvioInterurbano`, `EnvioInternacional`. `Cliente` (persona física / corporativo). Calcula monto por período.

**Concepto que practica:**
- Polimorfismo en `calcularCosto()` según tipo de envío
- Particiones en `EnvioInterurbano` (< 100 km, 100-500 km, > 500 km) y `EnvioInternacional` (≤ 1 kg, > 1 kg)
- Descuento del 10% para personas físicas

**Particiones para tests:**
```
Local:          estándar | con entrega rápida (+$500)
Interurbano:    < 100 km | 100-500 km | > 500 km (borde exacto 100 y 500)
Internacional:  ≤ 1000 g | > 1000 g | con entrega rápida
Cliente:        persona física (−10%) | corporativo (sin descuento)
```

---

### Ejercicio 22 — Sistema de pedidos (solo UML)

**Qué implementa:** solo diagrama de clases. No hay código Java.

**Conceptos a modelar:**
- `Pedido` (viandas vs supermercado) → Strategy o herencia
- `Entrega` (delivery vs retiro en sucursal) → Strategy
- `Producto` abstracto: `Vianda` y `ArticuloSupermercado`
- Cupón de descuento aplica solo a pedidos de supermercado

---

### Ejercicio 23 — Poolcar (solo UML)

**Qué implementa:** solo diagrama de clases. No hay código Java.

**Conceptos a modelar:**
- `Usuario` → `Conductor` y `Pasajero` (herencia, rol fijo)
- `Conductor` tiene `Vehiculo`s, crea `Viaje`s
- `Pasajero` se inscribe en `Viaje`s (con restricciones: saldo > 0, 2 días antes)
- Al cobrar el viaje: descuento por vehículo (conductor) y descuento por viaje previo (pasajero)

---

### Ejercicio 24 — GreenHome

**Qué implementa:** `OrdenDeCompra` y `OrdenDeServicio` con `calcularCosto()`. Descuento 10% si ≥ 5 productos o > 10 hs de trabajo.

**Concepto que practica:**
- Template Method o polimorfismo: `Orden.calcularCosto()` difiere entre compra y servicio
- Particiones: `OrdenDeCompra` con < 5 productos (sin desc) y ≥ 5 (con desc); `OrdenDeServicio` con ≤ 10 hs y > 10 hs
- Borde exacto: 5 productos y 10 horas

**Costo de servicio:**
```
costo = sum(productos) + sum(tecnicos.map(horas * valorHora))
si (cantidadProductos >= 5 || horas > 10) → costo × 0.90
```

---

### Ejercicio 25 — Bag

**Qué implementa:** `Bag<T>` implementada con composición sobre `Map<T, Integer>`. Cardinalidad por elemento.

**Concepto que practica:**
- `Map<K,V>`: estructura, métodos (`put`, `get`, `getOrDefault`, `remove`)
- Composición sobre un `HashMap` + herencia de `AbstractCollection`
- Genéricos: `Bag<T>` parametrizado

**Qué aprender:**
```java
// Internamente:
Map<T, Integer> elementos = new HashMap<>();

// add:
elementos.merge(element, 1, Integer::sum); // incrementa o inicia en 1

// occurrencesOf:
return elementos.getOrDefault(element, 0);

// removeOccurrence:
int actual = occurrencesOf(element);
if (actual == 1) elementos.remove(element);
else elementos.put(element, actual - 1);
```

---

### Ejercicio 26 — Estadísticas del Cliente de Correo

**Qué implementa:** extiende Ej 15 con estadísticas: cantidad de emails por carpeta, total de emails, y categorización por tamaño (pequeño/mediano/grande).

**Concepto que practica:**
- Streams con `Collectors.groupingBy()` o `Collectors.counting()`
- Particiones por tamaño: [0–300] pequeño, [301–500] mediano, [501+] grande
- Borde exacto: tamaño 300 (pequeño), 301 (mediano), 500 (mediano), 501 (grande)

**Qué aprender:**
```java
// Cantidad por carpeta:
carpeta.getEmails().size()

// Categorizar:
Map<String, Long> porCategoria = carpeta.getEmails().stream()
    .collect(Collectors.groupingBy(e -> {
        int tam = e.getTamanio();
        if (tam <= 300) return "Pequeño";
        if (tam <= 500) return "Mediano";
        return "Grande";
    }, Collectors.counting()));
```

---

## Orden de trabajo recomendado (ejercicios 12–26)

### Fase 1 — Fundamentos (más importantes para el examen)
1. **Ej 12** — Job Scheduler: eliminar if/switch con polimorfismo (concepto más evaluado)
2. **Ej 13** — Inversores: implementar lo diseñado en Ej 05 con tests
3. **Ej 16** — DateLapse: `LocalDate` base para todo el bloque 19-20

### Fase 2 — Modelo de dominio completo
4. **Ej 19** — OOBnB: el ejercicio integrador más grande (requiere Ej 16)
5. **Ej 20** — Políticas: Strategy sobre Ej 19
6. **Ej 14** — Sólidos: similar a Ej 07 pero más simple (clase abstracta pura)

### Fase 3 — Colecciones avanzadas y diseño
7. **Ej 21** — Envíos: buen ejercicio de particiones + polimorfismo
8. **Ej 15** — Cliente de correo: streams + composición
9. **Ej 24** — GreenHome: particiones en `calcularCosto()`

### Fase 4 — Ejercicios de colecciones / UML
10. **Ej 25** — Bag: `Map` + genéricos
11. **Ej 17** — DateLapse alt: encapsulamiento de representación
12. **Ej 18** — FilteredSet: herencia vs composición en colecciones
13. **Ej 26** — Estadísticas: streams + Ej 15
14. **Ej 22, 23** — Solo UML (hacer al final si hay tiempo)
