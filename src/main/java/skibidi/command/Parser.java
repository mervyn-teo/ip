package skibidi.command;

/**
 * The {@code Parser} class is responsible for parsing user input commands and converting them into
 * corresponding {@link CommandType} enums. It interprets raw text input and executes corresponding
 * command execution logics.
 */
public class Parser {

    /**
     * Parses a raw string input and determines the corresponding {@link CommandType}.
     *
     * @param s the user input string to be parsed
     * @return the {@link CommandType} corresponding to the string input. If the input is null,
     *         empty, or does not match any known command, {@code commandType.UNKNOWN} is returned.
     */
    public static CommandType parse(String s) {
        if (s == null || s.isBlank()) {
            return CommandType.UNKNOWN;
        }
        String[] words = s.trim().replaceAll("\\s+", " ").split(" ");
        String command = words[0].toUpperCase();
        try {
            return CommandType.valueOf(command);
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }
}
