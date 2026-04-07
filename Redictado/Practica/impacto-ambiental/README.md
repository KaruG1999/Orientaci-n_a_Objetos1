# Gestión del impacto ambiental de visitas en una reserva natural

Una reserva natural organiza visitas guiadas de distintos tipos. Las visitas tienen impacto ambiental, y la reserva desea poder gestionar y evaluar ese impacto en función de las características de cada visita.

Existen **tres tipos de visita**: recreativas, educativas y científicas. De cada visita se puede obtener la fecha, la duración en horas y el listado de participantes.

## Tipos de visita

### Visita Recreativa
Dirigida a turistas o familias. Su impacto ambiental se estima en **1 punto por participante por hora** de duración.

### Visita Educativa
Realizada por grupos escolares. Puede participar un único grupo o varios. Cada grupo se identifica con:
- Nombre del colegio
- Identificador de grupo
- Lista de docentes responsables
- Lista de alumnos

El impacto ambiental de un grupo es de **0.5 puntos por alumno por hora**, menos **1 punto por hora por cada docente** responsable. Sin importar el tamaño del grupo, el **impacto mínimo es de 2 puntos por hora**.

### Visita Científica
Solicitada por investigadores, puede requerir acceso a zonas restringidas. Su impacto:
- **Base:** 50 puntos
- **+** 5 puntos por cada hora adicional a la primera
- **+** 100 puntos por cada zona restringida a la que acceden

Al registrar la visita se indican las zonas a las que accede. De las zonas solo interesa su nombre y descripción.

## Funcionalidad requerida

- Calcular el impacto ambiental de una visita
- Obtener el listado completo de asistentes de una visita (ordenado alfabéticamente)

---

## Revisión de código — 2026-04-05

### Reglas de diseño OO: APROBADAS
- `VisitaGuiada` abstracta con `calcularImpacto()` abstracto ✓
- `Zona` abstracta con `getAdicionalImpacto()` + subclases (elimina instanceof) ✓
- `VisitaEducativa` delega en `Grupo.calcularImpactoGrupo()` ✓
- Colecciones inicializadas en constructores ✓

### Estado: NO apta para Testing — corregir antes de escribir tests

---

### Errores de compilación (4)

**1. `VisitaGuiada.java:11` — Tipo incorrecto en el atributo**
```java
// MAL:
private Participante participantes;
// BIEN:
private List<Participante> participantes;
```

**2. `VisitaGuiada.java:23` — Método `abstract` con cuerpo**
En Java un método abstract no puede tener cuerpo. Además no debe ser abstracto: todas las subclases comparten la misma lista.
```java
// MAL:
public abstract List<Participante> getParticipantes(){ return this.participantes; }
// BIEN (quitar abstract):
public List<Participante> getParticipantes(){ return this.participantes; }
```

**3. `VisitaCientifica.java:20` — Falta `()` en llamada al método**
```java
// MAL:
if (this.getDuracionHoras > 1) {
// BIEN:
if (this.getDuracionHoras() > 1) {
```

**4. `Grupo.java` — Faltan `getAlumnos()` y `getDocentes()`**
`VisitaEducativa.agregarGrupo()` los llama pero no están definidos en Grupo.
```java
public List<Participante> getAlumnos() { return alumnos; }
public List<Participante> getDocentes() { return docentes; }
```

---

### Errores de lógica (2)

**5. `VisitaCientifica.java:21` — Fórmula del adicional por hora incorrecta**
"5 puntos por cada hora adicional a la primera" → hay que restar la primera hora.
```java
// MAL — para 3h da 50+15=65, debería ser 50+10=60:
impacto += (this.getDuracionHoras() * 5);
// BIEN:
impacto += (this.getDuracionHoras() - 1) * 5;
```

| Duración | MAL | BIEN |
|---|---|---|
| 1h | 50 | 50 |
| 2h | 60 | 55 |
| 3h | 65 | 60 |

**6. `Grupo.java:32` — El mínimo de 2 puntos es por hora, no total**
```java
// MAL — mínimo fijo de 2 sin importar las horas:
double impacto = Math.max(2.0, puntos);
// BIEN — mínimo escala con la duración:
double impacto = Math.max(2.0 * duracionHoras, puntos);
```
Ejemplo: 1 alumno, 0 docentes, 4 horas → puntos=2, mínimo debería ser 8, no 2.

---

### Detalles menores
- `Zona.java`: falta el atributo `descripcion` que pide el enunciado.
- `VisitaCientifica.java:18`: falta `@Override` en `calcularImpacto()`.
- `VisitaCientifica.java:23`: el `if (zonas.size() > 0)` es redundante, el for sobre lista vacía no itera.

