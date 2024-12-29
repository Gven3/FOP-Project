import java.util.HashMap;
import java.util.Stack;

public class PythonInterpreter {
    private static boolean conditionMet = false;
    public void handleVariableAssignment(HashMap<String, Integer> variables, String line) {
        if (line.contains("=")) {
            String[] parts = line.split("=", 2);
            String variableName = parts[0].trim();
            String expression = parts[1].trim();

            try {
                int value = handleArithmeticOperations(variables, expression);
                variables.put(variableName, value);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid arithmetic expression");
            }
        }
    }

    public void handlePrint(HashMap<String, Integer> variables, String line) {
        if (line.startsWith("print(") && line.endsWith(")")) {
            String content = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();

            if (content.startsWith("\"") && content.endsWith("\"")) {
                System.out.println(content.substring(1, content.length() - 1));
            } else {
                try {
                    int value = handleArithmeticOperations(variables, content);
                    System.out.println(value);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid arithmetic expression or undefined variable");
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

    public void handleIfElse(HashMap<String, Integer> variables, String[] lines, int startLine) {
        int endLine = lines.length;


        for (int i = startLine; i < lines.length; i++) {
            if (!lines[i].startsWith(" ") && i > startLine) {
                endLine = i;
                break;
            }
        }

        for (int i = startLine; i < endLine; i++) {
            String currentLine = lines[i].trim();


            if ((currentLine.startsWith("if ") || currentLine.startsWith("elif ")) && currentLine.endsWith(":")) {
                String condition = currentLine.substring(currentLine.indexOf(" ") + 1, currentLine.length() - 1).trim();

                if (!conditionMet && evaluateCondition(variables, condition)) {
                    conditionMet = true;


                    for (int j = i + 1; j < endLine && lines[j].startsWith("    "); j++) {
                        executeLine(variables, lines[j].trim());
                        i = j;
                    }
                }
            }
            if (currentLine.startsWith("else:") && !conditionMet) {
                for (int j = i + 1; j < endLine && lines[j].startsWith("    "); j++) {
                    executeLine(variables, lines[j].trim());
                }
                break;
            }
        }

    }

    private void executeLine(HashMap<String, Integer> variables, String line) {
        if (line.contains("=")) {
            handleVariableAssignment(variables, line);
        } else if (line.startsWith("print")) {
            handlePrint(variables, line);
        }
    }

    private boolean evaluateCondition(HashMap<String, Integer> variables, String condition) {
        String[] parts = condition.split(" ");
        if (parts.length != 3) {
            System.out.println("Error: Invalid condition syntax");
            return false;
        }

        String leftOperand = parts[0];
        String operator = parts[1];
        String rightOperand = parts[2];

        int leftValue = variables.containsKey(leftOperand) ? variables.get(leftOperand) : Integer.parseInt(leftOperand);
        int rightValue = variables.containsKey(rightOperand) ? variables.get(rightOperand) : Integer.parseInt(rightOperand);

        switch (operator) {
            case "<":
                return leftValue < rightValue;
            case "<=":
                return leftValue <= rightValue;
            case ">":
                return leftValue > rightValue;
            case ">=":
                return leftValue >= rightValue;
            case "==":
                return leftValue == rightValue;
            case "!=":
                return leftValue != rightValue;
            default:
                System.out.println("Error: Invalid operator");
                return false;
        }
    }

}
