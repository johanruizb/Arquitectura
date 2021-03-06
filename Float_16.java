
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

import java.math.BigDecimal;

/**
 * Permite crear floats de solo 16 bits,
 * no funciona con negativos
 */
public class Float_16 {
    final int sign = 1;
    final int exponent = 5;
    final int mantissa = 10;
    final int bias = 15;
    int Exp = 0;

    /**
     * Constructor
     */
    public Float_16() {
        System.out.println("Clase Float");
    }

    /**
     * Retorna un string del numero en binario
     *
     * @param n
     * @return string. Binario en texto, como "10","011", etc
     */
    public String toBinary(float n) {
        BigDecimal number = new BigDecimal(String.valueOf(n));
        int whole = number.intValue();
        float decimal = number.remainder(BigDecimal.ONE).floatValue();

        String binary = "";
        while (whole > 0) {
            if (whole % 2 == 0) {
                binary = '0' + binary;
            } else {
                binary = '1' + binary;
            }
            whole = (int) whole / 2;
        }

        binary += ('.');
        int i = 0;
        if (decimal == 0.0) {
            binary += ('0');
        } else {
            while (decimal != 0.0 && i < bias) {
                binary += ((int) (decimal * 2));
                decimal = (decimal * 2) % 1;
                i++;
            }
        }

        return binary;
    }

    /**
     * Calcula el exponente del numero
     *
     * @param n
     */
    public void setExp(String n) {
        int exp = 0;
        if (n.charAt(0) == '.') {
            exp = -(n.indexOf('1'));
        } else {
            exp = n.substring(0, n.indexOf('.')).length() - 1;
        }

        Exp = exp;
    }

    /**
     * Obtiene el exponente del numero
     *
     * @return Exp. El exponente en numero entero
     */
    public int getExp() {
        return Exp;
    }

    /**
     * Normaliza un numero binario,
     * debe ingresar como un string
     *
     * @param n
     * @return string. Binario normalizado
     */
    public String normalize(String n) {

        setExp(n);
        String normalized = "";
        if (Exp > 0) {
            normalized = 1 + "." + n.substring(1, n.indexOf('.')) + n.substring(n.indexOf('.') + 1, n.length());
        } else if (Exp < 0) {
            normalized = 1 + "." + n.substring(-Exp + 1, n.length());
        } else {
            return n;
        }

        return normalized;
    }

    /**
     * Vuelve un string de un numero binario
     * en su forma punto flotante.
     * No funciona con negativos aun.
     *
     * @param n
     * @return floatingPoint. El binario en representacion de 16bits como string
     */
    public String getFloatingPoint(String n) {
        n = n.substring(2, n.length());
        String floatingPoint = "0";
        String exp = Integer.toBinaryString(bias + Exp);
        while (exp.length() < exponent) {
            exp = "0" + exp;
        }
        floatingPoint += exp;
        if (n.length() > mantissa) {
            n = overflow(n);
        }
        floatingPoint += n;
        while (floatingPoint.length() < (sign + exponent + mantissa)) {
            floatingPoint += "0";
        }

        return floatingPoint;
    }

    /**
     * Trunca el numero binario si hay
     * un desborde
     *
     * @param n
     * @return number. El numero en binario como string una vez ya truncado
     */
    public String overflow(String n) {
        String number = n.substring(0, 10);
        if (n.charAt(10) == '1') {
            int decimal = Integer.parseInt(number, 2) + 1;
            number = Integer.toBinaryString(decimal);
            while (number.length() < mantissa) {
                number = "0" + number;
            }
        }
        return number;
    }

    /**
     * Llama las funciones necesarias para obtener
     * el valor ya en 16 bits.
     *
     * @param n
     * @return float. Representacion en numero flotante de 16 bits
     */
    public float float16(float n) {
        String bit16 = getFloatingPoint(normalize(toBinary(n)));
        String binary = toBinary(bit16);

        return getNumber(binary);
    }

    /**
     * Vuelve un string de un binario en punto flotante,
     * a un binario normal.
     *
     * @param n
     * @return String. El binario en representacion de punto flotante convertido en
     *         un binario natural.
     */
    public String toBinary(String n) {
        String binary = "";
        String expfp = n.substring(1, 6);
        int exp = Integer.parseInt(expfp, 2) - bias;
        if (exp < 0) {
            binary += "0.";
            while (exp != -1) {
                binary += "0";
                exp++;
            }
            binary += '1' + n.substring(6, n.lastIndexOf('1') + 1);

        } else if (exp > 0) {
            binary += "1";
            if (exp > 10) {
                binary += n.substring(6, n.length());
                exp = exp - 10;
                while (exp > 0) {
                    binary += "0";
                    exp--;
                }
            } else {
                binary += n.substring(6, 6 + exp);
                if (n.substring(6 + exp, n.length()).contains("1")) {
                    binary += "." + n.substring(6 + exp, n.lastIndexOf('1') + 1);
                } else {
                    binary += ".0";
                }
            }

        } else {
            binary += "1.";
            binary += n.substring(6, n.lastIndexOf('1') + 1);
        }

        return binary;
    }

    /**
     * Retorna el valor float de un numero binario
     *
     * @param n
     * @return Float. El valor flotante de un binario natural.
     */
    public float getNumber(String n) {
        String whole = "";
        float number = 0;
        if (n.contains(".")) {
            whole = n.substring(0, n.indexOf('.'));
            String decimal = n.substring(n.indexOf('.') + 1, n.length());
            for (int i = 0, x = whole.length() - 1; i < whole.length(); i++) {
                if (whole.charAt(i) == '1') {
                    number += Math.pow(2, x);
                }
                x--;
            }
            for (int i = 0, x = -1; i < decimal.length(); i++) {
                if (decimal.charAt(i) == '1') {
                    number += Math.pow(2, x);
                }
                x--;
            }
        } else {
            whole = n;
            for (int i = 0, x = whole.length() - 1; i < whole.length(); i++) {
                if (whole.charAt(i) == '1') {
                    number += Math.pow(2, x);
                }
                x--;
            }
        }

        return number;
    }

}
