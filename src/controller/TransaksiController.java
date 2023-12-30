/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DB.KoneksiDB;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import tubes_pbo.Transaksi;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import model.ModelTransaksi;
import model.Produk;

/**
 *
 * @author reyyo
 */
public class TransaksiController {

    private Transaksi trans;
    private Connection conn;
    private ModelTransaksi mtrans;

    public TransaksiController(Transaksi trans) {
        this.trans = trans;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.conn = koneksiDB.getConn();
    }

    public void tampilkanTransaksi() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama Pembeli");
        model.addColumn("Nomor HP");
        model.addColumn("Alamat");
        model.addColumn("Nama Produk");
        model.addColumn("Jenis Air");
        model.addColumn("Kode Produk");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Total");
        model.addColumn("Metode Pengambilan");

        try {
            int no = 1;
            String sql = "SELECT * FROM transaksi";
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {

                model.addRow(new Object[]{
                    no++,
                    
                    res.getString("nama_pembeli"),
                    res.getString("nomor_HP"),
                    res.getString("alamat"),
                    res.getString("nama_produk"),
                    res.getString("jenis_air"),
                    res.getString("kode_produk"),
                    res.getInt("harga"),
                    res.getInt("jumlah"),
                    res.getInt("total"),
                    res.getString("metode_pengambilan")
                });
            }
            trans.getTable().setModel(model);
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public void showNama() {
        DefaultTableModel model_prdk = new DefaultTableModel();
        model_prdk.getDataVector().removeAllElements();
        model_prdk.fireTableDataChanged();

        try {
            String sql = "SELECT * FROM produk";
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                Object[] obj = new Object[4];
                obj[0] = res.getString("jenis_air");
                obj[1] = res.getString("nama_produk");
                obj[2] = res.getString("kode_produk");
                obj[3] = res.getInt("harga");
                obj[3] = res.getString("stok");
                model_prdk.addRow(obj);

                trans.getNamaProduk().addItem((String) obj[1]);
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public void isiData() {
        String sql = "SELECT * FROM produk WHERE nama_produk = '" + trans.getNamaProduk().getSelectedItem() + "'";
//        "select * from product where nama='"+cbProduk.getSelectedItem()+"'"

        try {
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                String jenis_air = rs.getString("jenis_air");
                String kode_produk = rs.getString("kode_produk");
                String harga = rs.getString("harga");
                trans.getJenisAir().setText(jenis_air);
                trans.getKodeProduk().setText(kode_produk);
                trans.getHarga().setText(harga);
            }
        } catch (Exception e) {
        }
    }

    public void tambah() {
        // Menggunakan konstruktor dengan parameter
        mtrans = new ModelTransaksi(
                trans.getNamaPembeli().getText(),
                trans.getNomorHP().getText(),
                trans.getAlamat().getText(),
                trans.getNamaProduk().getSelectedItem().toString(),
                trans.getJenisAir().getText(),
                trans.getKodeProduk().getText(),
                Integer.parseInt(trans.getHarga().getText()),
                Integer.parseInt(trans.getJumlah().getText()),
                Integer.parseInt(trans.getTotal().getText()),
                trans.getMetode().getSelectedItem().toString()
        );

        // Pemeriksaan keberadaan nama produk sebelum menyimpan
        String sql = "INSERT INTO transaksi (nama_pembeli, nomor_HP, alamat, nama_produk, jenis_air, kode_produk, harga, jumlah, total, metode_pengambilan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Menggunakan getter dari objek prdkm
            statement.setString(1, mtrans.getNamaPembeli());
            statement.setString(2, mtrans.getNomorHP());
            statement.setString(3, mtrans.getAlamat());
            statement.setString(4, mtrans.getNamaProduk());
            statement.setString(5, mtrans.getJenisAir());
            statement.setString(6, mtrans.getKodeProduk());
            statement.setInt(7, mtrans.getHarga());
            statement.setInt(8, mtrans.getJumlah());
            statement.setInt(9, mtrans.getTotal());
            statement.setString(10, mtrans.getMetode());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showConfirmDialog(trans, "transaksi berhasil ditambahkan.", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showConfirmDialog(trans, "Gagal menambahkan produk.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(trans, "Terjadi kesalahan saat menambahkan transaksi: " + e.getMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void batakan() {
    // Pemeriksaan keberadaan kode produk sebelum menghapus
    String kodeProdukHapus = trans.getKodeProduk().getText();
    
    if (kodeProdukHapus.isEmpty()) {
        JOptionPane.showConfirmDialog(trans, "Masukkan kode produk untuk menghapus.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        return;
    }

    String sql = "DELETE FROM transaksi WHERE kode_produk = ?";

    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setString(1, kodeProdukHapus);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showConfirmDialog(trans, "Transaksi berhasil dihapus.", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            // Menampilkan data setelah penghapusan (jika diperlukan)
            // tampilkanProduk();
        } else {
            JOptionPane.showConfirmDialog(trans, "Tidak ada transaksi dengan kode produk tersebut.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showConfirmDialog(trans, "Terjadi kesalahan saat menghapus transaksi: " + e.getMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }
}

}
