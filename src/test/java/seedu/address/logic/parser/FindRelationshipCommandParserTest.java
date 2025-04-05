package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertRelationshipParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertRelationshipParseSuccess;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindRelationshipCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.predicate.RelationshipContainsKeywordsAsSubstringPredicate;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;

public class FindRelationshipCommandParserTest {
    private final FindRelationshipCommandParser parser = new FindRelationshipCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test invalid user input: any input should throw a ParseException because the method always throws it.
        assertParseFailure(parser, "random input",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRelationshipCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        Model model = new ModelManager();
        assertRelationshipParseFailure(parser, " ", model, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindRelationshipCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindRelationshipCommand() {
        // Set up sample data (mock relationships for testing)
        Person person1 = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@example.com"),
                new Address("123 Street"), new HashSet<>(), new HashSet<>());
        Person person2 = new Person(new Name("Jane Doe"), new Phone("98765432"), new Email("janedoe@example.com"),
                new Address("456 Avenue"), new HashSet<>(), new HashSet<>());

        // Set up tags for relationship
        Tag friendTag = new Tag("Friend");

        // Create relationship
        Relationship relationship1 = new Relationship(person1.getId(), person2.getId(), "Close friends",
                "Best friends", new HashSet<>(Arrays.asList(friendTag)));

        // Initialize ModelManager
        Model model = new ModelManager();

        // Add relationship to the model
        model.addRelationship(relationship1);

        // Expected FindRelationshipCommand
        FindRelationshipCommand expectedFindCommand = new FindRelationshipCommand(
                new RelationshipContainsKeywordsAsSubstringPredicate(Arrays.asList("friend"), model));
        FindRelationshipCommand expectedFindCommandAnotherCase = new FindRelationshipCommand(
                new RelationshipContainsKeywordsAsSubstringPredicate(Arrays.asList("friend", "best"), model));
        // Calling the parser with valid input
        assertRelationshipParseSuccess(parser, "friend", model, expectedFindCommand);

        // Valid input with multiple whitespaces between keywords
        assertRelationshipParseSuccess(parser, " \n friend \n \t best \t", model,
                expectedFindCommandAnotherCase);
    }
}
