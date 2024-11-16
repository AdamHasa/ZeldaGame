package Main;

import Monster.MON_Zol;
import Object.OBJ_SmallKey;
import Object.OBJ_LockedDoor;

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
