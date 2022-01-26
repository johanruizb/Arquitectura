package problemas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class Avion {
    static float DeltaTime = Float_16(0.09375f);
    static float Vx = Float_16(92.25f);
    static float a = Float_16(0.625f);
    static float xLimite = Float_16(6808.05f);
    // 2^(5-1)-1
    static int sesgo = (15);
    static float time = (0.0f);
    static float v = (0.0f);
    static float x = (0.0f);
    static float i = (0.0f);

    public static void main(String[] args) {
        while (x <= xLimite) {
            time = Float_16(DeltaTime + time);
            v = Float_16(a * time);
            x = Float_16((v * v) / (2 * a));
            i++;
            System.out.println("Deltatime " + v);

            if (v >= Vx) {
                System.out.println("");
                System.out.println("=============================");
                System.out.println("Despegue");
                System.out.println("Iteracion " + i);
                System.out.println("Distancia " + x);
                System.out.println("Tiempo " + time + " sg");
                System.out.println("=============================");
                System.out.println("");

                break;
            }
        }
    }

    /**
     * Pasar un numero flotante de 32b bits a el numero binario de 16 bits
     * 
     * @param num
     */
    public static ArrayList<Integer> Binary16(float num) {
        // BigDecimal para no perder precision al dividir el numero en entero y fracción
        BigDecimal number = new BigDecimal(String.valueOf(num));
        ArrayList<Integer> wholeBinary = wholeToBinary(number.intValue());
        ArrayList<Integer> fractionBinary = fractionToBinary(number.remainder(BigDecimal.ONE).floatValue());
        // Exponente
        int exp = 0;
        if (wholeBinary.isEmpty()) {
            // Si no tiene numero entero y solo fracción
            // El Exp toma la posición del primer 1 en la fracción
            // Y se vuelve negativo en esa posición
            for (exp = 0; exp < fractionBinary.size(); exp++) {
                if (fractionBinary.get(exp) == 1) {
                    exp++;
                    break;
                }
            }
            exp = -exp;
        } else {
            // Si tiene entero entonces toma la posición antes del 1
            // Ej. 1010101 -> 1.010101 size=7-1 = 6 (Posición del punto)
            exp = wholeBinary.size() - 1;
        }
        // Volviendo el resultado del sesgo +/- exp a numero binario
        ArrayList<Integer> expfp = wholeToBinary((sesgo + exp));
        // Tiene que tener tamaño 5 de la mantisa
        while (expfp.size() != 5) {
            expfp.add(0, 0);
        }

        // Lista que contiene el resultado final de los bits la cual
        // en este caso se suponen que todos son positivos
        ArrayList<Integer> bit16 = new ArrayList<Integer>();
        bit16.add(0);
        // Se agrega los valores de exp a la mantisa
        for (Integer n : expfp) {
            bit16.add(n);
        }
        // Se agregan el resto de números dependiendo si el exp es negativo o no
        if (exp <= 0) {
            for (int i = (exp * -1); i < fractionBinary.size() && bit16.size() <= 16; i++) {
                bit16.add(fractionBinary.get(i));
            }
        } else {
            for (int i = (wholeBinary.size() - exp); i < wholeBinary.size() && bit16.size() < 16; i++) {
                bit16.add(wholeBinary.get(i));
            }
            for (int i = 0; i < fractionBinary.size() && bit16.size() < 16; i++) {
                bit16.add(fractionBinary.get(i));
            }
        }
        // Se rellena el binario de 0 para cumplir el tamaño de 16 bits
        while (bit16.size() < 16) {
            bit16.add(0);
        }
        // Retorna la lista de 16 bits
        return bit16;

    }

    /**
     * Parte entera del numero decimal a binario
     * 
     * @param n
     * @return ArrayList<Integer>
     */
    public static ArrayList<Integer> wholeToBinary(float n) {
        ArrayList<Integer> binary = new ArrayList<Integer>();
        while (n > 0) {
            if (n % 2 == 0) {
                binary.add(0);
            } else {
                binary.add(1);
            }
            n = (int) n / 2;
        }
        Collections.reverse(binary);
        return binary;
    }

    /**
     * Parte fraccionaria del numero decimal a binario
     * 
     * @param n
     * @return ArrayList<Integer>
     */
    public static ArrayList<Integer> fractionToBinary(float n) {
        ArrayList<Integer> binary = new ArrayList<Integer>();
        int max = 15;
        while (n != 0.0 && max != 0) {
            binary.add((int) (n * 2));
            n = (n * 2) % 1;
            max--;
        }
        return binary;
    }

    /**
     * Retorna el numero en 16 bits que es mas limitado
     * 
     * @param n
     * @return 16 bit float
     */
    public static float Float_16(float n) {
        // Se obtiene el valor binario de 16 bits
        ArrayList<Integer> binary = Binary16(n);
        // Se obtiene el exponente y se convierte a decimal
        // Luego se le resta el sesgo
        String expBinary = "";
        for (int i = 1; i < 6; i++) {
            expBinary += binary.get(i);
        }
        int expo = Integer.parseInt(expBinary, 2) - sesgo;
        // Se crea un string con el valor del binario normalizado
        String normalized = "";
        if (expo > 0) {
            normalized += "1";
            int whole;
            if (expo + 6 >= binary.size()) {
                whole = binary.size();
            } else {
                whole = expo + 6;
            }

            for (int i = 6; i < whole; i++) {
                normalized += binary.get(i);
            }
            if (binary.subList(whole, binary.size()).contains(1)) {
                normalized += ".";
                for (int i = whole; i < binary.lastIndexOf(1) + 1; i++) {
                    normalized += binary.get(i);
                }
            }
            while (normalized.length() < expo + 1) {
                normalized += "0";
            }
        } else if (expo < 0) {
            normalized += "0.";
            for (int i = 1; i < expo * -1; i++) {
                normalized += 0;
            }
            normalized += 1;
            for (int i = 6; i < binary.lastIndexOf(1) + 1; i++) {
                normalized += binary.get(i);
            }
        } else {
            normalized += "1.";
            for (int i = 6; i < binary.lastIndexOf(1) + 1; i++) {
                normalized += binary.get(i);
            }
        }
        // El valor del binario normalizado se pasa a un decimal y se retorna este.
        return toDecimal(normalized);

    }

    /**
     * Turns binary number to decimal
     * 
     * @param binary
     * @return float
     */
    public static float toDecimal(String binary) {
        float number = 0;
        float fraction = 0;
        if (!binary.contains(".")) {
            number = Integer.parseInt(binary, 2);
        } else {
            String[] decimal = binary.split("[.]");
            number = Integer.parseInt(decimal[0], 2);
            for (int i = 0; i < decimal[1].length(); i++) {
                int num = Character.getNumericValue(decimal[1].charAt(i));
                if (num == 1) {
                    fraction += Math.pow(2, -(i + 1));
                }
            }
        }
        number += fraction;
        return number;
    }

}