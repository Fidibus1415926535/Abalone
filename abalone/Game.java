/**
 * Class game manages one entire game and takes inputs and delegates the handling of the inputs
 */
public class Game{
    Board board;
    public Game(){
        board = new Board();
    }
    public void run(){
        System.out.println(board.toString());
    }
}