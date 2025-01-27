package skibidi.ui;

import skibidi.command.Command;
import skibidi.storage.Storage;
import skibidi.task.Task;

import java.util.List;
import java.util.Scanner;

public class Skibidi {
    private final Storage storage;
    private final UI ui;
    private final List<Task> listItems;

    public Skibidi(Storage storage, UI ui) {
        this.storage = storage;
        this.ui = ui;
        this.listItems = storage.loadList();
    }

    public void run() {
        ui.printContent(Messages.GREET);
        Scanner in = new Scanner(System.in);
        while (true) {
            String userChoice = in.nextLine();
            Command command = new Command(listItems, storage, ui);
            command.processCommand(userChoice);
        }
    }

    public static void main(String[] args) {
        Storage storage = new Storage("saved_list.json");
        UI ui = new UI();
        new Skibidi(storage, ui).run();
    }
}
