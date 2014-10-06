
import java.io.*;
import java.net.*;

/**
 *
 * @author Riyad
 */
public class ComModule {

    ObjectOutputStream out;
    ObjectInputStream in;
    ServerSocket providerSocket = null;
    Socket connection = null;
    
    /**
     * opens a socket
     */
    public void openBinderSocket(Binder binder) throws ClassNotFoundException {
        try {
            providerSocket = new ServerSocket(Binder.BINDER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * accepts connection and creates input-output stream
     * and returns a Connection object
     */
    public Connection acceptConnection() {
       Connection cp = new Connection();
        try {
            System.out.println("Binder status: waiting on port " + Binder.BINDER_PORT);
            cp.setSocket(providerSocket.accept());
            cp.setOut(new ObjectOutputStream(cp.getSocket().getOutputStream()));
            cp.getOut().flush();
            cp.setIn(new ObjectInputStream(cp.getSocket().getInputStream()));

            System.out.println("Binder update: new connection received from " + cp.getSocket().getInetAddress().getHostName());
        } catch (IOException ex) {
            System.err.println("Could not accept connection on port " + Binder.BINDER_PORT);
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