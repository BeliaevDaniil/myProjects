package creatures;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * The Creature class represents a generic creature in the game.
 * It serves as a base class for different types of creatures in the game.
 */
public abstract class Creature {
    private int mapX, mapY;
    private int windowX, windowY;
    private int col;
    private int row;
    private int speed;
    private int hp;
    private boolean collision = false;
    private String direction;
    private int moveCounter = 0;
    private int moveNum = 1;
    private int hitCounter;

    private Image up1, up2, down1, down2, left1, left2, right1, right2;
    private Image upAttack1, upAttack2, downAttack1, downAttack2, leftAttack1, leftAttack2, rightAttack1, rightAttack2;

    private Rectangle2D hitBox;
    private Rectangle2D actionZone;
    private double actionZoneWidth;
    private double actionZoneHeight;

    private boolean attackIsActive = false;

    /**
     * Retrieves the current X-coordinate of the creature on the game map.
     *
     * @return The X-coordinate of the creature on the game map.
     */
    public int getMapX() {
        return mapX;
    }

    /**
     * Retrieves the current Y-coordinate of the creature on the game map.
     *
     * @return The Y-coordinate of the creature on the game map.
     */
    public int getMapY() {
        return mapY;
    }

    /**
     * Sets the X-coordinate of the creature on the game map.
     *
     * @param mapX The X-coordinate of the creature on the game map.
     */
    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    /**
     * Sets the Y-coordinate of the creature on the game map.
     *
     * @param mapY The Y-coordinate of the creature on the game map.
     */
    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    /**
     * Increases the Y-coordinate of the creature on the game map by the specified amount.
     *
     * @param num The amount to increase the Y-coordinate by.
     */
    public void increaseMapY(int num){
        this.mapY+=num;
    }

    /**
     * Decreases the Y-coordinate of the creature on the game map by the specified amount.
     *
     * @param num The amount to decrease the Y-coordinate by.
     */
    public void decreaseMapY(int num){
        this.mapY-=num;
    }

    /**
     * Increases the X-coordinate of the creature on the game map by the specified amount.
     *
     * @param num The amount to increase the X-coordinate by.
     */
    public void increaseMapX(int num){
        this.mapX+=num;
    }

    /**
     * Decreases the X-coordinate of the creature on the game map by the specified amount.
     *
     * @param num The amount to decrease the X-coordinate by.
     */
    public void decreaseMapX(int num){
        this.mapX-=num;
    }

    /**
     * Retrieves the column index of the creature's position in the game map.
     *
     * @return The column index of the creature's position in the game map.
     */
    public int getCol() {
        return col;
    }

    /**
     * Retrieves the row index of the creature's position in the game map.
     *
     * @return The row index of the creature's position in the game map.
     */
    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void decreaseHP(int num){
        this.hp-=num;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public void increaseMoveCounter(int num){
        this.moveCounter+=num;
    }

    public int getMoveNum() {
        return moveNum;
    }

    public void setMoveNum(int moveNum) {
        this.moveNum = moveNum;
    }

    public int getHitCounter() {
        return hitCounter;
    }

    public void setHitCounter(int hitCounter) {
        this.hitCounter = hitCounter;
    }

    public void increaseHitCounter(int num){
        this.hitCounter+=num;
    }

    public Image getUp1() {
        return up1;
    }

    public void setUp1(Image up1) {
        this.up1 = up1;
    }

    public Image getUp2() {
        return up2;
    }

    public void setUp2(Image up2) {
        this.up2 = up2;
    }

    public Image getDown1() {
        return down1;
    }

    public void setDown1(Image down1) {
        this.down1 = down1;
    }

    public Image getDown2() {
        return down2;
    }

    public void setDown2(Image down2) {
        this.down2 = down2;
    }

    public Image getLeft1() {
        return left1;
    }

    public void setLeft1(Image left1) {
        this.left1 = left1;
    }

    public Image getLeft2() {
        return left2;
    }

    public void setLeft2(Image left2) {
        this.left2 = left2;
    }

    public Image getRight1() {
        return right1;
    }

    public void setRight1(Image right1) {
        this.right1 = right1;
    }

    public Image getRight2() {
        return right2;
    }

    public void setRight2(Image right2) {
        this.right2 = right2;
    }

    public Image getUpAttack1() {
        return upAttack1;
    }

    public void setUpAttack1(Image upAttack1) {
        this.upAttack1 = upAttack1;
    }

    public Image getUpAttack2() {
        return upAttack2;
    }

    public void setUpAttack2(Image upAttack2) {
        this.upAttack2 = upAttack2;
    }

    public Image getDownAttack1() {
        return downAttack1;
    }

    public void setDownAttack1(Image downAttack1) {
        this.downAttack1 = downAttack1;
    }

    public Image getDownAttack2() {
        return downAttack2;
    }

    public void setDownAttack2(Image downAttack2) {
        this.downAttack2 = downAttack2;
    }

    public Image getLeftAttack1() {
        return leftAttack1;
    }

    public void setLeftAttack1(Image leftAttack1) {
        this.leftAttack1 = leftAttack1;
    }

    public Image getLeftAttack2() {
        return leftAttack2;
    }

    public void setLeftAttack2(Image leftAttack2) {
        this.leftAttack2 = leftAttack2;
    }

    public Image getRightAttack1() {
        return rightAttack1;
    }

    public void setRightAttack1(Image rightAttack1) {
        this.rightAttack1 = rightAttack1;
    }

    public Image getRightAttack2() {
        return rightAttack2;
    }

    public void setRightAttack2(Image rightAttack2) {
        this.rightAttack2 = rightAttack2;
    }

    public Rectangle2D getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle2D hitBox) {
        this.hitBox = hitBox;
    }

    public Rectangle2D getActionZone() {
        return actionZone;
    }

    public void setActionZone(Rectangle2D actionZone) {
        this.actionZone = actionZone;
    }

    public double getActionZoneWidth() {
        return actionZoneWidth;
    }

    public void setActionZoneWidth(double actionZoneWidth) {
        this.actionZoneWidth = actionZoneWidth;
    }

    public double getActionZoneHeight() {
        return actionZoneHeight;
    }

    public void setActionZoneHeight(double actionZoneHeight) {
        this.actionZoneHeight = actionZoneHeight;
    }

    public boolean isAttackIsActive() {
        return attackIsActive;
    }

    public void setAttackIsActive(boolean attackIsActive) {
        this.attackIsActive = attackIsActive;
    }

    public int getWindowX() {
        return windowX;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }
}
