package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_LockedDoor extends SuperObject {
    public OBJ_LockedDoor(){
        name = "LockedDoor";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/door/small_door_locked.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

        collision = true;
    }
}
