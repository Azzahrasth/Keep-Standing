/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 *	Filename	= DB.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= model untuk mkoneksi engakses basis data
 */

package model;

// import library
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sitih
 */
public class DB {
    // konfigurasi koneksi
    private String connAddress = "jdbc:mysql://localhost/db_tmddpbo_keepstanding?user=root&password=";
    private Statement stmt = null; // koneksi query
    private Connection conn = null; // koneksi mysql dan basis data
    private ResultSet rs = null; // hasil query
    
    public DB() throws Exception, SQLException {
        /*
         * Method DB
         * Konstruktor melakukan koneksi ke mysql dan basis data
         * Menerima masukan berupa string alamat koneksi ke mysql dan basis data
        */
        try {
            // membuat driver mysql
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // membuat koneksi ke mysql dan basis data
            conn = DriverManager.getConnection(connAddress);
            conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
        } catch (SQLException es) {
            // error jika koneksi gagal
            throw es;
        }
    }
    
    public void createQuery(String Query) throws Exception, SQLException {
        /*
         * Method createQuery
         * Mengeksekusi query tanpa mengubah isi data
         * Menerima masukan berupa string query
        */
        try {
            stmt = conn.createStatement();
            // eksekusi query
            rs = stmt.executeQuery(Query);
            if(stmt.execute(Query)) {
                // ambil hasil query
                rs = stmt.getResultSet();
            }
        } catch (SQLException e) {
            // eksrpsi jika query gagal di eksekusi
            throw e;
        }
    }
    
    public void createUpdate(String Query)throws Exception, SQLException {
        /*
         * Method createUpdate
         * Mengeksekusi query yang mengubah isi data (update, insert, delete)
         * Menerima masukan berupa string query
        */
        try {
            stmt = conn.createStatement();
            // eksekusi query
            int res = stmt.executeUpdate(Query);
        } catch (SQLException e) {
            // eksepsi jika query gagal di eksekusi
            throw e;
        }
    }
    
    public ResultSet getResult() throws Exception {
        /*
         * Method getResult
         * Memberikan hasil query
        */
        ResultSet Temp = null;
        try {
            return rs;
        } catch (Exception e) {
            // eksepsi jika hasil tidak dapat dikembalikan
            return Temp;
        }
    }
    
    public void closeResult() throws Exception, SQLException {
        /*
         * Method closeResult
         * Menutup hubungan dari eksekusi query
        */
        if(rs != null) {
            try {
                rs.close();
            }
            catch(SQLException es){
                rs = null;
                throw es;
            }
        }
        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException es) {
                stmt = null;
                throw es;
            }
        }
    }
    
    public void closeConnection() throws Exception, SQLException {
        /*
         * Method closeConnection
         * Menutup hubungan dengan mysql dan basis data
        */
        if(conn != null) {
            try {
                conn.close();
            }
            catch(SQLException es){
                conn = null;
            }
        }
    }
}

