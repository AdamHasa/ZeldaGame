package Object;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class OBJ_Chest extends Entity {

    Image item;

    public OBJ_Chest(GamePanel gp) {
        super(gp);

        name = "Chest";
        down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);

        collision = true;
    }
}
