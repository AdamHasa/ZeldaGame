package Monster;

import Main.GamePanel;
import Entity.Entity;

import java.awt.*;
import java.util.Random;

public class MON_Zol extends Entity{

    GamePanel gp;

    public MON_Zol(GamePanel gp){
        super(gp);

        this.gp = gp;

        name = "Zol";
        type = 2;
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage(){
        up1 = setup("/enemies/zol_01", gp.tileSize, gp.tileSize);
        up2 = setup("/enemies/zol_02", gp.tileSize, gp.tileSize);
        down1 = setup("/enemies/zol_01", gp.tileSize, gp.tileSize);
        down2 = setup("/enemies/zol_02", gp.tileSize, gp.tileSize);
        left1 = setup("/enemies/zol_01", gp.tileSize, gp.tileSize);
        left2 = setup("/enemies/zol_02", gp.tileSize, gp.tileSize);
        right1 = setup("/enemies/zol_01", gp.tileSize, gp.tileSize);
        right2 = setup("/enemies/zol_02", gp.tileSize, gp.tileSize);
    }

    public void setAction(){
        actionLockCounter++;

        if (actionLockCounter ==120){
            Random random = new Random();
            int i = random.nextInt(100);

            if(i<25){
                direction = "up";
            } else if (i<50) {
                direction = "down";
            } else if (i<75) {
                direction = "left";
            }else {
                direction = "right";
            }

            actionLockCounter =0;
        }
    }
}
