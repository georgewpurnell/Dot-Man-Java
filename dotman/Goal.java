/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotman;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author georgewpurnell
 */
public class Goal {
    private Rectangle goal;
    private final double xPos, yPos, width, height, goalBoundsBuffer;
    private int count, cindex;
    private Bounds closeBounds;
    private ArrayList<Color> colorList;
    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public Goal () { 
        colorList = new ArrayList<>();
        colorList.add(Color.DARKBLUE);
        colorList.add(Color.INDIGO);
        colorList.add(Color.YELLOW);
        colorList.add(Color.ORANGE);
        colorList.add(Color.SPRINGGREEN);
        colorList.add(Color.STEELBLUE);
        xPos = 400;
        yPos = 10;
        width = 40;
        height = 40;
        goalBoundsBuffer = 20;
        goal = new Rectangle(xPos, yPos, width, height);
        goal.setFill(Color.AQUAMARINE); 
        closeBounds = new BoundingBox(goal.getBoundsInParent().getMinX() - goalBoundsBuffer,
                      goal.getBoundsInParent().getMinY()- goalBoundsBuffer,
                      goal.getBoundsInParent().getWidth() + (goalBoundsBuffer*2),
                      goal.getBoundsInParent().getHeight() + (goalBoundsBuffer*2));
    }
    public Rectangle getGoal() {
        return goal;
    }
    public void winGame() {
        if(count == 0) {
            goal.setFill(colorList.get(cindex));
            cindex++;
            if(cindex == 6){cindex = 0;}
        }
        count++;
        if(count == 30) {count = 0;}        
    } 

    public Bounds getCloseBounds() {
        return closeBounds;
    }
    
}
