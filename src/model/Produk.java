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
    protected String jenis_air, nama_produk, kode_produk, stok;
    protected int harga;

    public Produk(String jenis_air, String nama_produk, String kode_produk, int harga, String stok) {
        this.jenis_air = jenis_air;
        this.nama_produk = nama_produk;
        this.kode_produk = kode_produk;
        this.harga = harga;
        this.stok = stok;
    }

    public String getJenisAir() {
        return jenis_air;
    }

    public String getNamaProduk() {
        return nama_produk;
    }

    public String getKodeProduk() {
        return kode_produk;
    }

    public int getHarga() {
        return harga;
    }
    
    public String getStok(){
        return stok;
    }
}
