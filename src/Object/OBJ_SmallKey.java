package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_SmallKey extends SuperObject {
    public OBJ_SmallKey(){
        name = "SmallKey";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/small_key.png"));
            System.out.println("retrieving image");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
