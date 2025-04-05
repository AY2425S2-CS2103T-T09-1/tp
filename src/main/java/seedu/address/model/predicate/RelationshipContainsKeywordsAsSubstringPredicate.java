package seedu.address.model.predicate;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;

/**
 * Tests that a {@code Person}'s {@code Relationships} matches any of the keywords given.
 */
public class RelationshipContainsKeywordsAsSubstringPredicate implements Predicate<Person> {
    private final List<String> keywords;

    private final Model model;

    /**
     * Constructs a {@code RelationshipContainsKeywordsAsSubstringPredicate}.
     *
     * @param keywords The list of keywords to match against relationship fields
     * @param model The model to search within for relationships
     */
    public RelationshipContainsKeywordsAsSubstringPredicate(List<String> keywords, Model model) {
        this.keywords = keywords;
        this.model = model;
    }

    @Override
    public boolean test(Person person) {
        ObservableList<Relationship> relationships = model.getFilteredRelationshipList();
        return relationships.stream().anyMatch(relationship -> {
            if (person.getId().equals(relationship.getFirstUserId())) {
                return keywords.stream().anyMatch(keyword ->
                        relationship.getForwardName() != null
                                && containsSubstringIgnoreCase(relationship.getForwardName(), keyword));
            }
            if (person.getId().equals(relationship.getSecondUserId())) {
                return keywords.stream().anyMatch(keyword ->
                        relationship.getReverseName() != null
                                && containsSubstringIgnoreCase(relationship.getReverseName(), keyword));
            }
            return false;
        });
    }
    private boolean containsSubstringIgnoreCase(String role, String keyword) {
        return role.toLowerCase().contains(keyword.toLowerCase());
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RelationshipContainsKeywordsAsSubstringPredicate otherPredicate)) {
            return false;
        }

        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
