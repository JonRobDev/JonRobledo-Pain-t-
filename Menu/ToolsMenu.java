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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import paint.jrobledo.Tools.DrawingTools;

/**
 *
 * @author Jonathan Robledo
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
    
    int sidesCnt;
    
    /**
    *   ToolsMenu is a constructor which contains all of the UI elements for the toolbox along with executing the required methods from DrawingTools. 
    *   
    *   @param canvas    Sets the currently available canvas.
    *   @param g         Sets the main accessible GraphicsContext.
    *   @param menuTop   Declared to allow access to pass/receive values from the TopMenu.
    *   @param grid      Sets the GridPane that will be rescaled.
    *   @param stage     Sets the Stage that the toolbox needs to access.
    * 
    *   @author Jonathan R.
    */
    
    public ToolsMenu(Canvas canvas, GraphicsContext g, TopMenu menuTop, GridPane grid, Stage stage) throws FileNotFoundException{
        
        //Image stacks for undo/redo
        Stack<Image> undo = new Stack<Image>();
        Stack<Image> redo = new Stack<Image>();
        
        // Icon loading
        
        
        InputStream penICN = new FileInputStream("src/paint/jrobledo/Icons/pencil.png");
        InputStream lineICN = new FileInputStream("src/paint/jrobledo/Icons/line.png");
        InputStream rectICN = new FileInputStream("src/paint/jrobledo/Icons/rect.png");
        InputStream ovalICN = new FileInputStream("src/paint/jrobledo/Icons/oval.png");
        InputStream pickerICN = new FileInputStream("src/paint/jrobledo/Icons/dropper.png");
        InputStream selectedICN = new FileInputStream("src/paint/jrobledo/Icons/selected.png");
        InputStream textICN = new FileInputStream("src/paint/jrobledo/Icons/text.png");
        InputStream eraserICN = new FileInputStream("src/paint/jrobledo/Icons/eraser.png");
        InputStream arcICN = new FileInputStream("src/paint/jrobledo/Icons/arc.png");
        InputStream selectICN = new FileInputStream("src/paint/jrobledo/Icons/select.png");
        InputStream polyICN = new FileInputStream("src/paint/jrobledo/Icons/poly.png");
        InputStream patternICN = new FileInputStream("src/paint/jrobledo/Icons/patternbrush.png");

        
        Image selected = new Image(selectedICN);
        Image notselected = new Image(rectICN);
        
        Tooltip penTip = new Tooltip("Pencil");
        Tooltip lineTip = new Tooltip("Line");
        Tooltip rectTip = new Tooltip("Rectangle");
        Tooltip ovalTip = new Tooltip("Ellipse");
        Tooltip selectTip = new Tooltip("Select and Move");
        Tooltip textTip = new Tooltip("Text");
        Tooltip eraserTip = new Tooltip("Eraser");
        Tooltip arcTip = new Tooltip("Arc/Semicircle");
        Tooltip colorTip = new Tooltip("Color Dropper");
        Tooltip polyTip = new Tooltip("Polygon");
        Tooltip pBTip = new Tooltip("Pattern brush");
        
        //Tool buttons
        //-- PENCIL --
        ToggleButton pencil = new ToggleButton();
        pencil.setGraphic(new ImageView( new Image ( penICN ) ) );
        Tooltip.install(pencil, penTip);
        
        //-- ERASER --
        ToggleButton eraser = new ToggleButton();
        eraser.setGraphic(new ImageView( new Image ( eraserICN ) ) );
        Tooltip.install(eraser, eraserTip);
        
        //-- LINE TOOL --
        ToggleButton line = new ToggleButton();
        line.setGraphic(new ImageView( new Image ( lineICN ) ) );
        Tooltip.install(line, lineTip);
        
        //-- RECT --
        ToggleButton rect = new ToggleButton();
        rect.setGraphic(new ImageView( notselected ) );
        Tooltip.install(rect, rectTip);
        
        //-- OVAL --
        ToggleButton oval = new ToggleButton();
        oval.setGraphic(new ImageView( new Image ( ovalICN ) ) );
        Tooltip.install(oval, ovalTip);
        
        //-- --
        ToggleButton arc = new ToggleButton();
        arc.setGraphic(new ImageView( new Image ( arcICN ) ) );
        Tooltip.install(arc, arcTip);
        
        //-- POLYGON --
        ToggleButton poly = new ToggleButton();
        poly.setGraphic(new ImageView( new Image ( polyICN ) ) );
        Tooltip.install(poly, polyTip);
        
        //-- SELECT TOOL --
        ToggleButton select = new ToggleButton();
        select.setGraphic(new ImageView( new Image ( selectICN ) ) );
        Tooltip.install(select, selectTip);
        
        //-- TEXT TOOL --
        ToggleButton textBtn = new ToggleButton();
        textBtn.setGraphic(new ImageView( new Image ( textICN ) ) );
        Tooltip.install(textBtn, textTip);
        
        //-- PATTERN BRUSH --
        ToggleButton patternBrush = new ToggleButton();
        patternBrush.setGraphic(new ImageView( new Image ( patternICN ) ) );
        Tooltip.install(patternBrush, pBTip);
        
        //-- COLOR DORRPER --
        ToggleButton dropper = new ToggleButton();
        dropper.setGraphic(new ImageView( new Image ( pickerICN ) ) );
        Tooltip.install(dropper, colorTip);
         
        
        ToggleGroup toolGroup = new ToggleGroup();
        
        ToggleButton[] toggleArray = {pencil, eraser, line, rect, oval, arc, poly, select, textBtn, patternBrush};
        
        
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
        
        sidesCnt = Integer.parseInt( sides.getText() );
        
        //Zoom slider setup
        zoomSlider = new Slider(10, 400, 100);
        zoomSlider.setBlockIncrement(10f);
        
        zoom = new HBox();
        Label zoomTxt = new Label("Zoom: ");
        Label sidesTxt = new Label("  Polygon Sides: ");
        zoom.getChildren().addAll(zoomTxt, zoomSlider, sidesTxt, sides);
        
        //Grouping
        line.setToggleGroup(toolGroup);
        pencil.setToggleGroup(toolGroup);
        rect.setToggleGroup(toolGroup);
        oval.setToggleGroup(toolGroup);
        textBtn.setToggleGroup(toolGroup);
        dropper.setToggleGroup(toolGroup);
        patternBrush.setToggleGroup(toolGroup);
        eraser.setToggleGroup(toolGroup);
        arc.setToggleGroup(toolGroup);
        select.setToggleGroup(toolGroup);
        poly.setToggleGroup(toolGroup);
        
        //NEW TOOLS
        DrawingTools drawingTools = new DrawingTools(canvas, g, toggleArray, fill, lineWidth, sidesCnt);
        
        toolBox = new HBox();
        Label widthTxt = new Label("  Stroke Width: ");
        Label colorTxt = new Label("  Stroke Color: ");
        Label fillTxt = new Label("  Fill Color: ");
        toolBox.getChildren().addAll(pencil, eraser, line, rect, oval, arc, poly, patternBrush, textBtn, dropper, select, colorTxt, strokePicker, fillTxt, fillPicker, widthTxt, widthSlider, fill);
        
        //UI Event stuff
        zoomSlider.valueProperty().addListener(e -> {
            grid.setScaleX(zoomSlider.getValue() / 100);
            grid.setScaleY(zoomSlider.getValue() / 100);
            
            menuTop.zoomVal = zoomSlider.getValue();
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
            
            drawingTools.GetLineWidth(lineWidth);
            
            drawingTools.onClick(e);
            
            wi = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
            canvas.snapshot(null, wi);
            
            undo.push(wi);
            
            sidesCnt = Integer.parseInt( sides.getText() );
            drawingTools.SetSides(sidesCnt);
            
        });
        
        canvas.setOnMouseDragged( e -> {
            
            drawingTools.GetLineWidth(lineWidth);
            
            sidesCnt = Integer.parseInt( sides.getText() );
            drawingTools.OnDrag(e);

        });
        
        canvas.setOnMouseReleased( e -> {
            
            drawingTools.GetLineWidth(lineWidth);
            
            sidesCnt = Integer.parseInt( sides.getText() );
            drawingTools.OnRelease(e);
            
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
            
            if( select.isSelected() == true) {
                drawingTools.Move(key);
                drawingTools.CopyPaste(key);
            }
            if( textBtn.isSelected() == true) drawingTools.DrawText(key);
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
