import java.util.HashMap;

public class PythonInterpreter {
    public void handleVariableAssignment(HashMap<String, Integer> variables, String line) {
        // Split the line into assignments using spaces as delimiters.


            if (line.contains("=")) {
                // 
                String[] parts = line.split("=", 2);
                String variableName = parts[0].trim();
                String expression = parts[1].trim();

                try {
                    int value = Integer.parseInt(expression);
                    variables.put(variableName, value);
                } catch (NumberFormatException e) {
                    if (variables.containsKey(expression)) {
                        variables.put(variableName, variables.get(expression));
                    } else {
                        System.out.println("Error: Undefined variable '" + expression + "'");

                }
            }
        }
    }

}
