import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


/**
 * Class ViewProxy provides the network proxy for the view object in the Network.
 * The view proxy resides in the server program and communicates with
 * the client program.
 *
 * @author  Michael Rinos
 */
public class ViewProxy implements ModelListener {

    // Hidden data members.
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ViewListener viewListener;

// Exported constructors.

    /**
     * Construct a new view proxy.
     *
     * @param  socket
     */
    public ViewProxy(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            new ReaderThread().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Exported operations.

    /**
     * Set the view listener object for this view proxy.
     *
     * @param  viewListener  View listener.
     */
    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }


    // Implemented methods
    @Override
    public void id(int id) throws IOException {
        out.writeByte('I');
        out.writeByte(id);
    }

    @Override
    public void name(int id, String name) throws IOException {
        out.writeByte('N');
        out.writeByte(id);
        out.writeUTF(name);
    }

    @Override
    public void score(int id, int Score) throws IOException {
        out.writeByte('S');
        out.writeByte(id);
        out.writeByte(Score);
    }

    @Override
    public void reset() throws IOException {
        out.writeByte('H');
    }

    @Override
    public void distance(int die1, int die2, int howFar) throws IOException {
        out.writeByte('D');
        out.writeByte(die1);
        out.writeByte(die2);
        out.writeByte(howFar);
    }

    @Override
    public void move(int id, int x) throws IOException {
        out.writeByte('M');
        out.writeByte(id);
        out.writeByte(x);
    }

    @Override
    public void turn(int id) throws IOException {
        out.writeByte('T');
        out.writeByte(id);
    }

    @Override
    public void win(int id) throws IOException {
        out.writeByte('W');
        out.writeByte(id);
    }

    @Override
    public void quit() throws IOException {
        out.writeByte('Q');
    }

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
                        case 'J':
                            String session = in.readUTF();
                            viewListener.join(ViewProxy.this, session);
                            break;
                        case 'P':
                            int id = in.readByte();
                            r = in.readByte();
                            viewListener.placed(id,r);
                            break;
                        case 'R':
                            viewListener.roll();
                            break;
                        case 'N':
                            viewListener.newgame();
                            break;
                        case 'Q':
                            viewListener.quit();
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