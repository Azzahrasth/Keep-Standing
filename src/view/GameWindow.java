/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/*
 *	Filename	= GameWindow.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= view untuk menmapilkan window game
 */

package view;

// import library
import viewmodel.Game;
import viewmodel.KeyInput;
import java.awt.Canvas;
import javax.swing.JFrame;


/**
 *
 * @author sitih
 */
public class GameWindow extends Canvas {
    
    // atrbit JFrame
    JFrame windowGame; // frame gameWindow
    JFrame menuFrame; //frame Menu
    
    public GameWindow(Game game){
        // konstruktor
        
        // setting window game
        String title = "Keep Standing"; // set title 
        windowGame = new JFrame(title); // membuat frame gameWindow
        windowGame.addKeyListener(new KeyInput(game)); // menambah key listener
        windowGame.add(game); // menambah game ke frame gameWindow
        
        // seeting window
        windowGame.setSize(800, 650); // set ukuran frame gameWindow
        windowGame.setLocationRelativeTo(null); // set lokasi frame gameWindow menjadi di tengah layar
        windowGame.setResizable(false); // set resizeable frame gameWindow
        windowGame.setVisible(true); // set frame gameWindow menjadi visible
        windowGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default close frame gameWindow
    }
    
    public void CloseWindow() {
        
        // menutup window/frame
        windowGame.setVisible(false); // set frame gameWindow menjadi invisible
        windowGame.dispose(); // membersihkan frame
        
        menuFrame = new Menu(); // membuat frame Menu
        menuFrame.setLocationRelativeTo(null); // set lokasi frame Menu menjadi di tengah layar
        menuFrame.setVisible(true); // set frame Menu menjadi visible
    }
}
