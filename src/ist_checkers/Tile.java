package ist_checkers;

//------------IMPORTS--------------
//---------------------------------

import javafx.scene.Cursor;
import javafx.scene.shape.Rectangle;

/**
 * THIS IS THE CLASS THAT REPRESENTS THE TILE OF THE CHECKERS GAME,
 * IT INCLUDES ALL THE PROPERTIES AND THE METHODS OF A TILE.
 *
 * @author sa725
 */
public class Tile extends Rectangle {

    //------------FIELDS---------------
    //---------------------------------
    
    /**
     * BOOLEAN INDICATING WHETHER THIS TILE HAS A PAWN OR NOT.
     */
    private boolean hasPawn;

    /**
     * COORDINATES OF THE TILE.
     */
    private double x;
    private double y;

    /**
     * POSSIBLE PAWN THAT THIS TILE MAY HAVE.
     */
    private Pawn pawn;

    
    //--------------CONSTRUCTOR-------------
    //--------------------------------------
    
    /**
     * THIS IS THE CONSTRUCTOR OF THE TILE CLASS
     *
     * @param x
     * @param y
     */
    public Tile(double x, double y) {

        // Set coordinates
        this.x = x;
        this.y = y;

        // Make the GUI of the Tiles
        setWidth(90);
        setHeight(90);
        relocate(x * 90, y * 90);
    }

    //----------------METHODS----------------
    //---------------------------------------
    
    /**
     * RETURN A BOOLEAN INDICATING WHETHER THIS TILE HAS A PAWN OR NOT.
     *
     * @return true if has a pawn false otherwise
     */
    public boolean hasPawn() {
        return pawn != null;
    }

    /**
     * RETURN THE X COORIDANATE OF THE TILE
     *
     * @return x coordinate
     */
    public double returnX() {
        return x;
    }

    /**
     * RETURN THE Y COORDINATE OF THE TILE
     *
     * @return y coordinate
     */
    public double returnY() {
        return y;
    }

    /**
     * RETURN THE POSSIBLE PAWN OF THE TILE
     *
     * @return the pawn that may be on tile
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * SET A PAWN ON THE TILE
     *
     * @param pawn
     */
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
}