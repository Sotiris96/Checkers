package ist_checkers;

//------------IMPORTS--------------
//---------------------------------

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundSize.DEFAULT;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * MAIN CLASS FOR THE CHECKERS GAME, IT CREATES THE INITIAL WINDOWS, SETS THE
 * LAYOUTS AS WELL AS THE DIFFICULTY lEVELS IT ALSO INITIALIZES THE BOARD.
 * 
 * @author sa725
 */
public class IST_Checkers extends Application {

    //------------FIELDS---------------
    //---------------------------------
    
    /**
     * VARIABLE TO HOLD THE DIFFICULTY LEVEL.
     */
     public static int DIFFICULTY;
     
    /**
     * VARIABLE TO HOLD THE PRIMARY STAGE.
     */
    private static Stage primaryStage = new Stage();
    
    /**
     * VARIABLE TO HOLD THE GAME STAGE OF THE APPLICATION.
     */
    public static Stage game_stage;
    
    /**
     * CSS FOR STYLING THE BUTTONS.
     */
    private static String button_css = "    -fx-background-color:transparent \n"
            + "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n"
            + "        linear-gradient(#020b02, #3a3a3a),\n"
            + "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\n"
            + "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\n"
            + "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\n"
            + "        -fx-background-insets: 0,1,4,5,6;\n"
            + "        -fx-background-radius: 9,8,5,4,3;\n"
            + "        -fx-padding: 15 30 15 30;\n"
            + "        -fx-font-family: \"Helvetica\";\n"
            + "        -fx-font-size: 19px;\n"
            + "        -fx-font-weight: bold;\n"
            + "        -fx-text-fill: white;\n"
            + "        -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);";

    private static String hovered_css = "    -fx-background-color: \n"
            + "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n"
            + "        linear-gradient(#020b02, #3a3a3a),\n"
            + "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\n"
            + "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\n"
            + "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\n"
            + "        -fx-background-insets: 0,1,4,5,6;\n"
            + "        -fx-background-radius: 9,8,5,4,3;\n"
            + "        -fx-padding: 15 30 15 30;\n"
            + "        -fx-font-family: \"Helvetica\";\n"
            + "        -fx-font-size: 19 px;\n"
            + "        -fx-font-weight: bold;\n"
            + "        -fx-text-fill: white;\n"
            + "        -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 0, 0.0 , 0 , 0);\n"
            + "        -fx-scale-x: 0.95;\n"
            + "        -fx-scale-y: 0.95;\n"
            + "        -fx-scale-z: 0.95";


    //----------------METHODS----------------
    //---------------------------------------
    
