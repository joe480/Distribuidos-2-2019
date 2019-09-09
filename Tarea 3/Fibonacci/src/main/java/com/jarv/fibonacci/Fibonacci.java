/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jarv.fibonacci;


/**
 *
 * @author User
 */
public class Fibonacci {
public String calcularfibo(int n){
    String fibo="";
    int a=0,b=1,c;
    for (int i = 0; i < n; i++) {
        fibo +=" "+a;
        c=a+b;
        a=b;
        b=c;
    }
    return fibo;
}
    
    
}
