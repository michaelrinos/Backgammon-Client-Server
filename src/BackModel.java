import java.io.IOException;

/**
 * Class BackModel provides the server-side model object in the Network Game.
 *
 * @author  Michael E. Rinos
 * @version 1
 */
public class BackModel implements ViewListener {

    // Hidden data members.
    private Player p1;
    private Player p2;
    private int pCount = 0;
    private int whosTurn = 0;
    private static final int DRAW = 9;
    private static final int BOARD_ID = 5;
    private static final int NUMHEAPS = 7;
    private static Piece p1Piece = new Piece(0);
    private static Piece p2Piece = new Piece(1);
    private static final int NUMOBJECTS = 6;

// Exported constructors.

    /**
     * Construct a new Go model.
     */
    public BackModel() {}



    public synchronized int getCount(){
        return this.pCount;
    }

// Exported operations.

    /**
     * Add the given model listener to this Nim Model
     * @param  modelListener  Model listener.
     * @param  id the players id
     * @param  name the name of the player
     */
    public synchronized void addModelListener(ModelListener modelListener, int id, String name) throws Exception{
        if (p1 == null){        //First person joins
            p1 = new Player(id, name, modelListener);

            //Tell this player their id name name and score
            p1.getModelListener().id(p1.getId());
            p1.getModelListener().name(p1.getId(), p1.getName());
            p1.getModelListener().score(p1.getId(), p1.getScore());
            pCount+=1;
        }
        else{                   //Second player joins
            p2 = new Player(id, name, modelListener);

            //Tell each player their id name and score as well as their opponents
            p2.getModelListener().id(p2.getId());
            p2.getModelListener().name(p2.getId(), p2.getName());
            p2.getModelListener().score(p2.getId(), p2.getScore());
            p1.getModelListener().name(p2.getId(), p2.getName());
            p1.getModelListener().score(p2.getId(), p2.getScore());
            p2.getModelListener().name(p1.getId(), p1.getName());
            p2.getModelListener().score(p1.getId(), p1.getScore());
            p1.getModelListener().turn(0);
        }
    }

    public synchronized int winner(Player p) {
        return 5;
    }

    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     * @param  session  Session name.
     */
    @Override
    public synchronized void join(ViewProxy proxy, String session) {}


    /**
     * Takes a element/s off of the board for the player who is up
     *
     * @throws IOException
     */
    @Override
    public synchronized void placed(int id, int x) throws IOException {

        //makes the move on each players board.
        if (id == p1.getId()){
            p1.move(x, p1Piece);
            p2.move(x, p1Piece);
        }
        else {
            p1.move(x, p2Piece);
            p2.move(x, p2Piece);
        }


        if (whosTurn == p1.getId()){
            //tells the players the board has changed.
            p1.getModelListener().move(id, x);
            p2.getModelListener().move(id, x);

            int winner = winner(p1);
            if (winner == p1.getId() || winner == p2.getId() || winner == DRAW){
                p1.incScore();
                p1.getModelListener().score(0,p1.getScore());
                p2.getModelListener().score(0,p1.getScore());
                p1.getModelListener().win(winner);
                p2.getModelListener().win(winner);
            }
            else {
                this.whosTurn = 1;
                //tells each player who's turn it is.
                p1.getModelListener().turn(whosTurn);
                p2.getModelListener().turn(whosTurn);
            }
        }
        else {
            //tells the players the board has changed.
            p1.getModelListener().move(id, x);
            p2.getModelListener().move(id, x);

            int winner = winner(p2);
            if (winner == p1.getId() || winner == p2.getId() || winner == DRAW){
                p2.incScore();
                p1.getModelListener().score(1,p2.getScore());
                p2.getModelListener().score(1,p2.getScore());
                p1.getModelListener().win(winner);
                p2.getModelListener().win(winner);
            }
            else {
                this.whosTurn = 0;
                //tells each player who's turn it is.
                p1.getModelListener().turn(whosTurn);
                p2.getModelListener().turn(whosTurn);
            }
        }

    }

    /**
     * resets the board to for both players
     * @throws IOException
     */
    @Override
    public synchronized void newgame() throws IOException {
        whosTurn = 0;
        p1.clearBoard();
        p2.clearBoard();
        p1.getModelListener().reset();
        p2.getModelListener().reset();
        p1.getModelListener().turn(whosTurn);
        p2.getModelListener().turn(whosTurn);
    }

    /**
     * Quits for any player that is still connected
     * @throws IOException
     */
    @Override
    public synchronized void quit() throws IOException {
        if (p1 != null) {
            p1.getModelListener().quit();
        }
        if (p2 != null) {
            p2.getModelListener().quit();
        }
        pCount = 0;
    }
}