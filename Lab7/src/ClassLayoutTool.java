/**
 * Lab7
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassLayoutTool {
    public ClassLayoutTool(String[] classNames) throws ClassNotFoundException {
        if(classNames.length == 0) { // Make sure we have input
            System.out.println("No input arguments!");
            return;
        }

        for(String input : classNames) {
            Class<?> c = Class.forName(input);
            final String replace = c.getName() + "."; // Replace this text in the output

            // Get fields, methods, exceptions and constructors
            Field[] classFields = c.getDeclaredFields();
            Method[] classMethods = c.getDeclaredMethods();
            Constructor<?>[] classConstructors = c.getDeclaredConstructors();

            // Output
            System.out.println("Class Name: " + c.getName()+"\nFields: ");
            for(Field field : classFields)
                System.out.println("\t"+field.toString().replace(replace,"")+";");
            //System.out.println();

            System.out.println("Constructors: ");
            for(Constructor<?> constructor : classConstructors)
                System.out.println("\t"+constructor.toString().replace(replace,"")+";");
            //System.out.println();

            System.out.println("Methods: ");
            for(Method method : classMethods)
                System.out.println("\t"+method.toString().replace(replace,"")+";");
            System.out.println("===============================================================");
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) { // Make sure we have input
            System.out.println("No input arguments!");
            return;
        }

        try {
            new ClassLayoutTool(args);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}