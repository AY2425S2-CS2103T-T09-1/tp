package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateEventDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for {@code UpdateEventDateCommandParser}.
 */
public class UpdateEventDateCommandParserTest {
    private final UpdateEventDateCommandParser parser = new UpdateEventDateCommandParser();

    /**
     * Tests that a valid input containing an event index in the preamble and a single valid date value
     * is parsed successfully into an UpdateEventDateCommand.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parse_validInput_success() throws ParseException {
        String dateString = "2025-12-31T23:59";
        String userInput = "1 " + CliSyntax.PREFIX_DATE + dateString;
        UpdateEventDateCommand expectedCommand =
                new UpdateEventDateCommand(Index.fromOneBased(1), LocalDateTime.parse(dateString));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that an input with an invalid date format fails to parse.
     */
    @Test
    public void parse_invalidDateFormat_failure() {
        String userInput = "1 " + CliSyntax.PREFIX_DATE + "invalid-date";
        String expectedMessage = "Invalid date format. Expected format: yyyy-MM-ddTHH:mm";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
