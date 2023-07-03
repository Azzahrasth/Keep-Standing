/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/*
 *	Filename	= Obstacle.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= model untuk objek balok rintangan pada game
 */

package model;

// import library
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author sitih
 */
public class Obstacle extends GameObject{
    // atribut 
    private boolean isVisit; //  untuk mengecek apakah obstacle pernah di kunjungi
    private int obsScore; // score obstacle
    private final int obsType; // tipe obstacle (batang/ranting pohon)
    private BufferedImage Obsimg;  // Buffer Image Obstacle
    // inisialisasi library
   
    public Obstacle(float x, float y, int width, int height, int obsType) {
        // konstruktor
        super(x, y, width, height);
        this.obsType = obsType;
        this.isVisit= false; // set obstacle belum pernah dikungjungi
        this.obsScore= 0; // set score obstacle = 0
        setObsImage();
    }
    
    public void setIsVisit(boolean b){
        // mengisi setVisit 
        this.isVisit = b;
    }
     
    public void setObsScore(int a){
        // mengisi score obstacle
        this.obsScore =a;
    }
    
    private void setObsImage(){
        // mengisi gambar obstacle
        
        // jika tipe obstacle 0 (batang pohon) maka menggunakan gambar texture_batang
        if(this.obsType  == 0)
        {    
            try {
            // Read Image
             Obsimg = ImageIO.read(new File("resources/images/texture_batang.jpeg").getAbsoluteFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // jika tipe obstacle 1 (ranting pohon) maka menggunakan gambar texture_daun
        else{
            
            try {
            // Read Image
            Obsimg = ImageIO.read(new File("resources/images/texture_daun.jpeg").getAbsoluteFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean getIsVisit(){
       // mengambil isVisit 
        return this.isVisit;
    }
    
    public int getObsScore(){
        // mengambil score obstacle
        return this.obsScore;
    }
    
    public int getObsType(){
        // mengambil type obstacle
        return this.obsType;
    }
    
    
    @Override
    public void render(Graphics g) {
        // override fungsi render dari parent
        
        // render obstacle
        g.drawImage(Obsimg, (int)x,(int) y, width, height, null);
        
        // jika tipe obstacle 1 (ranting pohon) maka render score obstacle
        if(obsType == 1)
        {
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(getObsScore()), (int)x +5 + width, (int)y+30);
        } 
    }
    


}
