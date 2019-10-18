/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisbanco;

import cotesApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
 
 class CotesImpl extends cotesPOA{
 private ORB orb;
 public void setORB(ORB orb_val){
 orb=orb_val;
 }
 

 
 public void shutdown(){
 orb.shutdown(false);
 }

    @Override
    public String calcular(int idcliente) {
        String Respuesta="";
        if (idcliente==1)
           {
            Respuesta="2256-36,3216-26,4547-44";
           }
        if (idcliente==2)
            {
              Respuesta="1354-25,3252-17";
            }                    
        return Respuesta;        
    }

    @Override
    public String pagar(String facturas) {
        return "SI PAGO";
    }
 }
 
 public class CotesServer {
 
 public static void main(String args[]){
 try{
 ORB orb= ORB.init(args,null);
 POA rootpoa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
 rootpoa.the_POAManager().activate();
 
 CotesImpl sumaImpl=new CotesImpl();
 sumaImpl.setORB(orb);
 
 org.omg.CORBA.Object ref=rootpoa.servant_to_reference(sumaImpl);
 cotes href=cotesHelper.narrow(ref);
 org.omg.CORBA.Object objRef=orb.resolve_initial_references("NameService");
 NamingContextExt ncRef=NamingContextExtHelper.narrow(objRef);
 
 String name="Suma";
 NameComponent path[]=ncRef.to_name(name);
 ncRef.rebind(path, href);
 System.out.println("Servidor de suma listo y en espera");
 orb.run();
 }catch(Exception e){
 System.err.println("Error: "+e);
 e.printStackTrace(System.out);
 }
 
 System.out.println("Adios cerrando servidor");
 }
 }

