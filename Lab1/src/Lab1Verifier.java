import javax.swing.*;

/**
 * Utility class for verifying Lab1 inputs
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */
public final class Lab1Verifier { // Finalize as there is no reason to inherit or extend
    /**
     * Enforce Utility Class
     */
    private Lab1Verifier() { }

    /**
     * Verifies name input
     *
     * @param input name
     * @return acceptable name or not
     * @throws NameException
     */
    public static boolean nameVerify(JComponent input) throws NameException {
        String inputText = ((JTextField) input).getText(); // We're only concerned with the text

        // Throw length exception when length is not [1, 15]
        if (!(inputText.length() >= 1 && inputText.length() <= 15))
            throw new NameException("0 <= (" + inputText.length() + ") characters <= 15 is invalid.");

        // Check accepted characters (' ', '-', alphabet, ',')
        for (int i = 0; i < inputText.length(); i++) // Check if any outlier character exists
            if (Character.isDigit(inputText.charAt(i)))
                throw new NameException("a digit exists."); // Throw error otherwise
            else if (   !Character.isLetter(inputText.charAt(i))    &&
                        !Character.isSpaceChar(inputText.charAt(i)) &&
                        inputText.charAt(i) != '-'                  &&
                        inputText.charAt(i) != ',')
                throw new NameException("a special character exists."); // Throw error otherwise

        return true; // Only true if no errors are thrown
    }

    /**
     * Verifies age input
     * @param input age
     * @return acceptable age or not
     * @throws AgeException
     */
    public static boolean ageVerify(JComponent input) throws AgeException {
        String inputText = ((JTextField) input).getText();

        // Empty age
        if(inputText.isEmpty())
            throw new AgeException("Age is empty.");

        int inputNumber;

        // Catch exception when String -> int isn't possible
        try { inputNumber = Integer.parseInt(inputText); }
        catch (NumberFormatException e) { throw new AgeException("Input is not in digits."); }

        // Age exception when age is not [0, 100]
        if (inputNumber < 0 || inputNumber > 100)
            throw new AgeException("0 <= ("+inputNumber+") age <= 100 is invalid.");

        // Returns true when no errors are thrown
        return true;
    }
}