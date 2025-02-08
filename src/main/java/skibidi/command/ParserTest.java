package skibidi.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ParserTest {
    @Test
    void parseList() {
        assertEquals(Parser.CommandType.LIST, Parser.parse("list"));
    }

    @Test
    void parseUnknown() {
        assertEquals(Parser.CommandType.UNKNOWN, Parser.parse(""));
    }

    @Test
    void parseUnknownNull() {
        assertEquals(Parser.CommandType.UNKNOWN, Parser.parse(null));
    }

    @Test
    void parseTodo() {
        assertEquals(Parser.CommandType.TODO, Parser.parse("todo"));
    }

    @Test
    void parseEvent() {
        assertEquals(Parser.CommandType.EVENT, Parser.parse("event"));
    }

    @Test
    void parseDeadline() {
        assertEquals(Parser.CommandType.DEADLINE, Parser.parse("deadline"));
    }

    @Test
    void parseMark() {
        assertEquals(Parser.CommandType.MARK, Parser.parse("mark"));
    }

    @Test
    void parseUnmark() {
        assertEquals(Parser.CommandType.UNMARK, Parser.parse("unmark"));
    }

    @Test
    void parseDelete() {
        assertEquals(Parser.CommandType.DELETE, Parser.parse("delete"));
    }

    @Test
    void parseBye() {
        assertEquals(Parser.CommandType.BYE, Parser.parse("bye"));
    }
}
