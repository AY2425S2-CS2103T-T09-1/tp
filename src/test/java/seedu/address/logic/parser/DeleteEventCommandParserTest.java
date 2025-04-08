package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for {@code DeleteEventCommandParser}.
 */
public class DeleteEventCommandParserTest {
    private final DeleteEventCommandParser parser = new DeleteEventCommandParser();

    /**
     * Tests that a valid input with a proper "u/" prefix and event ID is parsed successfully
     * into a DeleteEventCommand.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parseValidInputSuccess() throws ParseException {
        String input = "u/12345678";
        DeleteEventCommand expectedCommand = new DeleteEventCommand("12345678");
        assertParseSuccess(parser, input, expectedCommand);
    }

    /**
     * Tests that extra leading and trailing whitespace is trimmed correctly and
     * the valid input still produces a correct DeleteEventCommand.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parseValidInputExtraWhitespaceSuccess() throws ParseException {
        String input = "   u/   12345678   ";
        DeleteEventCommand expectedCommand = new DeleteEventCommand("12345678");
        assertParseSuccess(parser, input, expectedCommand);
    }

    /**
     * Tests that an input missing the "u/" prefix fails to parse and throws a ParseException
     * with the expected error message.
     */
    @Test
    public void parseMissingPrefixFailure() {
        String input = "12345678"; // Missing u/ prefix
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    /**
     * Tests that an input with a "u/" prefix but with an empty event ID fails to parse,
     * resulting in a ParseException with the expected error message.
     */
    @Test
    public void parseEmptyEventIdFailure() {
        String input = "u/   "; // u/ is present but event ID is empty
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }
}
