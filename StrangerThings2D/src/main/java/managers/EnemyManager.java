package managers;

import creatures.BossEnemy;
import creatures.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.GamePanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The EnemyManager class manages the enemies in the game.
 * It handles loading enemies onto the map, checking for player proximity to activate enemy attacks,
 * drawing enemies on the game panel, and providing access to the list of enemies.
 */
public class EnemyManager {
    private GamePanel gamePanel;
    private List<Enemy> enemies;
    private String ENEMIES_ON_MAP_IN;
    //Logging
    private static final Logger LOGGER = Logger.getLogger(EnemyManager.class.getName());

    /**
     * Constructs an EnemyManager object with the specified GamePanel.
     *
     * @param gamePanel the GamePanel instance
     * @throws IOException if an error occurs while loading enemies
     */
    public EnemyManager(GamePanel gamePanel) throws IOException {
        this.gamePanel = gamePanel;
        enemies = new ArrayList<>();
        ENEMIES_ON_MAP_IN = "src/main/resources/serialization/enemiesOnMap_in.txt";
        loadEnemiesOnMap();
        FileHandler fh = new FileHandler("src/main/resources/logging/logsEnemyManager.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    /**
     * Constructs an EnemyManager object without any initial enemies.
     * Used for tests
     */
    public EnemyManager() {
        enemies = new ArrayList<>();
    }


    /**
     * Removes all enemies from the map.
     */
    public void removeAllEnemiesFromMap(){
        enemies.clear();
    }

    /**
     * Loads the enemies onto the map from the designated file.
     *
     * @throws IOException if an error occurs while reading the file
     */
    public void loadEnemiesOnMap() throws IOException {
        List<String> names = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        List<Integer> rows = new ArrayList<>();

        try{
            FileReader fr = new FileReader(ENEMIES_ON_MAP_IN);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length>=3) {
                    String name = parts[0];
                    int col = Integer.parseInt(parts[1]);
                    int row = Integer.parseInt(parts[2]);
                    names.add(name);
                    cols.add(col);
                    rows.add(row);
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        for (int i = 0; i < names.size(); i++) {
            int col = cols.get(i);
            int row = rows.get(i);
            String name = names.get(i);
            if (name.equals("enemy")) {
                Enemy newEnemy = new Enemy(gamePanel,col,row);
                newEnemy.setName(name);
                enemies.add(newEnemy);
            } else if (name.equals("boss")) {
                BossEnemy newEnemy = new BossEnemy(gamePanel,col,row);
                newEnemy.setName(name);
                enemies.add(newEnemy);
            }

        }

    }

    /**
     * Checks if the player is in the active zone of any enemy and triggers their attacks accordingly.
     */
    public void checkIfPlayerIsInActiveZone(){
        for (Enemy enemy : enemies) {
            enemy.setSpeed(1);
            enemy.setAttackIsActive(gamePanel.getCollisionManager().collides(gamePanel.getPlayer().getHitBox(), enemy.getActionZone()));
            if (enemy.isAttackIsActive()) {
                enemy.setSpeed(2);
                enemy.attack();
            }
        }
    }

    /**
     * Draws the enemies on the specified GraphicsContext.
     *
     * @param gc the GraphicsContext to draw on
     */
    public void draw(GraphicsContext gc) {
        if (enemies.isEmpty()) {
            return;
        }
        List<Enemy> enemiesCopy = new ArrayList<>(enemies);
        for (Enemy enemy : enemiesCopy) {
            if (enemy != null) {
                enemy.update();
                Image image = null;
                switch (enemy.getDirection()) {
                    case "up":
                        image = (enemy.getMoveNum() == 1) ? enemy.getUp1() : (enemy.getMoveNum()== 2) ? enemy.getUp2() : null;
                        break;
                    case "down":
                        image = (enemy.getMoveNum() == 1) ? enemy.getDown1() : (enemy.getMoveNum() == 2) ? enemy.getDown2() : null;
                        break;
                    case "left":
                        image = (enemy.getMoveNum() == 1) ? enemy.getLeft1() : (enemy.getMoveNum() == 2) ? enemy.getLeft2() : null;
                        break;
                    case "right":
                        image = (enemy.getMoveNum() == 1) ? enemy.getRight1() : (enemy.getMoveNum() == 2) ? enemy.getRight2() : null;
                        break;
                }

                enemy.setWindowX(enemy.getMapX() - gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getWindowX());
                enemy.setWindowY(enemy.getMapY() - gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getWindowY());
                if (Math.abs(enemy.getMapX() - gamePanel.getPlayer().getMapX()) < gamePanel.getTileSize() + gamePanel.getPlayer().getWindowX() &&
                    Math.abs(enemy.getMapY() - gamePanel.getPlayer().getMapY()) < gamePanel.getTileSize() + gamePanel.getPlayer().getWindowY()) {
                    gc.setFill(Color.WHITE);
                    gc.setFont(gamePanel.getEnemyFont());
                    String textHP = "HP: " + enemy.getHp();
                    gc.fillText(textHP, enemy.getWindowX() + 4, enemy.getWindowY() - 5);
                    if (enemy.getName().equals("enemy")){
                        gc.fillText(textHP, enemy.getWindowX() + 4, enemy.getWindowY() - 5);
                        gc.drawImage(image, enemy.getWindowX(), enemy.getWindowY(), gamePanel.getTileSize(), gamePanel.getTileSize());
                    } else if (enemy.getName().equals("boss")){
                        gc.fillText(textHP, enemy.getWindowX() + 4, enemy.getWindowY() - 5);
                        gc.drawImage(image, enemy.getWindowX(), enemy.getWindowY(), gamePanel.getTileSize()*2, gamePanel.getTileSize()*2);
                    }
                }
            }
        }
    }

    /**
     * Returns the list of enemies.
     *
     * @return the list of enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Sets the list of enemies.
     *
     * @param enemies the list of enemies to set
     */
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
}
