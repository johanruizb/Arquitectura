import java.util.ArrayList;
import java.util.Random;

public class BinaryRound {

    public static void main(String[] args) {
        ArrayList<Integer> bin16 = new ArrayList<>(16);
        Random random = new Random();

        int bin = random.nextInt(1);

        for (int i = 0; i < 16; i++) {
            bin16.add(bin);
            bin = random.nextInt(2);
        }

        System.out.println("==========================");
        System.out.println("bin16 normal: \t " + bin16);
        System.out.println("\t\t+[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1]");
        System.out.println("bin16 round: \t " + binaryRound(bin16));
        System.out.println("==========================");

    }

    /**
     * Aproxima un binario sumandole +1 (en binario)
     * 
     * @param num
     */
    private static ArrayList<Integer> binaryRound(ArrayList<Integer> num) {
        // Esta en true cuando llevas un +1 (no hace falta pasarla de nuevo a false
        // porque cuando se termine de llevar finaliza la suma)
        boolean carry = false;

        for (int i = num.size() - 1; i > 0; i--) {
            // Si 0 + 1 y no lleva, suma y sale.
            if (num.get(i) == 0 && !carry) {
                num.set(i, 1);
                break;

                // Si 1 + 1, suma, pone 0 y lleva en bandera (1).
            } else if (num.get(i) == 1) {
                num.set(i, 0);
                carry = true;

                // Si 0 + 1 y lleva, suma y se sale.
            } else if (num.get(i) == 0 && carry) {
                num.set(i, 1);
                break;
            }
        }

        return num;
    }

}
