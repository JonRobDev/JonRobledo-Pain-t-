/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Obsolete;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author acoff
 */
public class Rect {
     int x1;
    int y1;
    int x2;
    int y2;
    
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton rect;
    private double lineWidth;
    
    WritableImage writableImage;
    
    /**
    *   Rect is the constructor used to set all variables intended for drawing a rectangle on the current Canvas.
    * 
    *   @param canvas       Sets the current canvas
    *   @param g            Sets the current GraphicsContext
    *   @param rect         Sets the ToggleButtons assigned to this tool in ToolsMenu
    *   @param lineWidth    Sets the current line width.
    *   
    *   @author Jonathan R.
    */
    
    public Rect(Canvas canvas, GraphicsContext g, ToggleButton rect, double lineWidth){
        this.canvas = canvas;
        this.g = g;
        this.rect = rect;
        this.lineWidth = lineWidth;
    }
    
    /**
    * <p> Registers the mouse position in X and Y upon clicking and sets an image for live draw functionality. </p>
    * @since 0.3.0
    * @param e          The mouse event of the current canvas.
    * @param lineWidth  Sets the line width for the graphics context.
    */
    
    public void onClick(MouseEvent e, double lineWidth){
        x1 = (int)e.getX();
        y1 = (int)e.getY();
        
        writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);
        
        g.setLineWidth(lineWidth);
    }
    
    /**
    * <p> Grabs the current position of the mouse and live draws a rectangle based on the dimensions of x1, x2, y1, and y2. </p>
    * @since 0.3.0
    * @param e          The mouse event of the current canvas.
    * @param fill       ToggleButton meant for setting wether fill is active or not.
    */
    
    
    public void onDrag(MouseEvent e, ToggleButton fill){
        x2 = (int)e.getX();
        y2 = (int)e.getY();
        
        g.drawImage(writableImage, 0, 0);
        
        if( y1 > y2 && rect.isSelected() == true) {
            if( x1 > x2 ){
                if( fill.isSelected() == true) g.fillRect(x2, y2,  (x1 - x2) , (y1 - y2));
                g.strokeRect(x2, y2,  (x1 - x2) , (y1 - y2) );
            }else{
                if( fill.isSelected() == true) g.fillRect(x1, y2,  (x2 - x1) , (y1 - y2));
                g.strokeRect(x1, y2,  (x2 - x1) , (y1 - y2) );
            }
        }else{
            if( x1 > x2 ){
                if( fill.isSelected() == true) g.fillRect(x2, y1,  (x1 - x2) , (y2 - y1));
                g.strokeRect(x2, y1,  (x1 - x2) , (y2 - y1) );
            }else{
                if( fill.isSelected() == true) g.fillRect(x1, y1,  (x2 - x1) , (y2 - y1));
                g.strokeRect(x1, y1,  (x2 - x1) , (y2 - y1) );
            }
        }
        
    }
    
    /**
    * <p> Draws the final rectangle based on the current mouse position. </p>
    * @since 0.3.0
    * @param e          The mouse event of the current canvas.
    * @param fill       ToggleButton meant for setting wether fill is active or not.
    */
    
    public void onRelease(MouseEvent e, ToggleButton fill){
        x2 = (int)e.getX();
        y2 = (int)e.getY();
           
        if( y1 > y2 && rect.isSelected() == true) {
            if( x1 > x2 ){
                if( fill.isSelected() == true) g.fillRect(x2, y2,  (x1 - x2) , (y1 - y2));
                g.strokeRect(x2, y2,  (x1 - x2) , (y1 - y2) );
            }else{
                if( fill.isSelected() == true) g.fillRect(x1, y2,  (x2 - x1) , (y1 - y2));
                g.strokeRect(x1, y2,  (x2 - x1) , (y1 - y2) );
            }
        }else{
            if( x1 > x2 ){
                if( fill.isSelected() == true) g.fillRect(x2, y1,  (x1 - x2) , (y2 - y1));
                g.strokeRect(x2, y1,  (x1 - x2) , (y2 - y1) );
            }else{
                if( fill.isSelected() == true) g.fillRect(x1, y1,  (x2 - x1) , (y2 - y1));
                g.strokeRect(x1, y1,  (x2 - x1) , (y2 - y1) );
            }
        }
    }
}
