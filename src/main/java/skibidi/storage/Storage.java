package skibidi.storage;

import skibidi.task.Task;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Storage class provides functionality for saving and loading a list of {@link Task} objects
 * to and from a JSON file at a specified storage location.
 */
public class Storage {
    private final String location;

    /**
     * Constructs a Storage instance with the specified file location.
     * If the file does not already exist, the file object is initialized.
     *
     * @param location the file path where the tasks will be stored or retrieved from
     */
    public Storage(String location) {
        this.location = location;
    }

    /**
     * Saves a list of {@link Task} objects to the file specified by the location.
     * The list is serialized into JSON format and stored in the specified location.
     *
     * @param listItems the list of tasks to save
     */
    public void saveList(List<Task> listItems) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(new File(location), listItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a list of {@link Task} objects from the file specified by the location.
     * If no file exists at the specified location, it creates an empty file.
     *
     * @return the list of tasks loaded from the file, or an empty list if the file is empty or cannot be read
     */
    public List<Task> loadList() {
        File myObj = new File(location);
        try {
            myObj.createNewFile(); // This garuntees that the JSON file exists
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Task> savedList = new ArrayList<>();
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.registerModule(new JavaTimeModule());
            savedList = objectMapper.readValue(myObj, new TypeReference<List<Task>>() {});
        } catch (IOException ignored) {
        }
        return savedList;
    }
}
