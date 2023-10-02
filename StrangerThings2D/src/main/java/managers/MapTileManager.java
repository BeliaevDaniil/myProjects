package managers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GamePanel;
import mapTiles.MapTile;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The MapTileManager class is responsible for managing map tiles and drawing them on the game panel.
 * It loads tile images, fills the map with tiles, and handles the drawing of tiles on the screen.
 */
public class MapTileManager {
    private final GamePanel gamePanel;
    private final MapTile[] tile;
    private final int[][] mapTileVal;

    //Logging
    private static final Logger LOGGER = Logger.getLogger(MapTileManager.class.getName());


    /**
     * Constructs a MapTileManager object with the specified GamePanel.
     *
     * @param gamePanel The GamePanel associated with this MapTileManager.
     * @throws IOException If an error occurs while initializing the MapTileManager.
     */
    public MapTileManager(GamePanel gamePanel) throws IOException {
        this.gamePanel = gamePanel;
        tile = new MapTile[10];
        mapTileVal = new int[gamePanel.getMapCol()][gamePanel.getMapRow()];
        String map = "/maps/bigMap01.txt";
        loadMapTileImage();
        fillMapWithTiles(map);
        FileHandler fh = new FileHandler("src/main/resources/logging/logs.txt");
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
    }

    /**
     * Loads the map tile images from files.
     * Handles exceptions and logs any errors.
     */
    public void loadMapTileImage(){
        try {
            //Grass
            tile[0] = new MapTile();
            tile[0].image = new Image(new File("src/main/resources/mapTiles/grass.png").toURI().toString());
            //Water
            tile[1] = new MapTile();
            tile[1].image = new Image(new File("src/main/resources/mapTiles/water.png").toURI().toString());
            tile[1].solid = true;
            //Wall
            tile[2] = new MapTile();
            tile[2].image = new Image(new File("src/main/resources/mapTiles/wall.png").toURI().toString());
            tile[2].solid = true;
            //Water lilly
            tile[3] = new MapTile();
            tile[3].image = new Image(new File("src/main/resources/mapTiles/waterLilly.png").toURI().toString());
            //Tree
            tile[4] = new MapTile();
            tile[4].image = new Image(new File("src/main/resources/mapTiles/tree.png").toURI().toString());
            tile[4].solid = true;
            //Sand
            tile[5] = new MapTile();
            tile[5].image = new Image(new File("src/main/resources/mapTiles/sand.png").toURI().toString());
            //Bridge1
            tile[6] = new MapTile();
            tile[6].image = new Image(new File("src/main/resources/mapTiles/bridge1.png").toURI().toString());
            //Bridge2
            tile[7] = new MapTile();
            tile[7].image = new Image(new File("src/main/resources/mapTiles/bridge2.png").toURI().toString());
            //Bridge3
            tile[8] = new MapTile();
            tile[8].image = new Image(new File("src/main/resources/mapTiles/bridge3.png").toURI().toString());
            //Bridge4
            tile[9] = new MapTile();
            tile[9].image = new Image(new File("src/main/resources/mapTiles/bridge4.png").toURI().toString());
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Draws the map tiles on the GraphicsContext provided.
     *
     * @param gc The GraphicsContext on which to draw the map tiles.
     */
    public void drawTiles(GraphicsContext gc){
        int col;
        int row;

        for (row = 0; row < gamePanel.getMapRow(); row++) {
            for (col = 0; col < gamePanel.getMapCol(); col++) {

                int tileNum = mapTileVal[col][row];
                //Position of tile on the map
                int mapX = col * gamePanel.getTileSize();
                int mapY = row * gamePanel.getTileSize();
                //Position of tile on the player`s window
                int tileX = mapX - gamePanel.getPlayer().getMapX() + gamePanel.getPlayer().getWindowX();
                int tileY = mapY - gamePanel.getPlayer().getMapY() + gamePanel.getPlayer().getWindowY();

                //Due to this "if statement" method draws not the whole map, but only those tiles which are
                //in the player`s window
                if (Math.abs(mapX - gamePanel.getPlayer().getMapX()) < gamePanel.getTileSize() + gamePanel.getPlayer().getWindowX() &&
                    Math.abs(mapY - gamePanel.getPlayer().getMapY()) < gamePanel.getTileSize() + gamePanel.getPlayer().getWindowY()) {
                    gc.drawImage(tile[tileNum].image, tileX, tileY, gamePanel.getTileSize(), gamePanel.getTileSize());
                }
            }
        }
    }

    /**
     * Fills the map with tiles based on the specified map file.
     *
     * @param mapPath The path to the map file.
     */
    public void fillMapWithTiles(String mapPath){
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while (col < gamePanel.getMapCol() && row < gamePanel.getMapRow()) {
                String line = br.readLine();
                while(col < gamePanel.getMapCol()) {
                    String[] numbers =line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileVal[col][row] = num;
                    col++;
                }
                if (col == gamePanel.getMapCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the array of map tiles.
     *
     * @return The array of map tiles.
     */
    public MapTile[] getTile() {
        return tile;
    }

    /**
     * Returns the 2D array representing the map tile values.
     *
     * @return The 2D array representing the map tile values.
     */
    public int[][] getMapTileVal() {
        return mapTileVal;
    }
}
