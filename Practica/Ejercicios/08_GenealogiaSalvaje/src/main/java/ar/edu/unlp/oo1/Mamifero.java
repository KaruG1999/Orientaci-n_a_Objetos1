package ar.edu.unlp.oo1;

import java.util.Date;

public class Mamifero {
	
	private String identificador;
	private String especie;
	private Date fechaNacimiento;
	private Mamifero padre;
	private Mamifero madre;
	
	// Constructor usado en test
	public Mamifero(String identificador) {
		this.setIdentificador(identificador);
	}
	
	// Constructor para anonimo
	public Mamifero() {
		this.identificador = "anonimo";
	}
	
	// Deberia sumar constr con todos los campos?

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getEspecie() {
		return this.especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Mamifero getPadre() {
		return this.padre;
	}

	public void setPadre(Mamifero padre) {
		this.padre = padre;
	}

	public Mamifero getMadre() {
		return this.madre;
	}

	public void setMadre(Mamifero madre) {
		this.madre = madre;
	}
	
	 // Métodos derivados de abuelos --> No es necesario que sean Atributos (ej: madre.getMadre())
	
    public Mamifero getAbuelaMaterna() {
        return (madre != null) ? madre.getMadre() : null; //para evitar errores puedo antes si tiene abuelo/abuela
    }

    public Mamifero getAbueloMaterno() {
        return (madre != null) ? madre.getPadre() : null;
    }

    public Mamifero getAbuelaPaterna() {
        return (padre != null) ? padre.getMadre() : null;
    }

    public Mamifero getAbueloPaterno() {
        return (padre != null) ? padre.getPadre() : null;
    }
	
    // Método verificar ancestros --> Usar recursividad para "subir" en el árbol
    
    public boolean tieneComoAncestroA(Mamifero unMamifero) {
    	
        if (unMamifero == null) return false;

        // Verificar padre o madre inmediatos
        if (this.padre == unMamifero || this.madre == unMamifero) {
            return true;
        }

        // Verificar recursivamente en el árbol genealógico
        boolean tienePorPadre = (padre != null) && padre.tieneComoAncestroA(unMamifero);
        boolean tienePorMadre = (madre != null) && madre.tieneComoAncestroA(unMamifero);

        return tienePorPadre || tienePorMadre; //devuelve falso si ambos son falso
    }
}
