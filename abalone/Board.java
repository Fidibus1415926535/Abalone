import java.util.ArrayList;
import java.awt.Point;
/**
 * Class board manages one specific position and all data that is relevant
 * like for example whos turn it is etc.
 */

public class Board{
    Boardstate state;
    boolean whitesTurn;
    int bOut = 0;
    int wOut = 0;

    public Board(){
        this.whitesTurn = true;  
        this.state = new Boardstate(); 
    }

    public void handleMove (Move m) throws InvalideMoveException{
        ArrayList<Move> moves = getMoves();
        if (moves.contains(m)) {
            state.handleMove(m);
            this.whitesTurn = !whitesTurn;
        }
        else throw new InvalideMoveException();
    }

    private ArrayList<Move> getMoves(){

        ArrayList<Move> list = new ArrayList<>();
        Piece[][] array = state.getArray();

        for (int i = 0; i < array.length; i++){ //Get all moves with 1 sphere
            for (int j = 0; j < array[i].length; j++){
                if ((array[i][j] == Piece.WHITE && whitesTurn )|| (array[i][j] == Piece.BLACK && !whitesTurn)){
                    list.addAll(calculateAllMoves1(array, new Point (i, j)));
                }
            }
        }
        return list;
    }

    /**
     * Adds all possible moves for a specific sphere to an ArrayList. 
     * Moves that are not possible wont be added
     */
    private ArrayList<Move> calculateAllMoves1 (Piece[][] array, Point p){ 

        ArrayList<Move> list = new ArrayList<>();
        Direction[] directions = Direction.values();

        for (Direction d : directions){
            
            Point o = d.getPoint(); //o stands for offset
            Point target = new Point (p.y + o.y, p.x + o.x);
            if (pointOutOfBounds(target)) continue;

            if (array[target.y][target.x] == Piece.EMPTY){
                list.add(new Move(1, p, d, null));
            }
        }
        return list;
    }

    private boolean pointOutOfBounds(Point p){
        if (this.state.getArray().length < p.y || p.y < 0) return true;
        if (this.state.getArray()[p.y].length < p.x || p.x < 0) return true;
        return false;
    }

    @Override
    public String toString(){
        String s = "-------------------------------------\n";
        if (whitesTurn) s += "Weiß ist am Zug\n";
        else s += "Schwarz ist am Zug\n";
        s += "Score: weiße Kugeln raus: " + wOut + " schwarze Kugeln raus: " + bOut + "\n";
        s += this.state.toString();
        s += "-------------------------------------";
        return s;
    }
}