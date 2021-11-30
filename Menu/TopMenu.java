/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Menu;

import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventType;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;

import paint.jrobledo.FileManagement.Save;
import paint.jrobledo.FileManagement.SaveAs;
import paint.jrobledo.FileManagement.Open;

/**
 *
 * @author Jonathan Robledo
 */
public class TopMenu{

    // This isn't really important, I just thought it'd be funny to add
        
    String[] titleJunk = {
        "World's first vomit-inducing paint program!", 
        "Now with 50% more product!", 
        "Asbestos-free!", 
        "Comes in beige!", 
        "It can do stuff!", 
        "Works miracles!", 
        "Not Minecraft!", 
        "The Movie!", 
        "Will not be held liable!", 
        "I had to fill a slot!", 
        "USDA approved!", 
        "Not allowed in North Korea!",
        "Totally not finished at the last minute!",
        "I hate Java!",
        "On the brink of nuclear war!",
        "4th horseman of the apocalypse!",
        "It's almost as good as MS Paint!",
        "Still infested with wasps!",
        "Politically charged!",
        "You don't wanna use this!"};
    int titleJunkNum = (int)Math.floor( titleJunk.length * Math.random() );    
    
    MenuBar menu;
    
    public double zoomVal = 100;
    
    String imgLocation = "No file loaded! ";
    String autoSaveLocation = System.getProperty("java.io.tmpdir") + "temp.png";
    
    MenuItem newCanvas = new MenuItem("New Canvas");
    MenuItem open = new MenuItem("Open");
    MenuItem save = new MenuItem("Save");
    MenuItem saveAs = new MenuItem("Save As");
    MenuItem quit = new MenuItem("Exit");
        
    MenuItem help = new MenuItem("Help");
    MenuItem about = new MenuItem("About");
    
    public double imgX;
    public double imgY;
    
    SnapshotParameters sp;
    Canvas canvas; 
    
    //File management init
    Save saveImg;
    Save autoSave;
    SaveAs saveImgAs;
    Open openImg;
    
    Image image;
    
    Boolean isAutoSave = false;
    
    /**
    *   TopMenu is a constructor which contains all of the UI elements for the toolbox along with executing the required methods from DrawingTools. 
    *   
    *   @param canvas       Sets the currently available canvas.
    *   @param g            Sets the main accessible GraphicsContext.
    *   @param sp           Sets the current SnapshotParameters.
    *   @param appStage     Sets the Stage that the toolbox needs to access.
    *   @param grid         Sets the GridPane that will be rescaled.
    *   @param imagePane    Sets the ImagePane that will be rescaled to fit all the GridPane content.
    *   @param root         Sets the current StackPane 
    * 
    *   @author Jonathan R.
    */
    
    public TopMenu(Canvas canvas, GraphicsContext g, SnapshotParameters sp, Stage appStage, GridPane grid, ScrollPane imagePane, StackPane root){
        this.sp = sp;
        this.canvas = canvas;
        
        //Accelerators
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        
        //Menu setup (Bar, menu, items)
        
        menu = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");

        fileMenu.getItems().addAll(open, save, saveAs, quit);
        helpMenu.getItems().addAll(help, about);

        menu.getMenus().addAll(fileMenu, helpMenu);
        
        //Dialog help
        
        Dialog<String> helpDialog = new Dialog<String>();
        helpDialog.setTitle("Help");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        
        helpDialog.setContentText("Help");
     
        helpDialog.getDialogPane().getButtonTypes().add(type);
        
        helpDialog.setContentText("So what does this stuff do? \n \n "
                + "File Menu - \n Open: Opens an image from the file explorer \n Save: Saves current image \n Save As: Saves current image in new directory \n "
                + "Tools - \n Pencil: Enables freehand drawing \n Line: Enables the ability to draw a straight line \n Rectangle: Draws a rectangle onto the canvas \n"
                + " \n Color Picker: Chooses stroke color \n Line Width: Sets line width of tools \n"
                + " Zoom: Sets the current zoom for the canvas. \n Polygon sides: Sets the amount of sides for the polygon tool. ");
        
        //Dialog about
        Dialog<String> aboutDialog = new Dialog<String>();
        aboutDialog.setTitle("Help");
        
        aboutDialog.setContentText("About this program");
        
        aboutDialog.getDialogPane().getButtonTypes().add(type);
        
        aboutDialog.setContentText("PAIN(T) (v. 0.5.0) by Jonathan Robledo \n Latest Build Date: October 8th, 2021 \n Made with pain and suffering (otherwise known as Java)");
        
        // AUTO SAVE
        TimerTask autoTask = new TimerTask(){
            public void run() {
                isAutoSave = true;
            }
        };
    
        Timer autoTime = new Timer();
        autoTime.scheduleAtFixedRate(autoTask, 10000, 10 * 1000);
        
        //Menu itemFunctions;
        open.setOnAction(e -> {
            openImg = new Open(canvas, g, imgLocation, imgX, imgY);
            double[] dimensions = openImg.GetDimensions();
            imgX = dimensions[0];
            imgY = dimensions[1];
            imgLocation = openImg.returnDir();
            System.out.println(imgX + " , " + imgY);
            
            appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum] + " || " + imgLocation);
            
            grid.setPrefWidth(  imgX * (zoomVal / 100) ); 
            grid.setPrefHeight(  imgY * (zoomVal / 100) ); 
            
            imagePane.setContent(grid);
            
            imagePane.setPrefSize(imgX * (zoomVal / 100), imgY * (zoomVal / 100));
            
            System.out.println(imagePane.getPrefHeight());
            System.out.println(imagePane.getPrefWidth());

            this.image = openImg.returnImg();
        });
        
        save.setOnAction(e -> {
            saveImg = new Save(imgLocation, sp, canvas);
            appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum] + " || " + imgLocation);
        });
        
        saveAs.setOnAction(e -> {
            saveImgAs = new SaveAs(imgLocation, sp, canvas);
            appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum] + " || " + imgLocation);
        });
        
        quit.setOnAction(e -> {
            autoTime.cancel();
            System.exit(0);
        });
        
        appStage.addEventHandler(EventType.ROOT, e -> {
            if(isAutoSave.equals(true)){
                autoSaveLocation = System.getProperty("java.io.tmpdir") + "temp.png";
                saveImg = new Save(autoSaveLocation, sp, canvas);
                isAutoSave = false;
            }
        });
        
        // -- HELP
        
        help.setOnAction(e -> {
            helpDialog.showAndWait();
        });
        
        about.setOnAction(e -> {
            aboutDialog.showAndWait();
        });
        
    }
    
    /**
    * <p> Returns the menu bar. </p>
    * @since 0.3.0
    * @return MenuBar
    */
    
    public MenuBar GetMenu(){
        return menu;
    }
    
    /**
    * <p> Returns the width of the image. </p>
    * @since 0.3.0
    * @return Image width
    */
    
    public double GetX(){
        return imgX;
    }
    
    /**
    * <p> Returns the height of the image. </p>
    * @since 0.3.0
    * @return Image height
    */
    
    public double GetY(){
        return imgY;
    }
    
    /**
    * <p> Returns a title for the window including the current directory of the image. </p>
    * @since 0.3.0
    * @return Window title
    */
    
    public String returnTitle(){
        return "Pain(t): " + titleJunk[titleJunkNum] + " || " +imgLocation;
    }
    
    /**
    * <p> Returns the current image. </p>
    * @since 0.3.0
    * @return Current image.
    */
    
    public Image returnImg(){
        return image;
    }
}
