import java.awt.Point;
/**
 * Class represents a move 
 * Each point represents one of the three possible spheres that could be moved
 * dir is the direction
 */
public class Move{
    public final int sphereCount;
    public final Point s1;
    public final Direction dSpheres;
    public final Direction dMove;
    

    /**
     * Constructor
     * dSpheres can be null if there is only one Sphere to be moved
     */
    public Move(int sphereCount, Point s1, Direction dMove, Direction dSpheres){
        this.s1 = s1;
        this.dSpheres = dSpheres;
        this.dMove = dMove;
        this.sphereCount = sphereCount;
    }

    @Override
    public String toString(){
        return "Count: " + sphereCount + " Coords [" + this.s1.x + ", " + this.s1.y + "] " + " Richtung: " + dMove;
    }
}