
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Riyad
 */
public class ComModule {

    ObjectOutputStream out = null;      // one stream active at a time
    ObjectInputStream in = null;        // one stream active at a time
    Socket binderSocket = null;

    /**
     * opens a socket
     */
    public void openConnection(String address, int port) {

        try {
            binderSocket = new Socket(address, port);
            out = new ObjectOutputStream(binderSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(binderSocket.getInputStream());
        } catch (IOException ex) {
            System.err.println("Could not connect on port " + Client.BINDER_PORT);
            System.exit(1);
        }
    }

    /**
     * close a connection and open streams associated with that connection
     */
    void closeConnection() {
        try {
            binderSocket.close();
            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * read an object from the stream
     */
    Object readObject() {
        try {
            return in.readObject();
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
    void writeObject(Object obj) {
        try {
            out.writeObject(obj);
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
