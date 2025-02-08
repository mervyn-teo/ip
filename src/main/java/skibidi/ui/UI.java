package skibidi.ui;

import java.util.List;

/**
 * Manages the user interface for the Skibidi application. This class handles
 * printing formatted content and messages to the console.
 * <p>
 * Provides functionality to display task-related messages, lists, and other
 * user feedback in the Skibidi application.
 * </p>
 */
public class UI {
    /**
     * Prints the given content to the console, formatting it with a spacer
     * line before and after. Each line of the content is indented for readability.
     *
     * @param content the text to be printed, with lines separated by '\n'
     */
    public String getContent(String content) {
        StringBuilder ret = new StringBuilder();
        String[] splitted = content.split("\n");
        for (String s : splitted) {
            ret.append(s).append("\n");
        }
        return ret.toString();
    }

    /**
     * Prints items in a given list to the console in a numbered format.
     *
     * @param content An {@link List} of items to display.
     * @param <T>     The type of items in the list.
     */
    public <T> String getContent(List<T> content) {
        StringBuilder ret = new StringBuilder();
        int i = 1;
        for (T s : content) {
            ret.append(i).append(". ").append(s).append("\n");
            i++;
        }
        return ret.toString();
    }

    /**
     * Prints a formatted message of the item added to the console.
     *
     * @param content The message content to display.
     */
    public String getAdded(String content, int index) {
        return "added: " + content + "\nthere are " + index + " tasks in the list now";
    }

    /**
     * Prints a formatted message of the item removed to the console.
     *
     * @param content The message content to display.
     */
    public String getRemoved(String content, int index) {
        return "removed: " + content + "\nthere are " + index + " tasks in the list now";
    }
}
