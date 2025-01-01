import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap<String, Integer> variables = new HashMap<>();
        PythonInterpreter interpreter = new PythonInterpreter();
        PythonAlgorithms algorithms = new PythonAlgorithms();
        String fibbonaci = algorithms.fib(7);
        String findSum = algorithms.findSum(10);
        String factorial = algorithms.factorial(7);
        String gcd = algorithms.gcd(48,18);
        String reverseNum = algorithms.reverseNum(4511241);
        String isPrime = algorithms.isPrime(18);
        String palindrome = algorithms.palindrome(12221);
        String largestDigit = algorithms.largestDigit(124219);
        String sumOfDigits = algorithms.sumOfDigits(123456789);
        String multiplicationTable = algorithms.multiplicationTable(12);

        // Initialize the Python-like code
        String[] algorithm = codeForExecution(multiplicationTable);

        // Process the code line by line
        processCode(interpreter, variables, algorithm);
    }

    private static String[] codeForExecution(String name){
        return name.split("\n");
    }
    private static void processCode(PythonInterpreter interpreter, HashMap<String, Integer> variables, String[] splittedCode) {
        String condition = ""; // To keep track of the latest `if`/`elif` condition

        // Iterate through each line of the code
        for (int i = 0; i < splittedCode.length; i++) {
            String line = splittedCode[i].trim();

            // Skip empty lines
            if (line.isEmpty()) continue;

            // Process control structures or regular statements
            i = processControlStructure(interpreter, variables, splittedCode, line, i, condition);
        }
    }

    private static int processControlStructure(PythonInterpreter interpreter, HashMap<String, Integer> variables, String[] splittedCode, String line, int currentIndex, String condition) {
        if (line.startsWith("while ")) {
            int endLine = interpreter.findBlockEnd(splittedCode, currentIndex);
            interpreter.handleWhile(variables, formatTabs(splittedCode, currentIndex, endLine - 1));

            // Skip over the processed block
            return skipProcessedBlock(splittedCode, currentIndex);
        } else if (line.startsWith("if ") || line.startsWith("elif ")) {
            int endLine = interpreter.findBlockEnd(splittedCode, currentIndex);
            condition = extractCondition(line);

            interpreter.handleIfElse(variables, formatTabs(splittedCode, currentIndex, endLine - 1), condition, false);

            // Skip over the processed block
            return skipProcessedBlock(splittedCode, currentIndex);
        } else if (line.startsWith("else:")) {
            int endLine = interpreter.findBlockEnd(splittedCode, currentIndex);
            interpreter.handleIfElse(variables, formatTabs(splittedCode, currentIndex, endLine - 1), condition, true);

            // Skip over the processed block
            return skipProcessedBlock(splittedCode, currentIndex);
        } else {
            // Handle variable assignments or print statements
            interpreter.handleVariableAssignment(variables, line);
            interpreter.handlePrint(variables, line, false);
        }

        return currentIndex;
    }

    private static int skipProcessedBlock(String[] splittedCode, int currentIndex) {
        // Skip lines that are part of the processed block (indented lines)
        while (currentIndex + 1 < splittedCode.length && splittedCode[currentIndex + 1].startsWith("\t")) {
            currentIndex++;
        }
        return currentIndex;
    }

    private static String extractCondition(String line) {
        // Extract the condition from an `if` or `elif` statement
        return line.substring(line.indexOf(" ") + 1, line.length() - 1).trim();
    }

    public static String[] formatTabs(String[] arr, int startLine, int endLine) {
        String[] newArr = new String[endLine - startLine + 1];
        int numberOfLoopTabs = 0;

        // Count the number of tabs in the first line
        for (int i = 0; i < arr[startLine].length(); i++) {
            if (arr[startLine].charAt(i) == ('\t')) numberOfLoopTabs++;
        }

        // Remove tabs for all lines in the block
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[startLine++].substring(numberOfLoopTabs);
        }

        return newArr;
    }
}
