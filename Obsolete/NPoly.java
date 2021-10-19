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
public class NPoly {
         int x1;
    int y1;
    int x2;
    int y2;
    
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton rect;
    private double lineWidth;
    
    int sides;
    int radius;
    
    double[] xPoly;
    double[] yPoly;
    
    WritableImage writableImage;
    
    /**
    *   Rect is the constructor used to set all variables intended for drawing an even, n-sided polygon on the current Canvas.
    *   @param canvas       Sets the current canvas
    *   @param g            Sets the current GraphicsContext
    *   @param rect         Sets the ToggleButtons assigned to this tool in ToolsMenu
    *   @param lineWidth    Sets the current line width.
    *   @param sides        Sets the amount of sides for the polygon.
    *   @author Jonathan R.
    */
    
    public NPoly(Canvas canvas, GraphicsContext g, ToggleButton rect, double lineWidth, int sides){
        this.canvas = canvas;
        this.g = g;
        this.rect = rect;
        this.lineWidth = lineWidth;
        this.sides = sides;
    }
    
    /**
    * <p> Registers the mouse position in X and Y upon clicking and sets an image for live draw functionality. </p>
    * @since 0.5.0
    * @param e          The mouse event of the current canvas.
    * @param lineWidth  Sets the line width for the graphics context.
    */
    
    public void onClick(MouseEvent e, double lineWidth){ 
        x1 = (int)e.getX();
        y1 = (int)e.getY();
        
        g.setLineWidth(lineWidth);
    }
    
    /**
    * <p> Draws the final rectangle based on the current mouse position. </p>
    * @since 0.5.0
    * @param e          The mouse event of the current canvas.
    * @param sides      The current amount of sides for the intended polygon.
    * @param fill       ToggleButton meant for setting wether fill is active or not.
    */
    public void OnRelease(MouseEvent e, int sides, ToggleButton fill){
        System.out.println("come on do something");
        System.out.println("sides: " + sides);

        radius = (x2 - x1)/2;
        
        System.out.println("radius: " + radius);
        
        double centerX = x1;
        double centerY = y1;
                
        xPoly = new double[sides];
        yPoly = new double[sides];
        
        for( int i = 0; i < sides; i++){
            xPoly[i] = centerX + radius * Math.cos(2 * i * Math.PI / sides); 
            yPoly[i] = centerY - radius * Math.sin(2 * i * Math.PI / sides); 
            
            System.out.println("COORDINATE " + i + ": " + xPoly[i] + ", " + yPoly[i]);
        }
        
        if(fill.isSelected() == true) g.fillPolygon(xPoly, yPoly, sides);
        g.strokePolygon(xPoly, yPoly, sides);
    }
}
