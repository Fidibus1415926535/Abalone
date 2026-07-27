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

            System.out.print("Wie viele Kugeln möchtest du ziehen? (1-3) >>> ");
            int sphereCount = sc.nextInt();

            System.out.print("Sphere1 X >>> ");
            int x = sc.nextInt();

            System.out.print("Sphere1 Y >>> ");
            int y = sc.nextInt();

            Direction dSpheres = null;

            if (sphereCount != 1) {
                System.out.print("In welche Richtung muss man von deiner ersten " +
                "Kugel aus gehen um die anderen zu erreichen? (0: UL, 1: UR, 2: L, 3: R, 4: DL, 5: DR) >>> ");
                dSpheres = Direction.values()[sc.nextInt()];
            } 

            System.out.print("In welche Richtung möchtest du ziehen?" + 
            "(0: UL, 1: UR, 2: L, 3: R, 4: DL, 5: DR) >>> ");

            Direction dMove = Direction.values()[sc.nextInt()];

            Move move = new Move(sphereCount, new Point (x, y), dMove, dSpheres, false);
            System.out.println("EingabeMove: " + move);
            move.transformToSystem();
            try {
                board.handleMove(move);
                System.out.println(this.board.toString());
            }
            catch (InvalideMoveException e){
                System.out.println("Dieser Move ist nicht valide");
            }
        }
    }
}