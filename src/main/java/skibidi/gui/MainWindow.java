package skibidi.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import skibidi.command.Command;
import skibidi.storage.Storage;
import skibidi.task.Task;
import skibidi.ui.Messages;
import skibidi.ui.UI;

import java.util.List;

public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final Storage storage;
    private final UI ui;
    private final List<Task> listItems;

    public MainWindow() {
        this.storage = new Storage("saved_list.json");
        this.ui = new UI();
        this.listItems = storage.loadList();
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(DialogBox.getDialogBox(Messages.GREET, DialogBox.dialogType.SKIBIDI));
    }

    @FXML
    private void handleUserInput() {
        dialogContainer.getChildren().addAll(DialogBox.getDialogBox(userInput.getText(), DialogBox.dialogType.USER));
        Command command = new Command(listItems, storage, ui);
        dialogContainer.getChildren().addAll(DialogBox.getDialogBox(command.processCommand(userInput.getText()), DialogBox.dialogType.SKIBIDI));
        if (userInput.getText().equalsIgnoreCase("bye")) {
            System.exit(0);
        }
        userInput.clear();
    }

}
