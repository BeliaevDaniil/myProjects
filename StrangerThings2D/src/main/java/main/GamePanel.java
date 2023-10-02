package main;
import creatures.Player;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import managers.InventoryManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Font;
import managers.*;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The game panel class, extending the Canvas class.
 * This class connects all other classes together.
 * Each class uses the attributes of this class to access other classes
 */
public class GamePanel extends Canvas {
    private Stage stage;
    //Game settings
    private final int tileSize = 48;
    private final Font inventoryFont = Font.loadFont(getClass().getResourceAsStream("/fonts/EightBits.ttf"), 25);
    private final Font enemyFont = Font.loadFont(getClass().getResourceAsStream("/fonts/EightBits.ttf"), 20);
    //World parameters
    private final int mapCol = 50;
    private final int mapRow = 50;
    //Creatures
    private final Player player = new Player(this);
    //Managers
    private final GameSaveManager gameSaveManager = new GameSaveManager(this);
    private final KeyManager keyManager = new KeyManager(this);
    private final GameLoop gameLoop = new GameLoop(this);
    private final MapTileManager tileManager = new MapTileManager(this);
    private final CollisionManager collisionManager = new CollisionManager(this);
    private final ItemManager itemManager = new ItemManager(this);
    private final EnemyManager enemyManager = new EnemyManager(this);
    private final InventoryManager inventoryManager = new InventoryManager(this);

    //Logging
    private static final Logger LOGGER = Logger.getLogger(GamePanel.class.getName());

    /**
     * Creates an instance of GamePanel.
     * @param stage the Stage object on which the game will be displayed.
     * @throws Exception if there is an error creating the GamePanel.
     */
    public GamePanel(Stage stage) throws Exception {
        this.stage = stage;
        int windowCol = 16;
        int windowWidth = tileSize * windowCol;
        int windowRol = 12;
        int windowHeight = tileSize * windowRol;
        setWidth(windowWidth);
        setHeight(windowHeight);
        FileHandler fh = new FileHandler("src/main/resources/logging/logsGamePanel.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    /**
     * Starts the game by starting GameLoop
     * and clearing game data storage
     */
    public void startGame() {
        gameLoop.start();
        gameSaveManager.applySavedGameData();
        gameSaveManager.clearDataStorage();
    }

    /**
     * Restarts the game by setting default values, removing items and enemies,
     * then uploading items and enemies again.
     * @throws IOException if there is an error during game restart.
     */
    public void restartGame() throws IOException {
        player.setDefaultValues();
        itemManager.removeAllItemsFromMap();
        enemyManager.removeAllEnemiesFromMap();
        itemManager.loadItemsOnMap();
        enemyManager.loadEnemiesOnMap();
        try {
            itemManager.getItemsFromFile();
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays an alert message when the game is passed.
     */
    public void alertGameIsPassedMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Congrats!");
            alert.setHeaderText("Game is passed\n" + "Pane Serédi, udělejte mi prosím započet :)" );


            ButtonType closeGameButton = new ButtonType("Close Game", ButtonBar.ButtonData.OK_DONE);
            ButtonType restartGameButton = new ButtonType("Restart Game", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(closeGameButton, restartGameButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == closeGameButton) {
                    stage.close();
                } else if (result.get() == restartGameButton) {
                    try {
                        restartGame();
                    } catch (IOException e) {
                        LOGGER.severe("Error: " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    /**
     * Displays a pop-up warning when the window is closed.
     *
     * @param stage the Stage object associated with the window.
     */
    public void closeWindowWarning(Stage stage){
        stage.setOnCloseRequest(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to close the game?");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                gameSaveManager.saveGameData();
                stage.close();
            }
        });
    }

    /**
     * Returns the tile size.
     *
     * @return the size of the tiles in pixels.
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Returns the font used for the inventory.
     *
     * @return the inventory font.
     */
    public Font getInventoryFont() {
        return inventoryFont;
    }

    /**
     * Returns the font used for enemies.
     *
     * @return the enemy font.
     */
    public Font getEnemyFont() {
        return enemyFont;
    }

    /**
     * Returns the number of columns in the game map.
     *
     * @return the number of map columns.
     */
    public int getMapCol() {
        return mapCol;
    }

    /**
     * Returns the number of rows in the game map.
     *
     * @return the number of map rows.
     */
    public int getMapRow() {
        return mapRow;
    }

    /**
     * Returns the player object.
     *
     * @return the player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the game save manager.
     *
     * @return the game save manager object.
     */
    public GameSaveManager getGameSaveManager() {
        return gameSaveManager;
    }

    /**
     * Returns the key manager.
     *
     * @return the key manager object.
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * Returns the game loop.
     *
     * @return the game loop object.
     */
    public GameLoop getGameLoop() {
        return gameLoop;
    }

    /**
     * Returns the map tile manager.
     *
     * @return the map tile manager object.
     */
    public MapTileManager getTileManager() {
        return tileManager;
    }

    /**
     * Returns the collision manager.
     *
     * @return the collision manager object.
     */
    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    /**
     * Returns the item manager.
     *
     * @return the item manager object.
     */
    public ItemManager getItemManager() {
        return itemManager;
    }

    /**
     * Returns the enemy manager.
     *
     * @return the enemy manager object.
     */
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    /**
     * Returns the inventory manager.
     *
     * @return the inventory manager object.
     */
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }




}


