package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;

    //images
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, player_fanfare;
    public BufferedImage attackUp1, attackDown1, attackLeft1, attackRight1;
    public BufferedImage image, image2, image3;

    //invincibility
    public boolean invincible = false;
    public int invincibleCounter;


    //Spritecounter
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int actionLockCounter = 0;

    //Collision
    public boolean collision = false;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public  Rectangle attackArea = new Rectangle(0,0,0,0);

    //character status
    public int maxLife;
    public int life;
    public int speed;
    public int type; //0: player, 1:npc, 2:monster
    public String name;
    public String direction = "down";
    boolean attacking = false;
    boolean fanfare = false;
    public int damage;

    //projectile
    public Projectile projectile;
    public int shotAvailableCounter;

    //dying
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;

    //
    public Entity containedItem;

    public Entity(GamePanel gp){
        this.gp = gp;

    }
    public void setAction(){

    }
    public void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type ==2 && contactPlayer){
            if (!gp.player.invincible){
                gp.playSE(5);
                gp.player.life -= 1;
                gp.player.invincible = true;

            }
        }

        if(!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
        spriteCounter++;
        if(spriteCounter >10){
            if(spriteNum ==1){
                spriteNum =2;
            }
            else if (spriteNum ==2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible){
            invincibleCounter++;
            if (invincibleCounter >40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            switch (direction){
                case "up":
                    if (spriteNum ==1){
                        image = up1;
                    }
                    if (spriteNum ==2){
                        image = up2;
                    }

                    break;
                case "down":
                    if (spriteNum ==1){
                        image = down1;
                    }
                    if (spriteNum ==2){
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum ==1){
                        image = left1;
                    }
                    if (spriteNum ==2){
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum ==1){
                        image = right1;
                    }
                    if (spriteNum ==2){
                        image = right2;
                    }
                    break;
            }

            if (invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }

            if (dying){
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if (this == gp.player){
            i = 20;
        }

        int step = dyingCounter / i;

        if (step < 9) {
            float alpha = (step % 2 == 0) ? 0f : 1f;
            changeAlpha(g2, alpha);
        } else {
            dying = false;
            alive = false;
        }
    }


    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;

        try{
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, width, height);

        }catch (IOException e){
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void setContainedItem(Entity entity) {
    }
}
