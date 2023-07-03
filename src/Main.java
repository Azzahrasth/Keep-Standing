/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/*
 *	Filename	= Main.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= kelas Main untuk menjalankan program

 *      Saya Azzahra Siti Hadjar 2100901 mengerjakan evaluasi Tugas Masa Depan dalam 
 *      mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahan-Nya 
 *      maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. 
 *      Aamiin.
 */

import view.Menu;
/**
 *
 * @author sitih
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // menampilkan tampilan Menu user
        Menu menu = new Menu();
        menu.setLocationRelativeTo(null); 
        menu.setVisible(true);
    }
    
}
