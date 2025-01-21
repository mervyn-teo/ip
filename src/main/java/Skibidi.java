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
        Scanner in = new Scanner(System.in);
        String userChoice = in.nextLine();
        System.out.println(spacer + "\n     " + userChoice + "\n" + spacer);
        System.out.println(bye);
    }
}
