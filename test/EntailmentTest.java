import Controller.EntailmentCheck;
import Controller.Strategy;
import Model.IKnowledgeBase;
import Model.KnowledgeBase;
import Model.Operator;
import org.junit.Test;

public class EntailmentTest {
    EntailmentCheck ec = new EntailmentCheck(Strategy.TRUSTS_NEW);

    @Test
    public void ecTest(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + Operator.OR.getOperator() + "B",Operator.NOT.getOperator() + "A"});
        ec.removeEntailments(kb);
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.println();

        kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + Operator.OR.getOperator() + "B",Operator.NOT.getOperator() + "B",Operator.NOT.getOperator() + "A"});
        ec.removeEntailments(kb);
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }
}
