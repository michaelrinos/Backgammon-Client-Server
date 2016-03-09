import java.io.IOException;

/**
 * Created by michael on 6/11/2015.
 * Updated by michael on 2//8/2016
 */
public interface ViewListener {

    /**
     * Sent to the server to report one of the player's has joined.
     * <proxy> is replaced with the instance of the ViewProxy
     * <name> is replaced with that player's name.
     * @param proxy the instance of the ViewProxy
     * @param name the players name
     * @throws IOException
     */
    public void join(ViewProxy proxy, String name) throws IOException;

    /**
     * Sent to the server to report one of the player's has placed a piece.
     * <id> is replaced with the players id
     * <x> is replaced with that player's move in the x-axis.
     * <y> is replaced with that player's move in the y-axis.
     * @param id the players id
     * @param x that player's move in the x-axis.
     * @param x that player's move in the x-axis.
     * @throws IOException
     */
    public void placed(int id, int x) throws IOException;

    /**
     * Sent to the server to tell it to roll the dice
     * @throws IOException
     */
    public void roll() throws IOException;


    /**
     * Sent to the server to report the start of a new game.
     * @throws IOException
     */
    public void newgame() throws IOException;

    /**
     * Sent to the server to report one of the player's has left and shut everything down.
     * @throws IOException
     */
    public void quit() throws IOException;
}