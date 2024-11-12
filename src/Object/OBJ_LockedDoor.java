package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_LockedDoor extends SuperObject {
    GamePanel gp;
    public OBJ_LockedDoor(GamePanel gp){
        this.gp = gp;
        name = "LockedDoor";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/door/small_door_locked.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }

        collision = true;
    }
}
