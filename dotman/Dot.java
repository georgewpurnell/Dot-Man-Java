/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotman;

import static dotman.DotMan.HEIGHT;
import javafx.scene.shape.Rectangle;
//delete movingRight check by pos or neg
/**
 *
 * @author georgewpurnell
 */
public class Dot {
    protected static double xPos, yPos, horVector, vertVector, gravity, acceleration, deceleration, jumpStart, solidFooting;
    private final static double TERMINAL_VELOCITY = -5;
    protected static int dotWidth, dotHeight, targetSpeed, targetHeight, vertAccel;
    private static boolean isAlive, isInAir, isFalling, isRunning, isOnPlatform, isOnGround, isMovingRight, isMovingLeft, closeToGoal,touchGoal,isContinue;
    protected Rectangle dot;
    protected static DotMan dotMan;
    protected static Score player;
    public Dot(DotMan gameData) {
        isAlive = true;
        dotWidth = 15;
        dotHeight = 15;
        yPos = jumpStart = solidFooting= HEIGHT - dotHeight;
        xPos = 10;
        targetHeight = 50;
        vertVector = 5;
        targetSpeed  = 5;
        acceleration = .04;
        deceleration = .90;
        vertAccel = 4;
        gravity = .05;
        dotMan = gameData; 
        dot = new Rectangle(xPos, yPos, dotWidth, dotHeight);
        player = dotMan.getPlayer();
        //dot.setStroke(Color.BLACK);               
    }
    public void update() {
        if (!isAlive() && !isContinue){
            //dotMan.root.getChildren().clear();            
            //dotMan.continueSequence();
            GameScreens.switchToContinue();
            isContinue = true;
            
            }
        else{
            scorePoints();
            goalCheck();
            if(dotMan.isLeft() && !(isMovingRight)) {moveDotL();}
            if(dotMan.isRight() && !(isMovingLeft)) {moveDotR();}
            if(((dotMan.isSpace()) && (yPos == jumpStart)) || isInAir) {jump();}
            if(!(dotMan.isLeft() || dotMan.isRight())) {
                isMovingLeft = isMovingRight = false;
                horVector = deceleration * horVector;
                //if(horVector != 0){System.out.println(horVector);}
                if((horVector >= 0) && (horVector < .9)) {horVector = 0; isMovingRight = false;}
                else if ((horVector <= 0) && (horVector > -.9)) {horVector = 0; isMovingLeft = false;}
                if(isOnPlatform && (horVector != 0)){
                    if(!dotMan.stageManagement.checkPlatformCollision()){
                        vertVector = 0;
                        jumpStart = HEIGHT- dotHeight;
                        jump();
                    }
                }           
            }
            fallIfOffPlatform();
            dotMan.stageManagement.keepOnScreen(xPos, yPos); 
            dot.setX(xPos);
            if ((yPos >= jumpStart) ) {checkIfLifeLost(); yPos = solidFooting = jumpStart; isInAir = false;vertVector = targetSpeed; isFalling = false;}
            dot.setY(yPos);
            }
    }
    protected void moveDotL() {
        isMovingRight = false;
        isMovingLeft = true;
        horVector = acceleration * -targetSpeed + (1- acceleration) * horVector;
        xPos += horVector;

    }
    protected void moveDotR() {
        isMovingRight = true;
        isMovingLeft = false;
        horVector = acceleration * targetSpeed + (1- acceleration) * horVector;
        xPos += horVector;        
    }
    protected void jump() {
        isInAir = true;
        isOnPlatform = false;              
        vertVector = vertVector - (gravity * vertAccel);
        if(vertVector < TERMINAL_VELOCITY) {vertVector = TERMINAL_VELOCITY;}
        isFalling = vertVector < 0;
        yPos -= vertVector; 
        if (isFalling()) {
            if(dotMan.stageManagement.checkPlatformCollision()){
                checkIfLifeLost();
                System.out.print(player.getLivesRemaining());
                jumpStart = solidFooting = yPos;
                isOnPlatform = true;
            }
            else{jumpStart = HEIGHT- dotHeight;}
        }

    }
    protected void fallIfOffPlatform() {
        if(isOnPlatform) {
            if(!dotMan.stageManagement.checkPlatformCollision()){
                vertVector = 0;
                jumpStart = HEIGHT- dotHeight;
                jump();
            }
        }
    }
    protected void goalCheck(){
        if(isCloseToGoal()) {closeToGoal = true;}
        else{closeToGoal = false;}
        if(closeToGoal) {
            if(dotMan.stageManagement.checkTouchGoal(xPos,yPos)){touchGoal = true;}
            if(touchGoal){dotMan.getGoal().winGame();}          
        }
    }
    protected boolean isCloseToGoal() {
        if(dotMan.getGoal().getCloseBounds().intersects(dot.getBoundsInParent())) {
            return true;
        }
        else {return false;}
    }
    protected void scorePoints() {
        if (isOnPlatform) {
            player.addPoints(20); 
            dotMan.setGameScore(player.getCurrentScore());
        dotMan.setLives(player.getLivesRemaining());
        }
    }
    protected void checkIfLifeLost() {
        if (((yPos - solidFooting) > 100) && isFalling){
            player.loseLife();
            dotMan.setLives(player.getLivesRemaining());
        }
            
    } 
    public static void resetDot(){
        yPos = jumpStart = solidFooting= HEIGHT - dotHeight;
        xPos = 10;
        player.setLivesRemaining(1);
        dotMan.setLives(player.getLivesRemaining());
        isAlive = true;
        isContinue = false;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public Rectangle getDot() {
        return dot;
    }

    public void setDot(Rectangle dot) {
        this.dot = dot;
    }

    public int getDotWidth() {
        return dotWidth;
    }

    public int getDotHeight() {
        return dotHeight;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double y) {
        yPos = y;
        dot.setY(yPos);
        
    }

    public static double getTerminalVelocity() {
        return TERMINAL_VELOCITY;
    }

    public double getJumpStart() {
        return jumpStart;
    }

    public boolean getCloseToGoal() {
        return closeToGoal;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public static void setIsAlive(boolean alive) {
        isAlive = alive;
    }
    
    
}
