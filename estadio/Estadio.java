package estadio;

public class Estadio {
    public static void main(String[] args) {
        float pagoxingreso = 0.01718750f;
        int CapacidadEstadio = 48712;
        float DineroAcumulado = 0;
        int numeroMaxAsistentes = (50 * CapacidadEstadio); // 2435600
        int ConteoAsistentes = 0;

        while (ConteoAsistentes < numeroMaxAsistentes) {
            ConteoAsistentes++;
            DineroAcumulado += pagoxingreso;
        }
        System.out.println("");
        System.out.println("=============================");
        //System.out.println("Iteracion " + ConteoAsistentes);
        System.out.println("Girar cheque por: " + DineroAcumulado);
        System.out.println("Pago que deberia haber sido: " + numeroMaxAsistentes * pagoxingreso);
        System.out.println("=============================");
        System.out.println("");

    }
}
