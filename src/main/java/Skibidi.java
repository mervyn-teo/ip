import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Skibidi {
    private enum commandType {
        LIST,
        BYE,
        MARK,
        UNMARK,
        TODO,
        EVENT,
        DEADLINE,
        DELETE,
        UNKNOWN
    }
    public static void main(String[] args) {
        // inits
        File myObj = new File("saved_list.json");
        ArrayList<Task> listItems = new ArrayList<>();
        String greet = """
                    ____________________________________________________________
                     Hello! I'm Mr. Skibidi
                     Have you Skibidied today?
                    ____________________________________________________________
                """;
        String bye = "     Skibidi bop bop!";
        String spacer = "    ____________________________________________________________";

        // check if file exists
        if (!myObj.exists()) {
            try {
                if (myObj.createNewFile()) {
                    // creates file
                    System.out.println("File created: " + myObj.getName());
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            //file exists
            listItems = loadList();
        }

        System.out.println(greet);

        Scanner in = new Scanner(System.in);

        while (true) {
            String userChoice = in.nextLine();
            System.out.println(spacer);
            if (userChoice.isEmpty()) {
                System.out.println("     Type something dum dum");
                System.out.println(spacer);
                continue;
            }

            // convert user choice to enum
            commandType userChoiceEnum = commandType.valueOf(userChoice.split(" ")[0].toUpperCase());

            String[] splitUserchoice = userChoice.split(" ", 2);
            switch (userChoiceEnum) {
                case BYE: {
                    System.out.println(bye);
                    System.out.println(spacer);
                    return;
                }
                case LIST: {
                        if (listItems.isEmpty()) {
                            System.out.println("     There are no item in your list skid skid!");
                        } else {
                            for (int i = 0; i < listItems.size(); i++) {
                                Task item = listItems.get(i);
                                System.out.println("     " + (i + 1) + "." + item);
                            }
                        }
                        break;
                }
                case MARK: {
                    if (splitUserchoice.length != 2){
                        System.out.println("     Double check what you want from me skibidi bop bop!");
                        break;
                    }
                    if (Integer.parseInt(splitUserchoice[1]) > listItems.size() | Integer.parseInt(splitUserchoice[1]) <= 0) {
                        System.out.println("     This is out of bounds my skibidi!");
                    } else {
                        Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                        taskItem.markDone();
                        System.out.println("     Yes this is marked as done skibidi yes yes\n     " + taskItem);
                        saveList(listItems);
                    }
                    break;
                }
                case UNMARK: {
                    if (splitUserchoice.length != 2){
                        System.out.println("     Double check what you want from me skibidi bop bop!");
                        break;
                    }
                    if (Integer.parseInt(splitUserchoice[1]) > listItems.size() | Integer.parseInt(splitUserchoice[1]) <= 0) {
                        System.out.println("     This is out of bounds my skibidi!");
                    } else {
                        Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                        taskItem.markUndone();
                        System.out.println("     skibidi this is marked as undone skibidi bop bop\n     " + taskItem);
                        saveList(listItems);
                    }
                    break;
                }
                case TODO: {
                    // expects no time
                    if (splitUserchoice.length == 1) {
                        System.out.println("     Empty todos are not very skibidi!");
                    } else {
                        listItems.add(new toDo(splitUserchoice[1]));
                        System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
                        saveList(listItems);
                    }
                    break;
                }
                case EVENT: {
                    // expects 1 from and  1 to
                    if (splitUserchoice.length != 2){
                        System.out.println("     no name no date? bop bop!");
                        break;
                    }
                    String[] splitted = splitUserchoice[1].split("/from | /to ", 3);
                    if (splitted.length != 3) {
                        System.out.println("     Double check what you want from me skibidi bop bop!");
                    } else {
                        LocalDate fromDate;
                        LocalDate toDate;
                        try {
                            fromDate = LocalDate.parse(splitted[1]);
                            toDate = LocalDate.parse(splitted[2]);
                        } catch (DateTimeParseException e) {
                            System.out.println("     Your date format must be YYYY-MM-DD");
                            break;
                        }

                        if (fromDate.isAfter(toDate)) {
                            System.out.println("     To date cannot be before from date skib skib!");
                            break;
                        }
                        listItems.add(new Event(splitted[0], fromDate, toDate));
                        System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
                        saveList(listItems);
                    }
                    break;
                }
                case DEADLINE: {
                    // expects 1 deadline time
                    if (splitUserchoice.length != 2){
                        System.out.println("     no name no date? bop bop!");
                        break;
                    }
                    String[] splitTaskTime = splitUserchoice[1].split("/by ", 2);
                    if (splitTaskTime.length != 2) {
                        System.out.println("     Double check what you want from me skibidi bop bop!");
                    } else {
                        LocalDate byDate;
                        try {
                            byDate = LocalDate.parse(splitTaskTime[1]);
                        } catch (DateTimeParseException e) {
                            System.out.println("     Your date format must be YYYY-MM-DD");
                            break;
                        }
                        listItems.add(new Deadline(splitTaskTime[0], byDate));
                        System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
                        saveList(listItems);
                    }
                    break;
                }
                case DELETE: {
                    if (splitUserchoice.length != 2 ) {
                        System.out.println("     You must've mistyped something my dear skibidi");
                    } else {
                        int index = Integer.parseInt(splitUserchoice[1]) - 1;
                        if (index < 0 || index >= listItems.size()) {
                            System.out.println("     This is out of bounds my skibidi!");
                        } else {
                            System.out.println("     Deleted: " + listItems.get(index) + "\n     there are " + (listItems.size() - 1) + " tasks in the list now");
                            listItems.remove(index);
                            saveList(listItems);
                        }
                    }
                    break;
                }
                default: {
                    System.out.println("     I dont understand what you mean my dear skibidi");
                }
            }
            System.out.println(spacer);
        }
    }

    private static void saveList(ArrayList<Task> listItems) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(new File("saved_list.json"), listItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Task> loadList() {
        ObjectMapper objectMapper = new ObjectMapper();
        File myObj = new File("saved_list.json");
        ArrayList<Task> savedList = new ArrayList<>();
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.registerModule(new JavaTimeModule());
            savedList = objectMapper.readValue(myObj, new TypeReference<ArrayList<Task>>() {});
        } catch (IOException ignored) {
        }
        return savedList;
    }
}
