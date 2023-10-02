package inventoryAndItems;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import main.GamePanel;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an attack item in the game.
 * Extends the Item class.
 */
public class AttackItem extends Item {
    //Logging
    private static final Logger LOGGER = Logger.getLogger(AttackItem.class.getName());
    /**
     * Constructs an AttackItem with the specified game panel, column, and row.
     *
     * @param gamePanel the GamePanel object associated with the item
     * @param col the column index of the item's position
     * @param row the row index of the item's position
     * @throws IOException if an I/O error occurs while constructing the AttackItem
     */
    public AttackItem(GamePanel gamePanel, int col, int row) throws IOException {
        setGamePanel(gamePanel);
        setName("AttackItem");
        setCol(col);
        setRow(row);
        setMapX(getCol()*gamePanel.getTileSize());
        setMapY(getRow()*gamePanel.getTileSize());
        setHitBox(new Rectangle2D(getMapX(), getMapY(),48,48));
        loadImage();
        FileHandler fh = new FileHandler("src/main/resources/logging/logs.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    /**
     * Constructs an AttackItem with the specified game panel.
     *
     * @param gamePanel the GamePanel object associated with the item
     */
    public AttackItem(GamePanel gamePanel) {
        setGamePanel(gamePanel);
        setName("AttackItem");
        loadImage();
    }

    /**
     * Loads the image for the AttackItem from the specified file path.
     * Displays an error message if the image fails to load.
     */
    public void loadImage(){
        try {
            setImage(new Image(new File("src/main/resources/itemTiles/attackItem.png").toURI().toString()));
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
