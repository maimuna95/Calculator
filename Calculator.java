import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Calculator extends Remote {
    void registerClient(String clientId) throws RemoteException;
    void pushValue(String clientId, int val) throws RemoteException;
    void pushOperation(String clientId, String operator) throws RemoteException;
    int pop(String clientId) throws RemoteException;
    boolean isEmpty(String clientId) throws RemoteException;
    int delayPop(String clientId, int millis) throws RemoteException;
    List<Integer> getStack(String clientId) throws RemoteException;
}

