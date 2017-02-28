/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotman;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author georgewpurnell
 */
public class GameScreens {
    private Node startMenu, continueNode, gameOverNode;
    private static DotMan dotMan;
    public static ImageView cMenu;
    private static boolean sequentialTransition;
    public GameScreens(DotMan dotMan) {
        this.dotMan = dotMan;
    }
        private Node createStartMenu(){
        Pane menuPane = new Pane();
        menuPane.setPrefSize(550, 600);
        Image menuBg = new Image("/menuBg1.png",550,600,false,true,false);
        ImageView sMenu = new ImageView(menuBg);
        //sMenu.setFitWidth(550);
        //sMenu.setFitHeight(600);
        Title title = new Title("D O T M A N", 300, 80);
        title.setTranslateX(122);
        title.setTranslateY(150);
        MenuItem  play = new MenuItem("Play");
        play.setOnMouseClicked(Event -> {
            System.out.println("createStartMenu Before clear: " + dotMan.root.getChildren());
            dotMan.root.getChildren().clear();
            System.out.println("createStartMenu after clear: " + dotMan.root.getChildren());
            dotMan.inGameSequence();
            changeMusic("file:////Users/georgewpurnell/Documents/Computer/NetBeansProjects/DotMan/src/in_game.wav");
            fadeImage("/menuBg1.png", 3000, 1, 0);
        });
        MenuItem  highScore = new MenuItem("HighScore");
        highScore.setOnMouseClicked(Event -> menuPane.toBack());
        MenuItem  options = new MenuItem("Options");
        options.setOnMouseClicked(Event -> menuPane.toBack());
        MenuItem  quit = new MenuItem("Quit");
        quit.setOnMouseClicked(Event -> System.exit(0));
        MenuBox menu = new MenuBox(
            play,
            highScore,
            options,
            quit);
        menu.setTranslateX(175);
        menu.setTranslateY(300);
        
        menuPane.getChildren().addAll(sMenu, title, menu);

        return menuPane;
    }
    private static Node createContinueScreen(){
        Pane continuePane = new Pane();
        continuePane.setPrefSize(550, 600);
        Image continueBg = new Image("/sky1.png",550,600,false,true,false);
        ImageView cMenu = new ImageView(continueBg);
        //sMenu.setFitWidth(550);
        //sMenu.setFitHeight(600);
        Title title = new Title("C O N T I N U E ?", 400, 80);
        title.setTranslateX(75);
        title.setTranslateY(200);
        MenuItem  continueItem = new MenuItem("Continue");
        continueItem.setOnMouseClicked(Event -> {
            Dot.resetDot();
            dotMan.root.getChildren().clear();
            changeMusic("file:////Users/georgewpurnell/Documents/Computer/NetBeansProjects/DotMan/src/in_game.wav");
            dotMan.inGameSequence();
            fadeImage("/night.jpg", 3000, 1, 0);
        });
        MenuItem  quit = new MenuItem("Quit");
        quit.setOnMouseClicked(Event -> System.exit(0));
        MenuBox menu = new MenuBox(
                continueItem,
                quit);
        menu.setTranslateX(175);
        menu.setTranslateY(300);
        System.out.println("createContinue before addAll clear: " + dotMan.root.getChildren());
        dotMan.root.getChildren().addAll( title, menu, cMenu);
        System.out.println("createContinue after addAll: " + dotMan.root.getChildren());
        cMenu.toBack();
        return continuePane;
    }
    private Node createGameOverScreen(){
        Pane gameOverPane = new Pane();
        gameOverPane.setPrefSize(550, 600);
        Image gameOverBg = new Image("/night.jpg",550,600,false,true,false);
        ImageView gameOver = new ImageView(gameOverBg);
        Title title = new Title("G A M E  O V E R", 400, 80);
        title.setTranslateX(75);
        title.setTranslateY(250);
        gameOverPane.getChildren().addAll(gameOver,title);
        return gameOverPane;
    }
    private Node createOpeningSequence(){
        Pane gameOverPane = new Pane();
        gameOverPane.setPrefSize(550, 600);
        Image continueBg = new Image("/night.jpg",550,600,false,true,false);
        ImageView gameOver = new ImageView(continueBg);
        gameOverPane.getChildren().addAll(gameOver);
        return gameOverPane;
    }
    private static void changeMusic(String audioPath) {
        dotMan.mediaPlayer.stop();//use method in DotMan
        Media media = new Media(audioPath);
        dotMan.mediaPlayer = new MediaPlayer(media);
        dotMan.mediaPlayer.setAutoPlay(true);
        dotMan.mediaPlayer.setVolume(.1);
    }
    private static FadeTransition fadeImage(String imagePath, double duration, double from, double to) {
        Image image = new Image(imagePath,550,600,false,true,false);
        ImageView fadeImage = new ImageView(image);
        fadeImage.setOpacity(from);
        FadeTransition ft = new FadeTransition();
        ft.setNode(fadeImage);
        ft.setDuration(new Duration(duration));
        ft.setFromValue(from);
        ft.setToValue(to);
        ft.setAutoReverse(true);
        System.out.println("switchScene before fadeImage: " + dotMan.root.getChildren());
        dotMan.root.getChildren().add(fadeImage);
        System.out.println("switchToContinue after fadeImage: " + dotMan.root.getChildren());
        if(!sequentialTransition){ft.play();}
        return ft;
    }
    public static void switchToContinue(){
        //createContinueScreen();
        dotMan.root.getChildren().clear();
        changeMusic("file:////Users/georgewpurnell/Documents/Computer/NetBeansProjects/DotMan/src/game_over.wav");
        fadeImage("/sky1.png", 3000, 1, 0);
        fadeImage("/game_over.png", 3000, 0, 1);
        FadeTransition ft;
        ft = fadeImage("/night.jpg", 6000, 0, 1);
        ft.setOnFinished(Event -> createContinueScreen());
        
        /*sequentialTransition = true;
        FadeTransition ft1, ft2;
        PauseTransition pT = new PauseTransition(Duration.millis(3000));
        SequentialTransition seqT = new SequentialTransition(pT, fadeImage("/game_over.png", 3000, 1, 0),
                fadeImage("/night.jpg", 3000, 0, 1));
        seqT.play();
        sequentialTransition = false;*/

    }
    private static class Title extends StackPane {
        public  Title(String name, double x, double y) {
            Rectangle bg = new Rectangle(x, y);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);bg.setFill(null);
            
            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("TW Cen MT Condensed",FontWeight.SEMI_BOLD,50));
            
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            
        }
    }
    private  static class MenuItem extends StackPane {
        public MenuItem(String name) {
            LinearGradient gradient = new LinearGradient(0,0,1,0, true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(0, Color.ALICEBLUE),
                new Stop(0.15,Color.DARKBLUE),
                new Stop(0.3,Color.BLACK),
                new Stop(0.7,Color.BLACK),
                new Stop(.85,Color.DARKBLUE),
                new Stop(1, Color.ALICEBLUE)
            });
            Rectangle bg = new Rectangle(200,30);
            bg.setOpacity(0.4);
            
            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("TW Cen MT Condensed",FontWeight.SEMI_BOLD,22));
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });
            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });
        }
    }
    private static class MenuBox extends VBox{
        public MenuBox(MenuItem... items) {
            getChildren().add(createSeperator());
            
            for (MenuItem item : items) {
                getChildren().addAll(item, createSeperator());
            }
        }
        private Line createSeperator() {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGRAY);
            return sep;
        }
    }
    public Node getStartMenu() {
        startMenu = createStartMenu();
        return startMenu;
    }
    public Node getContinuePane() {
        continueNode = createContinueScreen();
        return continueNode;
    }
    public Node getGameOverPane() {
        gameOverNode = createGameOverScreen();
        return gameOverNode;
    }
    
    
}
