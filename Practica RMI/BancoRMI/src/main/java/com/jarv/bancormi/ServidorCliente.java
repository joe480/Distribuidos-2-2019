package com.jarv.bancormi;


import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;




public class ServidorCliente 
        extends UnicastRemoteObject
        implements InterfazCliente{
    
    ServidorCliente() throws java.rmi.RemoteException{
	super();
    }
    
    public double Cotizar(String FInicio, String FFin, String FCotizacion){
        InterfazBancoCentral CotizarDolar;
        double valorDolar=0.0;
        String[][] costoHotel={{"26-09-19","30"},{"27-09-19","25"},{"28-09-19","25"},{"29-09-19","35"},{"26-09-19","40"}};
        String[]inicioS=FInicio.split("-");
        int [] inicio=convertirVector(inicioS);
        String[]inicioF=FFin.split("-");
        int [] fin=convertirVector(inicioF);
        int diasReserva=calcularDias(inicio, fin);
        
        try {
	    CotizarDolar=(InterfazBancoCentral)Naming.lookup("rmi://localhost/CotizarDolar");
	    System.out.println();
	    if (CotizarDolar.cotizarDolar(FCotizacion)==0)
            System.out.println("Dato no encontrado");
            else
            valorDolar=CotizarDolar.cotizarDolar(FCotizacion);
	    System.out.println();
	}
	catch (Exception e){
	    e.printStackTrace();
	}
        
        return calcularCosto(costoHotel, diasReserva,valorDolar,FCotizacion);
        
    }
    
private int[] convertirVector(String[]FechaString){
    int[] convertido=new int[FechaString.length];
    for (int i = 0; i < FechaString.length; i++) {
        convertido[i]=Integer.parseInt(FechaString[i]);
    }
    return convertido;
}

private int calcularDias(int[] inicio, int[] fin){
    if(fin[0]>inicio[0])
        return fin[0]-inicio[0];
    else
        return 0;
}

private double calcularCosto(String[][] costoNoche,int diasReserva, double valorDolar, String FCotizacion){
  /* if(diasReserva==0)
       diasReserva=1; */
   double costoTotal=0.0;
   double costoNocheHotel=0.0;
   for (int i=0;i<costoNoche.length;i++){
           if(costoNoche[i][0].equals(FCotizacion))
               costoNocheHotel=Double.parseDouble(costoNoche[i][1]);
        }
   costoTotal=diasReserva*costoNocheHotel*valorDolar;
   return costoTotal;
}

    
    public static void main(String args[]){
	try {
	    ServidorCliente ServidorBanco;
	    ServidorBanco=new ServidorCliente(); 
	    Naming.bind("Cotizar",ServidorBanco); 
            System.out.println("El servidor del Banco Cliente esta listo\n");
        }
	catch (Exception e){
	    e.printStackTrace();
	}
    }
}

