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
public class DrawLine {
    
    int x1;
    int y1;
    int x2;
    int y2;
    
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton line;
    private double lineWidth;
    WritableImage writableImage;
    
    public DrawLine(Canvas canvas, GraphicsContext g, ToggleButton line, double lineWidth){
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
    
    public void onDrag(MouseEvent e){
        x2 = (int)e.getX();
        y2 = (int)e.getY();
        
        g.drawImage(writableImage, 0, 0);
        if (line.isSelected() == true) g.strokeLine(x1, y1, x2, y2);
        
    }
    
    public void onRelease(MouseEvent e){
        x2 = (int)e.getX();
        y2 = (int)e.getY();
            
            
        if (line.isSelected() == true) {
            g.drawImage(writableImage, 0, 0);
            g.strokeLine(x1, y1, x2, y2);
        }
    }
    
    public void setWidth(double lineWidth){
        this.lineWidth = lineWidth;
    }
}
