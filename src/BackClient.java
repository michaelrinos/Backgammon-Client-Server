import java.io.IOException;
import java.net.*;

/**
 * BackClient is the main program, it starts the connection to the server
 * and initializes the model proxy and view
 *
 * Usage: java BackClient <I>serverhost</I> <I>serverport</I>
 *          <I>clienthost</I> <I>clientport</I> <I>playername</I>
 *
 * @author Michael Rinos
 */
public class BackClient {

    /**
     * Main program.
     * @exception throws and catches BindException in case the port is in use and all other exceptions caught
     */
    public static void main(String[] args){
        if (args.length != 3) {
            usage();
        }
        String serverhost = args[0];
        int serverport = Integer.parseInt(args[1]);
        String session = args[2];


        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serverhost, serverport));      //Connect to server
            ModelProxy proxy = new ModelProxy(socket);

            BackUI view = BackUI.create(session);                               //Create the view

            proxy.setModelListener(view);                                       //Set the view for the proxy
            view.setViewListener(proxy);                                        //Set the proxy for the view

            proxy.join(null, session);                                          //Initial join
            view.reset();                                                       //Tell Server to set up a board

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Print a usage message and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java BackClient <serverhost> <serverport> <session>");
        System.exit (1);
    }

}