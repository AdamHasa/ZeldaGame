package Entity;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    //Spritecounter
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;

    //Collision
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    //character status
    public int maxLife;
    public int life;

    public Entity(GamePanel gp){
        this.gp = gp;

    }
}
