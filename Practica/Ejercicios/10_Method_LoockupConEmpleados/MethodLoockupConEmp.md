# Ejercicio 10: Method Lookup con Empleados

        +------------------+
        |    Empleado      |
        +------------------+
        | - nombre: String |
        +------------------+
        | +aportes(): Real |
        | +montoBasico():Real |
        | +sueldoBasico():Real|
        +------------------+
               ^
               |
               |
     +----------------------+
     | EmpleadoJerarquico   |
     +----------------------+
     |                      |
     +----------------------+
     | +sueldoBasico(): Real |
     | +montoBasico(): Real  |
     | +bonoPorCategoria():Real|
     +----------------------+
               ^
               |
               |
          +---------+
          | Gerente |
          +---------+
          |         |
          +---------+
          | +aportes(): Real |
          | +montoBasico(): Real|
          +---------+


## 1. Pasos para resolver
1. **Identificar la clase concreta** del objeto instanciado.
   - En los fragmentos de código, el objeto creado es de tipo `Gerente`.

2. **Localizar el método invocado** en la jerarquía de clases.
   - Si está definido en `Gerente`, se ejecuta esa versión.
   - Si no está en `Gerente`, se busca en `EmpleadoJerarquico`.
   - Si no está en `EmpleadoJerarquico`, se busca en `Empleado`.

3. **Verificar si hay sobreescritura (`override`)**.
   - Cuando un método redefine otro, prevalece la implementación de la subclase.
   - Si la redefinición usa `super`, también se invoca la versión de la superclase.

4. **Seguir la cadena de llamadas** hasta que no haya más `super` ni delegaciones.

---

# Method Lookup para los fragmentos de código

#*Fragmento 1:*

Gerente alan = new Gerente("Alan Turing");
double aportesDeAlan = alan.aportes();

alan.aportes() → Gerente.aportes()
Dentro de Gerente.aportes(), se llama a this.montoBasico() → Gerente.montoBasico()

Métodos ejecutados:

- Gerente.aportes()
- Gerente.montoBasico()
- Valor de aportesDeAlan:

aportes = this.montoBasico() * 0.05
montoBasico() returns 57000
aportes = 57000 * 0.05 = 2850

✅ aportesDeAlan = 2850

# *Fragmento 2:*

Gerente alan = new Gerente("Alan Turing");
double sueldoBasicoDeAlan = alan.sueldoBasico();


alan.sueldoBasico() → EmpleadoJerarquico.sueldoBasico()
Dentro de EmpleadoJerarquico.sueldoBasico(): super.sueldoBasico() → Empleado.sueldoBasico(), luego suma this.bonoPorCategoria() → EmpleadoJerarquico.bonoPorCategoria()

Métodos ejecutados:
- EmpleadoJerarquico.sueldoBasico()
- Empleado.sueldoBasico()
- EmpleadoJerarquico.bonoPorCategoria()

Valor de sueldoBasicoDeAlan:

Empleado.sueldoBasico() → 35000
EmpleadoJerarquico.bonoPorCategoria() → 10000
sueldoBasicoDeAlan = 35000 + 10000 = 45000

✅ sueldoBasicoDeAlan = 45000

------------------------------------------------------

## Resolución


# Method Lookup en la jerarquía Empleado

## Fragmento 1
```java
Gerente alan = new Gerente("Alan Turing");
double aportesDeAlan = alan.aportes();
Métodos ejecutados
1) aportes() → Gerente
29 montoBasico() → Gerente


montoBasico() = 57000  
aportes() = 57000 * 0.05 = 2850
Valor final:
	aportesDeAlan = 2850;

Fragmento 2


Gerente alan = new Gerente("Alan Turing");
double sueldoBasicoDeAlan = alan.sueldoBasico();
Métodos ejecutados
1) sueldoBasico() → EmpleadoJerarquico
2) super.sueldoBasico() → Empleado
3) montoBasico() → Gerente
4) aportes() → Gerente
5) montoBasico() → Gerente (dentro de aportes())
6) bonoPorCategoria() → EmpleadoJerarquico

montoBasico() = 57000  
aportes() = 57000 * 0.05 = 2850  
super.sueldoBasico() = 57000 + 2850 = 59850  
bonoPorCategoria() = 8000  
sueldoBasico() = 59850 + 8000 = 67850
Valor final:
	sueldoBasicoDeAlan = 67850;



