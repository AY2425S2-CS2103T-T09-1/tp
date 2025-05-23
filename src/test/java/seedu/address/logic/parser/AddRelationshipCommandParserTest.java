package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.model.tag.Tag;

public class AddRelationshipCommandParserTest {
    private final AddRelationshipCommandParser parser = new AddRelationshipCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Basic case
        assertParseSuccess(parser, " u/1 u/2 fn/Friend rn/Reports to",
                new AddRelationshipCommand("1", "2", "Friend", "Reports to", new HashSet<>()));

        // With tags
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_FRIEND));
        assertParseSuccess(parser, " u/1 u/2 fn/Friend rn/Reports to t/friend",
                new AddRelationshipCommand("1", "2", "Friend", "Reports to", tags));

        // With multiple tags
        Set<Tag> multipleTags = new HashSet<>();
        multipleTags.add(new Tag(VALID_TAG_FRIEND));
        multipleTags.add(new Tag("colleague"));
        assertParseSuccess(parser, " u/1 u/2 fn/Friend rn/Reports to t/friend t/colleague",
                new AddRelationshipCommand("1", "2", "Friend", "Reports to", multipleTags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE);

        // Missing user ID prefixes
        assertParseFailure(parser, " fn/Friend rn/Reports to", expectedMessage);

        // Only one user ID
        assertParseFailure(parser, " u/1 fn/Friend rn/Reports to", expectedMessage);

        // Missing name prefix
        assertParseFailure(parser, " u/1 u/2", expectedMessage);

        // Missing all prefixes
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Non-empty preamble
        assertParseFailure(parser, "some random string u/1 u/2 fn/Friend rn/Reports to",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));

        // Invalid tag
        assertParseFailure(parser, " u/1 u/2 fn/Friend rn/Reports to t/*&",
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidNameValues_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE);

        // Missing forward name value
        assertParseFailure(parser, " u/1 u/2 rn/Reports to", expectedMessage);

        // Missing reverse name value
        assertParseFailure(parser, " u/1 u/2 fn/Boss of", expectedMessage);
    }
}
