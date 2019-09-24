package com.jarv.bancormi;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;


public class ServidorCentral 
    extends UnicastRemoteObject
    implements InterfazBancoCentral
	 
{
    ServidorCentral() throws java.rmi.RemoteException{
	super();
    }
    
    public double cotizarDolar(String a) {
        String[][] FechaCotizar={{"26-09-19","6.90"},{"27-09-19","6.91"},{"28-09-19","6.93"},{"29-09-19","6.92"},{"26-09-19","6.96"}};
	double Respuesta=0;
        for (int i=0;i<FechaCotizar.length;i++){
           if(FechaCotizar[i][0].equals(a))
               Respuesta=Double.parseDouble(FechaCotizar[i][1]);
        }
        
         return Respuesta;
    }

    
    public static void main(String args[]) { 
	try {
	    ServidorCentral ServidorBanco;
	    LocateRegistry.createRegistry(1099);
	    ServidorBanco=new ServidorCentral(); 
	    Naming.bind("CotizarDolar", ServidorBanco); 
            System.out.println("El servidor del Banco Central esta listo\n");
        }
	catch (Exception e){
	    e.printStackTrace();
	}
    } 

    
}


