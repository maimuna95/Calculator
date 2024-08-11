Distributed Calculator Application (Java RMI) 

Overview:
This is a Calculator Application System using Java RMI. This application consists of a server and multiple clients. Each client will have a stack to push value in it, pop the value from the stack, show the stack value, check if the stack is empty, can do operations such as find the min and max from the stack, and calculate GCD and LCM of the stack values. 

I tried for the bonus points: Each client will have individual stack by maintaining "hashmap' so that client's stack can store values separately by Client ID (client ID is string).


Files:
As mentioned in the assignment description, I used the following file names:
- Calculator.java: Interface defining the remote methods available to clients.
- CalculatorImplementation.java: Implements the Calculator interface and contains the logic for stack operations.
- CalculatorServer.java: Hosts the RMI server and binds the Calculator implementation to the RMI registry.
- CalculatorClient.java: Client application that interacts with the server to perform stack operations.


Run the code:
- Open a terminal to compile java files and run server
- Compile the java files: javac CalculatorServer.java CalculatorClient.java
- Run the server: java CalculatorServer   
- Open another terminal to run the client file 
- Run the client: java Calculator.Client 
After running the client file: 
- It will ask to put a client ID, as example you can write "1" or "Your Name" because it will take input as string value. This way you can open another terminal as another client and you can create another client ID
- Following that you will get these options 
    1. Push Value : Push value into the stack -- 
    2. Push Operation : Mention the operation you want to do with the stack values
    3. Pop Value : Pop the value from the stack
    4. Check if Stack is Empty : It will show the stack is empty or not
    5. Delay Pop : It will pop the value after the mentioned delay
    6. Show Stack : By this option, you will be able to view the value of the stack 
    7. Exit : By this option, client will exit from the server 




