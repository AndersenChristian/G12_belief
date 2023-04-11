import InputValidation.IValidation;
import InputValidation.Validator1;
import Model.Expressions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        Main m = new Main();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        IValidation validator = new Validator1();
        Expressions data = new Expressions(args);

        m.welcomeMessage();
        String input;
        mainloop: while(true) {
            try {
                input = in.readLine();
            } catch (IOException e) {
                System.out.println("Error catched from input stream, try again");
                continue;
            }
            switch (input) {
                case "help" -> {
                    m.commands();
                }
                case "exit" -> {
                    break mainloop;
                }
                default -> {
                    if(validator.validateString(input)){
                        data.addData(input);
                        System.out.println("Successfully added");
                    } else System.out.println("Regex control failed, please see the help section.");
                }
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void welcomeMessage(){
        System.out.println("Welcome to the belief handler");
        System.out.println("Use the command \"help\" to get some help!");
    }

    public void commands(){
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
}