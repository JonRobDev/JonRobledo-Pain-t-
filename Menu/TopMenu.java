/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.jrobledo.Menu;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import paint.jrobledo.FileManagement.Save;
import paint.jrobledo.FileManagement.SaveAs;
import paint.jrobledo.FileManagement.Open;

/**
 *
 * @author acoff
 */
public class TopMenu{

    // This isn't really important, I just thought it'd be funny to add
        
    String[] titleJunk = {"World's first vomit-inducing paint program!", "Now with 50% more product!", "Asbestos-free!", "Comes in beige!", "It can do stuff!", "Works miracles!", "Not Minecraft!", "The Movie!", "Will not be held liable!", "I had to fill a slot!", "USDA approved!", "Not allowed in North Korea!"};
    int titleJunkNum = (int)Math.floor( titleJunk.length * Math.random() );    
    
    MenuBar menu;
    
    public String imgLocation = "No file loaded! ";
    
    public MenuItem open = new MenuItem("Open");
    public MenuItem save = new MenuItem("Save");
    public MenuItem saveAs = new MenuItem("Save As");
    public MenuItem quit = new MenuItem("Exit");
        
    public MenuItem help = new MenuItem("Help");
    public MenuItem about = new MenuItem("About");
    
    private double imgX;
    private double imgY;
    
    //File management init
    Save saveImg;
    SaveAs saveImgAs;
    Open openImg;
    
    Image image;
    
    public TopMenu(Canvas canvas, GraphicsContext g, SnapshotParameters sp, Stage appStage, ScrollPane imagePane){
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
                + "Tools - \n Line: Enables the ability to draw a straight line \n Color Picker: Chooses stroke color \n Line Width: Sets line width of tools");
        
        //Dialog about
        
        Dialog<String> aboutDialog = new Dialog<String>();
        aboutDialog.setTitle("Help");
        
        aboutDialog.setContentText("About this program");
        
        aboutDialog.getDialogPane().getButtonTypes().add(type);
        
        aboutDialog.setContentText("PAIN(T) (v. 0.2.0) by Jonathan Robledo \n Latest Build Date: September 17, 2021 \n Made with pain and suffering (otherwise known as Java)");
        
        //Menu itemFunctions;
        
        open.setOnAction(e -> {
            openImg = new Open(canvas, g, imgLocation, imgX, imgY);
            double[] dimensions = openImg.GetDimensions();
            imgX = dimensions[0];
            imgY = dimensions[1];
            imgLocation = openImg.returnDir();
            System.out.println(imgX + " , " + imgY);
            
            appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum] + " || " + imgLocation);
            
            this.image = openImg.returnImg();
        });
        
        //EVENTS
        
        save.setOnAction(e -> {
            saveImg = new Save(imgLocation, sp, canvas);
            appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum] + " || " + imgLocation);
        });
        
        saveAs.setOnAction(e -> {
            saveImgAs = new SaveAs(imgLocation, sp, canvas);
            appStage.setTitle("Pain(t): " + titleJunk[titleJunkNum] + " || " + imgLocation);
        });
        
        quit.setOnAction(e -> {
            System.exit(0);
        });
        
        // -- HELP
        
        help.setOnAction(e -> {
            helpDialog.showAndWait();
        });
        
        about.setOnAction(e -> {
            aboutDialog.showAndWait();
        });
        
    }
    
    public MenuBar GetMenu(){
        return menu;
    }
    
    public double GetX(){
        return imgX;
    }
    
    public double GetY(){
        return imgY;
    }
    
    public String returnTitle(){
        return "Pain(t): " + titleJunk[titleJunkNum] + " || " +imgLocation;
    }
    
    public Image returnImg(){
        return image;
    }
}
