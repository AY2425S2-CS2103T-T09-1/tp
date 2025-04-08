package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTagFromEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Contains tests for {@code DeleteTagFromEventCommandParser}.
 */
public class DeleteTagFromEventCommandParserTest {
    private final DeleteTagFromEventCommandParser parser = new DeleteTagFromEventCommandParser();

    /**
     * Tests that a valid input with a proper event index in the preamble and a tag value is parsed
     * successfully into a DeleteTagFromEventCommand.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parseValidInput_success() throws ParseException {
        String userInput = "1 " + CliSyntax.PREFIX_TAG + "Finance";
        DeleteTagFromEventCommand expectedCommand = new DeleteTagFromEventCommand(
                Index.fromOneBased(1), new Tag("Finance"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that extra whitespace around the event index and tag value is trimmed correctly and
     * a valid DeleteTagFromEventCommand is produced.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parseValidInputExtraWhitespace_success() throws ParseException {
        String userInput = "   1   " + CliSyntax.PREFIX_TAG + "   Finance   ";
        DeleteTagFromEventCommand expectedCommand = new DeleteTagFromEventCommand(
                Index.fromOneBased(1), new Tag("Finance"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that an input missing tag information fails to parse,
     * throwing a ParseException with the expected error message.
     */
    @Test
    public void parseMissingTag_failure() {
        String userInput = "1";
        String expectedMessage = "Tag information is required.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that an input with an invalid tag value fails to parse, resulting in a ParseException
     * with the expected error message.
     */
    @Test
    public void parseInvalidTag_failure() {
        String userInput = "1 " + CliSyntax.PREFIX_TAG + "*&invalid";
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
