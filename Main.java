/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author acoff
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        //Instantiates the app GUI
        PaintGUI app = new PaintGUI(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
