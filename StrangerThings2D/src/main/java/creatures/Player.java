package creatures;

import inventoryAndItems.HealingItem;
import inventoryAndItems.Item;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GamePanel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Player class represents the player character in the game.
 * It extends the Creature class and contains methods and attributes specific to the player.
 */
public class Player extends Creature {
    private GamePanel gamePanel;

    private List<Item> inventory;
    private Rectangle2D damageArea;
    private int killsCounter = 0;

    //Logging
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    /**
     * Constructs a new Player object with a reference to the GamePanel.
     *
     * @param gamePanel the GamePanel object
     * @throws IOException if there is an error loading the player images
     */
    public Player(GamePanel gamePanel) throws IOException {
        this.gamePanel = gamePanel;
        setDefaultValues();
        loadImages();
        FileHandler fh = new FileHandler("src/main/resources/logging/logs.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    /**
     * Constructs a new Player object with default values.
     * Used for test.
     */
    public Player() {
        setDefaultValues();
    }

    /**
     * Sets the default values for the player.
     */
    public void setDefaultValues(){
        inventory = new ArrayList<>();
        setMapX(48 * 24);
        setMapY(1152);
        setSpeed(5);
        setHp(100);
        killsCounter=0;
        setDirection("right");
        setWindowX(360);
        setWindowY(264);
        setHitBox(new Rectangle2D(getMapX()+8,getMapY()+16,32,32));
        damageArea = new Rectangle2D(getMapX()+48, getMapY(),48, 48);
    }

    /**
     * Restores the player's health to full by consuming a healing item from the inventory.
     * If a healing item is used, it is removed from the inventory.
     */
    public void heal(){
        for (Item item : inventory) {
            if (item instanceof HealingItem) {
                setHp(100);
                inventory.remove(item);
                LOGGER.info("Item: "+item.getName()+" was used by player");
                break;
            }
        }
    }

    /**
     * Checks if the player's health is zero or below, indicating that the player has died.
     * If the player died, a log message is recorded and the game is restarted.
     *
     * @throws IOException if there is an error restarting the game
     */
    public void checkIfPlayerDied() throws IOException {
        if (getHp()<=0) {
            LOGGER.info("Player died");
            gamePanel.restartGame();
        }
    }

    /**
     * Initiates an attack action by the player.
     * The attack consists of a sequence of movements and damaging enemy entities.
     * The player's move counter and hit counter are incremented during the attack.
     */
    public void attack(){
        increaseMoveCounter(1);
        if (getMoveCounter()<=5) {
            setMoveNum(1);
        }
        if (getMoveCounter()>5 && getMoveCounter() <=25) {
            setMoveNum(2);
        }
        if (getMoveCounter()>25) {
            setMoveNum(1);
            setMoveCounter(0);
            setAttackIsActive(false);
        }
        increaseHitCounter(1);
        damageEnemy();
    }

    /**
     * Damages enemy entities within the player's damage area.
     * If the player's hit counter reaches a certain threshold, the enemy's HP is reduced by 20.
     */
    public void damageEnemy(){
        for (int i = 0; i<gamePanel.getEnemyManager().getEnemies().size(); i++) {
            if (gamePanel.getCollisionManager().collides(damageArea, gamePanel.getEnemyManager().getEnemies().get(i).getHitBox())) {
                if (getHitCounter() >= 40) {
                    gamePanel.getEnemyManager().getEnemies().get(i).decreaseHP(20);
                    setHitCounter(0);
                }
            }
        }
    }

    /**
     * Updates the player's state and performs necessary actions.
     * This includes checking if the player has died, initiating an attack, or handling movement.
     *
     * @throws IOException if there is an error restarting the game
     */
    public void update() throws IOException {
        checkIfPlayerDied();
        if (isAttackIsActive()) {
            attack();
        }
        else if (gamePanel.getKeyManager().isUpPressed() || gamePanel.getKeyManager().isDownPressed() || gamePanel.getKeyManager().isLeftPressed() || gamePanel.getKeyManager().isRightPressed()) {
            if (gamePanel.getKeyManager().isUpPressed()) {
                setDirection("up");
            } if (gamePanel.getKeyManager().isDownPressed()) {
                setDirection("down");
            } if (gamePanel.getKeyManager().isLeftPressed()) {
                setDirection("left");
            } if (gamePanel.getKeyManager().isRightPressed()) {
                setDirection("right");
            }
            //Check collision
            setCollision(false);
            gamePanel.getCollisionManager().checkCollisionWithTiles(this);
            if (!isCollision()) {
                switch (getDirection()) {
                    case "up":
                        decreaseMapY(getSpeed());
                        break;
                    case "down":
                        increaseMapY(getSpeed());
                        break;
                    case "left":
                        decreaseMapX(getSpeed());
                        break;
                    case "right":
                        increaseMapX(getSpeed());
                        break;
                }
                updateHitBoxAndDamageArea(getMapX(),getMapY());
            }
            increaseMoveCounter(1);
            if (getMoveCounter() > 6) {
                if (getMoveNum() == 1) {
                    setMoveNum(2);
                } else if (getMoveNum() == 2) {
                    setMoveNum(1);
                }
                setMoveCounter(0);
            }
        }
    }


    /**
     * Updates the player's hit box and damage area based on the new coordinates.
     *
     * @param newX the new x-coordinate of the player
     * @param newY the new y-coordinate of the player
     */
    public void updateHitBoxAndDamageArea(int newX, int newY){
        setHitBox(new Rectangle2D(newX+8,newY+16,32,32));
        switch (getDirection()) {
            case "up":
                damageArea = new Rectangle2D(newX,newY-gamePanel.getTileSize(),gamePanel.getTileSize(), gamePanel.getTileSize());
                break;
            case "down":
                damageArea = new Rectangle2D(newX,newY+gamePanel.getTileSize(),gamePanel.getTileSize(), gamePanel.getTileSize());
                break;
            case "left":
                damageArea = new Rectangle2D(newX-gamePanel.getTileSize(),newY, gamePanel.getTileSize(), gamePanel.getTileSize());
                break;
            case "right":
                damageArea = new Rectangle2D(newX+ gamePanel.getTileSize(),newY, gamePanel.getTileSize(), gamePanel.getTileSize());
                break;
        }
    }

/**
 * Draws the player on the screen.
 * The player's image is selected based on the direction and attack status.
 *
 * @param gc the GraphicsContext object used for drawing
 * @throws IOException if there is an error reading the images or updating the game.
 *
 * */

    public void draw(GraphicsContext gc) throws IOException {
        update();
        Image image = null;

        int tmpWindowX = getWindowX();
        int tmpWindowY = getWindowY();

        switch (getDirection()) {
            case "up":
                if (!isAttackIsActive()) {
                    image = (getMoveNum() == 1) ? getUp1() : (getMoveNum() == 2) ? getUp2() : null;
                } else {
                    tmpWindowY = getWindowY() - 32;
                    image = (getMoveNum() == 1) ? getUpAttack1() : (getMoveNum() == 2) ? getUpAttack2() : null;
                }
                break;
            case "down":
                if (!isAttackIsActive()){
                    image = (getMoveNum() == 1) ? getDown1() : (getMoveNum() == 2) ? getDown2() : null;
                } else {
                    image = (getMoveNum() == 1) ? getDownAttack1() : (getMoveNum() == 2) ? getDownAttack2() : null;
                }
                break;
            case "left":
                if (!isAttackIsActive()){
                    image = (getMoveNum() == 1) ? getLeft1() : (getMoveNum() == 2) ? getLeft2() : null;
                } else {
                    tmpWindowX = getWindowX() - 30;
                    image = (getMoveNum() == 1) ? getLeftAttack1() : (getMoveNum() == 2) ? getLeftAttack2() : null;
                }
                break;
            case "right":
                if (!isAttackIsActive()){
                    image = (getMoveNum() == 1) ? getRight1() : (getMoveNum() == 2) ? getRight2() : null;
                } else {
                    image = (getMoveNum() == 1) ? getRightAttack1() : (getMoveNum() == 2) ? getRightAttack2() : null;
                }
                break;
        }

        if (!isAttackIsActive()) {
            gc.drawImage(image, getWindowX(), getWindowY(), 48, 48);
        } else {
            if (getDirection().equals("right")||getDirection().equals("left")) {
                gc.drawImage(image, tmpWindowX, tmpWindowY, 80, 48);
            }
            else if (getDirection().equals("up")||getDirection().equals("down")) {
                gc.drawImage(image, tmpWindowX, tmpWindowY, 48, 80);
            }

        }
    }

    /**
     * Loads the player's images from file paths.
     * The images are stored in instance variables for later use.
     */
    public void loadImages() {
        //Movement sprites
        setUp1(new Image(new File("src/main/resources/playerSprites/el_up_1.png").toURI().toString()));
        setUp2(new Image(new File("src/main/resources/playerSprites/el_up_2.png").toURI().toString()));
        setDown1(new Image(new File("src/main/resources/playerSprites/el_down_1.png").toURI().toString()));
        setDown2(new Image(new File("src/main/resources/playerSprites/el_down_2.png").toURI().toString()));
        setLeft1(new Image(new File("src/main/resources/playerSprites/el_left_1.png").toURI().toString()));
        setLeft2(new Image(new File("src/main/resources/playerSprites/el_left_2.png").toURI().toString()));
        setRight1(new Image(new File("src/main/resources/playerSprites/el_right_1.png").toURI().toString()));
        setRight2(new Image(new File("src/main/resources/playerSprites/el_right_2.png").toURI().toString()));
        //Attack sprites
        setUpAttack1(new Image(new File("src/main/resources/playerSprites/upAttack1.png").toURI().toString()));
        setUpAttack2(new Image(new File("src/main/resources/playerSprites/upAttack2.png").toURI().toString()));
        setDownAttack1(new Image(new File("src/main/resources/playerSprites/downAttack1.png").toURI().toString()));
        setDownAttack2(new Image(new File("src/main/resources/playerSprites/downAttack2.png").toURI().toString()));
        setLeftAttack1(new Image(new File("src/main/resources/playerSprites/leftAttack1.png").toURI().toString()));
        setLeftAttack2(new Image(new File("src/main/resources/playerSprites/leftAttack2.png").toURI().toString()));
        setRightAttack1(new Image(new File("src/main/resources/playerSprites/rightAttack1.png").toURI().toString()));
        setRightAttack2(new Image(new File("src/main/resources/playerSprites/rightAttack2.png").toURI().toString()));
    }

    /**
     * Retrieves the player's inventory.
     *
     * @return The list of items in the player's inventory.
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * Sets the player's inventory to the specified list of items.
     *
     * @param inventory The list of items to set as the player's inventory.
     */
    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Adds an item to the player's inventory.
     *
     * @param item The item to add to the player's inventory.
     */
    public void addItemToInventory(Item item){
        inventory.add(item);
    }

    /**
     * Retrieves the player's kills counter.
     *
     * @return The number of kills the player has achieved.
     */
    public int getKillsCounter() {
        return killsCounter;
    }

    /**
     * Sets the player's kills counter to the specified value.
     *
     * @param killsCounter The number of kills to set for the player.
     */
    public void setKillsCounter(int killsCounter) {
        this.killsCounter = killsCounter;
    }

    /**
     * Increases the player's kills counter by 1.
     */
    public void increaseKillsCounter() {
        killsCounter++;
    }

    /**
     * Retrieves the damage area of the player's attack.
     *
     * @return The rectangular area representing the player's attack damage.
     */
    public Rectangle2D getDamageArea() {
        return damageArea;
    }
}
















