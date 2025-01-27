package test;


import org.junit.jupiter.api.Test;
import task.Deadline;
import task.Event;
import task.Todo;
import application.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains unit tests for the Parser class methods.
 * The tests validate the functionality of extracting and creating tasks from
 * both user input and saved files using formatted and unformatted strings.
 *
 * <p>Test Naming Convention:</p>
 * Tests follow the convention: methodName_inputType_expectedOutput
 */
public class ParserTest {

    /**
     * Tests if the method correctly extracts and creates a Todo task
     * from a properly formatted user input string.
     */
    @Test
    public void extractAndCreateTask_toDoInput_success() {
        assertEquals(true, Parser.extractAndCreateTask("todo Read Book", Todo.REGEX_1, 1 ));
    }

    /**
     * Tests if the method correctly extracts and creates a Deadline task
     * from a properly formatted user input string.
     */
    @Test
    public void extractAndCreateTask_deadlineInput_success() {
        assertEquals(true, Parser.extractAndCreateTask("deadline task /by 18-09-2023 1800", Deadline.DATE_TIME_REGEX_1, 2 ));
    }

    /**
     * Tests if the method correctly extracts and creates an Event task
     * from a properly formatted user input string.
     */
    @Test
    public void extractAndCreateTask_eventInput_success() {
        assertEquals(true, Parser.extractAndCreateTask("event sport /from 18-09-2023 1800 /to 20-08-2024 1930", Event.DATE_TIME_REGEX_1, 3 ));
    }

    /**
     * Tests if the method fails to extract and create a Deadline task
     * from an improperly formatted user input string.
     */
    @Test
    public void extractAndCreateTask_deadlineInputUnformat_fail() {
        assertEquals(false, Parser.extractAndCreateTask("deadline sport/ by 17:00", Deadline.DATE_TIME_REGEX_1, 2 ));
    }

    /**
     * Tests if the method fails to extract and create a Deadline task
     * from a user input string with invalid date format.
     */
    @Test
    public void extractAndCreateTask_deadlineInputInvalid_fail() {
        assertEquals(false, Parser.extractAndCreateTask("deadline sport /by 19-13-2025 1830", Deadline.DATE_TIME_REGEX_1, 2 ));
    }

    /**
     * Tests if the method fails to extract and create an Event task
     * from an improperly formatted user input string.
     */
    @Test
    public void extractAndCreateTask_eventInputUnformat_fail() {
        assertEquals(false, Parser.extractAndCreateTask("event sport/from 18-09-2023 1800 /to 20-13-2024 1930", Event.DATE_TIME_REGEX_1, 3 ));
    }

    /**
     * Tests if the method fails to extract and create an Event task
     * from a user input string with invalid date values.
     */
    @Test
    public void extractAndCreateTask_eventInputInvalid_fail() {
        assertEquals(false, Parser.extractAndCreateTask("event sport /from 18-09-2023 1800 /to 20-13-2024 1930", Event.DATE_TIME_REGEX_1, 3 ));
    }

    /**
     * Tests if the method correctly extracts a Todo task
     * from a properly formatted string saved in a file.
     */
    @Test
    public void extractTaskFromFile_todoInput_success() {
        assertEquals(true, Parser.extractTaskFromFile("[T][ ] read book"));
    }

    /**
     * Tests if the method correctly extracts a Deadline task
     * from a properly formatted string saved in a file.
     */
    @Test
    public void extractTaskFromFile_deadlineInput_success() {
        assertEquals(true, Parser.extractTaskFromFile("[D][X] bath (by: Sep 17 2023 06:00 PM)"));
    }

    /**
     * Tests if the method correctly extracts an Event task
     * from a properly formatted string saved in a file.
     */
    @Test
    public void extractTaskFromFile_eventInput_success() {
        assertEquals(true, Parser.extractTaskFromFile("[E][X] bath (from: Sep 17 2023 06:00 PM to: Oct 18 2025 11:59 AM)"));
    }

    /**
     * Tests if the method fails to extract a Todo task
     * from an improperly formatted string saved in a file.
     */
    @Test
    public void extractTaskFromFile_todoInputUnformat_fail() {
        assertEquals(false, Parser.extractTaskFromFile("[T][] read book"));
    }

    /**
     * Tests if the method fails to extract a Deadline task
     * from an improperly formatted string saved in a file.
     */
    @Test
    public void extractTaskFromFile_deadlineInputUnformat_fail() {
        assertEquals(false, Parser.extractTaskFromFile("[D][X] bath (by:Sep 17 2023 06:00 PM)"));
    }

    /**
     * Tests if the method fails to extract an Event task
     * from an improperly formatted string saved in a file.
     */
    @Test
    public void extractTaskFromFile_eventInputUnformat_fail() {
        assertEquals(false, Parser.extractTaskFromFile("[E][X] bath (from: Sep 17 2023 06:00PM to: Oct 18 2025 11:59 AM)"));
    }

}
