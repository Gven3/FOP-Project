import java.util.HashMap;
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

        for (int i = 0; i < splittedCode.length; i++) {
            String line = splittedCode[i].trim();

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("if ") || line.startsWith("elif ") || line.startsWith("else:")) {
                interpreter.handleIfElse(variables, splittedCode, i);
                // Skip over the block of code that was handled
                while (i + 1 < splittedCode.length && splittedCode[i + 1].startsWith("\t")) {
                    i++;
                }
            } else {
                interpreter.handleVariableAssignment(variables, line);
                interpreter.handlePrint(variables, line);
            }
        }

        System.out.println("Final Variables: " + variables);
    }
}
