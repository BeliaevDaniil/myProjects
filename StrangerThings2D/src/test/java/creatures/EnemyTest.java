package creatures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the Enemy class.
 */
public class EnemyTest {
    Enemy testEnemy;

    /**
     * Sets up the test fixture by creating a new Enemy instance.
     *
     * @throws Exception if an exception occurs during setup
     */
    @BeforeEach
    public void setUp() throws Exception {
        testEnemy = new Enemy();
    }

    /**
     * Tests the updateHitBoxAndActionZone() method of the Enemy class.
     * It verifies that the hit box and action zone of an enemy are updated correctly.
     */
    @Test
    public void updateHitBoxAndActionZoneTest() {
        Enemy enemy = new Enemy();
        enemy.setName("enemy");
        enemy.setMapX(0);
        enemy.setMapY(0);
        enemy.setActionZoneWidth(100);
        enemy.setActionZoneHeight(100);
        enemy.updateHitBoxAndActionZone(50, 50);
        BossEnemy bossEnemy = new BossEnemy();
        bossEnemy.setName("boss");
        bossEnemy.setMapX(0);
        bossEnemy.setMapY(0);
        bossEnemy.setActionZoneWidth(100);
        bossEnemy.setActionZoneHeight(100);
        bossEnemy.updateHitBoxAndActionZone(50, 50);

        //Expected results Enemy
        int expEnemyHitBoxX = 58;
        int expEnemyHitBoxY = 50;
        int expEnemyActionZoneX = 24;
        int expEnemyActionZoneY = 24;

        //Expected results BossEnemy
        int expBossEnemyHitBoxX = 66;
        int expBossEnemyHitBoxY = 50;
        int expBossEnemyActionZoneX = 24;
        int expBossEnemyActionZoneY = 24;

        //Results Enemy
        double resEnemyHitBoxX = enemy.getHitBox().getMinX();
        double resEnemyHitBoxY = enemy.getHitBox().getMinY();
        double resEnemyActionZoneX = enemy.getActionZone().getMinX();
        double resEnemyActionZoneY = enemy.getActionZone().getMinY();

        //Results BossEnemy
        double resBossEnemyHitBoxX = bossEnemy.getHitBox().getMinX();
        double resBossEnemyHitBoxY = bossEnemy.getHitBox().getMinY();
        double resBossEnemyActionZoneX = bossEnemy.getActionZone().getMinX();
        double resBossEnemyActionZoneY = bossEnemy.getActionZone().getMinY();

        //Assertions
        Assertions.assertEquals(expEnemyHitBoxX, resEnemyHitBoxX);
        Assertions.assertEquals(expEnemyHitBoxY, resEnemyHitBoxY);
        Assertions.assertEquals(expEnemyActionZoneX, resEnemyActionZoneX);
        Assertions.assertEquals(expEnemyActionZoneY, resEnemyActionZoneY);

        Assertions.assertEquals(expBossEnemyHitBoxX, resBossEnemyHitBoxX);
        Assertions.assertEquals(expBossEnemyHitBoxY, resBossEnemyHitBoxY);
        Assertions.assertEquals(expBossEnemyActionZoneX, resBossEnemyActionZoneX);
        Assertions.assertEquals(expBossEnemyActionZoneY, resBossEnemyActionZoneY);
    }
}


