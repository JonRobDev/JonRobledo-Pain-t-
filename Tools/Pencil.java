/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author acoff
 */
public class Pencil {
    int x1;
    int y1;
    int x2;
    int y2;
    
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton pencil;
    private double lineWidth;
    
    Boolean isHeld = false;
    
    public Pencil(Canvas canvas, GraphicsContext g, ToggleButton pencil, double lineWidth){
        this.canvas = canvas;
        this.g = g;
        this.pencil = pencil;
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
            
            
        if (pencil.isSelected() == true) g.strokeLine(x2, y2, x1, y1);
            
        x2 = x1;
        y2 = y1;
    }
    public void OnRelease(MouseEvent e){
        isHeld = false;
    }
}
