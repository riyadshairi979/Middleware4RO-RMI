import java.util.HashMap;

/**
 *
 * @author Riyad
 */
public class RRM {

    private HashMap<String, ROR> RORtable = new HashMap();
    
    public RRM() {
    }
    
    public ROR getROR(String objectName) {
        return RORtable.get(objectName);
    }
    
    public void addROR(String objectName, ROR ror) {
        RORtable.put(objectName, ror);
    }
}
