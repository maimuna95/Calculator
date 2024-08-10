package Server;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Stack;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    private Stack<Integer> stack;

    protected CalculatorImplementation() throws RemoteException {
        stack = new Stack<>();
    }

    @Override
    public synchronized void pushValue(int val) throws RemoteException {
        stack.push(val);
    }

    @Override
    public synchronized void pushOperation(String operator) throws RemoteException {
        if (stack.isEmpty()) {
            throw new RemoteException("Stack is empty, cannot perform operation.");
        }
        
        int result;
        switch (operator) {
            case "min":
                result = stack.stream().min(Integer::compare).get();
                break;
            case "max":
                result = stack.stream().max(Integer::compare).get();
                break;
            case "lcm":
                result = lcm(stack);
                break;
            case "gcd":
                result = gcd(stack);
                break;
            default:
                throw new RemoteException("Unknown operator.");
        }
        stack.clear();
        stack.push(result);
    }

    @Override
    public synchronized int pop() throws RemoteException {
        if (stack.isEmpty()) {
            throw new RemoteException("Stack is empty.");
        }
        return stack.pop();
    }

    @Override
    public synchronized boolean isEmpty() throws RemoteException {
        return stack.isEmpty();
    }

    @Override
    public int delayPop(int millis) throws RemoteException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pop();
    }

    private int gcd(Stack<Integer> stack) {
        int result = stack.pop();
        while (!stack.isEmpty()) {
            result = gcd(result, stack.pop());
        }
        return result;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private int lcm(Stack<Integer> stack) {
        int result = stack.pop();
        while (!stack.isEmpty()) {
            result = lcm(result, stack.pop());
        }
        return result;
    }

    private int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }
}
