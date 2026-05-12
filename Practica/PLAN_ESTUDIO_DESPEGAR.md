# Plan de estudio — Test técnico Despegar (jueves 14/05)

Hoy es lunes 12/05. Tenés 3 días. Este plan está diseñado para cubrir los conceptos que más aparecen
en tests técnicos de trainee en empresas de tecnología como Despegar.

---

## Por qué estos ejercicios

Despegar es una empresa de viajes. Sus sistemas manejan **reservas, disponibilidad, precios y políticas**.
Los ejercicios elegidos replican exactamente ese dominio y los patrones de diseño que se usan para implementarlo.

---

## Ejercicios seleccionados y por qué

| Ejercicio | Ubicación | Concepto clave | Relevancia |
|-----------|-----------|----------------|-----------|
| **Ej 11** — Cuenta con Ganchos | `Ejercicios/11_CuentaConGanchos` | **Template Method** | Patrón más evaluado en tests técnicos |
| **Ej 10** — Method Lookup Empleados | `Ejercicios/10_Method_LoockupConEmpleados` | **Herencia + `super` + dispatch dinámico** | Concepto OOP fundamental |
| **Ej 12** — Job Scheduler | `Ejercicios/12_JobScheduler` | **Strategy pattern** (eliminar if/switch) | Muy común en tests de trainee |
| **Ej 16** — DateLapse | `Ejercicios/16_DateLapse` | **LocalDate, intervalos** | Base para 19 y 20 |
| **Ej 19** — OOBnB | `Ejercicios/19_OOBnB` | **Modelo de dominio completo** (reservas, disponibilidad) | Dominio idéntico al de Despegar |
| **Ej 20** — Políticas de Cancelación | `Ejercicios/20_PoliticasCancelacion` | **Strategy sobre dominio de viajes** | Directamente aplicable a Despegar |
| **Ej 21** — Servicio de Envíos | `Ejercicios/21_ServicioEnvios` | **Polimorfismo + particiones equivalentes** | Practica testing con bordes |

---

## Plan día a día

### Lunes 12/05 — Patrones + Template Method

**Objetivo:** dominar los patrones más evaluados.

1. **Ej 11** — Leer `cuentaCG.md`, releer `src/main/java/.../Cuenta.java`.
   - Preguntarte: ¿qué hace la superclase? ¿qué hace cada subclase?
   - Correr `mvn test` en `11_CuentaConGanchos` → todos deben pasar.
   - Si algo no pasa: entender POR QUÉ antes de seguir.

2. **Ej 10** — Leer `MethodLoockupConEmp.md`, trazar en papel:
   ```
   Gerente g = new Gerente("X");
   g.sueldoBasico() → ¿qué método se ejecuta en cada paso?
   ```
   - Correr `mvn test` → verificar que los tests pasan y entender qué prueban.

3. **Ej 12** — Implementar (proyecto nuevo):
   - Abrir `12_JobScheduler/src/main/java/ar/edu/unlp/oo1/`
   - Implementar en orden: `EstrategiaFIFO` → `EstrategiaPrioridad` → `EstrategiaSJF` → `JobScheduler`
   - Correr `mvn test` hasta que todos pasen.

---

### Martes 13/05 — Dominio de viajes (lo más relevante para Despegar)

**Objetivo:** construir el modelo completo de reservas.

1. **Ej 16** — Implementar `DateLapse` (30 minutos):
   - `sizeInDays()`, `includesDate()`, `overlaps()`
   - Correr `mvn test` → los 8 tests deben pasar.
   - Este es la clase utilitaria que usa OOBnB.

2. **Ej 19** — Implementar OOBnB:
   - Orden recomendado: `Reserva` → `Propiedad` → `Usuario`
   - Ir test por test: hacer pasar uno antes de seguir al siguiente
   - El modelo mental: *"¿quién tiene los datos para hacer este cálculo?"* (Experto en Info)

