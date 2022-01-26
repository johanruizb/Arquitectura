package avion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class Avion {
    static float  DeltaTime = 0.09375f;
    static float Vx = 92.25f;
    static float a = 0.625f;
    static float xLimite = 6808.05f;
    static int sesgo = 15; //2^(5-1)-1
    float time = 0.0f;
    float v = 0.0f;
    float x = 0.0f;
    public static void main(String[] args) {
        System.out.print(DeltaTime + ": ");
        Binary16(DeltaTime);
        System.out.print(Vx + ": ");
        Binary16(Vx);
        System.out.print(a + ": ");
        Binary16(a);
        System.out.print(xLimite + ": ");
        Binary16(xLimite);
    }
    /**
     * Pasar un numero flotante de 32b bits a el numero binario de 16 bits
     * @param num
     */
    private static ArrayList<Integer> Binary16(float num) {
        //BigDecimal para no perder precision al dividir el numero en entero y fracción
        BigDecimal number = new BigDecimal(String.valueOf(num));
        ArrayList<Integer> wholeBinary = wholeToBinary(number.intValue());
        ArrayList<Integer> fractionBinary = fractionToBinary(number.remainder(BigDecimal.ONE).floatValue());
        //Exponente
        int exp = 0;
        if(wholeBinary.isEmpty()){
            //Si no tiene numero entero y solo fracción
            //El Exp toma la posición del primer 1 en la fracción
            //Y se vuelve negativo en esa posición
            for(exp = 0; exp < fractionBinary.size(); exp++){
                if(fractionBinary.get(exp) == 1){
                    exp++;
                    break;
                }
            }
            exp = -exp;
        }else{
            //Si tiene entero entonces toma la posición antes del 1
            // Ej. 1010101 -> 1.010101  size=7-1 = 6 (Posición del punto)
            exp = wholeBinary.size()-1;
        }
        //Volviendo el resultado del sesgo +/- exp a numero binario
        ArrayList<Integer> expfp = wholeToBinary((sesgo+exp));
        //Tiene que tener tamaño 5 de la mantisa
        while(expfp.size() != 5){
            expfp.add(0,0);
        }

        //Lista que contiene el resultado final de los bits la cual
        //en este caso se suponen que todos son positivos
        ArrayList<Integer> bit16 = new ArrayList<Integer>();
        bit16.add(0);
        //Se agrega los valores de exp a la mantisa
        for (Integer n : expfp) {
            bit16.add(n);
        }
        //Se agregan el resto de números dependiendo si el exp es negativo o no
        if(exp <= 0){
            for(int i=(exp * -1); i < fractionBinary.size() && bit16.size() <= 16; i++){
                bit16.add(fractionBinary.get(i));
            }
        }else{
            for(int i=(wholeBinary.size()-exp); i < wholeBinary.size() && bit16.size() < 16; i++){
                bit16.add(wholeBinary.get(i));
            }
            for(int i=0; i < fractionBinary.size() && bit16.size() < 16; i++){
                bit16.add(fractionBinary.get(i));
            }
        }
        //Se rellena el binario de 0 para cumplir el tamaño de 16 bits
        while(bit16.size() <16){
            bit16.add(0);
        }
        //Retorna la lista de 16 bits
        return bit16;

    }
    /**
     * Parte entera del numero decimal a binario
     * @param n
     * @return ArrayList<Integer>
     */
    public static ArrayList<Integer> wholeToBinary(float n) {
        ArrayList<Integer> binary = new ArrayList<Integer>();
        while(n > 0){
            if(n%2 == 0){
                binary.add(0);
            }else{
                binary.add(1);
            }
            n = (int)n/2;
        }
        Collections.reverse(binary);
        return binary;
    }
    /**
     * Parte fraccionaria del numero decimal a binario
     * @param n
     * @return ArrayList<Integer>
     */
    public static ArrayList<Integer> fractionToBinary(float n) {
        ArrayList<Integer> binary = new ArrayList<Integer>();
        int max = 15;
        while(n != 0.0 && max != 0){
            binary.add((int)(n * 2));
            n = (n * 2)%1;
            max--;
        }
        return binary;
    }

}