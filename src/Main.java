import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder wholeText = new StringBuilder();
        HashMap<String, Integer> variables = new HashMap<>();
         PythonInterpreter interpreter = new PythonInterpreter();
        System.out.println("Enter text (press Ctrl+D or Ctrl+Z to finish):");
        while (scanner.hasNextLine()) {
            wholeText.append(scanner.nextLine()).append("\n");
        }
        String[] splittedCode = wholeText.toString().split("\n");


        for (String s : splittedCode) {
            String line = s.trim();
            interpreter.handleVariableAssignment(variables, line);
        }

            System.out.println(variables);
    }
}