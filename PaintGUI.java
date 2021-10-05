/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventType;
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

//STAGE LIBS
import javafx.stage.Stage;

//SELF LIBS
import paint.jrobledo.Menu.ToolsMenu;
import paint.jrobledo.Menu.TopMenu;

/**
 *
 * @author acoff
 */
public class PaintGUI {
    
    SnapshotParameters sp = new SnapshotParameters();
    
    double imgX = 600;
    double imgY = 600;
    
    int curTab = 0;
    
    Image image;
    
    public PaintGUI(Stage appStage) throws FileNotFoundException{
        
        //Grid propertiess
        GridPane grid = new GridPane();
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
        
        ScrollPane imagePane = new ScrollPane();
        imagePane.setPrefSize(600, 600);
        imagePane.setContent(grid);
        
        //Menu setup (Bar, menu, items
        
        TopMenu menuBar = new TopMenu(canvas, g, sp, appStage, imagePane);
        ToolsMenu toolsMenu = new ToolsMenu(canvas, g, menuBar, grid, appStage);
        MenuBar menu = menuBar.GetMenu();
        
        //Align objects vertically
        VBox appBox = new VBox();
        appBox.getChildren().addAll(menu, toolsMenu.toolBox, imagePane, toolsMenu.zoom);
        
        //Application StackPane and scene
        StackPane root = new StackPane();
        root.getChildren().addAll(appBox);
        
        Scene scene = new Scene(root, 1280, 720);

        //Stage settings
        appStage.setTitle(menuBar.returnTitle());
        appStage.setScene(scene);
        
        //ImgBox resizing
        if (canvas.getWidth() > appStage.getWidth()) imagePane.setPrefWidth(appStage.getWidth() );
        if (canvas.getHeight() > appStage.getHeight()) imagePane.setPrefHeight( appStage.getHeight() - 100 );
            
        appStage.show();
        
        //Let's see if this works
        if ( ( canvas.getWidth() * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getWidth()) { 
                imagePane.setPrefWidth( appStage.getWidth() ); 
            } else { 
                imagePane.setPrefWidth(  imgX * toolsMenu.zoomSlider.getValue() ); 
            }
            
            if ( ( canvas.getHeight() * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getHeight()) { 
                imagePane.setPrefHeight( appStage.getHeight() - 100 ); 
            } else { 
                imagePane.setPrefHeight( imgY * toolsMenu.zoomSlider.getValue() ); 
            }   
        
        //Stage image resizing
        
        grid.setPrefWidth( appStage.getWidth() );
        grid.setPrefHeight( appStage.getHeight());
        
        toolsMenu.zoomSlider.valueProperty().addListener(e -> {
            
            toolsMenu.dimensions[0] = imgX;
            toolsMenu.dimensions[1] = imgY;
            
            imgX = menuBar.GetX();
            System.out.println("Canvas Pane Width: " + imgX);
            
            imgY = menuBar.GetY();
            System.out.println("Canvas Pane Height: " + imgY);

            if ( ( imgX * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getWidth()) { 
                imagePane.setPrefWidth( appStage.getWidth() ); 
            } else { 
                imagePane.setPrefWidth(  imgX * toolsMenu.zoomSlider.getValue() ); 
            }
            
            if ( ( imgY * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getHeight()) { 
                imagePane.setPrefHeight( appStage.getHeight() - 100 ); 
            } else { 
                imagePane.setPrefHeight( imgY * toolsMenu.zoomSlider.getValue() ); 
            }  
        });
        
        appStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            imgX = menuBar.GetX();
            System.out.println(imgX);
            
            toolsMenu.dimensions[0] = imgX;
            toolsMenu.dimensions[1] = imgY;
            
            //System.out.println("Canvas Pane Width: " + canvasPane.getWidth());
            
            grid.setPrefWidth( appStage.getWidth() );
            
            if ( ( imgX * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getWidth()) { 
                imagePane.setPrefWidth( appStage.getWidth() ); 
            } else { 
                imagePane.setPrefWidth(  imgX * toolsMenu.zoomSlider.getValue() ); 
            }
        });

        appStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            imgY = menuBar.GetY();
            System.out.println("Canvas Pane Height: " + imgY);
            
            toolsMenu.dimensions[0] = imgX;
            toolsMenu.dimensions[1] = imgY;
            
            
            grid.setPrefHeight( canvas.getHeight() );
            
            if ( ( imgY * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getHeight()) { 
                imagePane.setPrefHeight( appStage.getHeight() - 100 ); 
            } else { 
                imagePane.setPrefHeight( imgY * toolsMenu.zoomSlider.getValue() ); 
            }   
            
        });
        
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            imgX = menuBar.GetX();
            System.out.println(imgX);
            
            toolsMenu.dimensions[0] = imgX;
            toolsMenu.dimensions[1] = imgY;
            
            //System.out.println("Canvas Pane Width: " + canvasPane.getWidth());
            
            grid.setPrefWidth( appStage.getWidth() );
            
            if ( ( imgX * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getWidth()) { 
                imagePane.setPrefWidth( appStage.getWidth() ); 
            } else { 
                imagePane.setPrefWidth(  imgX * toolsMenu.zoomSlider.getValue() ); 
            }
        });

        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            imgY = menuBar.GetY();
            System.out.println("Canvas Pane Height: " + imgY);
            
            toolsMenu.dimensions[0] = imgX;
            toolsMenu.dimensions[1] = imgY;
            
            
            grid.setPrefHeight( canvas.getHeight() );
            
            if ( ( imgY * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getHeight()) { 
                imagePane.setPrefHeight( appStage.getHeight() - 100 ); 
            } else { 
                imagePane.setPrefHeight( imgY * toolsMenu.zoomSlider.getValue() ); 
            }       
        });
        
        /*
        
        imagePane.addEventHandler(EventType.ROOT, e -> {
            if ( ( canvasPane.getWidth() * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getWidth()) { 
                imagePane.setPrefWidth( canvas.getWidth() ); 
                grid.setPrefWidth( appStage.getWidth() );
            } else { 
                imagePane.setPrefWidth( canvas.getWidth() * ( toolsMenu.zoomSlider.getValue() / 100 ) ); 
                grid.setPrefWidth( canvas.getWidth() * ( toolsMenu.zoomSlider.getValue() / 100 ) );
            }
            
            if ( ( canvasPane.getHeight() * ( toolsMenu.zoomSlider.getValue() / 100 ) ) > imagePane.getHeight()) { 
                imagePane.setPrefHeight( canvas.getHeight()); 
                grid.setPrefHeight( appStage.getHeight());
            } else { 
                imagePane.setPrefHeight( canvas.getHeight() * ( toolsMenu.zoomSlider.getValue() / 100 ) ); 
                grid.setPrefHeight( canvas.getHeight() * ( toolsMenu.zoomSlider.getValue() / 100 ) );
            }    
        }); */
    }
}
