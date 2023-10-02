package inventoryAndItems;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import main.GamePanel;
import managers.GameSaveManager;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Key extends Item {
    //Logging
    private static final Logger LOGGER = Logger.getLogger(Key.class.getName());
    public Key(GamePanel gamepanel, int col, int row) throws IOException {
        setGamePanel(gamepanel);
        setName("Key");
        setCol(col);
        setRow(row);
        setMapX(getCol()*gamepanel.getTileSize());
        setMapY(getRow()*gamepanel.getTileSize());
        setHitBox(new Rectangle2D(getMapX(), getMapY(),48,48));
        loadImage();
        FileHandler fh = new FileHandler("src/main/resources/logging/logs.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    public Key(GamePanel gamePanel) {
        setGamePanel(gamePanel);
        setName("Key");
        loadImage();
    }

    public void loadImage(){
        try {
            setImage(new Image(new File("src/main/resources/itemTiles/key.png").toURI().toString()));
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
