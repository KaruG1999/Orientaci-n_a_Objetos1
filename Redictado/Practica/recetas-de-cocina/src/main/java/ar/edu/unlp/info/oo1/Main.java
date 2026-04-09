package ar.edu.unlp.info.oo1;

public class Main {

    public static void main(String[] args) {
        // Ejemplo: "Bowl tibio de pollo"
        Receta bowl = new Receta("Bowl tibio de pollo");

        bowl.agregarComponente(new Base("arroz", 2, true));
        bowl.agregarComponente(new Condimento("mix provenzal", 3, false));
        bowl.agregarComponente(new Proteina("pollo", "cubos", 2, 2200.0));

        System.out.println(bowl.getDescripcion());
        System.out.println("\nCosto estimado: $" + bowl.getCostoEstimado());
    }
}
