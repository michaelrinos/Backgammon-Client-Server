import java.io.IOException;

/**
 * Class SessionManager maintains the sessions' model objects in the Network Connect Four game server.
 *
 * @author  Michael Rinos
 */
public class SessionManager implements ViewListener {

    // Hidden data members.
    private boolean twoPlayers = false;
    private BackModel model;

// Exported constructors.

    /**
     * Construct a new session manager.
     */
    public SessionManager() {}

// Exported operations.

    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     * @param  session  Session name.
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public synchronized void join(ViewProxy proxy, String session) throws IOException {
        try {
            if (twoPlayers && model.getCount() !=0) {
                this.model.addModelListener(proxy, 1, session);
                proxy.setViewListener(model);
                twoPlayers = false;
            } else {
                this.model = new BackModel();
                this.model.addModelListener(proxy, 0, session);
                proxy.setViewListener(model);
                twoPlayers = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes an element/s off of the board for the current player
     * Exported to the model
     *
     * @throws IOException
     */
    @Override
    public void placed(int id, int x) throws IOException {}

    @Override
    public void roll() throws IOException {

    }

    /**
     * Starts a new game
     * @throws IOException
     */
    @Override
    public void newgame() throws IOException {}

    /**
     * Quits for any player still connected
     * Exported to the model
     * @throws IOException
     */
    @Override
    public void quit() throws IOException {}


}