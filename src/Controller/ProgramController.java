package Controller;

import Controller.CNF.CNF;
import InputValidation.IValidation;
import InputValidation.Regex;
import Model.KnowledgeBase;
import Model.IKnowledgeBase;
import View.IView;
import View.TUI;
import antlr4.CNFConverter;
import antlr4.Environment;
import antlr4.Expr;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class ProgramController {
    private final IKnowledgeBase data = new KnowledgeBase();
    private final IView view = new TUI();
    private final CNFConverter cnfConverter = new CNFConverter();

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
                case "validate" -> {

                }
                default -> {
                    try {
                        List<String> cnf = cnfConverter.convertToCNF(input);
                        // Split & and remove parenthesis
                        cnf.forEach(s -> {
                            s = s.replaceAll("[()]","");
                            data.addData(s);
                        });
                        //data.addData(cnf.toArray(new String[0]));
                        System.out.println("Successfully added");
                    } catch (IllegalArgumentException e) {
                        this.view.errorMessage(e);
                    }
                }
            }
        }
        this.view.displayExit();
    }
}
