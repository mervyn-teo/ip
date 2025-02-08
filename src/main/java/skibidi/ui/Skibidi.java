package skibidi.ui;

import java.util.List;
import java.util.Scanner;

import skibidi.command.Command;
import skibidi.storage.Storage;
import skibidi.task.Task;


/**
 * Represents the main application class that manages the interaction between the user interface (UI),
 * storage, and task management commands.
 * <p>
 * The Skibidi application starts here by initializing storage, UI, and executing the main run loop
 * that listens for user input and processes commands accordingly.
 * </p>
 */
public class Skibidi {
    private final Storage storage;
    private final UI ui;
    private final List<Task> listItems;

    /**
     * Constructs a Skibidi object with specified storage and UI instances.
     *
     * @param storage The storage manager responsible for saving and loading tasks.
     * @param ui      The UI handler for displaying messages and task lists.
     */
    public Skibidi(Storage storage, UI ui) {
        this.storage = storage;
        this.ui = ui;
        this.listItems = storage.loadList();
    }

    /**
     * Starts the Skibidi application. It greets the user, then continuously listens
     * for user commands to manage tasks until terminated.
     * It delegates the command processing to the {@code Command} class.
     */
    public void run() {
        ui.printContent(Messages.GREET);
        Scanner in = new Scanner(System.in);
        while (true) {
            String userChoice = in.nextLine();
            Command command = new Command(listItems, storage, ui);
            command.processCommand(userChoice);
        }
    }

    /**
     * Main method of the application. Initializes dependencies and starts the application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Storage storage = new Storage("saved_list.json");
        UI ui = new UI();
        new Skibidi(storage, ui).run();
    }
}
