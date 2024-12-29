import java.util.HashMap;
import java.util.Stack;

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

    public Integer handleArithmeticOperations(HashMap<String, Integer> variables, String expression) {
        try {
            for (String variableName : variables.keySet()) {
                expression = expression.replaceAll("\\b" + variableName + "\\b", variables.get(variableName).toString());
            }

            return evaluateExpression(expression);
        } catch (Exception e) {
            System.out.println("Error: Invalid arithmetic expression '" + expression + "'");
            return null;
        }
    }

    private Integer evaluateExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");

        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                int num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                operands.push(num);
                continue;
            }

            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    compute(operands, operators);
                }
                operators.push(ch);
            }

            i++;
        }

        while (!operators.isEmpty()) {
            compute(operands, operators);
        }

        return operands.pop();
    }

    private void compute(Stack<Integer> operands, Stack<Character> operators) {
        int b = operands.pop();
        int a = operands.pop();
        char op = operators.pop();

        switch (op) {
            case '+':
                operands.push(a + b);
                break;
            case '-':
                operands.push(a - b);
                break;
            case '*':
                operands.push(a * b);
                break;
            case '/':
                operands.push(a / b);
                break;
        }
    }

    private int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        return 0;
    }
}
