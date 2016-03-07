import java.net.*;
import java.util.HashMap;

/**
 * Class BackServer is the server main program for the Network Go Game. The
 * command line arguments specify the host and port to which the server should
 * listen for connections.
 * <P>
 * Usage: java BackServer <I>host</I> <I>port</I>
 *
 * @author  Michael Rinos

 */
public class BackServer
{

    private static HashMap<SocketAddress,ViewProxy> proxyMap = new HashMap<>();

    /**
     * Main program.
     *@exception `1 Exception thrown
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) usage();

        String host = args[0];
        int port = Integer.parseInt (args[1]);

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(host, port));

        SessionManager sessionManager = new SessionManager();

        for (;;) {
            Socket socket = serverSocket.accept();
            ViewProxy proxy = proxyMap.get(socket);
            if (proxy == null) {
                proxy = new ViewProxy(socket);
                proxy.setViewListener(sessionManager);
                proxyMap.put(socket.getRemoteSocketAddress(), proxy);
            }

        }
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java BackServer <host> <port>");
        System.exit (1);
    }

}
