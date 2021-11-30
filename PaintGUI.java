/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

//SCENE CONTROL
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

//IMAGE AND VIEWPORT STUFF

import javafx.scene.layout.GridPane;

//LAYOUT
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

//STAGE LIBS
import javafx.stage.Stage;

//SELF LIBS
import paint.jrobledo.Menu.ToolsMenu;
import paint.jrobledo.Menu.TopMenu;

/**
 *
 * @author Jonathan Robledo
 */
public class PaintGUI {
    
    SnapshotParameters sp = new SnapshotParameters();
    
    double imgX = 600;
    double imgY = 600;
    
    double zoomVal;
    
    int curTab = 0;
    
    Image image;
    
    //Pane declarations
    GridPane grid = new GridPane();
    ScrollPane imagePane = new ScrollPane();
    
    /**
    *   PaintGUI is the constructor that sets up the canvas and canvas alignment, as well as displaying the menu, scene, etc. onto the current scene.
    *   
    *   @param appStage     Sets the Stage that the application wants to access.
    * 
    *   @author Jonathan R.
    */
    
    public PaintGUI(Stage appStage) throws FileNotFoundException{
        
        //Grid propertiess
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10,10,10,10));
        grid.setGridLinesVisible(false);
        
        //canvas initialization
        
        Canvas canvas = new Canvas(600,600);  // The canvas on which everything is drawn.
        List<Canvas> cnavasList = new ArrayList<Canvas>();
        List<GraphicsContext> gList = new ArrayList<GraphicsContext>();
        
        GraphicsContext g = canvas.getGraphicsContext2D();  // For drawing on the canvas.
        g.setLineCap(StrokeLineCap.ROUND);
        g.setLineJoin(StrokeLineJoin.ROUND);
        
        gList.add(g);
        
        grid.add(canvas, 1, 1);
        
        //ScrollPane setup
        imagePane.setContent(grid);
        
        StackPane root = new StackPane();
        
        //Menu setup (Bar, menu, items)
        
        TopMenu menuBar = new TopMenu(canvas, g, sp, appStage, grid, imagePane, root);
        ToolsMenu toolsMenu = new ToolsMenu(canvas, g, menuBar, grid, appStage);
        MenuBar menu = menuBar.GetMenu();
        
        //Align objects vertically
        VBox appBox = new VBox();
        appBox.getChildren().addAll(menu, toolsMenu.toolBox, imagePane, toolsMenu.zoom);
        
        //Application StackPane and scene
        root.getChildren().addAll(appBox);
        
        Scene scene = new Scene(root, 1280, 720);

        //Stage settings
        appStage.setTitle(menuBar.returnTitle());
        appStage.setScene(scene);
            
        appStage.show();
        
        toolsMenu.zoomSlider.valueProperty().addListener(e -> {          
            grid.setPrefWidth(  menuBar.imgX * (menuBar.zoomVal / 100) ); 
            grid.setPrefHeight(  menuBar.imgY * (menuBar.zoomVal / 100) ); 
            
            imagePane.setContent(grid);
            
            imagePane.setPrefSize(scene.getWidth() - 100, scene.getHeight() - 100);
        }); 
    }  
}
