package InputValidation;

import java.util.regex.*;

public class Validation {

    Pattern strREGEX = Pattern.compile("[A-Z]");
    String test = "((Abc o Bac) a (Abc imp (Abc o Bac)) a ((Bac a Abc) imp Abd))";

    boolean testString= Pattern.matches(strREGEX, test);
}
