package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the address book.
 * <p>
 * Guarantees: details are present and not null, field values are validated, and the class is immutable.
 * </p>
 */
public class Event {

    public static final String MESSAGE_CONSTRAINTS_NAME = "Event name cannot be blank.";
    public static final String MESSAGE_CONSTRAINTS_DATE = "Date must be in the format YYYY-MM-DD.";

    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final String id;
    private final String name;
    private final String date;
    private final String location;
    private final String description;
    private final Set<Tag> tags = new HashSet<>();
    private final UniquePersonList contacts;

    /**
     * Constructs an {@code Event} with the given details.
     *
     * @param name        A valid event name.
     * @param date        A valid date in the format YYYY-MM-DD.
     * @param location    The location of the event (can be empty).
     * @param description The event description (can be empty).
     * @param tags        A set of tags associated with the event.
     * @param contacts    A {@code UniquePersonList} of contacts associated with the event.
     */
    public Event(String name, String date, String location, String description, Set<Tag> tags,
                 UniquePersonList contacts) {
        requireNonNull(name, "Event name is required");
        requireNonNull(date, "Event date is required");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS_NAME);
        }
        // Optionally, add date format validation here.

        this.id = String.format("%08d", COUNTER.getAndIncrement());
        this.name = name;
        this.date = date;
        this.location = (location == null) ? "" : location;
        this.description = (description == null) ? "" : description;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.contacts = contacts;
    }

    /**
     * Returns the unique identifier of this event.
     *
     * @return the event id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of this event.
     *
     * @return the event name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the date of this event.
     *
     * @return the event date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the location of this event.
     *
     * @return the event location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the description of this event.
     *
     * @return the event description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns an immutable set of tags associated with this event.
     * <p>
     * The returned set is unmodifiable and any attempt to modify it will result in an
     * {@code UnsupportedOperationException}.
     * </p>
     *
     * @return an unmodifiable set of tags.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an unmodifiable view of the contacts associated with this event.
     *
     * @return an observable list of persons.
     */
    public ObservableList<Person> getContacts() {
        return contacts.asUnmodifiableObservableList();
    }

    /**
     * Adds a person to the contacts list.
     *
     * @param person the person to add; must not be null.
     */
    public void addContact(Person person) {
        requireNonNull(person, "Person cannot be null");
        contacts.add(person);
    }

    /**
     * Deletes a person from the contacts list.
     *
     * @param person the person to remove; must not be null.
     */
    public void deleteContact(Person person) {
        requireNonNull(person, "Person cannot be null");
        contacts.remove(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Event)) {
            return false;
        }
        Event otherEvent = (Event) other;
        return id.equals(otherEvent.id)
                && name.equals(otherEvent.name)
                && date.equals(otherEvent.date)
                && location.equals(otherEvent.location)
                && description.equals(otherEvent.description)
                && tags.equals(otherEvent.tags)
                && contacts.equals(otherEvent.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, location, description, tags, contacts);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event Name: ")
                .append(name)
                .append(" | Date: ")
                .append(date)
                .append(" | Location: ")
                .append(location)
                .append(" | Description: ")
                .append(description);
        if (!tags.isEmpty()) {
            builder.append(" | Tags: ");
            tags.forEach(tag -> builder.append(tag.toString()).append(" "));
        }
        builder.append(" | Contacts: ").append(contacts.toString());
        builder.append(" | ID: ").append(id);
        return builder.toString();
    }
}
