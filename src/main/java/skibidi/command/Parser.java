package skibidi.command;

public class Parser {
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
