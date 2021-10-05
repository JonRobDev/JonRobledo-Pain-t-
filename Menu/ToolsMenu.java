/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import paint.jrobledo.Tools.Arc;


import paint.jrobledo.Tools.DrawLine;
import paint.jrobledo.Tools.Pencil;
import paint.jrobledo.Tools.Rect;
import paint.jrobledo.Tools.Ellipse;
import paint.jrobledo.Tools.Eraser;
import paint.jrobledo.Tools.NPoly;
import paint.jrobledo.Tools.Select;
import paint.jrobledo.Tools.TextDraw;

/**
 *
 * @author acoff
 */
public class ToolsMenu {
    
    Slider widthSlider = new Slider(0, 50, 5);
    public Slider zoomSlider;
            
    double lineWidth = 5;
    public HBox toolBox;
    public HBox zoom;
    
    Image img;
    
    public double[] dimensions = {600.0, 600.0};
    
    WritableImage wi;
    
    public ToolsMenu(Canvas canvas, GraphicsContext g, TopMenu menuTop, GridPane grid, Stage stage) throws FileNotFoundException{
        
        //Image stacks for undo/redo
        Stack<Image> undo = new Stack<Image>();
        Stack<Image> redo = new Stack<Image>();
        
        
        
        // Ucon loading
        InputStream penICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/pencil.png");
        InputStream lineICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/line.png");
        InputStream rectICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/rect.png");
        InputStream ovalICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/oval.png");
        InputStream pickerICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/dropper.png");
        InputStream selectedICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/selected.png");
        InputStream textICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/text.png");
        InputStream eraserICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/eraser.png");
        InputStream arcICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/arc.png");
        InputStream selectICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/select.png");
        InputStream polyICN = new FileInputStream("D:/Java Projects/Pain(t)/src/paint/jrobledo/Icons/poly.png");

        
        Image selected = new Image(selectedICN);
        Image notselected = new Image(rectICN);
        
        //Tool buttons
        ToggleButton pencil = new ToggleButton();
        pencil.setGraphic(new ImageView( new Image ( penICN ) ) );
        
        ToggleButton eraser = new ToggleButton();
        eraser.setGraphic(new ImageView( new Image ( eraserICN ) ) );
        
        ToggleButton line = new ToggleButton();
        line.setGraphic(new ImageView( new Image ( lineICN ) ) );
        
        ToggleButton rect = new ToggleButton();
        rect.setGraphic(new ImageView( notselected ) );
        
        ToggleButton oval = new ToggleButton();
        oval.setGraphic(new ImageView( new Image ( ovalICN ) ) );
        
        ToggleButton arc = new ToggleButton();
        arc.setGraphic(new ImageView( new Image ( arcICN ) ) );
        
        ToggleButton poly = new ToggleButton();
        poly.setGraphic(new ImageView( new Image ( polyICN ) ) );
        
        ToggleButton select = new ToggleButton();
        select.setGraphic(new ImageView( new Image ( selectICN ) ) );
        
        ToggleButton textBtn = new ToggleButton();
        textBtn.setGraphic(new ImageView( new Image ( textICN ) ) );
        
        ToggleButton dropper = new ToggleButton();
        dropper.setGraphic(new ImageView( new Image ( pickerICN ) ) );
         
        ToggleGroup toolGroup = new ToggleGroup();
        
        
        //Fill toggle
        ToggleButton fill = new ToggleButton("Fill?");
        fill.setGraphic(new ImageView( notselected ) );
        
        //Color picker
        ColorPicker strokePicker = new ColorPicker(Color.BLACK);
        ColorPicker fillPicker = new ColorPicker(Color.BLACK);

        
        //Slider settinfs
        widthSlider.setBlockIncrement(1f);
        widthSlider.setShowTickMarks(true);
        widthSlider.setShowTickLabels(true);
        widthSlider.setMajorTickUnit(10f);
        
        //Polygon settings
        TextField sides = new TextField("8");
        TextField radius = new TextField("8");
        
        int sidesCnt = Integer.parseInt( sides.getText() );
        int radiusLen = Integer.parseInt( radius.getText() );
        
        //Zoom slider setup
        zoomSlider = new Slider(10, 400, 100);
        zoomSlider.setBlockIncrement(10f);
        
        zoom = new HBox();
        Label zoomTxt = new Label("Zoom: ");
        zoom.getChildren().addAll(zoomTxt, zoomSlider, sides, radius);
        
        //Grouping
        line.setToggleGroup(toolGroup);
        pencil.setToggleGroup(toolGroup);
        rect.setToggleGroup(toolGroup);
        oval.setToggleGroup(toolGroup);
        textBtn.setToggleGroup(toolGroup);
        dropper.setToggleGroup(toolGroup);
        eraser.setToggleGroup(toolGroup);
        arc.setToggleGroup(toolGroup);
        select.setToggleGroup(toolGroup);
        poly.setToggleGroup(toolGroup);
        
        DrawLine lineTool = new DrawLine(canvas, g, line, lineWidth);
        Pencil pencilTool = new Pencil(canvas, g, pencil, lineWidth); 
        Eraser eraserTool = new Eraser(canvas, g, eraser, lineWidth); 
        Rect rectTool = new Rect(canvas, g, rect, lineWidth);
        Ellipse ovalTool = new Ellipse(canvas, g, oval, lineWidth);
        Arc arcTool = new Arc(canvas, g, arc, lineWidth);
        Select selectTool = new Select(canvas, g, select, lineWidth);
        TextDraw textTool = new TextDraw (canvas, g, textBtn, lineWidth);
        NPoly nPoly = new NPoly(canvas, g, textBtn, lineWidth, sidesCnt, radiusLen);
        
        toolBox = new HBox();
        Label widthTxt = new Label("  Stroke Width: ");
        Label colorTxt = new Label("  Stroke Color: ");
        Label fillTxt = new Label("  Fill Color: ");
        toolBox.getChildren().addAll(pencil, eraser, line, rect, oval, arc, poly, textBtn, dropper, select, colorTxt, strokePicker, fillTxt, fillPicker, widthTxt, widthSlider, fill);
        
        //UI Event stuff
        zoomSlider.valueProperty().addListener(e -> {
            grid.setScaleX(zoomSlider.getValue() / 100);
            grid.setScaleY(zoomSlider.getValue() / 100);
        });
        
        fill.setOnAction(e -> {
            if(fill.isSelected() == true){
                fill.setGraphic(new ImageView( selected ) );
            }else{
                fill.setGraphic(new ImageView( notselected ) );
            }
        });
        
        canvas.setOnMousePressed( e -> {
            
            System.out.println(widthSlider.getValue());
            System.out.println(zoomSlider.getValue());
            
            lineWidth = widthSlider.getValue();
            
            if( textBtn.isSelected() == true) textTool.onClick(e, lineWidth);
            lineTool.onClick(e, lineWidth);
            eraserTool.OnClick(e, lineWidth);
            pencilTool.OnClick(e, lineWidth);
            ovalTool.onClick(e, lineWidth);
            rectTool.onClick(e, lineWidth);
            arcTool.onClick(e, lineWidth);
            selectTool.onClick(e, lineWidth);
            nPoly.onClick(e, lineWidth);
            
            wi = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
            canvas.snapshot(null, wi);
            
            undo.push(wi);
            
        });
        canvas.setOnMouseDragged( e -> {
            
            if( line.isSelected() == true) lineTool.onDrag(e);
            if( eraser.isSelected() == true) eraserTool.OnDrag(e);
            pencilTool.OnDrag(e);
            if( oval.isSelected() == true) ovalTool.onDrag(e, fill);
            if( rect.isSelected() == true) rectTool.onDrag(e, fill);
            if( arc.isSelected() == true) arcTool.onDrag(e, fill);
            if( select.isSelected() == true) selectTool.onDrag(e, fill);
        });
        canvas.setOnMouseReleased( e -> {
           
            if( line.isSelected() == true) lineTool.onRelease(e);
            if( eraser.isSelected() == true) eraserTool.OnRelease(e);
            pencilTool.OnRelease(e);
            if( oval.isSelected() == true) ovalTool.onRelease(e, fill);
            if( rect.isSelected() == true) rectTool.onRelease(e, fill);
            if( arc.isSelected() == true) arcTool.onRelease(e, fill);
            if( select.isSelected() == true) selectTool.onRelease(e, fill);
            if( poly.isSelected() == true) nPoly.OnRelease(e, sidesCnt, radiusLen);
            
            if(dropper.isSelected() == true){
                img = menuTop.returnImg();
                
                int x = (int)e.getX();
                int y = (int)e.getY();
             
                Color color2;
                
                WritableImage snap = g.getCanvas().snapshot(null, null);
                color2 = snap.getPixelReader().getColor(x, y);
               
                g.setStroke(color2);
                strokePicker.setValue(color2);
            }
            
        });
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.isControlDown()){
                if(key.getCode() == KeyCode.Z){
                    Image imageUndo = undo.pop();
                    g.drawImage(imageUndo, 0, 0);
                    redo.push(imageUndo);
                }else if(key.getCode() == KeyCode.Y){
                    Image imageRedo = redo.pop();
                    g.drawImage(imageRedo, 0, 0);
                    undo.push(imageRedo);
                }
            }
            
            if( select.isSelected() == true) selectTool.Move(key);
            if( textBtn.isSelected() == true) textTool.onRelease(key, lineWidth);
        });
        
        strokePicker.setOnAction(e -> {
            Color color = strokePicker.getValue();
            g.setStroke(color);
        });
        
        fillPicker.setOnAction(e -> {
            Color color = fillPicker.getValue();
            g.setFill(color);
        });
        
    }
}