3. **Ej 20** — Implementar Políticas de Cancelación (extiende 19):
   - Primero: copiar/reimplementar `Propiedad`, `Reserva`, `Usuario` del Ej 19
   - Luego: `PoliticaFlexible` → `PoliticaEstricta` → `PoliticaModerada`
   - Los bordes de `PoliticaModerada` (7 días y 2 días) son los que más se evalúan.

---

### Miércoles 14/05 — Polimorfismo + repaso integral

**Objetivo:** consolidar y detectar qué falta.

1. **Ej 21** — Implementar Servicio de Envíos:
   - Orden: `EnvioLocal` → `EnvioInterurbano` → `EnvioInternacional` → `PersonaFisica` → `ClienteCorporativo` → `Cliente.montoEnPeriodo()`
   - Prestar atención a los **bordes exactos**: 100 km, 500 km, 1000 g.

2. **Repaso rápido** de errores comunes (ver `evaluacion_ejercicios.md`):
   - Encapsulamiento: no exponer listas internas
   - Experto en Información: el cálculo va en la clase que tiene los datos
   - Estado redundante: no duplicar contadores si ya tenés la lista

3. **Síntesis teórica**: revisar estos conceptos que pueden aparecer en preguntas:
   - ¿Qué es el dispatch dinámico / binding tardío?
   - ¿Cuándo usarías Template Method vs Strategy?
   - ¿Qué es encapsulamiento y por qué `getItems()` puede romperlo?

---

## Conceptos que SEGURO te preguntan

### 1. Dispatch dinámico (Method Lookup)
```java
Cuenta c = new CajaDeAhorro(5000);
c.extraer(1000);  // ¿qué método se ejecuta?
```
→ Se ejecuta `Cuenta.extraer()` (el template), que llama a `CajaDeAhorro.puedeExtraer()`.
El tipo de la **variable** (`Cuenta`) no importa. Importa el tipo **real** del objeto (`CajaDeAhorro`).

### 2. Template Method
- La superclase define el **algoritmo** (no se toca).
- Las subclases solo implementan el **gancho** (la parte variable).
- Error común: sobreescribir el método completo en vez del gancho.

### 3. Strategy
- El comportamiento variable se extrae a una clase separada (la estrategia).
- El objeto que usa la estrategia no sabe qué tipo concreto tiene.
- Permite cambiar el comportamiento en runtime (o en el constructor).

### 4. Experto en Información
- Si un método necesita datos de otro objeto, probablemente el método pertenece a ese otro objeto.
- Ejemplo: `peso × precio` → no lo calcula `Balanza`, lo calcula `Producto`.

### 5. Encapsulamiento real
```java
// MAL: quien llama puede hacer lista.clear() y corrompe el estado
public List<Item> getItems() { return this.items; }

// BIEN: lista de solo lectura
public List<Item> getItems() { return Collections.unmodifiableList(this.items); }
```

---

## Cómo correr los tests

```bash
cd Practica/Ejercicios/XX_NombreEjercicio
mvn test
```

Para ver solo los errores:
```bash
mvn test 2>&1 | grep -E "FAILED|ERROR|Tests run"
```

---

## Ejercicios ya implementados para revisar (no implementar desde cero)

| # | Carpeta | Qué repasar |
|---|---------|------------|
| 10 | `10_Method_LoockupConEmpleados` | Trazar la ejecución de `sueldoBasico()` a mano |
| 11 | `11_CuentaConGanchos` | Identificar cuál es el template y cuál el gancho |
| 05 | `05_Inversores` | Revisar cómo `valorActual()` es polimórfico |
| 06 | `06_DistribuidoraElectrica` | Ver cómo se identifican las particiones equivalentes |
| 14 | `14_VolumenYSuperficieDeSolidos` | Clase abstracta pura (sin composición) |

---

## Lo que NO hace falta para el test técnico de trainee

- Ejercicios de UML puro (22 y 23)
- Ej 25 (Bag con Map) — más avanzado
- Ej 26 (Estadísticas) — requiere Ej 15 primero
- Ej 17 y 18 — variantes que no agregan conceptos nuevos para este nivel
