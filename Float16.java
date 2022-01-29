import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class Float16 {
    // numero - sesgo;

    static ArrayList<Integer> signo = new ArrayList<Integer>(Arrays.asList(0));
    static ArrayList<Integer> exponente = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0));
    static ArrayList<Integer> mantisa = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

    public static void main(String[] args) {
        System.out.println("Float_16(64.0625f);: " + Float_16(64.0625f));
        System.out.println("Float_16(64.10156f);: " + Float_16(64.10156f));
    }

    public static float Float_16(float num) {

        BigDecimal number = new BigDecimal(String.valueOf(num));
        ArrayList<Integer> enteroBinario = enteroBin(number.intValue());
        ArrayList<Integer> fraccionBinario = fraccionBin(number.remainder(BigDecimal.ONE).floatValue());

        ArrayList<Integer> resultado = new ArrayList<Integer>();

        int coma;
        int exp;

        if (num < 0)
            signo.set(0, 1);

        // Normalizar
        if (enteroBinario.isEmpty()) {
            coma = (fraccionBinario.indexOf(1) + 1);
            mantisa = new ArrayList<Integer>(fraccionBinario.subList(coma, (fraccionBinario.size())));
            mantisa = binaryRound(mantisa, 10);

            coma *= -1;
        } else {
            coma = enteroBinario.size() - 1;
            mantisa = new ArrayList<Integer>(enteroBinario.subList(1, enteroBinario.size()));
            mantisa.addAll(binaryRound(fraccionBinario, 10 - mantisa.size()));
        }

        exp = coma + 15;
        exponente = enteroBin(exp);

        resultado.addAll(signo);
        resultado.addAll(exponente);
        resultado.addAll(mantisa);

        // Resultado
        System.out.println("resultado: " + resultado);

        return toDecimal();
    }

    private static float toDecimal() {

        float resultado = 0;
        int exp = 0;
        float entero = 0;
        float fraccion = 0;

        // Exponente
        exp = Integer.parseInt(toString(exponente), 2) - 15;

        // Entero
        if (exp > 0) {
            ArrayList<Integer> ent = new ArrayList<>(mantisa.subList(0, exp));
            ent.add(0, 1);
            entero = Integer.parseInt(toString(ent), 2);
        }

        resultado += entero;

        // Fraccion
        ArrayList<Integer> frac = new ArrayList<>(exp > 0 ? (mantisa.subList(exp, mantisa.size()))
                : (mantisa.subList(0, mantisa.lastIndexOf(1))));

        if (exp < 0)
            for (int i = 0; i < exp * -1; i++)
                frac.add(0, 0);

        for (int i = 0; i < frac.size(); i++) {
            int num = frac.get(i);
            if (num == 1) {
                fraccion += Math.pow(2, -(i + 1));
            }
        }

        resultado += fraccion;

        // Signo
        if (signo.get(0) == 0)
            resultado *= 1;
        else
            resultado *= -1;

        return resultado;
    }

    private static String toString(ArrayList<Integer> array) {
        String res = "";
        for (int i : array)
            res += i;

        return res;
    }

    private static ArrayList<Integer> enteroBin(float floatValue) {
        ArrayList<Integer> bin = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        if (floatValue != 0) {
            for (int i = 15; i > 0; i--) {
                if (floatValue == 0 || floatValue == 1) {
                    bin.set(i, (int) floatValue);
                    break;
                }
                bin.set(i, (int) floatValue % 2);
                floatValue = (int) floatValue / 2;
            }

            for (;;) {
                if (bin.get(0) == 0) {
                    bin.remove(0);
                } else if (bin.get(0) == 1) {
                    break;
                }
            }

        } else {
            bin.clear();
        }

        return bin;
    }

    private static ArrayList<Integer> fraccionBin(float floatValue) {
        ArrayList<Integer> bin = new ArrayList<Integer>();
        for (int i = 0; i < 65536; i++) {
            if (floatValue == 0)
                break;

            floatValue = floatValue * 2;
            bin.add(i, (int) (floatValue));
            floatValue = floatValue - (int) floatValue;
        }

        return bin;
    }

    /**
     * Aproxima un binario sumandole +1 (en binario)
     * 
     * @param num
     */
    private static ArrayList<Integer> binaryRound(ArrayList<Integer> num, int size) {
        // Esta en true cuando llevas un +1 (no hace falta pasarla de nuevo a false
        // porque cuando se termine de llevar finaliza la suma)

        while (num.size() > size) {
            int last = num.size() - 1;

            if (num.get(last) == 1) {
                int carry = 1;

                num.remove(last);
                last = num.size() - 1;

                for (int i = last; i > 0; i--) {
                    int bin1 = num.get(i);

                    if (bin1 + carry == 1) {
                        num.set(i, 1);
                        break;
                    } else if (bin1 + carry == 2) {
                        num.set(i, 0);
                        carry = 1;
                    }

                }
            } else {
                num.remove(last);
            }

        }
        return num;
    }

}
