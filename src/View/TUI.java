package View;

import Controller.Strategy;
import Model.IKnowledgeBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TUI implements IView{
    BufferedReader input;

    public TUI(){
        input = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void displayCommands() {
        System.out.println("Commands:");
        System.out.println("help - obviusly");
        System.out.println("exit - close the program");
        System.out.println("view - Display all entries in knowledge base");

        System.out.println("Naming syntax:");
        System.out.println("All variable must start with a uppercase letter and can be trailed by as lowercase letters as needed\n");

        System.out.println("Operator syntax:");
        System.out.println("&\tAnd");
        System.out.println("|\tOr");
        System.out.println("=>\tImplies");
        System.out.println("<=>\tBiimplication");
        System.out.println("~\tnegation");
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
    public void displayKnowledgeBase(IKnowledgeBase data) {
        System.out.println(data.toString());
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public Strategy changeStrategy() throws IOException {
        System.out.println("Select a strategy, but typing the number:");
        System.out.println("(0)\t-\tTRUST_NEW");
        System.out.println("(1)\t-\tTRUST_OLD");
        System.out.println("(2)\t-\tTRUST_LONG");
        System.out.println("(3)\t-\tTRUST_SHORT");

        String in = getCommand();
        if (in.length() == 1 && in.charAt(0) >= '0' && in.charAt(0) < '4') {
            Strategy s = Strategy.getStrategy(Character.getNumericValue(in.charAt(0)));
            System.out.println(s);
            return s;
        }
        System.out.println("error, wrong input, sticking with trust oldest");
        return Strategy.TRUST_OLD;
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
