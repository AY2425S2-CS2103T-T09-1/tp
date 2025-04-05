package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.predicate.RelationshipContainsKeywordsAsSubstringPredicate;

/**
 * Finds and lists all persons in address book whose relationships contain any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindRelationshipCommand extends Command {

    public static final String COMMAND_WORD = "findRelationship";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose role name in any relationship "
            + "contains any of the specified keywords (case-insensitive) and displays them as a list with index "
            + "numbers.\n" + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " family friend";

    private final RelationshipContainsKeywordsAsSubstringPredicate predicate;

    public FindRelationshipCommand(RelationshipContainsKeywordsAsSubstringPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindRelationshipCommand otherFindRelationshipCommand)) {
            return false;
        }

        return predicate.equals(otherFindRelationshipCommand.predicate);
    }

    @Override
    public String toString() {
        return "FindRelationshipCommand{" + "predicate=" + predicate + "}";
    }
}
