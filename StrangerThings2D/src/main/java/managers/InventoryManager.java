package managers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.GamePanel;

/**
 * The InventoryManager class handles the management and display of the player's inventory.
 */
public class InventoryManager {
    private final GamePanel gamePanel;
    private final int windowCol;
    private final int windowRow;
    private final double windowX;
    private final double windowY;
    private final double width;
    private final double height;
    private boolean isOpened = false;

    /**
     * Constructs a new InventoryManager object with the specified GamePanel.
     *
     * @param gamePanel The GamePanel object to associate with the inventory manager.
     */
    public InventoryManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        //Inventory window settings
        windowCol = 5;
        windowRow = 3;
        windowX = (double)gamePanel.getTileSize()/2;
        windowY = (double)gamePanel.getTileSize()/2;
        width = windowCol*gamePanel.getTileSize();
        height = windowRow* gamePanel.getTileSize();
    }

    /**
     * Displays an inventory full message on the specified GraphicsContext.
     *
     * @param gc The GraphicsContext to draw the message on.
     */
    public void alertInventoryMassage(GraphicsContext gc){
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRoundRect((double)gamePanel.getTileSize()/2, 185, 130, 25, 10, 10);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect((double)gamePanel.getTileSize()/2, 185, 130, 25, 10, 10);

        gc.setFill(Color.RED);
        gc.setFont(gamePanel.getInventoryFont());
        gc.fillText("Inventory is full", (double)gamePanel.getTileSize()/2+5, 203);
    }

    /**
     * Draws the inventory window and its contents on the specified GraphicsContext.
     *
     * @param gc The GraphicsContext to draw the inventory on.
     */
    public void draw(GraphicsContext gc) {
        if (!isOpened) {
            return;
        }
        // Drawing window
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRoundRect(windowX, windowY, width, height, 10, 10);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(windowX, windowY, width, height, 10, 10);

        // Drawing Title
        gc.setFill(Color.WHITE);
        gc.setFont(gamePanel.getInventoryFont());
        gc.fillText("Inventory", windowX + 85, windowY + 25);

        // Drawing player's hp
        String textHP = "HP: " + gamePanel.getPlayer().getHp();
        gc.fillText(textHP, windowX + 10, windowY + 50);

        // Drawing items
        gc.fillText("Items:", windowX + 10, windowY + 75);

        // Drawing kills counter
        String textKills = "Kills: " + gamePanel.getPlayer().getKillsCounter();
        gc.fillText(textKills, windowX + 70, windowY + 50);

        // Drawing slots
        int slotX = (int) windowX + 10;
        int slotY = (int) windowY + 90;
        int slotSize = 40;
        int slotSpacing = 50;

        gc.setFill(Color.rgb(0, 0, 0, 0));
        gc.setLineWidth(1);

        for (int i = 0; i < 4; i++) {
            int slotXPos = slotX + i * slotSpacing;

            gc.fillRoundRect(slotXPos, slotY, slotSize, slotSize, 10, 10);
            gc.strokeRoundRect(slotXPos, slotY, slotSize, slotSize, 10, 10);

            if (!gamePanel.getPlayer().getInventory().isEmpty() && i < gamePanel.getPlayer().getInventory().size() && gamePanel.getPlayer().getInventory().get(i) != null) {
                gc.drawImage(gamePanel.getPlayer().getInventory().get(i).getImage(), slotXPos + 5, slotY + 5, 30, 30);
            }
        }
    }

    /**
     * Checks if the inventory is currently opened.
     *
     * @return true if the inventory is opened, false otherwise.
     */
    public boolean isOpened() {
        return isOpened;
    }

    /**
     * Sets the opened state of the inventory.
     *
     * @param opened true to open the inventory, false to close it.
     */
    public void setOpened(boolean opened) {
        isOpened = opened;
    }
}