---

## Plan de testing — 2026-04-06

### Estado: código corregido y listo para tests

Todos los errores del README fueron aplicados. Además se corrigió:
- Doble `{{` en constructores de `ZonaRestringida` y `ZonaComun`
- Agregado `agregarZona(Zona zona)` en `VisitaCientifica`
- Implementado `getListadoAsistentes()` en `VisitaGuiada`

---

### Paso 1 — Directorio de tests a crear

```
src/test/java/ar/edu/unlp/info/oo1/
```

---

### Paso 2 — Clases de test y orden de implementación

| Orden | Clase de test | Método a testear |
|---|---|---|
| 1 | `GrupoTest` | `calcularImpactoGrupo()` |
| 2 | `VisitaRecreativaTest` | `calcularImpacto()` |
| 3 | `VisitaCientificaTest` | `calcularImpacto()` |
| 4 | `VisitaEducativaTest` | `calcularImpacto()` + `getListadoAsistentes()` |

---

### Paso 3 — Casos de test por clase

#### `GrupoTest` — `calcularImpactoGrupo()`
La condición `Math.max(2.0 * horas, puntos)` genera dos particiones y un borde.

| Test | Alumnos | Docentes | Horas | Esperado | Justificación |
|---|---|---|---|---|---|
| `impactoNormal` | 6 | 0 | 1 | 3.0 | Partición: fórmula > mínimo |
| `impactoMinimo` | 1 | 0 | 2 | 4.0 | Partición: fórmula < mínimo |
| `impactoExactamenteEnElBorde` | 4 | 0 | 1 | 2.0 | Borde: fórmula == mínimo |
| `docentesReducenAlMinimo` | 4 | 2 | 2 | 4.0 | Docentes llevan puntos a negativo |

---

#### `VisitaRecreativaTest` — `calcularImpacto()`
Fórmula sin condiciones → borde natural es lista vacía.

| Test | Participantes | Horas | Esperado | Justificación |
|---|---|---|---|---|
| `impactoSinParticipantes` | 0 | 2 | 0.0 | Borde: lista vacía |
| `impactoConParticipantes` | 3 | 2 | 6.0 | Representante de la partición general |

---

#### `VisitaCientificaTest` — `calcularImpacto()`
Dos variables independientes: duración (con `if`) y zonas restringidas (con `for`).

| Test | Horas | Zonas restringidas | Esperado | Justificación |
|---|---|---|---|---|
| `impactoUnaHoraSinZonas` | 1 | 0 | 50.0 | Borde: el `if` NO entra |
| `impactoDosHorasSinZonas` | 2 | 0 | 55.0 | Borde: el `if` entra por primera vez |
| `impactoVariasHorasSinZonas` | 3 | 0 | 60.0 | Representante partición >1h |
| `impactoConUnaZonaRestringida` | 1 | 1 | 150.0 | Partición: con zona restringida |
| `impactoConVariasZonasRestringidas` | 1 | 2 | 250.0 | Verifica que el `for` acumula |

---

#### `VisitaEducativaTest` — `calcularImpacto()` y `getListadoAsistentes()`
No redundar con `GrupoTest`. Solo verificar que la suma entre grupos funciona y que el listado sale ordenado.

**`calcularImpacto()`**

| Test | Setup | Esperado | Justificación |
|---|---|---|---|
| `impactoSinGrupos` | Ningún grupo | 0.0 | Borde: lista vacía |
| `impactoUnGrupo` | 1 grupo (6 alum, 0 doc, 1h) | 3.0 | Representante |
| `impactoVariosGrupos` | 2 grupos iguales (6 alum, 0 doc, 1h) | 6.0 | Verifica que acumula |

**`getListadoAsistentes()`**

| Test | Setup | Esperado | Justificación |
|---|---|---|---|
| `listadoSinGrupos` | Ningún grupo | lista vacía | Borde |
| `listadoOrdenado` | Grupo con alumnos/docentes agregados en orden inverso | todos presentes, orden alfabético | Verifica el ordenamiento |

---

### Paso 4 — Estructura base de cada clase de test

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class XxxTest {

    private Xxx objeto;  // una variable por partición/borde relevante

    @BeforeEach
    void setUp() {
        // Se recrea antes de CADA @Test → independencia garantizada
        objeto = new Xxx(...);
    }

    @Test
    void nombreDelCaso() {
        // ejercitar
        objeto.metodo();
        // verificar
        assertEquals(esperado, objeto.getResultado());
    }
}
```