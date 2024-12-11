package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean zPressed, xPressed;
    private boolean pausePressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.playState){
            playState(code);
        }
        if (gp.gameState == gp.pauseState){
            pauseState(code);
        }
        if (gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
    }

    private void gameOverState(int code) {
        if(code == KeyEvent.VK_Z){
            gp.gameState = gp.playState;
            gp.setupGame();
        }
    }

    private void pauseState(int code) {
        if (code == KeyEvent.VK_P && !pausePressed) {
            gp.gameState = gp.playState;
            pausePressed = true;
        }
    }

    private void playState(int code) {
        if(code == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_Z){
            zPressed = true;
        }
        if(code == KeyEvent.VK_X){
            xPressed = true;
        }
        if (code == KeyEvent.VK_P && !pausePressed) {
            gp.gameState = gp.pauseState;
            pausePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_Z){
            zPressed = false;
        }
        if(code == KeyEvent.VK_X){
            xPressed = false;
        }
        if (code == KeyEvent.VK_P) {
            pausePressed = false;
        }

    }
}
