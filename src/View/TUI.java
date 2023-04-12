package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TUI implements IView{
    BufferedReader input;

    public TUI(){
        input = new BufferedReader(new InputStreamReader(System.in));
    }
    @Override
    public void display() {

    }

    @Override
    public void displayCommands() {
        System.out.println("Commands:");
        System.out.println("help - obviusly");
        System.out.println("exit - close the program");

        System.out.println("Naming syntax:");
        System.out.println("All variable must start with a uppercase letter and can be trailed by as lowercase letters as needed\n");

        System.out.println("Operator syntax:");
        System.out.println("a\tAnd");
        System.out.println("o\tOr");
        System.out.println("i\tImplies");
        System.out.println("b\tBiimplication");
        System.out.println("n\tnegation");
        System.out.println("()\t must all be closed");
    }

    @Override
    public void displayWelcome() {
        System.out.println("Welcome to the belief handler");
        System.out.println("Use the command \"help\" to get some help!");
    }

    @Override
    public void displayExit() {
        System.out.println("Shutting down program");
        System.out.println("Thanks for the visit - Buy");
        try{
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommand() throws IOException {
        return this.input.readLine();
    }

    @Override
    public void errorMessage(Exception e) {
        System.out.println(e.getMessage());
    }
}
