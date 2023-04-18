package InputValidation;

import Model.Operator;

import java.util.regex.Pattern;

public class Regex implements IValidation{

    String name = Operator.NOT.getOperator() + "?[A-Z]";
    String operator = "([" + Operator.OR.getOperator() + "]|[" + Operator.AND.getOperator() + "]|" + Operator.IFF.getOperator() + "|" + Operator.IMP.getOperator() + ")";

    Pattern regex = Pattern.compile( name + "\s" + "(" + operator + name + ")*");

    public boolean validparenthesis(String s) {
        int parenthesiscount = 0;
        String s1 = s;

        for (char i:s1.toCharArray()) {
            
        }

        if (parenthesiscount == 0){
            return true;
        } else
            return false;
    }

    @Override
    public boolean validateString(String s) {
        return regex.matcher(s).matches();
    }
}
