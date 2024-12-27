import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder wholeText = new StringBuilder();
        HashMap<String, Integer> variables = new HashMap<>();

        System.out.println("Enter text (press Ctrl+D or Ctrl+Z to finish):");
        while (scanner.hasNextLine()) {
            wholeText.append(scanner.nextLine()).append("\n");
        }

        String[] splittedCode = wholeText.toString().split("\n");


        for (int i = 0; i < splittedCode.length; i++) {
            String line = splittedCode[i].trim();

            if (line.contains("=")) {
                String[] assignment = line.split("=", 2);
                String variableName = assignment[0].trim();
                String expression = assignment[1].trim();

                variables.put(variableName, Integer.parseInt(expression));
            }



        }
        System.out.println(variables);
    }
}