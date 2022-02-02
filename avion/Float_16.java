package avion;

import java.math.BigDecimal;

public class Float_16 {
    final int sign = 1;
    final int exponent = 5;
    final int mantissa = 10;
    final int bias = 15;
    int Exp = 0;

    public Float_16(){
        System.out.println("Clase Float");
    }

    public String toBinary(float n) {
        BigDecimal number = new BigDecimal(String.valueOf(n));
        int whole = number.intValue();
        float decimal = number.remainder(BigDecimal.ONE).floatValue();
        //ArrayList<Object> binary = new ArrayList<>();
        String binary = "";
        while(whole > 0){
            if(whole%2 == 0){
                binary = '0' + binary;
            }else{
                binary = '1' + binary;
            }
            whole = (int)whole/2;
        }

        binary += ('.');
        int i = 0;
        if(decimal == 0.0){
            binary += ('0');
        }else{
            while(decimal != 0.0 && i < bias){
                binary += ((int)(decimal * 2));
                decimal = (decimal * 2)%1;
                i++;
            }
        }
        //System.out.println(n + " = " + binary);
        return binary;
    }

    public void setExp(String n){
        int exp = 0;
        if(n.charAt(0) == '.'){
            exp = -(n.indexOf('1'));
        }else{
            exp = n.substring(0, n.indexOf('.')).length() -1;
        }
        //System.out.print("Exp = " + exp + " ");
        Exp = exp;
    }

    public int getExp(){
        return Exp;
    }

    public String normalize(String n) {

        setExp(n);
        String normalized = "";
        if(Exp > 0){
            normalized = 1 + "." + n.substring(1, n.indexOf('.')) + n.substring(n.indexOf('.')+1, n.length());
        }else if(Exp < 0){
            normalized = 1 + "."+n.substring(-Exp+1, n.length());
        }else{
            return n;
        }
        //System.out.println(" Normalized = " + normalized);
        return normalized;
    }
    //Por ahora no cuenta números negativos
    public String getFloatingPoint(String n){
        n = n.substring(2, n.length());
        String floatingPoint = "0";
        String exp = Integer.toBinaryString(bias + Exp);
        while(exp.length() < exponent){
            exp = "0" + exp;
        }
        floatingPoint += exp;
        if(n.length() > mantissa){
            n = overflow(n);
        }
        floatingPoint += n;
        while(floatingPoint.length() < (sign + exponent + mantissa)){
            floatingPoint += "0";
        }
        //System.out.println(" Floating: " + floatingPoint);
        return floatingPoint;

    }

    public String overflow(String n){
        String number = n.substring(0, 10);
        if(n.charAt(10) == '1'){
            int decimal=Integer.parseInt(number,2) + 1;
            number = Integer.toBinaryString(decimal);
            while(number.length() < mantissa){
                number = "0" + number;
            }
        }
        return number;
    }
    /**Organizarlo */
    public float float16(float n){
        String bit16 = getFloatingPoint(normalize(toBinary(n)));
        String binary = toBinary(bit16);
        return getNumber(binary);
    }

    public String toBinary(String n){
       String binary = "";
       String expfp = n.substring(1, 6);
       int exp = Integer.parseInt(expfp,2)-bias;
        if(exp < 0){
            binary += "0.";
            while(exp != -1){
                binary += "0";
                exp++;
            }
            binary +=  '1' + n.substring(6, n.lastIndexOf('1')+1);

        }else if(exp > 0){
            binary += "1";
            if(exp > 10){
                binary += n.substring(6, n.length());
                exp = exp - 10;
                while(exp > 0){
                    binary += "0";
                    exp--;
                }
            }else{
                binary += n.substring(6, 6+exp);
                if(n.substring(6+exp, n.length()).contains("1")){
                    binary += "." + n.substring(6+exp, n.lastIndexOf('1')+1);
                }else{
                    binary += ".0";
                }
            }


        }else{
            binary += "1.";
            binary += n.substring(6, n.lastIndexOf('1')+1);
        }
        //System.out.print(" BINARIO " + binary);
        return binary;
    }

    public float getNumber(String n){
        String whole ="";
        float number = 0;
        if(n.contains(".")){
            whole = n.substring(0, n.indexOf('.'));
            String decimal = n.substring(n.indexOf('.')+1, n.length());
            for(int i=0, x=whole.length()-1; i < whole.length(); i++){
                if(whole.charAt(i) == '1'){
                    number += Math.pow(2, x);
                }
                x--;
            }
            for(int i=0, x=-1; i < decimal.length(); i++){
                if(decimal.charAt(i) == '1'){
                    number += Math.pow(2, x);
                }
                x--;
            }
        }else{
            whole = n;
            for(int i=0, x=whole.length()-1; i < whole.length(); i++){
                if(whole.charAt(i) == '1'){
                    number += Math.pow(2, x);
                }
                x--;
            }
        }
        return number;

    }

}
