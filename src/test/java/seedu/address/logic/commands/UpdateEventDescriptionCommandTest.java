package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.ModelStub;

public class UpdateEventDescriptionCommandTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String validDescription = "Valid Description";
        assertThrows(NullPointerException.class, () ->
                new UpdateEventDescriptionCommand(null, validDescription));
        assertThrows(NullPointerException.class, () ->
                new UpdateEventDescriptionCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validIndex_updatesEventDescriptionSuccessfully() throws Exception {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        String oldDescription = "Old Description";
        Event event = new Event(
                "Sample Event",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                null,
                oldDescription,
                null,
                new UniquePersonList()
        );
        modelStub.events.add(event);

        String newDescription = "New description for the event";
        UpdateEventDescriptionCommand command =
                new UpdateEventDescriptionCommand(Index.fromOneBased(1), newDescription);
        CommandResult result = command.execute(modelStub);

        Event updatedEvent = event.withUpdatedDescription(newDescription);
        String expectedMessage = String.format(UpdateEventDescriptionCommand.MESSAGE_UPDATE_DESCRIPTION_SUCCESS,
                updatedEvent);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(updatedEvent, modelStub.events.get(0));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        String newDescription = "New description for the event";
        UpdateEventDescriptionCommand command =
                new UpdateEventDescriptionCommand(Index.fromOneBased(1), newDescription);
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        String description = "Description";
        UpdateEventDescriptionCommand commandOne =
                new UpdateEventDescriptionCommand(Index.fromOneBased(1), description);
        UpdateEventDescriptionCommand commandOneCopy =
                new UpdateEventDescriptionCommand(Index.fromOneBased(1), description);
        UpdateEventDescriptionCommand commandTwo =
                new UpdateEventDescriptionCommand(Index.fromOneBased(2), description);

        // same object -> returns true
        assertEquals(commandOne, commandOne);
        // same values -> returns true
        assertEquals(commandOne, commandOneCopy);
        // different index -> returns false
        assertFalse(commandOne.equals(commandTwo));
        // null -> returns false
        assertFalse(commandOne.equals(null));
        // different type -> returns false
        assertFalse(commandOne.equals("some string"));
    }

    /**
     * A Model stub that supports event updates.
     */
    private class ModelStubAcceptingEventUpdate extends ModelStub {
        final ArrayList<Event> events = new ArrayList<>();

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return FXCollections.observableList(events);
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
}
