package ar.edu.unlp.oo1;

public class JobDescription {

    private String name;
    private int priority;       // mayor número = mayor prioridad
    private int estimatedTime;  // duración estimada en minutos

    public JobDescription(String name, int priority, int estimatedTime) {
        this.name = name;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
    }

    public String getName() { return name; }
    public int getPriority() { return priority; }
    public int getEstimatedTime() { return estimatedTime; }
}
