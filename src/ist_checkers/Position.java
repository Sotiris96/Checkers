package ist_checkers;

/**
 * THIS IS THE THE CLASS THE REPRESENTS THE POSSIBLE POSITION OF A PAWN.
 * 
 * @author sa725
 */
public class Position {
    
    //------------FIELDS---------------
    //---------------------------------
    
    /**
     * COORDINATES OF THE POSITION.
     */
    private int x;
    private int y;
    
        
    //--------------CONSTRUCTOR-------------
    //--------------------------------------
    
    /**
     * THIS IS THE CONSTRUCTOR OF THE POSITION CLASS
     * 
     * @param x
     * @param y 
     */
    public Position (int x, int y){
        this.x = x;
        this.y = y;
    }

    
    //----------------METHODS----------------
    //---------------------------------------
    
    /**
     * THIS METHOD RETURNS THE X COORDINATE OF THE
     * POSITION 
     * 
     * @return 
     */
    public int getX() {
        return x;
    }

    /**
     * THIS METHOS RETURNS THE Y COORDINATE OD THE
     * POSITION
     * 
     * @return 
     */
    public int getY() {
        return y;
    }

    /**
     * THIS METHOD SETS THE X COORDINATE OF THE
     * POSITION
     * 
     * @param x 
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * THIS METHOD SETS THE Y COORDINATE OF THE 
     * POSITION
     * 
     * @param y 
     */
    public void setY(int y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object m){
        Position m1 = (Position) m;
        if(m1.x == this.x && m1.y == this.y){
            return true;
        }
        return false;
    }
}
