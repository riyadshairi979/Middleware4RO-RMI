
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Riyad
 */
public class RRM {

    private HashMap<String, ROR> RORtable = new HashMap();
    public BasicMathClass bmoOneProxy = null;
    public BasicMathClass bmoTwoProxy = null;
    public AdvancedMathClass amoOneProxy = null;
    public AdvancedMathClass amoTwoProxy = null;

    public RRM() {
    }

    public ROR getROR(String objectName) {
        return RORtable.get(objectName);
    }

    public void addROR(String objectName, ROR ror) {
        RORtable.put(objectName, ror);

        /*
         * proxy creation (if it is not already created)
         */
        switch (objectName) {
            case Client.BMO_ONE:
                if (bmoOneProxy == null) {
                    bmoOneProxy = new BasicMathClass();
                }
                break;

            case Client.BMO_TWO:
                if (bmoTwoProxy == null) {
                    bmoTwoProxy = new BasicMathClass();
                }
                break;

            case Client.AMO_ONE:
                if (amoOneProxy == null) {
                    amoOneProxy = new AdvancedMathClass();
                }
                break;

            case Client.AMO_TWO:
                if (amoTwoProxy == null) {
                    amoTwoProxy = new AdvancedMathClass();
                }
        }
    }

    /**
     * RRM creates the proxy object and call the appropriate method through dispatcher/skeleton
     */
    public double callMethodInProxy(String objectName, int methodNumber, ComModule cm) throws ClassNotFoundException {
        Random rand = new Random();

        /**
         * integer is taken instead of double because it looks better in output
         */
        double a = rand.nextInt(10);
        double b = rand.nextInt(10);
        double c = rand.nextInt(10);
        double result = Double.MIN_VALUE;

        switch (objectName) {
            case Client.BMO_ONE:
                bmoOneProxy.setRORInfo(RORtable.get(objectName));
                bmoOneProxy.setCommModule(cm);

                if (methodNumber == 1) {
                    result = bmoOneProxy.magicAdd(a, b);
                    System.out.println("magicAdd(" + a + ", " + b + ") = " + result);
                } else {
                    result = bmoOneProxy.magicSubstract(a, b);
                    System.out.println("magicSubstract(" + a + ", " + b + ") = " + result);
                }
                break;

            case Client.BMO_TWO:
                bmoTwoProxy.setRORInfo(RORtable.get(objectName));
                bmoTwoProxy.setCommModule(cm);

                if (methodNumber == 1) {
                    result = bmoTwoProxy.magicAdd(a, b);
                    System.out.println("magicAdd(" + a + ", " + b + ") = " + result);
                } else {
                    result = bmoTwoProxy.magicSubstract(a, b);
                    System.out.println("magicSubstract(" + a + ", " + b + ") = " + result);
                }
                break;

            case Client.AMO_ONE:
                amoOneProxy.setRORInfo(RORtable.get(objectName));
                amoOneProxy.setCommModule(cm);

                if (methodNumber == 1) {
                    result = amoOneProxy.magicFindMin(a, b, c);
                    System.out.println("magicFindMin(" + a + " , " + b + " , " + c + ") = " + result);
                } else {
                    result = amoOneProxy.magicFindMax(a, b, c);
                    System.out.println("magicFindMax(" + a + " , " + b + " , " + c + ") = " + result);
                }
                break;

            case Client.AMO_TWO:
                amoTwoProxy.setRORInfo(RORtable.get(objectName));
                amoTwoProxy.setCommModule(cm);

                if (methodNumber == 1) {
                    result = amoTwoProxy.magicFindMin(a, b, c);
                    System.out.println("magicFindMin(" + a + " , " + b + " , " + c + ") = " + result);
                } else {
                    result = amoTwoProxy.magicFindMax(a, b, c);
                    System.out.println("magicFindMax(" + a + " , " + b + " , " + c + ") = " + result);
                }
                break;
        }
        return result;
    }
}
