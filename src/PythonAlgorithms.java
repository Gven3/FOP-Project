public class PythonAlgorithms {
    public String findSum(int n) {
        return "sum = 0\n" +
                "    x = 1\n" +
                "    while x <= " + n + ":\n" +
                "        sum = sum + x\n" +
                "        x = x + 1\n" +
                "    return sum";
    }
    public String factorial(int n){
        return "if " + n + " < 0:\n" +
                "        print(Factorial is not defined for negative numbers.)\n" +
                "    elif " + n + " == 0:\n" +
                "        return 1\n" +
                "    \n" +
                "    result = 1\n" +
                "    while " + n + " > 0:\n" +
                "        result = result * " + n + "\n" +
                "        " + n + " = " + n + " - 1\n" +
                "    print(result)";
    }
    public String gcd(int a, int b){
        return "while " + b + " != 0:\n" +
                "        remainder = " + a + " % " + b + "\n" +
                "        " + a + " = " + b + "\n" +
                "        " + b + " = remainder\n" +
                "    print(" + a + ")";
    }

    public String reverseNum(int n){
        return "reversed_num = 0\n" +
                "    while" + n + " > 0:\n" +
                "        reversed_num = reversed_num * 10 + (n % 10)\n" +
                "        " + n + "= " + n + " / 10\n" +
                "        " + n + " = " + n + " - (" + n + " % 1)\n" +
                "    return reversed_num";

    }

    public String isPrime(int n){
     return "if " + n + " <= 1:\n" +
             "        return False\n" +
             "    \n" +
             "    i = 2\n" +
             "    while i * i <= " + n + ":\n" +
             "        if " + n + " % i == 0:\n" +
             "            return False\n" +
             "        i = i + 1\n" +
             "    \n" +
             "    return True\n";
    }

    public String palindrome(int n){
        return "if " + n + " < 0:\n" +
                "        return False\n" +
                "\n" +
                "    original_n = " + n + "\n" +
                "    reversed_n = 0\n" +
                "\n" +
                "    while " + n + " > 0:\n" +
                "        last_digit = " + n + " % 10\n" +
                "        reversed_n = reversed_n * 10 + last_digit\n" +
                "        " + n + " = n - last_digit\n" +
                "\n" +
                "    return original_n == reversed_n";
    }
    public String largestDigit (int n){
        return " largest = 0\n" +
                "    while " + n + " > 0:\n" +
                "        digit = " + n + " % 10\n" +
                "        if digit > largest:\n" +
                "            largest = digit\n" +
                "        " + n + " = " + n + " / 10\n" +
                "        " + n + " = " + n + " - (" + n + " % 1)\n" +
                "    return largest";
    }
    public String sumOfDigits(int n){
        return " total = 0\n" +
                "    while " + n + " > 0:\n" +
                "        total = total + " + n + " % 10\n" +
                "        " + n + " = " + n + " / 10\n" +
                "        " + n + " = " + n + " - (" + n + " % 1)\n" +
                "    return total";
    }
    public String multiplicationTable(int n){
        return "count = 1\n" +
                "    while count <= 10:\n" +
                "        print(" + n + " * count)\n" +
                "        count = count + 1";
    }

    public String fib(int n){
        return "a = 0\n" +
                "    b = 1\n" +
                "    count = 0\n" +
                "    while count < " + n + ":\n" +
                "        a = b\n" +
                "        b = a + b\n" +
                "        count = count + 1\n" +
                "    return a";
    }
}
