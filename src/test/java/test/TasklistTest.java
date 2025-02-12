package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import task.Tasklist;

/**
 * TasklistTest is a JUnit test class that verifies the functionality of the Tasklist class.
 * It contains tests for adding, deleting, and handling invalid inputs for tasks.
 */

public class TasklistTest {

    /**
     * Tests adding a todo task without arguments.
     * Expects a failure message indicating that something must be provided after "todo".
     */
    @Test
    public void add_toDoNoArg_fail() {
        assertEquals("There must be something after \"todo\"!", Tasklist.add("todo "));
    }

    /**
     * Tests adding a todo task with valid arguments.
     * Expects success with the task being added and a success message.
     */
    @Test
    public void add_toDoValidArg_success() {
        String expectedOutput = "Got it. I've added this task:\n   [T][ ] read book "
                + "\nNow you have 3 tasks in the list.";
        assertEquals(expectedOutput, Tasklist.add("todo read book"));
    }



    /**
     * Tests adding a deadline task without arguments.
     * Expects a failure message indicating that something must be provided after "deadline".
     */
    @Test
    public void add_deadlineNoArg_fail() {
        assertEquals("There must be something after \"deadline\"!", Tasklist.add("deadline "));
    }

    /**
     * Tests adding a deadline task with valid arguments.
     * Expects success with the task being added and a success message.
     */
    @Test
    public void add_deadlineValidArg_success() {
        String expectedOutput = "Got it. I've added this task:\n   "
                + "[D][ ] read book (by: Aug 12 2024 06:00 PM)\n"
                + "Now you have 2 tasks in the list.";
        assertEquals(expectedOutput, Tasklist.add("deadline read book /by 12-08-2024 1800"));
    }

    /**
     * Tests adding a deadline task with invalid arguments.
     * Expects a failure message indicating incorrect input format.
     */
    @Test
    public void add_deadlineInvalidArg_fail() {
        String expectedError = "    Input format is incorrect.\n"
                + "    todo input format :todo XX\n"
                + "    deadline input format :deadline XX /by dd-mm-yyyy hhmm\n"
                + "    event input format :event XX /from dd-mm-yyyy hhmm /to dd-mm-yyyy hhmm";
        assertEquals(expectedError, Tasklist.add("deadline read book /by12-08-2024 1800"));
    }

    /**
     * Tests adding an event task without arguments.
     * Expects a failure message indicating that something must be provided after "event".
     */
    @Test
    public void add_eventNoArg_fail() {
        assertEquals("There must be something after \"event\"!", Tasklist.add("event "));
    }

    /**
     * Tests adding an event task with valid arguments.
     * Expects success with the task being added and a success message.
     */
    @Test
    public void add_eventValidArg_success() {
        String expectedOutput = "Got it. I've added this task:\n   "
                + "[E][ ] sport (from: Aug 12 2024 06:00 PM to: Aug 13 2024 06:00 PM)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(expectedOutput, Tasklist.add("event sport /from 12-08-2024 1800 /to 13-08-2024 1800"));
    }

    /**
     * Tests adding an event task with invalid arguments.
     * Expects a failure message indicating incorrect input format.
     */
    @Test
    public void add_eventInvalidArg_fail() {
        String expectedError = "    Input format is incorrect.\n"
                + "    todo input format :todo XX\n"
                + "    deadline input format :deadline XX /by dd-mm-yyyy hhmm\n"
                + "    event input format :event XX /from dd-mm-yyyy hhmm /to dd-mm-yyyy hhmm";
        assertEquals(expectedError, Tasklist.add("event sport /from12-08-2024 1800 /to 13-08-2024 1800"));
    }

    /**
     * Tests adding a task with an empty or invalid command.
     * Expects a failure message indicating invalid command.
     */
    @Test
    public void add_eventInvalidCommand_fail_1() {
        assertEquals("System does not support such command. Only todo ..., deadline ..., event..., "
                + "mark..., unmark..., delete..., find..., list..., lookup... and bye!", Tasklist.add(" "));
    }

    /**
     * Tests adding a task with an unrecognized command.
     * Expects a failure message indicating unrecognized command.
     */
    @Test
    public void add_eventInvalidCommand_fail_2() {
        assertEquals("System does not support such command. Only todo ..., deadline ..., event...,"
                + " mark..., unmark..., delete..., find..., list..., lookup... and bye!", Tasklist.add("hahaha"));
    }

    /**
     * Tests deleting a task with an invalid input of type String.
     * Expects a NumberFormatException.
     */
    @Test
    public void delete_invalidInputTypeString_fail_2() {
        try {
            String input = "hahaha";
            Tasklist.delete(Integer.parseInt(input));
            fail("Expected NumberFormatException was not thrown");
        } catch (NumberFormatException e) {
            assertEquals("For input string: \"hahaha\"", e.getMessage());
        }
    }
}
