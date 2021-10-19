/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Obsolete;

import java.util.Optional;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 *
 * @author acoff
 */
public class TextDraw {
    
    int x1;
    int y1;
    int x2;
    int y2;
    
    int size;
    String textStr;
    Canvas canvas;
    GraphicsContext g;
    ToggleButton textBtn;
    double lineWidth;
    TextInputDialog td;
    
    Optional<String> result;

    
    public TextDraw(Canvas canvas, GraphicsContext g, ToggleButton textBtn, double lineWidth){
        this.g = g;
        this.canvas = canvas;
        this.textBtn = textBtn;
        this.lineWidth = lineWidth;
        
    }
    
    public void onClick(MouseEvent e, double lineWidth){
        td = new TextInputDialog("Input your text here: ");
        
        td.setTitle("Text");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        
        td.setContentText("Text");
        
        result = td.showAndWait();
        
        textStr = result.get();
      
        x1 = (int)e.getX();
        y1 = (int)e.getY();
    }
    
    public void onRelease(KeyEvent e, double lineWidth){
        g.setFont(new Font("Arial", lineWidth));
        if(e.getCode() == KeyCode.ENTER){
            System.out.println(result);
            g.fillText(textStr, x1, y1);
        }
    }
    
    
    
    
}
