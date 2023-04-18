import Controller.EntailmentCheck;
import Controller.Strategy;
import Model.IKnowledgeBase;
import Model.KnowledgeBase;
import Model.Symbol;
import org.junit.Test;

public class EntailmentTest {
    EntailmentCheck ec = new EntailmentCheck(Strategy.TRUSTS_NEW);

    @Test
    public void ecTest(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + Symbol.OR.getSYMBOL() + "B",Symbol.NOT.getSYMBOL() + "A"});
        ec.removeEntailments(kb);
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.println();

        kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + Symbol.OR.getSYMBOL() + "B",Symbol.NOT.getSYMBOL() + "B",Symbol.NOT.getSYMBOL() + "A"});
        ec.removeEntailments(kb);
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }
}
