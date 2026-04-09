# Revisión UML — Recetas de cocina

## Lo que está bien

- La jerarquía es correcta: `Componente` como supertipo con `Base`, `Condimento` y `Proteina` como subtipos.
- `RecetaCocina` tiene la relación de composición con `List<Componente>` — correcto.
- `getDescripcion()` y `getCostoEstimado()` están declarados en `Componente` — correcto, eso habilita el polimorfismo.
- Los atributos de cada clase coinciden con el enunciado.

---

## Problemas a corregir

### 1. `Componente` como `<<interface>>` pero el código la tiene como clase abstracta

En el diagrama aparece `<<interface>> Componente`, pero el código tiene `abstract class Componente`. Son dos cosas distintas:
- Una **interfaz** no puede tener atributos de instancia ni constructores.
- Una **clase abstracta** sí puede.

Como `Componente` no tiene atributos en este diseño, una interfaz es perfectamente válida. Pero UML y código deben ser consistentes. Hay que elegir uno y alinear el otro.

### 2. Las flechas de las subclases hacia `Componente` deberían ser de realización

Si `Componente` es una interfaz, `Base`, `Condimento` y `Proteina` la *implementan* → la flecha debe ser **punteada con punta abierta** (realización). Si fuera clase abstracta, sería **sólida con punta abierta** (herencia). En el diagrama se usaron flechas sólidas, lo cual es incorrecto para una interfaz.

### 3. Nombre inconsistente: `RecetaCocina` vs `Receta`

En el UML la clase se llama `RecetaCocina`, pero en el código se llama `Receta`. Hay que unificar. El enunciado no especifica nombre de clase, así que cualquiera sirve — pero deben coincidir.

### 4. `getComponentes()` en `RecetaCocina` es innecesario

El diagrama muestra un método `getComponentes(): List<Componente>`, pero el enunciado no lo pide y el código no lo usa. No agregar métodos que no respondan a ningún requerimiento.

---

## Resumen

| Item | Estado |
|---|---|
| Jerarquía de clases | Correcto |
| Atributos de cada clase | Correcto |
| Relación Receta → Componente | Correcto |
| Tipo de `Componente` (interface vs abstract) | Inconsistente con el código |
| Tipo de flecha subclases → Componente | Incorrecto si es interface |
| Nombre `RecetaCocina` vs `Receta` | Inconsistente con el código |
