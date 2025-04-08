package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DateParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.ModelStub;

class DeleteEventCommandTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteEventCommand(null));
    }

    @Test
    public void execute_eventFound_eventDeletedSuccessful() throws CommandException, ParseException {
        ModelStubAcceptingEventDeletion modelStub = new ModelStubAcceptingEventDeletion();
        Event validEvent = new Event(
                "Test Event",
                DateParserUtil.parseDate("2025-01-01"),
                null,
                null,
                null,
                new UniquePersonList()
        );
        // Add the event to the stub's list so that it can be found and then deleted.
        modelStub.events.add(validEvent);

        // Execute DeleteEventCommand using the valid event ID
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(validEvent.getId());
        CommandResult commandResult = deleteEventCommand.execute(modelStub);

        // Verify that the event was successfully deleted
        assertEquals(DeleteEventCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertEquals(0, modelStub.events.size());
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        ModelStubAcceptingEventDeletion modelStub = new ModelStubAcceptingEventDeletion();
        // Pass an ID that does not exist in modelStub
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand("nonexistentId");

        // Expect CommandException because the event ID is not found in modelStub
        assertThrows(CommandException.class, () -> deleteEventCommand.execute(modelStub));
    }

    /**
     * A Model stub that supports deletion of events.
     */
    private class ModelStubAcceptingEventDeletion extends ModelStub {
        final java.util.ArrayList<Event> events = new java.util.ArrayList<>();

        @Override
        public Event getEventById(String id) {
            for (Event event : events) {
                if (event.getId().equals(id)) {
                    return event;
                }
            }
            return null;
        }

        @Override
        public void deleteEvent(Event event) {
            requireNonNull(event);
            events.remove(event);
        }
    }
}
