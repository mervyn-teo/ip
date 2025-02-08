package skibidi.command;

/**
 * The {@code Parser} class is responsible for parsing user input commands and converting them into
 * corresponding {@link CommandType} enums. It interprets raw text input and executes corresponding
 * command execution logics.
 */
public class Parser {
    /**
     * Represents the different types of commands that can be issued in the application.
     */
    public enum CommandType {
        LIST,
        BYE,
        MARK,
        UNMARK,
        TODO,
        EVENT,
        DEADLINE,
        DELETE,
        FIND,
        UNKNOWN
    }

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
        String[] split = s.split("\\s+");
        try {
            return CommandType.valueOf(split[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }
}
