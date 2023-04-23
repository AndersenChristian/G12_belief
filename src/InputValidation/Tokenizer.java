package InputValidation;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class that defines internal class TokenInfo, which holds
 * information about the individual token
 */
public class Tokenizer {
    private class TokenInfo {
        public Pattern regex;
        public int token;

        public TokenInfo(Pattern regex, int token){
            super();
            this.token=token;
            this.regex=regex;
        }
    }

    private LinkedList<TokenInfo> tokenInfos;

    public Tokenizer() {
        tokenInfos = new LinkedList<TokenInfo>();
        tokens = new LinkedList<Token>();
    }

    public void add(String regex, int token){
        tokenInfos.add(new TokenInfo(Pattern.compile("^("+regex+")"), token));
    }

    public class Token{
        public int token;
        public String sequence;

        public Token(int token, String sequence){
            super();
            this.token=token;
            this.sequence=sequence;
        }
    }

    private LinkedList<Token> tokens;

    /**
     * Method to tokenize a string, into tokens in a linked-list
     * @param str The string that should be tokenized
     * @return Places the tokens into the linked-list called tokens
     */
    public void tokenize(String str){
        String s = new String(str);
        tokens.clear();

        while (!s.equals("")){
            boolean match = false;

            for(TokenInfo info : tokenInfos){
                Matcher m = info.regex.matcher(s);

                if(m.find()){
                    match = true;
                    String tok=m.group().trim();
                    tokens.add(new Token(info.token, tok));

                    s = m.replaceFirst("");
                    break;
                }
            }

            if(!match) throw new ParserException(
                    "Character input not allowed for in input: "+s);
        }
    }

    public LinkedList<Token> getTokens(){return tokens;}

}

