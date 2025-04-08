package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.ModelStub;

public class AddContactToEventCommandTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Person validContact = createDummyPerson("John Doe");
        assertThrows(NullPointerException.class, () ->
                new AddContactToEventCommand(null, validContact));
        assertThrows(NullPointerException.class, () ->
                new AddContactToEventCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validIndex_addsContactSuccessfully() throws Exception {
        ModelStubAcceptingContactUpdate modelStub = new ModelStubAcceptingContactUpdate();
        Person contact = createDummyPerson("John Doe");
        // Create an event with no contacts.
        Event event = new Event(
                "Workshop",
                LocalDateTime.of(2025, 5, 20, 14, 0),
                "Hall A",
                "Tech Workshop",
                null,
                new UniquePersonList()
        );
        modelStub.events.add(event);
        // Simulate that the contact exists in the address book.
        modelStub.personExists = true;

        AddContactToEventCommand command = new AddContactToEventCommand(Index.fromOneBased(1), contact);
        CommandResult result = command.execute(modelStub);

        // Verify that the event's contacts now include the added contact.
        boolean containsContact = event.getContacts().contains(contact);
        assertEquals(true, containsContact);
        String expectedMessage = String.format(AddContactToEventCommand.MESSAGE_ADD_CONTACT_SUCCESS, event);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        ModelStubAcceptingContactUpdate modelStub = new ModelStubAcceptingContactUpdate();
        Person contact = createDummyPerson("John Doe");
        AddContactToEventCommand command = new AddContactToEventCommand(Index.fromOneBased(1), contact);
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_contactDoesNotExist_throwsCommandException() {
        ModelStubAcceptingContactUpdate modelStub = new ModelStubAcceptingContactUpdate();
        Person contact = createDummyPerson("John Doe");
        Event event = new Event(
                "Workshop",
                LocalDateTime.of(2025, 5, 20, 14, 0),
                "Hall A",
                "Tech Workshop",
                null,
                new UniquePersonList()
        );
        modelStub.events.add(event);
        // Simulate that the contact does not exist.
        modelStub.personExists = false;

        AddContactToEventCommand command = new AddContactToEventCommand(Index.fromOneBased(1), contact);
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_contactAlreadyExists_throwsCommandException() {
        ModelStubAcceptingContactUpdate modelStub = new ModelStubAcceptingContactUpdate();
        Person contact = createDummyPerson("John Doe");
        Event event = new Event(
                "Workshop",
                LocalDateTime.of(2025, 5, 20, 14, 0),
                "Hall A",
                "Tech Workshop",
                null,
                new UniquePersonList()
        );
        // Add the contact beforehand.
        event.addContact(contact);
        modelStub.events.add(event);
        modelStub.personExists = true;

        AddContactToEventCommand command = new AddContactToEventCommand(Index.fromOneBased(1), contact);
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Person contact = createDummyPerson("John Doe");
        AddContactToEventCommand commandOne = new AddContactToEventCommand(Index.fromOneBased(1), contact);
        AddContactToEventCommand commandOneCopy = new AddContactToEventCommand(Index.fromOneBased(1), contact);
        Person anotherContact = createDummyPerson("Jane Smith");
        AddContactToEventCommand commandTwo = new AddContactToEventCommand(Index.fromOneBased(1), anotherContact);

        // same object -> returns true
        assertEquals(commandOne, commandOne);
        // same values -> returns true
        assertEquals(commandOne, commandOneCopy);
        // different contact -> returns false
        assertFalse(commandOne.equals(commandTwo));
        // null -> returns false
        assertFalse(commandOne.equals(null));
        // different type -> returns false
        assertFalse(commandOne.equals("some string"));
    }

    /**
     * A Model stub that supports contact addition for events.
     */
    private class ModelStubAcceptingContactUpdate extends ModelStub {
        final ArrayList<Event> events = new ArrayList<>();
        private boolean personExists = false;

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return FXCollections.observableList(events);
        }

        @Override
        public boolean hasPerson(Person person) {
            return personExists;
        }

        @Override
        public void updateEvent(Event originalEvent, Event updatedEvent) {
            int index = events.indexOf(originalEvent);
            if (index == -1) {
                throw new AssertionError("Event not found in model stub.");
            }
            events.set(index, updatedEvent);
        }
    }

    /**
     * Helper method to create a dummy Person instance with the given full name.
     */
    private Person createDummyPerson(String fullName) {
        return new Person(
                new Name(fullName),
                new Phone("00000000"),
                new Email("dummy@example.com"),
                new Address("Dummy Address"),
                new HashSet<>(),
                new HashSet<>()
        );
    }
}
