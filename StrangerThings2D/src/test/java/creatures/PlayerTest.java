package creatures;

import inventoryAndItems.HealingItem;
import inventoryAndItems.Item;
import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains test cases for the Player class.
 */
public class PlayerTest {
    Player testPlayer;

    /**
     * Sets up the test fixture by creating a new Player instance.
     *
     * @throws Exception if an exception occurs during setup
     */
    @BeforeEach
    public void setUp() throws Exception {
        testPlayer = new Player();
    }

    /**
     * Tests the setDefaultValues() method of the Player class.
     * It verifies that the player's default values are set correctly.
     */
    @Test
    @Order(1)
    public void setDefaultValuesTest(){
        testPlayer.setDefaultValues();
        //Expected results
        List<Item> expInventory = new ArrayList<>();
        int expMapX = 48 * 24;
        int expMapY = 48 * 24;
        int expSpeed = 5;
        int expHP = 100;
        int expKillsCounter = 0;
        String expDirection = "right";
        int expWindowX = 360;
        int expWindowY = 264;
        Rectangle2D expHitBox = new Rectangle2D(testPlayer.getMapX() + 8, testPlayer.getMapY() + 16, 32, 32);
        Rectangle2D expDamageArea = new Rectangle2D(testPlayer.getMapX() + 48, testPlayer.getMapY(), 48, 48);

        //Results
        List<Item> resInventory = testPlayer.getInventory();
        int resMapX = testPlayer.getMapX();
        int resMapY = testPlayer.getMapY();
        int resSpeed = testPlayer.getSpeed();
        int resHP = testPlayer.getHp();
        int resKillsCounter = testPlayer.getKillsCounter();
        String resDirection = testPlayer.getDirection();
        int resWindowX = testPlayer.getWindowX();
        int resWindowY = testPlayer.getWindowY();
        Rectangle2D resHitBox = testPlayer.getHitBox();
        Rectangle2D resDamageArea = testPlayer.getDamageArea();

        //Assertions
        Assertions.assertEquals(expInventory, resInventory);
        Assertions.assertEquals(expMapX, resMapX);
        Assertions.assertEquals(expMapY, resMapY);
        Assertions.assertEquals(expSpeed, resSpeed);
        Assertions.assertEquals(expHP, resHP);
        Assertions.assertEquals(expKillsCounter, resKillsCounter);
        Assertions.assertEquals(expDirection, resDirection);
        Assertions.assertEquals(expWindowX, resWindowX);
        Assertions.assertEquals(expWindowY, resWindowY);
        Assertions.assertEquals(expHitBox, resHitBox);
        Assertions.assertEquals(expDamageArea, resDamageArea);
    }

    /**
     * Tests the heal() method of the Player class.
     * It verifies that the player's HP is not increased and the healing item is removed from the inventory.
     */
    @Test
    @Order(2)
    public void healTest(){
        HealingItem healingItem = new HealingItem();
        List<Item> inventory = new ArrayList<>();
        inventory.add(healingItem);
        testPlayer.setInventory(inventory);
        testPlayer.heal();

        //Expected results
        int expHP = 100;
        boolean expInventoryContainsHealingItem = false;

        //Results
        int resHP = testPlayer.getHp();
        boolean resInventoryContainsHealingItem = testPlayer.getInventory().contains(healingItem);

        //Assertions
        Assertions.assertEquals(expHP, resHP);
        Assertions.assertEquals(expInventoryContainsHealingItem, resInventoryContainsHealingItem);
    }
}
