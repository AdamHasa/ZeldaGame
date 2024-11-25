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
            drawPauseScreen();
        }
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

    public void drawPauseScreen() {
        String text = "PAUSE";
        int x = getXForCenteredText(text);

        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
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
