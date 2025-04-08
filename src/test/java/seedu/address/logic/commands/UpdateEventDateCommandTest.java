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

public class UpdateEventDateCommandTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        LocalDateTime validDate = LocalDateTime.now();
        // Passing a null index should throw an exception.
        assertThrows(NullPointerException.class, () -> new UpdateEventDateCommand(null, validDate));
        // Passing a null newDate should throw an exception.
        assertThrows(NullPointerException.class, () -> new UpdateEventDateCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validIndex_updatesEventDateSuccessfully() throws Exception {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        LocalDateTime originalDate = LocalDateTime.of(2025, 1, 1, 12, 0);
        Event event = new Event(
                "Sample Event",
                originalDate,
                null,
                null,
                null,
                new UniquePersonList()
        );
        // Add the event to the model's list so it can be updated.
        modelStub.events.add(event);

        LocalDateTime newDate = LocalDateTime.of(2025, 3, 15, 9, 30);
        UpdateEventDateCommand command = new UpdateEventDateCommand(Index.fromOneBased(1), newDate);
        CommandResult result = command.execute(modelStub);

        // Create an expected updated event.
        Event updatedEvent = event.withUpdatedDate(newDate);
        String expectedMessage = String.format(UpdateEventDateCommand.MESSAGE_UPDATE_DATE_SUCCESS, updatedEvent);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        // Check that the event in the model has been updated.
        assertEquals(updatedEvent, modelStub.events.get(0));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        LocalDateTime newDate = LocalDateTime.of(2025, 3, 15, 9, 30);
        // With no events in the list, an invalid index should throw a CommandException.
        UpdateEventDateCommand command = new UpdateEventDateCommand(Index.fromOneBased(1), newDate);
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        LocalDateTime date = LocalDateTime.of(2025, 3, 15, 9, 30);
        UpdateEventDateCommand commandOne = new UpdateEventDateCommand(Index.fromOneBased(1), date);
        UpdateEventDateCommand commandOneCopy = new UpdateEventDateCommand(Index.fromOneBased(1), date);
        UpdateEventDateCommand commandTwo = new UpdateEventDateCommand(Index.fromOneBased(2), date);

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
     * A Model stub that accepts event updates. It reuses the ModelStub from testutil and only overrides the
     * methods necessary for UpdateEventDateCommand testing.
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
