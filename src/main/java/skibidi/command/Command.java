package skibidi.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import skibidi.storage.Storage;
import skibidi.task.Deadline;
import skibidi.task.Event;
import skibidi.task.Task;
import skibidi.task.ToDo;
import skibidi.ui.Messages;
import skibidi.ui.UI;

/**
 * The {@code Command} class processes and executes user commands in a task management application.
 * It serves as the controller that interacts with the task list, storage, and user interface to
 * process various task-related actions.
 * <p>
 * Supported commands include:
 * - adding different types of tasks (e.g., ToDo, Deadline, Event),
 * - marking tasks as done or undone,
 * - deleting tasks,
 * - listing all tasks,
 * - exiting the application.
 * </p>
 */
public class Command {
    private final List<Task> listItems;
    private final Storage storage;
    private final UI ui;

    /**
     * Constructs a {@code Command} object initialized with the task list,
     * storage handler, and user interface.
     *
     * @param listItems the task list
     * @param storage   the storage manager responsible for saving and loading tasks
     * @param ui        the user interface for displaying output to the user
     */
    public Command(List<Task> listItems, Storage storage, UI ui) {
        this.listItems = listItems;
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * Processes a user command and executes related action.
     *
     * @param userChoice the command entered by the user
     */
    public String processCommand(String userChoice) {
        String msg;
        if (userChoice.isEmpty()) {
            msg = ui.getContent(Messages.EMPTY_COMMAND);
        } else {
            // convert user choice to enum
            CommandType userChoiceEnum;
            userChoiceEnum = Parser.parse(userChoice);

            String[] splitUserchoice = userChoice.split(" ", 2);
            switch (userChoiceEnum) {
            case BYE: {
                msg = bye();
                break;
            }
            case LIST: {
                msg = list();
                break;
            }
            case MARK: {
                msg = markDone(splitUserchoice);
                break;
            }
            case UNMARK: {
                msg = markUndone(splitUserchoice);
                break;
            }
            case TODO: {
                msg = addTodo(splitUserchoice);
                break;
            }
            case EVENT: {
                msg = addEvent(splitUserchoice);
                break;
            }
            case DEADLINE: {
                msg = addDeadline(splitUserchoice);
                break;
            }
            case DELETE: {
                msg = deleteTask(splitUserchoice);
                break;
            }
            case FIND: {
                msg = findTask(splitUserchoice);
                break;
            }
            default: {
                msg = defaultBehaviour();
            }
            }
        }
        return msg;
    }

    /**
     * Handles cases of unrecognized or invalid commands.
     */
    private String defaultBehaviour() {
        return ui.getContent(Messages.CONFUSED);
    }


    private String findTask(String[] splitUserchoice) {
        String msg = "";
        if (listItems.isEmpty()) {
            ui.getContent(Messages.EMPTY_LIST);
            return msg;
        }
        String wordToFind = splitUserchoice[1];
        List<Task> taskFound = new ArrayList<>();
        for (Task task : listItems) {
            if (task.toString().contains(wordToFind)) {
                taskFound.add(task);
            }
        }

        if (taskFound.isEmpty()) {
            msg = ui.getContent("I cannot find any task that fulfill your requirement");
        } else {
            msg = ui.getContent(taskFound);
        }
        return msg;
    }

    /**
     * Exits the application with a message.
     */
    private String bye() {
        return ui.getContent(Messages.BYE);
    }

    /**
     * Lists all tasks in the task list. Displays an error message if the list is empty.
     */
    private String list() {
        String msg = "";
        if (listItems.isEmpty()) {
            msg = ui.getContent(Messages.EMPTY_LIST);
        } else {
            msg = ui.getContent(listItems);
        }
        return msg;
    }

    /**
     * Marks choosen task as done.
     *
     * @param splitUserchoice the split string containing the command and task number
     */
    private String markDone(String[] splitUserchoice) {
        String msg = "";
        if (splitUserchoice.length != 2) {
            msg = ui.getContent(Messages.DOUBLE_CHECK);
            return msg;
        }
        try {
            if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)) {
                msg = ui.getContent(Messages.OUT_OF_BOUNDS);
            } else {
                Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                taskItem.markDone();
                msg = ui.getContent(Messages.MARK_DONE + taskItem);
                storage.saveList(listItems);
            }
        } catch (NumberFormatException e) {
            msg = ui.getContent(Messages.NAN_ERROR);
        }
        return msg;
    }

    /**
     * Marks choosen task as undone.
     *
     * @param splitUserchoice the split string containing the command and task number
     */
    private String markUndone(String[] splitUserchoice) {
        String msg = "";
        if (splitUserchoice.length != 2) {
            msg = ui.getContent(Messages.DOUBLE_CHECK);
            return msg;
        }
        try {
            if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)) {
                msg = ui.getContent(Messages.OUT_OF_BOUNDS);
            } else {
                Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                taskItem.markUndone();
                msg = ui.getContent(Messages.MARK_UNDONE + taskItem);
                storage.saveList(listItems);
            }
        } catch (NumberFormatException e) {
            msg = ui.getContent(Messages.NAN_ERROR);
        }
        return msg;
    }

    /**
     * Adds a {@code ToDo} task to the list based on the user's input.
     *
     * @param splitUserchoice the split string containing the command and task description
     */
    private String addTodo(String[] splitUserchoice) {
        String msg = "";
        if (splitUserchoice.length == 1 || splitUserchoice[1].isEmpty()) {
            msg = ui.getContent(Messages.EMPTY_TODO);
        } else {
            msg = addTask(new ToDo(splitUserchoice[1]));
        }
        return msg;
    }

    /**
     * Adds a {@code Event} task to the list based on the user's input.
     *
     * @param splitUserchoice the split string containing the command and task description
     */
    private String addEvent(String[] splitUserchoice) {
        String msg = "";
        if (splitUserchoice.length != 2) {
            msg = ui.getContent(Messages.EMPTY_EVENT);
            return msg;
        }
        String[] splitted = splitUserchoice[1].split("/from | /to ", 3);
        if (splitted.length != 3) {
            msg = ui.getContent(Messages.DOUBLE_CHECK);
        } else {
            LocalDate fromDate;
            LocalDate toDate;
            try {
                fromDate = LocalDate.parse(splitted[1]);
                toDate = LocalDate.parse(splitted[2]);
            } catch (DateTimeParseException e) {
                msg = ui.getContent(Messages.DATE_FORMAT);
                return msg;
            }

            if (fromDate.isAfter(toDate)) {
                msg = ui.getContent(Messages.DATE_CONFLICT);
                return msg;
            }
            msg = addTask(new Event(splitted[0], fromDate, toDate));
        }
        return msg;
    }

    /**
     * Deletes a task from the list based on the user's input.
     *
     * @param splitUserchoice the split string containing the command and task description
     */
    private String deleteTask(String[] splitUserchoice) {
        String msg = "";
        if (splitUserchoice.length != 2) {
            msg = ui.getContent(Messages.DOUBLE_CHECK);
        } else {
            try {
                if (!isValidIndex(Integer.parseInt(splitUserchoice[1]) - 1, listItems)) {
                    msg = ui.getContent(Messages.OUT_OF_BOUNDS);
                } else {
                    int index = Integer.parseInt(splitUserchoice[1]) - 1;
                    msg = ui.getRemoved(listItems.get(index).toString(), (listItems.size() - 1));
                    listItems.remove(index);
                    storage.saveList(listItems);
                }
            } catch (NumberFormatException e) {
                msg = ui.getContent(Messages.NAN_ERROR);
            }
        }
        return msg;
    }

    /**
     * Adds a {@code Deadline} task to the list based on the user's input.
     *
     * @param splitUserchoice the split string containing the command and task description
     */
    private String addDeadline(String[] splitUserchoice) {
        String msg = "";
        if (splitUserchoice.length != 2) {
            msg = ui.getContent(Messages.EMPTY_EVENT);
            return msg;
        }
        String[] splitTaskTime = splitUserchoice[1].split("/by ", 2);
        if (splitTaskTime.length != 2) {
            msg = ui.getContent(Messages.DOUBLE_CHECK);
        } else {
            LocalDate byDate;
            try {
                byDate = LocalDate.parse(splitTaskTime[1]);
            } catch (DateTimeParseException e) {
                msg = ui.getContent(Messages.DATE_FORMAT);
                return msg;
            }
            msg = addTask(new Deadline(splitTaskTime[0], byDate));
        }
        return msg;
    }

    /**
     * Validates whether the provided index for a task is within the bounds
     * of the task list.
     *
     * @param index     the task index to check
     * @param listItems the task list to validate against
     * @return {@code true} if the index is valid, {@code false} otherwise
     */
    private boolean isValidIndex(int index, List<Task> listItems) {
        return index >= 0 && index < listItems.size();
    }

    /**
     * Adds a given task to the list, returns a confirmation message, and saves the list to storage.
     *
     * @param task the task to add
     */
    private String addTask(Task task) {
        listItems.add(task);
        String msg = ui.getAdded(task.toString(), listItems.size());
        storage.saveList(listItems);
        return msg;
    }
}
