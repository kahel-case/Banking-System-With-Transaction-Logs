package sys.utility;

import javax.swing.*;

public class DialogUtility {

    /**
     * Shows a reusable JOptionPane message dialog.
     *
     * @param title        The title of the dialog window.
     * @param message      The message to display.
     * @param messageType  One of JOptionPane constants:
     *                     INFORMATION_MESSAGE, WARNING_MESSAGE, ERROR_MESSAGE, PLAIN_MESSAGE
     */
    public static void showMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(
                null,          // parent (null = center on screen)
                message,       // message content
                title,         // dialog title
                messageType    // message type icon
        );
    }

    // --- Optional convenience methods for specific message types ---

    public static void showInfo(String title, String message) {
        showMessage(title, message, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarning(String title, String message) {
        showMessage(title, message, JOptionPane.WARNING_MESSAGE);
    }

    public static void showError(String title, String message) {
        showMessage(title, message, JOptionPane.ERROR_MESSAGE);
    }

    public static void showPlain(String title, String message) {
        showMessage(title, message, JOptionPane.PLAIN_MESSAGE);
    }
}
