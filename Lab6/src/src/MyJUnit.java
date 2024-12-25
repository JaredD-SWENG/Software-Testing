import java.lang.reflect.InvocationTargetException;

public class MyJUnit extends TestHarness {
    public MyJUnit(String[] classNames) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(classNames.length == 0) { // Make sure we have input
            System.out.println("No input arguments!");
            return;
        }

        for(String input : classNames) { // Run each input Testlet and test cases
            System.out.println(input);
            Class.forName(input).getMethod("runTest", this.getClass()).invoke(Class.forName(input).newInstance(), this);
            System.out.println("===================================");
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) { // Make sure we have input
            System.out.println("No input arguments!");
            return;
        }

        try {
            new MyJUnit(args); // Check in lecture notes if allowed
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}