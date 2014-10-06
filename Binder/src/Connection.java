
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Riyad
 */
public class Connection {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket binderSocket) {
        this.socket = binderSocket;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }
}
