/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotman;

import javafx.animation.AnimationTimer;

public class GamePlayLoop extends AnimationTimer {
    protected DotMan dotMan;
    protected StageManagement stageManagement;
    public GamePlayLoop(DotMan dots){
        dotMan = dots;
    }
    @Override
    public void handle(long now) {
       dotMan.getDottie().update();
    }
    @Override
    public void start() {
        super.start();
    }
    @Override
    public void stop() {
        super.stop();
    }
}
