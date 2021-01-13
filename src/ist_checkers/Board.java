package ist_checkers;

//------------IMPORTS--------------
//---------------------------------

import static ist_checkers.IST_Checkers.DIFFICULTY;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * THIS CLASS REPRESENTS THE BOARD OF THE CHECKERS GAME, IT
 * INCLUDES ALL THE PROPERTIES AND THE METHODS OF THE BOARD.
 * 
 * @author sa725
 */
public class Board {

    //------------FIELDS---------------
    //---------------------------------
    
    /**
     * BOOLEAN INDICATING WHOSE TURN.
     */
    public boolean blue_Turn = true;

    /**
     * BOOLEAN INDICATING WHETHER THERE IS A KILL OR NOT.
     */
    public boolean kill_found = false;

    /**
     * DIFFICULTY OF THE AI.
     */
    private int difficulty;

    /**
     * VARIABLES FOR THW GUI OF THE TILES AND PAWNS.
     */
    private final Group tiles_Group = new Group();
    private final Group pawns_Group = new Group();
    
    /**
     * ARRAYLIST TO HOLD THE BLUE AND MAUVE PAWNS.
     */
    private final  ArrayList<Pawn> bluePawns = new ArrayList();
    private final  ArrayList<Pawn> mauvePawns = new ArrayList();

    /**
     * 2-D ARRAY TO STORE THE BOARD.
     */
    private Tile[][] main_board = new Tile[8][8];
    
    /**
     * AI CLASS.
     */
    private AI ai;
    
    /**
     * IMAGES FOR THE KINGS.
     */
    Image crown_blue = new Image("/images/crown.png");
    Image crown_mauve = new Image("/images/crown_m.png");

    
    //--------------CONSTRUCTORS-------------
    //---------------------------------------
    
    
    /**
     * THIS IS THE CONSTRUCTOR OF THE BOARD CLASS.
     * 
     */
    public Board() {
        //Set up the Difficulty of the Game
        this.difficulty = DIFFICULTY;
        
        // Set up the AI
        this.ai = new AI(this);
    }
    
