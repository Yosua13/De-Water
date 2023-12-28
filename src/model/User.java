/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author reyyo
 */
public class User {
    protected int id;
    protected String namaLengkap, email, nomorHP, alamat, kataSandi;

    public User(int id, String namaLengkap, String email, String nomorHP, String alamat, String kataSandi) {
        this.id = id;
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.nomorHP = nomorHP;
        this.alamat = alamat;
        this.kataSandi = kataSandi;
    }
    
    public User(String currentUser){
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKataSandi() {
        return kataSandi;
    }
    
    
}
