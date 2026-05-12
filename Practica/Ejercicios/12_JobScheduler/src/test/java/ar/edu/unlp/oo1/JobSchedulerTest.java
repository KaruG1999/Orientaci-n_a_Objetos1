package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JobSchedulerTest {

    private JobDescription jobA;
    private JobDescription jobB;
    private JobDescription jobC;

    @BeforeEach
    void setUp() {
        // A: prioridad 1, tiempo 30 min
        // B: prioridad 3, tiempo 10 min
        // C: prioridad 2, tiempo 20 min
        // Orden llegada: A, B, C
        jobA = new JobDescription("A", 1, 30);
        jobB = new JobDescription("B", 3, 10);
        jobC = new JobDescription("C", 2, 20);
    }

    // --- FIFO: retorna en orden de llegada ---

    @Test
    void fifoRetornaElPrimeroQueEntro() {
        JobScheduler scheduler = new JobScheduler(new EstrategiaFIFO());
        scheduler.schedule(jobA);
        scheduler.schedule(jobB);
        scheduler.schedule(jobC);

        // TODO: verificar que next() retorna jobA (el primero en entrar)
        assertEquals(jobA, scheduler.next());
    }

    @Test
    void fifoEliminaElJobRetornado() {
        JobScheduler scheduler = new JobScheduler(new EstrategiaFIFO());
        scheduler.schedule(jobA);
        scheduler.schedule(jobB);

        scheduler.next(); // consume jobA
        // TODO: verificar que el siguiente ahora es jobB
        assertEquals(jobB, scheduler.next());
    }

    // --- Priority: retorna el de mayor prioridad ---

    @Test
    void priorityRetornaElDeMayorPrioridad() {
        JobScheduler scheduler = new JobScheduler(new EstrategiaPrioridad());
        scheduler.schedule(jobA);
        scheduler.schedule(jobB);
        scheduler.schedule(jobC);

        // B tiene prioridad 3 (la más alta)
        // TODO: verificar que next() retorna jobB
        assertEquals(jobB, scheduler.next());
    }

    @Test
    void priorityConUnSoloJobRetornaeseJob() {
        JobScheduler scheduler = new JobScheduler(new EstrategiaPrioridad());
        scheduler.schedule(jobA);

        assertEquals(jobA, scheduler.next());
    }

    // --- SJF: retorna el de menor tiempo estimado ---

    @Test
    void sjfRetornaElDeMenorTiempo() {
        JobScheduler scheduler = new JobScheduler(new EstrategiaSJF());
        scheduler.schedule(jobA);
        scheduler.schedule(jobB);
        scheduler.schedule(jobC);

        // B tiene 10 min (el menor)
        // TODO: verificar que next() retorna jobB
        assertEquals(jobB, scheduler.next());
    }

    @Test
    void sjfConUnSoloJobRetornaEseJob() {
        JobScheduler scheduler = new JobScheduler(new EstrategiaSJF());
        scheduler.schedule(jobC);

        assertEquals(jobC, scheduler.next());
    }
}
