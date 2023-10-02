package managers;

import creatures.BossEnemy;
import creatures.Enemy;
import inventoryAndItems.AttackItem;
import inventoryAndItems.HealingItem;
import inventoryAndItems.Item;
import main.GamePanel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The GameSaveManager class handles the saving and loading of game data.
 */
public class GameSaveManager {
    private final GamePanel gamePanel;
    //Game data storage
    private int tmpPlayerX;
    private int tmpPlayerY;
    private int tmpHP;
    private int tmpKillsCounter;
    private final List<Item> tmpInventory = new ArrayList<>();
    private final List<Item> tmpItemsOnMap = new ArrayList<>();
    private final List<Enemy> tmpEnemies = new ArrayList<>();

    //Data sources
    final String PLAYER_DATA = "src/main/resources/serialization/playerData.txt";
    final String INVENTORY_OUT = "src/main/resources/serialization/inventory_out.txt";
    final String ITEMS_ON_MAP_OUT = "src/main/resources/serialization/itemsOnMap_out.txt";
    final String ENEMIES_ON_MAP_OUT = "src/main/resources/serialization/enemiesOnMap_out.txt";

    //Logging
    private static final Logger LOGGER = Logger.getLogger(GameSaveManager.class.getName());


    /**
     * Constructs a GameSaveManager object.
     *
     * @param gamePanel the GamePanel object
     * @throws IOException if an I/O error occurs
     */
    public GameSaveManager(GamePanel gamePanel) throws IOException {
        this.gamePanel = gamePanel;
        FileHandler fh = new FileHandler("src/main/resources/logging/logs.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    /**
     * Clears the data storage by deleting all saved game data files.
     */
    public void clearDataStorage(){
        File enemiesOnMap_out_file = new File(ENEMIES_ON_MAP_OUT);
        File itemsOnMap_out_file = new File(ITEMS_ON_MAP_OUT);
        File inventory_out_file = new File(INVENTORY_OUT);
        File playerData_file = new File(PLAYER_DATA);
        try {
            FileWriter fw1 = new FileWriter(enemiesOnMap_out_file);
            FileWriter fw2 = new FileWriter(itemsOnMap_out_file);
            FileWriter fw3 = new FileWriter(inventory_out_file);
            FileWriter fw4 = new FileWriter(playerData_file);
            fw1.close();
            fw2.close();
            fw3.close();
            fw4.close();
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Applies the saved game data to the game panel.
     */
    public void applySavedGameData(){
        getGameData();
        gamePanel.getPlayer().setMapX(tmpPlayerX);
        gamePanel.getPlayer().setMapY(tmpPlayerY);
        gamePanel.getPlayer().setHp(tmpHP);
        gamePanel.getPlayer().setKillsCounter(tmpKillsCounter);
        gamePanel.getPlayer().setInventory(tmpInventory);
        gamePanel.getItemManager().setItemsOnMap(tmpItemsOnMap);
        gamePanel.getEnemyManager().setEnemies(tmpEnemies);
    }

    /**
     * Saves the game data.
     */
    public void saveGameData(){
        savePlayerData();
        saveItemsOnMap();
        saveItemsFromInventory();
        saveEnemiesOnMap();
    }

    /**
     * Retrieves the game data from the saved files.
     */
    public void getGameData(){
        getPlayerData();
        getEnemiesOnMap();
        getItemsFromInventory();
        getItemsOnMap();
    }

    /**
     * Saves the player data to a file.
     */
    public void savePlayerData(){
        try (
                FileWriter fw = new FileWriter(PLAYER_DATA);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            bw.write(Integer.toString(gamePanel.getPlayer().getMapX()));
            bw.newLine();
            bw.write(Integer.toString(gamePanel.getPlayer().getMapY()));
            bw.newLine();
            bw.write(Integer.toString(gamePanel.getPlayer().getHp()));
            bw.newLine();
            bw.write(Integer.toString(gamePanel.getPlayer().getKillsCounter()));
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the Player data from file.
     */
    public void getPlayerData(){
        try(
                FileReader fr = new FileReader(PLAYER_DATA);
                BufferedReader br = new BufferedReader(fr)
        ){
            String line;
            List<String> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
            if (!data.isEmpty()) {
                tmpPlayerX = Integer.parseInt(data.get(0));
                tmpPlayerY = Integer.parseInt(data.get(1));
                tmpHP = Integer.parseInt(data.get(2));
                tmpKillsCounter = Integer.parseInt(data.get(3));
            }
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the items on the map to a file.
     */
    public void saveItemsOnMap(){
        try (
                FileWriter fw = new FileWriter(ITEMS_ON_MAP_OUT);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            for (Item item : gamePanel.getItemManager().getItemsOnMap()) {
                bw.write(item.getName() + ", " + item.getCol() + ", " + item.getRow());
                bw.newLine();
            }
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the items on the map from the saved file.
     */
    public void getItemsOnMap(){
        try(
                FileReader fr = new FileReader(ITEMS_ON_MAP_OUT);
                BufferedReader br = new BufferedReader(fr)
        ){
            String line;
            Item item;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length==3){
                    String name = parts[0];
                    int col = Integer.parseInt(parts[1]);
                    int row = Integer.parseInt(parts[2]);
                    switch (name){
                        case "HealingItem":
                            item = new HealingItem(gamePanel,col,row);
                            tmpItemsOnMap.add(item);
                            break;
                        case "AttackItem":
                            item = new AttackItem(gamePanel,col,row);
                            tmpItemsOnMap.add(item);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the items from the player's inventory to a file.
     */
    public void saveItemsFromInventory(){
        try (
                FileWriter fw = new FileWriter(INVENTORY_OUT);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            for (Item item : gamePanel.getPlayer().getInventory()) {
                bw.write(item.getName());
                bw.newLine();
            }
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the items from the player's inventory from the saved file.
     */
    public void getItemsFromInventory(){
        try(
                FileReader fr = new FileReader(INVENTORY_OUT);
                BufferedReader br = new BufferedReader(fr)
        ){
            String line;
            Item item;
            while ((line = br.readLine()) != null) {
                switch (line){
                    case "HealingItem":
                        item = new HealingItem(gamePanel);
                        tmpInventory.add(item);
                        break;
                    case "AttackItem":
                        item = new AttackItem(gamePanel);
                        tmpInventory.add(item);
                        break;
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the enemies on the map to a file.
     */
    public void saveEnemiesOnMap(){
        try (
                FileWriter fw = new FileWriter(ENEMIES_ON_MAP_OUT);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            for (Enemy enemy : gamePanel.getEnemyManager().getEnemies()) {
                bw.write(enemy.getName() + ", " + enemy.getCol() + ", " + enemy.getRow());
                bw.newLine();
            }
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the enemies on the map from the saved file.
     */
    public void getEnemiesOnMap(){
        try(
                FileReader fr = new FileReader(ENEMIES_ON_MAP_OUT);
                BufferedReader br = new BufferedReader(fr)
        ){
            String line;
            Enemy enemy;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length==3){
                    String name = parts[0];
                    int col = Integer.parseInt(parts[1]);
                    int row = Integer.parseInt(parts[2]);
                    switch (name){
                        case "enemy":
                            enemy = new Enemy(gamePanel,col,row);
                            enemy.setName("enemy");
                            tmpEnemies.add(enemy);
                            break;
                        case "boss":
                            enemy = new BossEnemy(gamePanel,col,row);
                            enemy.setName("boss");
                            tmpEnemies.add(enemy);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
