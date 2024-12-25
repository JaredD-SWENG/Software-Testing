import javafx.util.Pair;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class CoverageTool extends JFrame {
    private JPanel coverageTool;
    private JButton openDirectoryB;
    private JList<String> classJL;
    private JTextPane codeJTP;
    private JSplitPane splitPane;
    private JPanel codeJP;
    private JTextPane executionJTP;
    private JButton runB;

    // Global Vars
    private File directory = null;
    private ArrayList<File> directoryClassFiles = new ArrayList<>();
    private Class<?> c;
    private Map<String, Integer> executionCounts = new HashMap<>();
    private ArrayList<Pair<String, String>> classConstructorsLookup = new ArrayList<>();
    private ArrayList<Pair<String, String>> classMethodsLookup = new ArrayList<>();


    public CoverageTool() {
        setContentPane(coverageTool); // Link to designed form
        setTitle("Coverage Tool");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(true);
        setLocationRelativeTo(null); // Set GUI at center of screen
        setVisible(true);
        setSize(800, 450); // I felt it
        splitPane.setDividerLocation(180);

        openDirectoryB.addActionListener(openDirectoryButtonListener()); // Get ".class" files within the directory
        classJL.addListSelectionListener(classListSelectionListener()); // Get which ".class" file was selected
        runB.addActionListener(e -> {
            if (c != null) {
                try {
                    // Execute the main method of the selected class
                    Method mainMethod = c.getDeclaredMethod("main", String[].class);
                    mainMethod.invoke(null, (Object) new String[] {});
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // Update the execution count
                updateExecutionCount();
            }
        });
    }

    private void updateExecutionCount() {
        // Reset the execution counts
        executionCounts.clear();

        if (c != null) {
            // Iterate through methods and constructors and initialize the counts to 0
            for (Method method : c.getDeclaredMethods()) {
                executionCounts.put(method.getName(), 0);
                updateExecutionText(getLineIndex(getValueFromMethodPair(String.valueOf(method))), 0);
            }
            for (Constructor<?> constructor : c.getDeclaredConstructors()) {
                executionCounts.put(constructor.getName(), 0);
                updateExecutionText(getLineIndex(getValueFromConstructorPair(String.valueOf(constructor))), 0);
            }
        }
    }

    private String getValueFromConstructorPair(String K) {
        for (Pair<String, String> stringStringPair : classConstructorsLookup)
            if (Objects.equals(stringStringPair.getKey(), K))
                return stringStringPair.getValue();
        return "";
    }

    private String getValueFromMethodPair(String K) {
        for (Pair<String, String> stringStringPair : classMethodsLookup)
            if (Objects.equals(stringStringPair.getKey(), K))
                return stringStringPair.getValue();
        return "";
    }

    private void resetParameters() {
        c = null;                       // Reset class, probably not needed
        directory = null;               // Reset directory
        directoryClassFiles.clear();    // Reset stored class files
        codeJTP.setText("");            // Reset code text
        executionJTP.setText("");       // Reset execution text
        classMethodsLookup.clear();
        classConstructorsLookup.clear();
    }

    private ActionListener openDirectoryButtonListener() {
        return e -> {
            resetParameters();
            JFileChooser jFileChooser = new JFileChooser("out/production");
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                directory = jFileChooser.getSelectedFile();

                if (directory != null) {
                    directoryClassFiles.addAll(Arrays.stream(directory.listFiles()).filter(file -> file.getName().endsWith(".class")).collect(Collectors.toList()));
                    classJL.setListData(directoryClassFiles.stream().map(File::getName).toArray(String[]::new));
                }
            }
        };
    }

    private ListSelectionListener classListSelectionListener() {
        return e -> {
            if(!e.getValueIsAdjusting()) { // Only want to run once
                codeJTP.setText("");            // Reset code text
                executionJTP.setText("");       // Reset execution text
                try { updateCodeJTP(getFile(classJL.getSelectedIndex())); }
                catch (Exception ignored) { }
            }
        };
    }

    private void updateCodeJTP(File file) {
        int trials;
        ArrayList<URL> urls = new ArrayList<>();
        ArrayList<String> classNames = new ArrayList<>();

        // Count '/' for directory depth
        try {
            trials = file.toURI().toURL().toString().length() - file.toURI().toURL().toString().replace("/", "").length();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }

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
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }

        for (int i = 0; i < urls.size(); i++) {
            try {
                c = Class.forName( // Store class if complete
                        classNames.get(i) + (i == 0 ? file.getName().substring(0, file.getName().lastIndexOf('.')) : "." +
                                file.getName().substring(0, file.getName().lastIndexOf('.'))), // Ignore extension
                        true,
                        new URLClassLoader(new URL[]{urls.get(i)})); // Extract path
            } catch (ClassNotFoundException | NoClassDefFoundError ignored) { } // Ignore all exceptions so class can be found by loop
        }

        if (c != null) {
            Package classPackage                = c.getPackage();
            Field[] classFields                 = c.getDeclaredFields();
            Constructor<?>[] classConstructors  = c.getDeclaredConstructors();
            Method[] classMethods               = c.getDeclaredMethods();

            if (classPackage != null) appendToDocument(codeJTP.getDocument(), "package "+ classPackage.getName() + ";\n");

            String interfaces = Arrays.toString(Arrays.stream(c.getInterfaces()).map(Class::getSimpleName).toArray(String[]::new));
            appendToDocument(codeJTP.getDocument(), Modifier.toString(c.getModifiers()).replace("interface", "") + (c.isInterface() ? "interface " : (c.isPrimitive() ? "" : " class ")) + c.getSimpleName() + (c.getSuperclass()!=null?" extends "+c.getSuperclass().getSimpleName():"") + (interfaces.length()==2?"":" implements "+interfaces.substring(1, interfaces.length()-1)) + " {");

            if(classFields.length > 0) appendToDocument(codeJTP.getDocument(), "\n// Fields\n");
            for(Field f : classFields)
                appendToDocument(codeJTP.getDocument(), ((f.getModifiers() == 0) ? "" : (Modifier.toString(f.getModifiers()) + " ")) + f.getType().getSimpleName() + " " + f.getName() + ";\n");

            if(classConstructors.length > 0) appendToDocument(codeJTP.getDocument(), "\n// Constructors\n");
            for(Constructor<?> c : classConstructors) {
                String parameters = Arrays.toString(Arrays.stream(c.getParameterTypes()).map(Class::getSimpleName).toArray(String[]::new));
                String exceptions = Arrays.toString(Arrays.stream(c.getExceptionTypes()).map(Class::getSimpleName).toArray(String[]::new));
                String text = Modifier.toString(c.getModifiers()) + " " + c.getDeclaringClass().getSimpleName() + "(" + parameters.substring(1, parameters.length()-1) + ")" + (exceptions.length()==2?"":(" throws new"+exceptions.substring(1, exceptions.length()-1)));
                appendToDocument(codeJTP.getDocument(), text + "{ }\n");
                classConstructorsLookup.add(new Pair<>(String.valueOf(c), text));
            }

            if(classMethods.length > 0) appendToDocument(codeJTP.getDocument(), "\n// Methods\n");
            for(Method m : classMethods) {
                String parameters = Arrays.toString(Arrays.stream(m.getParameterTypes()).map(Class::getSimpleName).toArray(String[]::new));
                String exceptions = Arrays.toString(Arrays.stream(m.getExceptionTypes()).map(Class::getSimpleName).toArray(String[]::new));
                String text = Modifier.toString(m.getModifiers()) + (m.isDefault()?" default ":" ") + m.getReturnType().getSimpleName() + " " + m.getName() + "(" + parameters.substring(1, parameters.length()-1) + ")" + (exceptions.length()==2?"":(" throws new"+exceptions.substring(1, exceptions.length()-1)));
                appendToDocument(codeJTP.getDocument(), text + " { }\n");
                classMethodsLookup.add(new Pair<>(String.valueOf(m), text));
            }

            appendToDocument(codeJTP.getDocument(), "}");

            // Initialize text
            for(int i=0;i<=codeJTP.getText().replace("\n", "").split(Character.toString((char) 13)).length;i++)
                appendToDocument(executionJTP.getDocument(), "\n");

            updateExecutionCount();
        }
    }

    private int getLineIndex(String text) {
        // Split by carriage return
        String[] textArr = codeJTP.getText().replace("\n", "").split(Character.toString((char) 13));

        for (int i = 0; i < textArr.length; i++)
            if (textArr[i].contains(text))
                return i + 1;
        return -1;
    }

    private void updateExecutionText(int lineIndex, int replaceWith) {
        String[] textArr = executionJTP.getText().split(Character.toString((char) 13));
        int offSet = 0, currentLineOffset = 0;

        for(int i=0;i<textArr.length;i++) {
            offSet+=textArr[i].length();
            if(i==lineIndex-1)  {
                currentLineOffset = offSet;
                offSet -= textArr[i].length()-2;
                break;
            }
        }

        try {
            executionJTP.getDocument().remove(offSet-1, currentLineOffset-offSet+1);
            executionJTP.getDocument().insertString(offSet-1, String.valueOf(replaceWith), null);
        }
        catch (BadLocationException ignored) { }
    }

    private void appendToDocument(Document document, String text) {
        // if (document.getLength() != 0) text += ("\n");
        try { document.insertString(document.getLength(), text, null);}
        catch (BadLocationException ignored) { }
    }

    private File getFile(int index) {
        return directoryClassFiles.get(index);
    }
}