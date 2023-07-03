/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 *	Filename	= ObstacleHandler.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= viwmodel untuk proses mengatur obastacle pada game
 */

package viewmodel;

// import library
import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

// akses konstanta
import static model.Attributes.attribute.*;

// akses package model
import model.Obstacle;

/**
 *
 * @author sitih
 */
public class ObstacleHandler {
    
    // atribut mengatur ukuran obstacleobstacle
    private final int MIN_WIDTH = 200; // panjang balok rintangan x minimal
    private final int MAX_WIDTH = gameWidth - 300; // panjang balok rintangan (obstacle) x maksimal    
    private final int MAX_OBSTACLE = 20; // jumlah maks obstacle dalam frame
    private final int MIN_GAP =60; // tinggi gap minimum antar obstacle
    private int OBS_HEIGHT = 50; // tinggi obstacle
    private int obsNumber = 0; // jumlah obstacle dalam frame
   
    private final Random rand = new Random(); // inisialisasi library random
    private final ArrayList<Obstacle> AOb = new ArrayList<>(); // array list obstacle
    
    public ObstacleHandler() {
        // konstruktor
        makeTrunk(); // membuat batang pohon
    }
    
    private void makeTrunk(){
        // membuat obstacle batang pohon
        
        int trunkWidth = 75; // lebar pohon
        // nilai obsType batang pohon = 0 
        Obstacle trunk = new Obstacle(0, 53, trunkWidth,gameHeight, 0);
        AOb.add(trunk); // tambah obs batang ke list AOb
        obsNumber++; // menambah nilai jumlah obstacle
    }
    
      public void addObstacle(){
        // membuat / menambah obstacle ranting pohon
        
        if(obsNumber < MAX_OBSTACLE){
            // jika jumlah obstacle dalam frame masih kurang dari batas jumlah maks obstacle
            
            float y = gameHeight / 2 + 150 ; // koordinat y di tengah tengah
           
            // merandom lebar obstacle 
            float obs_width = rand.nextInt(MAX_WIDTH  - MIN_WIDTH) + MIN_WIDTH; 
            
            if(obsNumber > 1){
                // menentukan celah obstacle atau koordinat y obstacle berikutnya
                y =  AOb.get(AOb.size() - 1).getY() + ((rand.nextInt(4 - 1) + 1)*MIN_GAP);
            }
            
            // membuat obstacle ranting pohon baru          
            // nilai obsType batang pohon = 0 
            Obstacle branch = new Obstacle(75, y,(int) obs_width, OBS_HEIGHT, 1);
            branch.setObsScore(100-((int)obs_width / 50)*(10));
            
            AOb.add(branch); // menambah obs ranting ke list AOb
            obsNumber++; // menambah nilai jumlah obstacle
        }
    }
    
    public void updateObstacle(){
        // mengupdate kondisi obstacle ranting pohon
        
        Iterator<Obstacle> itr = AOb.iterator(); // iterator untuk setiap obstacle
        while(itr.hasNext()) {
            // selama obstacle ada
            Obstacle ob = itr.next();
            
            if(ob.getObsType() == 1){
                // menghapus obstacle ranitng yang keluar batas atas batang pohon
                if(ob.getY() < 53){
                    // jika posisi y obs ranting melebihi batas y obs batang
                    itr.remove(); // hapus obstacle
                    obsNumber--; // mengurangi nilai jumlah obstacle
                }else{
                    // jika tidak melebihi
                    // update posisi obs ranting
                    ob.setY(ob.getY()- gameSpeed); 
                    // pdate collisionBox obs ranting
                    ob.updateCollisionBox();
                    
                }
            }
        }
    }
    
    public void renderObstacle(Graphics g){
        // merender obstacle
        for (Obstacle ob : AOb) {
            // untuk setiap obstacle
            ob.render(g); // render objek obastacle
        }
    }

    public ArrayList<Obstacle> getObstacles() {
        // mengambil obstacle
        return AOb;
    }
    
}
