/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculadora;

/**
 *
 * @author logra
 */
public class Cola {
    
    Nodo inicio = null;
    Nodo fin = null;
    
    public Nodo crearNodo(double dato){
        Nodo nuevo = new Nodo();
        nuevo.anterior = null;
        nuevo.siguiente = null;
        nuevo.dato = dato;
        return nuevo;
    }
    

    public void push(double dato){
        Nodo nuevo = crearNodo(dato);
        if(isEmpty()){
            inicio = nuevo;
            fin = nuevo;
        }
        else{
            nuevo.anterior = fin;
            fin.siguiente = nuevo;
            fin = nuevo;
        }
    }
    

    public double pop(){
        double resultado = inicio.dato;
        if(inicio == fin){
            inicio = null;
            fin = null;
        }else{
            inicio = inicio.siguiente;
            inicio.anterior = null;
        }
        
        return resultado;
    }
    
    public boolean isEmpty(){
        return (inicio == null) && (fin == null);
    }

}

