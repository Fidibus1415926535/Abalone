import java.awt.Point;
/**
 * Represents the direction in which spheres can be moved
 * The Point represents the offset how a sphere is moved if you move it in the 
 * named direction
 * The first point represents the vector if the sphere is in the top half of the board
 * The second point for the bottom half
 */
public enum Direction{
    UL(new Point(-1, -1), new Point(0, -1)), //Up left
    UR(new Point(0, -1), new Point(1, -1)),
    L(new Point(-1, 0), new Point(-1, 0)),
    R(new Point(1, 0), new Point(1, 0)),
    DL(new Point(0, 1), new Point(-1, 1)),
    DR(new Point(1, 1), new Point(0, 1));

    private final Point p1; //for a boardstate y <= 5
    private final Point p2; //for y >5

    Direction(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }
    public Point getPoint(int y){
        if (y <= 5) return this.p1;
        else return this.p2;
    }
}