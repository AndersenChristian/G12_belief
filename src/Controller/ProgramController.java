package Controller;

import Controller.EntailmentControl.EntailmentTrustOrder;
import Controller.EntailmentControl.IEntailmentCheck;
import Model.Data;
import Model.KnowledgeBase;
import Model.IKnowledgeBase;
import View.IView;
import View.TUI;
import antlr4.CNFConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramController {
    private final IKnowledgeBase data = new KnowledgeBase();
    private final IView view = new TUI();
    private final CNFConverter cnfConverter = new CNFConverter();
    private final IEntailmentCheck entailCheck = new EntailmentTrustOrder();
    private Strategy strategy = Strategy.TRUST_OLD; //default

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
                case "Strategy" ->{
                    try {
                        strategy = this.view.changeStrategy();
                    } catch (Exception e){
                        this.view.errorMessage(e);
                    }
                }
                default -> {
                    try {
                        List<String> cnf = cnfConverter.convertToCNF(input);
                        // Split & and remove parenthesis
                        AtomicInteger count = new AtomicInteger();
                        cnf.forEach(s -> {
                            s = s.replaceAll("[()]","");
                            data.addData(s);
                            count.getAndIncrement();
                        });
                        view.displayMessage("Successfully added " + count + ", new beliefs");

                        view.displayMessage("Checking for contradiction in belief base");
                        entailCheck.removeEntailments(data.sort(strategy), data);
                        Data[] removed = entailCheck.whatWasRemoved();
                        if (removed.length == 0) view.displayMessage("Nothing was removed");
                        else{
                            view.displayMessage("The following was removed:");
                            Arrays.stream(removed).forEach(r ->
                                    view.displayMessage(r.toString())
                            );
                        }

                    } catch (IllegalArgumentException e) {
                        this.view.errorMessage(e);
                    }
                }
            }
        }
        this.view.displayExit();
    }
}
