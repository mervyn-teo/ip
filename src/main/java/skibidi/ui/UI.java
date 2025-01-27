package skibidi.ui;

import java.util.ArrayList;

public class UI {
    public void printContent(String content) {
        System.out.println(Messages.spacer);
        String[] splitted = content.split("\n");
        for (String s : splitted) {
            System.out.println("     " + s);
        }
        System.out.println(Messages.spacer);
    }

    public <T> void printContent(ArrayList<T> content) {
        System.out.println(Messages.spacer);
        int i = 1;
        for (T s : content) {
            System.out.println("     " + i + ". " + s);
            i++;
        }
        System.out.println(Messages.spacer);
    }

    public void printAdded(String content, int index) {
        this.printContent("added: " + content + "\nthere are " + index + " tasks in the list now");
    }

    public void printRemoved(String content, int index) {
        this.printContent("removed: " + content + "\nthere are " + index + " tasks in the list now");
    }
}
