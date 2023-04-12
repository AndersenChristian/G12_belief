package View;

import java.io.IOException;

public interface IView {
    void display();
    void displayCommands();
    void displayWelcome();
    void displayExit();


    String getCommand() throws IOException;
    void errorMessage(Exception e);

}
