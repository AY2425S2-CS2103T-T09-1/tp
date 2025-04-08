package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteContactFromEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Contains tests for {@code DeleteContactFromEventCommandParser}.
 */
public class DeleteContactFromEventCommandParserTest {
    private final DeleteContactFromEventCommandParser parser = new DeleteContactFromEventCommandParser();

    /**
     * Tests that a valid input with a proper event index in the preamble and contact information
     * is parsed successfully into a DeleteContactFromEventCommand.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parseValidInput_success() throws ParseException {
        String userInput = "1 " + CliSyntax.PREFIX_CONTACT + "John Doe";
        Person expectedContact = ParserUtil.parseContact("John Doe");
        DeleteContactFromEventCommand expectedCommand =
                new DeleteContactFromEventCommand(Index.fromOneBased(1), expectedContact);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that extra whitespace around the event index and contact information is trimmed correctly
     * and the input is parsed into the expected DeleteContactFromEventCommand.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parseValidInputExtraWhitespace_success() throws ParseException {
        String userInput = "   1   " + CliSyntax.PREFIX_CONTACT + "   John Doe   ";
        Person expectedContact = ParserUtil.parseContact("John Doe");
        DeleteContactFromEventCommand expectedCommand =
                new DeleteContactFromEventCommand(Index.fromOneBased(1), expectedContact);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that an input missing the contact information fails to parse,
     * throwing a ParseException with the expected error message.
     */
    @Test
    public void parseMissingContact_failure() {
        String userInput = "1";
        String expectedMessage = "Contact information is required.";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
