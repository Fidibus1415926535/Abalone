import java.awt.Point;
public class Main{
    public static void main(String [] args){
        Game game = new Game();
        game.run();
        //movetests();
    }

    private static void movetests(){
        Move move = new Move(1, new Point(2, 6) , Direction.UL, null, false);
        System.out.println(move);
        move.transformToSystem();
        System.out.println(move);
        move.transformToInOut();
        System.out.println(move);
    }
}