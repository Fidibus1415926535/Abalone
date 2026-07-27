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
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).compareTo(m) == 0){
                state.handleMove(m);
                this.whitesTurn = !whitesTurn;
                return;
            }
        }
        throw new InvalideMoveException();
    }


    /**
     * Adds the moves for all 1, 2 and 3 Sphere Moves together into an ArrayList
     */
    private ArrayList<Move> getMoves(){

        ArrayList<Move> list = new ArrayList<>();
        Piece[][] array = state.getArray();

        //Get all moves with 1 sphere
        for (int y = 0; y < array.length; y++){ 
            for (int x = 0; x < array[y].length; x++){
                if ((array[y][x] == Piece.WHITE && whitesTurn ) || (array[y][x] == Piece.BLACK && !whitesTurn)){
                    list.addAll(calculateAllMoves1(array, new Point (x, y)));
                }
            }
        }

        //Get all moves with 2 spheres
        for (int y = 0; y < array.length; y++){ 
            for (int x = 0; x < array[y].length; x++){
                if ((array[y][x] == Piece.WHITE && whitesTurn ) || (array[y][x] == Piece.BLACK && !whitesTurn)){
                    list.addAll(getPairandCalculateMove(array, new Point (x, y)));
                }
            }
        }

        /*
        for (int i = 0; i < list.size(); i++) {
            list.get(i).transformToInOut();
            System.out.println(list.get(i).toString());
            list.get(i).transformToSystem();
        }
        */        

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
                list.add(new Move(1, p, d, null, true));
            }
        }
        return list;
    }

    /**
     * For each Point where a sphere of the current players color has been found,
     * it searches for a second sphere of the same color while only applying
     * downwards vectors or the right vector to the sphere to prevent adding the 
     * same moves multiple times. Like the calculateAllMoves1() function, all found
     * moves are returned in an arraylist
     */
    private ArrayList<Move> getPairandCalculateMove(Piece [][] array, Point p){
        ArrayList<Move>list = new ArrayList<>();
        Direction[] directions = {Direction.R, Direction.DL, Direction.DR};
        for (Direction d : directions){
            Point o = d.getPoint();
            Piece neighbour = array[p.y + o.y][p.x + o.x]; //for shorter line defined here
            if (neighbour == Piece.WHITE && whitesTurn || neighbour == Piece.BLACK && !whitesTurn){
                list.addAll(calculateAllMoves2(array, p, new Point(p.x + o.x, p.y + o.y), d));
            }
        }
        return list;
    }

    private ArrayList<Move> calculateAllMoves2(Piece[][] array, Point s1, Point s2, Direction connector){
        ArrayList<Move>list = new ArrayList<>();
        Direction[] directions = Direction.values();

        Direction[] pushMoves = new Direction[2];
        pushMoves[0] = connector;
        switch(connector) {
            case UL -> pushMoves[1] = Direction.DR;
            case UR -> pushMoves[1] = Direction.DL;
            case L -> pushMoves[1] = Direction.R;
            case R -> pushMoves[1] = Direction.L;
            case DL -> pushMoves[1] = Direction.UR;
            case DR -> pushMoves[1] = Direction.UL;
            default -> System.err.println("pushMoves finder failed");
        }

        for (Direction d : directions){
            if (d != pushMoves[0] && d != pushMoves[1]){ //Not a Push Move
                Point o = d.getPoint();
                if (array[s1.y + o.y][s2.x + o.x] == Piece.EMPTY){
                    if (array[s1.y + o.y][s2.x + o.x] == Piece.EMPTY){
                        list.add(new Move(2, s1, d, connector, true));
                    }
                }
            } else { // Push Move
                //s1 moves first. Works because of 2nd sphere search only searches right and downwards
                if (d == Direction.UL || d == Direction.UR || d == Direction.L){ //Scanning from s1 
                    Point o = d.getPoint();
                    if (array[s1.y + o.y][s1.x + o.x] == Piece.EMPTY){ // empty field: Move is allowed
                        list.add(new Move(2, s1, d, connector, true));
                    } else { // not an empty filed: Move is only allowed if a push works
                        if (array[s1.y + o.y][s1.x + o.x] == Piece.BLACK && whitesTurn
                        || array[s1.y + o.y][s1.x + o.x] == Piece.WHITE && !whitesTurn){
                            if (array[s1.y + o.y + o.y][s1.x + o.x + o.x] == Piece.EMPTY
                                || array[s2.y + o.y + o.y][s2.x + o.x + o.x] == Piece.OUT){ //needs buffer zone 
                                list.add(new Move(2, s1, d, connector, true));
                            }
                        }
                    }
                } else { // Scanning from s2
                    Point o = d.getPoint();
                    if (array[s2.y + o.y][s2.x + o.x] == Piece.EMPTY){ // empty field: Move is allowed
                        list.add(new Move(2, s1, d, connector, true));
                    } else { // not an empty filed: Move is only allowed if a push works
                        if (array[s2.y + o.y][s2.x + o.x] == Piece.BLACK && whitesTurn
                        || array[s1.y + o.y][s1.x + o.x] == Piece.WHITE && !whitesTurn){
                            if (array[s2.y + o.y + o.y][s2.x + o.x + o.x] == Piece.EMPTY
                                || array[s2.y + o.y + o.y][s2.x + o.x + o.x] == Piece.OUT ){ //may need buffer zone 
                                list.add(new Move(2, s1, d, connector, true));
                            }
                        }
                    }
                }
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