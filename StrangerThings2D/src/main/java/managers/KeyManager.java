package managers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import main.GamePanel;

import java.io.IOException;

/**
 * The KeyManager class is responsible for managing key events and keeping track of which keys are currently pressed.
 * It implements the EventHandler interface for handling KeyEvent events.
 */
public class KeyManager implements EventHandler<KeyEvent> {
    private final GamePanel gamePanel;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    /**
     * Constructs a KeyManager object with the specified GamePanel.
     *
     * @param gamePanel The GamePanel associated with this KeyManager.
     */
    public KeyManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Handles the key events.
     *
     * @param e The KeyEvent to be handled.
     */
    @Override
    public void handle(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (e.getCode()){
                case W:
                    upPressed = true;
                    break;
                case S:
                    downPressed = true;
                    break;
                case A:
                    leftPressed = true;
                    break;
                case D:
                    rightPressed = true;
                    break;
                case I:
                    gamePanel.getInventoryManager().setOpened(!gamePanel.getInventoryManager().isOpened());
                    break;
                case DIGIT1:
                    gamePanel.getPlayer().heal();
                    break;
                case F:
                    if (gamePanel.getItemManager().checkIfItemIsInInventory("AttackItem")) {
                    gamePanel.getPlayer().setAttackIsActive(true);
                    }
                    break;
                case R:
                    try {
                        gamePanel.restartGame();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
            }
        }

        if (e.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (e.getCode()){
                case W:
                    upPressed = false;
                    break;
                case S:
                    downPressed = false;
                    break;
                case A:
                    leftPressed = false;
                    break;
                case D:
                    rightPressed = false;
                    break;
            }
        }
    }

    /**
     * Checks if the W ("up") key is currently pressed.
     *
     * @return true if the key is pressed, false otherwise.
     */
    public boolean isUpPressed() {
        return upPressed;
    }
    /**
     * Checks if the S ("down") key is currently pressed.
     *
     * @return true if the key is pressed, false otherwise.
     */
    public boolean isDownPressed() {
        return downPressed;
    }
    /**
     * Checks if the A ("left") key is currently pressed.
     *
     * @return true if the key is pressed, false otherwise.
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }
    /**
     * Checks if the D ("right") key is currently pressed.
     *
     * @return true if the key is pressed, false otherwise.
     */
    public boolean isRightPressed() {
        return rightPressed;
    }
}
