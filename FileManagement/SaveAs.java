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
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import paint.jrobledo.PaintGUI;

/**
 *
 * @author Jonathan Robledo
 */
public final class SaveAs {
    
    String imgLocation;
    SnapshotParameters sp;
    Canvas canvas;
    
    /**
    *   Save AS is the constructor used to save an image to a different directory from the original.
    *   
    *   @param imgLocation      Sets the current string which contains the directory to be saved to
    *   @param sp               Sets the current SnapshotParameters
    *   @param canvas           Sets the current active Canvas.
    * 
    *   @author Jonathan R.
    */
    
    public SaveAs(String imgLocation, SnapshotParameters sp, Canvas canvas){
        this.imgLocation = imgLocation;
        this.sp = sp;
        this.canvas = canvas;
        
        SaveAs();
    }
    
    /**
    * <p>Saves the current image to a new directory via the File Explorer. </p>
    * @since 0.3.0
    */
    
    public void SaveAs(){
        FileChooser imgExplorer = new FileChooser();
            
            FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            FileChooser.ExtensionFilter BMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");

            imgExplorer.getExtensionFilters().addAll(JPG, PNG, BMP);
            
            File file = imgExplorer.showSaveDialog(null);
            
            sp.setFill(Color.TRANSPARENT);
                
                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                        canvas.snapshot(sp, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                        
                        imgLocation = file.toURI().toString();
                        System.out.println(imgLocation);

                    } catch (IOException ex) {
                        Logger.getLogger(PaintGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
    }
}
