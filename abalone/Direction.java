import java.awt.Point;
/**
 * Represents the direction in which spheres can be moved
 * The Point represents the offset how a sphere is moved if you move it in the 
 * named direction
 */
public enum Direction{
    UL(new Point(-1, -1)), //Up left
    UR(new Point(0, -1)),
    L(new Point(-1, 0)),
    R(new Point(1, 0)),
    DL(new Point(-1, 1)),
    DR(new Point(0, 1));

    private final Point p;

    Direction(Point p){
        this.p = p;
    }
    public Point getPoint(){
        return this.p;
    }
}