import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Command {
    private final ArrayList<Task> listItems;
    private final Storage storage;
    private final UI ui;

    public Command(ArrayList<Task> listItems, Storage storage, UI ui) {
        this.listItems = listItems;
        this.storage = storage;
        this.ui = ui;
    }

    public void processCommand(String userChoice) {
        if (userChoice.isEmpty()) {
            ui.printContent(Messages.emptyCommand);
        } else {
        // convert user choice to enum
            Parser.commandType userChoiceEnum;
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
                default: {
                    defaultBehaviour();
                    }
                }
        }
    }

    private void defaultBehaviour() {
        ui.printContent(Messages.confused);
    }

    private void bye() {
        ui.printContent(Messages.bye);
        System.exit(0);
    }

    private void list() {
        if (listItems.isEmpty()) {
            ui.printContent(Messages.emptyList);
        } else {
            ui.printContent(listItems);
        }
    }

    private void markDone(String[] splitUserchoice) {
        if (splitUserchoice.length != 2){
            ui.printContent(Messages.doubleCheck);
            return;
        }
        try {
            if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)){
                ui.printContent(Messages.outOfBounds);
            } else {
                Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                taskItem.markDone();
                ui.printContent(Messages.markDone + taskItem);
                storage.saveList(listItems);
            }
        } catch (NumberFormatException e) {
            ui.printContent(Messages.nanError);
        }
    }

    private void markUndone(String[] splitUserchoice) {
        if (splitUserchoice.length != 2){
            ui.printContent(Messages.doubleCheck);
            return;
        }
        try {
            if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)){
                ui.printContent(Messages.outOfBounds);
            } else {
                Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                taskItem.markUndone();
                ui.printContent(Messages.markUnDone + taskItem);
                storage.saveList(listItems);
            }
        } catch (NumberFormatException e) {
            ui.printContent(Messages.nanError);
        }
    }

    private void addTodo(String[] splitUserchoice) {
        if (splitUserchoice.length == 1) {
            ui.printContent(Messages.emptyTodo);
        } else {
            addTask(new toDo(splitUserchoice[1]));
        }
    }

    private void addEvent(String[] splitUserchoice) {
        if (splitUserchoice.length != 2){
            ui.printContent(Messages.emptyEvent);
            return;
        }
        String[] splitted = splitUserchoice[1].split("/from | /to ", 3);
        if (splitted.length != 3) {
            ui.printContent(Messages.doubleCheck);
        } else {
            LocalDate fromDate;
            LocalDate toDate;
            try {
                fromDate = LocalDate.parse(splitted[1]);
                toDate = LocalDate.parse(splitted[2]);
            } catch (DateTimeParseException e) {
                ui.printContent(Messages.dateFormat);
                return;
            }

            if (fromDate.isAfter(toDate)) {
                ui.printContent(Messages.dateConflict);
                return;
            }
            addTask(new Event(splitted[0], fromDate, toDate));
        }
    }

    private void deleteTask(String[] splitUserchoice) {
        if (splitUserchoice.length != 2 ) {
            ui.printContent(Messages.doubleCheck);
        } else {
            int index = 0;
            try {
                if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)){
                    ui.printContent(Messages.outOfBounds);
                } else {
                    ui.printRemoved(listItems.get(index).toString(), (listItems.size() - 1));
                    listItems.remove(index);
                    storage.saveList(listItems);
                }
            } catch (NumberFormatException e) {
                ui.printContent(Messages.nanError);
            }
        }
    }

    private void addDeadline(String[] splitUserchoice) {
        if (splitUserchoice.length != 2){
            ui.printContent(Messages.emptyEvent);
            return;
        }
        String[] splitTaskTime = splitUserchoice[1].split("/by ", 2);
        if (splitTaskTime.length != 2) {
            ui.printContent(Messages.doubleCheck);
        } else {
            LocalDate byDate;
            try {
                byDate = LocalDate.parse(splitTaskTime[1]);
            } catch (DateTimeParseException e) {
                ui.printContent(Messages.dateFormat);
                return;
            }
            addTask(new Deadline(splitTaskTime[0], byDate));
        }
    }

    private boolean isValidIndex(int index, ArrayList<Task> listItems) {
        return index >= 0 && index < listItems.size();
    }

    private void addTask(Task task) {
        listItems.add(task);
        ui.printAdded(task.toString(), listItems.size());
        storage.saveList(listItems);
    }
}
