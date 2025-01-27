package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import task.Tasklist;

public class TasklistTest {

    // Test todo without any arg
    @Test
    public void add_toDoNoArg_fail() {
        assertEquals(-1, Tasklist.add("todo "));
    }

    // Test todo with valid arg
    @Test
    public void add_toDoValidArg_success() {
        assertEquals(1, Tasklist.add("todo read book"));
    }

    // Test todo with invalid arg
    @Test
    public void add_toDoInValidArg_success() {
        assertEquals(1, Tasklist.add("todo read book"));
    }


    // Test deadline without any arg
    @Test
    public void add_deadlineNoArg_fail() {
        assertEquals(-2, Tasklist.add("deadline "));
    }

    // Test deadline with  valid arg
    @Test
    public void add_deadlineValidArg_success() {
        assertEquals(1, Tasklist.add("deadline read book /by 12-08-2024 1800"));
    }

    // Test deadline with arg
    @Test
    public void add_deadlineInvalidArg_success() {
        assertEquals(1, Tasklist.add("deadline read book /by12-08-2024 1800"));
    }

    // Test event without any arg
    @Test
    public void add_eventNoArg_fail() {
        assertEquals(-3, Tasklist.add("event "));
    }


    // Test event with valid arg
    @Test
    public void add_eventValidArg_success() {
        assertEquals(1, Tasklist.add("event sport /from 12-08-2024 1800 /to 13-08-2024 1800"));
    }

    // Test event with invalid arg
    @Test
    public void add_eventInvalidArg_success() {
        assertEquals(1, Tasklist.add("event sport /from12-08-2024 1800 /to 13-08-2024 1800"));
    }

    // Test add with invalid command
    @Test
    public void add_eventInvalidCommand_fail_1() {
        assertEquals(0, Tasklist.add(" "));
    }

    // Test add with invalid command
    @Test
    public void add_eventInvalidCommand_fail_2() {
        assertEquals(0, Tasklist.add("hahaha"));
    }


    // Test delete with invalid input type String
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
