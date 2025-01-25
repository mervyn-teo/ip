import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Skibidi {
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
            switch (userChoice) {
                case "bye": {
                    System.out.println(bye);
                    System.out.println(spacer);
                    return;
                }
                case "list": {
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
                default: {
                    String[] splitUserChoice = userChoice.split(" ", 2);
                    switch (splitUserChoice[0]) {
                            case "mark": {
                                if (splitUserChoice.length != 2){
                                    System.out.println("     Double check what you want from me skibidi bop bop!");
                                    break;
                                }
                                if (Integer.parseInt(splitUserChoice[1]) > listItems.size() | Integer.parseInt(splitUserChoice[1]) <= 0) {
                                    System.out.println("     This is out of bounds my skibidi!");
                                } else {
                                    Task taskItem = listItems.get(Integer.parseInt(splitUserChoice[1]) - 1);
                                    taskItem.markDone();
                                    System.out.println("     Yes this is marked as done skibidi yes yes\n     " + taskItem);
                                    saveList(listItems);
                                }
                                break;
                            }
                            case "unmark": {
                                if (splitUserChoice.length != 2){
                                    System.out.println("     Double check what you want from me skibidi bop bop!");
                                    break;
                                }
                                if (Integer.parseInt(splitUserChoice[1]) > listItems.size() | Integer.parseInt(splitUserChoice[1]) <= 0) {
                                    System.out.println("     This is out of bounds my skibidi!");
                                } else {
                                    Task taskItem = listItems.get(Integer.parseInt(splitUserChoice[1]) - 1);
                                    taskItem.markUndone();
                                    System.out.println("     skibidi this is marked as undone skibidi bop bop\n     " + taskItem);
                                    saveList(listItems);
                                }
                                break;
                            }
                            case "todo": {
                                // expects no time
                                if (splitUserChoice.length == 1) {
                                    System.out.println("     Empty todos are not very skibidi!");
                                } else {
                                    listItems.add(new toDo(splitUserChoice[1]));
                                    System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
                                    saveList(listItems);
                                }
                                break;
                            }
                            case "event": {
                                // expects 1 from and  1 to
                                if (splitUserChoice.length != 2){
                                    System.out.println("     no name no date? bop bop!");
                                    break;
                                }
                                String[] split = splitUserChoice[1].split("/from | /to ", 3);
                                if (split.length != 3) {
                                    System.out.println("     Double check what you want from me skibidi bop bop!");
                                } else {
                                    listItems.add(new Event(split[0], split[1], split[2]));
                                    System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
                                    saveList(listItems);
                                }
                                break;
                            }
                            case "deadline": {
                                // expects 1 deadline time
                                if (splitUserChoice.length != 2){
                                    System.out.println("     no name no date? bop bop!");
                                    break;
                                }
                                String[] splitTaskTime = splitUserChoice[1].split("/by ", 2);
                                if (splitTaskTime.length != 2) {
                                    System.out.println("     Double check what you want from me skibidi bop bop!");
                                } else {
                                    listItems.add(new Deadline(splitTaskTime[0], splitTaskTime[1]));
                                    System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
                                    saveList(listItems);
                                }
                                break;
                            }
                            default: {
                                System.out.println("     I dont understand what you mean my dear skibidi");
                            }

                        }
                }
            }
            System.out.println(spacer);
        }
    }

    private static void saveList(ArrayList<Task> listItems) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
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
            savedList = objectMapper.readValue(myObj, new TypeReference<ArrayList<Task>>() {});
        } catch (IOException ignored) {
        }
        return savedList;
    }
}
