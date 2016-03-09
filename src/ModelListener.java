import java.io.IOException;

/**
 * Created by michael on 6/11/2015.
 * Updated by michael on 2//8/2016
 */
public interface ModelListener {

    /**
     * Sent to each client to report their id.
     * <id> is replaced with the ID of either themselves or their opponent
     * @param id the players id
     * @throws IOException
     */
    public void id(int id) throws IOException;

    /**
     * Sent to each client to report one of the player's names.
     * <i> is replaced with the ID of the player whose name is being reported.
     * <n> is replaced with that player's name.
     * @param id the players id
     * @param name the players name
     * @throws IOException
     */
    public void name(int id, String name) throws IOException;

    /**
     * Sent to each client to report one of the player's score.
     * <id> is replaced with the ID of the player whose score is being reported.
     * <score> is replaced with that player's score.
     * @param id the players id
     * @param score the players name
     * @throws IOException
     */
    public void score(int id, int score) throws IOException;

    /**
     * Resets the game board usually when the "new game" button is pressed
     * @throws IOException
     */
    public void reset() throws IOException;

    /**
     * Sent to each client to report how many spaces they may move
     * @param die1 value of the first die
     * @param die2 value of the second die
     * @param howFar
     * @throws IOException
     */
    public void distance(int die1, int die2, int howFar) throws IOException;

    /**
     * Sent to each client to report one of the player's move.
     * <id> is replaced with the ID of the player whose move is being reported.
     * <x> is replaced with that player's move in the x-axis.
     * <y> is replaced with that player's move in the x-axis.
     * @param id the players id
     * @param x that player's move in the x-axis.
     * @throws IOException
     */
    public void move(int id, int x) throws IOException;

    /**
     * Sent to each client to report one of the player's turn.
     * <id> is replaced with the ID of the player whose turn it is
     * @param id the players id
     * @throws IOException
     */
    public void turn(int id) throws IOException;

    /**
     * Sent to each client to report one of the player's victory.
     * <id> is replaced with the ID of the player whose victory is being reported.
     * @param id the players id
     * @throws IOException
     */
    public void win(int id) throws IOException;

    /**
     * Sent to each client to report one of the player's surrender.
     * @throws IOException
     */
    public void quit() throws IOException;
}
