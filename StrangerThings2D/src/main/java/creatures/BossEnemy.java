package creatures;

import javafx.geometry.Rectangle2D;
import main.GamePanel;
import java.io.IOException;

/**
 * The BossEnemy class represents a boss enemy in the game.
 * It extends the Enemy class and provides additional functionality specific to boss enemies.
 */
public class BossEnemy extends Enemy {


    /**
     * Constructs a BossEnemy object with the specified parameters.
     *
     * @param gamePanel The GamePanel instance associated with the boss enemy.
     * @param col       The column index of the boss enemy's initial position in the map.
     * @param row       The row index of the boss enemy's initial position in the map.
     * @throws IOException if there is an error while loading resources.
     */
    public BossEnemy(GamePanel gamePanel, int col, int row) throws IOException {
        super(gamePanel, col, row);
        setHitBox(new Rectangle2D(getMapX()+16, getMapY(), 64, 96));
        setHp(150);
        setSpeed(1);
        setDamage(15);
        setActionZoneWidth(gamePanel.getTileSize()*5);
        setActionZoneHeight(gamePanel.getTileSize()*5);
        setActionZone(new Rectangle2D((getMapX()+24)-(getActionZoneWidth()/2), (getMapY()+24)-(getActionZoneHeight()/2), getActionZoneWidth(), getActionZoneHeight()));
        loadImage();
    }

    /**
     * Default constructor for the BossEnemy class.
     * Creates an empty BossEnemy object.
     */
    public BossEnemy(){}
}
