package skibidi.command;

/**
 * The {@code Parser} class is responsible for parsing user input commands and converting them into
 * corresponding {@link commandType} enums. It interprets raw text input and executes corresponding
 * command execution logics.
 */
public class Parser {

    /**
     * Represents the types of commands that can be parsed and processed.
     */
    public enum commandType {
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

    /**
     * Parses a raw string input and determines the corresponding {@link commandType}.
     *
     * @param s the user input string to be parsed
     * @return the {@link commandType} corresponding to the string input. If the input is null,
     *         empty, or does not match any known command, {@code commandType.UNKNOWN} is returned.
     */
    public static commandType parse(String s) {
        if (s == null || s.isBlank()) {
            return commandType.UNKNOWN;
        }
        String[] splitted = s.split("\\s+");
        try {
            return commandType.valueOf(splitted[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            return commandType.UNKNOWN;
        }
    }
}
