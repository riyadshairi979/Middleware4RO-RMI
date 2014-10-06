
import java.io.IOException;

/**
 *
 * @author Riyad
 */
public class Server {

    public static final int NUM_OBJECTS = 4;
    public static int BINDER_PORT = 6543;
    public static int SERVER_PORT = 2012;
    public static final int REFERENCE_BASE = 7000;
    public static final int CMD_COUNTER = -1;
    public static String BINDER_HOST_NAME = "127.0.0.1";
    public static String SERVER_HOST_NAME = "127.0.0.1";
    public static final String CMD_REGISTER = "register";
    public static final String DELIMITER = "__";
    public static final String BMO_ONE = "BMOONE";
    public static final String BMO_TWO = "BMOTWO";
    public static final String AMO_ONE = "AMOONE";
    public static final String AMO_TWO = "AMOTWO";
    public static final String MAGIC_ADD = "MAGICADD";
    public static final String MAGIC_SUB = "MAGICSUB";
    public static final String MAGIC_MAX = "MAGICMAX";
    public static final String MAGIC_MIN = "MAGICMIN";
    RRM rrm;
    ComModule cm;

    /**
     * server initializes RRM and Communication module
     * creates remote objects and complete registration with the binder
     * and starts listening to the port
     */
    public Server() {
        rrm = new RRM();
        cm = new ComModule();
        createRemoteObjects();

        try {
            registration();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        startListening();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Server \"server_port\" \"binder_ip\" \"binder_port\"");
            System.exit(1);
        }
        Server.SERVER_PORT = Integer.parseInt(args[0]);
        Server.BINDER_HOST_NAME = args[1];
        Server.BINDER_PORT = Integer.parseInt(args[2]);
        System.out.println("Server started");
        Server server = new Server();
    }

    /**
     * remote object creation which are in RRM
     */
    private void createRemoteObjects() {
        rrm.bmo1 = new BasicMathClass();
        rrm.bmo2 = new BasicMathClass();
        rrm.amo1 = new AdvancedMathClass();
        rrm.amo2 = new AdvancedMathClass();
    }

    /**
     * registration method used to register the services with binder
     */
    private void registration() throws IOException, ClassNotFoundException {
        Connection cp = cm.openConnection(Server.BINDER_HOST_NAME, Server.BINDER_PORT);
        cm.writeObject(Server.CMD_REGISTER, cp);
        cm.writeObject(Server.NUM_OBJECTS, cp);
        for (int i = Server.REFERENCE_BASE; i < Server.REFERENCE_BASE + Server.NUM_OBJECTS; i++) {
            cm.writeObject(rrm.getObjectName(i), cp);
            cm.writeObject(rrm.getROR(rrm.getObjectName(i)), cp);
            System.out.println("service: " + rrm.getObjectName(i) + " -> object#: " + rrm.getROR(rrm.getObjectName(i)).getObjectNumber());
        }
        cm.closeConnection(cp);
        System.out.println("Server update: registration complete for " + Server.NUM_OBJECTS + " objects");
        System.out.println();
    }

    /**
     * listening to the wind of change!
     * actually listening to the socket and serving clients dynamic requests
     */
    private void startListening() {
        cm.openServerSocket();
        while (true) {
            System.out.println("Server status: waiting on port " + Server.SERVER_PORT);
            Connection cp = cm.acceptConnection();

            int cmd = (int) cm.readObject(cp);

            /**
             * if the command is counter (-1) then return the objects counter to the client
             */
            if (cmd == Server.CMD_COUNTER) {
                String response = Server.BMO_ONE + ".counter = " + rrm.bmo1.getCounter() + Server.DELIMITER
                        + Server.BMO_TWO + ".counter = " + rrm.bmo2.getCounter() + Server.DELIMITER
                        + Server.AMO_ONE + ".counter = " + rrm.amo1.getCounter() + Server.DELIMITER
                        + Server.AMO_TWO + ".counter = " + rrm.amo2.getCounter();
                cm.writeObject(response, cp);
                cm.closeConnection(cp);
                System.out.println("Server update: the objects operation counter sent: " + response);
                System.out.println();
            } else {
                /**
                 * otherwise it is a request with a object reference number
                 * consult RRM to serve the request
                 */
                int objectNumber = cmd;
                rrm.callObjectsDispatcher(objectNumber, cm, cp);
                System.out.println();
            }
        }
    }
}
