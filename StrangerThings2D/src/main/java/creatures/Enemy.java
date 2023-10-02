package creatures;

import javafx.geometry.Rectangle2D;
import main.GamePanel;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Represents an enemy creature in the game.
 * Extends the Creature class.
 */
public class Enemy extends Creature {
    private GamePanel gamePanel;
    private int actionLock;
    private int damage;

    private String name;


    //Logging
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    /**
     * Constructs an Enemy object with the specified game panel, column, and row.
     *
     * @param gamePanel the game panel in which the enemy exists
     * @param col       the column of the enemy on the game panel
     * @param row       the row of the enemy on the game panel
     * @throws IOException if an I/O error occurs while loading resources
     */
    public Enemy(GamePanel gamePanel, int col, int row) throws IOException {
        this.gamePanel = gamePanel;
        setCol(col);
        setRow(row);
        setMapX(gamePanel.getTileSize() * col);
        setMapY(gamePanel.getTileSize() * row);
        setHitBox(new Rectangle2D(getMapX()+8, getMapY(), 32, 48));
        setDirection("down");
        setHp(100);
        setSpeed(1);
        setDamage(10);
        setActionZoneWidth(gamePanel.getTileSize()*5);
        setActionZoneHeight(gamePanel.getTileSize()*5);
        setActionZone(new Rectangle2D((getMapX()+24)-(getActionZoneWidth()/2), (getMapY()+24)-(getActionZoneHeight()/2), getActionZoneWidth(), getActionZoneHeight()));
        loadImage();
        FileHandler fh = new FileHandler("src/main/resources/logging/logs.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    /**
     * Constructs an Enemy object with default values.
     * Used for tests
     */
    public Enemy() {
        setHitBox(new Rectangle2D(getMapX()+8, getMapY(), 32, 48));
        setHp(100);
    }

    /**
     * Performs an attack action.
     * Increases the hit counter and damages the player if a collision occurs.
     */
    public void attack(){
        increaseHitCounter(1);
        if (gamePanel.getCollisionManager().collides(getHitBox(), gamePanel.getPlayer().getHitBox())){
            if (getHitCounter()==50) {
                gamePanel.getPlayer().decreaseHP(damage);
                setHitCounter(0);
            }
        } else {
            setHitCounter(0);
        }
    }

    /**
     * Loads the enemy sprites from resource files.
     *
     * @throws IOException if an I/O error occurs while loading the images
     */
    public void loadImage(){
        setUp1(new Image(new File("src/main/resources/enemySprites/enemy3.png").toURI().toString()));
        setUp2(new Image(new File("src/main/resources/enemySprites/enemy4.png").toURI().toString()));
        setDown1(new Image(new File("src/main/resources/enemySprites/enemy1.png").toURI().toString()));
        setDown2(new Image(new File("src/main/resources/enemySprites/enemy2.png").toURI().toString()));
        setLeft1(new Image(new File("src/main/resources/enemySprites/enemy1.png").toURI().toString()));
        setLeft2(new Image(new File("src/main/resources/enemySprites/enemy2.png").toURI().toString()));
        setRight1(new Image(new File("src/main/resources/enemySprites/enemy1.png").toURI().toString()));
        setRight2(new Image(new File("src/main/resources/enemySprites/enemy2.png").toURI().toString()));
    }

    /**
     * Updates the enemy's position and performs collision checks.
     * Determines the movement direction and updates the hitBox and action zone.
     */
    public void update(){
        setCollision(false);
        gamePanel.getCollisionManager().checkCollisionWithCreatures(this, gamePanel.getPlayer());
        gamePanel.getCollisionManager().checkCollisionWithTiles(this);
        int dx = getMapX();
        int dy = getMapY();
        if (!isAttackIsActive()) {
            changeDirection();
            if (!isCollision()) {
                switch (getDirection()) {
                    case "up":
                        dy -= getSpeed();
                        break;
                    case "down":
                        dy += getSpeed();
                        break;
                    case "left":
                        dx -= getSpeed();
                        break;
                    case "right":
                        dx += getSpeed();
                        break;
                }
            }
        } else {
            if (!isCollision()) {
                int playerX = gamePanel.getPlayer().getMapX();
                int playerY = gamePanel.getPlayer().getMapY();

                if (getMapX()==playerX && getMapY()>playerY) {
                    dy-=getSpeed();
                }
                else if (getMapX()==playerX && getMapY() < playerY) {
                    dy+=getSpeed();
                }
                else if (getMapX()> playerX && getMapY()==playerY) {
                    dx-=getSpeed();
                }
                else if (getMapX()< playerX && getMapY()==playerY) {
                    dx+=getSpeed();
                }
                else if (getMapX() < playerX) {
                    dx += getSpeed();
                }
                else if (getMapX() > playerX) {
                    dx -= getSpeed();
                }
                else if (getMapY() < playerY) {
                    dy += getSpeed();
                }
                else if (getMapY() > playerY) {
                    dy -= getSpeed();
                }

            }
        }
        updateHitBoxAndActionZone(dx,dy);
        increaseMoveCounter(1);
        if (getMoveCounter() > 6) {
            if (getMoveNum() == 1) {
                setMoveNum(2);
            } else if (getMoveNum() == 2) {
                setMoveNum(1);
            }
            setMoveCounter(0);
        }
        checkIfEnemyIsDead();
    }

    /**
     * Checks if the enemy is dead and performs necessary actions.
     * If the enemy is a boss, logs the boss kill and alerts the game is passed.
     * Removes the enemy from the enemy manager and increases the player's kill counter.
     */
    public void checkIfEnemyIsDead(){
        if (getHp()<=0) {
            if (name.equals("boss")){
                LOGGER.info("Boss was killed");
                LOGGER.info("Game is passed");
                gamePanel.getEnemyManager().getEnemies().remove(this);
                gamePanel.alertGameIsPassedMessage();
            }
            gamePanel.getEnemyManager().getEnemies().remove(this);
            gamePanel.getPlayer().increaseKillsCounter();
            LOGGER.info("Enemy was killed");
        }
    }

    /**
     * Updates the hitbox and action zone of the enemy with the new coordinates.
     *
     * @param newX the new x-coordinate of the enemy
     * @param newY the new y-coordinate of the enemy
     */
    public void updateHitBoxAndActionZone(int newX, int newY){
        setMapX(newX);
        setMapY(newY);
        if (name.equals("enemy")) {
            setHitBox(new Rectangle2D(newX+8, newY, 32, 48));
        } else if (name.equals("boss")) {
            setHitBox(new Rectangle2D(getMapX()+16, getMapY(), 64, 96));
        }
        setActionZone(new Rectangle2D((getMapX()+24)-(getActionZoneWidth()/2), (getMapY()+24)-(getActionZoneHeight()/2), getActionZoneWidth(), getActionZoneHeight()));
    }

    /**
     * Changes the enemy's direction randomly after a certain action lock.
     * The action lock determines the frequency of direction changes.
     */
    public void changeDirection(){
        actionLock++;
        if (actionLock == 50) {
            Random random = new Random();
            int randomNum = random.nextInt(101);
            if (randomNum<=25) {
                setDirection("up");
            }
            if (randomNum>25 && randomNum <=50) {
                setDirection("down");
            }
            if (randomNum>50 && randomNum <=75) {
                setDirection("left");
            }
            if (randomNum>75) {
                setDirection("right");
            }
            actionLock = 0;
        }
    }

    /**
     * Sets the damage of the enemy.
     *
     * @param damage the amount of damage the enemy can inflict
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Sets the name of the enemy.
     *
     * @param name the name of the enemy
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the enemy.
     *
     * @return the name of the enemy
     */
    public String getName() {
        return name;
    }
}
