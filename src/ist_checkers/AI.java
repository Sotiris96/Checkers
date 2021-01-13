package ist_checkers;

//------------IMPORTS--------------
//---------------------------------
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import static javafx.util.Duration.seconds;

/**
 * THIS CLASS REPRESENTS THE AI CLASS OF THE GAME, IT INCLUDES THE MINIMAX
 * ALGORITHM AND THE METHODS USED BY THE AI TO PLAY.
 *
 * @author sa725
 */
public class AI {

    /**
     * MAIN BOARD.
     */
    private Board mainBoard;

    /**
     * MAX DEPTH OF MINIMAX.
     */
    private int maxDepth;

    /**
     * ARRAYLIST WHICH HOLDS THE EVALUATED MOVES.
     */
    private ArrayList<Move> evalueatedMoves = new ArrayList<>();

    /**
     * DELAY USED BY THE ANIMATION.
     */
    private final PauseTransition wait = new PauseTransition(seconds(1));

    // CONSTRUCTOR
    //------------
    /**
     * THIS IS THE CONSTRUCTOR OF THE AI CLASS
     *
     * @param board
     */
    public AI(Board board) {
        this.mainBoard = board;
    }

    /**
     * THIS IS THE PLAY METHOD OF THE AI, IT HANDLES THE DIFFERENT LEVELS OF
     * DIFFICULTY, SETS THE APPROPRIATE DEPTH AND EXECUTES THE MOVES OF THE AI.
     */
    public void play() {

        if (mainBoard.getDifficulty() == 1) {
            ArrayList<Pawn> available_pawns = mainBoard.getPlayerMoves(false);

            // Select randomly an available pawn
            Random random = new Random();
            int random_pawn = random.nextInt(available_pawns.size());
            Pawn p = available_pawns.get(random_pawn);

            // From the selected pawn, select randonmly an available move
            ArrayList<Position> available_moves = p.get_moves();
            int random_move = random.nextInt(available_moves.size());
            Position pos = available_moves.get(random_move);

            // Animation
            p.toFront();
            int x = pos.getX() * 90;
            int y = pos.getY() * 90;

            int initial_x = (p.getCurrent_position().getX()) * 90;
            int initial_y = (p.getCurrent_position().getY()) * 90;

            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(1));
            transition.setByX((x - initial_x));
            transition.setByY((y - initial_y));
            transition.setNode(p);
            transition.play();

            ScaleTransition scale = new ScaleTransition();
            scale.setDuration(Duration.seconds(0.5));
            scale.setByX(0.25);
            scale.setByY(0.25);
            scale.setNode(p);
            scale.setAutoReverse(true);
            scale.setCycleCount(2);
            scale.play();

            if (p.can_kill() == true) {

                wait.setOnFinished(e
                        -> {
                    mainBoard.kill(p, pos);
                });
                wait.play();

            } else {

                wait.setOnFinished(e
                        -> {
                    mainBoard.move(p, pos);
                });
                wait.play();
            }
        } else if (mainBoard.getDifficulty() == 2) {

            //Set depth of the Minimax
            maxDepth = 2;

            // Clear the evaluated Pawns List
            evalueatedMoves.clear();

            // Call minimax algorithm
            minimax(mainBoard, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            // Get best move
            Move b_move = get_best_move();

            // Get Pawn and position
            Pawn p = b_move.getPawn();
            Position pos = b_move.getPosition();

            // Animation
            p.toFront();
            int x = pos.getX() * 90;
            int y = pos.getY() * 90;

            int intial_x = (p.getCurrent_position().getX()) * 90;
            int initial_y = (p.getCurrent_position().getY()) * 90;

            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(1));
            transition.setByX((x - intial_x));
            transition.setByY((y - initial_y));
            transition.setNode(p);
            transition.play();

            ScaleTransition scale = new ScaleTransition();
            scale.setDuration(Duration.seconds(0.5));
            scale.setByX(0.25);
            scale.setByY(0.25);
            scale.setNode(p);
            scale.setAutoReverse(true);
            scale.setCycleCount(2);
            scale.play();

            if (p.can_kill() == true) {

                wait.setOnFinished(e
                        -> {
                    mainBoard.kill(p, pos);
                });
                wait.play();

            } else {

                wait.setOnFinished(e
                        -> {
                    mainBoard.move(p, pos);
                });
                wait.play();
            }

        } else if (mainBoard.getDifficulty() == 3) {

            //Set depth of the Minimax
            maxDepth = 4;

            // Clear the evaluated Pawns List
            evalueatedMoves.clear();

            // Call minimax algorithm
            minimax(mainBoard, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            //Get best move
            Move b_move = get_best_move();

            // Get Pawn and position
            Pawn p = b_move.getPawn();
            Position pos = b_move.getPosition();

            // Animation
            p.toFront();
            int x = pos.getX() * 90;
            int y = pos.getY() * 90;

            int initail_x = (p.getCurrent_position().getX()) * 90;
            int initial_y = (p.getCurrent_position().getY()) * 90;

            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(1));
            transition.setByX((x - initail_x));
            transition.setByY((y - initial_y));
            transition.setNode(p);
            transition.play();

            ScaleTransition scale = new ScaleTransition();
            scale.setDuration(Duration.seconds(0.5));
            scale.setByX(0.25);
            scale.setByY(0.25);
            scale.setNode(p);
            scale.setAutoReverse(true);
            scale.setCycleCount(2);
            scale.play();

            if (p.can_kill() == true) {

                wait.setOnFinished(e
                        -> {
                    mainBoard.kill(p, pos);
                });
                wait.play();

            } else {

                wait.setOnFinished(e
                        -> {
                    mainBoard.move(p, pos);
                });
                wait.play();
            }

        }
    }

    /**
     * THIS IS THE MINIMAX ALGORITHM USED BY THE AI TO EVALAUTE ITS POSSIBLE
     * MOVES.
     *
     * @param board current board
     * @param depth depth of search
     * @param a alpha for a - b pruning
     * @param b beta for a - b pruning
     * @param player indicates the player (mauve - blue)
     * @return A score for each possible move
     */
    private int minimax(Board board, int depth, int a, int b, boolean player) {

        // Exit Condition
        if (depth == 0 || board.finishGame()) {
            return board.evaluate();
        }

        // Maximising Player Mauve
        if (player == false) {

            // Initialise lowest possible value
            int value = Integer.MIN_VALUE;

            // Copy board and get available pawns
            Board parentBoard = new Board(board.getBoard(), player);
            ArrayList<Pawn> available_pawns = parentBoard.getPlayerMoves(false);

            // Create a List with all the available moves
            ArrayList<Move> availableMoves = new ArrayList();
            for (int i = 0; i < available_pawns.size(); i++) {
                Pawn p = available_pawns.get(i);
                ArrayList<Position> moves = p.get_moves();
                for (Position m : moves) {
                    Move move = new Move(p, m);
                    availableMoves.add(move);
                }
            }

            //For every available move of the parent board 
            for (int i = 0; i < availableMoves.size(); i++) {
                Move move = availableMoves.get(i);

                //Create the child board
                Board childBoard = new Board(parentBoard.getBoard(), parentBoard.blue_Turn);

                // Exctract the pawn and the position of the move
                Pawn piece = childBoard.getBoard()[move.getPawn().current_position.getX()][move.getPawn().current_position.getY()].getPawn();
                Position pos = move.getPosition();

                //Perform the capturing or non-capturing move
                if (piece.can_kill()) {
                    childBoard.kill(piece, pos);
                } else {
                    childBoard.move(piece, pos);
                }

                //Update value 
                value = Math.max(value, minimax(childBoard, depth - 1, a, b, childBoard.blue_Turn));

                //Set alpha 
                a = Math.max(a, value);

                // Alpha-beta prunning
                if (a >= b) {
                    break;
                }

                // Evaluate the move
                if (depth == maxDepth) {
                    move.setScore(value);
                    evalueatedMoves.add(move);
                }
            }
            return value;

            // Minimising Player Blue
        } else {

            // Initialise highest possible value       
            int value = Integer.MAX_VALUE;

            // Copy board and get available pawns
            Board parentBoard = new Board(board.getBoard(), true);
            ArrayList<Pawn> available_pawns = parentBoard.getPlayerMoves(true);

            // Create a List with all the available moves
            ArrayList<Move> availableMoves = new ArrayList();
            for (int i = 0; i < available_pawns.size(); i++) {
                Pawn p = available_pawns.get(i);
                ArrayList<Position> moves = p.get_moves();
                for (Position m : moves) {
                    Move move = new Move(p, m);
                    availableMoves.add(move);
                }
            }

            //For every available move of the parent board 
            for (int i = 0; i < availableMoves.size(); i++) {
                Move move = availableMoves.get(i);

                //Make the child board
                Board childBoard = new Board(parentBoard.getBoard(), true);

                // Exctract the pawn and the position of the move
                Pawn piece = childBoard.getBoard()[move.getPawn().current_position.getX()][move.getPawn().current_position.getY()].getPawn();
                Position pos = move.getPosition();

                //Perform the capturing or non-capturing move
                if (piece.can_kill()) {
                    childBoard.kill(piece, pos);
                } else {
                    childBoard.move(piece, pos);
                }

                // Update value
                value = Math.min(value, minimax(childBoard, depth - 1, a, b,
                        childBoard.blue_Turn));

                // Set beta 
                b = Math.min(b, value);

                // Alpha beta pruning
                if (a >= b) {
                    break;
                }
            }
            return value;
        }
    }
    
    
    
 private Move get_best_move()
    {
        // Set the best score as the lowest possible value
        int best_score = Integer.MIN_VALUE;
        
        // Initialise a move
        Move move = null;
        
        //Find the best move
        for (int i=0; i<evalueatedMoves.size(); i++)
        {
            if (evalueatedMoves.get(i).getScore() > best_score)
            {
                best_score = evalueatedMoves.get(i).getScore();
                move = evalueatedMoves.get(i);
            }
        }
        
        // Extract the best move on the main board
        Tile[][] main_board = mainBoard.getBoard();
        
        // Find pawn
        Pawn piece = main_board[move.getPawn().current_position.getX()]
                           [move.getPawn().current_position.getY()].getPawn();
        
        // Find position
        Position pos = new Position(move.getPosition().getX(),move.getPosition().getY());
        
        // Create final best move
        Move best_move = new Move(piece, pos);
        return best_move;
    }
}