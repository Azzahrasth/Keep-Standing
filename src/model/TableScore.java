/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 *	Filename	= TableScore.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= model untuk mengambil dan menyimpan data tabel tscore pada database
 */

package model;

// import library
import java.sql.SQLException;

/**
 *
 * @author sitih
 */
public class TableScore extends DB {
    
    // atribut
    private String tableName; // nama tabel
    
    public TableScore() throws Exception, SQLException{
        // konstruktor
        super();
        this.tableName = "tscore";
    }

    public void getTScore(){
        // mengambil semua data dari tabel tscore
        try {
            String query = "SELECT * from " + this.tableName + " order by score DESC";
            createQuery(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void getDataTScore(String username) {
        // mengambil 1 data dari tabel tscore berdasarkan username
        
        try {
            String query = "SELECT * from " + this.tableName +" WHERE username='" + username + "'";
            createQuery(query);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void insertData(String username, int score, int standing){
     // insert/update data tabel
     
        // untuk mengecek apakah harus update
        boolean isUpdate = false;
        
        // proses pengecekan data dalam tabel
        // mencari apakah username sudah ada atau belum
        try {
            TableScore temp = new TableScore();
            temp.getDataTScore(username);
            
            // jika username sudah ada data nya dalam tabel
            if(temp.getResult().next()) {
                isUpdate = true; // set update menjadi true
            } else { // jika usernmae belum ada
                isUpdate = false; // set update menjadi false
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // melalukan insert data
        if(!isUpdate){
            try {
                String query = "INSERT INTO " + this.tableName + " VALUES('" + username + "', " + Integer.toString(score) + ", " + Integer.toString(standing) + ")";
                createUpdate(query);
            } catch (Exception e) {
                // menampilkan error
                System.out.println(e.toString());
            }
        }
        // melakukan update data 
        else if(isUpdate){
            try {
                String query = "UPDATE " + this.tableName + " SET score=" + score + ", standing=" + standing + " WHERE username='" + username + "'";
                createUpdate(query);
            } catch (Exception e) {
                // menampilkan error
                System.out.println(e.getMessage());
            }
        }
    }
    
  
}

