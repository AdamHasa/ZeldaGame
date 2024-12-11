package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

import Entity.Entity;
import Object.*;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public int slotCol = 0;
    public int slotRow = 0;

    Graphics2D g2;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_SmallKey key = new OBJ_SmallKey(gp);
        keyImage = key.down1;
        

        //Hud Object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        drawItemUI();

        drawPlayerLife();

        drawMessage();
        
        if (gp.gameState == gp.playState){
        }
        if (gp.gameState == gp.pauseState){

            drawInventory();
        }
        if (gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
    }

    public void drawInventory() {
        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*3;

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        final int slotXStart = frameX +20;
        final int slotYStart = frameY +20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        int cursorX = slotXStart + (gp.tileSize * slotCol);
        int cursorY = slotYStart + (gp.tileSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10,10);

    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25,25);
    }

    private void drawGameOverScreen() {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(arial_80B);
        g2.setColor(Color.white);
        String text = "GAME OVER";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2 - gp.tileSize;
        g2.drawString(text, x, y);

        g2.setFont(arial_40);
        text = "Press Z to start again";
        x = getXForCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
    }

    private void drawMessage() {
        if(messageOn){
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

            messageCounter++;

            if(messageCounter > 120){
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    private void drawItemUI() {
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2 + 80, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.smallKey, 74, gp.tileSize/2 + 120);
    }
    public void drawPlayerLife() {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //Maxlife
        while (i<gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x+= gp.tileSize;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        while (i<gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x+= gp.tileSize;
        }
    }

    public int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
