import Model.KnowledgeBase;
import antlr4.CNFConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class CNFTest {

    CNFConverter converter;
    private final Logger LOG = Logger.getLogger("test");

    @Before
    public void setup(){
        LOG.info("running setup");
        converter = new CNFConverter();
    }

    @Test
    public void implicationToCNFTest() {
        List<String> cnfs = converter.convertToCNF("P => Q");
        String result = cnfs.get(0);

        // Split & and remove parenthesis
        result = result.replaceAll("[()]","");

        Assert.assertEquals("~P | Q", result);
    }

    @Test
    public void bimplicationToCNFTest() {
        List<String> cnfs = converter.convertToCNF("P <=> Q");

        // Check if it splits the two statements
        Assert.assertEquals(2, cnfs.size());

        String left = cnfs.get(0).replaceAll("[()]","");
        Assert.assertEquals("~P | Q", left);

        String right = cnfs.get(1).replaceAll("[()]","");
        Assert.assertEquals("~Q | P", right);
    }

    @Test
    public void deMorganAndTest() {
        List<String> cnfs = converter.convertToCNF("~(Q & R)");

        String left = cnfs.get(0).replaceAll("[()]","");
        Assert.assertEquals("~Q | ~R", left);
    }

    @Test
    public void deMorganOrTest() {
        List<String> cnfs = converter.convertToCNF("~(Q | R)");

        String left = cnfs.get(0).replaceAll("[()]","");
        Assert.assertEquals("~Q", left);

        String right = cnfs.get(1).replaceAll("[()]","");
        Assert.assertEquals("~R", right);
    }

    @Test
    public void SplitAndsTest() {
        List<String> cnfs = converter.convertToCNF("P & (Q | R)");

        String left = cnfs.get(0).replaceAll("[()]","");
        Assert.assertEquals("P", left);

        String right = cnfs.get(1).replaceAll("[()]","");
        Assert.assertEquals("Q | R", right);
    }

    @Test
    public void lawOfDistributionAndTest() {
        List<String> cnfs = converter.convertToCNF("P & (Q | R)");

        // Check if it splits the two statements
        Assert.assertEquals(2, cnfs.size());

        String left = cnfs.get(0).replaceAll("[()]","");
        Assert.assertEquals("P", left);

        String right = cnfs.get(1).replaceAll("[()]","");
        Assert.assertEquals("Q | R", right);
    }

    @Test
    public void nestedCNFTest() {
        List<String> cnfs = converter.convertToCNF("(P & Q) => T");

        String left = cnfs.get(0).replaceAll("[()]","");
        Assert.assertEquals("~P | ~Q | T", left);
    }

}
