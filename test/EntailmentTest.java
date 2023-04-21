import Controller.EntailmentCheck;
import Controller.Strategy;
import Model.IKnowledgeBase;
import Model.KnowledgeBase;
import Model.Operator;
import org.junit.Test;

public class EntailmentTest {
    EntailmentCheck ec = new EntailmentCheck(Strategy.TRUSTS_NEW);
    String and = Operator.AND.getOperator(), or = Operator.OR.getOperator(), not = Operator.NOT.getOperator();

    @Test
    public void ecTest(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + Operator.OR.getOperator() + "B",Operator.NOT.getOperator() + "A"});
        System.out.println("Before:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        ec.removeEntailments(kb);
        System.out.println("\nAfter;:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.println("\n");

        kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + Operator.OR.getOperator() + "B",Operator.NOT.getOperator() + "B",Operator.NOT.getOperator() + "A"});
        System.out.println("before:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        ec.removeEntailments(kb);
        System.out.println("\nafter:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void ecTest2(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{Operator.NOT.getOperator() + "A",Operator.NOT.getOperator() + "B","A" + Operator.OR.getOperator() + "B"});
        System.out.println("before:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        ec.removeEntailments(kb);
        System.out.println("\nafter:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void ecTest3(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{not + "A","A" + or + "B" + or + "C", not + "B" + or + "C", not + "C"});
        System.out.println("before:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        ec.removeEntailments(kb);
        System.out.println("\nafter:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void ecTest4(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{
                "A" + or + "B" + or + not + "C" + or + "D",
                "A" + or + "B" + or + "C" + or + not + "D",
                not + "A",
                not + "B"});
        System.out.println("before:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        ec.removeEntailments(kb);
        System.out.println("\nafter:");
        for(String s: kb.getAllData()){
            System.out.print(s.replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }
}
