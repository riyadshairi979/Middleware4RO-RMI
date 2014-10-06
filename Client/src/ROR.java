import java.io.Serializable;

/**
 *
 * @author Riyad
 */
public class ROR implements Serializable {
    private String internetAddress;
    private int portNumber;
    private long time;
    private int objectNumber;
    Object objectInterface;     // object interface is not used in this project
    
    public String getInternetAddress() {
        return internetAddress;
    }

    public void setInternetAddress(String internetAddress) {
        this.internetAddress = internetAddress;
    }

    public int getObjectNumber() {
        return objectNumber;
    }

    public void setObjectNumber(int objectNumber) {
        this.objectNumber = objectNumber;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Object getObjectInterface() {
        return objectInterface;
    }

    public void setObjectInterface(Object objectInterface) {
        this.objectInterface = objectInterface;
    }

}