    /**
     * SETS THE PRIMARY STAGE OF THE APPLICATION.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        // Menu Bar
        MenuBar menu = new MenuBar();
        Label help_label = new Label("Help");
        Menu help = new Menu("", help_label);
        menu.getMenus().addAll(help);

        // Buttons
        Button multiplayer = new Button("Multiplayer");
        Button single = new Button("Play Against AI");
        multiplayer.setPrefWidth(220);
        multiplayer.setPrefHeight(70);
        single.setPrefWidth(220);
        single.setPrefHeight(70);
        multiplayer.setStyle(button_css);
        multiplayer.setOnMouseEntered(e -> multiplayer.setStyle(hovered_css));
        multiplayer.setOnMouseExited(e -> multiplayer.setStyle(button_css));
        single.setStyle(button_css);
        single.setOnMouseEntered(e -> single.setStyle(hovered_css));
        single.setOnMouseExited(e -> single.setStyle(button_css));

        // Welcome Text 
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        Text t = new Text();
        t.setX(20);
        t.setY(100);
        t.setText("Checkers Game");
        t.setFill(Color.WHITE);
        t.setFont(Font.font(null, FontWeight.BOLD, 50));
        t.setEffect(ds);

        // Add all the components into a VBox
        VBox vbox_buttons = new VBox(2);
        VBox vbox_UI = new VBox(2);
        vbox_buttons.getChildren().addAll(multiplayer, single);
        vbox_buttons.setAlignment(Pos.CENTER);
        vbox_buttons.setSpacing(13);
        vbox_UI.getChildren().addAll(t, vbox_buttons);
        vbox_UI.setAlignment(Pos.CENTER);
        vbox_UI.setSpacing(60);

        // Set layout
        Image img = new Image("/images/background.png");
        BackgroundImage bg = new BackgroundImage(img, NO_REPEAT, NO_REPEAT, CENTER, DEFAULT);
        Background background = new Background(bg);
        BorderPane layout = new BorderPane();
        layout.setTop(menu);
        layout.setCenter(vbox_UI);
        layout.setBackground(background);
        Scene initialScene = new Scene(layout, 200, 200);
        primaryStage.setTitle("Checkers Game");
        primaryStage.getIcons().add(new Image("/images/checkers_icon.jpg"));
        primaryStage.setScene(initialScene);
        primaryStage.setWidth(640);
        primaryStage.setHeight(660);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Set the Actions
        multiplayer.setOnMouseClicked(e -> {
            this.DIFFICULTY = 0;
            startCheckers(primaryStage);
            System.out.println("Multiplayer Game Mode");
        });

        single.setOnMouseClicked(e -> {
            set_difficulty(primaryStage);
            System.out.println("Single Game Mode");
        });

        help_label.setOnMouseClicked(e -> {
            System.out.println("Help me");
            helpScreen();    
        });
    }
   

    /**
     * THIS METHOD CREATES A NEW WINDOW FOR SELECTING THE DIFFICULTY OF THE GAME,
     * BETWEEN: EASY MEDIUM AND HARD.
     * 
     * @param stage
     */
    public void set_difficulty(Stage stage) {
        // Initialise a new Stage
        stage.close();
        Stage diff_stage = new Stage();

        // Menu Bar
        MenuBar menu = new MenuBar();
        Label home_label = new Label("Home");
        Label help_label = new Label("Help");
        Menu help = new Menu("", help_label);
        Menu home = new Menu("", home_label);
        menu.getMenus().addAll(home);
        menu.getMenus().addAll(help);

        // Buttons
        Button easy = new Button("Easy");
        Button medium = new Button("Medium");
        Button hard = new Button("Hard");
        easy.setPrefWidth(220);
        easy.setPrefHeight(70);
        medium.setPrefWidth(220);
        medium.setPrefHeight(70);
        hard.setPrefWidth(220);
        hard.setPrefHeight(70);
        easy.setStyle(button_css);
        easy.setOnMouseEntered(e -> easy.setStyle(hovered_css));
        easy.setOnMouseExited(e -> easy.setStyle(button_css));
        medium.setStyle(button_css);
        medium.setOnMouseEntered(e -> medium.setStyle(hovered_css));
        medium.setOnMouseExited(e -> medium.setStyle(button_css));
        hard.setStyle(button_css);
        hard.setOnMouseEntered(e -> hard.setStyle(hovered_css));
        hard.setOnMouseExited(e -> hard.setStyle(button_css));

        // Add all the components into a VBox
        VBox vbox_buttons = new VBox(3);
        vbox_buttons.getChildren().addAll(easy, medium, hard);
        vbox_buttons.setAlignment(Pos.CENTER);
        vbox_buttons.setSpacing(13);

        // Set layout
        Image img = new Image("/images/background.png");
        BackgroundImage bg = new BackgroundImage(img, NO_REPEAT, NO_REPEAT, CENTER, DEFAULT);
        Background background = new Background(bg);
        BorderPane layout = new BorderPane();
        layout.setTop(menu);
        layout.setCenter(vbox_buttons);
        layout.setBackground(background);
        Scene initialScene = new Scene(layout, 200, 200);
        diff_stage.setTitle("Checkers Game");
        diff_stage.getIcons().add(new Image("/images/checkers_icon.jpg"));
        diff_stage.setScene(initialScene);
        diff_stage.setWidth(640);
        diff_stage.setHeight(660);
        diff_stage.setResizable(false);
        diff_stage.show();

        // Set the Actions
        home_label.setOnMouseClicked(e -> {
            IST_Checkers restart = new IST_Checkers();
            Stage new_home = new Stage();
            diff_stage.close();
            restart.start(new_home);
            System.out.println("Home");
        });

        help_label.setOnMouseClicked(e -> {
            System.out.println("Help");
            helpScreen();
        });
        easy.setOnMouseClicked(e -> {
            System.out.println("Easy");
            this.DIFFICULTY = 1;
            diff_stage.close();
            startCheckers(stage);
        });

        medium.setOnMouseClicked(e -> {
            System.out.println("Medium");
            this.DIFFICULTY = 2;
            diff_stage.close();
            startCheckers(stage);
        });

        hard.setOnMouseClicked(e -> {
            System.out.println("Hard");
            this.DIFFICULTY = 3;
            diff_stage.close();
            startCheckers(stage);
        });
    }

