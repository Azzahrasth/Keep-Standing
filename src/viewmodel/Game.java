/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 *	Filename	= Game.java
 *	Author		= Azzahra Siti Hadjar
 *	NIM		= 2100901 
 *      Email           = azzahrasth@upi.edu
 *	Deskripsi 	= viwmodel untuk proses jalan nya game
 */

package viewmodel;

// import libarary
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

// akses package model
import model.Player;
import model.TableScore;
// akses package view
import view.GameWindow;
// akses atribut global
import static model.Attributes.attribute.gameWidth;
import static model.Attributes.attribute.gameHeight;


/**
 *
 * @author sitih
 */
public class Game extends JPanel implements Runnable {
    
    // atribut thread untuk animasi
    private Thread gameThread;
    private boolean running = false; // untuk mendeteksi berjalan nya game
    
    // atribut window dan musik
    private GameWindow windowGame; // window game
    private Clip musik; // backsound
    
    // atribut objek - objek pada game
    private final Player player; // player
    private final ObstacleHandler obsHandler; // obstacle
    private String username; // username player
    private int score; // skor score player
    private int standing; // skor standing player
    private TableScore tableScore; // data tabel tscore pada database
    
    private BufferedImage bgImg;  // Buffer Image background game
    
    // state game
    public enum STATE{
        Play,
        GameOver
    }
    
    public Game(String username){
        // konstruktor
        
        // mengisi buffer image background dengan gambar bg_malam2
        try {
            // Read Image
            this.bgImg = ImageIO.read(new File("resources/images/bg_malam2.jpg").getAbsoluteFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        
       
        // membuat obstacle
        this.obsHandler = new ObstacleHandler();
        
        // membuat player
        this.player  = new Player(70, 200);
        
        // mengeset username
        this.username = username;
        
        // kalkulasi skor dan standing player game di awal game
        KalkulasiScore(); 
        
        // membuat backsound
        Sound bgm = new Sound();
        musik = bgm.playSound(this.musik, "2_Game_Play.wav");
    }
    
    // inisialisasi state game menjadi play
    public STATE gameState = STATE.Play;
    
    public synchronized void StartGame(GameWindow gw){
        // memulai menjalankan game
        gameThread = new Thread(this); // buat thread baru
        gameThread.start(); // thread dijalankan
        this.windowGame = gw; // buat window game
        running = true; // set running menjadi true
    }
    
    @Override
    public void paint(Graphics g){
    // paint komponen / onjek game
        super.paint(g); // method parent
        
        // render background
        g.drawImage(bgImg, 0,0,  gameWidth, gameHeight, null);
        
        // render player
        player.render(g); 
        
        // render obstacle
        obsHandler.renderObstacle(g); 
        
        // render tampilan score dan standing
        g.setColor(Color.WHITE);
        g.fillRoundRect(5, 5, 100, 20, 20, 20);
        g.fillRoundRect(5, 30, 100, 20, 20, 20);
        g.setColor(Color.decode("#17434A"));
        g.drawString("Skor : " + Integer.toString(this.score), 10, 20);
        g.drawString("Standing : " + Integer.toString(this.standing), 10, 45);
    }
    
    @Override
    public void run() {
        // menjalankan thread animasi
        while(true){
            // looping game
            try {
                updateGame(); // update game
                repaint(); // render ulang Component (update paint())
                Thread.sleep(1000L/60L); // pause thread
                
                // set nilai score dan standing game
                this.score = player.getSkor(); // mengambil nilai score player
                this.standing = player.getStanding(); // mengambil nilai standing player
                
                // kondisi player apabila gameover
                if(this.player.getBoundBawah().y   > (gameHeight - 40) || this.player.getBoundAtas().y   < 9) {  
                // jika bound atas dan bawah player menyentuh batas game
                    this.gameState = STATE.GameOver; // ubah state game menjadi GameOver
                }
                if(gameState == STATE.GameOver) {
                    // jika state game GameOver
                    
                    // stop musik
                    new Sound().stopSound(this.musik);

                    saveScore(); // simpan nilai score dan standing
                    
                    closeWindow(); // tutup window game
                    
                    stopGame(); // stop game
                }
            } catch (InterruptedException ex) {
                // log error
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void updateGame(){
        // update objek dalam game
        obsHandler.addObstacle(); // menambah obstacle
        obsHandler.updateObstacle(); // mengupdate kondisi obstacle
        player.update(obsHandler.getObstacles()); // mengupdate kondisi player
    }
    
    
     public void setScore(int score, int standing) {
        // mengeset skor player di awal game
        this.player.setSkor(score);
        this.player.setStanding(standing);
    }
    
    
    public void KalkulasiScore(){
    // kalkulasi nilai skor dan standing player di awal game
        try {
            this.tableScore = new TableScore();
            // mengambil data username pada tabel score
            this.tableScore.getDataTScore(this.username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
         
        boolean ada = false;
        int c = 0, score = 0, standing = 0;
        try {
            while(tableScore.getResult().next()){
                // jika data username sudah ada di database,
                // ambil nilai score dan standing nya
                score = Integer.parseInt(tableScore.getResult().getString(2));
                standing = Integer.parseInt(tableScore.getResult().getString(3));
                ada = true;
           }
       } catch (Exception ex) {
            System.out.println(ex.getMessage());
       }
            
            // set nilai score adapt dan fall di awal game
            if(ada == true){
                // jika user lama, set nilai score dan standing sesuai data 
                this.setScore(score, standing);
            } else {
                // jika user baru, set nilai score dan standing 0
                this.setScore(0,0);
            }
    }
    
    public void saveScore() {
        // menyimpan skor ketika game over
        
        // play musik untuk menu game
        musik  = new Sound().playSound(this.musik, "3_Game_Over.wav");
        
        try {
            // menambah atau mengupdate data player
            this.tableScore = new TableScore();
            this.tableScore.insertData(this.username, this.score, this.standing);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // menampilan panel game over
        JOptionPane.showMessageDialog(null, "Username    : " + this.username + "\nScore    : " + this.score + "\nStanding    : " + this.standing, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        
        // stop sound saat panel game over hilang
        new Sound().stopSound(this.musik);
    }
    
     public synchronized void stopGame() {
         // menghentikan game
        try{
            gameThread.join(); // menghentikan thread
            running = false; // set tidak berjalan
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void closeWindow() {
        // menutup window
        windowGame.CloseWindow();
    }
    
    public Player getPlayer(){
        // mengambil player
        return this.player;
    }
    
}
