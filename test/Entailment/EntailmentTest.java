package Entailment;

import Controller.EntailmentControl.TrustShortestNewestFirst;
import Controller.Strategy;
import Model.Data;
import Model.IKnowledgeBase;
import Model.KnowledgeBase;
import Model.Operator;
import org.junit.Test;

import java.util.Timer;

public class EntailmentTest {
    TrustShortestNewestFirst ec = new TrustShortestNewestFirst(Strategy.TRUSTS_NEW);
    String and = Operator.AND.getOperator(), or = Operator.OR.getOperator(), not = Operator.NOT.getOperator();

    @Test
    public void ecTest(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + Operator.OR.getOperator() + "B",Operator.NOT.getOperator() + "A"});
        System.out.println("Before:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.println("\n after sorting:");
        ec.removeEntailments(kb);
        System.out.println("\n \nAfter;:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        System.out.println("\n");

        kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + Operator.OR.getOperator() + "B",Operator.NOT.getOperator() + "B",Operator.NOT.getOperator() + "A"});
        System.out.println("before:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        ec.removeEntailments(kb);
        System.out.println("\nafter:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void ecTest2(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{Operator.NOT.getOperator() + "A",Operator.NOT.getOperator() + "B","A" + Operator.OR.getOperator() + "B"});
        System.out.println("before:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        ec.removeEntailments(kb);
        System.out.println("\nafter:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void ecTest3(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{not + "A","A" + or + "B" + or + "C", not + "B" + or + "C", not + "C"});
        System.out.println("before:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
        ec.removeEntailments(kb);
        System.out.println("\nafter:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }

    @Test
    public void longClauseTest1(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + or + "B", not + "A" + or + "B", not + "B" + or + "A", not + "B" + or + not + "A", not + "C" + or + "B" + or + not + "A"});
        ec.removeEntailments(kb);
    }
    @Test
    public void longClauseTest2(){
        IKnowledgeBase kb = new KnowledgeBase();
        kb.addData(new String[]{"A" + or + "B", not + "A" + or + "B", not + "B" + or + "A", not + "B" + or + not + "A"});
        ec.removeEntailments(kb);
    }

    /*
    @Test
    public void ecTest4(){
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

        Timer timer = new Timer();
        InterruptTimerTask interruptTimerTask = new InterruptTimerTask(Thread.currentThread());
        try {
            ec.removeEntailments(kb);
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            timer.cancel();
        }
        System.out.println("\nafter:");
        for(Data d: kb.getAllData()){
            System.out.print(d.getClaus().replaceAll("OR", "|").replaceAll("AND", "&") + "\t");
        }
    }*/
}
