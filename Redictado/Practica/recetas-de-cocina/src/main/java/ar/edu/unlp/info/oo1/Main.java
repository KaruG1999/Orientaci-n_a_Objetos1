package ar.edu.unlp.info.oo1;

public class Main {

    public static void main(String[] args) {
        // Ejemplo: "Bowl tibio de pollo"
        Receta bowl = new Receta("Bowl tibio de pollo");

        bowl.agregarComponente(new BaseIntegral("arroz", 2));
        bowl.agregarComponente(new CondimentoNoPicante("mix provenzal", 3));
        bowl.agregarComponente(new Proteina("pollo", "cubos", 2, 2200.0));

        System.out.println(bowl.getDescripcion());
        System.out.println("\nCosto estimado: $" + bowl.getCostoEstimado());
    }
}
