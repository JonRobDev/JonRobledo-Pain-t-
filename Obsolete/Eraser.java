/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Obsolete;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author acoff
 */
public class Eraser {
        int x1;
    int y1;
    int x2;
    int y2;
    
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton eraser;
    private double lineWidth;
    
    Boolean isHeld = false;
    
    public Eraser(Canvas canvas, GraphicsContext g, ToggleButton eraser, double lineWidth){
        this.canvas = canvas;
        this.g = g;
        this.eraser = eraser;
        this.lineWidth = lineWidth;
    }
    
    public void OnClick(MouseEvent e, double lineWidth){
        if (isHeld == true) return;
            
        isHeld = true;
            
        x1 = (int)e.getX();
        y1 = (int)e.getY();
        
        System.out.println(lineWidth);
            
        g.setLineWidth(lineWidth);
            
        x2 = x1;
        y2 = y1;
    }
    public void OnDrag(MouseEvent e){
        if (isHeld == false) return;
            
        x1 = (int)e.getX();
        y1 = (int)e.getY();
            
        if (eraser.isSelected() == true) g.clearRect(x2 - (lineWidth/2), y2 - (lineWidth/2), lineWidth, lineWidth);
            
        x2 = x1;
        y2 = y1;
    }
    public void OnRelease(MouseEvent e){
        isHeld = false;
    }
}
