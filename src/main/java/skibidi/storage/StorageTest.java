package skibidi.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import skibidi.task.Deadline;
import skibidi.task.Event;
import skibidi.task.Task;
import skibidi.task.ToDo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {
    private static final String TEST_FILE_PATH = "test_storage.json";
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(TEST_FILE_PATH);
        File myOj = new File(TEST_FILE_PATH);
        try {
            myOj.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
//         Clean up after each test by deleting the test file
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveList_savesTasksToFile() {
        // Arrange
        List<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("Buy groceries"));
        tasks.add(new Deadline("Finish project", LocalDate.of(2023, 12, 25)));
        tasks.add(new Event("Team meeting", LocalDate.of(2023, 10, 15), LocalDate.of(2023, 10, 16)));

        // Act
        storage.saveList(tasks);

        // Assert
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists(), "The file should be created after saving");
        assertTrue(file.length() > 0, "The file should not be empty after saving");
    }

    @Test
    void loadList_loadsTasksFromFile() {
        // Arrange
        List<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("Buy groceries"));
        tasks.add(new Deadline("Finish project", LocalDate.of(2023, 12, 25)));
        tasks.add(new Event("Team meeting", LocalDate.of(2023, 10, 15), LocalDate.of(2023, 10, 16)));
        storage.saveList(tasks);

        // Act
        List<Task> loadedTasks = storage.loadList();

        // Assert
        assertEquals(3, loadedTasks.size(), "The size of the loaded list should match the saved list");
        assertEquals(tasks.get(0).toString(), loadedTasks.get(0).toString());
        assertEquals(tasks.get(1).toString(), loadedTasks.get(1).toString());
        assertEquals(tasks.get(2).toString(), loadedTasks.get(2).toString());
    }

    @Test
    void loadList_handlesEmptyFileGracefully() {
        // Act
        List<Task> loadedTasks = storage.loadList();

        // Assert
        assertTrue(loadedTasks.isEmpty(), "The loaded list should be empty if the file is empty or non-existent");
    }
}