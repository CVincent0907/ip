package TearIT;

import application.Ui;

/**
 * TearIT.TearIT is a class that provides entry point to the user interaction simulator.
 */
public class TearIT {
    private static final String ending = "Bye. Hope to see you again soon! ";

    public String getResponse(String input) {
        gitreturn Ui.echo(TearIT.ending, input);
    }

}
