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
    protected String nama_lengkap, nama_pengguna, nomor_hp, alamat, kata_sandi;

    public User(int id, String nama_lengkap, String nama_pengguna, String nomor_hp, String alamat, String kata_sandi) {
        this.id = id;
        this.nama_lengkap = nama_lengkap;
        this.nama_pengguna = nama_pengguna;
        this.nomor_hp = nomor_hp;
        this.alamat = alamat;
        this.kata_sandi = kata_sandi;
    }

    public int getId() {
        return id;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getNama_pengguna() {
        return nama_pengguna;
    }

    public String getNomor_hp() {
        return nomor_hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKata_sandi() {
        return kata_sandi;
    }
    
    
}
