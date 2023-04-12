import Controller.ProgramController;
import InputValidation.IValidation;
import InputValidation.Validator1;
import Model.Expressions;
import View.IView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        new ProgramController().primary(args);
    }
}