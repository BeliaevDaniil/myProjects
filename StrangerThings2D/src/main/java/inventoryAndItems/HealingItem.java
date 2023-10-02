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
 * Represents a healing item in the game.
 * Extends the Item class.
 */
public class HealingItem extends Item {
    //Logging
    private static final Logger LOGGER = Logger.getLogger(HealingItem.class.getName());

    /**
     * Constructs a HealingItem with the specified game panel, column, and row.
     *
     * @param gamePanel the GamePanel object associated with the item
     * @param col the column index of the item's position
     * @param row the row index of the item's position
     * @throws IOException if an I/O error occurs while constructing the HealingItem
     */
    public HealingItem(GamePanel gamePanel, int col, int row) throws IOException {
        setGamePanel(gamePanel);
        setName("HealingItem");
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
     * Constructs a HealingItem object with the specified GamePanel.
     *
     * @param gamePanel The GamePanel associated with this HealingItem.
     */
    public HealingItem(GamePanel gamePanel) {
        setGamePanel(gamePanel);
        setName("HealingItem");
        loadImage();
    }

    /**
     * Constructs a HealingItem object with default settings.
     * The GamePanel must be set separately using the setGamePanel() method.
     */
    public HealingItem() {
        setName("HealingItem");
    }

    /**
     * Loads the image for the HealingItem from the specified file path.
     * Displays an error message if the image fails to load.
     */
    public void loadImage(){
        try {
            setImage(new Image(new File("src/main/resources/itemTiles/healingItem.png").toURI().toString()));
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
