package sisbanco;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IOperacionesEmpresa extends Remote {
    public Factura[] calcular(int idcliente) throws RemoteException;
    public String pagar(Factura[] facturas) throws RemoteException;
    
}
