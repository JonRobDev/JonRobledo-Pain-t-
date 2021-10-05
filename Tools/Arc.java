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
import javafx.scene.shape.ArcType;

/**
 *
 * @author acoff
 */
public class Arc {
    double x1;
    double y1;
    double x2;
    double y2;
    
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton line;
    private double lineWidth;
    WritableImage writableImage;
    
    public Arc(Canvas canvas, GraphicsContext g, ToggleButton line, double lineWidth){
        this.canvas = canvas;
        this.g = g;
        this.line = line;
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
        
        if( y1 > y2 && line.isSelected() == true) {
            if( x1 > x2 ){
                if( fill.isSelected() == true) g.fillArc(x2, y2,  (x1 - x2) , (y1 - y2), 0, 180, ArcType.ROUND );
                g.strokeArc(x2, y2,  (x1 - x2) , (y1 - y2), 0, 180, ArcType.OPEN );
            }else{
                if( fill.isSelected() == true) g.fillArc(x1, y2,  (x2 - x1) , (y1 - y2), 0, 180, ArcType.ROUND);
                g.strokeArc(x1, y2,  (x2 - x1) , (y1 - y2), 0, 180, ArcType.OPEN );
            }
        }else{
            if( x1 > x2 ){
                if( fill.isSelected() == true) g.fillArc(x2, y1,  (x1 - x2) , (y2 - y1), 0, 180, ArcType.ROUND);
                g.strokeArc(x2, y1,  (x1 - x2) , (y2 - y1), 0, 180, ArcType.OPEN );
            }else{
                if( fill.isSelected() == true) g.fillArc(x1, y1,  (x2 - x1) , (y2 - y1), 0, 180, ArcType.ROUND);
                g.strokeArc(x1, y1,  (x2 - x1) , (y2 - y1) , 0, 180, ArcType.OPEN );
            }
        }
    }
    
    public void onRelease(MouseEvent e, ToggleButton fill){
        
        x2 = (int)e.getX();
        y2 = (int)e.getY();
        
        if( y1 > y2 && line.isSelected() == true) {
            if( x1 > x2 ){
                if( fill.isSelected() == true) g.fillArc(x2, y2,  (x1 - x2) , (y1 - y2), 0, 180, ArcType.ROUND );
                g.strokeArc(x2, y2,  (x1 - x2) , (y1 - y2), 0, 180, ArcType.OPEN );
            }else{
                if( fill.isSelected() == true) g.fillArc(x1, y2,  (x2 - x1) , (y1 - y2), 0, 180, ArcType.ROUND);
                g.strokeArc(x1, y2,  (x2 - x1) , (y1 - y2), 0, 180, ArcType.OPEN );
            }
        }else{
            if( x1 > x2 ){
                if( fill.isSelected() == true) g.fillArc(x2, y1,  (x1 - x2) , (y2 - y1), 0, 180, ArcType.ROUND);
                g.strokeArc(x2, y1,  (x1 - x2) , (y2 - y1), 0, 180, ArcType.OPEN );
            }else{
                if( fill.isSelected() == true) g.fillArc(x1, y1,  (x2 - x1) , (y2 - y1), 0, 180, ArcType.ROUND);
                g.strokeArc(x1, y1,  (x2 - x1) , (y2 - y1) , 0, 180, ArcType.OPEN );
            }
        }
    }
}
