/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Tools;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author acoff
 */
public class Select {
    int x1;
    int y1;
    int x2;
    int y2;
    
    int x3;
    int y3;
    int x4;
    int y4;
    
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton rect;
    private double lineWidth;
    
    WritableImage writableImage;
    WritableImage writableImage2;
    
    Boolean moving;
    
    SnapshotParameters sp;
    
    public Select(Canvas canvas, GraphicsContext g, ToggleButton rect, double lineWidth){
        this.canvas = canvas;
        this.g = g;
        this.rect = rect;
        this.lineWidth = lineWidth;
        
        //sp.setFill(Color.TRANSPARENT);
    }
    
    public void onClick(MouseEvent e, double lineWidth){
        x1 = (int)e.getX();
        y1 = (int)e.getY();
        
        writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        
        canvas.snapshot( sp , writableImage);
        
        g.setLineWidth(lineWidth);
    }
    
    public void onDrag(MouseEvent e, ToggleButton fill){
        x2 = (int)e.getX();
        y2 = (int)e.getY();
        
        g.drawImage(writableImage, 0, 0);
           
        if( y1 > y2 && rect.isSelected() == true) {
            if( x1 > x2 ){
                //if( fill.isSelected() == true) g.fillRect(x2, y2,  (x1 - x2) , (y1 - y2));
                g.strokeRect(x2, y2,  (x1 - x2) , (y1 - y2) );
            }else{
                //if( fill.isSelected() == true) g.fillRect(x1, y2,  (x2 - x1) , (y1 - y2));
                g.strokeRect(x1, y2,  (x2 - x1) , (y1 - y2) );
            }
        }else{
            if( x1 > x2 ){
                //if( fill.isSelected() == true) g.fillRect(x2, y1,  (x1 - x2) , (y2 - y1));
                g.strokeRect(x2, y1,  (x1 - x2) , (y2 - y1) );
            }else{
                //if( fill.isSelected() == true) g.fillRect(x1, y1,  (x2 - x1) , (y2 - y1));
                g.strokeRect(x1, y1,  (x2 - x1) , (y2 - y1) );
            }
        }
    }
    
    public void onRelease(MouseEvent e, ToggleButton fill){
        x2 = (int)e.getX();
        y2 = (int)e.getY();
        
        writableImage2 = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage2);
        
        g.drawImage(writableImage, 0, 0);
        
        
        if( y1 > y2 && rect.isSelected() == true) {
            if( x1 > x2 ){
                g.clearRect(x2, y2,  (x1 - x2) , (y1 - y2));
                //g.drawImage(writableImage, x2, y2,  (x1 - x2) , (y1 - y2));
                g.strokeRect(x2, y2,  (x1 - x2) , (y1 - y2) );
            }else{
                g.clearRect(x1, y2,  (x2 - x1) , (y1 - y2));
                //g.drawImage(writableImage, x1, y2,  (x2 - x1) , (y1 - y2));
                g.strokeRect(x1, y2,  (x2 - x1) , (y1 - y2) );
            }
        }else{
            if( x1 > x2 ){
                 g.clearRect(x2, y1,  (x1 - x2) , (y2 - y1));
                //g.drawImage(writableImage, x2, y1,  (x1 - x2) , (y2 - y1));
                g.strokeRect(x2, y1,  (x1 - x2) , (y2 - y1) );
            }else{
                 g.clearRect(x1, y1,  (x2 - x1) , (y2 - y1));
                //g.drawImage(writableImage, x1, y1,  (x2 - x1) , (y2 - y1));
                g.strokeRect(x1, y1,  (x2 - x1) , (y2 - y1) );
            }
        }
        
        //g.drawImage(writableImage2, 10, 10,  (x1 - x2) , (y1 - y2));
        
        g.drawImage(writableImage, x1, y1, (x2 - x1), (y2 - y1), x1, y1, (x2 - x1), (y2 - y1) );
        
        x3 = x1;
        y3 = y1;
        x4 = x2;
        y4 = y2;
        
        moving = true;
        
    }
    
    public void Move(KeyEvent e){
        if(moving == true){
        if(e.getCode() == KeyCode.A){
            System.out.println("trollei");
            x3 -= 10;
            x4 -= 10;
        }
        if(e.getCode() == KeyCode.D){
            System.out.println("trollei");
            x3 += 10;
            x4 += 10;
        }
        if(e.getCode() == KeyCode.W){
            System.out.println("trollei");
            y3 -= 10;
            y4 -= 10;
        }
        if(e.getCode() == KeyCode.S){
            System.out.println("trollei");
            y3 += 10;
            y4 += 10;
        }
        if(e.getCode() == KeyCode.ENTER){
            moving = false;
        }
        
        g.drawImage(writableImage, 0, 0);
        
        if( y1 > y2 && rect.isSelected() == true) {
            if( x1 > x2 ){
                g.clearRect(x2, y2,  (x1 - x2) , (y1 - y2));
            }else{
                g.clearRect(x1, y2,  (x2 - x1) , (y1 - y2));
            }
        }else{
            if( x1 > x2 ){
                 g.clearRect(x2, y1,  (x1 - x2) , (y2 - y1));
            }else{
                 g.clearRect(x1, y1,  (x2 - x1) , (y2 - y1));
            }
        }
        }else{
            //do nothing
        }
        
        g.drawImage(writableImage, x1, y1, (x2 - x1), (y2 - y1), x3, y3, (x2 - x1), (y2 - y1) );
    }
    
    
}
