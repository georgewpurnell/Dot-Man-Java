/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotman;

/**
 *
 * @author georgewpurnell
 */
public class Score {
    int yum;
    private int currentScore, currentLevel,currentMultiplier, livesRemaining;
    public Score() {
        currentMultiplier = 1;
        livesRemaining = 1;
    }
    public void addPoints(int amount) {
        if(amount <= 0) {}
        else{
        currentScore += amount * currentMultiplier;
        while(true) {
            int upperBound;
             upperBound = 10000 * ( (int) Math.pow(2, currentLevel));
             if(currentScore >upperBound -1) {currentLevel++;}
             else{break;}
        }   
        }
    }
    public void subtractPoints(int amount) {
        if(amount <= 0) {}
        else{
        currentMultiplier = 1;
       
        if(amount > currentScore) {currentScore = currentLevel = 0;}
        currentScore -= amount;
        while(currentLevel > 0) {
            int lowerBound;
             lowerBound = 10000 * ( (int) Math.pow(2, (currentLevel-1)));
             if(currentScore <= lowerBound) {currentLevel--;}
             else{break;}
            
        }
        }
    }
    public void incrementMultiplier() {
        currentMultiplier ++;
    }
    public void loseLife() {
        livesRemaining --;
        if(livesRemaining > 0) {Dot.setIsAlive(true);}
        else{livesRemaining = 0; Dot.setIsAlive(false);}
    }
    public void gainLife () {
        livesRemaining ++;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentMultiplier() {
        return currentMultiplier;
    }

    public int getLivesRemaining() {
        return livesRemaining;
        
    }
    public void setLivesRemaining(int lives){
        livesRemaining = lives;
    }
    
}
