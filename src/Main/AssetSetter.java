package Main;

import Monster.MON_Zol;
import Object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_SmallKey(gp);
        gp.obj[0].worldX = 3 * gp.tileSize;
        gp.obj[0].worldY = 3 * gp.tileSize;

        gp.obj[1] = new OBJ_SmallKey(gp);
        gp.obj[1].worldX = 5 * gp.tileSize;
        gp.obj[1].worldY = 5 * gp.tileSize;

        gp.obj[2] = new OBJ_LockedDoor(gp);
        gp.obj[2].worldX = 1 * gp.tileSize;
        gp.obj[2].worldY = 6 * gp.tileSize;

        OBJ_SmallKey smallKey = new OBJ_SmallKey(gp);
        gp.obj[3] = new OBJ_Chest(gp);
        gp.obj[3].worldX = 11 * gp.tileSize;
        gp.obj[3].worldY = 7 * gp.tileSize;
        gp.obj[3].setContainedItem(smallKey);

        OBJ_Hookshot hookshot = new OBJ_Hookshot(gp);
        gp.obj[4] = new OBJ_Chest(gp);
        gp.obj[4].worldX = 11 * gp.tileSize;
        gp.obj[4].worldY = 3 * gp.tileSize;
        gp.obj[4].setContainedItem(hookshot);

    }

    public void setMonster(){
        gp.monster[0] = new MON_Zol(gp);
        gp.monster[0].worldX = 12 * gp.tileSize;
        gp.monster[0].worldY = 4 * gp.tileSize;

        gp.monster[1] = new MON_Zol(gp);
        gp.monster[1].worldX = 16 * gp.tileSize;
        gp.monster[1].worldY = 4 * gp.tileSize;
    }
}
