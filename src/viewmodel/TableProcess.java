/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 *	Filename	= TableProcess.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= viewmodel untuk proses ngambil data tabel score untuk tampilan data di tabel pada Window Menu
 */

package viewmodel;

// import library
import javax.swing.table.DefaultTableModel;

//  akses package model
import model.TableScore;

public class TableProcess {
    private TableScore tableScore; // data tabel tscore pada database

    public TableProcess() {
        // konstruktor
        try {
            this.tableScore = new TableScore();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public DefaultTableModel setTable(){
        // prosedur untuk mengambil semua data pada tabel score
        // lalu mengembalikan data tersebut sebagai DefaultTableModel
        
        DefaultTableModel dataTable = null;
        try{
            // membuat header tabel
            Object[] column = {"Username", "Score", "Standing"};
            dataTable = new DefaultTableModel(null, column);
            
            // mengambil data tabel score  pada database
            this.tableScore.getTScore();
            // mengambil data per baris
            while(this.tableScore.getResult().next()){
                Object[] row = new Object[3];
                // mengambil data per kolom
                row[0] = this.tableScore.getResult().getString(1);
                row[1] = this.tableScore.getResult().getString(2);
                row[2] = this.tableScore.getResult().getString(3);
                dataTable.addRow(row);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        // mengembalikan data yang sudah diambil
        return dataTable;
    }
 
}
