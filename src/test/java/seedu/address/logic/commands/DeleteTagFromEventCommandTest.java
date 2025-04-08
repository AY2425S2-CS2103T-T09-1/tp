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
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModelStub;

public class DeleteTagFromEventCommandTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Tag validTag = new Tag("Finance");
        assertThrows(NullPointerException.class, () ->
                new DeleteTagFromEventCommand(null, validTag));
        assertThrows(NullPointerException.class, () ->
                new DeleteTagFromEventCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validIndex_deletesTagSuccessfully() throws Exception {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        Tag tag = new Tag("Finance");
        // Create an event with the tag present.
        HashSet<Tag> tags = new HashSet<>();
        tags.add(tag);
        Event event = new Event(
                "Budget Meeting",
                LocalDateTime.of(2025, 1, 1, 10, 0),
                "Room B",
                "Discuss budget",
                tags,
                new UniquePersonList()
        );
        modelStub.events.add(event);

        DeleteTagFromEventCommand command = new DeleteTagFromEventCommand(Index.fromOneBased(1), tag);
        CommandResult result = command.execute(modelStub);

        Event updatedEvent = event.withRemovedTag(tag);
        String expectedMessage = String.format(DeleteTagFromEventCommand.MESSAGE_DELETE_TAG_SUCCESS, updatedEvent);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(updatedEvent, modelStub.events.get(0));
    }

    @Test
    public void execute_tagNotFound_throwsCommandException() {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        Tag tag = new Tag("Finance");
        // Create an event without the tag.
        Event event = new Event(
                "Budget Meeting",
                LocalDateTime.of(2025, 1, 1, 10, 0),
                "Room B",
                "Discuss budget",
                new HashSet<>(),
                new UniquePersonList()
        );
        modelStub.events.add(event);

        DeleteTagFromEventCommand command = new DeleteTagFromEventCommand(Index.fromOneBased(1), tag);
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubAcceptingEventUpdate modelStub = new ModelStubAcceptingEventUpdate();
        Tag tag = new Tag("Finance");
        DeleteTagFromEventCommand command = new DeleteTagFromEventCommand(Index.fromOneBased(1), tag);
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Tag tag = new Tag("Finance");
        DeleteTagFromEventCommand commandOne = new DeleteTagFromEventCommand(Index.fromOneBased(1), tag);
        DeleteTagFromEventCommand commandOneCopy = new DeleteTagFromEventCommand(Index.fromOneBased(1), tag);
        DeleteTagFromEventCommand commandTwo = new DeleteTagFromEventCommand(Index.fromOneBased(2), tag);

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
