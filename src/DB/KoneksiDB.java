/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author reyyo
 */
public class KoneksiDB {
    // driver JDBC driver dan database URL

    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/tubes_pbo";
    // Database credentials
    private final String USER = "root";
    private final String PASS = "";
    private Connection conn = null;

    public void bukaKoneksi() {
        boolean flag = false;
        try {
            // Langkah ke-2: Registrasi JDBC
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            flag = true;
        }
        if (!flag) {
            try {
                // Langkah ke-3: buka koneksi
                conn = DriverManager.getConnection(DB_URL, USER,
                        PASS);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Connection getConn() {
        return conn;
    }
}
