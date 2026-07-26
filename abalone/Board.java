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


    /**
     * Adds the moves for all 1, 2 and 3 Sphere Moves together into an ArrayList
     */
    private ArrayList<Move> getMoves(){

        ArrayList<Move> list = new ArrayList<>();
        Piece[][] array = state.getArray();
        for (int y = 0; y < array.length; y++){ //Get all moves with 1 sphere
            for (int x = 0; x < array[y].length; x++){
                if ((array[y][x] == Piece.WHITE && whitesTurn ) || (array[y][x] == Piece.BLACK && !whitesTurn)){
                    list.addAll(calculateAllMoves1(array, new Point (x, y)));
                }
            }
        }
        for (int i = 0; i < list.size(); i++) System.out.println(list.get(i).toString());
        return list;
    }

    /**
     * Adds all possible moves for a specific sphere to an ArrayList. 
     * Moves that are not possible wont be added
     */
    private ArrayList<Move> calculateAllMoves1 (Piece[][] array, Point p){ 
        //System.out.println(p);
        ArrayList<Move> list = new ArrayList<>();
        Direction[] directions = Direction.values();

        for (Direction d : directions){

            Point o = d.getPoint(); //o stands for offset

            Point target = new Point (p.x + o.x, p.y + o.y); //the point where the sphere will end up
            
            if (!pointOutOfBounds(target) && array[target.y][target.x] == Piece.EMPTY){
                list.add(new Move(1, p, d, null));
            }
        }
        return list;
    }

    private boolean pointOutOfBounds(Point p) {
        Piece[][] array = this.state.getArray();
        if (p.y < 0 || p.y >= array.length) return true;
        if (p.x < 0 || p.x >= array[p.y].length) return true;
        if (array[p.y][p.x] == Piece.OUT) return true;
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