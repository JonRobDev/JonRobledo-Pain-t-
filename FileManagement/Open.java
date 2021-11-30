/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.FileManagement;

import java.io.File;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 *
 * @author Jonathan Robledo
 */
public final class Open {
    
    Canvas canvas;
    GraphicsContext g;
    String imgLocation;
    double imgX;
    double imgY;
    
    Image image;
    
    /**
    *   Save is the constructor used to save an image based on the current directory selected.
    *  
    *   @param canvas           Sets the current active Canvas.
    *   @param g                Sets the current active GraphicsContext.
    *   @param imgLocation      Sets the current string which contains the directory to be saved to.
    *   @param imgX             Sets the indicated width of the image.
    *   @param imgY             Sets the indicated height of the image.
    * 
    *   @author Jonathan R.
    */
    
    public Open(Canvas canvas, GraphicsContext g, String imgLocation, double imgX, double imgY){
        this.canvas = canvas;
        this.g = g;
        this.imgLocation = imgLocation;
        this.imgX = imgX;
        this.imgY = imgY;
        
        Open();
    }
    
    /**
    * <p> Opens an image to display on the canvas via the File Explorer. </p>
    * @since 0.2.0
    */
    
    public void Open(){
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    
            FileChooser imgExplorer = new FileChooser();
            
            FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            FileChooser.ExtensionFilter BMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");

            imgExplorer.getExtensionFilters().addAll(JPG, PNG, BMP);

            File imageFile = imgExplorer.showOpenDialog(null);
            
            if (imageFile != null) {
                System.out.println(imageFile.toURI().toString());
                Image img = new Image(imageFile.toURI().toString());
                
                image = img;
                
                imgLocation = imageFile.toURI().toString().substring(5);
                
                int x = (int)img.getWidth();
                int y = (int)img.getHeight();
                
                canvas.setHeight(y);
                canvas.setWidth(x);
                
                g.drawImage(image, 0, 0);
                
                imgX = img.getWidth();
                imgY = img.getHeight();
            }
    }
    
    /**
    * <p> Returns dimensions of the image being currently opened. </p>
    * @return double[]
    * @since 0.3.0
    */
    public double[] GetDimensions(){
        double[] dimensions = {imgX, imgY};
        return dimensions;
    }
    
    /**
    * <p> Returns the directory of the image being opened. </p>
    * @return String
    * @since 0.3.0
    */
    public String returnDir(){
        return imgLocation;
    }
    
    /**
    * <p> Returns the current image being opened. </p>
    * @return Image
    * @since 0.3.0
    */
    public Image returnImg(){
        return image;
    }
}
