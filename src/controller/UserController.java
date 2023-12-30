/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DB.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Admin;
import model.Customer;
import tubes_pbo.Katalog;
import tubes_pbo.KatalogAdmin;
import tubes_pbo.Login;
import tubes_pbo.Register;

/**
 *
 * @author reyyo
 */
public class UserController {

    private Login login;
    private Register register;
    private Connection conn;
    private Katalog katalog;
    private KatalogAdmin katalogAdmin;
    private Customer customer;
    private Customer currentUser;

    protected final ArrayList<Customer> userList = new ArrayList<>();

    public UserController(Register register, Login login, Katalog katalog) {
        this.login = login;
        this.register = register;
        this.katalog = katalog;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.conn = koneksiDB.getConn();
    }

    public void addUser(Customer user) {
        userList.add(user);
    }

    public void register() {
        customer = new Customer(
                0,
                register.getNamaLengkap().getText(),
                register.getEmail().getText(),
                register.getNomorHP().getText(),
                register.getAlamat().getText(),
                register.getSandi().getText()
        );

        //Validasi nama pengguna
        if (!isValidEmail(customer.getEmail())) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid email format", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the email already exists in the database
        if (isEmailExists(customer.getEmail())) {
            JOptionPane.showMessageDialog(new JFrame(), "Email already exists. Please choose a different email.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if email already exists
        }
        String sql = "INSERT INTO user (namaLengkap, email, nomorHP, alamat, kataSandi, role) VALUES (?, ?, ?, ?, ?, 'user')";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            if ("".equals(register.getNamaLengkap().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Name is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ("".equals(register.getEmail().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Email Address is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ("".equals(register.getNomorHP().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Whatsapp Number is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ("".equals(register.getAlamat().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Password is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ("".equals(register.getSandi().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Password is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                st.setString(1, customer.getNamaLengkap());
                st.setString(2, customer.getEmail());
                st.setString(3, customer.getNomorHP());
                st.setString(4, customer.getAlamat());
                st.setString(5, customer.getKataSandi());

                int rowsInserted = st.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showConfirmDialog(register, "New account has been created successfully!", "Info",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    tampilkanLoginPanel();
                } else {
                    JOptionPane.showConfirmDialog(register, "Gagal menambahkan akun.", "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    tampilkanRegistPanel();
                }
                st.execute(sql);
            }

        } catch (Exception e) {
            System.out.println("Error!" + e.getMessage());
        }
    }

    public void login() {
        customer = new Customer(
                0,
                null,
                login.getEmail().getText(),
                null,
                null,
                login.getKataSandi().getText()
        );

        String sql = "SELECT * FROM user WHERE email = ? AND kataSandi = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            if ("".equals(login.getEmail().getText()) || "".equals(login.getKataSandi().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Email and password are required", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                st.setString(1, customer.getEmail());
                st.setString(2, customer.getKataSandi());

                ResultSet resultSet = st.executeQuery();
                if (resultSet.next()) {
//                String role = resultSet.getString("role");
                    String role = resultSet.getString("role");

                    if ("admin".equals(role)) {
                        JOptionPane.showConfirmDialog(login, "Login successful as Admin.", "Info",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        Admin currentUser = new Admin(
                                resultSet.getInt("id"),
                                resultSet.getString("namaLengkap"),
                                resultSet.getString("email"),
                                resultSet.getString("nomorHP"),
                                resultSet.getString("alamat"),
                                resultSet.getString("kataSandi")
                        );
//                    addUser(currentUser);
                        tampilkanKatalogAdminPanel();
                        katalog.dispose();
                    } else if ("user".equals(role)) {
//                    JOptionPane.showConfirmDialog(up, "Login successful.", "Info",
//                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        Customer currentUser = new Customer(
                                resultSet.getInt("id"),
                                resultSet.getString("namaLengkap"),
                                resultSet.getString("email"),
                                resultSet.getString("nomorHP"),
                                resultSet.getString("alamat"),
                                resultSet.getString("kataSandi")
                        );
                        addUser(currentUser);
                        tampilkanKatalogPanel(currentUser);
                        katalog.dispose();
                    } else {
                        JOptionPane.showConfirmDialog(login, "Invalid role.", "Error",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showConfirmDialog(login, "Incorrect email or password.", "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
        }
    }

    public void tampilkanPesan(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void edit() {
        String sql = "UPDATE user SET namaLengkap=?, email=?, nomorHP=?, alamat=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, customer.getNamaLengkap());
            st.setString(2, customer.getEmail());
            st.setString(3, customer.getNomorHP());
            st.setString(4, customer.getAlamat());
            st.setInt(5, customer.getId());

            int affectedRows = st.executeUpdate();

            if (affectedRows > 0) {
                tampilkanPesan("Profil berhasil diupdate");
            } else {
                tampilkanPesan("Gagal mengupdate profil");
            }
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
        }

    }

    public void tampilkanRegistPanel() {
        Register register = new Register();
        register.setVisible(true);
        register.pack();
        register.setLocationRelativeTo(null);
    }

    public void tampilkanLoginPanel() {
        Login login = new Login();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null);
    }

    public void tampilkanKatalogPanel(Customer currentUser) {
        Katalog katalog = new Katalog();
        katalog.setVisible(true);
        katalog.pack();
        katalog.setLocationRelativeTo(null);
    }

    public void tampilkanKatalogAdminPanel() {
        KatalogAdmin katalogAdmin = new KatalogAdmin();
        katalogAdmin.setVisible(true);
        katalogAdmin.pack();
        katalogAdmin.setLocation(null);
    }

    // Method to check if an email already exists in the database
    private boolean isEmailExists(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Return true if the email already exists in the database
            }
        } catch (SQLException e) {
            System.out.println("Error checking email existence: " + e.getMessage());
            return false; // Return false in case of an exception or error
        }
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        // Use a simple regex for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

}
