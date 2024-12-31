import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder wholeText = new StringBuilder();
        HashMap<String, Integer> variables = new HashMap<>();
        PythonInterpreter interpreter = new PythonInterpreter();
        PythonAlgorithms algorithms = new PythonAlgorithms();



        String[] splittedCode = algorithms.sumOfDigits(12345).split("\n");
        String condition = "";

        for (int i = 0; i < splittedCode.length; i++) {
            String line = splittedCode[i].trim();

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("while ")) {
                int endLine = interpreter.findBlockEnd(splittedCode,i);
                interpreter.handleWhile(variables, formatTabs(splittedCode,i, endLine-1), i);

                // Skip over the block of code that was handled
                while (i + 1 < splittedCode.length && splittedCode[i + 1].startsWith("\t")) {
                    i++;
                }
            } else if (line.startsWith("if ") || line.startsWith("elif ")) {
                int endLine = interpreter.findBlockEnd(splittedCode,i);
                condition = line.substring(line.indexOf(" ") + 1, line.length() - 1).trim();

                interpreter.handleIfElse(variables, formatTabs(splittedCode,i, endLine-1), condition, false);
                while (i + 1 < splittedCode.length && splittedCode[i + 1].startsWith("\t")) {
                    i++;
                }
            } else if (line.startsWith("else:")) {
                int endLine = interpreter.findBlockEnd(splittedCode,i);

                interpreter.handleIfElse(variables, formatTabs(splittedCode,i, endLine-1), condition, true);
                while (i + 1 < splittedCode.length && splittedCode[i + 1].startsWith("\t")) {
                    i++;
                }

            } else {
                interpreter.handleVariableAssignment(variables, line);
                interpreter.handlePrint(variables, line,false);
            }

        }

        System.out.println("Final Variables: " + variables);
    }

    public static String[] formatTabs(String[] arr, int startLine, int endLine) {
        String[] newArr = new String[endLine - startLine + 1];
        int numberOfLoopTabs = 0;
        for (int i = 0; i < arr[startLine].length(); i++) {
            if (arr[startLine].charAt(i) == ('\t')) numberOfLoopTabs++;
        }
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[startLine++].substring(numberOfLoopTabs);
        }


        return newArr;
    }
}