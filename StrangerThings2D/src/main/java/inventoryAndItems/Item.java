package inventoryAndItems;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import main.GamePanel;

/**
 * Represents an item in the game.
 */
public class Item {
    private GamePanel gamePanel;
    private String name;
    private Image image;
    private Rectangle2D hitBox;
    private int mapX, mapY;
    private int col;
    private int row;

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Rectangle2D getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle2D hitBox) {
        this.hitBox = hitBox;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}
