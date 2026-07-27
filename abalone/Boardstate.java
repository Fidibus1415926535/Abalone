import java.awt.Point;
import java.util.ArrayList;
/**
 * Class Boardstate represents only one specific board state and only the board and the spheres
 * It is one component that makes up a boardstate
 */

public class Boardstate{
    
    private Piece [][] state = {
        {Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT,Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.OUT, Piece.EMPTY, Piece.EMPTY, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.EMPTY, Piece.EMPTY, Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.OUT},
        {Piece.OUT, Piece.EMPTY, Piece.EMPTY ,Piece.EMPTY ,Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.OUT, Piece.EMPTY, Piece.EMPTY, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.EMPTY, Piece.EMPTY, Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT,Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE,Piece.OUT},
        {Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT, Piece.OUT}
    };

    /**
     * Changes the board according to the incomming move. 
     * Only works for moves that are valid. 
     * Invalide moves are sorted out during the process of Board.getMoves();
     */
    public void handleMove(Move m){
        int count = m.sphereCount;
        if (count == 1) handleMove1(m);
        else if (count == 2) handleMove2(m);
        else handleMove3(m);
    }

    private void handleMove1(Move m){
        m.transformToInOut();
        System.out.println(m.toString());
        m.transformToSystem();

        Point oldCoords = m.s1;
        Point offset = m.dMove.getPoint();
        Piece oldPiece = this.state[oldCoords.y][oldCoords.x];
        this.state[oldCoords.y][oldCoords.x] = Piece.EMPTY;
        if (this.state[oldCoords.y + offset.y][oldCoords.x + offset.x] != Piece.OUT){ //Only replaces if target wasnt out
            this.state[oldCoords.y + offset.y][oldCoords.x + offset.x] = oldPiece;
        }
    }

    private void handleMove2(Move m){
        Direction[] s1DirectionsArray = {Direction.L, Direction.UL, Direction.UR};
        Direction[] pushMovesArray = new Direction[2];
        pushMovesArray[0] = m.dSpheres;
        switch(m.dSpheres) {
            case UL -> pushMovesArray[1] = Direction.DR;
            case UR -> pushMovesArray[1] = Direction.DL;
            case L -> pushMovesArray[1] = Direction.R;
            case R -> pushMovesArray[1] = Direction.L;
            case DL -> pushMovesArray[1] = Direction.UR;
            case DR -> pushMovesArray[1] = Direction.UL;
            default -> System.err.println("pushMoves finder failed");
        }

        ArrayList<Direction> s1Directions = new ArrayList<>(3);
        for (Direction d : s1DirectionsArray) s1Directions.add(d);
        ArrayList<Direction> pushMoves = new ArrayList<>(2);
        for (Direction d : pushMovesArray) pushMoves.add(d);

        final Point o = m.dMove.getPoint();
        final Point so = m.dSpheres.getPoint();
        //System.out.println(o);
        //System.out.println(so);

        if (s1Directions.contains(m.dMove)){//If the move is outgoing from sphere 1
            if (!pushMoves.contains(m.dMove)){ // If it is not a push move
                System.out.println(new Point(m.s1.x + so.x, m.s1.y + so.y));
                Move splitMove1 = new Move(1, m.s1, m.dMove, null, true);
                Move splitMove2 = new Move(1, new Point(m.s1.x + so.x, m.s1.y + so.y), m.dMove, null, true);
                handleMove1(splitMove1);
                handleMove1(splitMove2);
            } else { // If it is a push move
                if (this.state[m.s1.y + o.y][m.s1.x + o.x] == Piece.EMPTY){ // Wenn nichts gepushed wird
                    Move splitMove1 = new Move(1, m.s1, m.dMove, null, true);
                    Move splitMove2 = new Move(1, new Point(m.s1.x + so.x, m.s1.y + so.y), m.dMove, null, true);
                    handleMove1(splitMove1);
                    handleMove1(splitMove2);
                } else {
                    Move splitMove1 = new Move(1, new Point(m.s1.x + o.x, m.s1.y + o.y), m.dMove, null, true);
                    Move splitMove2 = new Move(1, m.s1, m.dMove, null, true);
                    Move splitMove3 = new Move(1, new Point(m.s1.x + so.x, m.s1.y + so.y), m.dMove, null, true);
                    handleMove1(splitMove1);
                    handleMove1(splitMove2);
                    handleMove1(splitMove3);
                }
            }
        } else {
            if (!pushMoves.contains(m.dMove)){ // If it is not a push move
                Move splitMove1 = new Move(1, new Point(m.s1.x + so.x, m.s1.y + so.y), m.dMove, null, true);
                Move splitMove2 = new Move(1, m.s1, m.dMove, null, true);
                handleMove1(splitMove1);
                handleMove1(splitMove2);
            } else { // If it is a push move
                if (this.state[m.s1.y + so.y + o.y][m.s1.x + so.x + o.x] == Piece.EMPTY){ // Wenn nichts gepushed wird
                    Move splitMove1 = new Move(1, new Point(m.s1.x + so.x, m.s1.y + so.y), m.dMove, null, true);
                    Move splitMove2 = new Move(1, m.s1, m.dMove, null, true);
                    handleMove1(splitMove1);
                    handleMove1(splitMove2);
                } else {
                    Move splitMove1 = new Move(1, new Point(m.s1.x + o.x + so.x, m.s1.y + o.y + so.y), m.dMove, null, true);
                    Move splitMove2 = new Move(1, new Point(m.s1.x + so.x, m.s1.y + so.y), m.dMove, null, true);
                    Move splitMove3 = new Move(1, m.s1, m.dMove, null, true);
                    handleMove1(splitMove1);
                    handleMove1(splitMove2);
                    handleMove1(splitMove3);
                }
            }
        }
    }

    private void handleMove3(Move m){
        System.out.println("Noch nicht erledigt :(");
    }

    public Piece[][] getArray(){
        return this.state;
    }

    @Override
    public String toString(){
        String s = "\n";
        for (int i = 0; i < state.length; i++){ //rows 
            s += i + ": ";
            for (Piece item : state[i]) {
                if (null == item) {
                    s += "B ";
                } else {
                    switch (item) {
                        case OUT -> s += "O";
                        case EMPTY -> s += "+ ";
                        case WHITE -> s += "W ";
                        default -> s += "B ";
                    }
                }
            }
            s += '\n';
        }
        return s;
    }
}