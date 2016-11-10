package m9practica1u42;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class M9P142 extends RecursiveTask<Double> {

    private final double[] array;
    private final int inici, fin;

    public M9P142(double[] array, int inici, int fin) {
        this.array = array;
        this.inici = inici;
        this.fin = fin;
    }

    public static void main(String[] args) {
        double[] temperatures = {
            13.0, 13.2, 13.3, 13.4, //00:00 h.
            13.5, 13.7, 13.8, 13.9, //01:00 h.
            14.1, 14.2, 14.3, 14.4, //02:00 h.
            14.6, 14.7, 14.8, 14.9, //03:00 h.
            15.0, 15.2, 15.3, 15.4, //04:00 h.
            15.5, 15.7, 15.8, 15.9, //05:00 h.
            16.1, 16.2, 16.3, 16.4, //06:00 h.
            16.6, 16.7, 16.8, 16.9, //07:00 h.
            17.0, 17.2, 17.3, 17.4, //08:00 h.
            17.5, 17.7, 17.8, 17.9, //09:00 h.
            18.1, 18.2, 18.3, 18.4, //10:00 h.
            18.6, 18.7, 18.8, 18.9, //11:00 h.
            18.0, 18.2, 18.3, 18.4, //12:00 h.
            18.5, 18.7, 18.8, 18.9, //13:00 h.
            17.1, 17.2, 17.3, 17.4, //14:00 h.
            17.6, 17.7, 17.8, 17.9, //15:00 h.
        };
        int procesos = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(procesos);
        M9P142 tasca = new M9P142(temperatures, 0, temperatures.length - 1);
        double resultat = pool.invoke(tasca);
        System.out.println("La temperatura mitja mes baixa es: " + resultat);
    }

    @Override
    protected Double compute() {
        if (fin - inici > 4) {
            //Dividim les tasques en tasques més petites per agilitzar la tasca.           
            int meitat = inici + (fin - inici) / 2;
            M9P142 forkJoin1 = new M9P142(array, inici, meitat);
            M9P142 forkJoin2 = new M9P142(array, meitat + 1, fin);
            invokeAll(forkJoin1, forkJoin2);
            return Math.min(forkJoin1.join(), forkJoin2.join());
        } else {
            double temperatura = 0;
            //Sumem les temperatures amb un for per recorrer les 4 temperatures de l'array.
            for (int i = inici; i <= fin; i++) {
                temperatura += array[i];
            }
            //Fem la mitjana de cada suma de les temperatures.
            temperatura /= 4;
            return temperatura;
        }
    }
}
