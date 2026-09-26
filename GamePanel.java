package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 4; // 16x16 tile
    final int scale = 1;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 512;
    public final int maxScreenRow = 384;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public int speed =1;
    int FPS = 2;

    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval= 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer =0;
        int drawCount = 0;

        while (gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >=1){
                update();
                repaint();
                delta --;
                drawCount ++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                timer = 0;
                drawCount = 0;
            }

        }
        
    }
    public void update(){

        int matriz[][] = new int[maxScreenCol][maxScreenRow];
        int maux[][] = new int[maxScreenCol][maxScreenRow];
        tileM.getMap(matriz);
        for(int i = 0; i< maxScreenCol; i++){
                for(int j = 0; j < maxScreenRow; j++ ){
                    
                    // faz uma verificacao do index antes de escrever a prox casa
                
                    if (i-1 <0){
                        if (j-1 <0){
                            ////////////////////////////////
                             if ((matriz[i+1][j] + matriz[i+1][j+1] + matriz[i][j+1]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                            /////////////////////////////// 
                        }
                        else if (j + 1 > maxScreenRow - 1){

                            /////////////////////////////////////////
                            if ((matriz[i+1][j] + matriz[i+1][j-1] + matriz[i][j-1]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                             ///////////////////////////////////////
                        }
                        else{
                            ////////////////////////////////////
                            if ((matriz[i][j-1] + matriz[i][j+1] + matriz[i+1][j-1] + matriz[i+1][j] + matriz[i+1][j+1]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                             ///////////////////////////////////////
                        }
                    }
                    else if (i+1 >maxScreenCol -1){
                        if (j-1 < 0){
                            ////////////////////////////////////////////
                            if ((matriz[i-1][j] + matriz[i-1][j+1] + matriz[i][j+1]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                            ////////////////////////////////////////////
                        }
                        else if(j+1 > maxScreenRow -1){
                            //////////////////////////////////////////////
                            if ((matriz[i-1][j] + matriz[i-1][j-1] + matriz[i][j-1]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                            //////////////////////////////////////////////
                        }
                        else{
                            //////////////////////////////////////////////
                            if ((matriz[i-1][j] + matriz[i-1][j-1] + matriz[i-1][j+1] + matriz[i][j-1] + matriz[i][j+1]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                            //////////////////////////////////////////////
                        }
                    }
                    else if (j-1 < 0){
                        ///////////////////////////////////////////////////
                        if ((matriz[i-1][j] + matriz[i-1][j+1] + matriz[i][j+1] + matriz[i+1][j+1] + matriz[i+1][j]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                        ////////////////////////////////////////////////////
                    }
                    else if (j+1 > maxScreenRow -1){
                        ///////////////////////////////////////////////////
                        if ((matriz[i-1][j] + matriz[i-1][j-1] + matriz[i][j-1] + matriz[i+1][j-1] + matriz[i+1][j]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                        ////////////////////////////////////////////////////
                    }
                    else{
                        ///////////////////////////////////////////////////
                        if ((  matriz[i-1][j-1] + matriz[i-1][j] + matriz[i-1][j+1] + matriz[i][j-1]+ matriz[i][j+1]
                             + matriz[i+1][j-1] + matriz [i+1][j] + matriz[i+1][j+1]) ==3){
                                maux[i][j] = 1;
                             }
                             else{
                                maux[i][j] = 0;
                             }
                        ////////////////////////////////////////////////////
                    }
                }
            }
            
            //preenche a matriz original com os novos elementos
           tileM.setMap(maux);

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);
        g2.dispose();
         
        




    }

}
