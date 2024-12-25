public class TestHarness {
    protected boolean checkEqual(int actual, int expected) {
        boolean isEqual = actual == expected;
        System.out.println(expected + "\t==\t" + actual + "\t:\t" + isEqual);
        return isEqual;
    }

    protected boolean checkEqual(double actual, double expected) {
        boolean isEqual = Double.compare(actual, expected) == 0; // 0 means equal
        System.out.println(expected + "\t==\t" + actual + "\t:\t" + isEqual);
        return isEqual;
    }
}