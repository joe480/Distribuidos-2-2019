/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jarv.socketudp;

/**
 *
 * @author Carlos
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientUDP {

  // Los argumentos proporcionan el mensaje y el nombre del servidor
  public static void main(String args[]) {

    try {
        Scanner br=new Scanner(System.in);
        System.out.println("introducir nombre");
        String dato=br.nextLine();
        
        String ip="localhost";
      DatagramSocket socketUDP = new DatagramSocket();
      byte[] mensaje = dato.getBytes();
      InetAddress hostServidor = InetAddress.getByName(ip);
      int puertoServidor = 6789;

      // Construimos un datagrama para enviar el mensaje al servidor
      DatagramPacket peticion =
        new DatagramPacket(mensaje, dato.length(), hostServidor,
                           puertoServidor);

      // Enviamos el datagrama
      socketUDP.send(peticion);

      // Construimos el DatagramPacket que contendrá la respuesta
      byte[] bufer = new byte[1000];
      DatagramPacket respuesta =
        new DatagramPacket(bufer, bufer.length);
      socketUDP.receive(respuesta);

      // Enviamos la respuesta del servidor a la salida estandar
      System.out.println("Respuesta: " + new String(respuesta.getData()));

      // Cerramos el socket
      socketUDP.close();

    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    }
  }
}

