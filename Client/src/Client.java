
import java.util.Random;

/**
 *
 * @author Riyad
 */
public class Client {

    public static int BINDER_PORT = 6543;
    public static String BINDER_HOST_NAME = "127.0.0.1";
    public static final String CMD_LOOKUP = "lookup";
    public static final int CMD_COUNTER = -1;
    public static int NUM_REQUEST = 1000;
    public static int NUM_OBJECTS = 4;
    public static final String DELIMITER = "__";
    public static final String BMO_ONE = "BMOONE";
    public static final String BMO_TWO = "BMOTWO";
    public static final String AMO_ONE = "AMOONE";
    public static final String AMO_TWO = "AMOTWO";
    RRM rrm;
    ComModule cm;
    String[] remoteObjectNamePool = {Client.BMO_ONE, Client.BMO_TWO, Client.AMO_ONE, Client.AMO_TWO};

    /**
     * client initializes RRM and Communication module
     */
    public Client() {
        rrm = new RRM();
        cm = new ComModule();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Client \"binder_ip\" \"binder_port\"");
            System.exit(1);
        }
        Client.BINDER_HOST_NAME = args[0];
        Client.BINDER_PORT = Integer.parseInt(args[1]);
        System.out.println("Client started");
        Client client = new Client();
        try {
            client.startOperations();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println("Client exited");
    }

    /**
     * client sends 1000 requests sequentially after having known the service details from the binder
     */
    private void startOperations() throws ClassNotFoundException {
        Random rand = new Random();
        ROR ror = null;

        for (int i = 0; i < Client.NUM_REQUEST; i++) {
            System.out.print((i+1) + "::\t");
            String objectName = remoteObjectNamePool[Math.abs(rand.nextInt() % Client.NUM_OBJECTS)];
            int methodNumber = Math.abs(rand.nextInt()) % 2;
            ror = (ROR) lookupRequest(objectName);

            System.out.println("Service info from binder: " + objectName + Client.DELIMITER + ror.getInternetAddress() + Client.DELIMITER + ror.getPortNumber() + Client.DELIMITER + ror.getObjectNumber());
            System.out.print("\t");
            
            /**
             * add the ROR to RRM and let the RRM module handle the rest of the task
             */
            rrm.addROR(objectName, ror);
            rrm.callMethodInProxy(objectName, methodNumber, cm);
            System.out.println();
        }

        /**
         * finally get the counter of different objects in the server
         */
        String counterInfo = (String) getCounter(ror);
        System.out.println(counterInfo);
    }

    /**
     * lookup method to request binder for service details
     */
    public Object lookupRequest(String objectName) throws ClassNotFoundException {
        cm.openConnection(Client.BINDER_HOST_NAME, Client.BINDER_PORT);
        cm.writeObject(Client.CMD_LOOKUP);
        cm.writeObject(objectName);
        Object obj = cm.readObject();
        cm.closeConnection();
        return obj;
    }

    /**
     * use server information from any of the ROR to get the counter from the server
     */
    public Object getCounter(ROR ror) throws ClassNotFoundException {
        cm.openConnection(ror.getInternetAddress(), ror.getPortNumber());
        cm.writeObject(Client.CMD_COUNTER);
        return cm.readObject();
    }
}
