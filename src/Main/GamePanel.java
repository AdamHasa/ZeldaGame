package Main;

import Entity.Entity;
import Entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    //Tilesize
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    //ScreenSize
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576
    public int maxWorldCol;
    public int maxWorldRow;

    //collision
    public CollisionChecker cChecker = new CollisionChecker(this);

    //asset setter
    public AssetSetter aSetter = new AssetSetter(this);
    public Entity[] npc;

    // FPS
    int FPS = 60;

    TileManager tileM;

    //Keyhandler
    KeyHandler keyH = new KeyHandler();

    //sound
    Sound music = new Sound();
    Sound se = new Sound();

    public UI ui = new UI(this);

    //Thread
    Thread gameThread;

    //Player and Entity
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity monster[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        loadMap("res\\maps\\world01.txt");

        tileM = new TileManager(this);
    }
    public void setupGame() {
        aSetter.setObject();
        aSetter.setMonster();

        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void loadMap(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int rowCount = 0;
            int colCount = 0;

            // Read through the file to calculate maxWorldCol and maxWorldRow
            while ((line = br.readLine()) != null) {
                String[] tiles = line.trim().split("\\s+"); // Split by whitespace
                colCount = Math.max(colCount, tiles.length); // Set colCount to the longest row found
                rowCount++; // Count the rows
            }

            // Set maxWorldCol and maxWorldRow based on file contents
            maxWorldCol = colCount;
            maxWorldRow = rowCount;

            System.out.println("World Size: " + maxWorldCol + " columns, " + maxWorldRow + " rows");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the map file.");
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null){

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){

        player.update();

        for (int i = 0; i < monster.length; i++){
            if (monster[i] != null){
                monster[i].update();
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;


//        for (int i = 0; i< obj.length; i++){
//            if (obj[i] != null){
//                obj[i].draw(g2,this);
//            }
//        }

        tileM.draw(g2);

        entityList.add(player);

        //voor npc's
//        for(int i = 0; i< npc.length; i++){
//            if(npc[i] != null){
//                entityList.add(npc[i]);
//            }
//        }

        for(int i = 0; i< monster.length; i++){
            if(monster[i] != null){
                entityList.add(monster[i]);
            }
        }

        for(int i = 0; i<obj.length; i++){
            if(obj[i] != null){
                entityList.add(obj[i]);
            }
        }

        Collections.sort(entityList, new Comparator<Entity>(){

            @Override
            public int compare(Entity e1, Entity e2) {
                int result = Integer.compare(e1.worldY, e2.worldY);
                return result;
            }
        });

        for (int i= 0; i<entityList.size(); i++){
            entityList.get(i).draw(g2);
        }
        entityList.clear();

        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
