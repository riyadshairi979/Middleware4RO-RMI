
import java.util.HashMap;

/**
 *
 * @author Riyad
 */
public class RRM {

    private String[] objectNamePool = {Server.BMO_ONE, Server.BMO_TWO, Server.AMO_ONE, Server.AMO_TWO};
    private HashMap<String, ROR> RORTable = new HashMap();
    public BasicMathClass bmo1;
    public BasicMathClass bmo2;
    public AdvancedMathClass amo1;
    public AdvancedMathClass amo2;

    public RRM() {
        /**
         * building the ROR table
         */
        for (int i = Server.REFERENCE_BASE; i < Server.REFERENCE_BASE + Server.NUM_OBJECTS; i++) {
            ROR ror = new ROR();
            ror.setInternetAddress(Server.SERVER_HOST_NAME);
            ror.setPortNumber(Server.SERVER_PORT);
            ror.setObjectNumber(i);
            ror.setTime(System.currentTimeMillis());
            RORTable.put(objectNamePool[i - Server.REFERENCE_BASE], ror);
        }
    }

    String getObjectName(int index) {
        return objectNamePool[index - Server.REFERENCE_BASE];
    }

    ROR getROR(String objectName) {
        return (ROR) RORTable.get(objectName);
    }

    /**
     * RRM gets the reference of the object using table and calls the objects dispatcher in new thread
     */
    void callObjectsDispatcher(int objectNumber, ComModule cm, Connection cp) {
        String objectName = getObjectName(objectNumber);
        System.out.println("Server update: Dispatcher of " + objectName + " is executing");
        Thread t;
        switch (objectName) {
            case Server.BMO_ONE:
                t = new Thread(bmo1.getDispatcher(cm, cp));
                t.start();
                break;
            case Server.BMO_TWO:
                t = new Thread(bmo2.getDispatcher(cm, cp));
                t.start();
                break;
            case Server.AMO_ONE:
                t = new Thread(amo1.getDispatcher(cm, cp));
                t.start();
                break;
            case Server.AMO_TWO:
                t = new Thread(amo2.getDispatcher(cm, cp));
                t.start();
                break;
        }
    }
}
