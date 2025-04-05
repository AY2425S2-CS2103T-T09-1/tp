package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true for events */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true for relationships */
    Predicate<Relationship> PREDICATE_SHOW_ALL_RELATIONSHIPS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered person list, sorted by the specified comparator.
     * If no comparator is provided, the list remains unsorted.
     *
     * @return an observable list of sorted and filtered persons.
     */
    ObservableList<Person> getSortedFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the sorting order of the filtered person list.
     *
     * @throws NullPointerException if {@code comparator} is null.
     */
    void updateSortedPersonList(Comparator<Person> comparator) throws CommandException;

    /**
     * Returns the person with the given ID, or null if not found.
     */
    Person getPersonById(String id);

    /**
     * Returns true if a relationship with the same identity fields exists in the address book.
     */
    boolean hasRelationship(Relationship relationship);

    /**
     * Returns true if a relationship exists between the given users.
     * The relationship can be found using either the forward or reverse name.
     * The order of user IDs doesn't matter.
     */
    boolean hasRelationship(String userId1, String userId2, String relationshipName);

    /**
     * Returns true if any relationship exists between the given users,
     * regardless of the relationship names.
     */
    boolean hasAnyRelationship(String userId1, String userId2);

    /**
     * Adds a relationship to the address book.
     * The relationship must not already exist in the address book.
     */
    void addRelationship(Relationship relationship);

    /**
     * Deletes the relationship with the given user IDs and name.
     * The relationship must exist in the address book.
     */
    void deleteRelationship(String userId1, String userId2, String relationshipName)
            throws RelationshipNotFoundException;

    /** Returns an unmodifiable view of the filtered relationship list */
    ObservableList<Relationship> getFilteredRelationshipList();

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    boolean hasEvent(Event event);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the address book.
     */
    void addEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the address book.
     */
    void deleteEvent(Event event);

    /**
     * Returns an unmodifiable view of the filtered event list.
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns the event with the given ID, or null if not found.
     */
    Event getEventById(String id);

    /**
     * Replaces the given event {@code target} with {@code updatedEvent}.
     * {@code target} must exist in the address book.
     */
    void updateEvent(Event target, Event updatedEvent);

    /**
     * Returns the relationship with the given user IDs and name, or null if not found.
     */
    Relationship getRelationship(String userId1, String userId2, String relationshipName);

    /**
     * Adds a tag to a relationship between two persons.
     */
    void updateRelationship(Relationship target, Relationship updatedRelationship);

    /**
     * Updates the filter of the filtered relationship list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRelationshipList(Predicate<Relationship> predicate);
}
