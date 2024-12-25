public class fooPart2 {
    public String foo(int a, int b, int c) throws InputOutOfRange {
        // Check input
        // Print exception otherwise
        if (a < 1 || a > 100 || b < 50 || b > 150 || c < 100 || c > 200)
            return "Exception"; // IDK which one
            //throw new InputOutOfRange("Input has criteria: 1 ≤ a ≤ 100, 50 ≤ b ≤ 150 and 100 ≤ c ≤ 200");

        // Check if sides work
        if(c>=(a+b) || b>=(a+c) || a>=(b+c))
            return "Not a Triangle";

        if(a==b && b==c)
            return "Equilateral";
        if(a!=b && b!=c && c!=a)
            return "Scalene";
        if(a==b || b==c || c==a)
            return "Isosceles";
        return "Exception";
    }
}