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
        String bye = """
                    ____________________________________________________________
                     Skibidi bop bop!
                    ____________________________________________________________
                """;
        String spacer = "    ____________________________________________________________";
        System.out.println(greet);

        ArrayList<String> listItems = new ArrayList<String>();
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
                        System.out.println("     " + (i+1) + ". " + listItems.get(i));
                    }
                    System.out.println(spacer);
                    break;
                default:
                    listItems.add(userChoice);
                    System.out.println(spacer + "\n     added: " + userChoice + "\n" + spacer);
                    break;
            }
        }



    }


}
