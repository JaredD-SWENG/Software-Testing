package Lab2.Lab2.src.Part1;

/**
 * Exception thrown when derivative is 0 for Part 1 of Lab2
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */

public class ZeroDerivativeException extends Exception {
    public ZeroDerivativeException(String message){
        super(message);
    }
}