package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RelationshipBuilder;

public class AddRelationshipCommandTest {

    @Test
    public void constructor_nullRelationship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                null,
                "2",
                "Friend",
                "Reports to",
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                "1",
                null,
                "Friend",
                "Reports to",
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                "1",
                "2",
                null,
                "Reports to",
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                "1",
                "2",
                "Friend",
                null,
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                "1",
                "2",
                "Friend",
                "Reports to",
                null));
        assertThrows(NullPointerException.class, "First user ID cannot be null", () -> new AddRelationshipCommand(
                null,
                "2",
                "Friend",
                "Reports to",
                new HashSet<>()));
        assertThrows(NullPointerException.class,
                "Second user ID cannot be null", () -> new AddRelationshipCommand(
                        "1",
                        null,
                        "Friend",
                        "Reports to",
                        new HashSet<>()));
        assertThrows(NullPointerException.class,
                "Forward relationship name cannot be null", () -> new AddRelationshipCommand(
                        "1",
                        "2",
                        null,
                        "Reports to",
                        new HashSet<>()));
        assertThrows(NullPointerException.class,
                "Reverse relationship name cannot be null", () -> new AddRelationshipCommand(
                        "1",
                        "2",
                        "Friend",
                        null,
                        new HashSet<>()));
        assertThrows(NullPointerException.class,
                "Tags set cannot be null", () -> new AddRelationshipCommand(
                        "1",
                        "2",
                        "Friend",
                        "Reports to",
                        null));
    }

    @Test
    public void execute_relationshipAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRelationshipAdded modelStub = new ModelStubAcceptingRelationshipAdded();
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();
        modelStub.addPerson(person1);
        modelStub.addPerson(person2);

        Relationship validRelationship = new RelationshipBuilder()
                .withUser1Id(person1.getId())
                .withUser2Id(person2.getId())
                .build();

        CommandResult commandResult = new AddRelationshipCommand(
                person1.getId(), person2.getId(), "Boss of", "Reports to", new HashSet<>()).execute(modelStub);

        assertEquals("New relationship created between Amy Bee and Bob", commandResult.getFeedbackToUser());
        assertEquals(validRelationship, modelStub.relationshipAdded);
    }

    @Test
    public void execute_duplicateRelationship_throwsCommandException() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();

        ModelStub modelStub = new ModelStubWithPersonsAndRelationship(person1, person2);

        AddRelationshipCommand addRelationshipCommand = new AddRelationshipCommand(
                person1.getId(), person2.getId(), "Boss of", "Reports to", new HashSet<>());

        assertThrows(CommandException.class,
                AddRelationshipCommand.MESSAGE_DUPLICATE_RELATIONSHIP, () -> addRelationshipCommand.execute(modelStub));
    }

    @Test
    public void execute_samePerson_throwsCommandException() {
        Person person = new PersonBuilder().build();

        ModelStub modelStub = new ModelStubWithPerson(person);

        AddRelationshipCommand addRelationshipCommand = new AddRelationshipCommand(
                person.getId(), person.getId(), "Boss of", "Reports to", new HashSet<>());

        assertThrows(CommandException.class,
                AddRelationshipCommand.MESSAGE_SAME_PERSON, () -> addRelationshipCommand.execute(modelStub));
    }

    @Test
    public void execute_emptyName_throwsCommandException() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();

        ModelStub modelStub = new ModelStubWithPersons(person1, person2);

        AddRelationshipCommand addRelationshipCommand = new AddRelationshipCommand(
                person1.getId(), person2.getId(), "", "", new HashSet<>());

        assertThrows(CommandException.class,
                AddRelationshipCommand.MESSAGE_EMPTY_NAME, () -> addRelationshipCommand.execute(modelStub));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        AddRelationshipCommand command = new AddRelationshipCommand(
                "1", "2", "Friend", "Reports to", new HashSet<>());
        assertThrows(NullPointerException.class, "Model cannot be null", () -> command.execute(null));
    }

    @Test
    public void execute_hasExistingReversedRelationship_throwsCommandException() throws Exception {
        ModelStubAcceptingRelationshipAdded modelStub = new ModelStubAcceptingRelationshipAdded();
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();
        modelStub.addPerson(person1);
        modelStub.addPerson(person2);

        // Add reversed relationship first
        Relationship existingRelationship = new RelationshipBuilder()
                .withUser1Id(person2.getId())
                .withUser2Id(person1.getId())
                .withForwardName("Reports to")
                .withReverseName("Boss of")
                .build();
        modelStub.addRelationship(existingRelationship);

        // Try to add relationship in opposite direction
        AddRelationshipCommand addCommand = new AddRelationshipCommand(
                person1.getId(), person2.getId(), "Boss of", "Reports to", new HashSet<>());

        assertThrows(CommandException.class, AddRelationshipCommand.MESSAGE_DUPLICATE_RELATIONSHIP, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void toString_returnsCorrectString() {
        AddRelationshipCommand command = new AddRelationshipCommand(
                "1", "2", "Boss of", "Reports to", new HashSet<>());
        String expected = "seedu.address.logic.commands.AddRelationshipCommand{userId1=1, userId2=2, "
                + "forwardName=Boss of, reverseName=Reports to, tags=[]}";
        assertEquals(expected, command.toString());
    }

    @Test
    public void equals() {
        String user1Id = "1";
        String user2Id = "2";
        String name1 = "Friend";
        String name2 = "BusinessPartner";

        AddRelationshipCommand addFriendCommand = new AddRelationshipCommand(
                user1Id, user2Id, name1, "Reports to", new HashSet<>());
        AddRelationshipCommand addBusinessPartnerCommand = new AddRelationshipCommand(
                user1Id, user2Id, name2, "Reports to", new HashSet<>());

        // same object -> returns true
        assertEquals(addFriendCommand, addFriendCommand);

        // same values -> returns true
        AddRelationshipCommand addFriendCommandCopy = new AddRelationshipCommand(
                user1Id, user2Id, name1, "Reports to", new HashSet<>());
        assertEquals(addFriendCommand, addFriendCommandCopy);

        // null -> returns false
        assertNotEquals(null, addFriendCommand);

        // different name -> returns false
        assertNotEquals(addFriendCommand, addBusinessPartnerCommand);
    }

    /**
     * A Model stub that contains a single person.
     */
    private static class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public Person getPersonById(String id) {
            if (person.getId().equals(id)) {
                return person;
            }
            return null;
        }
    }

    /**
     * A Model stub that contains two persons.
     */
    private static class ModelStubWithPersons extends ModelStub {
        private final Person person1;
        private final Person person2;

        ModelStubWithPersons(Person person1, Person person2) {
            requireNonNull(person1);
            requireNonNull(person2);
            this.person1 = person1;
            this.person2 = person2;
        }

        @Override
        public Person getPersonById(String id) {
            if (person1.getId().equals(id)) {
                return person1;
            } else if (person2.getId().equals(id)) {
                return person2;
            }
            return null;
        }
    }

    /**
     * A Model stub that contains two persons and a relationship between them.
     */
    private static class ModelStubWithPersonsAndRelationship extends ModelStubWithPersons {
        private final Relationship relationship;

        ModelStubWithPersonsAndRelationship(Person person1, Person person2) {
            super(person1, person2);
            this.relationship = new RelationshipBuilder()
                    .withUser1Id(person1.getId())
                    .withUser2Id(person2.getId())
                    .build();
        }

        @Override
        public boolean hasRelationship(String userId1, String userId2, String relationshipName) {
            return relationship.isSameRelationship(userId1, userId2, relationshipName);
        }
    }

    /**
     * A Model stub that always accepts the relationship being added.
     */
    private static class ModelStubAcceptingRelationshipAdded extends ModelStub {
        private Relationship relationshipAdded;
        private final java.util.ArrayList<Person> persons = new java.util.ArrayList<>();

        @Override
        public boolean hasRelationship(String userId1, String userId2, String relationshipName) {
            requireNonNull(userId1);
            requireNonNull(userId2);
            requireNonNull(relationshipName);
            return relationshipAdded != null
                    && relationshipAdded.isSameRelationship(userId1, userId2, relationshipName);
        }

        @Override
        public void addRelationship(Relationship relationship) {
            requireNonNull(relationship);
            relationshipAdded = relationship;
        }

        @Override
        public Person getPersonById(String id) {
            requireNonNull(id);
            return persons.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            persons.add(person);
        }
    }
}
