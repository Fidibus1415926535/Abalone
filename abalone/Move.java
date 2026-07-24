import java.awt.Point;
/**
 * Class represents a move 
 * Each point represents one of the three possible spheres that could be moved
 * dir is the direction
 */
public class Move{
    public final Point s1;
    public final Point s2;
    public final Point s3;
    public final Direction d;
    
    public Move(Point s1, Point s2, Point s3, Direction d){
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.d = d;
    }

    public int getSphereCount(){
        if (this.s2 == null) return 1;
        else if (this.s3 == null) return 2;
        else return 3;
    }
}