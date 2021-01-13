package ist_checkers;

//------------IMPORTS--------------
//---------------------------------
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * THIS IS THE CLASS THAT REPRESENTS THE PAWN CLASS OF THE CHECKERS GAME, IT
 * INCLUDES ALL THE PROPERTIES AND THE METHODS OF A PAWN.
 *
 * @author sa725
 */
public class Pawn extends Circle {

    //------------FIELDS---------------
    //---------------------------------
    
    /**
     * STRING REPRESENTING THE COLOR OF THE PAWNS.
     */
    private String color;

    /**
     * POSITION OF THE PAWN.
     */
    Position current_position;

    /**
     * RADIUS OF THE PAWN (GUI).
     */
    private int radius = 28;

    /**
     * DIRECTION OF THE MOVEMENT OF THE PAWN.
     */
    private int dir;

    /**
     * BOOLEAN INDICATING WHETHER A PAWN IS KING OR NOT.
     */
    private boolean king = false;

    /**
     * ARRAYLIST THAT HOLDS THE MOVES OF A PAWN.
     */
    private ArrayList<Position> moves = new ArrayList();

    /**
     * BOOLEAN INDICATING WHETHER A PAWN HAS A KILL MOVE OR NOT.
     */
    private boolean can_kill = false;

    
    //--------------CONSTRUCTORS-------------
    //---------------------------------------
    
    /**
     * THIS IS THE CONSTRUCTOR OF THE PAWN CLAS, IT USED BY THE AI TO MAKE THE
     * DEEP COPY OF THE BOARD AND THE PAWNS.
     *
     * @param color
     * @param current_position
     */
    public Pawn(String color, Position current_position) {
        // Set position
        this.current_position = current_position;

        // Set Color
        this.color = color;

        // Set direction
        if (color.equals("blue")) {
            dir = -1;
        } else {
            dir = 1;
        }
    }

    /**
     * THIS IS THE CONSTRUCTOR OF THE PAWN CLASS, IT USED TO INITIALISE THE
     * PAWNS ON THE MAIN BOARD OF THE APPLICATION.
     *
     * @param color
     * @param current_position
     * @param main_board
     */
    public Pawn(String color, Position current_position, final Board main_board) {

        // Set position
        this.current_position = current_position;

        // Set Color
        this.color = color;

        // Set direction
        if (color.equals("blue")) {
            dir = -1;
        } else {
            dir = 1;
        }

        // Make the GUI of the Tiles
        setCenterX(current_position.getX() * 90 + 90 / 2);
        setCenterY(current_position.getY() * 90 + 90 / 2);
        setRadius(radius);
        setStroke(Color.web("#474a48"));
        setStrokeWidth(4);

        //        Set Actions
        //-------------------------------
        setOnMouseEntered(e
                -> {
            if ((main_board.blue_Turn == true && color == "blue") || (main_board.blue_Turn == false && color == "mauve")) {
                setCursor(Cursor.OPEN_HAND);
            }
        });

        setOnMousePressed(e
                -> {

            if ((main_board.blue_Turn == true && color == "blue") || (main_board.blue_Turn == false && color == "mauve")) {
                setCursor(Cursor.CLOSED_HAND);
                setRadius(radius + 5);
                toFront();
                if (can_kill == true && main_board.kill_found == true) {
                    main_board.highlight_board("kill", moves);
                } else if (main_board.kill_found == false) {
                    main_board.highlight_board("simple", moves);
                }

            }

        });

        setOnMouseDragged(e
                -> {
            if ((main_board.blue_Turn == true && color == "blue") || (main_board.blue_Turn == false && color == "mauve")) {
                setCenterX(e.getSceneX());
                setCenterY(e.getSceneY() - 25);
            }

        });

        setOnMouseReleased(e
                -> {
            if ((main_board.blue_Turn == true && color == "blue") || (main_board.blue_Turn == false && color == "mauve")) {

                // Find new position / Move
                int current_x = (int) Math.floor(e.getX() / 90);
                int current_y = (int) Math.floor(e.getY() / 90);
                Position new_position = new Position(current_x, current_y);

                if (can_kill == true && main_board.kill_found == true) {

                    if (!moves.contains(new_position)) {
                        setRadius(radius);
                        setCursor(Cursor.OPEN_HAND);
                        setCenterX(current_position.getX() * 90 + 90 / 2);
                        setCenterY(current_position.getY() * 90 + 90 / 2);
                        main_board.highlight_board("normal", moves);
                        IST_Checkers.showErrorMsg();
                    } else {
                        setRadius(radius);
                        setCursor(Cursor.OPEN_HAND);
                        setCenterX(current_x * 90 + 90 / 2);
                        setCenterY(current_y * 90 + 90 / 2);
                        main_board.highlight_board("normal", moves);
                        main_board.kill(this, new_position);
                    }

                } else if (can_kill == false && main_board.kill_found == true) {
                    setRadius(radius);
                    setCursor(Cursor.OPEN_HAND);
                    setCenterX(current_position.getX() * 90 + 90 / 2);
                    setCenterY(current_position.getY() * 90 + 90 / 2);
                    main_board.highlight_board("normal", moves);
                } else if (main_board.kill_found == false) {
                    if (!moves.contains(new_position)) {
                        setRadius(radius);
                        setCursor(Cursor.OPEN_HAND);
                        setCenterX(current_position.getX() * 90 + 90 / 2);
                        setCenterY(current_position.getY() * 90 + 90 / 2);
                        main_board.highlight_board("normal", moves);
                        IST_Checkers.showErrorMsg();
                    } else {
                        setRadius(radius);
                        setCursor(Cursor.OPEN_HAND);
                        setCenterX(current_x * 90 + 90 / 2);
                        setCenterY(current_y * 90 + 90 / 2);
                        main_board.highlight_board("normal", moves);
                        main_board.move(this, new_position);
                    }
                }
                setCursor(Cursor.DEFAULT);
            }
        });
    }

    
    //----------------METHODS----------------
    //---------------------------------------
    
