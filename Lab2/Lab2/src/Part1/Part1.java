package Lab2.Lab2.src.Part1;

/**
 * Part 1 of Lab2
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */
public final class Part1 {
    private static final int[]  PSU_ID   = {9, 4, 0, 7, 2, 9, 2}; // Haris's First 7 PSU ID Digits
    private static final double MAX_DIFF = 0.00001; // As listed in Part 1
    private static final int    MAX_ITER = 2000; // As listed in Part 1

    /**
     * Return function output at input point
     * @param input function input value
     * @return function output value
     */
    public static double getFunction(double input) {
        return (PSU_ID[0] * Math.pow(input, 6)) - (PSU_ID[1] * Math.pow(input, 5)) + (PSU_ID[2] * Math.pow(input, 4)) -
                (PSU_ID[3] * Math.pow(input, 3)) + (PSU_ID[4] * Math.pow(input, 2)) - (PSU_ID[5] * input) + PSU_ID[6];
    }

    /**
     * Return derivative function output at input point of {@link #getFunction(double)}
     * @param input derivative input value
     * @return derivative output value
     */
    public static double getFunctionDerivative(double input) {
        return (6 * PSU_ID[0] * Math.pow(input, 5)) - (5 * PSU_ID[1] * Math.pow(input, 4)) + (4 * PSU_ID[2] * Math.pow(input, 3)) -
                (3 * PSU_ID[3] * Math.pow(input, 2)) + (2 * PSU_ID[4] * input) - PSU_ID[5];
    }

    /**
     * Prints roots of {@link #getFunction(double)} using the Newton-Raphson's method
     */
    private static void findRoots(double initialValue) throws ZeroDerivativeException
    {
        int iterations = 0; // Stores iterations
        double  output      = initialValue; // The output will be the root

        if (getFunctionDerivative(initialValue) == 0)
            throw new ZeroDerivativeException("Newton-Raphson's method failed: derivative equals 0");

        double difference = getFunction(initialValue) / getFunctionDerivative(initialValue); // Difference between next and previous value

        for (; iterations < MAX_ITER && Math.abs(difference) >= MAX_DIFF; iterations++) {
            output     = output - difference; // x{n+1}=x{n}-(f/f')
            difference = getFunction(output) / getFunctionDerivative(output); // f/f'
        }

        System.out.println(
                "Initial Value: "   + initialValue                                                                  +
                "\nDerived Root: "  + (Math.abs(difference) < MAX_DIFF ? output : "Not Found!")                     +
                "\nIterations: "    + iterations                                                                    +
                "\nValue at root: " + (Math.abs(difference) < MAX_DIFF ? getFunction(output) : "No root given!")    + "\n");
    }

    public static void main(String[] args) throws ZeroDerivativeException
    {
        findRoots(2);
        findRoots(0);
    }
}