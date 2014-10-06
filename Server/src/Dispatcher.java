/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Riyad
 */
/**
 * Dispatcher class to server each request from clients
 */
public class Dispatcher implements Runnable {

    ComModule cm;
    Connection cp;
    Object parent;

    Dispatcher(Object parent, ComModule cm, Connection cp) {
        this.parent = parent;
        this.cm = cm;
        this.cp = cp;
    }

    @Override
    public void run() {
        cm.writeObject(dispatch(cm, cp), cp);
    }

    /**
     * dispatcher determine the method to call
     */
    public double dispatch(ComModule cm, Connection cp) {
        String methodName = (String) cm.readObject(cp);
        String parameters = (String) cm.readObject(cp);
        /**
         * skeleton marshal/unmarshals the parameters and call the actual method
         */
        String[] responseUnmarshal = parameters.split(Server.DELIMITER);
        double magicResult = Double.NaN;
        double a = Double.parseDouble(responseUnmarshal[0]);
        double b = Double.parseDouble(responseUnmarshal[1]);
        double c = Double.NaN;
        if (responseUnmarshal.length > 2)
            c = Double.parseDouble(responseUnmarshal[2]);

        switch (methodName) {
            case Server.MAGIC_MIN:
                System.out.println("Server update: skeleton is performing magicFindMin");
                magicResult = ((AdvancedMathClass) parent).magicFindMin(a, b, c);
                break;

            case Server.MAGIC_MAX:
                System.out.println("Server update: skeleton is performing magicFindMax");
                System.out.println();
                magicResult = ((AdvancedMathClass) parent).magicFindMax(a, b, c);
                break;
            case Server.MAGIC_ADD:
                System.out.println("Server update: skeleton is performing magicAdd");
                magicResult = ((BasicMathClass) parent).magicAdd(a, b);
                break;

            case Server.MAGIC_SUB:
                System.out.println("Server update: skeleton is performing magicSubstract");
                magicResult = ((BasicMathClass) parent).magicSubstract(a, b);
                break;

        }
        System.out.println("Server update: service complete for client " + cp.getSocket().getInetAddress().getHostName());
        return magicResult;
    }
}
