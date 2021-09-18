/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
/** 
-- UNUSED BUT POTENTIALLY USEFUL LIBRARIES GO HERE --

import javafx.event.ActionEvent;
import javafx.event.EventHandler; 

**/

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

//SCENE CONTROL
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

//IMAGE AND VIEWPORT STUFF
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

//LAYOUT
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

//STAGE LIBS
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author acoff
 */
public class PaintGUI {
    
    int x1;
    int y1;
    int x2;
    int y2;
    
    double imgX;
    double imgY;
    
    double lineWidth = 5;
    
    String imgLocation = "";
    
    SnapshotParameters sp = new SnapshotParameters();
    
    public PaintGUI(Stage appStage){
        // This isn't really important, I just thought it'd be funny to add
        
        String[] titleJunk = {"World's first vomit-inducing paint program!", "Now with 50% more product!", "Asbestos-free!", "Comes in beige!", "It can do stuff!", "Works miracles!", "Not Minecraft!", "The Movie!", "Will not be held liable!", "I had to fill a slot!", "USDA approved!", "Not allowed in North Korea!"};
        int titleJunkNum = (int)Math.floor( titleJunk.length * Math.random() );
        
        //canvas initialization
        
        Canvas canvas = new Canvas(600,600);  // The canvas on which everything is drawn.
        GraphicsContext g = canvas.getGraphicsContext2D();  // For drawing on the canvas.
        Pane canvasPane = new Pane();

        //Menu setup (Bar, menu, items
        
        MenuBar menu = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        
        Menu helpMenu = new Menu("Help");
        
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem quit = new MenuItem("Exit");
        
        MenuItem help = new MenuItem("Help");
        MenuItem about = new MenuItem("About");

        
        fileMenu.getItems().addAll(open, save, saveAs, quit);
        
        helpMenu.getItems().addAll(help, about);

        menu.getMenus().addAll(fileMenu, helpMenu);
        
        //Dialog help
        
        Dialog<String> helpDialog = new Dialog<String>();
        helpDialog.setTitle("Help");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        
        helpDialog.setContentText("Help");
     
        helpDialog.getDialogPane().getButtonTypes().add(type);
        
        helpDialog.setContentText("So what does this stuff do? \n \n "
                + "File Menu - \n Open: Opens an image from the file explorer \n Save: Saves current image \n Save As: Saves current image in new directory \n "
                + "Tools - \n Line: Enables the ability to draw a straight line \n Color Picker: Chooses stroke color \n Line Width: Sets line width of tools");
        
        //Dialog about
        
        Dialog<String> aboutDialog = new Dialog<String>();
        aboutDialog.setTitle("Help");
        
        aboutDialog.setContentText("About this program");
        
        aboutDialog.getDialogPane().getButtonTypes().add(type);
        
        aboutDialog.setContentText("PAIN(T) (v. 0.2.0) by Jonathan Robledo \n Latest Build Date: September 17, 2021 \n Made with pain and suffering (otherwise known as Java)");
        
        //Tools 
        
        ToggleButton line = new ToggleButton("Line");
        ToggleGroup toolGroup = new ToggleGroup();
        
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        
        Slider widthSlider = new Slider(0, 50, 5);
        widthSlider.setBlockIncrement(1f);
        widthSlider.setShowTickMarks(true);
        widthSlider.setShowTickLabels(true);
        widthSlider.setMajorTickUnit(10f);
        
        line.setToggleGroup(toolGroup);

        //Set the ImageView
        
        ImageView imgBox = new ImageView();
        
        imgBox.setFitHeight(10);
        imgBox.setFitWidth(10);
        imgBox.setPreserveRatio(true);
        
        canvasPane.getChildren().addAll(imgBox, canvas);
        
        //ScrollPane setup
        
        ScrollPane imagePane = new ScrollPane();
        imagePane.setPrefSize(600, 600);
        imagePane.setContent(canvasPane);
        
        //Zoom slider setup
        Slider zoomSlider = new Slider(10, 400, 100);
        zoomSlider.setBlockIncrement(10f);
        
        //Align objects vertically
        HBox zoom = new HBox();
        Label zoomTxt = new Label("Zoom: ");
        zoom.getChildren().addAll(zoomTxt, zoomSlider);
        
        HBox toolBox = new HBox();
        Label widthTxt = new Label("  Stroke Width: ");
        Label colorTxt = new Label("  Stroke Color: ");
        toolBox.getChildren().addAll(line, colorTxt, colorPicker, widthTxt, widthSlider);
        
        VBox appBox = new VBox();
        appBox.getChildren().addAll(menu, toolBox, imagePane, zoom);
        
        //Application StackPane and scene
        StackPane root = new StackPane();
        root.getChildren().addAll(appBox);
        
        Scene scene = new Scene(root, 1024, 768);
        
        //Menu item functions
        
        // -- HELP
        
        help.setOnAction(e -> {
            helpDialog.showAndWait();
        });
        
        about.setOnAction(e -> {
            aboutDialog.showAndWait();
        });
        
        // -- OPEN
        
        open.setOnAction(e -> {
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    
            FileChooser imgExplorer = new FileChooser();
            
            FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            imgExplorer.getExtensionFilters().addAll(JPG, PNG);

            File imageFile = imgExplorer.showOpenDialog(null);
            
            if (imageFile != null) {
                System.out.println(imageFile.toURI().toString());
                Image img = new Image(imageFile.toURI().toString());
                
                imgLocation = imageFile.toURI().toString();
                
                appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum] + " - " + imgLocation);
                
                int x = (int)img.getWidth();
                int y = (int)img.getHeight();
                
                canvas.setHeight(y);
                canvas.setWidth(x);
                
                imgBox.setFitHeight(img.getHeight());
                imgBox.setFitWidth(img.getWidth());
                imgBox.setImage(img);
                
                imgX = img.getWidth();
                imgY = img.getHeight();
                
                if (canvas.getWidth() > appStage.getWidth()) { imagePane.setPrefWidth(appStage.getWidth() ); } else { imagePane.setPrefWidth(imgX); }
                if (canvas.getHeight() > appStage.getHeight()) { imagePane.setPrefHeight( appStage.getHeight() - 100 ); } else { imagePane.setPrefHeight(imgY); }
            }
            
        });
        
        // -- SAVE
        
        save.setOnAction(e -> {
            FileChooser imgExplorer = new FileChooser();
            
            FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            imgExplorer.getExtensionFilters().addAll(JPG, PNG);
            
            File file = new File(imgLocation);
            
            sp.setFill(Color.TRANSPARENT);
                
                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                        canvasPane.snapshot(sp, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        Logger.getLogger(PaintGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
        
        // -- SAVE AS
        
        saveAs.setOnAction(e -> {
            FileChooser imgExplorer = new FileChooser();
            
            FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            imgExplorer.getExtensionFilters().addAll(JPG, PNG);
            
            File file = imgExplorer.showSaveDialog(appStage);
            
            sp.setFill(Color.TRANSPARENT);
                
                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                        canvasPane.snapshot(sp, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                        
                        imgLocation = file.toURI().toString();
                        
                        appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum] + " - " + imgLocation);

                    } catch (IOException ex) {
                        Logger.getLogger(PaintGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
        
        // -- ZOOM SLIDER
        
        /* I can deal with this later
        (zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                g.scale( (double)newValue / 100, (double)newValue / 100 );
            }
        });*/
        
        // -- QUIT
        
        quit.setOnAction(e -> {
            System.exit(0);
        });
        
        //Tool functions
        
        canvas.setOnMousePressed( e -> {
            x1 = (int)e.getX();
            y1 = (int)e.getY();
        });
        canvas.setOnMouseReleased( e -> {
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            
            System.out.println("trollsome");
            
            g.setLineWidth(lineWidth);
            if (line.isSelected() == true) g.strokeLine(x1, y1, x2, y2);

        });
        
        colorPicker.setOnAction(e -> {
            Color color = colorPicker.getValue();
            
            g.setStroke(color);
        });
        
        widthSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    lineWidth = (double) new_val;
            }
        });

        //Stage settings
        
        appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum]);
        appStage.setScene(scene);
        
        //ImgBox resizing
        if (canvas.getWidth() > appStage.getWidth()) imagePane.setPrefWidth(appStage.getWidth() );
        if (canvas.getHeight() > appStage.getHeight()) imagePane.setPrefHeight( appStage.getHeight() - 100 );
        
        appStage.heightProperty().addListener(e -> {
            if (canvas.getHeight() > appStage.getHeight()) imagePane.setPrefHeight( appStage.getHeight() - 100 );
        });
            
        appStage.show();
        
        //Stage image resizing
        
        appStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (canvas.getWidth() > appStage.getWidth()) { imagePane.setPrefWidth(appStage.getWidth() ); } else { imagePane.setPrefWidth(imgX); }
        });

        appStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (canvas.getHeight() > appStage.getHeight()) { imagePane.setPrefHeight( appStage.getHeight() - 100 ); } else { imagePane.setPrefHeight(imgY); }
        });
    }
}
