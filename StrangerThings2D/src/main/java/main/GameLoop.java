package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;

/**
 * The GameLoop class represents the main game loop that continuously updates and renders the game.
 * It extends the AnimationTimer class from JavaFX.
 */
public class GameLoop extends AnimationTimer {
    private final GamePanel gamePanel;
    private final GraphicsContext gc;

    /**
     * Constructs a new GameLoop object.
     *
     * @param gamePanel the GamePanel object associated with the game loop.
     */
    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gc = gamePanel.getGraphicsContext2D();

    }

    /**
     * This method is called on each frame update of the game loop.
     * It handles the game logic and rendering.
     *
     * @param currentTime the current time in nanoseconds.
     */
    @Override
    public void handle(long currentTime) {
        try {
            drawEverything();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Check collisions
        gamePanel.getItemManager().checkCollisionWithPlayer();
        gamePanel.getEnemyManager().checkIfPlayerIsInActiveZone();
        gamePanel.getGameSaveManager().saveGameData();
    }

    /**
     * Draws all game elements on the canvas.
     *
     * @throws IOException if an I/O error occurs while drawing.
     */
    public void drawEverything() throws IOException {
        clearEverything(gc);
        //Tiles drawing
        gamePanel.getTileManager().drawTiles(gc);

        //Items drawing
        gamePanel.getItemManager().drawItems(gc);

        //Creatures drawing
        gamePanel.getEnemyManager().draw(gc);
        gamePanel.getPlayer().draw(gc);

        //Inventory drawing
        gamePanel.getInventoryManager().draw(gc);
    }
    /**
     * Clears the entire canvas.
     *
     * @param gc the GraphicsContext used for drawing.
     */
    public void clearEverything(GraphicsContext gc){
        gc.clearRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
    }

    /**
     * Returns the GraphicsContext associated with this GameLoop.
     *
     * @return the GraphicsContext object.
     */
    public GraphicsContext getGC() {
        return gc;
    }
}

