import java.util.ArrayList;
import java.util.Scanner;

public class Skibidi {
    public static void main(String[] args) {
        String greet = """
                    ____________________________________________________________
                     Hello! I'm Mr. Skibidi
                     Have you Skibidied today?
                    ____________________________________________________________
                """;
        String bye = "     Skibidi bop bop!";
        String spacer = "    ____________________________________________________________";
        System.out.println(greet);

        ArrayList<Task> listItems = new ArrayList<>();
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
                            System.out.println("There are no item in your list skid skid!");
                        } else {
                            for (int i = 0; i < listItems.size(); i++) {
                                Task item = listItems.get(i);
                                System.out.println("     " + (i + 1) + "." + item);
                            }
                        }
                        break;
                }
                default: {
                    String[] splitUserchoice = userChoice.split(" ", 2);
                    switch (splitUserchoice[0]) {
                            case "mark": {
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
                                }
                                break;
                            }
                            case "unmark": {
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
                                }
                                break;
                            }
                            case "todo": {
                                // expects no time
                                if (splitUserchoice.length == 1) {
                                    System.out.println("     Empty todos are not very skibidi!");
                                } else {
                                    listItems.add(new toDo(splitUserchoice[1]));
                                    System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
                                }
                                break;
                            }
                            case "event": {
                                // expects 1 from and  1 to
                                if (splitUserchoice.length != 2){
                                    System.out.println("     no name no date? bop bop!");
                                    break;
                                }
                                String[] splitted = splitUserchoice[1].split("/from | /to ", 3);
                                if (splitted.length != 3) {
                                    System.out.println("     Double check what you want from me skibidi bop bop!");
                                } else {
                                    listItems.add(new Event(splitted[0], splitted[1], splitted[2]));
                                    System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
                                }
                                break;
                            }
                            case "deadline": {
                                // expects 1 deadline time
                                if (splitUserchoice.length != 2){
                                    System.out.println("     no name no date? bop bop!");
                                    break;
                                }
                                String[] splitTaskTime = splitUserchoice[1].split("/by ", 2);
                                if (splitTaskTime.length != 2) {
                                    System.out.println("     Double check what you want from me skibidi bop bop!");
                                } else {
                                    listItems.add(new Deadline(splitTaskTime[0], splitTaskTime[1]));
                                    System.out.println("     added: " + listItems.get(listItems.size() - 1) + "\n     there are " + listItems.size() + " tasks in the list now");
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
}
