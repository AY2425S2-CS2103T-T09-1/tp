package seedu.address.logic.commands;

import java.util.Comparator;
import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the address book by one or more fields.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the address book by one or more fields.\n"
            + "Parameters: [-r] [FIELD]...\n"
            + "  -r: Reverse the order\n"
            + "  FIELD: one or more fields to sort by (e.g., name, phone, email, address, tags, socials)\n"
            + "Example: " + COMMAND_WORD + " -r name phone";

    private final boolean isReverse;
    private final List<String> fields;

    /**
     * Creates a SortCommand to sort the address book by the specified fields.
     *
     * @param isReverse Whether to reverse the sorting order
     * @param fields The fields by which to sort
     */
    public SortCommand(boolean isReverse, List<String> fields) {
        this.isReverse = isReverse;
        this.fields = fields;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Comparator<Person> comparator = createComparator(fields);

        if (isReverse) {
            comparator = comparator.reversed();
        }

        model.updateSortedPersonList(comparator);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Creates a comparator based on the fields provided.
     *
     * @param fields The fields to sort by
     * @return A comparator for sorting
     */
    private Comparator<Person> createComparator(List<String> fields) {
        Comparator<Person> comparator;

        String firstField = fields.get(0).toLowerCase();
        comparator = switch (firstField) {
            case "name" -> (p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
            case "phone" -> (p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString());
            case "email" -> (p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString());
            case "address" -> (p1, p2) -> p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString());
            case "tags" -> (p1, p2) -> p1.getTags().toString().compareToIgnoreCase(p2.getTags().toString());
            case "socials" -> (p1, p2) -> p1.getSocials().toString().compareToIgnoreCase(p2.getSocials().toString());
            default -> null;
        };

        for (int i = 1; i < fields.size(); i++) {
            String field = fields.get(i).toLowerCase();
            comparator = switch (field) {
                case "name" ->
                        comparator.thenComparing((p1, p2) -> p1.getName().toString()
                                .compareToIgnoreCase(p2.getName().toString()));
                case "phone" ->
                        comparator.thenComparing((p1, p2) -> p1.getPhone().toString()
                                .compareToIgnoreCase(p2.getPhone().toString()));
                case "email" ->
                        comparator.thenComparing((p1, p2) -> p1.getEmail().toString()
                                .compareToIgnoreCase(p2.getEmail().toString()));
                case "address" ->
                        comparator.thenComparing((p1, p2) -> p1.getAddress().toString()
                                .compareToIgnoreCase(p2.getAddress().toString()));
                case "tags" -> comparator.thenComparingInt(p -> p.getTags().size());
                case "socials" -> comparator.thenComparingInt(p -> p.getSocials().size());
                default -> null;
            };
        }

        return comparator;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand otherSortCommand)) {
            return false;
        }

        return isReverse == otherSortCommand.isReverse && fields.equals(otherSortCommand.fields);
    }

    @Override
    public String toString() {
        return "SortCommand{" + "isReverse=" + isReverse + ", fields=" + fields + '}';
    }
}
