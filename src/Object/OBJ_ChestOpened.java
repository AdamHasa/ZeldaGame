package Object;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class OBJ_ChestOpened extends Entity {

    public OBJ_ChestOpened(GamePanel gp) {
        super(gp);

        name = "ChestOpened";
        down1 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);

        collision = true;
    }
}
