public class PythonAlgorithms {
    public String findSum() {
        return "sum = 0\n" +
                "    x = 1\n" +
                "    while x <= n:\n" +
                "        sum = sum + x\n" +
                "        x = x + 1\n" +
                "    return sum";
    }
    public String factorial(){
        return "if n < 0:\n" +
                "        print(Factorial is not defined for negative numbers.)\n" +
                "    elif n == 0:\n" +
                "        return 1\n" +
                "    \n" +
                "    result = 1\n" +
                "    while n > 0:\n" +
                "        result = result * n\n" +
                "        n = n - 1\n" +
                "    print(result)";
    }
    public String gcd(){
        return "while b != 0:\n" +
                "        remainder = a % b\n" +
                "        a = b\n" +
                "        b = remainder\n" +
                "    print(a)";
    }
}
