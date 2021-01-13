package ist_checkers;

/**
 * THIS IS THE MOVE CLASS, IT IS USED AS A HELP CLASS FOR THE IMPLMENTATION
 * OF THE AI AS IT HOLDS ALL THE INFORMATION OF A MOVE (PAWN,POSSIBLE NEW 
 * POSITION OF THE PAWN)
 * 
 * @author sa725
 */
public class Move {

    //------------FIELDS---------------
    //--------------------------------- 
    
    /**
     * PAWN OF THE MOVE.
     */
    private Pawn pawn;
    
    /**
     * POSSIBLE NEW POSITION OF THE PAWN.
     */
    private Position position;
    
    /**
     * SCORE OF THE MOVEMENT.
     */
    private int score;

    
    //--------------CONSTRUCTORS-------------
    //---------------------------------------
    
    /**
     * THIS IS THE CONSTRUCTOR OF THE MOVE CLASS, IT TAKES AS
     * INPUTS A PAWN AND ITS POSSIBLE NEW POSITION.
     * 
     * @param pawn
     * @param position 
     */
    public Move(Pawn pawn, Position position) {
        this.pawn = pawn;
        this.position = position;
    }

    
    //----------------METHODS----------------
    //---------------------------------------
    
    /**
     * THIS METHOD RETURNS THE PAWN OF THE MOVE.
     * 
     * @return pawn 
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * THIS METHOD RETURNS THE POSITION OF THE MOVE
     * 
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * THIS METHOD RETURNS THE SCORE OF THE MOVE
     * 
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * THIS METHOD SETS THE SCORE OF THE MOVE
     * 
     * @param score 
     */
    public void setScore(int score) {
        this.score = score;
    }

}