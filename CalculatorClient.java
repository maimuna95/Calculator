import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            // Lookup the remote object from the registry
            Calculator calculator = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");

            Scanner scanner = new Scanner(System.in);

            // Register the client with a unique identifier
            System.out.print("Enter your client ID: ");
            String clientId = scanner.nextLine();
            calculator.registerClient(clientId);

            while (true) {
                System.out.println("\n \n1. Push Value");
                System.out.println("2. Push Operation");
                System.out.println("3. Pop Value");
                System.out.println("4. Check if Stack is Empty");
                System.out.println("5. Delay Pop");
                System.out.println("6. Show Stack");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter value to push: ");
                        int value = scanner.nextInt();
                        calculator.pushValue(clientId, value);
                        break;
                    case 2:
                        System.out.print("Enter operation (min, max, lcm, gcd): ");
                        String operation = scanner.next();
                        calculator.pushOperation(clientId, operation);

                        // Immediately pop and display the result after an operation
                        int result = calculator.pop(clientId);
                        System.out.println("Result after operation '" + operation + "': " + result);
                        break;
                    case 3:
                        System.out.println("Popped value: " + calculator.pop(clientId));
                        break;
                    case 4:
                        System.out.println("Is stack empty? " + calculator.isEmpty(clientId));
                        break;
                    case 5:
                        System.out.print("Enter delay in milliseconds: ");
                        int millis = scanner.nextInt();
                        System.out.println("Delayed pop value: " + calculator.delayPop(clientId, millis));
                        break;
                    case 6:
                        List<Integer> stackContents = calculator.getStack(clientId);
                        System.out.println("Current stack: " + stackContents);
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option, try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("Calculator Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
