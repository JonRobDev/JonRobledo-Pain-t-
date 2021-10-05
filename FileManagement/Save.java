/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.FileManagement;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import paint.jrobledo.PaintGUI;

/**
 *
 * @author acoff
 */
public final class Save {
    String imgLocation;
    SnapshotParameters sp;
    Canvas canvas;
    
    public Save(String imgLocation, SnapshotParameters sp, Canvas canvas){
        this.imgLocation = imgLocation;
        this.sp = sp;
        this.canvas = canvas;
        
        Save();
    }
    
    public void Save(){
        FileChooser imgExplorer = new FileChooser();
            
            FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            FileChooser.ExtensionFilter BMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");

            imgExplorer.getExtensionFilters().addAll(JPG, PNG, BMP);
            
            System.out.println(imgLocation);

            File file = new File(imgLocation);
            
            sp.setFill(Color.TRANSPARENT);
                
                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                        canvas.snapshot(sp, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                        System.out.println(imgLocation);
                    } catch (IOException ex) {
                        Logger.getLogger(PaintGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
    }
}
