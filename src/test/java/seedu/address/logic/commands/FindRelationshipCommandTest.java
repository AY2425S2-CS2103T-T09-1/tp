package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersonsTestSet2.ALICE;
import static seedu.address.testutil.TypicalPersonsTestSet2.BENSON;
import static seedu.address.testutil.TypicalPersonsTestSet2.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.predicate.RelationshipContainsKeywordsAsSubstringPredicate;
import seedu.address.model.relationship.Relationship;

/**
 * Contains integration tests (interaction with the Model) for {@code FindRelationshipCommand}.
 */
public class FindRelationshipCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        // Create a new address book with our test data
        AddressBook addressBook = new AddressBook(getTypicalAddressBook());

        // Setup models
        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(addressBook, new UserPrefs());

        // Setup relationships
        String aliceId = ALICE.getId();
        String bensonId = BENSON.getId();

        // Create relationship with forward and reverse names
        Relationship relationship = new Relationship(aliceId, bensonId, "family", "family", new HashSet<>());

        // Add the relationship to both models
        model.addRelationship(relationship);
        expectedModel.addRelationship(relationship);
    }

    @Test
    public void equals() {
        RelationshipContainsKeywordsAsSubstringPredicate firstPredicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(Collections.singletonList("first"), model);
        RelationshipContainsKeywordsAsSubstringPredicate secondPredicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(Collections.singletonList("second"), model);

        FindRelationshipCommand findFirstCommand = new FindRelationshipCommand(firstPredicate);
        FindRelationshipCommand findSecondCommand = new FindRelationshipCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindRelationshipCommand findFirstCommandCopy = new FindRelationshipCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        RelationshipContainsKeywordsAsSubstringPredicate predicate = preparePredicate(" ", model);
        FindRelationshipCommand command = new FindRelationshipCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_keywordMatchesRelationship_personsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        RelationshipContainsKeywordsAsSubstringPredicate predicate = preparePredicate("family", model);
        FindRelationshipCommand command = new FindRelationshipCommand(predicate);

        // Setup expected result with ALICE and BENSON who have a "family" relationship
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        // Add another relationship with a different relationship type
        String aliceId = ALICE.getId();
        String bensonId = BENSON.getId();
        Relationship relationship = new Relationship(aliceId, bensonId, "friend", "friend", new HashSet<>());

        model.addRelationship(relationship);
        expectedModel.addRelationship(relationship);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        RelationshipContainsKeywordsAsSubstringPredicate predicate = preparePredicate("friend family", model);
        FindRelationshipCommand command = new FindRelationshipCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialKeywords_partialPersonsFound() {
        // Add another relationship with a relationship type containing partial match
        String aliceId = ALICE.getId();
        String bensonId = BENSON.getId();
        Relationship relationship = new Relationship(aliceId, bensonId, "colleague", "colleague", new HashSet<>());

        model.addRelationship(relationship);
        expectedModel.addRelationship(relationship);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        RelationshipContainsKeywordsAsSubstringPredicate predicate = preparePredicate("lea", model);
        FindRelationshipCommand command = new FindRelationshipCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        RelationshipContainsKeywordsAsSubstringPredicate predicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(Arrays.asList("family"), model);
        FindRelationshipCommand findCommand = new FindRelationshipCommand(predicate);
        String expected = FindRelationshipCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, "seedu.address.logic.commands." + findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code RelationshipContainsKeywordsAsSubstringPredicate}.
     */
    private RelationshipContainsKeywordsAsSubstringPredicate preparePredicate(String userInput, Model model) {
        return new RelationshipContainsKeywordsAsSubstringPredicate(
                Arrays.asList(userInput.split("\\s+")), model);
    }
}

