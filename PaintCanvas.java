/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 *
 * @author acoff
 */
public class PaintCanvas {
    public PaintCanvas(){
        //Grid propertiess
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10,10,10,10));
        grid.setGridLinesVisible(false);
        
        //canvas initialization
        
        Canvas canvas = new Canvas(600,600);  // The canvas on which everything is drawn.
        GraphicsContext g = canvas.getGraphicsContext2D();  // For drawing on the canvas.
        g.setLineCap(StrokeLineCap.ROUND);
        g.setLineJoin(StrokeLineJoin.ROUND);
        Pane canvasPane = new Pane();
        
        //Set the ImageView
        
        ImageView imgBox = new ImageView();
        imgBox.setPreserveRatio(true);
        
        canvasPane.getChildren().addAll(imgBox, canvas);
        
        grid.add(canvasPane, 1, 1);
        
        //ScrollPane setup
        
        ScrollPane imagePane = new ScrollPane();
        imagePane.setPrefSize(600, 600);
        imagePane.setContent(grid);
    }
}
