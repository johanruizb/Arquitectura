package avion;

public class Avion {
    public static void main(String[] args) {
        float DeltaTime = 0.09375f;
        float time = 0.0f;
        float Vx = 92.25f;
        float v = 0.0f;
        float a = 0.625f;
        float x = 0.0f;
        float xLimite = 6808.05f;
        int i = 0;

        while (x <= xLimite) {
            time += DeltaTime;
            v = (a * time);
            x = ((v * v) / (2 * a));
            i++;
            if (v >= Vx) {
                System.out.println("Despegue");
                System.out.println("Iteracion " + i);
                System.out.println("Distancia " + x);
                System.out.println("Tiempo " + time);

                break;
            }
        }
    }
}