import Model.IKnowledgeBase;
import Model.KnowledgeBase;
import Model.Operator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.logging.*;

@RunWith(JUnit4.class)
public class KnowledgeBaseTest {
    IKnowledgeBase kb;
    private final Logger LOG = Logger.getLogger("test");

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
}
