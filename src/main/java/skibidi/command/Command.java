package skibidi.command;

import skibidi.storage.Storage;
import skibidi.task.Deadline;
import skibidi.task.Event;
import skibidi.task.Task;
import skibidi.task.ToDo;
import skibidi.ui.Messages;
import skibidi.ui.UI;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class Command {
    private final List<Task> listItems;
    private final Storage storage;
    private final UI ui;

    public Command(List<Task> listItems, Storage storage, UI ui) {
        this.listItems = listItems;
        this.storage = storage;
        this.ui = ui;
    }

    public void processCommand(String userChoice) {
        if (userChoice.isEmpty()) {
            ui.printContent(Messages.EMPTY_COMMAND);
        } else {
        // convert user choice to enum
            Parser.CommandType userChoiceEnum;
            userChoiceEnum = Parser.parse(userChoice);

            String[] splitUserchoice = userChoice.split(" ", 2);
            switch (userChoiceEnum) {
                case BYE: {
                    bye();
                    break;
                }
                case LIST: {
                    list();
                    break;
                }
                case MARK: {
                    markDone(splitUserchoice);
                    break;
                }
                case UNMARK: {
                    markUndone(splitUserchoice);
                    break;
                }
                case TODO: {
                    addTodo(splitUserchoice);
                    break;
                }
                case EVENT: {
                    addEvent(splitUserchoice);
                    break;
                }
                case DEADLINE: {
                    addDeadline(splitUserchoice);
                    break;
                }
                case DELETE: {
                    deleteTask(splitUserchoice);
                    break;
                }
                case FIND: {
                    findTask(splitUserchoice);
                    break;
                }
                default: {
                    defaultBehaviour();
                    }
                }
        }
    }

    private void defaultBehaviour() {
        ui.printContent(Messages.CONFUSED);
    }


    private void findTask(String[] splitUserchoice) {
        if (listItems.isEmpty()) {
            ui.printContent(Messages.emptyList);
            return;
        }
        String wordToFind = splitUserchoice[1];
        List<Task> taskFound = new ArrayList<>();
        for (Task task : listItems) {
            if (task.toString().contains(wordToFind)) {
                taskFound.add(task);
            }
        }

        if (taskFound.isEmpty()) {
            ui.printContent("I cannot find any task that fulfill your requirement");
        } else {
            ui.printContent(taskFound);
        }
    }

    private void bye() {
        ui.printContent(Messages.BYE);
        System.exit(0);
    }

    private void list() {
        if (listItems.isEmpty()) {
            ui.printContent(Messages.EMPTY_lIST);
        } else {
            ui.printContent(listItems);
        }
    }

    private void markDone(String[] splitUserchoice) {
        if (splitUserchoice.length != 2){
            ui.printContent(Messages.DOUBLE_CHECK);
            return;
        }
        try {
            if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)){
                ui.printContent(Messages.OUT_OF_BOUNDS);
            } else {
                Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                taskItem.markDone();
                ui.printContent(Messages.MARK_DONE + taskItem);
                storage.saveList(listItems);
            }
        } catch (NumberFormatException e) {
            ui.printContent(Messages.NAN_ERROR);
        }
    }

    private void markUndone(String[] splitUserchoice) {
        if (splitUserchoice.length != 2){
            ui.printContent(Messages.DOUBLE_CHECK);
            return;
        }
        try {
            if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)){
                ui.printContent(Messages.OUT_OF_BOUNDS);
            } else {
                Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                taskItem.markUndone();
                ui.printContent(Messages.MARK_UNDONE + taskItem);
                storage.saveList(listItems);
            }
        } catch (NumberFormatException e) {
            ui.printContent(Messages.NAN_ERROR);
        }
    }

    private void addTodo(String[] splitUserchoice) {
        if (splitUserchoice.length == 1 || splitUserchoice[1].isEmpty()) {
            ui.printContent(Messages.EMPTY_TODO);
        } else {
            addTask(new ToDo(splitUserchoice[1]));
        }
    }

    private void addEvent(String[] splitUserchoice) {
        if (splitUserchoice.length != 2){
            ui.printContent(Messages.EMPTY_EVENT);
            return;
        }
        String[] splitted = splitUserchoice[1].split("/from | /to ", 3);
        if (splitted.length != 3) {
            ui.printContent(Messages.DOUBLE_CHECK);
        } else {
            LocalDate fromDate;
            LocalDate toDate;
            try {
                fromDate = LocalDate.parse(splitted[1]);
                toDate = LocalDate.parse(splitted[2]);
            } catch (DateTimeParseException e) {
                ui.printContent(Messages.DATE_FORMAT);
                return;
            }

            if (fromDate.isAfter(toDate)) {
                ui.printContent(Messages.DATE_CONFLICT);
                return;
            }
            addTask(new Event(splitted[0], fromDate, toDate));
        }
    }

    private void deleteTask(String[] splitUserchoice) {
        if (splitUserchoice.length != 2 ) {
            ui.printContent(Messages.DOUBLE_CHECK);
        } else {
            int index = 0;
            try {
                if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)){
                    ui.printContent(Messages.OUT_OF_BOUNDS);
                } else {
                    ui.printRemoved(listItems.get(index).toString(), (listItems.size() - 1));
                    listItems.remove(index);
                    storage.saveList(listItems);
                }
            } catch (NumberFormatException e) {
                ui.printContent(Messages.NAN_ERROR);
            }
        }
    }

    private void addDeadline(String[] splitUserchoice) {
        if (splitUserchoice.length != 2){
            ui.printContent(Messages.EMPTY_EVENT);
            return;
        }
        String[] splitTaskTime = splitUserchoice[1].split("/by ", 2);
        if (splitTaskTime.length != 2) {
            ui.printContent(Messages.DOUBLE_CHECK);
        } else {
            LocalDate byDate;
            try {
                byDate = LocalDate.parse(splitTaskTime[1]);
            } catch (DateTimeParseException e) {
                ui.printContent(Messages.DATE_FORMAT);
                return;
            }
            addTask(new Deadline(splitTaskTime[0], byDate));
        }
    }

    private boolean isValidIndex(int index, List<Task> listItems) {
        return index >= 0 && index < listItems.size();
    }

    private void addTask(Task task) {
        listItems.add(task);
        ui.printAdded(task.toString(), listItems.size());
        storage.saveList(listItems);
    }
}
