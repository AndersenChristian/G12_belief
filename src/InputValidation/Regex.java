package InputValidation;

import Model.Symbol;

import java.util.regex.Pattern;

public class Regex implements IValidation{
    String name = Symbol.NOT.getSYMBOL() + "?[A-Z]";
    String operator = "([" + Symbol.OR.getSYMBOL() + "]|[" + Symbol.AND.getSYMBOL() + "]|" + Symbol.IFF.getSYMBOL() + "|" + Symbol.IMP.getSYMBOL() + ")";

    Pattern regex = Pattern.compile(name + "(" + operator + name + ")*");

    @Override
    public boolean validateString(String s) {
        return regex.matcher(s).matches();
    }

    String test = "A&~B";
    public boolean testString(){
        System.out.println(regex.toString());
        System.out.println(test);
        return regex.matcher(test).matches();
    }
}
