/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Tools;

import java.awt.MouseInfo;
import java.util.Optional;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

/**
 *
 * @author acoff
 */
public class DrawingTools{
    int x1;
    int y1;
    
    int x2;
    int y2;
    
    int x3;
    int y3;
    
    int x4;
    int y4;
    
    int mouseCurPosX;
    int mouseCurPosY;
    
    int savedX;
    int savedY;
    int savedHeight;
    int savedWidth;
    WritableImage savedImage;
    
    private Canvas canvas;
    private GraphicsContext g;
    private ToggleButton[] toggleButtons;
    private ToggleButton fill;
    private double lineWidth;
    
    ToggleButton textBtn;
    TextInputDialog td;
    int size;
    String textStr;
    Optional<String> result;
    
    int sides;
    int radius;
    double[] xPoly;
    double[] yPoly;
    
    WritableImage writableImage2;
    WritableImage copyImg;
    Boolean moving;
    
    Boolean isHeld = false;
    
    WritableImage writableImage;
    
    SnapshotParameters sp;
    
    /*
    okay so this is the order of the toggle buttons buck
    0 = pencil
    1 = eraser
    2 = line
    3 = rect
    4 = oval
    5 = arc
    6 = poly
    7 = select
    8 = text
    9 = pattern brush
    */
    
    /**
    *   DrawingTools is a class containing all of the methods for drawing or editing on the canvas.
    * 
    *   @param canvas           Sets the current canvas
    *   @param g                Sets the current GraphicsContext
    *   @param toggleButtons    Sets all of the ToggleButtons assigned for each tool.  
    *   @param fill             Sets the ToggleButton used for toggling filling shapes.
    *   @param lineWidth        Sets the current line width.
    *   @param sides            Sets the integer used for setting the amount of sides for the DrawPolygon method.
    *   
    *   @author Jonathan R.
    */
    
    public DrawingTools(Canvas canvas, GraphicsContext g, ToggleButton[] toggleButtons, ToggleButton fill, double lineWidth, int sides){
        this.canvas = canvas;
        this.g = g;
        this.toggleButtons = toggleButtons;
        this.fill = fill;
        this.lineWidth = lineWidth;
        this.sides = sides;
    }
    
    /**
    * <p> Registers the mouse position in X and Y upon clicking and sets an image for live draw functionality. </p>
    * @since 0.6.0
    * @param e          The mouse event of the current canvas.
    */
    
    public void onClick(MouseEvent e){
        
        if (isHeld == true) return;
        isHeld = true;
        
        x1 = (int)e.getX();
        y1 = (int)e.getY();
        
        if( toggleButtons[8].isSelected() == true ){
            td = new TextInputDialog("Input your text here: ");
        
            td.setTitle("Text");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        
            td.setContentText("Text");
        
            result = td.showAndWait();
        
            textStr = result.get();
        }
        
        writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);
        
