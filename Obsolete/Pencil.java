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
    
    /**
    *   Pencil is the constructor used to set all variables intended for freehand drawing on the current Canvas.
    * 
    *   @param canvas       Sets the current canvas
    *   @param g            Sets the current GraphicsContext
    *   @param pencil         Sets the ToggleButtons assigned to this tool in ToolsMenu
    *   @param lineWidth    Sets the current line width.
    *   
    *   @author Jonathan R.
    */
    
    public Pencil(Canvas canvas, GraphicsContext g, ToggleButton pencil, double lineWidth){
        this.canvas = canvas;
        this.g = g;
        this.pencil = pencil;
        this.lineWidth = lineWidth;
    }
    
    /**
    * <p> Registers the mouse position in X and Y upon clicking </p>
    * @since 0.3.0
    * @param e          The mouse event of the current canvas.
    * @param lineWidth  Sets the line width for the graphics context.
    */
    
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
    
    /**
    * <p> Draws a freehand line as long as the left mouse button is held. </p>
    * @since 0.3.0
    * @param e          The mouse event of the current canvas.
    */
    
    public void OnDrag(MouseEvent e){
        if (isHeld == false) return;
            
        x1 = (int)e.getX();
        y1 = (int)e.getY();
            
            
        if (pencil.isSelected() == true) g.strokeLine(x2, y2, x1, y1);
            
        x2 = x1;
        y2 = y1;
    }
    
    /**
    * <p> Stops drawing once the left mouse is released. </p>
    * @since 0.3.0
    * @param e          The mouse event of the current canvas.
    */
    public void OnRelease(MouseEvent e){
        isHeld = false;
    }
}
