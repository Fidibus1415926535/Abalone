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