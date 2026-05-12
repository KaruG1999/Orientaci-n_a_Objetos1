# Ejercicio 21 — Servicio de Envíos de Paquetes

## Enunciado

Una empresa de envíos ofrece tres tipos de servicio. Cada tipo tiene una fórmula de costo distinta.

### Tipos de envío

| Tipo | Costo base |
|------|-----------|
| **Local** | $1000 fijo. Si tiene entrega rápida: +$500 |
| **Interurbano** | Varía por distancia × gramos: `< 100 km → $20/g` \| `100–500 km → $25/g` \| `> 500 km → $30/g` |
| **Internacional** | $5000 fijo + `$10/g` si peso ≤ 1000g \| `$12/g` si peso > 1000g. Si tiene rápido: +$800 |

### Tipos de cliente

| Tipo | Descuento |
|------|-----------|
| **Persona física** | 10% de descuento sobre el costo base |
| **Cliente corporativo** | Sin descuento |

### Funcionalidad

- `cliente.agregarEnvio(envio)` — registra un envío.
- `cliente.montoEnPeriodo(from, to)` — suma el costo de los envíos despachados entre `from` y `to` (inclusive), aplicando el descuento del cliente.

---

## Objetivo de aprendizaje

Aplicar **polimorfismo** para calcular costos sin usar `instanceof` ni `if` sobre el tipo de envío, y diseñar **tests con particiones equivalentes** para los valores de borde (100 km, 500 km, 1000 g).

---

## Concepto OOP: polimorfismo + Template Method liviano

La clase abstracta `Envio` define el algoritmo de cálculo:

```java
// En Envio (abstracta):
public double calcularCosto(Cliente cliente) {
    double base = calcularCostoBase();   // ← cada subclase implementa esto
    return cliente.aplicarDescuento(base);
}

// EnvioLocal sobreescribe solo calcularCostoBase():
public double calcularCostoBase() {
    return 1000 + (entregaRapida ? 500 : 0);
}
```

Y `Cliente` es abstracta para el descuento:
```java
// PersonaFisica:
public double aplicarDescuento(double monto) { return monto * 0.90; }

// ClienteCorporativo:
public double aplicarDescuento(double monto) { return monto; }
```

Así `Envio.calcularCosto(cliente)` llama a `cliente.aplicarDescuento(...)` sin saber si el cliente es persona física o corporativo. **Doble dispatch** sin escribir ni un solo `instanceof`.

---

## Particiones equivalentes de EnvioInterurbano

La distancia tiene **3 zonas** y **2 bordes exactos**:

```
│◄── $20/g ──►│◄──────── $25/g ────────►│◄── $30/g ──►│
0            100                        500           ∞
             ↑ borde                    ↑ borde
          (¿20 o 25?)               (¿25 o 30?)
```

- `distancia < 100` → $20/g
- `distancia entre 100 y 500 (inclusive)` → $25/g
- `distancia > 500` → $30/g

Con exactamente 100 km → $25/g (segunda franja)  
Con exactamente 500 km → $25/g (todavía segunda franja)  
Con 501 km → $30/g (tercera franja)

---

## Por dónde empezar — guía paso a paso

### Paso 1: `EnvioLocal` — el más simple

```java
public double calcularCostoBase() {
    return 1000 + (entregaRapida ? 500 : 0);
}
```

### Paso 2: `EnvioInterurbano` — cuidado con los bordes

```java
public double calcularCostoBase() {
    double tarifaPorGramo;
    if (distanciaKm < 100) {
        tarifaPorGramo = 20;
    } else if (distanciaKm <= 500) {
        tarifaPorGramo = 25;
    } else {
        tarifaPorGramo = 30;
    }
    return tarifaPorGramo * getPesoEnGramos();
}
```

### Paso 3: `EnvioInternacional`

```java
public double calcularCostoBase() {
    double tarifaPorGramo = getPesoEnGramos() <= 1000 ? 10 : 12;
    return 5000
        + tarifaPorGramo * getPesoEnGramos()
        + (entregaRapida ? 800 : 0);
}
```

### Paso 4: `PersonaFisica` y `ClienteCorporativo`

```java
// PersonaFisica:
public double aplicarDescuento(double monto) { return monto * 0.90; }

// ClienteCorporativo:
public double aplicarDescuento(double monto) { return monto; }
```

### Paso 5: `Cliente.montoEnPeriodo()`

```java
public double montoEnPeriodo(LocalDate from, LocalDate to) {
    return envios.stream()
        .filter(e -> !e.getFechaDespacho().isBefore(from)
                  && !e.getFechaDespacho().isAfter(to))
        .mapToDouble(e -> e.calcularCosto(this))
        .sum();
}
```

---

## Correr los tests

```bash
cd Practica/Ejercicios/21_ServicioEnvios
mvn test
```

Los tests cubren:
- Envío local estándar y rápido
- Interurbano en cada franja + los 2 bordes exactos (100 km y 500 km)
- Internacional con ≤ 1 kg y > 1 kg, con y sin rápido
- Descuento persona física vs corporativo
- `montoEnPeriodo()` con envíos dentro y fuera del período, y en los bordes

---

## Preguntas para reflexionar

1. ¿Qué pasaría si `calcularCosto()` estuviera en `Cliente` con un `if (envio instanceof EnvioLocal)...`? ¿Qué problema tendría?
2. Si el borde de 100 km fuera `distanciaKm <= 100` en vez de `distanciaKm < 100`, ¿qué test fallaría?
3. `calcularCosto(cliente)` está en `Envio`. ¿Por qué no está en `Cliente`?
4. ¿Podrías aplicar Strategy acá para el cuadro tarifario? ¿Qué quedaría en una clase `CuadroTarifario`?
