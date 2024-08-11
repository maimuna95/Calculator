import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

// Calculator Implementation method
public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    private Map<String, Stack<Integer>> clientStacks;

    protected CalculatorImplementation() throws RemoteException {
        clientStacks = new HashMap<>();
    }

    // Create new stack for the new client
    @Override
    public synchronized void registerClient(String clientId) throws RemoteException {
        if (!clientStacks.containsKey(clientId)) {
            clientStacks.put(clientId, new Stack<>());
        }
    }

    // According to the client ID, get entry from the client to push into the designated stack for that specific client
    @Override
    public synchronized void pushValue(String clientId, int val) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId); // Push value to the stack via client ID
        if (stack != null) {
            stack.push(val);
        }
    }

    // For the individual client, push the operation that has been choosen by the client 
    @Override
    public synchronized void pushOperation(String clientId, String operator) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId);
        if (stack == null || stack.isEmpty()) return; // Return if the stack is empty 

        int result = stack.pop();

        switch (operator) {
            case "min":
                while (!stack.isEmpty()) {
                    result = Math.min(result, stack.pop()); // Find out the min value from the stack
                }
                break;
            case "max":
                while (!stack.isEmpty()) {
                    result = Math.max(result, stack.pop()); // Find out the max value from the stack 
                }
                break;
            case "lcm":
                while (!stack.isEmpty()) {
                    result = lcm(result, stack.pop()); 
                }
                break;
            case "gcd":
                while (!stack.isEmpty()) {
                    result = gcd(result, stack.pop());
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator); // Throw an exception if the operation doesn't match
        }

        stack.push(result);
    }

    @Override
    public synchronized int pop(String clientId) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId); // Pop value from the stack via client ID 
        if (stack != null && !stack.isEmpty()) {
            return stack.pop();
        } else {
            throw new RemoteException("Stack is empty or client not registered!");
        }
    }

    @Override
    public synchronized boolean isEmpty(String clientId) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId); // Access the stack via client ID
        return stack == null || stack.isEmpty(); // return true if the stack is empty or vice versa 
    }

    @Override
    public synchronized int delayPop(String clientId, int millis) throws RemoteException {
        // Input time (in milli seconds) to delay to pop a value from the stack
        try {
            Thread.sleep(millis); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RemoteException("Thread interrupted during delay", e);
        }
        return pop(clientId); // pop the value after the requested time delay 
    }

    @Override
    public synchronized List<Integer> getStack(String clientId) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId); // Access the stack via client ID 
        return stack != null ? new ArrayList<>(stack) : new ArrayList<>();  // Make an array to show the values of the stack if the stack is not empty 
    }

    // Methods for LCM and GCD
    // GCD: The initial value of temp is the value of b; then divide a%b and keep the remainder into b, store the remainder in a, keep doing the process until b is 0; and the final value of a is the GCD
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    // LCM: used the value of gcd; divide b/the result of gcd and multiply it with a, would be the result of LCM
    private int lcm(int a, int b) {
        return a * (b / gcd(a, b)); 
    }
}
