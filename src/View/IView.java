package View;

import java.io.IOException;
import java.util.List;

public interface IView {
    void displayBeliefs(List<String> list);
    void displayCommands();
    void displayWelcome();
    void displayExit();


    String getCommand() throws IOException;
    void errorMessage(Exception e);

}
