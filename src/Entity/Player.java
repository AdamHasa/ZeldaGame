package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int smallKey = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8,16,25,25);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea = new Rectangle(36, 36);

        setDefaultValues();
        getPlayerImage();
        getAttackImage();
    }

    private void getPlayerImage() {
        up1 = setup("/player/player_up_01", gp.tileSize, gp.tileSize);
        up2 = setup("/player/player_up_02", gp.tileSize, gp.tileSize);
        down1 = setup("/player/player_down_01", gp.tileSize, gp.tileSize);
        down2 = setup("/player/player_down_02", gp.tileSize, gp.tileSize);
        left1 = setup("/player/player_left_01", gp.tileSize, gp.tileSize);
        left2 = setup("/player/player_left_02", gp.tileSize, gp.tileSize);
        right1 = setup("/player/player_right_01", gp.tileSize, gp.tileSize);
        right2 = setup("/player/player_right_02", gp.tileSize, gp.tileSize);
    }

    public void getAttackImage(){
        attackUp1 = setup("/player/attack/player_attack_up01", gp.tileSize, 28 * gp.scale);
        attackDown1 = setup("/player/attack/player_attack_down01", gp.tileSize, 27 * gp.scale);
        attackLeft1 = setup("/player/attack/player_attack_left01", gp.scale * 27 , gp.tileSize);
        attackRight1 = setup("/player/attack/player_attack_right01", gp.scale * 27, gp.tileSize);
    }

    public void update() {
        attackStatus();
        if (attacking){
            attack();

        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

//            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
//            interactNPC(npcIndex);
            
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

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
        }
        // invincibilty
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter >120){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    private void attack() {
        spriteCounter++;
        if (spriteCounter<30){

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction){
                case "up": worldY -= attackArea.height;
                break;
                case "down": worldY += attackArea.height;
                break;
                case "left": worldX -= attackArea.width;
                break;
                case "right": worldX += attackArea.width;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }else {
            spriteCounter = 0;
            attacking = false;
        }

    }

    private void attackStatus() {
        if (keyH.enterPressed == true){
            attacking = true;
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "SmallKey":
                    gp.playSE(1);
                    smallKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Picked up key");
                    break;
                case "LockedDoor":
                    if(smallKey >0){
                        gp.playSE(2);
                        smallKey--;
                        gp.obj[i] = null;
                    }else {
                        //TODO play sound effect
                    }

                    break;
            }
        }
    }
    private void setDefaultValues() {
        worldX = gp.tileSize * 4;
        worldY = gp.tileSize * 4;
        speed = 4;
        direction = "down";

        //player status
        maxLife = 6;
        life = maxLife;
        
    }

    public void contactMonster(int i){
        if (i != 999){
            if (!invincible){
                life -=1;
                invincible = true;

                //knockback need work to make it smooth
//                switch (direction) {
//                    case "up":
//                        worldY += gp.tileSize;
//                        break;
//                    case "down":
//                        worldY -= gp.tileSize;
//                        break;
//                    case "left":
//                        worldX += gp.tileSize;
//                        break;
//                    case "right":
//                        worldX -= gp.tileSize;
//                        break;
//                }
            }
        }

    }

    public void damageMonster(int i){
        if (i != 999){
            if (!gp.monster[i].invincible){
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;

                if (gp.monster[i].life <=0){
                    gp.monster[i].dying = true;

                }

            }
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction){
            case "up":
                if (!attacking){
                    if (spriteNum ==1){image = up1;}
                    if (spriteNum ==2){image = up2;}
                }
                if (attacking){
                    tempScreenY = screenY - gp.tileSize;
                    image = attackUp1;
                }
                break;
            case "down":
                if (!attacking){
                    if (spriteNum ==1){image = down1;}
                    if (spriteNum ==2){image = down2;}
                }
                if (attacking){
                    image = attackDown1;
                }
                break;
            case "left":
                if (!attacking){
                    if (spriteNum ==1){image = left1;}
                    if (spriteNum ==2){image = left2;}
                }
                if (attacking){
                    tempScreenX = screenX - gp.tileSize;
                    image = attackLeft1;
                }
                break;
            case "right":
                if (!attacking){
                    if (spriteNum ==1){image = right1;}
                    if (spriteNum ==2){image = right2;}
                }
                if (attacking){
                    image = attackRight1;
                }
                break;
        }

        if (invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
