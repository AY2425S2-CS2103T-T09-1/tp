package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateEventLocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for {@code UpdateEventLocationCommandParser}.
 */
public class UpdateEventLocationCommandParserTest {
    private final UpdateEventLocationCommandParser parser = new UpdateEventLocationCommandParser();

    /**
     * Tests that a valid input with a proper event index (in the preamble) and exactly one new location value
     * is parsed successfully into an UpdateEventLocationCommand.
     *
     * @throws ParseException if the parsing fails unexpectedly.
     */
    @Test
    public void parse_validInput_success() throws ParseException {
        // Preamble "1" means event index 1.
        String userInput = "1 " + CliSyntax.PREFIX_LOCATION + "New Location";
        UpdateEventLocationCommand expectedCommand =
                new UpdateEventLocationCommand(Index.fromOneBased(1), "New Location");
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
