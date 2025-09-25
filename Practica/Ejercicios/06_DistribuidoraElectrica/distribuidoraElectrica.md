**Proceso visto en clase paso a paso:**
1) Identificar clases conceptuales usando categorías y frases nominales
2) Listar conceptos candidatos
3) Crear diagrama UML con las clases seleccionadas
4) Definir asociaciones entre clases (evitar atributos como claves)
5) Implementar en Java respetando la herencia y asociaciones
6) Iterar y refinar el modelo según necesidades

notas: 

ATRIBUTOS
- Usuario -> Nombre / Domicilio
- Consumo: 
- Consumo de energia activa -> costo Asociado: cantKWh/Hora 
- Consumo de energia reactiva -> Bonificación: kVarh
- fecha 
- Cuadro Tarifario -> precio KWh 
- Factura -> Usuario / FechaEmision / Bonificacion / MontoFinal

MÉTODOS
- Factura -> MontoFinal(): Costo de consumo - Bonificacion
- CostoConsumo() : Consumo EAct * precio Kwh (cuadro)
- FactorDePotencia() : si es mayor a 0.8 -> bonificación de 10% 

1) CONCEPTOS CANDIDATOS

- Consumo
- Usuario
- Factura 
- Consumo de energia activa
- Consumo de energia reactiva
- Cuadro Tarifario 
- Domicilio
- Bonificacion
- Factor de potencial estimado (fpe)

2) CLASIFICACIÓN DE CATEGORIAS 

| Concepto           | Categoría del apunte                     |
| ------------------ | ---------------------------------------- |
| Usuario            | Rol de la gente / Contenedor de cosas    |
| Domicilio          | Lugar                                    |
| Consumo            | Transacción (hecho registrado)           |
| Energía activa     | Registro financiero (impacta en costo)   |
| Energía reactiva   | Hecho técnico (dato, no financiero)      |
| Cuadro tarifario   | Reglas y políticas                       |
| Factura            | Registro financiero (documento de cobro) |
| Bonificación       | Regla / Política (condición sobre fpe)   |
| Factor de potencia | Hecho calculado (derivado de consumos)   |


3) MODELO DE DOMINIO UML

                 +------------------+
                 |     Usuario      |
                 +------------------+
                 | -nombre : String |
                 | -domicilio : String |
                 +------------------+
                         1
                         |
                         | 1
                         v
                 +------------------+
                 |     Consumo      |
                 +------------------+
                 | -energiaActiva : Real |
                 | -energiaReactiva : Real|
                 | -fecha : Date          |
                 | +calcularFPE() : Real  |
                 +------------------+
                         |
                         | 1
                         v
                 +------------------+
                 |     Factura      |
                 +------------------+
                 | -fechaEmision : Date   |
                 | -bonificacion : Real   |
                 | -montoFinal : Real     |
                 +------------------+
                 | +calcularMonto() : Real|
                 +------------------+
                         |
                         | usa
                         v
                 +------------------+
                 | CuadroTarifario  |
                 +------------------+
                 | -precioKWh : Real|
                 +------------------+

4) INCORPORACION DE ATRIBUTOS A UML

- Usuario: nombre, domicilio.
- Consumo: energíaActiva (kWh), energíaReactiva (kVArh), fecha.
- Factura: fechaEmision, bonificacion, montoFinal.
- CuadroTarifario: precioKWh.

5) ASOCIACIONES CON CATEGORIAS 

- Usuario - Consumo -> A maneja a B 
- Consumo - Factura -> A esta relacionado con la transacción de B
- Factura - CuadroTarifario -> A usa a B    


