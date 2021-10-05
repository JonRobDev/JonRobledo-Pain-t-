/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
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
    
    public NPoly(Canvas canvas, GraphicsContext g, ToggleButton rect, double lineWidth, int sides, int radius){
        this.canvas = canvas;
        this.g = g;
        this.rect = rect;
        this.lineWidth = lineWidth;
        this.sides = sides;
        this.radius = radius;
    }
    
    public void onClick(MouseEvent e, double lineWidth){ 
        x1 = (int)e.getX();
        y1 = (int)e.getY();
        
        g.setLineWidth(lineWidth);
    }
    
    public void OnRelease(MouseEvent e, int sides, int radius){
        System.out.println("come on do something");
        System.out.println("sides: " + sides);
        System.out.println("radius: " + radius);
        
        double centerX = x1;
        double centerY = y1;
                
        xPoly = new double[sides - 1];
        yPoly = new double[sides - 1];
        
        for( int i = 0; i < sides; i++){
            xPoly[i] = centerX + radius * Math.cos(2 * i * Math.PI / sides); 
            yPoly[i] = centerY - radius * Math.sin(2 * i * Math.PI / sides); 
        }
        
        g.strokePolygon(xPoly, yPoly, sides);
    }
}
