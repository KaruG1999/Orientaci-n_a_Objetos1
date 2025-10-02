package ar.edu.unlp.info.oo1;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Inversor inversor = new Inversor();

        Accion apple = new Accion("Apple", 10, 180.5);
        Accion tesla = new Accion("Tesla", 5, 250.0);
        PlazoFijo pf = new PlazoFijo(LocalDate.of(2025, 9, 1), 10000, 0.2);

        inversor.agregarInversion(apple);
        inversor.agregarInversion(tesla);
        inversor.agregarInversion(pf);

        System.out.println("Valor total de inversiones: " + inversor.valorActualTotal());
    }
}

