/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author reyyo
 */
public class ModelTransaksi {
    private String namaPembeli, nomorHP, Alamat, NamaProduk, jenisAir, kodeProduk, metodePengambilan;
    private int harga, jumlah, total;

    public ModelTransaksi(String namaPembeli, String nomorHP, String Alamat, String NamaProduk, String jenisAir, String kodeProduk , int harga, int jumlah, int total, String metodePengambilan) {
        this.namaPembeli = namaPembeli;
        this.nomorHP = nomorHP;
        this.Alamat = Alamat;
        this.NamaProduk = NamaProduk;
        this.jenisAir = jenisAir;
        this.kodeProduk = kodeProduk;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total = total;
        this.metodePengambilan = metodePengambilan;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public String getAlamat() {
        return Alamat;
    }

    public String getNamaProduk() {
        return NamaProduk;
    }

    public String getJenisAir() {
        return jenisAir;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public String getMetodePengambilan() {
        return metodePengambilan;
    }

    public int getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getTotal() {
        return total;
    }
    
    public String getMetode() {
        return metodePengambilan;
    }
    
    
}
