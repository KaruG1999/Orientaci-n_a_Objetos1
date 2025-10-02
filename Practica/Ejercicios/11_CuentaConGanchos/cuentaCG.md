# Tarea A: 
Complete la implementación de las clases CajaDeAhorro y CuentaCorriente para que se puedan efectuar depósitos, extracciones y transferencias teniendo en cuenta los siguientes criterios. 

1) Las cajas de ahorro solo pueden extraer y transferir cuando cuentan con fondos suficientes. 
2)Las extracciones, los depósitos y las transferencias desde cajas de ahorro tienen un costo adicional de 2% del monto en cuestión (téngalo en cuenta antes de permitir una extracción o transferencia desde caja de ahorro).
3) Las cuentas corrientes pueden extraer aún cuando el saldo de la cuenta sea insuficiente. Sin embargo, no deben superar cierto límite por debajo del saldo. Dicho límite se conoce como límite de descubierto (algo así como el máximo saldo negativo permitido). Ese límite es diferente para cada cuenta (lo negocia el cliente con la gente del banco). 
4) Cuando se abre una cuenta corriente, su límite descubierto es 0 (no olvide definir el constructor por default).

# Tarea B: Reflexione, charle con el ayudante y responda a las siguientes preguntas.
a) ¿Por qué cree que este ejercicio se llama "Cuenta con ganchos"? 
b) En las implementaciones de los métodos extraer() y transferirACuenta()  que se ven en el diagrama, ¿quién es this? 
c) ¿Puede decir de qué clase es this?
d) ¿Por qué decidimos que los métodos puedeExtraer() y extraerSinControlar tengan visibilidad "protegido"?
e) ¿Se puede transferir de una caja de ahorro a una cuenta corriente y viceversa? ¿por qué? ¡Pruébelo!
¿Cómo se declara en Java un método abstracto? ¿Es obligatorio implementarlo? ¿Qué dice el compilador de Java si una subclase no implementa un método abstracto que hereda?

# Tarea C: Escriba los tests de unidad que crea necesarios para validar que su implementación funciona adecuadamente.
