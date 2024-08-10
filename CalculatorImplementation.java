import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    private Map<String, Stack<Integer>> clientStacks;

    protected CalculatorImplementation() throws RemoteException {
        clientStacks = new HashMap<>();
    }

    @Override
    public synchronized void registerClient(String clientId) throws RemoteException {
        if (!clientStacks.containsKey(clientId)) {
            clientStacks.put(clientId, new Stack<>());
        }
    }

    @Override
    public synchronized void pushValue(String clientId, int val) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId);
        if (stack != null) {
            stack.push(val);
        }
    }

    @Override
    public synchronized void pushOperation(String clientId, String operator) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId);
        if (stack == null || stack.isEmpty()) return;

        int result = stack.pop();

        switch (operator) {
            case "min":
                while (!stack.isEmpty()) {
                    result = Math.min(result, stack.pop());
                }
                break;
            case "max":
                while (!stack.isEmpty()) {
                    result = Math.max(result, stack.pop());
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
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }

        stack.push(result);
    }

    @Override
    public synchronized int pop(String clientId) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId);
        if (stack != null && !stack.isEmpty()) {
            return stack.pop();
        } else {
            throw new RemoteException("Stack is empty or client not registered!");
        }
    }

    @Override
    public synchronized boolean isEmpty(String clientId) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId);
        return stack == null || stack.isEmpty();
    }

    @Override
    public synchronized int delayPop(String clientId, int millis) throws RemoteException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RemoteException("Thread interrupted during delay", e);
        }
        return pop(clientId);
    }

    @Override
    public synchronized List<Integer> getStack(String clientId) throws RemoteException {
        Stack<Integer> stack = clientStacks.get(clientId);
        return stack != null ? new ArrayList<>(stack) : new ArrayList<>();
    }

    // Helper methods for LCM and GCD
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }
}
