import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Calculator extends Remote {
    // Add Client ID
    void registerClient(String clientId) throws RemoteException;
    // Push to the stack 
    void pushValue(String clientId, int val) throws RemoteException;
    // Operation wants to use
    void pushOperation(String clientId, String operator) throws RemoteException;
    // Pop the number from stack
    int pop(String clientId) throws RemoteException;
    // Check if the stack is empty
    boolean isEmpty(String clientId) throws RemoteException;
    // Pop a number after delaying 
    int delayPop(String clientId, int millis) throws RemoteException;
    
    List<Integer> getStack(String clientId) throws RemoteException;
}

