/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jarv.socketudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Carlos
 */
public class ServerUDP {

  public static void main (String args[]) {

    try {

      DatagramSocket socketUDP = new DatagramSocket(6789);
      byte[] bufer = new byte[1000];

      while (true) {
        // Construimos el DatagramPacket para recibir peticiones
        DatagramPacket peticion =
          new DatagramPacket(bufer, bufer.length);
        

        // Leemos una petición del DatagramSocket
        socketUDP.receive(peticion);
        
        String data = new String(peticion.getData());
        
        System.out.print("Datagrama recibido del host: " +
                           peticion.getAddress());
        System.out.println(" desde el puerto remoto: " +
                           peticion.getPort());
        String CadenaInvertida="";
                
                for (int x=data.length()-1;x>=0;x--)
		CadenaInvertida = CadenaInvertida + data.charAt(x);
          byte[] n=CadenaInvertida.getBytes();       
        DatagramPacket peticion2 =
          new DatagramPacket(n, n.length);
          System.out.println(CadenaInvertida);
        // Construimos el DatagramPacket para enviar la respuesta
        DatagramPacket respuesta =
          new DatagramPacket(peticion2.getData(), peticion2.getLength(),
                             peticion.getAddress(), peticion.getPort());
          
        // Enviamos la respuesta, que es un eco
        socketUDP.send(respuesta);
      }

    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    }
  }

}

    