    /**
     * THIS METHOD STARTS A NEW GAME AND SETS UP A NEW BOARD.
     */
    private void startCheckers(Stage stage) {
        // Initialise a new Stage
        stage.close();
        game_stage = new Stage();

        // Menu Bar
        MenuBar menu = new MenuBar();
        Label home_label = new Label("Home");
        Label help_label = new Label("Help");
        Menu help = new Menu("", help_label);
        Menu home = new Menu("", home_label);
        menu.getMenus().addAll(home);
        menu.getMenus().addAll(help);

        //Layout
        BorderPane layout = new BorderPane();
        layout.setTop(menu);

        //Board
        Board board = new Board();
        layout.setCenter(board.createGameScene());
        Scene gameScene = new Scene(layout, 200, 200);
        game_stage.setTitle("Checkers GamePlay");
        game_stage.getIcons().add(new Image("/images/checkers_icon.jpg"));
        game_stage.setResizable(false);
        game_stage.centerOnScreen();
        game_stage.setWidth(720);
        game_stage.setHeight(770);
        game_stage.setScene(gameScene);
        game_stage.show();
       
        // Set the Actions
        home_label.setOnMouseClicked(e -> {
            IST_Checkers restart = new IST_Checkers();
            Stage new_home = new Stage();
            game_stage.close();
            restart.start(new_home);
            System.out.println("Home");
        });

        help_label.setOnMouseClicked(e -> {
            System.out.println("Help");
            helpScreen();
        });
    }
    
    /**
     * THIS METHOD TAKES AS INPUT THE STRING OF THE WINNER'S COLOR AND 
     * POPS UP THE ENDING SCREEN OF THE GAME.
     * @param color 
     */
    public static void winnerScreen(String color){
        
        Stage ending_screen = new Stage();
        ending_screen.centerOnScreen();
        
         //fonts
        Font lblFont = new Font("Verdana", 30);
        Font btnFont = new Font("Verdana", 20);
        
        //labels
        Label label = new Label("CONGRATULATIONS!");
        label.setFont(lblFont);
        label.relocate(40, 10);
        Label label2 = new Label(color + " WON!");
        label2.setFont(lblFont);
        label2.relocate(80, 60);
        label.setTextFill(Color.WHITE);
        label2.setTextFill(Color.WHITE);
        
        // button
        Button playAgain = new Button("Play Again");
        playAgain.setPrefWidth(220);
        playAgain.setPrefHeight(70);
        playAgain.setStyle(button_css);
        playAgain.setOnMouseEntered(e -> playAgain.setStyle(hovered_css));
        playAgain.setOnMouseExited(e -> playAgain.setStyle(button_css));
        playAgain.relocate(100, 120);
        
        // Set Action
        playAgain.setOnAction(e ->  
        {
            IST_Checkers restart = new IST_Checkers();
            Stage new_home = new Stage();
            ending_screen.close();
            game_stage.close();
            restart.start(new_home);   
        });
        
         //pane
        Pane pane =  new Pane();
        pane.getChildren().addAll(label,label2,playAgain);
        
         //layout
        BorderPane layout = new BorderPane();
        layout.setCenter(pane);
        
         //background
        Image img = new Image("/images/background.png");
        BackgroundImage bg_Img = new BackgroundImage(img, NO_REPEAT, NO_REPEAT, 
                                                               CENTER, DEFAULT);
        Background bg = new Background(bg_Img);
        layout.setBackground(bg);
        
        //scene
        Scene scene = new Scene(layout, 400, 200);        
        ending_screen.setTitle("Game End");
        ending_screen.setResizable(false);
        ending_screen.centerOnScreen();
        ending_screen.setScene(scene);
        ending_screen.show();
    }
    
