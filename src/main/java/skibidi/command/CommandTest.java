package skibidi.command;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import skibidi.storage.Storage;
import skibidi.task.Deadline;
import skibidi.task.Event;
import skibidi.task.Task;
import skibidi.task.ToDo;
import skibidi.ui.Messages;
import skibidi.ui.UI;



class CommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private ArrayList<Task> listItems;
    private Storage testStorage;
    private UI testUI;
    private Command command;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        listItems = new ArrayList<>();
        testStorage = new Storage("test_storage.json");
        testUI = new UI();
        command = new Command(listItems, testStorage, testUI);
    }

    @Test
    void emptyCommand() {
        command.processCommand("");
        assertEquals("%s\r\n     %s\r\n%s\r\n"
                .formatted(Messages.SPACER, Messages.EMPTY_COMMAND, Messages.SPACER), outContent.toString());
    }

    @Test
    void listEmpty() {
        command.processCommand("LIST");
        assertEquals("%s\r\n     %s\r\n%s\r\n"
                .formatted(Messages.SPACER, Messages.EMPTY_LIST, Messages.SPACER), outContent.toString());
    }

    @Test
    void listNonEmpty() {
        listItems.add(new ToDo("Test task"));
        command.processCommand("LIST");
        assertEquals("%s\r\n     1. %s\r\n%s\r\n"
                .formatted(Messages.SPACER, listItems.get(0), Messages.SPACER), outContent.toString());
    }

    @Test
    void addTodoValid() {
        command.processCommand("TODO Read a book");
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof ToDo);
        assertEquals("%s\r\n     %s\r\n     there are 1 tasks in the list now\r\n%s\r\n"
                .formatted(
                        Messages.SPACER, "added: " + listItems.get(0), Messages.SPACER), outContent.toString());
    }

    @Test
    void addTodoEmptyDes() {
        command.processCommand("TODO ");
        assertEquals("%s\r\n     %s\r\n%s\r\n"
                .formatted(Messages.SPACER, Messages.EMPTY_TODO, Messages.SPACER), outContent.toString());
        assertTrue(listItems.isEmpty());
    }

    @Test
    void addEventValid() {
        command.processCommand("EVENT Meeting /from 2023-10-01 /to 2023-10-02");
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Event);
        Event event = (Event) listItems.get(0);
        assertEquals(LocalDate.of(2023, 10, 1), event.getStartDate());
        assertEquals(LocalDate.of(2023, 10, 2), event.getEndDate());
        assertEquals("%s\r\n     %s\r\n     there are 1 tasks in the list now\r\n%s\r\n"
                .formatted(
                        Messages.SPACER, "added: " + listItems.get(0), Messages.SPACER), outContent.toString());
    }

    @Test
    void addEventInvalidDates() {
        command.processCommand("EVENT Meeting /from 2023-10-02 /to 2023-10-01");
        assertEquals("%s\r\n     %s\r\n%s\r\n"
                .formatted(Messages.SPACER, Messages.DATE_CONFLICT, Messages.SPACER), outContent.toString());
        assertTrue(listItems.isEmpty());
    }

    @Test
    void addDeadlineValid() {
        command.processCommand("DEADLINE Meeting /by 2023-10-01");
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Deadline);
        Deadline deadline = (Deadline) listItems.get(0);
        assertEquals(LocalDate.of(2023, 10, 1), deadline.getDeadline());
        assertEquals("%s\r\n     %s\r\n     there are 1 tasks in the list now\r\n%s\r\n"
                .formatted(
                        Messages.SPACER, "added: " + listItems.get(0), Messages.SPACER), outContent.toString());
    }

    @Test
    void addDeadlineInvalidDates() {
        command.processCommand("EVENT Meeting /by 2023-13-02");
        assertEquals("%s\r\n     %s\r\n%s\r\n"
                .formatted(Messages.SPACER, Messages.DOUBLE_CHECK, Messages.SPACER), outContent.toString());
        assertTrue(listItems.isEmpty());
    }

    @Test
    void markDoneValid() {
        ToDo task = new ToDo("Test task");
        listItems.add(task);

        command.processCommand("MARK 1");

        assertTrue(task.isDone());
        assertEquals("%s\r\n     Yes this is marked as done skibidi yes yes\r\n     %s\r\n%s\r\n"
                .formatted(Messages.SPACER, listItems.get(0), Messages.SPACER), outContent.toString());
    }

    @Test
    void markDoneInvalidIndex() {
        command.processCommand("MARK 1");
        assertEquals("%s\r\n     %s\r\n%s\r\n"
                .formatted(Messages.SPACER, Messages.OUT_OF_BOUNDS, Messages.SPACER), outContent.toString());
    }

    @Test
    void deleteTaskValidIndex() {
        ToDo task = new ToDo("Test task");
        listItems.add(task);

        command.processCommand("DELETE 1");

        assertTrue(listItems.isEmpty());
        assertEquals("%s\r\n     %s\r\n     there are 0 tasks in the list now\r\n%s\r\n"
                .formatted(Messages.SPACER, "removed: " + task, Messages.SPACER), outContent.toString());
    }

    @Test
    void invalidCommand() {
        command.processCommand("INVALID");
        assertEquals("%s\r\n     %s\r\n%s\r\n"
                .formatted(Messages.SPACER, Messages.CONFUSED, Messages.SPACER), outContent.toString());

    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
