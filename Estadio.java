/**
 * En este documento se realiza el desarrollo de la asignacion "Estadio PSG"
 * visto en clase. Emplearemos los temas de representacion en coma flotante de
 * 32bits desde el lenguaje Java usando float.
 * 
 * Nota: Ejecutado usando Java (TM) by Red Hat
 *
 * Codigo fuente: Estadio PSG.pdf
 *
 * Modificado por: Johan Ruiz - johan.andres.ruiz@correounivalle.edu.co
 *                 Ingrid Echeverri - ingrid.echeverri@correounivalle.edu.co
 * Fecha: 02/02/2022
 */

public class Estadio {

    /**
     * Funcion principal que se encarga de realizar los calculos como se requeria en
     * la asignacion para su posterior analisis
     * 
     * @param args
     */
    public static void main(String[] args) {

        float pagoxingreso = 0.171875f;
        float dineroAcumulado = 0;
        float temporada = 0.0f;
        float dineroAnterior = 0;

        int capacidadEstadio = 48712;
        int numeroMaxAsistentes = (50 * capacidadEstadio); // 2435600
        int conteoAsistentes = 0;

        System.out.println("=============================");
        System.out.println("");

        // Itera por siempre hasta que deje de sumarse dinero a la variable
        // dineroAcumulado
        while (true) {
            // Inicia iteracion

            conteoAsistentes++;
            dineroAcumulado = dineroAcumulado + pagoxingreso;

            // Si dinero acumulado es igual al dinero de la iteracion anterior
            if (dineroAnterior == dineroAcumulado) {
                temporada = (conteoAsistentes / numeroMaxAsistentes);

                System.out.printf("Delta %f\n", (((pagoxingreso + dineroAcumulado) - dineroAcumulado)));
                System.out.printf("Temporada %.2f\n", temporada);
                System.out.printf("Asistentes totales %d\n", conteoAsistentes);
                System.out.printf("Se debe girar cheque por: %f\n", dineroAcumulado);
                System.out.printf("Deberia haber sido por: %f\n", (conteoAsistentes * pagoxingreso));
                break;
            }

            // Se actualiza el valor del dinero anterior para la siguiente iteracion
            dineroAnterior = dineroAcumulado;

            // Finaliza iteracion
        }

        System.out.println("");
        System.out.println("=============================");
    }
}
