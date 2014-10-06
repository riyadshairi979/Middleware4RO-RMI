
import java.net.Socket;

/**
 *
 * @author Riyad
 */
public class BasicMathClass {

    Socket serverSocket;
    String serverName;
    String methodName;
    int portNumber;
    int objectNumber;
    ComModule cm;

    public BasicMathClass() {
    }

    void setRORInfo(ROR ror) {
        this.serverName = ror.getInternetAddress();
        this.portNumber = ror.getPortNumber();
        this.objectNumber = ror.getObjectNumber();

    }

    void setCommModule(ComModule cm) {
        this.cm = cm;
    }

    double magicAdd(double a, double b) throws ClassNotFoundException {

        double magicResult = 0.;
        this.methodName = "MAGICADD";

        /**
         * internally manage the result through communication module
         */
        cm.openConnection(serverName, portNumber);
        cm.writeObject(objectNumber);
        cm.writeObject(methodName);
        cm.writeObject(a + Client.DELIMITER + b);
        magicResult = (double) cm.readObject();
        cm.closeConnection();

        return magicResult;
    }

    double magicSubstract(double a, double b) throws ClassNotFoundException {

        double magicResult = 0.;
        this.methodName = "MAGICSUB";

        /**
         * internally manage the result through communication module
         */
        cm.openConnection(serverName, portNumber);
        cm.writeObject(objectNumber);
        cm.writeObject(methodName);
        cm.writeObject(a + Client.DELIMITER + b);
        magicResult = (double) cm.readObject();
        cm.closeConnection();

        return magicResult;
    }
}