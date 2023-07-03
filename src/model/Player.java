/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 *	Filename	= Player.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= model untuk objek player pada game
 */

package model;

// import library
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// akses atribut global
import static model.Attributes.attribute.gameSpeed;

/**
 *
 * @author sitih
 */

public class Player extends GameObject {
    
    // atribut mengecek pergerakan player
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    
    private boolean inAir = true; // mengecek apakah player sedang diudara
    private boolean start = true; // mengecek start game

    private float airSpeed = 0; // kecepatan udara(koordinat y) 
    private float xSpeed = 0; // kecepatan pergerakan kanan kiri (koordinat x)
    private int score = 0, standing = 0; // menyimpan nilai score dan standing player
    
    // atribut kecepatan pada game
    private final float playerSpeed = 7.0f; // kecepatan player
    private final float jumpStrength = 3.30f; // kecepatan melompat
    private final float gravity = 0.07f; // kecepatan gravitasi
    
    private BufferedImage PlayerImg; // Buffer Image Player

    public Player(int x, int y) {
        // konstruktor
        super(x, y, 55, 55); // set properti parent
        setPlayerImage(); // set gambar player

    }

    @Override
    public void render(Graphics g) {
        // render objek player 
        g.drawImage(PlayerImg, (int) x, (int) y, 55, 55, null);

    }
    
    public void update(ArrayList<Obstacle> ob) {
        // mengupdate posisi dan colision obstable
        updatePosition(ob);
        updateCollisionBox();
    }

    public void updatePosition(ArrayList<Obstacle> AOb) {
        // mengupdate posisi dan kecepatan player ketika game berjalan
        if ( moveLeft && moveRight || !moveLeft && !moveRight ) {
            // jika berpindah ke kanan dan kiri secara bersamaan atau tidak berpindah ke kanan maupun kiri
            // player diam atau kecepatan xSpeed 0
            xSpeed = 0;
        } else if (moveLeft) {
            // jika berpindah ke kiri
            // kecepatan xSpeed berkurang
            xSpeed = xSpeed - (playerSpeed) - 2;
        } else if (moveRight) {
            // jika berpindah ke kanan
            // kecepatan xSpeed bertambah
            xSpeed += playerSpeed;
        }

        // menjaga kecepatan xSpeed
        if (xSpeed > 5) {
            // kecepatan maks xSpeed 4
            xSpeed = 5;
        } else if (xSpeed < -5) {
            // kecepatan min xSpeed -4
            xSpeed = -5;
        }

       
        if (moveUp && !inAir) 
        { // jika lompat  
            airSpeed -= jumpStrength; // kecepatan airSpeed berkurang
            inAir = true; 
        }

        
        if (!inAir && !isOnBranch(AOb)) 
        {   // jika sedang tidak di ranting pohon, berarti diudara
            inAir = true;
        }

        
        if (inAir) 
        {
            // jika di udara
            airSpeed += gravity; // kecepatan airSpeed bertambah
        }

        // menghitung score dan standing player serta collision player dengan balok rintangan
        cekCollision(AOb);

        x += xSpeed;
        y += airSpeed;
    }
    
    // menghitung score dan standing player
    // serta mengatur tumbukan antara player dengan balok rintangan
    public void cekCollision(ArrayList<Obstacle> AOb) {
        for (Obstacle ob : AOb) {
            // looping untuk setiap obstacle
            
            // jika player menyentuh balok rintangan            
            if (getBoundBawah().intersects(ob.getCollisionBox())) {
                // jika bound bawah player menyentuh collisionBox balok rintangan
                // berarti sedang berdiri di atas ranting pohon
                
                inAir = false; // set false di udara

                // update koordinat y player
                y = ob.getCollisionBox().y - height - 1; 

                // jika game baru di mulai
                if (start == true) {
                    start = false; // ubah nilai start
                    ob.setIsVisit(true); // ubah isVisit balok rintangan menjadi sudah di kunjungi
                } else { //  jika bukan
                    if (ob.getIsVisit() == false) { 
                        // jika balok rintangan/ranting pohon belum pernah di kunjungi sebelumnya
                        
                        // menambah nilai standing dan score
                        standing++;
                        score += ob.getObsScore();
                        
                        ob.setIsVisit(true);// ubah isVisit balok rintangan menjadi sudah di kunjungi
                    }
                }
            }

            if (getBoundAtas().intersects(ob.getCollisionBox())) {
                // jika bound atas player menyentuh collisionBox balok rintangan    
                
                // update koordinat y player
                y = ob.getCollisionBox().y + ob.getCollisionBox().height + 5;

            }
            if (getBoundKiri().intersects(ob.getCollisionBox())) {
                // jika bound kiri player menyentuh collisionBox balok rintangan   
                
                // kembalikan kecepatan player ke speed normal game
                xSpeed = gameSpeed;
                
                // update koordinat x player
                x = ob.getCollisionBox().x + ob.getCollisionBox().width;
            }

        }
    }

    public boolean isOnBranch(ArrayList<Obstacle> AOb) 
    {
        // method mengecek apakah player sedang di ranting
        
        // looping untuk setiap obstacle
        for (Obstacle ob : AOb) {
            // jika bound bawah player menyentuh collisionBox balok rintangan
            if (getBoundBawah().intersects(ob.getCollisionBox()))
                return true;
        }
        return false;
    }
    
    private void setPlayerImage() {
        // mengisi buffer image player dengan gambar owl
        try {
            // Read Image
            this.PlayerImg = ImageIO.read(new File("resources/images/owl.png").getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMoveLeft(boolean moveLeft) {
        // set player ke kiri
        this.moveLeft = moveLeft;
    }

    public void setMoveUp(boolean moveUp) {
        // set player ke atas
        this.moveUp = moveUp;
    }

    public void setMoveRight(boolean moveRight) {
        // set player ke kanan
        this.moveRight = moveRight;
    }

    public void setSkor(int skor) {
        // set nilai skor player
        this.score = skor;
    }

    public void setStanding(int standing) {
        // set nilai standing player
        this.standing = standing;
    }
    
     public int getSkor() {
        // mendapatkan skor fall
        return this.score;
    }
    
    public int getStanding() {
        // mendapatkan standing player
        return this.standing;
    }

    public Rectangle getBoundBawah() {
        // membuat batas bawah player
        return new Rectangle((int) (x + (width / 2) - (width / 4)), (int) (y + (height / 2) ), width / 2, height / 2);
    }

    public Rectangle getBoundAtas() {
        // membuat batas kanan player
        return new Rectangle((int) x + width - 5, (int) y + 5, 5, height - 10);
    }

    public Rectangle getBoundKiri() {
        // membuat batas kiri player
        return new Rectangle((int) x, (int) y + 5, 5, height - 10);
    }

}