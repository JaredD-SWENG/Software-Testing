/**
 * Lab 8 Part2
 * Haris Siddiqui, Jared Daniel, Yifan Liu
 */

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaPGUI extends JFrame {
    private JButton openFileB;
    private JButton closeFileB;
    private JTextField testClassF;
    private JLabel testClassL;
    private JPanel javaPGUIP;
    private JButton newObjectButton;
    private JButton runButton;
    private JTextArea consoleOutputJTextArea;
    private JList<String> constructorJList;
    private JList<String> methodJList;
    private JButton classEnterB;

    // File related vars
    private java.io.File file;
    private Class<?> c;

    // Get fields, methods, exceptions and constructors
    private Field[] classFields;
    private Method[] classMethods;
    private Constructor<?>[] classConstructors;

    // GUI Related
    //Constructor<?> constructor;
    Object object;

    private static final boolean DEBUG = true;

    public JavaPGUI() {
        setContentPane(javaPGUIP); // Link to designed form
        setTitle("javap GUI Testing Tool");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(true);
        setLocationRelativeTo(null); // Set GUI at center of screen
        setVisible(true);
        setSize(640,440); // From XML source

        // If I just force the user to find me the .class file, it ignores ambiguity and makes my life easier
        closeFileB.setVisible(false);
        testClassL.setVisible(false);
        testClassF.setVisible(false);
        classEnterB.setVisible(false);

        openFileB.addActionListener(openFileBActionListener()); // Open file

    }

    private ActionListener openFileBActionListener() { // This sets the class for future operations
        return e -> {
            // Set current directory to last for ease of use
            //JFileChooser jFileChooser = new JFileChooser(file != null ? file.getPath() : "");
            JFileChooser jFileChooser = new JFileChooser("src");
            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                file = jFileChooser.getSelectedFile();

            if(file!=null) { // Make sure
                // Do NOT use file.getClass() because it extends File through Class<? extends File>
                // You will return an unrelated class
                // Now loop through until we get the right class

                c = null;
                object = null;
                int trials;
                ArrayList<URL> urls = new ArrayList<>();
                ArrayList<String> classNames = new ArrayList<>();

                // Count '/' for directory depth
                try { trials = file.toURI().toURL().toString().length() - file.toURI().toURL().toString().replace("/", "").length(); }
                catch (MalformedURLException ex) { throw new RuntimeException(ex); }

                try {
                    urls.add(file.toURI().toURL()); // Add full URL
                    classNames.add(""); // Ignore full URL package (there isn't any)
                    // For URLs
                    File tempFile = file.getParentFile();

                    for (int i = 1; i < trials - 1; i++) { // Add all other URLs
                        classNames.add(tempFile.getName() + (i == 1 ? "" : "." + classNames.get(i - 1)));
                        tempFile = tempFile.getParentFile();
                        urls.add(tempFile.toURI().toURL());
                    }
                } catch (MalformedURLException ex) { throw new RuntimeException(ex); }

                for(int i=0;i<urls.size();i++) {
                    if(DEBUG) { // DELETE LATER
                        System.out.println("+++++++++++\t" + i);
                        System.out.println(urls.get(i));
                        System.out.println(classNames.get(i) + (i == 0 ? "" : "." + file.getName().substring(0, file.getName().lastIndexOf('.'))));
                        System.out.println("+++++++++++");
                    }

                    try {
                        c = Class.forName( // Store class if complete
                                classNames.get(i) + (i == 0 ? file.getName().substring(0, file.getName().lastIndexOf('.')) : "." +
                                        file.getName().substring(0, file.getName().lastIndexOf('.'))), // Ignore extension
                                true,
                                new URLClassLoader(new URL[]{urls.get(i)})); // Extract path
                    } catch (ClassNotFoundException ignored) { }

                    if(c!=null) {
                        // Pre-checks
                        updateClassFCM(); // Update class field, constructors, and methods
                        updateConstructorJList(); // Update constructors
                        updateMethodJList(); // Update methods
                        newObjectButton.addActionListener(updateConstructor()); // Get constructor
                        runButton.addActionListener(runButtonActionListener());
                        break;
                    }
                }
            }
        };
    }

    private void updateClassFCM() {
        classMethods       = c.getDeclaredMethods();
        classConstructors  = c.getDeclaredConstructors();
    }

    private void updateConstructorJList() {
        // We're lazy out here
        constructorJList.setListData(Arrays.stream(classConstructors).
                map(constructor -> constructor.toString().replace(
                        (c.getPackage() == null ? "" : c.getPackage().getName())
                                + ".", "")).toArray(String[]::new));
    }

    private void updateMethodJList() {
        // Lazy again
        methodJList.setListData(Arrays.stream(classMethods).
                map(method -> method.toString().replace(c.getName() + ".","")).toArray(String[]::new));
    }

    private ActionListener updateConstructor() {
        return e -> {
            Constructor<?> selectedConstructor = classConstructors[constructorJList.getSelectedIndex()];

            // Get the parameter types of the constructor
            Class<?>[] parameterTypes = selectedConstructor.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> paramType = parameterTypes[i];
                if (paramType.isPrimitive()) {
                    String userInput = JOptionPane.showInputDialog((paramType).getSimpleName());
                    args[i] = convertToType(userInput, paramType);
                } else {
                    // For non-primitive types, prompt user to select constructor
                    Constructor<?>[] constructors = paramType.getDeclaredConstructors();

                    JComboBox<String> constructorComboBox = new JComboBox<>(
                            Arrays.stream(constructors).map(constructor1 -> constructor1.toString().replace(
                            (c.getPackage() == null ? "" : c.getPackage().getName())
                                    + ".", "")).toArray(String[]::new));
                    constructorComboBox.setSelectedIndex(0);

                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Select Constructor for " + paramType.getSimpleName() + ":"));
                    panel.add(constructorComboBox);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Select Constructor",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        Constructor<?> selectedParamConstructor = constructors[constructorComboBox.getSelectedIndex()];
                        if (selectedParamConstructor != null) {
                            Class<?>[] paramParameterTypes = selectedParamConstructor.getParameterTypes();
                            Object[] paramArgs = new Object[paramParameterTypes.length];
                            for (int j = 0; j < paramParameterTypes.length; j++) {
                                String userInput = JOptionPane.showInputDialog(paramParameterTypes[j].getSimpleName());
                                paramArgs[j] = convertToType(userInput, paramParameterTypes[j]);
                            }
                            try {
                                args[i] = selectedParamConstructor.newInstance(paramArgs);
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
// User clicked cancel, handle accordingly
                    }
                }
            }

            try {
                // Create an instance using the selected constructor and arguments
                object = selectedConstructor.newInstance(args);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }


    private Object convertToType(String userInput, Class<?> targetType) {
        // Implement conversion logic based on targetType (e.g., Integer.parseInt for int)
        // Add more cases as needed for different types
        if (targetType == int.class) {
            return Integer.parseInt(userInput);
        } else if (targetType == String.class) {
            return userInput;
        }
        // Add more cases as needed

        throw new IllegalArgumentException("Unsupported parameter type: " + targetType);
    }

    private ActionListener runButtonActionListener() {
        return e -> {
            if (object == null) {
                JOptionPane.showMessageDialog(null, "Please create an object first.");
                return;
            }

            Method selectedMethod = classMethods[methodJList.getSelectedIndex()];

            Class<?>[] parameterTypes = selectedMethod.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                String userInput = JOptionPane.showInputDialog("Enter value for parameter " + i);
                args[i] = convertToType(userInput, parameterTypes[i]);
            }

            try {
                Object result = selectedMethod.invoke(object, args);
                consoleOutputJTextArea.append(result.toString()+"\n");
            } catch (IllegalAccessException | IllegalArgumentException |
                     InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        };
    }


}