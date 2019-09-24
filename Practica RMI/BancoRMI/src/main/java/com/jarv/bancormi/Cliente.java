/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jarv.bancormi;

import java.rmi.Naming;
import java.util.Scanner;


public class Cliente {
       public static void main(String args[]){
	InterfazCliente cotizar;
        Scanner sc =new Scanner(System.in);
        String cadena="";
        String Finicio="";
        String FFinal="";
        String FCotizar="";
	try {
	    cotizar=(InterfazCliente)Naming.lookup("rmi://localhost/Cotizar");
            System.out.println("Introduce una Fecha de Entrada Ej. 27-09-19");
            Finicio=sc.nextLine();
	    System.out.println();
	    System.out.println("Introduce una Fecha de Salida Ej. 27-09-19");
            FFinal=sc.nextLine();
            System.out.println();
            System.out.println("Introduce una Fecha de Cotizacion Ej. 27-09-19");
            FCotizar=sc.nextLine();
            System.out.println();
            System.out.println("El costo de su hospedaje en el hotel es de "+cotizar.Cotizar(Finicio, FFinal, FCotizar)+"Bs");
	}
	catch (Exception e){
	    e.printStackTrace();
	}
    } 
}
