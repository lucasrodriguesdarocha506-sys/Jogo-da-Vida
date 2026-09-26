package tile;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];




    public TileManager(GamePanel gp){

        this.gp = gp;
        tile = new Tile[2];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage(){
        try {
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/vivo.png"));
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/morto.png"));


        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(){
        
        //int maux[][] = new int[gp.maxScreenCol][gp.maxScreenRow];
        Random random = new Random();
        // instância a matriz inicial
        for (int i = 0; i<gp.maxScreenCol; i++){
            for (int j = 0; j < gp.maxScreenRow;j++) {
                
                mapTileNum[i][j] = random.nextInt(2) * random.nextInt(2)* random.nextInt(2);
            }

        }
        
        
    }



    public void draw(Graphics2D g2){
        //g2.drawImage(tile[0].image, 0, 0 ,gp.tileSize, gp.tileSize, null);
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < gp.maxScreenCol && row < gp.maxScreenRow){
            int tileNum = mapTileNum[col][row];
           
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col ++;
            x+= gp.tileSize;
            if (col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row ++;
                y += gp.tileSize;
            }
        }



    }
    public void setMap(int matriz[][]){
        for (int i = 0; i< gp.maxScreenCol; i++){
            for (int j = 0; j < gp.maxScreenRow; j++){
                this.mapTileNum[i][j] = matriz[i][j];
            }
        }
    }
    public void getMap ( int matriz[][]){
        for (int i = 0; i< gp.maxScreenCol; i++){
            for (int j = 0; j < gp.maxScreenRow; j++){
                matriz[i][j] = this.mapTileNum[i][j];
            }
        }
    }

}
