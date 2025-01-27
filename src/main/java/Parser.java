public class Parser {
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

    public static commandType parse(String s) {
        if (s == null || s.isBlank()) {
            return commandType.UNKNOWN;
        }
        String[] splitted = s.split("\\s+");
        try {
            commandType ret = commandType.valueOf(splitted[0].toUpperCase());
            return ret;
        } catch (IllegalArgumentException e) {
            return commandType.UNKNOWN;
        }
    }
}
