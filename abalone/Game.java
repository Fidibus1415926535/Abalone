import java.awt.Point;
import java.util.Scanner;
/**
 * Class game manages one entire game and takes inputs and delegates the handling of the inputs
 */
public class Game{

    Board board;
    Scanner sc = new Scanner(System.in);

    public Game(){
        board = new Board();
    }

    public void run(){
        System.out.println("Startaufstellung\n" + board.toString());
        boolean running = true;
        
        while (running){

            System.out.print("S1 X >>> ");
            int x = sc.nextInt();
            System.out.print("S1 Y >>> ");
            int y = sc.nextInt();
            System.out.print("Direction (0: UL, 1: UR, 2: L, 3: D, 4: DL, 5: DR) >>> ");
            Direction d = Direction.values()[sc.nextInt()];

            try {
                board.handleMove(new Move(new Point (x, y), null, null, d));
            }
            catch (InvalideMoveException e){
                System.out.println("Dieser Move ist nicht valide");
            }
        }
    }
}