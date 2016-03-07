import java.awt.*;
import java.util.ArrayList;

/**
 * Created by michael on 5/9/2015.
 */
public class Piece {
    private int owner;
    private Color color;

    public Piece(int owner){
        this.owner = owner;
        if (owner ==0 ){
            color = new Color(255,245,195);
        }
        else if (owner == 1){
            this.color = new Color(61,61,61);
        }
        else {
            color=Color.gray;
        }
    }

    public int getOwner() {
        return owner;
    }

    public Color getColor() {
        return this.color;
    }
}
