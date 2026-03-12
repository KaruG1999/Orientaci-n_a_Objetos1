# Trabajar con Java + Maven en VSCode

## Extensiones necesarias (instalar una vez)

Buscar e instalar en VSCode:
- **Extension Pack for Java** (Microsoft) — incluye todo: soporte Java, Maven, debugger y tests

---

## Abrir un ejercicio

Cada ejercicio es un proyecto Maven independiente. Hay dos formas de abrirlos:

**Opción A — Abrir la carpeta del ejercicio directamente:**
```
Archivo → Abrir carpeta → Redictado/Practica/Ejercicios/01_WallPost
```

**Opción B — Abrir `Redictado/` completo y navegar desde el explorador:**
```
Archivo → Abrir carpeta → Redictado/
```
VSCode detecta los `pom.xml` automáticamente y configura cada proyecto.

---

## Comandos desde la terminal

Abrir terminal en VSCode: `Ctrl + Ñ` (o `Ver → Terminal`)

Navegar al ejercicio:
```bash
cd Practica/Ejercicios/01_WallPost
```

### Compilar
```bash
mvn compile
```

### Correr los tests
```bash
mvn test
```
Muestra en la terminal cuántos tests pasaron, cuáles fallaron y el stack trace de los errores.

### Correr un test específico
```bash
mvn test -Dtest=WallPostTest
```

### Compilar + tests (todo junto)
```bash
mvn verify
```

### Limpiar archivos compilados (equivalente a "Clean" en Eclipse)
```bash
mvn clean
```

### Limpiar y volver a compilar desde cero
```bash
mvn clean compile
```

### Limpiar y correr tests desde cero
```bash
mvn clean test
```

---

## Flujo de trabajo típico (equivalente a Eclipse)

| En Eclipse | En VSCode + terminal |
|-----------|----------------------|
| Importar proyecto Maven | `cd` al ejercicio, VSCode lo detecta solo |
| Run → Run As → Java Application | `mvn exec:java` (si hay main) |
| Run → Run As → JUnit Test | `mvn test` |
| Run → Run As → JUnit Test (clase sola) | `mvn test -Dtest=NombreDeLaClaseTest` |
| Project → Clean | `mvn clean` |
| Ver errores de compilación | `mvn compile` (los imprime en terminal) |

---

## Ver resultados de tests con más detalle

Si un test falla, Maven muestra el error en la terminal. Para ver el reporte completo:
```bash
cat target/surefire-reports/NombreDeLaClaseTest.txt
```

---

## Atajos útiles en VSCode

| Acción | Atajo |
|--------|-------|
| Abrir terminal | `Ctrl + Ñ` |
| Buscar archivo | `Ctrl + P` |
| Buscar texto en todo el proyecto | `Ctrl + Shift + F` |
| Ir a definición de clase/método | `F12` |
| Renombrar variable/método | `F2` |
| Formatear código | `Ctrl + Shift + I` |
| Comentar/descomentar línea | `Ctrl + /` |
| Mover línea arriba/abajo | `Alt + ↑ / ↓` |

---

## Si Maven no está instalado

Verificar con:
```bash
mvn -version
```

Si no está instalado (Linux):
```bash
sudo apt install maven
```

---

## Notas

- Cada ejercicio tiene su propio `pom.xml` — no hay un `pom.xml` raíz que los agrupe. Navegar al ejercicio antes de correr `mvn`.
- El `target/` se genera automáticamente al compilar — está en el `.gitignore`, no se commitea.
- Si la extensión Java de VSCode muestra errores pero `mvn compile` funciona, hacer: `Ctrl + Shift + P` → "Java: Clean Language Server Workspace".
