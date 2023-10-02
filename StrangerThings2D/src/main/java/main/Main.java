package main;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The Main class is the entry point of the game application.
 * It extends the Application class from JavaFX.
 */
public class Main extends Application {
    /**
     * The main entry point for the JavaFX application.
     * It creates the game panel, sets up the stage, and starts the game.
     *
     * @param stage the primary stage for the application.
     * @throws Exception if an exception occurs during application startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        GamePanel gamePanel = new GamePanel(stage);
        Group root = new Group(gamePanel);
        Scene scene = new Scene(root);
        stage.setTitle("Stranger Things 2D");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        gamePanel.closeWindowWarning(stage);
        gamePanel.startGame();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, gamePanel.getKeyManager());
        scene.addEventHandler(KeyEvent.KEY_RELEASED, gamePanel.getKeyManager());
        stage.show();
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}