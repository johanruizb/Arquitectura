/**
 * En este documento se realiza el desarrollo de la asignacion "EjercicioEnclase_7_Abril2021"
 * visto en clase. Emplearemos los temas de representacion en coma flotante de
 * 16bits desde el lenguaje Java usando float modificado para funcionar a 16 bits.
 * 
 * Nota: Ejecutado usando Java (TM) by Red Hat
 *
 * Codigo fuente: EjercicioEnclase_7_Abril2021.pdf
 *
 * Modificado por: Johan Ruiz - johan.andres.ruiz@correounivalle.edu.co
 *                 Ingrid Echeverri - ingrid.echeverri@correounivalle.edu.co
 * Fecha: 02/02/2022
 */

public class Avion {

    public static void main(String[] args) {
        Float_16 f = new Float_16();
        float DeltaTime = f.float16(0.09375f);
        float Vx = f.float16(92.25f);
        float a = f.float16(0.625f);
        float xLimite = f.float16(6808.05f);

        float time = 0.0f;
        float v = 0.0f;
        float x = 0.0f;
        float i = 0;

        while (x <= xLimite) {
            time = f.float16(DeltaTime + time);
            v = f.float16(a * time);
            x = f.float16(((v * v)) / (2 * a));
            i++;

            if (v >= Vx) {
                System.out.println("Tiempo: " + time);
                System.out.println("Iteraciones: " + i);
                System.out.println("Velocidad: " + v);
                System.out.println("Distancia: " + x);
                break;
            }
        }
    }

}