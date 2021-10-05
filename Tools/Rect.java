/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Tools;

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
    
    public Rect(Canvas canvas, GraphicsContext g, ToggleButton rect, double lineWidth){
        this.canvas = canvas;
        this.g = g;
        this.rect = rect;
        this.lineWidth = lineWidth;
    }
    
    public void onClick(MouseEvent e, double lineWidth){
        x1 = (int)e.getX();
        y1 = (int)e.getY();
        
        writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);
        
        g.setLineWidth(lineWidth);
    }
    
    
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
    
    public void setWidth(double lineWidth){
        this.lineWidth = lineWidth;
    }
}
