/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculadora;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author logra
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static final Integer SPACE = 32;
    
    public static String convertir(Cola res){
        StringBuilder str = new StringBuilder();
        
        while(!res.isEmpty()){
            double parte = res.pop();
            String insertar = Double.toString(parte);
            str.append(insertar);
        }
        str.append("\n");
        String posfijo = str.toString();
        return posfijo;
    }
    
    
    public static boolean operador(char simbolo){
        if(simbolo == '+' || simbolo == '-'|| simbolo == '*' || simbolo == '/' || simbolo == '^'){
            return true;
        }else {
            return false;
        }
    }
    
    public static double operacion(char operador, double num1, double num2){
        if( operador == '+') {
            return num1 + num2;
        }else if(operador == '-') {
            return num1 - num2;
        }else if(operador == '*') {
            return num1 * num2;
        }else if(operador == '/') {
            if(num2 != 0){
                return num1 / num2;
            }
        } 
        return Math.pow(num1, num2);
    }
    
    public static String separar(String ecuacion){
        StringBuilder sep = new StringBuilder();
        StringBuilder fin = new StringBuilder();
        for(int i = 0 ; i < ecuacion.length(); i++){
            char opc = ecuacion.charAt(i);
            if(opc <= '9' && opc >= '0'){
                sep.append(opc);
            }else{
                fin.append(sep);
                fin.append(" ");
                fin.append(opc);
                fin.append(" ");
                sep = new StringBuilder();
            }
        }
        fin.append(sep);
        String res_fin = fin.toString();
        return res_fin;
    }
    
    public static String resolverSY(Cola resultado){
        Pila numeros = new Pila();
        while(!resultado.isEmpty()){
            char opc = (char)resultado.pop();
            if(operador(opc) == true){
                double a = numeros.pop();
                double b = numeros.pop();
                double res = operacion(opc, b,a);
                numeros.push(res);
                
            }else{
                double numero_real = opc;
                numeros.push(numero_real);
            }
        }
        double fin = (double)numeros.peek();
        String res_final = String.valueOf(fin);
        return res_final;
    }
    
    
    public static Cola ShuntingYard(String ecuacion){
        Cola resultado = new Cola();
        Pila operador = new Pila();
        Pila negativo = new Pila();
        
        ecuacion = separar(ecuacion);
        StringTokenizer toks = new StringTokenizer(ecuacion," ");

        while(toks.hasMoreTokens()){
            String parte = toks.nextToken();
            double ascii = (double)parte.charAt(0);

            if(ascii == SPACE) continue;
            else if( ascii <= 57 && ascii >= 48 ) {
                double num = Double.valueOf(parte);
                resultado.push(num);
                if(!negativo.isEmpty()){
                    resultado.push(negativo.pop());
                }
                
            }
            else{
                if(resultado.isEmpty()){
                    resultado.push(0);
                    negativo.push(ascii);
                }
                else if(operador.isEmpty() || ascii == 40){
                    operador.push(ascii);
                }
                else{
                    
                    if(ascii == 43 || ascii == 45){
                        while(!operador.isEmpty() && (operador.peek() != ascii && operador.peek() != 40)){
                            resultado.push(operador.pop());
                        }
                        operador.push(ascii);
                    }
                    else if(ascii == 42 || ascii == 47){
                        while(!operador.isEmpty() && ((operador.peek() != 43 && operador.peek() != 45) && operador.peek() != 40)){
                            resultado.push(operador.pop());
                        }
                        operador.push(ascii);
                    }
                    else if(ascii == 41){
                        while(!operador.isEmpty() && operador.peek() != 40){
                            resultado.push(operador.pop());
                        }
                        operador.pop();
                    }
                    else{
                        operador.push(ascii);
                    }
                }

            }
        }

        while(!operador.isEmpty()) resultado.push(operador.pop());
        return resultado;
    }
    
    public static void main(String[] args) {
        
        // TODO code application logic here
        //C:\Users\logra\Desktop
        
        Main programa = new Main();
        try{           
            Scanner sc = new Scanner(System.in);
            System.out.print("\nEscriba la direccion del archivo: ");
            String direccion = sc.nextLine();
            String ecuacion = new String();
            
            File archivo = new File(direccion, "INPUT.TXT");           
            BufferedReader origen = new BufferedReader(new FileReader(archivo));
            
            
            System.out.print("\nEscriba la direccion donde quiere guardar el archivo: ");
            String dest_dirr = sc.nextLine();
            
            File writer = new File(dest_dirr, "OUTPUT.TXT");
            FileWriter destino = new FileWriter(writer, false);
            
            while( (ecuacion = origen.readLine()) != null){
                Cola expresion = programa.ShuntingYard(ecuacion);
                String resultado = resolverSY(expresion);
                destino.write(resultado);
                destino.append("\n");
            }
            
            origen.close();
            destino.close();
            
        }catch(Exception e){
            System.out.println("No se encontro el archivo");
        } 
    }
}
