import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Skibidi {
    public static void main(String[] args) {
        String greet = """
                    ____________________________________________________________
                     Hello! I'm Mr. Skibidi
                     Have you Skibidied today?
                    ____________________________________________________________                          
                """;
        String bye = """
                    ____________________________________________________________
                     Skibidi bop bop!
                    ____________________________________________________________
                """;
        String spacer = "    ____________________________________________________________";
        System.out.println(greet);

        ArrayList<Task> listItems = new ArrayList<>();
        while (true) {
            Scanner in = new Scanner(System.in);
            String userChoice = in.nextLine();

            switch (userChoice) {
                case "bye":
                    System.out.println(bye);
                    return;
                case "list":
                    System.out.println(spacer);
                    for (int i = 0; i < listItems.size(); i++) {
                        Task item = listItems.get(i);
                        System.out.println("     " + (i+1) + "." + item );
                    }
                    System.out.println(spacer);
                    break;
                default:
                    String[] splitUserchoice = userChoice.split(" ", 2);

                    switch (splitUserchoice[0]){
                        case "mark": {
                            Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                            taskItem.markDone();
                            System.out.println(spacer);
                            System.out.println("     Yes this is marked as done skibidi yes yes\n     " + taskItem);
                            System.out.println(spacer);
                            break;
                        }
                        case "unmark": {
                            Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                            taskItem.markUndone();
                            System.out.println(spacer);
                            System.out.println("     skibidi this is marked as undone skibidi bop bop\n     " + taskItem);
                            System.out.println(spacer);
                            break;
                        }
                        case "todo": {
                            // expects no time
                            listItems.add(new toDo(splitUserchoice[1]));
                            System.out.println(spacer + "\n     added: " + listItems.get(listItems.size() - 1) + "\n" + spacer);
                            break;
                        }
                        case "event": {
                            // expects 1 from and  1 to
                            String[] splitted = splitUserchoice[1].split("/from | /to ", 3);
                            listItems.add(new Event(splitted[0], splitted[1], splitted[2]));
                            System.out.println(spacer + "\n     added: " + listItems.get(listItems.size() - 1) + "\n" + spacer);
                            break;
                        }
                        case "deadline": {
                            // expects 1 deadline time
                            String[] splitTaskTime = splitUserchoice[1].split("/by ", 2);
                            listItems.add(new Deadline(splitTaskTime[0], splitTaskTime[1]));
                            System.out.println(spacer + "\n     added: " + listItems.get(listItems.size() - 1) + "\n" + spacer);
                            break;
                        }
                        default: {
                        }
                    }

            }
        }
    }
}
