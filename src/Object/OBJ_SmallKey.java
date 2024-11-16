package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_SmallKey extends Entity {
    public OBJ_SmallKey(GamePanel gp){
        super(gp);
        name = "SmallKey";
        down1 = setup("/objects/small_key", gp.tileSize, gp.tileSize);
    }
}
