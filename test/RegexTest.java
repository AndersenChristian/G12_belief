import InputValidation.IValidation;
import InputValidation.Regex;
import org.junit.Assert;
import org.junit.Test;

public class RegexTest {
    IValidation validation = new Regex();

    @Test
    public void regexBasicTest(){
        String[] test = {"A","B","A|B","A&B","A=>B","A<=>B","~A","A&~B"};
        for (String s: test) {
            Assert.assertTrue(validation.validateString(s));
        }
    }
}
