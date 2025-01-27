package skibidi.ui;

import java.util.ArrayList;

/**
 * Manages the user interface for the Skibidi application. This class handles
 * printing formatted content and messages to the console.
 * <p>
 * Provides functionality to display task-related messages, lists, and other
 * user feedback in the Skibidi application.
 * </p>
 */
public class UI {
    public void printContent(String content) {
        System.out.println(Messages.spacer);
        String[] splitted = content.split("\n");
        for (String s : splitted) {
            System.out.println("     " + s);
        }
        System.out.println(Messages.spacer);
    }

    /**
     * Prints items in a given list to the console in a numbered format.
     *
     * @param content An {@link ArrayList} of items to display.
     * @param <T>     The type of items in the list.
     */
    public <T> void printContent(ArrayList<T> content) {
        System.out.println(Messages.spacer);
        int i = 1;
        for (T s : content) {
            System.out.println("     " + i + ". " + s);
            i++;
        }
        System.out.println(Messages.spacer);
    }

    /**
     * Prints a formatted message of the item added to the console.
     *
     * @param content The message content to display.
     */
    public void printAdded(String content, int index) {
        this.printContent("added: " + content + "\nthere are " + index + " tasks in the list now");
    }

    /**
     * Prints a formatted message of the item removed to the console.
     *
     * @param content The message content to display.
     */
    public void printRemoved(String content, int index) {
        this.printContent("removed: " + content + "\nthere are " + index + " tasks in the list now");
    }
}
