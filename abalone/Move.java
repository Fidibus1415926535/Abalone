import java.awt.Point;
/**
 * Class represents a move 
 * Each point represents one of the three possible spheres that could be moved
 * dir is the direction
 */
public class Move implements Comparable<Move>{
    public final int sphereCount;
    public final Point s1;
    public final Direction dSpheres;
    public final Direction dMove;
    
    private boolean systemMove;

    /**
     * Constructor
     * dSpheres can be null if there is only one Sphere to be moved
     */
    public Move(int sphereCount, Point s1, Direction dMove, Direction dSpheres, boolean systemMove){
        this.s1 = s1;
        this.dSpheres = dSpheres;
        this.dMove = dMove;
        this.sphereCount = sphereCount;
        this.systemMove = systemMove;
    }

    /**
     * A move can be either in the Input Output form which is prettier to read
     * because the coords ignore some list indexes that are hidden for the player.
     * In the system form the coords display the real list indices.
     */
    public void transformToInOut(){
        if (this.systemMove){
            this.s1.x -= getXOffset();
            this.systemMove = false;
        }
    }

    public void transformToSystem(){
        if (!this.systemMove){
            this.s1.x += getXOffset();
            this.systemMove = true;
        }
    }

    /**
     * Get the x offset for the transformation functions
     */
    private int getXOffset(){
        if (this.s1.y <= 5) return 6 - this.s1.y;
        else return this.s1.y - 4;
    }

    @Override
    public int compareTo(Move other){
        if (this.sphereCount != other.sphereCount) return 1;
        if (this.s1.x != other.s1.x && this.s1.y != other.s1.y) return 2;
        if (this.dSpheres != other.dSpheres) return 3;
        if (this.dMove != other.dMove) return 4;
        if (this.systemMove != other.systemMove){
            System.err.println("Error: Compared System Move with InOutMove");
            return 5;
        }
        return 0;
    }

    @Override
    public String toString(){
        return "Count: " + sphereCount + " Coords [" + this.s1.x + ", " + this.s1.y + "] " + " Richtung: " + dMove;
    }
}