        g.setLineWidth(lineWidth);
    }
    
    /**
    * <p> Grabs the current position of the mouse and live draws shapes based on the dimensions of x1, x2, y1, and y2. </p>
    * @since 0.6.0
    * @param e          The mouse event of the current canvas.
    */
    
    public void OnDrag(MouseEvent e){
        if (isHeld == false) return;
            
        if (toggleButtons[0].isSelected() == true || toggleButtons[9].isSelected() == true) {
            x1 = (int)e.getX();
            y1 = (int)e.getY();
            if( toggleButtons[0].isSelected() == true )g.strokeLine(x2, y2, x1, y1);
            if( toggleButtons[9].isSelected() == true )g.drawImage(savedImage, savedX, savedY, savedWidth, savedHeight, x1, y1, savedWidth, savedHeight );
        }else if (toggleButtons[1].isSelected() == true){
            g.clearRect(x2 - (lineWidth/2), y2 - (lineWidth/2), lineWidth, lineWidth);
        }else{
            
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            
            g.drawImage(writableImage, 0, 0);
            if( toggleButtons[2].isSelected() == true ) g.strokeLine(x2, y2, x1, y1);
            //this covers 3 4 and 5
            DrawAndDrag();
            if( toggleButtons[6].isSelected() == true ) DrawPolygon();
            if( toggleButtons[7].isSelected() == true ) Select(e);
        }
            
        x2 = x1;
        y2 = y1;
    }
    
    /**
    * <p> Draws the final shapes based on the dimensions of x1, x2, y1, and y2. </p>
    * @since 0.6.0
    * @param e          The mouse event of the current canvas.
    */
    
    public void OnRelease(MouseEvent e){
        
        x2 = (int)e.getX();
        y2 = (int)e.getY();
        
        isHeld = false;
        
        if( toggleButtons[2].isSelected() == true ) g.strokeLine(x2, y2, x1, y1);
        //this covers 3 4 and 5
        DrawAndDrag();
        if( toggleButtons[6].isSelected() == true ) DrawPolygon();
        if( toggleButtons[7].isSelected() == true ) SelectPiece();
    }
    
    /**
    * <p> Erases part of the canvas based on mouse position. </p>
    * @since 0.6.0
    * @param e          The mouse event of the current canvas.
    */
    
    public void Erase(MouseEvent e){
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
    * <p> Responsible for drawing rectangles, ellipses, and arcs based on the position of the mouse. </p>
    * @since 0.6.0
    */
    
    public void DrawAndDrag(){
        if( y1 > y2) {
            if( x1 > x2 ){
                //RECT
                if( toggleButtons[3].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillRect(x2, y2,  (x1 - x2) , (y1 - y2));
                    g.strokeRect(x2, y2,  (x1 - x2) , (y1 - y2) );
                }
                //OVAL
                if( toggleButtons[4].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillOval(x2, y2,  (x1 - x2) , (y1 - y2));
                    g.strokeOval(x2, y2,  (x1 - x2) , (y1 - y2) );
                }
                //ARC
                if( toggleButtons[5].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillArc(x2, y2,  (x1 - x2) , (y1 - y2), 0, 180, ArcType.ROUND );
                    g.strokeArc(x2, y2,  (x1 - x2) , (y1 - y2), 0, 180, ArcType.OPEN );
                }
                
            }else{
                //RECT
                if( toggleButtons[3].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillRect(x1, y2,  (x2 - x1) , (y1 - y2));
                    g.strokeRect(x1, y2,  (x2 - x1) , (y1 - y2) );
                }
                //OVAL
                if( toggleButtons[4].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillOval(x1, y2,  (x2 - x1) , (y1 - y2));
                    g.strokeOval(x1, y2,  (x2 - x1) , (y1 - y2) );
                }
                //ARC
                if( toggleButtons[5].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillArc(x1, y2,  (x2 - x1) , (y1 - y2), 0, 180, ArcType.ROUND);
                    g.strokeArc(x1, y2,  (x2 - x1) , (y1 - y2), 0, 180, ArcType.OPEN );
                }
                
            }
        }else{
            if( x1 > x2 ){
                //RECT
                if( toggleButtons[3].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillRect(x2, y1,  (x1 - x2) , (y2 - y1));
                    g.strokeRect(x2, y1,  (x1 - x2) , (y2 - y1) );
                }
                //OVAL
                if( toggleButtons[4].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillOval(x2, y1,  (x1 - x2) , (y2 - y1));
                    g.strokeOval(x2, y1,  (x1 - x2) , (y2 - y1) );
                }
                //ARC
                if( toggleButtons[5].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillArc(x2, y1,  (x1 - x2) , (y2 - y1), 0, 180, ArcType.ROUND);
                    g.strokeArc(x2, y1,  (x1 - x2) , (y2 - y1), 0, 180, ArcType.OPEN );
                }
                
            }else{
                //RECT
                if( toggleButtons[3].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillRect(x1, y1,  (x2 - x1) , (y2 - y1));
                    g.strokeRect(x1, y1,  (x2 - x1) , (y2 - y1) );
                }
                //OVAL
                if( toggleButtons[4].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillOval(x1, y1,  (x2 - x1) , (y2 - y1));
                    g.strokeOval(x1, y1,  (x2 - x1) , (y2 - y1) );
                }
                //ARC
                if( toggleButtons[5].isSelected() == true) {
                    if( fill.isSelected() == true) g.fillArc(x1, y1,  (x2 - x1) , (y2 - y1), 0, 180, ArcType.ROUND);
                    g.strokeArc(x1, y1,  (x2 - x1) , (y2 - y1) , 0, 180, ArcType.OPEN );
                }
                
            }
        }
    }
    
    /**
    * <p> Draws a polygon based on  the amount of sides declared and the position of the mouse. </p>
    * @since 0.6.0
    */
    
    public void DrawPolygon(){
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
    
    public void DrawText(KeyEvent e){
        g.setFont(new Font("Arial", lineWidth));
        if(e.getCode() == KeyCode.ENTER){
            System.out.println(result);
            g.fillText(textStr, x1, y1);
        }
    }
    
    /**
    * <p> Draws a rectangle to show the area which the user wants to select. </p>
    * @since 0.6.0
    * @param e          The mouse event of the current canvas.
    */
    
    public void Select(MouseEvent e){
        x2 = (int)e.getX();
        y2 = (int)e.getY();
        
        g.drawImage(writableImage, 0, 0);
           
        if( y1 > y2 && toggleButtons[7].isSelected() == true) {
            if( x1 > x2 ){
                g.strokeRect(x2, y2,  (x1 - x2) , (y1 - y2) );
            }else{
                g.strokeRect(x1, y2,  (x2 - x1) , (y1 - y2) );
            }
        }else{
            if( x1 > x2 ){
                g.strokeRect(x2, y1,  (x1 - x2) , (y2 - y1) );
            }else{
                g.strokeRect(x1, y1,  (x2 - x1) , (y2 - y1) );
            }
        }
    }
    
    /**
    * <p> Selects the piece of the image set by the user. </p>
    * @since 0.6.0
    */
    
    public void SelectPiece(){
        writableImage2 = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        savedImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage2);
        canvas.snapshot(null, savedImage);
        
        g.drawImage(writableImage, 0, 0);
        
        
        if( y1 > y2 && toggleButtons[7].isSelected() == true) {
            if( x1 > x2 ){
                g.clearRect(x2, y2,  (x1 - x2) , (y1 - y2));
                g.strokeRect(x2, y2,  (x1 - x2) , (y1 - y2) );
            }else{
                g.clearRect(x1, y2,  (x2 - x1) , (y1 - y2));
                g.strokeRect(x1, y2,  (x2 - x1) , (y1 - y2) );
            }
        }else{
            if( x1 > x2 ){
                 g.clearRect(x2, y1,  (x1 - x2) , (y2 - y1));
                g.strokeRect(x2, y1,  (x1 - x2) , (y2 - y1) );
            }else{
                 g.clearRect(x1, y1,  (x2 - x1) , (y2 - y1));
                g.strokeRect(x1, y1,  (x2 - x1) , (y2 - y1) );
            }
        }

        g.drawImage(writableImage, x1, y1, (x2 - x1), (y2 - y1), x1, y1, (x2 - x1), (y2 - y1) );
        
        savedX = x1;
        savedY = y1;
        savedWidth = (x2 - x1);
        savedHeight = (y2 - y1);
        
        x3 = x1;
        y3 = y1;
        x4 = x2;
        y4 = y2;
        
        moving = true;
    }
    
    public void PatternBrush(){
        
    }
    
    /**
    * <p> Moves the current selection using WASD to move and Enter/Return to set position. </p>
    * @since 0.6.0
    * @param e          The key event handler of the current canvas.
    */
    
    //MOVES THE SELECTION WITH ARROW KEYS
    public void Move(KeyEvent e){     
        if(moving == true){
        if(e.getCode() == KeyCode.A){
            x3 -= 10;
            x4 -= 10;
        }
        if(e.getCode() == KeyCode.D){
            x3 += 10;
            x4 += 10;
        }
        if(e.getCode() == KeyCode.W){
            y3 -= 10;
            y4 -= 10;
        }
        if(e.getCode() == KeyCode.S){
            y3 += 10;
            y4 += 10;
        }
        if(e.getCode() == KeyCode.ENTER){
            moving = false;
        }
        
        g.drawImage(writableImage, 0, 0);
        
        if( y1 > y2 && toggleButtons[7].isSelected() == true) {
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
    
    /**
    * <p> Handles copying the current selection to a WritableImage and pasting it onto the canvas based on mouse position. </p>
    * @since 0.6.0
    * @param key          The key event handler of the current canvas.
    */
    public void CopyPaste(KeyEvent key){   
        if(key.isControlDown()){
            if(key.getCode() == KeyCode.C){
                copyImg = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                canvas.snapshot(null, copyImg);
            }
            if(key.getCode() == KeyCode.V){
                if(copyImg != null){
                    GetMousePos(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                    g.drawImage(writableImage, x1, y1, (x2 - x1), (y2 - y1), mouseCurPosX - 200 , mouseCurPosY - 200, (x2 - x1), (y2 - y1) );
                    canvas.snapshot(null, writableImage);
                }
            }
        }
    }
    
    /**
    * <p> Sets the current position of the mouse based on the parameters, updating them when called. </p>
    * @since 0.6.0
    * @param x          Mouse position x-coordinate.
    * @param y          Mouse position y-coordinate.
    */
    public void GetMousePos(int x, int y){
        mouseCurPosX = x;
        mouseCurPosY = y;
    }
    
    
    /**
    * <p> Sets the current line width the current GraphicsContext will be using based on the parameter. </p>
    * @since 0.6.0
    * @param curLineWide    Parameter for setting line width.
    */
    
    public void GetLineWidth(double curLineWide){
        lineWidth = curLineWide;
    }
}
