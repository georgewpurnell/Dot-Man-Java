/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotman;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author georgewpurnell
 */
public class Platform {
    private final String[] paramList;
    private Rectangle platform;
    private double platformHeight;
    private BoundingBox bounds;
    private static final List<Rectangle> platformList = new ArrayList<>();
    private static final List<BoundingBox> boundsList = new ArrayList<>();
    public Platform(DotMan gameData, String paramString) {
        platformHeight = 4;
        paramList = paramString.split(",");
        if ((paramList.length % 4) == 0) {
            for(int i = 4; i <= paramList.length; i += 4) {
                makePlatform(Integer.valueOf(paramList[i-4]),Integer.valueOf(paramList[i-3]),
                        Integer.valueOf(paramList[i-2] ) - Integer.valueOf(paramList[i-4]),platformHeight);                
                bounds = new BoundingBox(Integer.valueOf(paramList[i-4]),
                        Integer.valueOf(paramList[i-3]) + Dot.getTerminalVelocity(), 
                        Integer.valueOf(paramList[i-2] ) - Integer.valueOf(paramList[i-4]),
                        abs(Dot.getTerminalVelocity()));
                boundsList.add(bounds);
            } 
        }   
    }
    private void makePlatform(double x, double y, double width, double height) {
        platform = new Rectangle(x,y,width,height);
        platform.setStroke(Color.BLACK);
        platform.setStrokeWidth(0);
        platform.setFill(Color.ORANGE);   
        platformList.add(platform);
    }

    public List<Rectangle> getPlatformList() {
        return platformList;
    }

    public static List<BoundingBox> getBoundsList() {
        return boundsList;
    }
            
}
 