    /**
     * THIS IS THE CONSTRUCTOR OF THE BOARD CLASS GIVEN A
     * 2-D BOARD, THIS CONSTRUCTOR IS USED BY THE AI IN 
     * ORDER TO MAKE A DEEP COPY OF THE MAIN BOARD
     *
     * @param board
     */
    public Board(Tile[][] board,boolean player) {
        
        // set up the diff paramaeter
        this.difficulty = -1;
        //create the board, tiles and pieces
        blue_Turn = player;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                
                Position pos = new Position(x, y);
                Tile tile = board[x][y];
           
                Tile new_tile = new Tile(x,y);
                this.main_board[x][y]=new_tile;
                
                if (tile.hasPawn()) {
                    
                    //create blue pieces
                    if (tile.getPawn().getColor().equals("blue")) {
                        Pawn pawn = new Pawn("blue", pos);
                        pawns_Group.getChildren().add(pawn);
                        bluePawns.add(pawn);
                        this.main_board[x][y].setPawn(pawn);
                        if (tile.getPawn().isKing()) {
                            pawn.setKing();
                        }
                        if (tile.getPawn().can_kill()) {
                            pawn.set_kill(true);
                        }
                    } else {
                        Pawn pawn = new Pawn("mauve", pos);
                        pawns_Group.getChildren().add(pawn);
                        mauvePawns.add(pawn);
                        this.main_board[x][y].setPawn(pawn);
                        if (tile.getPawn().isKing()) {
                            pawn.setKing();
                        }
                        if (tile.getPawn().can_kill()) {
                            pawn.set_kill(true);
                        }
                    }
                }
            }
        }
        turn(blue_Turn);
    }

    
    //----------------METHODS----------------
    //---------------------------------------
    
    /**
     * THIS METHOD CREATES THE GAME SCENE,SETS UP THE BOARD 
     * AND THE PAWNS OF THE GAME
     *
     * @return pane with the initial scene of the Game.
     */
    public Parent createGameScene() {

        Pane pane = new Pane();
        pane.setPrefSize(8 * 90, 8 * 90);
        pane.getChildren().addAll(tiles_Group, pawns_Group);

        //Initialise the board of the game
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile tile = new Tile(x, y);
                if ((x + y) % 2 == 0) {
                    tile.setFill(Color.web("#E2EDF4"));
                } else {
                    tile.setFill(Color.web("#C0BDDD"));
                }
                main_board[x][y] = tile;
                tiles_Group.getChildren().add(tile);
            }
        }
        //Set the pawns to their initial positions.
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (y >= 5 && (x + y) % 2 != 0) {
                    Position p = new Position(x, y);
                    Pawn pawn = new Pawn("blue", p, this);
                    bluePawns.add(pawn);
                    pawn.setFill(Color.web("#92A1CC"));
                    main_board[x][y].setPawn(pawn);
                    pawns_Group.getChildren().add(pawn);
                }
                if (y <= 2 && (x + y) % 2 != 0) {
                    Position p = new Position(x, y);
                    Pawn pawn = new Pawn("mauve", p, this);
                    mauvePawns.add(pawn);
                    pawn.setFill(Color.web("#916F9D"));
                    main_board[x][y].setPawn(pawn);
                    pawns_Group.getChildren().add(pawn);
                }
            }
        }
        //Sets the first turn 
        turn(blue_Turn);
        
        return pane;
    }

    /**
     * THIS METHOD SETS THE TURN OF THE GAME, IT IS RESPONSIBLE 
     * FOR INDICATING THE POSSIBLE MOVES BY CALLING THE SUCCESSOR
     * 
     * @param blue_Turn boolean indicating the turn of the player
     */
    public void turn(boolean blue_Turn) {

        if (blue_Turn == true) {

            // Set mauve strokes back to normal color
            for (int i = 0; i < mauvePawns.size(); i++) {
                Pawn blue_pawn = mauvePawns.get(i);
                blue_pawn.setStroke(Color.web("#474a48"));
            }
            
            // Find all the available pawns/moves of the blue player
             ArrayList<Pawn> available_blue = getPlayerMoves(blue_Turn);
            
             // indicate which pawns have available moves
            if (kill_found == true) {
             
                for (int i = 0; i < bluePawns.size(); i++) {
                    if (bluePawns.get(i).can_kill() == false) {
                        bluePawns.get(i).setStroke(Color.web("#474a48"));
                    } else {
                        bluePawns.get(i).setStroke(Color.web("#6C0868"));
                    }
                }
            } else {
                for (int i = 0; i < available_blue.size(); i++) {
                    available_blue.get(i).setStroke(Color.web("#BF8DBD"));
                }
            }

        } else {

            // Set blue strokes back to normal color
            for (int i = 0; i < bluePawns.size(); i++) {
                Pawn blue_pawn = bluePawns.get(i);
                blue_pawn.setStroke(Color.web("#474a48"));
            }

            // Find all the available pawns/moves of the player
            ArrayList<Pawn> available_mauve = getPlayerMoves(blue_Turn);

            if (kill_found == true) {

                for (int i = 0; i < mauvePawns.size(); i++) {
                    if (mauvePawns.get(i).can_kill() == false) {
                        mauvePawns.get(i).setStroke(Color.web("#474a48"));
                    } else {
                        mauvePawns.get(i).setStroke(Color.web("#6C0868"));
                    }
                }
            } else {
                for (int i = 0; i < available_mauve.size(); i++) {
                    available_mauve.get(i).setStroke(Color.web("#BF8DBD"));
                }
            }
        }
        if (!blue_Turn && this.difficulty > 0 && finishGame()==false) {
            ai.play();
        }
    }

    /**
     * THIS IS THE SUCCESSOR FUNCTION OF OUR GAME, IT TAKES AS INPUT A PAWN,
     * SETS ITS POSSIBLE MOVES AND  REUTURNS THEM IN AN ARRAYLIST
     *
     * @param pawn
     * @return array-list with its possible new positions
     */
    public ArrayList<Position> successor(Pawn p) {

        // Array to hold the moves of each pawn
        ArrayList<Position> valid_moves = new ArrayList();

        // Array to hold the kills of each pawn
        ArrayList<Position> valid_kills = new ArrayList();

        //                        SIMPLE MOVES
        //----------------------------------------------------------------------
        // Initialise the Simple Pawn moves
        Position m1 = new Position((p.getCurrent_position().getX() + 1),
                (p.getCurrent_position().getY() + (1 * p.getDir())));
        Position m2 = new Position((p.getCurrent_position().getX() - 1),
                (p.getCurrent_position().getY() + (1 * p.getDir())));
        Position m3 = new Position((p.getCurrent_position().getX() + 1),
                (p.getCurrent_position().getY() + (1 * (-1) * p.getDir())));
        Position m4 = new Position((p.getCurrent_position().getX() - 1),
                (p.getCurrent_position().getY() + (1 * (-1) * p.getDir())));

        if (p.isKing() == true) {

            try {
                if (main_board[m3.getX()][m3.getY()].hasPawn() == false) {
                    valid_moves.add(m3);

                }
            } catch (ArrayIndexOutOfBoundsException exception) {
            }

            try {
                if (main_board[m4.getX()][m4.getY()].hasPawn() == false) {
                    valid_moves.add(m4);

                }
            } catch (ArrayIndexOutOfBoundsException exception) {
            }
        }

        try {
            if (main_board[m2.getX()][m2.getY()].hasPawn() == false) {
                valid_moves.add(m2);

            }
        } catch (ArrayIndexOutOfBoundsException exception) {
        }

        try {
            if (main_board[m1.getX()][m1.getY()].hasPawn() == false) {
                valid_moves.add(m1);

            }
        } catch (ArrayIndexOutOfBoundsException exception) {
        }

        //                        KILL MOVES
        //----------------------------------------------------------------------
        if (p.isKing() == true) {
            try {
                if (main_board[m3.getX()][m3.getY()].hasPawn() == true
                        && !(p.getColor().equals(main_board[m3.getX()][m3.getY()].getPawn().getColor()))
                        && main_board[m3.getX() + 1][m3.getY() - (1 * p.getDir())].hasPawn() == false) {
                    Position kill = new Position(m3.getX() + 1, m3.getY() - (1 * p.getDir()));
                    valid_kills.add(kill);
                    p.set_kill(true);
                    kill_found = true;

                }
            } catch (ArrayIndexOutOfBoundsException exception) {
            }

            try {
                if (main_board[m4.getX()][m4.getY()].hasPawn() == true
                        && !(p.getColor().equals(main_board[m4.getX()][m4.getY()].getPawn().getColor()))
                        && main_board[m4.getX() - 1][m4.getY() - (1 * p.getDir())].hasPawn() == false) {

                    Position kill = new Position(m4.getX() - 1, m4.getY() - (1 * p.getDir()));
                    valid_kills.add(kill);
                    p.set_kill(true);
                    kill_found = true;

                }
            } catch (ArrayIndexOutOfBoundsException exception) {
            }
        }

        try {
            if (main_board[m1.getX()][m1.getY()].hasPawn() == true
                    && !(p.getColor().equals(main_board[m1.getX()][m1.getY()].getPawn().getColor()))
                    && main_board[m1.getX() + 1][m1.getY() + (1 * p.getDir())].hasPawn() == false) {
                Position kill = new Position(m1.getX() + 1, m1.getY() + (1 * p.getDir()));
                valid_kills.add(kill);
                p.set_kill(true);
                kill_found = true;

            }
        } catch (ArrayIndexOutOfBoundsException exception) {
        }

        try {
            if (main_board[m2.getX()][m2.getY()].hasPawn() == true
                    && !(p.getColor().equals(main_board[m2.getX()][m2.getY()].getPawn().getColor()))
                    && main_board[m2.getX() - 1][m2.getY() + (1 * p.getDir())].hasPawn() == false) {
                Position kill = new Position(m2.getX() - 1, m2.getY() + (1 * p.getDir()));
                valid_kills.add(kill);
                p.set_kill(true);
                kill_found = true;

            }
        } catch (ArrayIndexOutOfBoundsException exception) {
        }
        
        //   SET THE MOVES TO EACH PAWN AND RETURN THEM
        //---------------------------------------------
        if (valid_kills.size() > 0) {
            p.set_moves(valid_kills);
            return valid_kills;
        } else if (valid_moves.size() > 0) {
            p.set_moves(valid_moves);
            return valid_moves;
        } else {
            return null;
        }
    }


    /**
     * RETURN ALL THE AVAILABLE PAWNS WITH THEIR SET NEW
     * POSITIONS FOR EACH PLAYER GIVEN THE PLAYER'S TURN
     * 
     * @param blue_Turn
     * @return array-list with available pawns
     */
    public ArrayList<Pawn> getPlayerMoves(boolean blue_Turn) {

        // Initialise a new list to save the avaiable pawns
        ArrayList<Pawn> available_pawns = new ArrayList();

        // Find the available pawns of the blue Player
        if (blue_Turn) {
            
            // Call the successor for all the blue pawns
            kill_found = false;
            for (int i = 0; i < bluePawns.size(); i++) {
                Pawn blue_pawn = bluePawns.get(i);
                blue_pawn.get_moves().clear();
                blue_pawn.set_kill(false);
                successor(blue_pawn);
            }
            
            for (int i = 0; i < bluePawns.size(); i++) {

                Pawn blue_pawn = bluePawns.get(i);

                if (blue_pawn.get_moves().size() > 0 && kill_found == false && blue_pawn.can_kill() == false) {
                    available_pawns.add(blue_pawn);

                } else if (blue_pawn.get_moves().size() > 0 && kill_found == true && blue_pawn.can_kill() == true) {
                    available_pawns.add(blue_pawn);
                }
            }
        }
        // Find the available pawns for the mauve Player
        else {
              
            // Call the successor for all the mauve pawns
            kill_found = false;
            for (int i = 0; i < mauvePawns.size(); i++) {
                Pawn blue_pawn = mauvePawns.get(i);
                blue_pawn.get_moves().clear();
                blue_pawn.set_kill(false);
                successor(blue_pawn);
            }
            
            for (int i = 0; i < mauvePawns.size(); i++) {
                Pawn blue_pawn = mauvePawns.get(i);
                if (blue_pawn.get_moves().size() > 0 && kill_found == false && blue_pawn.can_kill() == false) {
                    available_pawns.add(blue_pawn);

                } else if (blue_pawn.get_moves().size() > 0 && kill_found == true && blue_pawn.can_kill() == true) {
                    available_pawns.add(blue_pawn);
                }
            }
        }
        return available_pawns;
    }
    
    
    /**
     * THIS METHOD TAKES AS INPUT A PAWN AND ITS NEW POSITION
     * AND EXECUTES THE SIMPLE MOVE UPDATING BOTH THE GUI AND
     * THE BACKEND
     * 
     * @param p pawn
     * @param new_position position
     */
    public void move(Pawn p, Position new_position) {
        
        // Update the board (backend)
        main_board[p.getCurrent_position().getX()][p.getCurrent_position().getY()].setPawn(null);
        main_board[new_position.getX()][new_position.getY()].setPawn(p);

        // Update the GUI
        if (difficulty == 0) {
            p.setCenterX(new_position.getX() * 90 + 90 / 2);
            p.setCenterY(new_position.getY() * 90 + 90 / 2);
        }

        // Update the position of the pawn
        p.setCurrent_position(new_position);

        // Check for kings
        if (p.getColor().equals("blue") && p.getCurrent_position().getY() == 0) {
            p.setKing();
            p.setFill(new ImagePattern(crown_blue));
        }

        if (p.getColor().equals("mauve") && p.getCurrent_position().getY() == 7) {
            p.setKing();
            p.setFill(new ImagePattern(crown_mauve));

        }
        endingScreen();
        // Change the turn
        blue_Turn = !blue_Turn;
        turn(blue_Turn);
    }

    
    /**
     * THIS METHOD TAKES AS INPUT A PAWN AND ITS NEW POSITION AND EXECUTES THE
     * KILL MOVE UPDATING BOTH THE GUI AND THE BACKEND
     *
     * @param p pawn
     * @param new_position position
     */
    public void kill(Pawn p, Position new_position) {

        //Find the tile with the killed pawn
        Tile killed_tile = main_board[(p.getCurrent_position().getX() + new_position.getX()) / 2][(p.getCurrent_position().getY() + new_position.getY()) / 2];
        
        // Executes the kill move for the blue pawns
        if (blue_Turn) {
            pawns_Group.getChildren().remove(killed_tile.getPawn());
            mauvePawns.remove(killed_tile.getPawn());
            killed_tile.setPawn(null);
            main_board[p.getCurrent_position().getX()][p.getCurrent_position().getY()].setPawn(null);
            main_board[new_position.getX()][new_position.getY()].setPawn(p);

            // Update the position of the pawn
            p.setCurrent_position(new_position);
            p.set_kill(false);

            // Check for extra kill
            for (int i = 0; i < bluePawns.size(); i++) {
                Pawn blue_pawn = bluePawns.get(i);
                blue_pawn.get_moves().clear();
            }
            if(mauvePawns.size()>=1){
                   successor(p);
            }
           
            // Check for King
            if (p.getColor().equals("blue") && p.getCurrent_position().getY() == 0) {
                p.setKing();
                p.setFill(new ImagePattern(crown_blue));
            }

            // Change turn
            if (p.can_kill() == false) {
                blue_Turn = !blue_Turn;
                turn(blue_Turn);
            }
                 
        } 
        
        // Executes the kill move for the mauve pawns
        else {
            pawns_Group.getChildren().remove(killed_tile.getPawn());
            bluePawns.remove(killed_tile.getPawn());
            killed_tile.setPawn(null);
            main_board[p.getCurrent_position().getX()][p.getCurrent_position().getY()].setPawn(null);
            main_board[new_position.getX()][new_position.getY()].setPawn(p);

            // Update the GUI
            if (difficulty == 0) {
                p.setCenterX(new_position.getX() * 90 + 90 / 2);
                p.setCenterY(new_position.getY() * 90 + 90 / 2);
            }

            // Update the position of the pawn
            p.setCurrent_position(new_position);
            p.set_kill(false);
            
            // Check for extra kill
            for (int i = 0; i < mauvePawns.size(); i++) {
                Pawn mauve_pawn = mauvePawns.get(i);
                mauve_pawn.get_moves().clear();
            }   
                 if(bluePawns.size()>=1){
                   successor(p);
            }

            // Check for King
            if (p.getColor().equals("mauve") && p.getCurrent_position().getY() == 7) {
                p.setKing();
                p.setFill(new ImagePattern(crown_mauve));
            }

            // Change turn
            if (p.can_kill() == false) {
                blue_Turn = !blue_Turn;
                turn(blue_Turn);
                
            } else if (p.can_kill() == true && difficulty > 0) {
                ai.play();
                if (p.can_kill() == false) {
                    blue_Turn = !blue_Turn;
                    turn(blue_Turn);
                }
            }
           
        }
         endingScreen();
    }

    /**
     * THIS METHOD TAKES AS INPUT A STRING INDICATING THE TYPE OF 
     * THE MOVEMENT AND HIGHLIGHTS THE CORRESPONDING TILES ON THE
     * BOARD
     *
     * @param color type of movement
     * @param moves array-list with the possible new positions of the pawn
     */
    public void highlight_board(String color, ArrayList<Position> moves) {
        if (color.equals("simple")) {
            for (int i = 0; i < moves.size(); i++) {
                Position m = moves.get(i);
                main_board[m.getX()][m.getY()].setFill(Color.web("#BF8DBD"));
            }
        }

        if (color.equals("kill")) {
            for (int i = 0; i < moves.size(); i++) {
                Position m = moves.get(i);
                main_board[m.getX()][m.getY()].setFill(Color.web("#6C0868"));
            }
        }

        if (color.equals("normal")) {
            for (int i = 0; i < moves.size(); i++) {
                Position m = moves.get(i);
                main_board[m.getX()][m.getY()].setFill(Color.web("#C0BDDD"));
            }
        }
    }
    
    /**
     * THIS METHOD CHECKS WHETHER THE GAME HAS ENDED AND CALLS
     * 
     * @return true if the game has ended, false otherwise
     */
    public boolean finishGame() {

        if (bluePawns.size() == 0) {
            System.out.println("mauve won");
            return true;
        }

        if (mauvePawns.size() == 0) {
            System.out.println("blue won");
            return true;
        }

        if (blue_Turn) {
            if (getPlayerMoves(true).size() == 0) {
                return true;
            }
        } else {
            if (getPlayerMoves(false).size() == 0) {
                return true;
            }
        }
        return false;
    }
    
   /**
    * THIS METHOD POPS UP THE ENDING SCREEN OF THE GAME.
    */ 
    public void endingScreen() {
          boolean winner_found = false;
      
        if (bluePawns.size() == 0 && difficulty != -1 && winner_found == false) {
            System.out.println("mauve won screen");
            IST_Checkers.winnerScreen("MAUVE");
            winner_found = true;
        }

        if (mauvePawns.size() == 0 && difficulty != -1 && winner_found == false) {
            System.out.println("blue won screen");
             IST_Checkers.winnerScreen("BLUE");
             winner_found = true;
        }

        if (blue_Turn && difficulty != -1 && winner_found == false) {
            if (getPlayerMoves(true).size() == 0) {
                System.out.println("mauve won screen");
                IST_Checkers.winnerScreen("MAUVE");
                 winner_found = true;

            }
        } else {
            if (getPlayerMoves(false).size() == 0 && winner_found == false) {
                System.out.println("blue won screen");
                IST_Checkers.winnerScreen("BLUE");
                 winner_found = true;
            }
        }
    }
    
    /**
     * THIS METHOD INCLUDES THE HEURISTICS THAT THEY ARE USED BY THE AI
     * IN ORDER TO EVALUATE THE BOARD AND SELECT THE OPTIMAL MOVEMENT
     * 
     * @return score indicating the score of the current board
     */
    public int evaluate() {

        // Heuristic - 1 Difference between Mauve and Blue Pawns
        int score = 5 * (mauvePawns.size() - bluePawns.size());

        //Heuristic 2 - Number of kings
        for (int i = 0; i < mauvePawns.size(); i++) {
            Pawn mauvePawn = mauvePawns.get(i);
            if (mauvePawn.isKing()) {
                score = score + 5;
            }
        }
        for (int i = 0; i < bluePawns.size(); i++) {
            Pawn bluePawn = bluePawns.get(i);
            if (bluePawn.isKing()) {
                score = score - 5;
            }
        }

        // Extra Heuristics for the Hard Level
        if (DIFFICULTY == 3) {

            // Heuristic 3 - Pawns on edges
            for (int i = 0; i < mauvePawns.size(); i++) {
                Pawn mauvePawn = mauvePawns.get(i);
                if (mauvePawn.getCurrent_position().getX() == 0
                        || mauvePawn.getCurrent_position().getX() == 7) {
                    score = score = score + 1;
                }
            }
            for (int i = 0; i < bluePawns.size(); i++) {
                Pawn bluePawn = bluePawns.get(i);
                if (bluePawn.getCurrent_position().getX() == 0
                        || bluePawn.getCurrent_position().getX() == 7) {
                    score = score - 1;
                }
            }
        }
        return score;
    }

    /**
     * THIS METHOD RETURNS THE 2-D BOARD
     *
     * @return the board stored in a 2D array
     */
    public Tile[][] getBoard() {
        return main_board;
    }

    /**
     * THIS METHOD RETURNS THE DIFFICULTY OF THE GAME
     * 
     * @return integer indicating the difficulty of the game
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * THIS METHOD RETURNS THE BLUE PAWNS OF THE BOARD
     * 
     * @return array-list with the blue pawns
     */
    public ArrayList<Pawn> getBluePawns() {
        return bluePawns;
    }

    /**
     * THIS METHOD RETURNS THE MAUVE PAWNS OF THE BOARD
     * 
     * @return array-list with the mauve pawns
     */
    public ArrayList<Pawn> getMauvePawns() {
        return mauvePawns;
    }
}