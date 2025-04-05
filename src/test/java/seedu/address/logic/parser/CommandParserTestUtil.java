package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser<? extends Command> parser, String userInput,
            Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser<? extends Command> parser, String userInput,
                                          String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} using {@code model} is successful
     * and the resulting {@code Command} equals to {@code expectedCommand}.
     */
    public static void assertRelationshipParseSuccess(FindRelationshipCommandParser parser,
                                                      String userInput, Model model, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput, model);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} using {@code model} is unsuccessful
     * and the error message equals to {@code expectedMessage}.
     */
    public static void assertRelationshipParseFailure(FindRelationshipCommandParser parser, String userInput,
                                                      Model model, String expectedMessage) {
        try {
            parser.parse(userInput, model);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
