package sisbanco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import cotesApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;


public class ServidorBanco 
    extends UnicastRemoteObject
    implements IOperacionesEmpresa
	 
{   static cotes cotesImpl;
    ServidorBanco() throws java.rmi.RemoteException{
	super();
    }
    
    public Factura[] calcular(int idcliente) throws RemoteException {
        ICessa cessa;
        try {
        cessa=(ICessa)Naming.lookup("rmi://localhost/Cessa");
        Factura[] FacturasPendientesCessa=cessa.pedientes(idcliente);
        String [] pendientesElapas=llamarElapas("fac-"+String.valueOf(idcliente)).split(",");
        Factura[] FacturasPendientesElapas=new Factura[pendientesElapas.length];
        Factura[] FacturasPendientes = new Factura[ FacturasPendientesCessa.length + pendientesElapas.length ];
        int i=0;
        for (String f:pendientesElapas )
        {
            String[] factu=f.split("-");
            int IdFactura=Integer.parseInt(factu[0]);
            double monto=Integer.parseInt(factu[1]);
            FacturasPendientesElapas[i]=new Factura("Elapas",IdFactura,monto);
            i++;
        }
        ORB orb= ORB.init(args,null);
        org.omg.CORBA.Object objRef=orb.resolve_initial_references("NameService");
        NamingContextExt ncRef= NamingContextExtHelper.narrow(objRef);
 
        String name="Suma";
        cotesImpl=cotesHelper.narrow(ncRef.resolve_str(name));
        //System.out.println("Obteniendo las cabeceras del objeto servidor: "+sumaImpl);
        String pendientesCotes =cotesImpl.calcular(idcliente);
        cotesImpl.shutdown();

        
        System.arraycopy( FacturasPendientesCessa, 0, FacturasPendientes, 0, FacturasPendientesCessa.length );
        System.arraycopy( FacturasPendientesElapas, 0, FacturasPendientes, FacturasPendientesCessa.length, FacturasPendientesElapas.length );
        return FacturasPendientes; 
        }
        catch (Exception e){
	    e.printStackTrace();
	return null;
        }
    }    
    public String pagar(Factura[] facturas) throws RemoteException {
          ICessa cessa;
        try 
        {
        cessa=(ICessa)Naming.lookup("rmi://localhost/Cessa");  
        int contarCessa=0;
        String  facturasElapas="";
        String empresa="";
        for (Factura f:facturas)
        {
            empresa=f.getEmpresa();
            if (empresa.equals("Cessa"))
            {
                contarCessa++;
            }
            if (empresa.equals("Elapas"))
            {
                facturasElapas+=String.valueOf(f.getIdFactura())+",";
            }
            
        }
        facturasElapas=facturasElapas.substring(0, facturasElapas.length()-2);
        
        Factura[] FacturasCessa=new Factura[contarCessa]; 
        
        int iCessa=0;
        for (Factura f:facturas)
        {
            if (f.getEmpresa().equals("Cessa"))
            {
                FacturasCessa[iCessa]=f;
                iCessa++;
            }
        }
        return cessa.pagar(FacturasCessa)+llamarElapas("pag-"+facturasElapas);
        }
        catch (Exception e){
	    e.printStackTrace();
	return null;
        }
        
    }
    
    
    
    public static void main(String args[]) { 
	try {
	    ServidorBanco Banco;
	    //LocateRegistry.createRegistry(1099);
	    Banco=new ServidorBanco(); 
	    Naming.bind("Operaciones", Banco); 
            System.out.println("El servidor esta listo\n");
        }
	catch (Exception e){
	    e.printStackTrace();
	}
    } 

    private String llamarElapas(String cadena) {
                int port = 5001; // puerto de comunicacion
        try{
            Socket client = new Socket("localhost", port); //conectarse al socket
            PrintStream toServer = new PrintStream(client.getOutputStream());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
            toServer.println(cadena);  //mandar alservidor 
            String result = fromServer.readLine();  // devolver del servidor
            return result;
                    }
        catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }



    
}


