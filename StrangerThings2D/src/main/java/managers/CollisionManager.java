package managers;


import creatures.Creature;
import javafx.geometry.Rectangle2D;
import main.GamePanel;

/**
 * The CollisionManager class is responsible for managing collision detection in the game.
 * It checks for collisions between hitBoxes of creatures and tiles, as well as between different creatures.
 */
public class CollisionManager {
    private final GamePanel gamePanel;

    /**
     * Constructs a CollisionManager object with the specified GamePanel.
     *
     * @param gamePanel the GamePanel associated with this CollisionManager.
     */
    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Checks if two hitBoxes intersect each other.
     *
     * @param hitBox1 the first hitBox.
     * @param hitBox2 the second hitBox.
     * @return true if the hitBoxes intersect, false otherwise.
     */
    public boolean collides(Rectangle2D hitBox1, Rectangle2D hitBox2) {
        return hitBox1.intersects(hitBox2);
    }

    /**
     * Checks for collision between two creatures.
     * Sets the collision flag of the first creature accordingly.
     *
     * @param creature1 the first creature.
     * @param creature2 the second creature.
     */
    public void checkCollisionWithCreatures(Creature creature1, Creature creature2){
        creature1.setCollision(collides(creature1.getHitBox(), creature2.getHitBox()));
    }

    /**
     * Checks for collision between a creature and tiles in the game panel.
     * Sets the collision flag of the creature accordingly.
     *
     * @param creature the creature to check collision with tiles.
     */
    public void checkCollisionWithTiles(Creature creature){
        //Coordinates of creature`s hitBox
        int hitBoxLeftMapX = (int)(creature.getHitBox().getMinX());
        int hitBoxRightMapX = (int)(creature.getHitBox().getMinX() + creature.getHitBox().getWidth());
        int hitBoxTopMapY = (int)(creature.getHitBox().getMinY());
        int hitBoxBottomMapY = (int)(creature.getHitBox().getMinY() + creature.getHitBox().getHeight());

        //Indexes of tiles that collide with player
        int leftColIndexOfTile = hitBoxLeftMapX/gamePanel.getTileSize();
        int rightColIndexOfTile = hitBoxRightMapX/gamePanel.getTileSize();
        int topColIndexOfTile = hitBoxTopMapY/gamePanel.getTileSize();
        int bottomColIndexOfTile = hitBoxBottomMapY/gamePanel.getTileSize();

        //Number values of tiles
        int mapTileVal1, mapTileVal2;

        switch (creature.getDirection()) {
            case "up":
                topColIndexOfTile = (hitBoxTopMapY - creature.getSpeed())/gamePanel.getTileSize();
                if (topColIndexOfTile > 0) {
                    mapTileVal1 = gamePanel.getTileManager().getMapTileVal()[leftColIndexOfTile][topColIndexOfTile];
                    mapTileVal2 = gamePanel.getTileManager().getMapTileVal()[rightColIndexOfTile][topColIndexOfTile];
                } else {
                    mapTileVal1 = 4;
                    mapTileVal2 = 4;
                }
                if (gamePanel.getTileManager().getTile()[mapTileVal1].solid || gamePanel.getTileManager().getTile()[mapTileVal2].solid) {
                    creature.setCollision(true);
                }
                break;
            case "down":
                bottomColIndexOfTile = (hitBoxBottomMapY + creature.getSpeed())/gamePanel.getTileSize();
                if (bottomColIndexOfTile != 50 ) {
                    mapTileVal1 = gamePanel.getTileManager().getMapTileVal()[leftColIndexOfTile][bottomColIndexOfTile];
                    mapTileVal2 = gamePanel.getTileManager().getMapTileVal()[rightColIndexOfTile][bottomColIndexOfTile];
                } else {
                    mapTileVal1 = 4;
                    mapTileVal2 = 4;
                }
                if (gamePanel.getTileManager().getTile()[mapTileVal1].solid || gamePanel.getTileManager().getTile()[mapTileVal2].solid) {
                    creature.setCollision(true);
                }
                break;
            case "left":
                leftColIndexOfTile = (hitBoxLeftMapX - creature.getSpeed())/gamePanel.getTileSize();
                if (leftColIndexOfTile > 0 ) {
                    mapTileVal1 = gamePanel.getTileManager().getMapTileVal()[leftColIndexOfTile][topColIndexOfTile];
                    mapTileVal2 = gamePanel.getTileManager().getMapTileVal()[leftColIndexOfTile][bottomColIndexOfTile];
                } else {
                    mapTileVal1 = 4;
                    mapTileVal2 = 4;
                }
                if ((gamePanel.getTileManager().getTile()[mapTileVal1].solid || gamePanel.getTileManager().getTile()[mapTileVal2].solid)) {
                    creature.setCollision(true);
                }
                break;
            case "right":
                rightColIndexOfTile = (hitBoxRightMapX + creature.getSpeed())/gamePanel.getTileSize();
                if (rightColIndexOfTile != 50 ) {
                    mapTileVal1 = gamePanel.getTileManager().getMapTileVal()[rightColIndexOfTile][topColIndexOfTile];
                    mapTileVal2 = gamePanel.getTileManager().getMapTileVal()[rightColIndexOfTile][bottomColIndexOfTile];
                } else {
                    mapTileVal1 = 4;
                    mapTileVal2 = 4;
                }
                if (gamePanel.getTileManager().getTile()[mapTileVal1].solid || gamePanel.getTileManager().getTile()[mapTileVal2].solid) {
                    creature.setCollision(true);
                }
                break;
        }
    }
}