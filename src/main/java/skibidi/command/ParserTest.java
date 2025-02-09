package skibidi.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ParserTest {
    @Test
    void parseList() {
        assertEquals(CommandType.LIST, Parser.parse("list"));
    }

    @Test
    void parseUnknown() {
        assertEquals(CommandType.UNKNOWN, Parser.parse(""));
    }

    @Test
    void parseUnknownNull() {
        assertEquals(CommandType.UNKNOWN, Parser.parse(null));
    }

    @Test
    void parseTodo() {
        assertEquals(CommandType.TODO, Parser.parse("todo"));
    }

    @Test
    void parseEvent() {
        assertEquals(CommandType.EVENT, Parser.parse("event"));
    }

    @Test
    void parseDeadline() {
        assertEquals(CommandType.DEADLINE, Parser.parse("deadline"));
    }

    @Test
    void parseMark() {
        assertEquals(CommandType.MARK, Parser.parse("mark"));
    }

    @Test
    void parseUnmark() {
        assertEquals(CommandType.UNMARK, Parser.parse("unmark"));
    }

    @Test
    void parseDelete() {
        assertEquals(CommandType.DELETE, Parser.parse("delete"));
    }

    @Test
    void parseBye() {
        assertEquals(CommandType.BYE, Parser.parse("bye"));
    }
}
