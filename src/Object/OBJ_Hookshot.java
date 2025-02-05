package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Hookshot extends Entity {

    GamePanel gp;
    public OBJ_Hookshot(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_equipment;
        name = "Hookshot";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        damage = 1;
        alive = false;
        down1 = setup("/objects/hookshot", gp.tileSize, gp.tileSize);
    }
}
