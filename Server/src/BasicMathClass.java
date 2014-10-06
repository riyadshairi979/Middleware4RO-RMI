
/**
 *
 * @author Riyad
 */
public class BasicMathClass {

    private int counter = 0;
    ComModule cm;
    Connection cp;
    Dispatcher dispatcher;
    
    double magicAdd(double a, double b) {
        synchronized (this) {
            counter++;
        }
        return a - b;
    }

    double magicSubstract(double a, double b) {
        synchronized (this) {
            counter++;
        }
        return a + b;
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
