
import java.io.IOException;

/**
 *
 * @author Riyad
 */
public class Binder {

    public static int BINDER_PORT = 6543;
    public static final String BMO_ONE = "BMOONE";
    public static final String BMO_TWO = "BMOTWO";
    public static final String AMO_ONE = "AMOONE";
    public static final String AMO_TWO = "AMOTWO";
    public static final String CMD_REGISTER = "register";
    public static final String CMD_LOOKUP = "lookup";
    public static final String DELIMITER = "__";
    RRM rrm;
    ComModule cm;
    private int clientCount = 0;

    /**
     * binder initializes RRM and Communication module
     * and starts listening to the port
     */
    public Binder() {
        rrm = new RRM();
        cm = new ComModule();
        try {
            cm.openBinderSocket(this);
            try {
                startListening();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Binder \"binder_port\"");
            System.exit(1);
        }
        Binder.BINDER_PORT = Integer.parseInt(args[0]);
        System.out.println("Binder started");
        Binder binder = new Binder();
    }

    /**
     * binder listens to the port and get command to execute
     */
    private void startListening() throws IOException, ClassNotFoundException {
        while (true) {
            Connection cp = cm.acceptConnection();
            String cmd = (String) cp.getIn().readObject();

            if (cmd.equals(Binder.CMD_REGISTER)) {
                registerService(cp);
            } else if (cmd.equals(Binder.CMD_LOOKUP)) {
                lookupService(cp);
            }
            
            cm.closeConnection(cp);
        }
    }

    /**
     * registration service for servers
     */
    void registerService(Connection cp) {
        // input how many registration
        int numRegistration = (int) cm.readObject(cp);

        for (int i = 0; i < numRegistration; i++) {
            String objectName = (String) cm.readObject(cp);
            ROR ror = (ROR) cm.readObject(cp);
            rrm.addROR(objectName, ror);
            System.out.println("service: " + objectName + " -> object#: " + ror.getObjectNumber());
        }

        System.out.println("Binder update: " + numRegistration + " service registration complete");
        System.out.println();
    }

    /**
     * lookup service for clients
     */
    void lookupService(Connection cp) {
        clientCount++;
        String serviceName = (String) cm.readObject(cp);
        System.out.println("Binder update: " + cp.getSocket().getInetAddress().getHostName() + " looking for service: " + serviceName);
        cm.writeObject(rrm.getROR(serviceName), cp);
        System.out.println();
    }
}
