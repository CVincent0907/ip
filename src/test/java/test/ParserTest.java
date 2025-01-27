package test;


import org.junit.jupiter.api.Test;
import task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    // Formatted todo input from file that user saved into
    @Test
    public void extractAndCreateTask_toDoInput_success() {
        assertEquals(true, ParserStub.extractAndCreateTask("[T][X] read book", Todo.REGEX_2, 1 ));
    }

}
