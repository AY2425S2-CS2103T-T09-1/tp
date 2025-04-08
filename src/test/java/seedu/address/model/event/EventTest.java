package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

public class EventTest {

    @Test
    public void getTags_modifyList_throwsUnsupportedOperationException() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("finance"));
        UniquePersonList contacts = new UniquePersonList();
        Event event = new Event("00000001", "Meeting", LocalDateTime.of(2025, 5, 20, 14, 0),
                "Room A", "Discuss Budget", tags, contacts);
        assertThrows(UnsupportedOperationException.class, () -> event.getTags().remove(new Tag("finance")));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("finance"));
        UniquePersonList contacts = new UniquePersonList();
        Event event1 = new Event("00000001", "Meeting", LocalDateTime.of(2025, 5, 20, 14, 0),
                "Room A", "Discuss Budget", tags, contacts);
        Event event2 = new Event("00000001", "Meeting", LocalDateTime.of(2025, 5, 20, 14, 0),
                "Room A", "Discuss Budget", tags, contacts);
        assertEquals(event1, event2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("finance"));
        UniquePersonList contacts = new UniquePersonList();
        Event event1 = new Event("00000001", "Meeting", LocalDateTime.of(2025, 5, 20, 14, 0),
                "Room A", "Discuss Budget", tags, contacts);
        // Different name
        Event event2 = new Event("00000001", "Conference", LocalDateTime.of(2025, 5, 20, 14, 0),
                "Room A", "Discuss Budget", tags, contacts);
        assertNotEquals(event1, event2);

        // Different date
        event2 = new Event("00000001", "Meeting", LocalDateTime.of(2025, 5, 21, 14, 0),
                "Room A", "Discuss Budget", tags, contacts);
        assertNotEquals(event1, event2);

        // Different location
        event2 = new Event("00000001", "Meeting", LocalDateTime.of(2025, 5, 20, 14, 0),
                "Room B", "Discuss Budget", tags, contacts);
        assertNotEquals(event1, event2);

        // Different description
        event2 = new Event("00000001", "Meeting", LocalDateTime.of(2025, 5, 20, 14, 0),
                "Room A", "Plan Budget", tags, contacts);
        assertNotEquals(event1, event2);

        // Different tags
        Set<Tag> differentTags = new HashSet<>();
        differentTags.add(new Tag("project"));
        event2 = new Event("00000001", "Meeting", LocalDateTime.of(2025, 5, 20, 14, 0),
                "Room A", "Discuss Budget", differentTags, contacts);
        assertNotEquals(event1, event2);
    }
}
