package seedu.address.model.commandhistory;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A class to manage the history of commands.
 */
public class CommandHistory {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Deque to store the last 10 user inputs.
     */
    private static final Deque<String> lastCommands = new LinkedList<>();

    /**
     * Adds the given command input to the history of last 10 commands.
     * If the list exceeds 10, removes the oldest command.
     *
     * @param userInput The command to add to the history.
     */
    public static void addCommandToHistory(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");

        if (Objects.equals(commandWord, RedoCommand.COMMAND_WORD)) {
            return;
        }
        if (lastCommands.size() == 10) {
            lastCommands.pollLast();
        }
        lastCommands.offerFirst(userInput);
    }

    /**
     * Retrieves the command at the specified index from the lastCommands deque.
     *
     * @param index The index of the command in the deque (1-based).
     * @return The command string at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public static String getCommandFromHistory(int index) {
        int dequeIndex = index - 1;

        if (lastCommands.size() <= dequeIndex) {
            throw new IndexOutOfBoundsException("Not enough commands in history to redo.");
        }

        return (String) lastCommands.toArray()[dequeIndex];
    }

    /**
     * Returns all the commands in the history.
     *
     * @return List of all commands in history.
     */
    public static Deque<String> getAllCommands() {
        return lastCommands;
    }
}
