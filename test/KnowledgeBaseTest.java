import Controller.Strategy;
import Model.Data;
import Model.IKnowledgeBase;
import Model.KnowledgeBase;
import Model.Operator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.logging.*;

@RunWith(JUnit4.class)
public class KnowledgeBaseTest {
    IKnowledgeBase kb;
    private final Logger LOG = Logger.getLogger("test");
    String or = Operator.OR.getOperator(), not = Operator.NOT.getOperator();

    public KnowledgeBaseTest() {
    }

    @Before
    public void setup(){
        LOG.info("running setup");
        kb = new KnowledgeBase();
    }


    @Test
    public void kbTest1(){
        kb.addData("B" + Operator.OR.getOperator() + "A");
        Assert.assertEquals("A" + Operator.OR.getOperator() + "B", kb.getDataAtIndex(0).getClaus());
    }

    @Test
    public void sortTest1(){
        kb.addData(new String[]{
                "A",
                "A" + or + "B",
                "A" + or + "B" + or + "C"
                }
        );
        IKnowledgeBase new_kb = kb.sort(Strategy.TRUST_LONG);
        Data[] data = new_kb.getAllData();

        Assert.assertEquals("A" + or + "B" + or + "C",data[0].toString());
        Assert.assertEquals("A" + or + "B",data[1].toString());
        Assert.assertEquals("A", data[2].toString());
    }

    @Test
    public void sortTest2(){
        kb.addData(new String[]{
                "A" + or + "B" + or + "C",
                "A" + or + "B",
                "A"
                }
        );
        IKnowledgeBase new_kb = kb.sort(Strategy.TRUST_SHORT);
        Data[] data = new_kb.getAllData();

        Assert.assertEquals("A" + or + "B" + or + "C",data[2].toString());
        Assert.assertEquals("A" + or + "B",data[1].toString());
        Assert.assertEquals("A", data[0].toString());
    }
}
