package com.jarv.fibonacci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ConexionPostgres {

    private Connection Conn = null;
    private String url;
    private String user;
    private String password;

    public ConexionPostgres(String BaseDatos) {

        url = "jdbc:postgresql://localhost:5432/"+BaseDatos;
        user = "postgres";
        password = "user";
                
    }
    
    
    
    public Connection conectar()
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionPostgres.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionPostgres.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Conn;
    }

}
