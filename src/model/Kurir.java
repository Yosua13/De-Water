/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author reyyo
 */
public class Kurir extends User{

    public Kurir(int id, String namaLengkap, String email, String nomorHP, String alamat, String kataSandi) {
        super(id, namaLengkap, email, nomorHP, alamat, kataSandi);
    }

    public Kurir(String namaLengkap) {
        super(namaLengkap);
    }

    
}
