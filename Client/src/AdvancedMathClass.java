
import java.net.Socket;

/**
 *
 * @author Riyad
 */
public class AdvancedMathClass {

    Socket serverSocket;
    String serverName;
    int portNum;
    int objectNumber;
    ComModule cm;

    public AdvancedMathClass() {
    }

    void setRORInfo(ROR ror) {
        this.serverName = ror.getInternetAddress();
        this.portNum = ror.getPortNumber();
        this.objectNumber = ror.getObjectNumber();
    }

    void setCommModule(ComModule cm) {
        this.cm = cm;
    }

    double magicFindMin(double a, double b, double c) throws ClassNotFoundException {
        double magicResult = 0.;
        String methodName = "MAGICMIN";

        /**
         * internally manage the result through communication module
         */
        cm.openConnection(serverName, portNum);
        cm.writeObject(objectNumber);
        cm.writeObject(methodName);
        cm.writeObject(a + Client.DELIMITER + b + Client.DELIMITER + c);
        magicResult = (double) cm.readObject();
        cm.closeConnection();

        return magicResult;
    }

    double magicFindMax(double a, double b, double c) throws ClassNotFoundException {
        double magicResult = 0.;
        String methodName = "MAGICMAX";

        /**
         * internally manage the result through communication module
         */
        cm.openConnection(serverName, portNum);
        cm.writeObject(objectNumber);
        cm.writeObject(methodName);
        cm.writeObject(a + Client.DELIMITER + b + Client.DELIMITER + c);
        magicResult = (double) cm.readObject();
        cm.closeConnection();

        return magicResult;
    }
}
