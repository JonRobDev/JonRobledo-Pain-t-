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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author acoff
 */
public final class Open {
    
    Canvas canvas;
    GraphicsContext g;
    String imgLocation;
    double imgX;
    double imgY;
    
    Image image;
    
    public Open(Canvas canvas, GraphicsContext g, String imgLocation, double imgX, double imgY){
        this.canvas = canvas;
        this.g = g;
        this.imgLocation = imgLocation;
        this.imgX = imgX;
        this.imgY = imgY;
        
        Open();
    }
    
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
                
                /*imageBox.setFitHeight(img.getHeight());
                imageBox.setFitWidth(img.getWidth());
                imageBox.setImage(img);*/
                
                g.drawImage(image, 0, 0);
                
                imgX = img.getWidth();
                imgY = img.getHeight();
            }
    }
    
    public double[] GetDimensions(){
        double[] dimensions = {imgX, imgY};
        return dimensions;
    }
    
    public String returnDir(){
        return imgLocation;
    }
    
    public Image returnImg(){
        return image;
    }
}
