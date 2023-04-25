package Controller;

import Controller.CNF.CNF;
import InputValidation.IValidation;
import InputValidation.Regex;
import Model.KnowledgeBase;
import Model.IKnowledgeBase;
import View.IView;
import View.TUI;
import antlr4.CNFConverter;
import antlr4.Expr;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class ProgramController {
    private final IValidation validator = new Regex(); //temp way to do it until actual validation class is made
    private final IKnowledgeBase data = new KnowledgeBase();
    private final IView view = new TUI();
    private final CNFConverter CNFConverter = new CNFConverter();
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
                default -> {
                    List<String> terms = List.of(input.split("&"));
                    terms.stream()
                            .map(t -> new CNFConverter().convertToCNF(t))
                            .forEach(e -> data.addData(e.toInputFormat()));

                    System.out.println("Successfully added");
                }
            }
        }
        this.view.displayExit();
    }
}
