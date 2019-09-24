package com.jarv.bancormi;


import java.rmi.*;

public interface InterfazBancoCentral extends Remote {
    double cotizarDolar(String arg) throws RemoteException; 
    
}


