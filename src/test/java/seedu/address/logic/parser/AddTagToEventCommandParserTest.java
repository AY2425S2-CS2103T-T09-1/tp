package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagToEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Contains tests for {@code AddTagToEventCommandParser}.
 */
public class AddTagToEventCommandParserTest {
    private final AddTagToEventCommandParser parser = new AddTagToEventCommandParser();

    /**
     * Tests that a valid input with a proper event index in the preamble and a tag value is parsed
     * successfully into an AddTagToEventCommand.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parseValidInputSuccess() throws ParseException {
        String userInput = "1 " + CliSyntax.PREFIX_TAG + "Finance";
        AddTagToEventCommand expectedCommand = new AddTagToEventCommand(Index.fromOneBased(1), new Tag("Finance"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that extra whitespace around the preamble and tag are trimmed correctly,
     * and a valid AddTagToEventCommand is produced.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parseValidInputExtraWhitespaceSuccess() throws ParseException {
        String userInput = "   1   " + CliSyntax.PREFIX_TAG + "   Finance   ";
        AddTagToEventCommand expectedCommand = new AddTagToEventCommand(Index.fromOneBased(1), new Tag("Finance"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that an input missing tag information fails to parse,
     * throwing a ParseException with the expected error message.
     */
    @Test
    public void parseMissingTagFailure() {
        String userInput = "1";
        String expectedMessage = "Tag information is required.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that an input with an invalid tag (if invalid characters are not permitted)
     * fails to parse, resulting in a ParseException with the expected error message.
     */
    @Test
    public void parseInvalidTagFailure() {
        String userInput = "1 " + CliSyntax.PREFIX_TAG + "*&invalid";
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
