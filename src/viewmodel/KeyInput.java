/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/*
 *	Filename	= KeyInput.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= viewmodel untuk proses inputan keyboard
 */

package viewmodel;

// import library
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// akses package viewmodel
import viewmodel.Game.STATE;

/**
 *
 * @author sitih
 */
public class KeyInput implements KeyListener{
    
    // atribut game
    private final Game game;

    public KeyInput(Game game) {
        // konstruktor
        this.game = game; 
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // menekan tombol
        switch (e.getKeyCode()) {
            // mengupate player movement menjadi true
            case KeyEvent.VK_UP -> game.getPlayer().setMoveUp(true); // atas
            case KeyEvent.VK_LEFT -> game.getPlayer().setMoveLeft(true); // kiri
            case KeyEvent.VK_RIGHT -> game.getPlayer().setMoveRight(true); // kanan
        }
        
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SPACE) {
            // key spasi untuk menghentikan game
            game.gameState = STATE.GameOver; // ubah state game menjadi game over
           
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // melepas tombol
        switch (e.getKeyCode()) {
            // mengupate player movement menjadi false
            case KeyEvent.VK_UP -> game.getPlayer().setMoveUp(false);// atas
            case KeyEvent.VK_LEFT -> game.getPlayer().setMoveLeft(false); // kiri
            case KeyEvent.VK_RIGHT -> game.getPlayer().setMoveRight(false); // kanan
        }
    }
    
}

