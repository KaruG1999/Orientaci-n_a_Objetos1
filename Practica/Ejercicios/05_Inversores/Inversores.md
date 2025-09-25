**Proceso visto en clase paso a paso:**
1) Identificar clases conceptuales usando categorías y frases nominales
2) Listar conceptos candidatos
3) Crear diagrama UML con las clases seleccionadas
4) Definir asociaciones entre clases (evitar atributos como claves)
5) Implementar en Java respetando la herencia y asociaciones
6) Iterar y refinar el modelo según necesidades

1) IDENTIFICAR CLASES CONCEPTUALES USANDO CATEGORIAS Y FRASES NOMINALES 

CLASES 
- Inversor 
- Inversion
- Dos tipos Inversion (CLASES ABSTRACTAS DE INVERSION): En accion / En Plazo Fijo

ATRIBUTOS
- Acciones (nombre,valorunitario)
- Inversiones en Acciones (nombre, cantidad, valor unitario)
- Plazos fijos (fecha, montoDepositado, porcentajeInteresGenerado)
- Inversor -> Inversiones realizada (lista)


METODOS MENCIONADOS
- Inversion -> Valor Actual (Abstracto)
- Inversiones en accion: valorActual(valorUnit*cantAcciones) 
- Plazos fijos : valorActual (valorInicial + InteresesDiarios(cantDias))
- Inversor -> Valor de Inversion actual de Inv (suma valores actuales que posee)
- Inversor -> AgregarInversion() / SacarInversion() .. tanto Acc como PF


2) LISTAR CONCEPTOS CANDIDATOS  -> Categorias de clase conceptual

| Concepto            | Categoría de clase conceptual         |
| ------------------- | ------------------------------------- |
| Inversor            | Rol de la gente / Contenedor de cosas |
| Inversión           | Transacción (genérica)                |
| Acción              | Transacción / Registro financiero     |
| PlazoFijo           | Transacción / Registro financiero     |
| Nombre de la acción | Especificación de una cosa            |


3) GRAFICAR USANDO MODELO DE DOMINIO (UML)

                +-------------------+
                |    Inversor       |
                +-------------------+
                | -nombre : String  |
                +-------------------+
                | +agregarInversion(inv: Inversion) |
                | +sacarInversion(inv: Inversion)   |
                | +valorActualTotal() : Real        |
                +-------------------+
                         1
                         |
                         | 0..*
                         v
                +-------------------+
                |   Inversion       |<<abstract>>
                +-------------------+
                | +valorActual() : Real |
                +-------------------+
                   ^             ^
                   |             |
        +----------------+   +--------------------+
        |     Accion     |   |    PlazoFijo       |
        +----------------+   +--------------------+
        | -nombre : String  | -fechaConstitucion : Date |
        | -cantidad : Int   | -montoDepositado : Real  |
        | -valorUnitario:Real| -porcentajeInteres:Real |
        +----------------+   +--------------------+
        | +valorActual() |   | +valorActual()     |
        +----------------+   +--------------------+

4) ASOCIACIONES ENTRE CONCEPTOS 

- *Inversor* tiene -> 0..* -> *Inversiones* (A contiene lógicamente a B)
- Herencia de *Inversión*: *Acción* y *Plazo Fijo*  (?)

