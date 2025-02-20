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
        String msg = command.processCommand("");
        assertEquals(Messages.EMPTY_COMMAND + "\n", msg);
    }

    @Test
    void listEmpty() {
        String msg = command.processCommand("list");
        assertEquals(Messages.EMPTY_LIST + "\n", msg);
    }

    @Test
    void listNonEmpty() {
        listItems.add(new ToDo("Test task"));
        String msg = command.processCommand("LIST");
        assertEquals("1. " + listItems.get(0) + "\n", msg);
    }

    @Test
    void addTodoValid() {
        String msg = command.processCommand("TODO Read a book");
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof ToDo);
        assertEquals("added: " + listItems.get(0) + "\nthere are 1 tasks in the list now", msg);
    }

    @Test
    void addTodoEmptyDes() {
        String msg = command.processCommand("TODO ");
        assertEquals(Messages.EMPTY_TODO + "\n", msg);
        assertTrue(listItems.isEmpty());
    }

    @Test
    void addEventValid() {
        String msg = command.processCommand("EVENT Meeting /from 2023-10-01 /to 2023-10-02");
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Event);
        Event event = (Event) listItems.get(0);
        assertEquals(LocalDate.of(2023, 10, 1), event.getStartDate());
        assertEquals(LocalDate.of(2023, 10, 2), event.getEndDate());
        assertEquals("added: " + listItems.get(0) + "\nthere are 1 tasks in the list now", msg);
    }

    @Test
    void addEventInvalidDates() {
        String msg = command.processCommand("EVENT Meeting /from 2023-10-02 /to 2023-10-01");
        assertEquals(Messages.DATE_CONFLICT + "\n", msg);
        assertTrue(listItems.isEmpty());
    }

    @Test
    void addDeadlineValid() {
        String msg = command.processCommand("DEADLINE Meeting /by 2023-10-01");
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Deadline);
        Deadline deadline = (Deadline) listItems.get(0);
        assertEquals(LocalDate.of(2023, 10, 1), deadline.getDeadline());
        assertEquals("added: " + listItems.get(0) + "\nthere are 1 tasks in the list now", msg);
    }

    @Test
    void addDeadlineInvalidDates() {
        String msg = command.processCommand("EVENT Meeting /by 2023-13-02");
        assertEquals(Messages.DOUBLE_CHECK + "\n",msg);
        assertTrue(listItems.isEmpty());
    }

    @Test
    void markDoneValid() {
        ToDo task = new ToDo("Test task");
        listItems.add(task);

        String msg = command.processCommand("MARK 1");

        assertTrue(task.isDone());
        assertEquals("Yes this is marked as done skibidi yes yes\n" + listItems.get(0) + "\n", msg);
    }

    @Test
    void markDoneInvalidIndex() {
        String msg = command.processCommand("MARK 1");
        assertEquals(Messages.OUT_OF_BOUNDS + "\n", msg);
    }

    @Test
    void deleteTaskValidIndex() {
        ToDo task = new ToDo("Test task");
        listItems.add(task);

        String msg = command.processCommand("DELETE 1");

        assertTrue(listItems.isEmpty());
        assertEquals("removed: " + task + "\nthere are 0 tasks in the list now", msg);
    }

    @Test
    void invalidCommand() {
        String msg = command.processCommand("INVALID");
        assertEquals(Messages.CONFUSED + "\n", msg);

    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
