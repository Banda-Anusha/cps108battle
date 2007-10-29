import java.io.*;
import java.net.*;


/**
 * 
 * The ServerStart class starts a per-client thread, GameHandler, to handle
 * communication with Clients/Views.
 * <P>
 * The GameHandler currently uses one game, created by this class, ServerStart.
 * It should be possible to create new games when a game is full, for example,
 * or when enough time has elapsed since the first Client was added to the game.
 * <P>
 * All Client->Server communication is handled by the per-Client GameHandler
 * class which has the input-stream associated with each Client. Outgoing
 * communication is handled by the Game object which either broadcasts
 * information to all Clients or sends per-Client information as appropriate.
 * The Game class maintains a map of Client->OutputStream pairs for this
 * Server->Client communication.
 * <P>
 * Note that there is then one thread per client on the server side, though all
 * handler threads share some common resources such as a game.
 * 
 * @author Owen Astrachan
 * 
 */

public class BattleServer extends Thread {
    private int myRequestCount;          // # requests handled
    private ServerSocket myServerSocket; // client request source
    private BattleQueue myQueueServer;

    public BattleServer(int port) {
        myRequestCount = 0;
        try {
            myServerSocket = new ServerSocket(port);
            myQueueServer = new BattleQueue();
            myQueueServer.start();
            System.err.println("Server Started");
        } catch (Exception e) {
            System.err.println("Server Error: " + e);
        }
    }

    public void run() {
        while (true) {

            try {
                // accept blocks until request comes over socket
                Socket sock = myServerSocket.accept();
                myQueueServer.add(sock);
                System.out.println("Serviced client #" + myRequestCount);
                myRequestCount++;
            } catch (Exception e) {
                System.err.println("trouble with server socket " + e);
                System.err.println("server going down...");
                System.exit(0);
            }
        }
    }

    public static void main(String args[]) {
        BattleServer server = null;
		try {
			server = new BattleServer(Integer.parseInt(args[0]));
		} catch (NumberFormatException e) {
			System.err.println("Error: \""+args[0]+"\" is not a valid port number!");
			System.exit(0);
		}
        server.start();
    }
}