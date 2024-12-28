import java.util.HashMap;

public class PythonInterpreter {
    public void handleVariableAssignment(HashMap<String, Integer> variables, String line) {

            if (line.contains("=")) {
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
    public void handlePrint(HashMap<String, Integer> variables, String line) {
        if (line.startsWith("print(") && line.endsWith(")")) {
            String content = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();

            if (content.startsWith("\"") && content.endsWith("\"")) {
                System.out.println(content.substring(1, content.length() - 1));
            } else {
                if (variables.containsKey(content)) {
                    System.out.println(variables.get(content));
                } else {
                    System.out.println(content);
                }
            }
        }
    }

}
