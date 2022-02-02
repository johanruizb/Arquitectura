
public class Estadio {

    public static void main(String[] args) {
        float pagoxingreso = 0.1718750f;
        float DineroAcumulado = 0;
        int CapacidadEstadio = 48712;
        int numeroMaxAsistentes = (50 * CapacidadEstadio); // 2435600
        int ConteoAsistentes = 0;

        float temporada = 0.0f;

        System.out.println("=============================");

        float delta = 0;
        while (true) {
            ConteoAsistentes++;
            DineroAcumulado += pagoxingreso;

            ConteoAsistentes++;
            DineroAcumulado = DineroAcumulado + pagoxingreso;

            if (delta == DineroAcumulado) {
                temporada = (ConteoAsistentes / numeroMaxAsistentes);

                System.out.println("Temporada " + temporada);
                System.out.println("Girar cheque por: " + DineroAcumulado);
                System.out.println("Pago que deberia haber sido: " + (ConteoAsistentes * pagoxingreso));
                System.out.println("Asistentes " + ConteoAsistentes);
                break;
            }

            delta = DineroAcumulado;

            /*
             * delta = (pagoxingreso + DineroAcumulado) - DineroAcumulado;
             * 
             * if (delta == 0) {
             * temporada = (ConteoAsistentes / numeroMaxAsistentes);
             * 
             * System.out.println("Temporada " + temporada);
             * System.out.println("Girar cheque por: " + DineroAcumulado);
             * System.out
             * .println("Pago que deberia haber sido: " + (ConteoAsistentes *
             * pagoxingreso));
             * System.out.println("Asistentes " + ConteoAsistentes);
             * System.out.printf("Delta %f", delta);
             * System.out.println();
             * break;
             */
        }

        System.out.println("=============================");
    }
}
