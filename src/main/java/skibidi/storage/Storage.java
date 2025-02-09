package skibidi.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import skibidi.task.Task;

/**
 * The Storage class provides functionality for saving and loading a list of {@link Task} objects
 * to and from a JSON file at a specified storage location.
 */
public class Storage {
    private static Logger logger = Logger.getLogger(Storage.class.getName());
    private static final ObjectMapper objectMapper = createObjectMapper();

    private final String location;
    private final File storageFile;

    /**
     * Constructs a Storage instance with the specified file location.
     * If the file does not already exist, the file object is initialized.
     *
     * @param location the file path where the tasks will be stored or retrieved from
     */
    public Storage(String location) {
        assert location != null : "Location cannot be null";
        this.location = location;
        this.storageFile = new File(location);
        ensureFileExist();
    }

    private void ensureFileExist() {
        try {
            if (storageFile.createNewFile()) {
                logger.info("Created new storage file at: " + location);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create storage file: " + location, e);
        }
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }

    /**
     * Saves a list of {@link Task} objects to the file specified by the location.
     * The list is serialized into JSON format and stored in the specified location.
     *
     * @param listItems the list of tasks to save
     */
    public void saveList(List<Task> listItems) {
        try {
            objectMapper.writeValue(storageFile, listItems);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save tasks to file: " + location, e);
        }
    }

    /**
     * Loads a list of {@link Task} objects from the file specified by the location.
     * If no file exists at the specified location, it creates an empty file.
     *
     * @return the list of tasks loaded from the file, or an empty list if the file is empty or cannot be read
     */
    public List<Task> loadList() {
        if (storageFile.length() == 0) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(storageFile, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to read tasks from file: " + location, e);
            return new ArrayList<>();
        }
    }
}
