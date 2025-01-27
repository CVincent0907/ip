package test;


import org.junit.jupiter.api.Test;
import task.Deadline;
import task.Event;
import task.Todo;
import application.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    // TestCase Naming Convention done by PascalCase and _ (ie: public void methodName_inputType_expectedOutput)
    // Formatted todo input from user
    @Test
    public void extractAndCreateTask_toDoInput_success() {
        assertEquals(true, Parser.extractAndCreateTask("todo Read Book", Todo.REGEX_1, 1 ));
    }

    // Formatted deadline input from user
    @Test
    public void extractAndCreateTask_deadlineInput_success() {
        assertEquals(true, Parser.extractAndCreateTask("deadline task /by 18-09-2023 1800", Deadline.DATE_TIME_REGEX_1, 2 ));
    }

    // Formatted event input from user
    @Test
    public void extractAndCreateTask_eventInput_success() {
        assertEquals(true, Parser.extractAndCreateTask("event sport /from 18-09-2023 1800 /to 20-08-2024 1930", Event.DATE_TIME_REGEX_1, 3 ));
    }

    // Unformat deadline input from user
    @Test
    public void extractAndCreateTask_deadlineInputUnformat_fail() {
        assertEquals(false, Parser.extractAndCreateTask("deadline sport/ by 17:00", Deadline.DATE_TIME_REGEX_1, 2 ));
    }

    // Invalid event date input from user
    @Test
    public void extractAndCreateTask_deadlineInputInvalid_fail() {
        assertEquals(false, Parser.extractAndCreateTask("deadline sport /by 19-13-2025 1830", Deadline.DATE_TIME_REGEX_1, 2 ));
    }

    // Unformat event input from user
    @Test
    public void extractAndCreateTask_eventInputUnformat_fail() {
        assertEquals(false, Parser.extractAndCreateTask("event sport/from 18-09-2023 1800 /to 20-13-2024 1930", Event.DATE_TIME_REGEX_1, 3 ));
    }

    // Invalid event date input from user
    @Test
    public void extractAndCreateTask_eventInputInvalid_fail() {
        assertEquals(false, Parser.extractAndCreateTask("event sport /from 18-09-2023 1800 /to 20-13-2024 1930", Event.DATE_TIME_REGEX_1, 3 ));
    }

    // Formatted todo input from file that user saved into
    @Test
    public void extractTaskFromFile_todoInput_success() {
        assertEquals(true, Parser.extractTaskFromFile("[T][ ] read book"));
    }

    // Formatted deadline input from file that user saved into
    @Test
    public void extractTaskFromFile_deadlineInput_success() {
        assertEquals(true, Parser.extractTaskFromFile("[D][X] bath (by: Sep 17 2023 06:00 PM)"));
    }

    // Formatted event input from file that user saved into
    @Test
    public void extractTaskFromFile_eventInput_success() {
        assertEquals(true, Parser.extractTaskFromFile("[E][X] bath (from: Sep 17 2023 06:00 PM to: Oct 18 2025 11:59 AM)"));
    }

    // Unformatted todo input from file that user saved into
    @Test
    public void extractTaskFromFile_todoInputUnformat_fail() {
        assertEquals(false, Parser.extractTaskFromFile("[T][] read book"));
    }

    // Unformatted deadline input from file that user saved into
    @Test
    public void extractTaskFromFile_deadlineInputUnformat_fail() {
        assertEquals(false, Parser.extractTaskFromFile("[D][X] bath (by:Sep 17 2023 06:00 PM)"));
    }

    // Unformatted event input from file that user saved into
    @Test
    public void extractTaskFromFile_eventInputUnformat_fail() {
        assertEquals(false, Parser.extractTaskFromFile("[E][X] bath (from: Sep 17 2023 06:00PM to: Oct 18 2025 11:59 AM)"));
    }

}
