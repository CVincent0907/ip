package test;

import org.junit.jupiter.api.Test;
import task.Tasklist;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * TasklistTest is a JUnit test class that verifies the functionality of the Tasklist class.
 * It contains tests for adding, deleting, and handling invalid inputs for tasks.
 */
public class TasklistTest {

    /**
     * Tests adding a todo task without arguments.
     * Expects a failure with a return value of -1.
     */
    @Test
    public void add_toDoNoArg_fail() {
        assertEquals(-1, Tasklist.add("todo "));
    }

    /**
     * Tests adding a todo task with valid arguments.
     * Expects success with a return value of 1.
     */
    @Test
    public void add_toDoValidArg_success() {
        assertEquals(1, Tasklist.add("todo read book"));
    }

    /**
     * Tests adding a todo task with invalid arguments.
     * Expects success with a return value of 1.
     */
    @Test
    public void add_toDoInValidArg_success() {
        assertEquals(1, Tasklist.add("todo read book"));
    }


    /**
     * Tests adding a deadline task without arguments.
     * Expects a failure with a return value of -2.
     */
    @Test
    public void add_deadlineNoArg_fail() {
        assertEquals(-2, Tasklist.add("deadline "));
    }

    /**
     * Tests adding a deadline task with valid arguments.
     * Expects success with a return value of 1.
     */
    @Test
    public void add_deadlineValidArg_success() {
        assertEquals(1, Tasklist.add("deadline read book /by 12-08-2024 1800"));
    }

    /**
     * Tests adding a deadline task with invalid arguments.
     * Expects success with a return value of 1.
     */
    @Test
    public void add_deadlineInvalidArg_success() {
        assertEquals(1, Tasklist.add("deadline read book /by12-08-2024 1800"));
    }

    /**
     * Tests adding an event task without arguments.
     * Expects a failure with a return value of -3.
     */
    @Test
    public void add_eventNoArg_fail() {
        assertEquals(-3, Tasklist.add("event "));
    }


    /**
     * Tests adding an event task with valid arguments.
     * Expects success with a return value of 1.
     */
    @Test
    public void add_eventValidArg_success() {
        assertEquals(1, Tasklist.add("event sport /from 12-08-2024 1800 /to 13-08-2024 1800"));
    }

    /**
     * Tests adding an event task with invalid arguments.
     * Expects success with a return value of 1.
     */
    @Test
    public void add_eventInvalidArg_success() {
        assertEquals(1, Tasklist.add("event sport /from12-08-2024 1800 /to 13-08-2024 1800"));
    }

    /**
     * Tests adding a task with an empty or invalid command.
     * Expects a failure with a return value of 0.
     */
    @Test
    public void add_eventInvalidCommand_fail_1() {
        assertEquals(0, Tasklist.add(" "));
    }

    /**
     * Tests adding a task with an unrecognized command.
     * Expects a failure with a return value of 0.
     */
    @Test
    public void add_eventInvalidCommand_fail_2() {
        assertEquals(0, Tasklist.add("hahaha"));
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
