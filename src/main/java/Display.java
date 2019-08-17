import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Display {
    private Scanner sc;

    public Display() {
        this.sc = new Scanner(System.in);
    }

    private static void printGreeting() {
        String greeting1 = "Hello! I'm Duke";
        String greeting2 = "What can I do for you?";
        ArrayList<String> printxs = new ArrayList<>();
        printxs.add(greeting1);
        printxs.add(greeting2);
        Display.printSection(printxs);
    }

    private static void printExitMessage() {
        String farewell = "Bye. Hope to see you "
            + "again soon!";

        Display.printSection(farewell);
    }

    private static void printLineBreak() {
        String line = "     "
            + "________________________________"
            + "____________________________";
        System.out.println(line);
    }

    private static void printHeader() {
        System.out.println();
        Display.printLineBreak();
    }

    private static void printFooter() {
        Display.printLineBreak();
        System.out.println();
        System.out.println();
    }

    private static boolean isEndCommand(String cmd) {
        return cmd.toUpperCase().equals("BYE");
    }

    private static void printList(List<String> printJobs) {
        /* TODO:  Delimit by \n */

        for (String printJob : printJobs) {
            System.out.print("      ");
            System.out.print(printJob);
            System.out.println();
        }
    }

    private static void printSection(List<String> printJobs){
            Display.printHeader();
            Display.printList(printJobs);
            Display.printFooter();
    }

    private static void printSection(String job) {
        ArrayList<String> printxs = new ArrayList<>();
        printxs.add(job);
        Display.printSection(printxs);
    }

    public void instance() {
        Display.printGreeting();
        String command = this.sc.nextLine();

        while (! isEndCommand(command)) {
            String output = "echo: " + command;
            ArrayList<String> printxs = new ArrayList<>();
            printxs.add(output);
            Display.printSection(printxs);


            command = this.sc.nextLine();
        }
        Display.printExitMessage();
    }
}
