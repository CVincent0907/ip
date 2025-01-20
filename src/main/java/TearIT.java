import java.awt.*;

public class TearIT {

    private static final String opening = """
            Hello! I'm TearIT.
            What can I do for you?
            
            -------------------------------------------------""";


    private static final String ending = """
            Bye. Hope to see you again soon!
           
            -------------------------------------------------""";


    public static void main(String[] args) {
        System.out.println("-------------------------------------------------");
        System.out.println(TearIT.opening);
        System.out.println(TearIT.ending);
    }

}
