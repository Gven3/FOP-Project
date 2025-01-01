public class PythonAlgorithms {
    public String findSum(int n) {
        return "sum = 0\n" +
                "x = 1\n" +
                "while x <= " + n + ":\n" +
                "\tsum = sum + x\n" +
                "\tx = x + 1\n" +
                "print(sum)";
    }

    public String factorial(int n) {
        return
                "n = " + n +"\n" +
                "if n < 0:\n" +
                "\tprint(\"Factorial is not defined for negative numbers.\")\n" +
                "elif n == 0:\n" +
                "\tprint(1)\n" +
                "result = 1\n" +
                "while n > 0:\n" +
                "\tresult = result * n\n" +
                "\tn = n - 1\n" +
                "print(result)";
    }

    public String gcd(int a, int b) {
        return "a = " + a + "\n" +
                "b = " + b + "\n" +
                "while b != 0:\n" +
                "\tremainder = a % b\n" +
                "\ta = b\n" +
                "\tb = remainder\n" +
                "print(a)";
    }

    public String reverseNum(int n) {
        return "n = " + n + "\n" +
                "reversed_n = 0\n" +
                "while n > 0:\n" +
                "\treversed_n = reversed_n * 10 + n%10\n" +
                "\tn = n / 10\n" +
                "print(reversed_n)";


    }

    public String isPrime(int n) {
        return "n = " + n + "\n" +
                "if " + n + " <= 1:\n" +
                "\tprint(" + '"' + "false" + '"' + ")\n" +
                "i = 2\n" +
                "while i * i <= " + n + ":\n" +
                "\tif " + n + " % i == 0:\n" +
                "\t\tprint(" + '"' + "false" + '"' + ")\n" +
                "\ti = i + 1\n" +
                "print(" + '"' + "true" + '"' + ")\n";
    }

    public String palindrome(int n) {
        return "if " + n + " < 0:\n" +
                "\tprint(\"false\")\n" +
                "n = " + n + "\n" +
                "original_n = " + n + "\n" +
                "reversed_n = 0\n" +
                "while n > 0:\n" +
                "\treversed_n = reversed_n * 10 + n%10\n" +
                "\tn = n / 10\n" +
                "if original_n == reversed_n:\n" +
                "\tprint(\"true\")\n" +
                "else:\n" +
                "\tprint(\"false\")"
                ;
    }

    public String largestDigit(int n) {
        return "largest = 0\n" +
                "n = " + n + "\n" +
                "while n > 0:\n" +
                "\tdigit = n % 10\n" +
                "\tif digit > largest:\n" +
                "\t\tlargest = digit\n" +
                "\tn = n / 10\n" +
                "print(largest)";
    }

    public String sumOfDigits(int n) {
        return "total = 0\n" +
                "n = " + n + "\n" +
                "while n > 0:\n" +
                "\ttotal = total + n % 10\n" +
                "\tn = n / 10\n" +
                "print(total)";
    }

    public String multiplicationTable(int n) {
        return "count = 1\n" +
                "while count <= 10:\n" +
                "\tprint(" + n + " * count)\n" +
                "\tcount = count + 1";
    }

    public String fib(int n) {
        return "a = 0\n" +
                "b = 1\n" +
                "count = 1\n" +
                "while count < " + n + ":\n" +
                "\ttemp = b\n" +
                "\tb = a + b\n" +
                "\ta = temp\n" +
                "\tcount = count + 1\n" +
                "print(a)";
    }
}
