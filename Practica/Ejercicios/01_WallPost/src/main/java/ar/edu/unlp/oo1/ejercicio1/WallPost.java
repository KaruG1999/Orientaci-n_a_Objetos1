package ar.edu.unlp.oo1.ejercicio1;

/**
 * Completar esta clase de acuerdo a lo especificado
 */
public class WallPost {

    private String texto;
    private int cantidadLikes;
    private boolean estaDestacado;

    // constructor 
    public WallPost() {
        this.cantidadLikes = 0;
        this.texto = "Undefined post";
        this.estaDestacado = false; 
    }

    // Retorna el texto descriptivo de la publicación
    public String getText() {
        return this.texto;
    }

    // Setea el texto descriptivo de la publicación
    public void setText(String text) {
        this.texto = text;
    }

    // Retorna la cantidad de “me gusta”   (!! consultar si va o no public)
    int getLikes() {
        return this.cantidadLikes;
    }

    // Incrementa la cantidad de likes en uno
    public void like() {
        this.cantidadLikes++;
    }

    // Decrementa la cantidad de likes en uno. Si ya es 0, no hace nada
    public void dislike() {
        if (this.cantidadLikes != 0)
            this.cantidadLikes--;  
    }

    // Retorna true si el post está marcado como destacado
    public boolean isFeatured() {
        return this.estaDestacado;
    }

    // Cambia el post del estado destacado a no destacado y viceversa
    public void toggleFeatured() {
        this.estaDestacado = !this.estaDestacado;
    }

    // Para mostrar una instancia de WallPost de forma adecuada
    @Override
    public String toString() {
        return "WallPost {" +
            "text: " + getText() +
            ", likes: " + getLikes() +
            ", featured: " + isFeatured() +
            "}";
    }
}
