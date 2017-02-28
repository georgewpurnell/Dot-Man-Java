/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import static javafx.scene.text.TextAlignment.CENTER;
import javafx.stage.Stage;

/**
 *
 * @author georgewpurnell
 */
public class DotMan extends Application {
    public static final int WIDTH = 550, HEIGHT = 600;
    private int gameScore, lives;
    private Text scoreText, scoreLabel, livesText, livesLabel, gameStateText;
    private Font scoreFont, livesFont, gameStateFont;
    private boolean space, left, right, wKey, sKey, dKey, aKey;
    private HBox textContainer,scoreContainer, livesContainer;
    private Scene scene;
    private BackgroundImage background;
    private Image sky;
    private static ImageView view;
    private static Node startPane, inGame, continuePane, gameOverPane;
    private GamePlayLoop gamePlayLoop;
    private Dot dottie;
    private Goal goal;
    private Platform platform;
    private String platformDataString;
     Group root;
     MediaPlayer mediaPlayer;
     Media media;
    private Score player;
    private static GameScreens gameScreens;
    StageManagement stageManagement;
   
    
    @Override
    public void start(Stage primaryStage) {
        platformDataString = "(200,560,300,560),(320,540,420,540),(190,520,290,520),(180,485,280,485),(120,475,160,475),(120,435,160,435),\n" +
                        "(130,410,165,410),(140,380,150,380),(170,360,300,360),(340,370,390,370),(350,340,380,340),(355,320,375,320),\n" +
                       "(355,290,375,290),(255,290,315,290),(180,260,240,260),(100,320,120,320),(20,290,80,290),(20,260,80,260),\n" +
                       "(20,230,80,230),(20,200,80,200),(195,170,205,170),(235,180,255,180),(285,170,305,170),(325,160,365,160),\n" +
                       "(395,230,480,230),(495,240,520,240),(470,170,500,170),(495,140,515,140),(470,110,500,110),(435,80,455,80),\n" +
                       "(300,120,350,120)";
        platformDataString = platformDataString.replaceAll("\\(|\\)|\n|\\p{javaSpaceChar}", "");
        player = new Score();
        lives = player.getLivesRemaining();
        root = new Group();
        dottie = new Dot(this);
        goal = new Goal();
        platform = new Platform(this, platformDataString);
        stageManagement = new StageManagement(dottie,platform,goal);
        gameScreens = new GameScreens(this);
        createNodes();
        loadImageAssets();
        loadAudioAssets();
        createGameActors();
        createStartGameLoop();
        scene = new Scene(root, WIDTH, HEIGHT);
        createSceneEventHandling();
        addNodesToGroup();
        primaryStage.setTitle("Dot Man");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   
    private void createStartGameLoop() {
        gamePlayLoop = new GamePlayLoop(this);
        gamePlayLoop.start();
    }
    private void createSceneEventHandling() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case SPACE: space = true; break;
                case LEFT: left = true; break;
                case RIGHT: right = true; break;
         
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case SPACE: space = false; break;
                case LEFT: left = false; break;
                case RIGHT: right = false; break;

            }
        });
    }

    /*public Dot getDottie() {
        return dottie;
    }*/
        private void createNodes() {
        scoreText = new Text(String.valueOf(gameScore) );
        scoreFont = new Font("Verdana", 12);
        scoreText.setFont(scoreFont);
        scoreText.setFill(Color.BLUE);
        scoreText.setWrappingWidth(150);
        livesText = new Text(String.valueOf(lives) );
        livesFont = new Font("Verdana", 12);
        livesText.setFont(scoreFont);
        livesText.setFill(Color.BLUE);
        livesText.setWrappingWidth(20);        
        livesText.setTextAlignment(TextAlignment.CENTER);
        gameStateText = new Text(String.valueOf("Level 1"));
        gameStateFont = new Font("Verdana", 15);
        gameStateText.setWrappingWidth(150);
        gameStateText.setTextAlignment(CENTER);
        scoreLabel = new Text("SCORE:");
        scoreLabel.setFont(scoreFont);
        scoreLabel.setFill(Color.BLACK);
        scoreLabel.setWrappingWidth(50);
        livesLabel = new Text("LIVES:");
        livesLabel.setFont(scoreFont);
        livesLabel.setFill(Color.BLACK);
        livesLabel.setWrappingWidth(180);
        livesLabel.setTextAlignment(TextAlignment.RIGHT);
        livesContainer = new HBox(livesLabel);       
        textContainer = new HBox();
        textContainer.getChildren().addAll(scoreLabel,scoreText,gameStateText,livesLabel, livesText);

//textContainer = new HBox( 150,scoreContainer, gameStateText, livesContainer);
        //textContainer.setPrefWidth(550);
        //scoreContainer.setAlignment(Pos.CENTER_LEFT);
        //textContainer.setHgrow(gameStateText,Priority.NEVER);
        //textContainer.setAlignment(Pos.CENTER);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    private void addNodesToGroup() {
        System.out.println("Before ToGroup: " + root.getChildren());
        startPane = gameScreens.getStartMenu();
        continuePane = gameScreens.getContinuePane();
        gameOverPane = gameScreens.getGameOverPane();
        view = new ImageView(sky);
        root.getChildren().add(startPane);
        System.out.println("after ToGroup: " + root.getChildren());

    }
    public void inGameSequence(){
        System.out.println("Before inGame: " + root.getChildren());
        for(int i = 0; i <= platform.getPlatformList().size() -1; i++ ) {
            root.getChildren().add(platform.getPlatformList().get(i));
        }
        root.getChildren().addAll(textContainer,dottie.getDot(),goal.getGoal(),view);
        view.toBack();
        System.out.println("after inGame: " + root.getChildren());
    }
    public void continueSequence() {
        //root.getChildren().addAll(continuePane);
        //GameScreens.cMenu.toBack();
        System.out.println("Before continueSequence: " + root.getChildren());
        for(int i = 0; i <= platform.getPlatformList().size() -1; i++ ) {
            root.getChildren().add(platform.getPlatformList().get(i));
        }
        root.getChildren().addAll(textContainer,dottie.getDot(),goal.getGoal(),view);
        System.out.println("after continueSequence: " + root.getChildren());
        view.toBack();
    }

    private void loadImageAssets() {
        sky = new Image("/sky1.png",550,600,false,true,false);
        
    }

    private void loadAudioAssets() {
        media = new Media("file:////Users/georgewpurnell/Documents/Computer/NetBeansProjects/DotMan/src/intro.wav");
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(.1);
    }

    private void createGameActors() {
        //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isSpace() {
        return space;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public Platform getPlatform() {
        return platform;
    }

    public StageManagement getStageManagement() {
        return stageManagement;
    }

    public Dot getDottie() {
        return dottie;
    }

    public Goal getGoal() {
        return goal;
    }

    public Score getPlayer() {
        return player;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
        scoreText.setText(String.valueOf(gameScore));
    }

    public void setLives(int lives) {
        this.lives = lives;
        livesText.setText(String.valueOf(lives));
    }

    public static Node getStartPane() {
        return startPane;
    }

    public ImageView getView() {
        return view;
    }
    
}
