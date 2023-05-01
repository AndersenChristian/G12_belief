package Entailment;

import Controller.EntailmentControl.EntailmentTrustOrder;
import Model.Data;
import Model.IKnowledgeBase;
import Model.KnowledgeBase;
import Model.Operator;
import org.junit.Test;

import java.util.Timer;

public class EntailmentTrustOrderTest {
    EntailmentTrustOrder ec = new EntailmentTrustOrder();
    String and = Operator.AND.getOperator(), or = Operator.OR.getOperator(), not = Operator.NOT.getOperator();
    @Test
    public void ecTest1(){
        ec.enableLOG(true);
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{
                "A" + or + "B" + or + not + "C" + or + "D",
                "A" + or + "B" + or + "C" + or + not + "D",
                not + "A",
                not + "B"});
        System.out.println("before:");

        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.print("\n\n");
        ec.removeEntailments(kb, kb);

        System.out.println("\nafter:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void ecTest2(){
        ec.enableLOG(true);
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{
                not + "A",
                not + "B",
                "A" + or + "B"});
        System.out.println("before:");

        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.print("\n\n");
        ec.removeEntailments(kb, kb);

        System.out.println("\nafter:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void ecTest3(){
        ec.enableLOG(true);
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{
                "A" + or + "B",
                not + "A",
                not + "B"});
        System.out.println("before:");

        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.print("\n\n");
        ec.removeEntailments(kb, kb);

        System.out.println("\nafter:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void ecTest4(){
        //ec.enableLOG(true);
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{
                "A" + or + "B",
                not + "A",
                not + "Q" + or + "H",
                not +"H" + or + "Q",
                not + "A"+ or+"B",
                not + "B"+or+"A"
        });
        System.out.println("before:");

        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.print("\n\n");
        ec.removeEntailments(kb, kb);

        System.out.println("\nafter:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }
}