    /**
     * THIS METHOD SET A PAWN AS KING.
     */
    public void setKing() {
        king = true;
    }

    /**
     * THIS METHOD RETURN A BOOLEAN INDICATING WHETHER A PAWN IS KING OR NOT
     *
     * @return true if pawn is king, false otherwise
     */
    public boolean isKing() {
        return king;
    }

    /**
     * RETURNS THE COLOR OF THE PAWN.
     *
     * @return color of the pawn
     */
    public String getColor() {
        return color;
    }

    /**
     * RETURNS THE POSITION OF THE PAWN.
     *
     * @return current position of the pawn
     */
    public Position getCurrent_position() {
        return current_position;
    }

    /**
     * SETS THE POSITION OF THE PAWN
     *
     * @param new_position of the pawn
     */
    public void setCurrent_position(Position new_position) {
        this.current_position.setX(new_position.getX());
        this.current_position.setY(new_position.getY());
    }

    /**
     * RETURNS AN ARRAYLIST WITH THE POSSIBLE MOVES OF A PAWN
     *
     * @return possible moves of the pawn
     */
    public ArrayList<Position> get_moves() {
        return moves;
    }

    /**
     * SETS THE POSSIBLE MOVES OF A PAWN
     *
     * @param simple_moves
     */
    public void set_moves(ArrayList<Position> simple_moves) {
        this.moves = simple_moves;
    }

    /**
     * RETURNS THE DIRECTION OF A PAWN
     *
     * @return -1 for mauve, 1 for blue
     */
    public int getDir() {
        return dir;
    }

    /**
     * RETURNS A BOOLEAN INDICATING WHETHER THE PAWN CAN KILL OR NO
     *
     * @return true if can kill, false otherwise
     */
    public boolean can_kill() {
        return can_kill;
    }

    /**
     * SETS THE BOOLEAN THAT INDICATES WHETER THE PAWN CAN KILL OR NOT
     *
     * @param can_kill
     */
    public void set_kill(boolean can_kill) {
        this.can_kill = can_kill;
    }

}