package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_SmallKey extends SuperObject {
    GamePanel gp;
    public OBJ_SmallKey(GamePanel gp){
        this.gp = gp;
        name = "SmallKey";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/small_key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
