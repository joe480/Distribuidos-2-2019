/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jarv.fibonacci;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class principal {
    public static void main(String[]args){
       Scanner sc=new Scanner(System.in);
       Fibonacci fb=new Fibonacci();
       int n;
       String fibo;
        System.out.println("Introducir cantidad de numeros del fibonaci");
        n=sc.nextInt();
        fibo=fb.calcularfibo(n);
        System.out.println(fibo);
        PreparedStatement pst = null;
        ResultSet rst = null;
        String sql = "insert into fibonaci (numero,fibonaci) values ("+n+",'"+fibo+"')";
        System.out.println(sql);
        
        try {
        ConexionPostgres posgres=new ConexionPostgres("db_fibonaci");
        Connection conexion =posgres.conectar(); 
        pst = conexion.prepareStatement(sql);
        rst = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
