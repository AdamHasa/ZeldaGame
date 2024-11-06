package Main;

import Object.OBJ_SmallKey;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_SmallKey();
        gp.obj[0].worldX = 3 * gp.tileSize;
        gp.obj[0].worldY = 3 * gp.tileSize;
        System.out.println(gp.obj[0].worldX);
        System.out.println(gp.obj[0].worldY);
        System.out.println(gp.obj[0].worldY);
    }
}
