package tearit;

import application.Ui;

/**
 * TearIT.TearIT is a class that provides entry point to the user interaction simulator.
 */
public class TearIT {
    private static final String ending = "Bye. Hope to see you again soon! ";

    public String getResponse(String input) {
        // Assume Ui.echo() will got give null response despite any input by users
        // Disabled now to remove side effects during run command
        assert Ui.echo(TearIT.ending, input) != "" : "Wrong assumption ! The return value is an empty strings";
        return Ui.echo(TearIT.ending, input);
    }

}
