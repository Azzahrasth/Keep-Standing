/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 *	Filename	= GameObject.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= model abstract untuk menampung data objek pada game
 */

package model;

// import library
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 *
 * @author sitih
 */
public abstract class GameObject {

    // atribut ukuran 
    protected int width; // lebar
    protected int height; // tinggi
    // atribut koordinat 
    protected float x; // koordinat x
    protected float y; // koordinat y
    
    // box pendeteksi collision
    protected Rectangle collisionBox;
  
    
    public GameObject(float x, float y, int width, int height) {
        // konstruktor
        
        // inisiasi atribut
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        
        // membuat box collision
        this.collisionBox = new Rectangle((int)x, (int)y, width, height);
    }
    
    public void updateCollisionBox(){
        // mengupdate posisi box collision
        this.collisionBox.x = (int) x;
        this.collisionBox.y = (int) y;
    }
    
    public void setX(float x){
        // mengisi kooridnat x
         this.x = x;
    }
    public void setY(float y){
        // mengisi kooridnat y 
        this.y = y;
    }
    public float getX(){
        // mengambil koordinat x
        return x;
    }
    public float getY(){
        // mengambil koordinat y
        return y;
    }

    public Rectangle getCollisionBox() {
        // mengambil box collision
        return this.collisionBox;
    }
    
    public abstract void render(Graphics g);
        // merender GameObject
}

