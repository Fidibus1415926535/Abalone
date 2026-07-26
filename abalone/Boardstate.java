import java.awt.Point;
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
    }

    private void handleMove1(Move m){
        Point oldCoords = m.s1;
        Point offset = m.dMove.getPoint();
        Piece oldPiece = this.state[oldCoords.y][oldCoords.x];
        this.state[oldCoords.y][oldCoords.x] = Piece.EMPTY;
        this.state[oldCoords.y + offset.y][oldCoords.x + offset.x] = oldPiece;
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
                        case OUT -> s += " ";
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