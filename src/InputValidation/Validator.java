package InputValidation;


public class Validator implements IValidation{
    //String name = Operator.NOT.getOperator() + "?[A-Z]";
    //String operator = "([" + Operator.OR.getOperator() + "]|[" + Operator.AND.getOperator() + "]|" + Operator.IFF.getOperator() + "|" + Operator.IMP.getOperator() + ")";
    //Pattern regex = Pattern.compile(name + "(" + operator + name + ")*" + "|" + name + "(" + regex + ")*");

    @Override
    public boolean validateString(String s) {
        boolean valid = false;

        Tokenizer tokenizer = new Tokenizer();
        tokenizer.add("\\(", 1); // open bracket
        tokenizer.add("\\)", 2); // close bracket
        tokenizer.add("\\|", 3); // or
        tokenizer.add("\\&", 4); // and
        tokenizer.add("\\~", 5); // not
        tokenizer.add("\\<=>", 6); // iff
        tokenizer.add("\\=>", 7); // imp
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", 8); // variable
        tokenizer.add("\\s", 9); // Whitespace

        try{
            tokenizer.tokenize(s);

            for(Tokenizer.Token tok : tokenizer.getTokens()){
                System.out.println("" + tok.token + " " + tok.sequence);

                if (tok.token == 1) {

                } else if (tok.token == 2) {

                } else if (tok.token == 3) {

                } else if (tok.token == 4) {

                } else if (tok.token == 5) {

                } else if (tok.token == 6) {

                } else if (tok.token == 7) {

                } else if (tok.token == 8) {

                }

            }
            valid = true;
        }
        catch (ParserException e){
            System.out.println(e.getMessage());
        }

        return valid;
    }
}



