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
                        System.out.println("     " + (i+1) + "." + item.getDone() + " " + item.getTask());
                    }
                    System.out.println(spacer);
                    break;
                default:
                    String[] splitUserchoice = userChoice.split(" ", 2);
                    if (Objects.equals(splitUserchoice[0], "mark")) {
                        Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                        taskItem.markDone();
                        System.out.println(spacer);
                        System.out.println("     Yes this is marked as done skibidi yes yes\n     " + taskItem.getDone() + " " + taskItem.getTask());
                        System.out.println(spacer);
                    } else if (Objects.equals(splitUserchoice[0], "unmark")) {
                        Task taskItem = listItems.get(Integer.parseInt(splitUserchoice[1]) - 1);
                        taskItem.markUndone();
                        System.out.println(spacer);
                        System.out.println("     skibidi this is marked as undone skibidi bop bop\n     " + taskItem.getDone() + " " + taskItem.getTask());
                        System.out.println(spacer);
                    } else {
                        listItems.add(new Task(userChoice));
                        System.out.println(spacer + "\n     added: " + userChoice + "\n" + spacer);
                        break;
                    }
            }
        }
    }
}
