# Recetas de cocina

Se busca desarrollar un programa que permita representar recetas de cocina. En este ejercicio nos enfocamos en dos funcionalidades: la obtención de la descripción de una receta y el cálculo de su costo estimado.

Las recetas pueden incluir **bases**, **condimentos** o **proteínas**.

## Atributos por tipo de componente

**Base:** tipo de base, cantidad de porciones, integral o tradicional.

**Condimento:** mezcla de condimentos, cantidad de cucharaditas, picante o no picante.

**Proteína:** tipo de proteína, forma de presentación, cantidad de porciones, precio por porción.

Las recetas tienen un nombre y generalmente incluyen componentes de distintos tipos.

---

## Funcionalidad requerida

### 1. Descripción de una receta

La descripción es un String de varias líneas con la descripción de cada componente, numerados. Ejemplo:

```
Receta "Bowl tibio de pollo"
1. Base de arroz (integral, 2 porciones)
2. Condimento mix provenzal (no picante, 3 cucharaditas)
3. Proteína de pollo en cubos (2 porciones a $2200 por porción)
```

Patrones por tipo:

- **Base:** `"Base de <tipo> (<integral/tradicional>, <porciones> porciones)"`
- **Condimento:** `"Condimento <mezcla> (<picante/no picante>, <cucharaditas> cucharaditas)"`
- **Proteína:** `"Proteína de <tipo> en <forma> (<porciones> porciones a $<precio> por porción)"`

### 2. Costo estimado de una receta

Suma de los costos estimados de cada componente:

| Tipo | Costo |
|---|---|
| Base tradicional | $1500 fijo |
| Base integral | $2200 fijo |
| Condimento | $0 (stock básico) |
| Proteína | precio por porción × cantidad de porciones |

---

## Tareas

1. Diagrama de clases UML (ver carpeta `UML/`)
2. Implementación en Java (ver `src/main/java/`)
3. Instanciar el ejemplo "Bowl tibio de pollo" (ver `Main.java`)
4. Casos de prueba JUnit (ver `src/test/java/`)

---

## Plan de testing

### Clases y métodos a testear

| Clase de test | Métodos a testear |
|---|---|
| `BaseTest` | `getDescripcion()`, `getCostoEstimado()` |
| `CondimentoTest` | `getDescripcion()`, `getCostoEstimado()` |
| `ProteinaTest` | `getDescripcion()`, `getCostoEstimado()` |
| `RecetaTest` | `getDescripcion()`, `getCostoEstimado()` |

---

### `BaseTest`

#### `getDescripcion()`

| Test | Fixture | Resultado esperado |
|---|---|---|
| `descripcionBaseIntegral` | `new Base("arroz", 2, true)` | `"Base de arroz (integral, 2 porciones)"` |
| `descripcionBaseTradicional` | `new Base("arroz", 2, false)` | `"Base de arroz (tradicional, 2 porciones)"` |

#### `getCostoEstimado()`

| Test | Fixture | Resultado esperado |
|---|---|---|
| `costoBaseIntegral` | `new Base("arroz", 2, true)` | `2200.0` |
| `costoBaseTradicional` | `new Base("arroz", 2, false)` | `1500.0` |

> **Por qué testear `Base` en unidad:** `getCostoEstimado()` tiene dos particiones (integral vs. tradicional) que dependen de un booleano — es el lugar donde vive esa lógica.

---

### `CondimentoTest`

#### `getDescripcion()`

| Test | Fixture | Resultado esperado |
|---|---|---|
| `descripcionCondimentoPicante` | `new Condimento("mix provenzal", 3, true)` | `"Condimento mix provenzal (picante, 3 cucharaditas)"` |
| `descripcionCondimentoNoPicante` | `new Condimento("mix provenzal", 3, false)` | `"Condimento mix provenzal (no picante, 3 cucharaditas)"` |

#### `getCostoEstimado()`

| Test | Fixture | Resultado esperado |
|---|---|---|
| `costoCondimentoEsCero` | `new Condimento("mix provenzal", 3, false)` | `0.0` |

---

### `ProteinaTest`

#### `getDescripcion()`

| Test | Fixture | Resultado esperado |
|---|---|---|
| `descripcionProteina` | `new Proteina("pollo", "cubos", 2, 2200.0)` | `"Proteína de pollo en cubos (2 porciones a $2200 por porción)"` |

#### `getCostoEstimado()`

| Test | Fixture | Resultado esperado |
|---|---|---|
| `costoProteina` | `new Proteina("pollo", "cubos", 2, 2200.0)` | `4400.0` |

---

### `RecetaTest`

#### `getCostoEstimado()`

| Test | Fixture | Resultado esperado | Justificación |
|---|---|---|---|
| `costoRecetaSinComponentes` | Receta vacía | `0.0` | Borde: lista vacía |
| `costoRecetaSoloCondimentos` | Receta con 2 condimentos | `0.0` | Condimentos no suman costo |
| `costoRecetaConTodosLosTipos` | Base integral + condimento + proteína (2 porc a $2200) | `6600.0` | Verifica que acumula correctamente (2200 + 0 + 4400) |

#### `getDescripcion()`

| Test | Fixture | Resultado esperado | Justificación |
|---|---|---|---|
| `descripcionRecetaSinComponentes` | `new Receta("Bowl")` | `"Receta \"Bowl\""` | Borde: sin componentes |
| `descripcionRecetaConComponentes` | Base integral + condimento + proteína | String multilínea con 3 componentes numerados | Verifica numeración y saltos de línea |
