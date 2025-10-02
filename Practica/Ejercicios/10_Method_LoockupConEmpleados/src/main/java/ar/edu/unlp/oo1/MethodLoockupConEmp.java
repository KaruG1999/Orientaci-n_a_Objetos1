package ar.edu.unlp.oo1;

public class MethodLoockupConEmp {
	
	public static void main(String[] args) {

		Gerente alan = new Gerente("Alan Turing");
		double aportesDeAlan = alan.aportes();

		System.out.println(aportesDeAlan);
		
		
		Gerente alan2 = new Gerente("Alan Turing");
		double sueldoBasicoDeAlan = alan2.sueldoBasico();
		
		System.out.println(sueldoBasicoDeAlan);
		
		
    }

}
