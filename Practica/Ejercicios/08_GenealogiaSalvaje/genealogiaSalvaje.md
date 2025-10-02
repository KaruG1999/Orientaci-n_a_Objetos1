**Ejercicio 8: Genealogía salvaje**

En una reserva de vida salvaje (como la estación de cría ECAS, en el camino Centenario), los cuidadores quieren llevar registro detallado de los animales que cuidan y sus familias. Para ello nos han pedido ayuda. Debemos: 
Tareas:
a) Complete el diseño e implemente
Modelar una solución en objetos e implementar la clase Mamífero (como subclase de Object). El siguiente diagrama de clases (incompleto) nos da una idea de los mensajes que un mamífero entiende. 
Proponga una solución para el método *tieneComoAncestroA(...) y deje la implementación para el final y discuta su solución con el ayudante*. 
Complete el diagrama de clases para reflejar los atributos y relaciones requeridas en su solución. 

class Mamifero {
  - identificador : String
  - especie : String
  - fechaNacimiento : Date
  - padre : Mamifero
  - madre : Mamifero

  + getIdentificador() : String
  + setIdentificador(id : String) : void
  + getEspecie() : String
  + setEspecie(especie : String) : void
  + getFechaNacimiento() : Date
  + setFechaNacimiento(fecha : Date) : void
  + getPadre() : Mamifero
  + setPadre(padre : Mamifero) : void
  + getMadre() : Mamifero
  + setMadre(madre : Mamifero) : void
  + getAbuelaMaterna() : Mamifero
  + getAbueloMaterno() : Mamifero
  + getAbuelaPaterna() : Mamifero
  + getAbueloPaterno() : Mamifero
  + tieneComoAncestroA(unMamifero : Mamifero) : Boolean
}

- Solo considero como variables padre y madre ya que ellos a su vez tienen padre y madre (que son abuelos) y puedo acceder a ellos con los mismos getMadre() y getPadre().
Como gráficamente es un árbol puedo implementar una estructura recursiva compuesta por los mamiferos para el método tieneComoAncestro(); y evaluo 3 posibilidades
-> Que no tenga padres
-> Que sus unicos ancestros sean su madre y padre
-> Que vaya subiendo recursivamente consultando con sus abuelos

b) Implementando el test lo único que restaba era agregar el constructor para el anonimo no contemplado.