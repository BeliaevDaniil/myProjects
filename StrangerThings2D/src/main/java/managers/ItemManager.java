package managers;

import inventoryAndItems.*;
import javafx.scene.canvas.GraphicsContext;
import main.GamePanel;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ItemManager class manages the items in the game, including items on the map and in the player's inventory.
 * It provides functionality for loading items, checking collisions, and drawing items on the screen.
 */
public class ItemManager {
    private final GamePanel gamePanel;

    //Items
    private List<Item> itemsOnMap;
    private final List<Item> allItems;

    //Items serialization files
    private final String INVENTORY_IN;
    private final String ITEMS_ON_MAP_IN;

    //Logging
    private static final Logger LOGGER = Logger.getLogger(ItemManager.class.getName());

    /**
     * Constructs an ItemManager object with the specified GamePanel.
     *
     * @param gamePanel the GamePanel instance.
     * @throws Exception if there is an error initializing the ItemManager.
     */
    public ItemManager(GamePanel gamePanel) throws Exception {
        this.gamePanel = gamePanel;
        itemsOnMap = new ArrayList<>();
        allItems = new ArrayList<>();
        fillAllItems();
        INVENTORY_IN = "src/main/resources/serialization/inventory_in.txt";
        ITEMS_ON_MAP_IN = "src/main/resources/serialization/itemsOnMap_in.txt";
        getItemsFromFile();
        loadItemsOnMap();
        FileHandler fh = new FileHandler("src/main/resources/logging/logs.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    /**
     * Removes all items from the map.
     */
    public void removeAllItemsFromMap(){
        itemsOnMap.clear();
    }

    /**
     * Fills the list of all available items.
     */
    public void fillAllItems(){
        allItems.add(new HealingItem(gamePanel));
        allItems.add(new AttackItem(gamePanel));
    }

    /**
     * Checks if a specific item is present in the player's inventory.
     *
     * @param itemName the name of the item to check.
     * @return true if the item is in the inventory, false otherwise.
     */
    public boolean checkIfItemIsInInventory(String itemName){
        for (Item item : gamePanel.getPlayer().getInventory()) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loads the items on the map from a file.
     *
     * @throws IOException if there is an error reading the file.
     */
    public void loadItemsOnMap() throws IOException {
        List<String> itemsNames = new ArrayList<>();
        List<Integer> itemsCols = new ArrayList<>();
        List<Integer> itemsRows = new ArrayList<>();

        try (
                FileReader fr = new FileReader(ITEMS_ON_MAP_IN);
                BufferedReader br = new BufferedReader(fr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length==3) {
                    String itemName = parts[0];
                    int itemCol = Integer.parseInt(parts[1]);
                    int itemRow = Integer.parseInt(parts[2]);
                    itemsNames.add(itemName);
                    itemsCols.add(itemCol);
                    itemsRows.add(itemRow);
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error reading items from file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        for (int i = 0; i<itemsNames.size(); i++) {
            int col = itemsCols.get(i);
            int row = itemsRows.get(i);
            switch (itemsNames.get(i)) {
                case "HealingItem":
                    HealingItem newHI = new HealingItem(gamePanel, col, row);
                    itemsOnMap.add(newHI);
                    break;
                case "Key":
                    Key newKey = new Key(gamePanel, col, row);
                    itemsOnMap.add(newKey);
                    break;
                case "AttackItem":
                    AttackItem newAI = new AttackItem(gamePanel, col, row);
                    itemsOnMap.add(newAI);
                    break;
                case "Door":
                    Door newDoor = new Door(gamePanel,col,row);
                    itemsOnMap.add(newDoor);
                    break;
            }
        }
    }

    /**
     * Reads the items from a file and adds them to the player's inventory.
     *
     * @throws Exception if there is an error reading the file or if there are too many items in the inventory.
     */
    public void getItemsFromFile() throws Exception {
        List<String> itemsNames= new ArrayList<>();
        int itemCounter = 0;
        try (
                FileReader fr = new FileReader(INVENTORY_IN);
                BufferedReader br = new BufferedReader(fr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                itemsNames.add(line);
            }
        } catch (IOException e) {
            LOGGER.severe("Error reading items from file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        for (String itemName : itemsNames) {
            for (Item item : allItems) {
                if (item.getName().equals(itemName)) {
                    if ((itemCounter < 4) && !itemName.equals("Door")) {
                        gamePanel.getPlayer().addItemToInventory(item);
                        itemCounter++;
                    } else {
                        LOGGER.warning("There are too many items in file 'inventory_in.txt'");
                        throw new Exception("There are too many items in file 'inventory_in.txt'");
                    }
                }
            }
        }
    }


    /**
     * Checks for collisions between the player and the items on the map.
     * If a collision occurs and the player's inventory is not full, the item is added to the inventory.
     * If the inventory is full, an alert message is displayed.
     */
    public void checkCollisionWithPlayer(){
        Iterator<Item> iterator = itemsOnMap.iterator();
        while (iterator.hasNext()) {
            Item itemInList = iterator.next();
            if (gamePanel.getCollisionManager().collides(gamePanel.getPlayer().getHitBox(), itemInList.getHitBox())) {
                if (gamePanel.getPlayer().getInventory().size() < 4) {
                    gamePanel.getPlayer().getInventory().add(itemInList);
                    LOGGER.info("Item: "+itemInList.getName() + " was added to inventory");
                    iterator.remove();
                } else {
                    gamePanel.getInventoryManager().alertInventoryMassage(gamePanel.getGameLoop().getGC());
                }
            }
        }
    }

    /**
     * Draws the items on the map on the graphics context.
     *
     * @param gc the GraphicsContext to draw on.
     */
    public void drawItems(GraphicsContext gc){
        for (Item item : itemsOnMap) {
            int itemWindowX = item.getMapX() - gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getWindowX();
            int itemWindowY = item.getMapY() - gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getWindowY();
            if (Math.abs(item.getMapX() - gamePanel.getPlayer().getMapX()) < gamePanel.getTileSize() + gamePanel.getPlayer().getWindowX() &&
                Math.abs(item.getMapY() - gamePanel.getPlayer().getMapY()) < gamePanel.getTileSize() + gamePanel.getPlayer().getWindowY()) {
                gc.drawImage(item.getImage(), itemWindowX, itemWindowY, gamePanel.getTileSize(), gamePanel.getTileSize());
            }
        }
    }


    /**
     * Returns the list of items on the map.
     *
     * @return the list of items on the map.
     */
    public List<Item> getItemsOnMap() {
        return itemsOnMap;
    }

    /**
     * Sets the list of items on the map.
     *
     * @param itemsOnMap the list of items on the map.
     */
    public void setItemsOnMap(List<Item> itemsOnMap) {
        this.itemsOnMap = itemsOnMap;
    }
}