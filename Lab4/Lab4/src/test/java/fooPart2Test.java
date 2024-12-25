import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class fooPart2Test {
    @ParameterizedTest(name = "{0}, {1}, {2} = {3}")
    @CsvSource({
            "  0, 100, 150, Exception",
            "  1, 100, 150, Not a Triangle",
            "  2, 100, 150, Not a Triangle",
            " 50, 100, 150, Not a Triangle",
            " 99, 100, 150,   Scalene",
            "100, 100, 150, Isosceles",
            "101, 100, 150, Exception",

            "50,  49, 150,  Exception",
            "50,  50, 150,  Not a Triangle",
            "50,  51, 150,  Not a Triangle",
            "50, 149, 150,    Scalene",
            "50, 150, 150,  Isosceles",
            "50, 151, 150,  Exception",

            "50, 100,  99,  Exception",
            "50, 100, 100,  Isosceles",
            "50, 100, 101,    Scalene",
            "50, 100, 199,  Not a Triangle",
            "50, 100, 200,  Not a Triangle",
            "50, 100, 201,  Exception"
    })
    void foo(int a, int b, int c, String expectedTriangle) throws InputOutOfRange {
        fooPart2 foo = new fooPart2();
        assertEquals(expectedTriangle, foo.foo(a, b, c),
                () -> "(" + a + ", " + b + ", " + c + ") should equal " + expectedTriangle);
    }
}