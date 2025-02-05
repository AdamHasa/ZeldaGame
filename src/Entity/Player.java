package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Object.*;

public class Player extends Entity{
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int smallKey = 0;
    public Entity fanfareItem;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 10;

    public Entity equipedItem;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);

        damage = 2;

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
        setItems();
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

        player_fanfare = setup("/player/player_fanfare", gp.tileSize, gp.tileSize);
    }

    public void getAttackImage(){
        attackUp1 = setup("/player/attack/player_attack_up01", gp.tileSize, 28 * gp.scale);
        attackDown1 = setup("/player/attack/player_attack_down01", gp.tileSize, 27 * gp.scale);
        attackLeft1 = setup("/player/attack/player_attack_left01", gp.scale * 27 , gp.tileSize);
        attackRight1 = setup("/player/attack/player_attack_right01", gp.scale * 27, gp.tileSize);
    }

    public void update() {
        if (life<= 0){
            gp.stopMusic();
            gp.playMusic(8);
            dying = true;

        }
        
        attackStatus();
        if (attacking){
            attack();

        } else if (fanfare) {
            openChest();
        } else if ((keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) && shotAvailableCounter >=15) {
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

        if(keyH.xPressed && !projectile.alive && shotAvailableCounter ==30){

            projectile.set(worldX, worldY, direction, true, this);

            gp.projectileList.add(projectile);

            shotAvailableCounter =0;
        }
        // invincibilty
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter >120){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter <30){
            shotAvailableCounter++;
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
            damageMonster(monsterIndex, damage);

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
        if (keyH.zPressed == true){
            if(!attacking){
                gp.playSE(6);
            }
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
                case "Chest":
                    gp.playSE(7);
                    fanfare = true;
                    if (gp.obj[i].containedItem != null) {
                        Entity item = gp.obj[i].containedItem;
                        this.fanfareItem = item;
                        switch (item.name){
                            case "SmallKey":
                                smallKey++;
                                break;
                            case "Hookshot":
                                OBJ_Hookshot hookshot = new OBJ_Hookshot(this.gp);
                                inventory.add(hookshot);
                                break;
                        }
                    }
                    int openedWorldX = gp.obj[i].worldX;
                    int openedWorldY = gp.obj[i].worldY;
                    gp.obj[i] = new OBJ_ChestOpened(gp);
                    gp.obj[i].worldX = openedWorldX;
                    gp.obj[i].worldY = openedWorldY;
            }
        }
    }

    private void openChest() {
        spriteCounter++;
        if (spriteCounter<60){

        }else {
            fanfare = false;
            spriteCounter = 0;
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

    public void setItems(){
        projectile = new OBJ_Arrow(this.gp);
        equipedItem = projectile;
        inventory.add(projectile);
    }

    public void contactMonster(int i){
        if (i != 999){
            if (!invincible && gp.monster[i].life > 0 ){
                gp.playSE(5);
                life -= gp.monster[i].damage;
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

    public void damageMonster(int i, int damagePoints){
        if (i != 999){
            if (!gp.monster[i].invincible){
                gp.monster[i].life -= damagePoints;
                gp.playSE(4);
                gp.monster[i].invincible = true;

                if (gp.monster[i].life <=0){
                    gp.playSE(3);
                    gp.monster[i].dying = true;

                }

            }
        }

    }

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot();

        if (itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_equipment){
                equipedItem = selectedItem;
            }
            if (selectedItem.type == type_consumable){
                //TODO
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

        if (fanfare){
            image = player_fanfare;
            fanfareItem.worldX = this.worldX;
            fanfareItem.worldY= this.worldY - gp.tileSize;
            gp.entityList.add(fanfareItem);
        }

        if (invincible && !dying){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        if (dying){
            gp.gameState = 999;
            dyingAnimation(g2);
            if (!dying){
                gp.gameState = gp.gameOverState;
            }
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