    /**
     * THIS METHOD POPS UP AN ERROR MESSAGE IN CASE OF AN INVALID MOVE
     * OF THE USER.
     */
    public static void showErrorMsg() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Move");
        alert.setHeaderText("\"You made an invalid move!");
        alert.setContentText("You can only move on the highlighted tiles!");
        alert.showAndWait();
    }

    /**
     * THIS METHOD POPS UP THE HELP SCREEN WITH THE RULES OF THE GAME.
     */
    public void helpScreen() {
        
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Help");
        popupwindow.setResizable(false);
        
        Text title = new Text();
        title.setText("Rules of the Game:");
        title.setFont(Font.font("Verdana",20));
        title.setFill(Color.WHITE);
        
        Text text = new Text();
        text.setText(" The objective is to eliminate all opposing checkers or to "
          + "create a situation in which it is  impossible for your opponent "
            + "to make any move. " +
               "Normally, the victory will be due to  complete elimination.\n" +
                "\n" +
                " Non-capturing Move\n" +
                " Blue moves first and play proceeds alternately. From their "
            + "initial positions, checkers may  only move forward. " +
               "\n  There are two types of moves that can be made, capturing "
         + "moves and non-capturing  moves. Non-capturing moves are simply a "
            + "diagonal move forward from one square to an  adjacent square. " +
              "(Note that the white squares are never used.) Capturing moves "
           + "occur when  a player \"jumps\" an opposing piece. This is also "
         + "done on the diagonal and can only happen  when the square behind "
                                     + "(on the same diagonal) is also open. " + 
               "This means that you may not  jump an opposing piece around a "
            + "corner.\n" +
                "\n" +
                " Capturing Move\n" +
            " On a capturing move, a piece may make multiple jumps. If after "
           + "a jump a player is in a  position to make another jump then he "
            + "may do so. " +
             "This means that a player may make several jumps in succession, "
            + "capturing several pieces on  a single turn.\n" +
                "\n" +
                 " Forced Captures: When a player is in a position to make a "
            + "capturing move, he must make a capturing move. " +
                 "When they have more than one capturing move to choose from "
            + "they may take whichever move suits him.\n" +
                "\n"+
                  " When a checker achieves the opponent's edge of the board "
            + "(called the \"king's row\") it is  crowned a king. " +
                     "The  king now gains an added ability to move backward.");
        
        text.setFont(Font.font("Verdana",12));
        text.setFill(Color.WHITE);
        text.setWrappingWidth(290);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(title,text);
        Image img = new Image("/images/background.png");
        BackgroundImage bg_Img = new BackgroundImage(img, NO_REPEAT, NO_REPEAT, 
                                                       CENTER, DEFAULT);
        Background bg = new Background(bg_Img);
        layout.setBackground(bg);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(layout); 
        scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        Scene scene = new Scene(scroll, 295, 300);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }
    
    
    //------------MAIN METHOD--------------
    //-------------------------------------
    
    /**
     * MAIN METHOD OF THE APPLICATION.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
