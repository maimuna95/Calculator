import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // Start RMI registry
            LocateRegistry.createRegistry(1099);

            // Create an instance of the CalculatorImplementation
            Calculator calculator = new CalculatorImplementation();

            // Bind the remote object in the registry
            Naming.rebind("CalculatorService", calculator);

            System.out.println("Calculator Server is ready.");
        } catch (Exception e) {
            System.err.println("Calculator Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
