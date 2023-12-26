/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import tubes_pbo.Transaksi;

/**
 *
 * @author reyyo
 */
public class TransaksiController {
    private Transaksi trans;

    public TransaksiController(Transaksi trans) {
        this.trans = trans;
    }
    
    public void tambah(){
        try {
            // TODO add your handling code here:
            String kode_produk = trans.getKode().getText();
            String nama_produk = trans.getNamaProduk().getText();
            String jenis_air = trans.getJenisAir().getText();
            String harga = trans.getHarga().getText();
            String jumlah = trans.getJumlah().getText();
            String metode_pengambilan = trans.getMetode().getText();

            // Check if any of the fields is empty
            if (kode_produk.isEmpty() || nama_produk.isEmpty() || jenis_air.isEmpty() || harga.isEmpty() || jumlah.isEmpty() || metode_pengambilan.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!");
            } else {
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubes_pbo", "root", "");
                cn.createStatement().executeUpdate("INSERT INTO produk VALUES" + "('" + kode_produk + "','" + nama_produk + "','" + jenis_air + "','" + harga + "','" + jumlah + "','" + metode_pengambilan + "')");
                trans.tampilkan();
                trans.reset();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memproses data!");
        }
    }
    
    
}
