/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author reyyo
 */
public class Produk {
    protected String jenis_air, nama_produk, kode_produk, metode_pengambilan;
    protected int harga, jumlah;

    public Produk(String jenis_air, String nama_produk, String kode_produk, String metode_pengambilan, int harga, int jumlah) {
        this.jenis_air = jenis_air;
        this.nama_produk = nama_produk;
        this.kode_produk = kode_produk;
        this.metode_pengambilan = metode_pengambilan;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getJenis_air() {
        return jenis_air;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public String getKode_produk() {
        return kode_produk;
    }

    public String getMetode_pengambilan() {
        return metode_pengambilan;
    }

    public int getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }
    
    
}
