import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;


/**
 * Class ModelProxy provides the network proxy for the model object in the
 * backgammon Game. The model proxy resides in the client program and
 * communicates with the server program.
 *
 * @author  Michael Rinos
 */
public class ModelProxy implements ViewListener {

// Hidden data members.

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ModelListener modelListener;

// Exported constructors.

    /**
     * Construct a new model proxy.
     *
     * @throws IOException Thrown if an I/O error occurred.
     */
    public ModelProxy(Socket socket) throws IOException {
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

// Exported operations.

    /**
     * Set the model listener object for this model proxy.
     *
     * @param modelListener Model listener.
     */
    public void setModelListener(ModelListener modelListener) {
        this.modelListener = modelListener;
        new ReaderThread().start();
    }

    /**
     * Tells server someone has joined
     */
    @Override
    public void join(ViewProxy proxy, String session) throws IOException {
        out.writeByte('J');
        out.writeUTF(session);
    }

    /**
     * tells server that a player took these numbers from a heap
     *
     * @param x the heap
     * @throws IOException
     */

    @Override
    public void placed(int id, int x) throws IOException {
        out.writeByte('P');
        out.writeByte(id);
        out.writeByte(x);
    }

    /**
     * Tells server to start a new game
     *
     * @throws IOException
     */
    @Override
    public void newgame() throws IOException {
        out.writeByte('N');
    }

    /**
     * Tells server to quit the game
     *
     * @throws IOException
     */
    @Override
    public void quit() throws IOException {
        out.writeByte('Q');
    }

// Hidden helper classes.

    /**
     * Class ReaderThread receives messages from the network, decodes them, and
     * invokes the proper methods to process them.
     */
    private class ReaderThread extends Thread {
        public void run() {

            try {
                for (;;) {
                    int r, c;
                    byte b = in.readByte();
                    switch (b) {
                        case 'I':
                            int id = in.readByte();
                            modelListener.id(id);
                            break;
                        case 'N':
                            id = in.readByte();
                            String name = in.readUTF();
                            modelListener.name(id, name);
                            break;
                        case 'S':
                            id = in.readByte();
                            int score = in.readByte();
                            modelListener.score(id, score);
                            break;
                        case 'H':
                            modelListener.reset();
                            break;
                        case 'M':
                            id = r = in.readByte();
                            r = in.readByte();
                            c = in.readByte();
                            modelListener.move(id, r);
                            break;
                        case 'T':
                            id = in.readByte();
                            modelListener.turn(id);
                            break;
                        case 'W':
                            id = in.readByte();
                            modelListener.win(id);
                            break;
                        case 'Q':
                            modelListener.quit();
                            break;
                        default:
                            System.err.println("Bad message");
                            break;
                    }
                }
            } catch (IOException exc) {
            } finally {
                try {
                    socket.close();
                } catch (IOException exc) {
                }
            }
        }
    }
}


