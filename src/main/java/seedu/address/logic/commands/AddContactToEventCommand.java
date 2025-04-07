package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Adds a contact to an event in the address book.
 * <p>
 * The event is identified by its index in the filtered event list.
 * The specified contact (a Person) is added to the event’s contacts.
 * </p>
 */
public class AddContactToEventCommand extends Command {

    public static final String COMMAND_WORD = "addEventContact";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to an event.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "c/CONTACT_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 c/John Doe";
    public static final String MESSAGE_ADD_CONTACT_SUCCESS = "Contact added to event: %1$s";
    public static final String MESSAGE_CONTACT_ALREADY_EXISTS = "This contact already exists in the event";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The event index provided is invalid.";
    public static final String MESSAGE_CONTACT_NOT_FOUND =
            "Contact %s does not exist in the address book. Please add the contact first.";

    private final Index eventIndex;
    private final Person contact;

    /**
     * Creates an AddContactToEventCommand to add the specified contact to the event at the given index.
     *
     * @param eventIndex the index of the event in the filtered event list.
     * @param contact    the contact to add.
     */
    public AddContactToEventCommand(Index eventIndex, Person contact) {
        requireNonNull(eventIndex);
        requireNonNull(contact);
        this.eventIndex = eventIndex;
        this.contact = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (eventIndex.getZeroBased() >= model.getFilteredEventList().size()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }
        // Check that the contact exists in the address book.
        if (!model.hasPerson(contact)) {
            throw new CommandException(String.format(MESSAGE_CONTACT_NOT_FOUND, contact.getName().fullName));
        }
        Event originalEvent = model.getFilteredEventList().get(eventIndex.getZeroBased());
        // Check if the contact already exists in the event.
        if (originalEvent.getContacts().stream().anyMatch(p -> p.equals(contact))) {
            throw new CommandException(MESSAGE_CONTACT_ALREADY_EXISTS);
        }
        // Add the contact to the event.
        originalEvent.addContact(contact);
        // Notify the model that the event has been updated (force UI refresh).
        model.updateEvent(originalEvent, originalEvent);
        return new CommandResult(String.format(MESSAGE_ADD_CONTACT_SUCCESS, originalEvent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddContactToEventCommand)) {
            return false;
        }
        AddContactToEventCommand otherCommand = (AddContactToEventCommand) other;
        return eventIndex.equals(otherCommand.eventIndex) && contact.equals(otherCommand.contact);
    }
}
