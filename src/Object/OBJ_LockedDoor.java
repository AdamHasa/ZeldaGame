package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_LockedDoor extends Entity {
    public OBJ_LockedDoor(GamePanel gp){
        super(gp);
        name = "LockedDoor";
        down1 = setup("/tiles/door/small_door_locked", gp.tileSize, gp.tileSize);

        collision = true;
    }
}
