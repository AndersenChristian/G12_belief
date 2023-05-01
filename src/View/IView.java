package View;

import Controller.Strategy;
import Model.IKnowledgeBase;

import java.io.IOException;
import java.util.List;

public interface IView {
    void displayCommands();
    void displayWelcome();
    void displayExit();
    void displayKnowledgeBase(IKnowledgeBase data);
    void displayMessage(String message);
    Strategy changeStrategy() throws IOException;


    String getCommand() throws IOException;
    void errorMessage(Exception e);

}
