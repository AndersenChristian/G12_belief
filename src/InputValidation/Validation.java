package InputValidation;

import java.util.regex.*;

public class Validation {

    String variable = "[A-Z][a-zA-Z0-9]*]";
    String parenthesis = "[()]";
    String operator = "[&|~]"+"[=>]"+"[<=>]";
    Pattern expression = Pattern.compile(variable + " " + operator );
    Pattern strREGEX = Pattern.compile(parenthesis + expression);


    String test = "((Abc o Bac) a (Abc imp (Abc o Bac)) a ((Bac a Abc) imp Abd))";

    Matcher matchString = strREGEX.matcher(test);
}
