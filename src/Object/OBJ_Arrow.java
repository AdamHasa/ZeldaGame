package Object;

import Entity.Projectile;
import Main.GamePanel;

public class OBJ_Arrow extends Projectile {
    GamePanel gp;
    public OBJ_Arrow(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_equipment;
        name = "Arrow";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        damage = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/player/projectile/Arrow_Up", gp.tileSize, gp.tileSize);
        left1 = setup("/player/projectile/Arrow_Left", gp.tileSize, gp.tileSize);
        right1 = setup("/player/projectile/Arrow_Right", gp.tileSize, gp.tileSize);
        down1 = setup("/player/projectile/Arrow_Down", gp.tileSize, gp.tileSize);
    }

    public void activateItem() {
        if (!gp.player.projectile.alive && gp.player.shotAvailableCounter ==30){
            gp.player.projectile.set(gp.player.worldX, gp.player.worldY, gp.player.direction, true, gp.player);

            gp.projectileList.add(gp.player.projectile);

            gp.player.shotAvailableCounter =0;
        }
    }
}
