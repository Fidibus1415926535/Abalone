/**
 * Class Boardstate represents only one specific board state and only the board and the spheres
 * It is one component that makes up a boardstate
 */

public class Boardstate{
    
    Piece [][] state = {
        {Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK},
        {Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.BLACK},
        {Piece.EMPTY, Piece.EMPTY, Piece.BLACK, Piece.BLACK, Piece.BLACK, Piece.EMPTY, Piece.EMPTY},
        {Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
        {Piece.EMPTY, Piece.EMPTY ,Piece.EMPTY ,Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
        {Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
        {Piece.EMPTY, Piece.EMPTY, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.EMPTY, Piece.EMPTY},
        {Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE},
        {Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE, Piece.WHITE}
    };

    @Override
    public String toString(){
        String s = "\n";
        for (int i = 0; i < 9; i++){ //rows 
            for (int l = 0; l < getAbs(i + 5 - 9); l++) s += " ";// lehrzeichen am Anfang
            for (int j = 0; j < state[i].length; j++){
                if (state[i][j] == Piece.EMPTY) s += "0 ";
                else if (state[i][j] == Piece.WHITE) s += "W ";
                else s += "B ";
            }
            s += '\n';
        }
        return s;
    }

    private int getAbs(int value){
        if (value < 0) return -value;
        return value;
    }
}