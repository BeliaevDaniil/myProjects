package managers;

import creatures.Enemy;
import creatures.Player;
import javafx.geometry.Rectangle2D;
import managers.EnemyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the EnemyManager class.
 */
public class EnemyManagerTest {
    EnemyManager enemyManager;

    /**
     * Sets up the test fixture by creating a new EnemyManager instance.
     */
    @BeforeEach
    public void setUp(){
        enemyManager = new EnemyManager();
    }

    /**
     * Tests the checkIfPlayerIsInActiveZone() method of the EnemyManager class.
     * It checks if a player is within the active zone of an enemy.
     */
    @Test
    public void checkIfPlayerIsInActiveZoneTest(){
        Player player = new Player();
        player.setHitBox(new Rectangle2D(100,100,32,32));;
        Enemy enemy = new Enemy();
        enemy.setHitBox(new Rectangle2D(100,100,32,32));
        enemyManager.getEnemies().add(enemy);
    }
}
