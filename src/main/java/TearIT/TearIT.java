import application.Ui;

/**
 * TearIT is a class that provides entry point to the user interaction simulator.
 */
public class TearIT {

    private static final String opening = """
            Hello! I'm TearIT.
            What can I do for you?
            
            -------------------------------------------------""";


    private static final String ending = """
            Bye. Hope to see you again soon! 
            
            -------------------------------------------------""";


    public static void main(String[] args) {
        TearIT.openingMessage();

        // Echo is done here with ending message included when user enter "bye"
        Ui.echo(TearIT.ending);
    }

    /**
     *   Prints opening message to first interact with the user
     */
    public static void openingMessage() {
        System.out.println("-------------------------------------------------");
        System.out.println(TearIT.opening);
    }

}
