package Controller;

import Controller.CNF.CNF;
import InputValidation.IValidation;
import InputValidation.Validator;
import Model.KnowledgeBase;
import Model.IKnowledgeBase;
import View.IView;
import View.TUI;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class ProgramController {
    private final IValidation validator = new Validator(); //temp way to do it until actual validation class is made
    private final IKnowledgeBase data = new KnowledgeBase();
    private final IView view = new TUI();
    private final CNFController CNFController = new CNFController();
    public void primary(String[] args){
        if(args != null){
            this.data.addData(args);
        }

        this.view.displayWelcome();

        String input;
        mainloop: while(true) {
            try {
                input = this.view.getCommand();
            } catch (IOException e) {
                this.view.errorMessage(e);
                continue;
            }
            switch (input) {
                case "help" -> {
                    this.view.displayCommands();
                }
                case "exit" -> {
                    break mainloop;
                }
                case "view" -> {
                    this.view.displayKnowledgeBase(data);
                }
                case "test" -> {
                    //System.out.println(((Regex) validator).testString());
                }
                default -> {
                    if(Pattern.matches("rm[A-Z][a-z]*", input)){

                    }
                    else if(validator.validateString(input)){

                        List<CNF> cnf = CNFController.convertToCNF(List.of(input.split("\n")));
                        cnf.forEach(expr -> data.addData(expr.toInputFormat()));
                        System.out.println("Successfully added");
                    } else System.out.println("Regex control failed, please see the help section.");
                }
            }
        }
        this.view.displayExit();
    }
}
