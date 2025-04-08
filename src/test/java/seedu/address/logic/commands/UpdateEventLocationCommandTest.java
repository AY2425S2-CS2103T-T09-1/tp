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

public class UpdateEventLocationCommandTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String validLocation = "Conference Room";
        assertThrows(NullPointerException.class, () ->
                new UpdateEventLocationCommand(null, validLocation));
        assertThrows(NullPointerException.class, () ->
                new UpdateEventLocationCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validIndex_updatesEventLocationSuccessfully() throws Exception {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        String originalLocation = "Room A";
        Event event = new Event(
                "Meeting",
                LocalDateTime.of(2025, 1, 1, 10, 0),
                originalLocation,
                null,
                null,
                new UniquePersonList()
        );
        modelStub.events.add(event);

        String newLocation = "Conference Room";
        UpdateEventLocationCommand command =
                new UpdateEventLocationCommand(Index.fromOneBased(1), newLocation);
        CommandResult result = command.execute(modelStub);

        Event updatedEvent = event.withUpdatedLocation(newLocation);
        String expectedMessage =
            String.format(UpdateEventLocationCommand.MESSAGE_UPDATE_LOCATION_SUCCESS, updatedEvent);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(updatedEvent, modelStub.events.get(0));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        String newLocation = "Conference Room";
        UpdateEventLocationCommand command = new UpdateEventLocationCommand(Index.fromOneBased(1), newLocation);
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        String location = "Conference Room";
        UpdateEventLocationCommand commandOne = new UpdateEventLocationCommand(Index.fromOneBased(1), location);
        UpdateEventLocationCommand commandOneCopy = new UpdateEventLocationCommand(Index.fromOneBased(1), location);
        UpdateEventLocationCommand commandTwo = new UpdateEventLocationCommand(Index.fromOneBased(2), location);

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
