/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotman;

import javafx.geometry.BoundingBox;

/**
 *
 * @author georgewpurnell
 */
public class StageManagement {
    Dot dottie;
    Platform Platform;
    Goal goal;
    private static final int SCREEN_LEFT_EDGE = 0;
    private int SCREEN_RIGHT_EDGE;
    private static final int TOP = 0;
    
    private static final int BOTTOM = DotMan.HEIGHT;
    public StageManagement(Dot dot, Platform platform, Goal goal){
        dottie = dot ;
        Platform = platform;
        this.goal = goal;
        SCREEN_RIGHT_EDGE= DotMan.WIDTH - dottie.getDotWidth();
    }

    public boolean checkPlatformCollision() {
        for(BoundingBox bounds: Platform.getBoundsList()) {
            if(bounds.intersects(dottie.getDot().getBoundsInParent()) && ((dottie.getDot().getBoundsInParent().getMaxY()) <= bounds.getMaxY())){  
                dottie.setyPos(bounds.getMaxY()- dottie.getDotHeight());
            return true;
        }
        }
        return false;
    }
    public boolean checkTouchGoal(double x, double y) {
        if(goal.getGoal().getBoundsInParent().intersects(dottie.getDot().getBoundsInParent())) {
            return true;        
        }
        else {return false;}
}
    public void keepOnScreen(double x, double y){
        if(x <= SCREEN_LEFT_EDGE) {dottie.setxPos(SCREEN_LEFT_EDGE);}
        else if (x >= SCREEN_RIGHT_EDGE) {dottie.setxPos(SCREEN_RIGHT_EDGE);}
        if(y <= TOP) {dottie.setyPos(TOP);}
        else if (y >= BOTTOM) {dottie.setyPos(BOTTOM);}
    }
}
