package Controller;

import InputValidation.IValidation;
import Model.KnowledgeBase;
import Model.IKnowledgeBase;
import View.IView;
import View.TUI;

import java.io.IOException;

public class ProgramController {
    private final IValidation validator = s -> true; //temp way to do it until actual validation class is made
    private final IKnowledgeBase data = new KnowledgeBase();
    private final IView view = new TUI();
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
                default -> {
                    if(validator.validateString(input)){
                        data.addData(input);
                        System.out.println("Successfully added");
                    } else System.out.println("Regex control failed, please see the help section.");
                }
            }
        }
        this.view.displayExit();
    }
}
