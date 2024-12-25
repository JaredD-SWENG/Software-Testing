import javax.swing.*;

/**
 * Lab1 GUI Program
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */
public class Lab1 extends JFrame{

    private JTextField nameTextField;
    private JPanel mainPanel;
    private JTextField ageTextField;
    private JButton submitButton;

    private boolean nameVerified = false;
    private boolean ageVerified  = false;

    /**
     * Creates Lab1 GUI
     */
    private Lab1() {
        setContentPane(mainPanel); // Link to the designed form
        setTitle("Lab1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null); // Center
        setVisible(true);

        verifyInputs();

        // Enable/Disable submit button
        nameTextField.addKeyListener(submitButtonVerificationListener());
        ageTextField.addKeyListener(submitButtonVerificationListener());

        // Output input when it's correct
        submitButton.addActionListener(e -> System.out.println("Name: " + nameTextField.getText() + "\tAge: " + ageTextField.getText()));
    }

    /**
     * Used for all other buttons to enable/disable the submit button
     * @return {@link java.awt.event.KeyAdapter}
     */
    private java.awt.event.KeyAdapter submitButtonVerificationListener() {
        return new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    submitButton.setEnabled(Lab1Verifier.nameVerify(nameTextField) && Lab1Verifier.ageVerify(ageTextField));
                } catch (Exception ignored) {
                    submitButton.setEnabled(false);
                }
            }};
    }

    /**
     * Stores all input verifications
     */
    private void verifyInputs() {
        // Name verifier
        InputVerifier nameInputVerifier = new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                try {
                    nameVerified = Lab1Verifier.nameVerify(input);
                    return nameVerified;
                } catch (NameException e) { System.out.println(e); } return false;
            }
        }; nameTextField.setInputVerifier(nameInputVerifier);

        // Age verifier
        InputVerifier ageInputVerifier = new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                try {
                    ageVerified = Lab1Verifier.ageVerify(input);
                    return ageVerified;
                } catch (AgeException e)  { System.out.println(e); } return false;
            }
        }; ageTextField.setInputVerifier(ageInputVerifier);
    }

    public static void main(String[] args) {
        Lab1 lab1 = new Lab1();
    }
}