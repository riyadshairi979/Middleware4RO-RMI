
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;

/**
 *
 * @author Riyad
 */
public class ComModule {

    ServerSocket remoteListener = null;

    /**
     * opens a socket
     */
    public void openServerSocket() {
        try {
            remoteListener = new ServerSocket(Server.SERVER_PORT);
        } catch (IOException e) {
            System.err.println("Could not listen on port " + Server.SERVER_PORT);
            System.exit(1);
        }
    }

    /**
     * opens a socket connection and create input-output stream
     * and returns a Connection object
     */
    public Connection openConnection(String address, int port) {
        Connection cp = new Connection();
        try {
            cp.setSocket(new Socket(address, port));
            Server.SERVER_HOST_NAME = cp.getSocket().getInetAddress().getHostName();
            cp.setOut(new ObjectOutputStream(cp.getSocket().getOutputStream()));
            cp.getOut().flush();
            cp.setIn(new ObjectInputStream(cp.getSocket().getInputStream()));

            System.out.println("Server update: new connection created with " + address);
        } catch (IOException ex) {
            System.err.println("Could not open connection on port " + port);
            System.exit(1);
        }
        return cp;
    }

    /**
     * accepts connection and create input-output stream
     * and returns a Connection object
     */
    public Connection acceptConnection() {
        Connection cp = new Connection();
        try {
            cp.setSocket(remoteListener.accept());
            cp.setOut(new ObjectOutputStream(cp.getSocket().getOutputStream()));
            cp.getOut().flush();
            cp.setIn(new ObjectInputStream(cp.getSocket().getInputStream()));

            System.out.println("Server update: new connection received from " + cp.getSocket().getInetAddress().getHostName());
        } catch (IOException ex) {
            System.err.println("Could not accept connection on port " + Server.SERVER_PORT);
            System.exit(1);
        }
        return cp;
    }

    /**
     * close a connection and open streams associated with that connection
     */
    void closeConnection(Connection cp) {
        try {
            cp.getSocket().close();
            cp.getOut().close();
            cp.getIn().close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * read an object from the stream
     */
    Object readObject(Connection cp) {
        try {
            return cp.getIn().readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * write an object in the stream
     */
    void writeObject(Object obj, Connection cp) {
        try {
            cp.getOut().writeObject(obj);
            cp.getOut().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
