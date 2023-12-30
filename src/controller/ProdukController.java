/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DB.KoneksiDB;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import model.Produk;
import tubes_pbo.KatalogAdmin;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author reyyo
 */
public class ProdukController {

    private Connection conn;
    private Produk produk;
    private KatalogAdmin katalogAdmin;

    public ProdukController(KatalogAdmin katalogAdmin) {
        this.katalogAdmin = katalogAdmin;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.conn = koneksiDB.getConn();
    }

    public void tampilkanProduk() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Jenis Air");
        model.addColumn("Nama Produk");
        model.addColumn("Kode Produk");
        model.addColumn("Harga");
        model.addColumn("Stok");

        try {
            int no = 1;
            String sql = "SELECT * FROM produk";
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {

                model.addRow(new Object[]{
                    no++,
                    res.getString("jenis_air"),
                    res.getString("nama_produk"),
                    res.getString("kode_produk"),
                    res.getInt("harga"),
                    res.getString("stok")
                });
            }
            katalogAdmin.getTable().setModel(model);
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public void tambah() {
        // Menggunakan konstruktor dengan parameter
        produk = new Produk(
                katalogAdmin.getJenisAir().getSelectedItem().toString(),
                katalogAdmin.getNamaProduk().getText(),
                katalogAdmin.getKodeProduk().getText(),
                Integer.parseInt(katalogAdmin.getHarga().getText()),
                katalogAdmin.getStok().getSelectedItem().toString()
        );

        // Pemeriksaan keberadaan nama produk sebelum menyimpan
        String sql = "INSERT INTO produk (jenis_air, nama_produk, kode_produk, harga, stok) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Menggunakan getter dari objek prdkm
            statement.setString(1, produk.getJenisAir());
            statement.setString(2, produk.getNamaProduk());
            statement.setString(3, produk.getKodeProduk());
            statement.setInt(4, produk.getHarga());
            statement.setString(5, produk.getStok());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showConfirmDialog(katalogAdmin, "Produk berhasil ditambahkan.", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                tampilkanProduk();
            } else {
                JOptionPane.showConfirmDialog(katalogAdmin, "Gagal menambahkan produk.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(katalogAdmin, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ubah() {
        // Menggunakan konstruktor dengan parameter
        produk = new Produk(
                katalogAdmin.getJenisAir().getSelectedItem().toString(),
                katalogAdmin.getNamaProduk().getText(),
                katalogAdmin.getKodeProduk().getText(),
                Integer.parseInt(katalogAdmin.getHarga().getText()),
                katalogAdmin.getStok().getSelectedItem().toString()
        );

        // Pemeriksaan keberadaan nama produk sebelum menyimpan
        String sql = "UPDATE produk SET jenis_air=?, nama_produk=?, harga=?, stok=? WHERE kode_produk=?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Menggunakan getter dari objek prdkm
            statement.setString(1, produk.getJenisAir());
            statement.setString(2, produk.getNamaProduk());
            statement.setInt(3, produk.getHarga());
            statement.setString(4, produk.getStok());

            statement.setString(5, produk.getKodeProduk());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showConfirmDialog(katalogAdmin, "Produk berhasil diubah.", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                tampilkanProduk();
            } else {
                JOptionPane.showConfirmDialog(katalogAdmin, "Gagal mengubah produk.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(katalogAdmin, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void hapus() {
    // Mendapatkan kode produk yang dipilih untuk dihapus
    String kodeProduk = katalogAdmin.getKodeProduk().getText();

    // SQL statement untuk menghapus data berdasarkan kode produk
    String sql = "DELETE FROM produk WHERE kode_produk = ?";

    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        // Mengatur parameter kode produk pada statement SQL
        statement.setString(1, kodeProduk);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showConfirmDialog(katalogAdmin, "Produk berhasil dihapus.", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            tampilkanProduk();
        } else {
            JOptionPane.showConfirmDialog(katalogAdmin, "Gagal menghapus produk. Kode produk tidak ditemukan.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showConfirmDialog(katalogAdmin, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }
}
}
