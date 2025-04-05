package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        List<String> fieldsOne = Arrays.asList("name");
        List<String> fieldsTwo = Arrays.asList("phone");
        List<String> multipleFields = Arrays.asList("name", "phone");

        SortCommand sortByNameCommand = new SortCommand(false, fieldsOne);
        SortCommand sortByNameReverseCommand = new SortCommand(true, fieldsOne);
        SortCommand sortByPhoneCommand = new SortCommand(false, fieldsTwo);
        SortCommand sortMultipleCommand = new SortCommand(false, multipleFields);

        // same object -> returns true
        assertTrue(sortByNameCommand.equals(sortByNameCommand));

        // same values -> returns true
        SortCommand sortByNameCommandCopy = new SortCommand(false, fieldsOne);
        assertTrue(sortByNameCommand.equals(sortByNameCommandCopy));

        // different types -> returns false
        assertFalse(sortByNameCommand.equals(1));

        // null -> returns false
        assertFalse(sortByNameCommand.equals(null));

        // different fields -> returns false
        assertFalse(sortByNameCommand.equals(sortByPhoneCommand));

        // different order -> returns false
        assertFalse(sortByNameCommand.equals(sortByNameReverseCommand));

        // different number of fields -> returns false
        assertFalse(sortByNameCommand.equals(sortMultipleCommand));
    }

    @Test
    public void execute_sortByName_success() throws CommandException {
        List<String> fields = Arrays.asList("name");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> nameComparator = (
                p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
        expectedModel.updateSortedPersonList(nameComparator);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByNameReversed_success() throws CommandException {
        List<String> fields = Arrays.asList("name");
        SortCommand command = new SortCommand(true, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> nameComparator = (
                p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
        expectedModel.updateSortedPersonList(nameComparator.reversed());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByPhone_success() throws CommandException {
        List<String> fields = Arrays.asList("phone");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> phoneComparator = (
                p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString());
        expectedModel.updateSortedPersonList(phoneComparator);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByEmail_success() throws CommandException {
        List<String> fields = Arrays.asList("email");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> emailComparator = (
                p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString());
        expectedModel.updateSortedPersonList(emailComparator);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByAddress_success() throws CommandException {
        List<String> fields = Arrays.asList("address");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> addressComparator = (
                p1, p2) -> p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString());
        expectedModel.updateSortedPersonList(addressComparator);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }
    @Test
    public void execute_sortByTags_success() throws CommandException {
        List<String> fields = Arrays.asList("tags");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> tagsComparator = (
                p1, p2) -> p1.getTags().toString().compareToIgnoreCase(p2.getTags().toString());

        expectedModel.updateSortedPersonList(tagsComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortBySocials_success() throws CommandException {
        List<String> fields = Arrays.asList("socials");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> socialsComparator = (
                p1, p2) -> p1.getSocials().toString().compareToIgnoreCase(p2.getSocials().toString());

        expectedModel.updateSortedPersonList(socialsComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByMultipleFields_success() throws CommandException {
        List<String> fields = Arrays.asList("name", "phone");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> multiFieldComparator = (
                p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
        multiFieldComparator = multiFieldComparator.thenComparing((
                p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString()));

        expectedModel.updateSortedPersonList(multiFieldComparator);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }
    @Test
    public void execute_sortByTagsThenAddress_success() throws CommandException {
        List<String> fields = Arrays.asList("tags", "address");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> tagsComparator = (
                p1, p2) -> p1.getTags().toString().compareToIgnoreCase(p2.getTags().toString());
        tagsComparator = tagsComparator.thenComparing((
                p1, p2) -> p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString()));

        expectedModel.updateSortedPersonList(tagsComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByEmailThenTags_success() throws CommandException {
        List<String> fields = Arrays.asList("email", "tags");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> emailComparator = (
                p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString());
        emailComparator = emailComparator.thenComparingInt(p -> p.getTags().size());

        expectedModel.updateSortedPersonList(emailComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByPhoneThenSocials_success() throws CommandException {
        List<String> fields = Arrays.asList("phone", "socials");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> phoneComparator = (
                p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString());
        phoneComparator = phoneComparator.thenComparingInt(p -> p.getSocials().size());

        expectedModel.updateSortedPersonList(phoneComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByThreeFields_success() throws CommandException {
        List<String> fields = Arrays.asList("name", "phone", "email");
        SortCommand command = new SortCommand(false, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> multiFieldComparator = (
                p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
        multiFieldComparator = multiFieldComparator.thenComparing((
                p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString()));
        multiFieldComparator = multiFieldComparator.thenComparing((
                p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString()));

        expectedModel.updateSortedPersonList(multiFieldComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByTagsReversed_success() throws CommandException {
        List<String> fields = Arrays.asList("tags");
        SortCommand command = new SortCommand(true, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> tagsComparator = (
                p1, p2) -> p1.getTags().toString().compareToIgnoreCase(p2.getTags().toString());
        expectedModel.updateSortedPersonList(tagsComparator.reversed());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }

    @Test
    public void execute_sortByMultipleFieldsReversed_success() throws CommandException {
        List<String> fields = Arrays.asList("name", "phone", "email");
        SortCommand command = new SortCommand(true, fields);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        Comparator<Person> multiFieldComparator = (
                p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
        multiFieldComparator = multiFieldComparator.thenComparing((
                p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString()));
        multiFieldComparator = multiFieldComparator.thenComparing((
                p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString()));

        expectedModel.updateSortedPersonList(multiFieldComparator.reversed());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the lists are sorted the same way
        List<Person> modelList = model.getFilteredPersonList();
        List<Person> expectedList = expectedModel.getFilteredPersonList();
        assertEquals(expectedList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            assertEquals(expectedList.get(i), modelList.get(i));
        }
    }
    @Test
    public void testToString() {
        List<String> fields = Arrays.asList("name", "phone");
        SortCommand command = new SortCommand(true, fields);
        String expected = "SortCommand{isReverse=true, fields=[name, phone]}";
        assertEquals(expected, command.toString());
    }
}



