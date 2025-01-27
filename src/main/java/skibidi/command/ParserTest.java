package skibidi.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    void parseList() {
        assertEquals(Parser.commandType.LIST, Parser.parse("list"));
    }

    @Test
    void parseUnknown() {
        assertEquals(Parser.commandType.UNKNOWN, Parser.parse(""));
    }

    @Test
    void parseUnknownNull() {
        assertEquals(Parser.commandType.UNKNOWN, Parser.parse(null));
    }

    @Test
    void parseTodo() {
        assertEquals(Parser.commandType.TODO, Parser.parse("todo"));
    }

    @Test
    void parseEvent() {
        assertEquals(Parser.commandType.EVENT, Parser.parse("event"));
    }

    @Test
    void parseDeadline() {
        assertEquals(Parser.commandType.DEADLINE, Parser.parse("deadline"));
    }

    @Test
    void parseMark() {
        assertEquals(Parser.commandType.MARK, Parser.parse("mark"));
    }

    @Test
    void parseUnmark() {
        assertEquals(Parser.commandType.UNMARK, Parser.parse("unmark"));
    }

    @Test
    void parseDelete() {
        assertEquals(Parser.commandType.DELETE, Parser.parse("delete"));
    }

    @Test
    void parseBye() {
        assertEquals(Parser.commandType.BYE, Parser.parse("bye"));
    }
}