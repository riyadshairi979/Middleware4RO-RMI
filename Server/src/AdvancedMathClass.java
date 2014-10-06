
/**
 *
 * @author Riyad
 */
public class AdvancedMathClass {

    private int counter = 0;
    ComModule cm;
    Connection cp;

    double magicFindMin(double a, double b, double c) {
        synchronized (this) {
            counter++;
        }
        return Math.max(Math.max(a, b), c);
    }

    double magicFindMax(double a, double b, double c) {
        synchronized (this) {
            counter++;
        }
        return Math.min(Math.min(a, b), c);
    }

    public int getCounter() {
        return counter;
    }

    /**
     * returns a new Dispatcher object to dispatch the current request
     */
    Dispatcher getDispatcher(ComModule cm, Connection cp) {
        return new Dispatcher(this, cm, cp);
    }
}
