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

public class Storage {
    private final String location;

    public Storage(String location) {
        this.location = location;
    }

    public void saveList(List<Task> listItems) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(new File(location), listItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
