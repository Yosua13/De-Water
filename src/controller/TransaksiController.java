/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import tubes_pbo.Transaksi;
import java.sql.PreparedStatement;

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
                reset();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memproses data!");
        }
    }

    public void edit() {
        try {
            // TODO add your handling code here:
            String jenis_air = trans.getJenisAir().getText();
            String nama_produk = trans.getNamaProduk().getText();
            String kode_produk = trans.getKode().getText();
            String harga = trans.getHarga().getText();
            String jumlah = trans.getJumlah().getText();
            String metode_pengambilan = trans.getMetode().getText();

            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubes_pbo", "root", "");
            cn.createStatement().executeUpdate("UPDATE produk SET jenis_air = '" + jenis_air + "', nama_produk = '" + nama_produk + "', kode_produk= '" + kode_produk + "', harga = '" + harga + "', jumlah = '" + jumlah + "', metode_pengambilan = '" + metode_pengambilan + "' WHERE kode_produk = '" + kode_produk + "'");
            trans.tampilkan();
            reset();
        } catch (SQLException ex) {
            Logger.getLogger(Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JTextField jkode_produk;
    private JTextField jnama_produk;
    private JTextField jjenis_air;
    private JTextField jharga;
    private JTextField jjumlah;
    private JTextField jmetode;
    
    public void reset() {

        if (jkode_produk
                != null) {
            jkode_produk.setText("");
        }
        if (jnama_produk
                != null) {
            jnama_produk.setText("");
        }
        if (jjenis_air
                != null) {
            jjenis_air.setText("");
        }
        if (jharga
                != null) {
            jharga.setText("");
        }
        if (jjumlah
                != null) {
            jjumlah.setText("");
        }
        if (jmetode
                != null) {
            jmetode.setText("");
        }
    }

    public void hapus() {
    try {
        // TODO add your handling code here:
        String kode_produk = trans.getKode().getText();

        Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubes_pbo", "root", "");
        cn.createStatement().executeUpdate("DELETE FROM produk WHERE kode_produk = '" + kode_produk + "'");
        trans.tampilkan();
        reset();

    } catch (SQLException ex) {
        Logger.getLogger(Transaksi.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    

}
