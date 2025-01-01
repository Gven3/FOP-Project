import java.util.HashMap;
import java.util.Stack;

public class PythonInterpreter {
    private boolean conditionMet = false;

    public void handleVariableAssignment(HashMap<String, Integer> variables, String line) {
        if (line.contains("=")) {
            // Split on the '=' to determine variable name and expression
            String[] parts = line.split("=", 2);
            String variableName = parts[0].trim();
            String expression = parts[1].trim();


            try {
                // If expression needs any arithmetic operation, we call handleArithmeticOperations
                int value = handleArithmeticOperations(variables, expression);
                // And put it in HashMap for future use
                variables.put(variableName, value);
            } catch (Exception e) {
                System.out.println("Error: Invalid arithmetic expression in line - " + line);
            }
        }
    }

    public void handlePrint(HashMap<String, Integer> variables, String line, boolean isInLoop) {
        if (line.startsWith("print(") && line.endsWith(")")) {
            String content = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();

            if (content.startsWith("\"") && content.endsWith("\"")) {
                System.out.println(content.substring(1, content.length() - 1));
                if(!isInLoop) System.exit(0);
            } else {
                try {
                    int value = handleArithmeticOperations(variables, content);
                    System.out.println(value);
                    if(!isInLoop) System.exit(0);
                } catch (Exception e) {
                    System.out.println("Error: Invalid arithmetic expression or undefined variable in print statement");
                }
            }
        }
    }

    public void handleWhile(HashMap<String, Integer> variables, String[] lines, int startLine) {
        // Find the end of the while block
        int endLine = findBlockEnd(lines, 0);

        // Take a condition to check if it is met
        String condition = lines[0].substring(6, lines[0].length() - 1).trim();

        while (evaluateCondition(variables, condition)) {
            for (int i = 1; i < endLine; i++) {
                String line = lines[i].trim();

                if (!line.isEmpty()) {
                    // Check if there is an if in loop
                    if (lines[i].startsWith("\tif ")) {
                        // Find the end of if block
                        int last = findBlockEnd(lines, i);
                        String conditional = lines[i].substring(lines[i].indexOf(" ") + 1, lines[i].length() - 1).trim();
                        handleIfElse(variables, Main.formatTabs(lines, i, last - 1), conditional, false);
                        // Skipping if's block lines
                        while (i + 1 < lines.length && lines[i + 1].startsWith("\t\t")) {
                            i++;
                        }
                    }
                    // If there is no if, execute line
                    executeLine(variables, line,true);
                }
            }
        }
    }

    public void handleIfElse(HashMap<String, Integer> variables, String[] lines, String condition, boolean conditionElse) {
        int endLine = findBlockEnd(lines, 0);

        for (int i = 1; i < endLine; i++) {
            if (!conditionMet && evaluateCondition(variables, condition) && !conditionElse && !lines[i].startsWith("\tif ")) {
                conditionMet = true;
                executeBlock(variables, lines, i, endLine);
                break;
            }
            if (conditionElse && !conditionMet && !lines[i].startsWith("\tif ")) {
                executeBlock(variables, lines, i, endLine);
                break;
            }
            if(lines[i].startsWith("\tif ")){
                int last = findBlockEnd(lines,i);
                String conditional = lines[i].substring(lines[i].indexOf(" ") + 1, lines[i].length() - 1).trim();
                handleIfElse(variables, Main.formatTabs(lines,i, last-1), conditional, false);
                while (i + 1 < lines.length && lines[i + 1].startsWith("\t")) {
                    i++;
                }
            }
        }
        conditionMet = false;
    }


    public int findBlockEnd(String[] lines, int startLine) {
        int endLine = lines.length;
        for (int i = startLine + 1; i < lines.length; i++) {
            if (!lines[i].startsWith("\t")) {
                endLine = i;
                break;
            }
        }

        return endLine;
    }

    private void executeBlock(HashMap<String, Integer> variables, String[] lines, int start, int end) {
        for (int i = start; i < end && lines[i].startsWith("\t"); i++) {
            executeLine(variables, lines[i].trim(),false);
        }
    }

    private void executeLine(HashMap<String, Integer> variables, String line,boolean isInLoop) {
        if (line.contains("=")) {
            handleVariableAssignment(variables, line);
        } else if (line.startsWith("print")) {
            handlePrint(variables, line,isInLoop);
        }
    }

    private boolean evaluateCondition(HashMap<String, Integer> variables, String condition) {
        // List of valid comparison operators
        String[] operators = {">=", "<=", ">", "<", "==", "!="};
        String operator = null;

        // Identify the operator present in the condition
        for (String op : operators) {
            if (condition.contains(op)) {
                operator = op;
                break;
            }
        }

        // Throw an exception if no valid operator is found
        if (operator == null) {
            throw new IllegalArgumentException("Error: Invalid condition - " + condition);
        }

        // Split the condition into two parts (left and right operands) based on the operator
        String[] parts = condition.split(operator);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Error: Invalid condition format - " + condition);
        }

        // Evaluate the left and right operands, including any arithmetic expressions
        int left = handleArithmeticOperations(variables, parts[0].trim());
        int right = handleArithmeticOperations(variables, parts[1].trim());

        // Perform the comparison based on the identified operator
        switch (operator) {
            case ">":
                return left > right;
            case ">=":
                return left >= right;
            case "<":
                return left < right;
            case "<=":
                return left <= right;
            case "==":
                return left == right;
            case "!=":
                return left != right;
            default:
                throw new IllegalArgumentException("Error: Unsupported operator - " + operator);
        }
    }

    public Integer handleArithmeticOperations(HashMap<String, Integer> variables, String expression) {
        // Replace variables in the expression with their corresponding values
        for (String variableName : variables.keySet()) {
            expression = expression.replaceAll("\\b" + variableName + "\\b", variables.get(variableName).toString());
        }

        // Evaluate the resulting arithmetic expression
        return evaluateExpression(expression);
    }

    private Integer evaluateExpression(String expression) {
        Stack<Integer> operands = new Stack<>(); // Stack to store numbers
        Stack<Character> operators = new Stack<>(); // Stack to store operators
        int i = 0;

        while (i < expression.length()) {
            char ch = expression.charAt(i);

            // Handle numbers (including negative numbers)
            if (Character.isDigit(ch) || (ch == '-' && (i == 0 || operators.contains(expression.charAt(i - 1))))) {
                boolean isNegative = ch == '-'; // Check if the number is negative
                int num = 0;

                if (isNegative) i++; // Skip the minus sign for parsing the number

                // Parse the number until a non-digit character is encountered
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }

                // Push the number (with the appropriate sign) to the operands stack
                operands.push(isNegative ? -num : num);
                continue;
            }

            // Handle arithmetic operators
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%') {
                // Process operators with higher or equal precedence
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    compute(operands, operators);
                }
                // Push the current operator onto the operators stack
                operators.push(ch);
            }

            i++;
        }

        // Compute any remaining operations
        while (!operators.isEmpty()) {
            compute(operands, operators);
        }

        // The final result is the only value left in the operands stack
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
            case '%':
                operands.push(a % b);
                break;
        }
    }

    private int precedence(char operator) {
        return (operator == '+' || operator == '-') ? 1 : 2;
    }
}