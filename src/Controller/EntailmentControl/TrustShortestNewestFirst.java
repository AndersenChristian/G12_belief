package Controller.EntailmentControl;

import Controller.Strategy;
import Model.Data;
import Model.IKnowledgeBase;
import Model.Operator;
import Model.TruthTable;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TrustShortestNewestFirst implements IEntailmentCheck {
    Strategy strategy;
    List<Data> remainClaus = new ArrayList<>();

    public TrustShortestNewestFirst(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void removeEntailments(IKnowledgeBase data) {

        // Copy the knowledge base
        Stream.of(data.getAllData()).forEach(d -> remainClaus.add(new Data(d)));
        
        //sorting
        Collections.reverse(remainClaus);
        remainClaus.sort(Comparator.comparing(Data::getSize));

        //for testing
        System.out.println("\n \nafter sorting:");
        remainClaus.forEach(o -> System.out.print(o.getClaus() + "\t"));
        System.out.println();

        // Add all short elements to a queue
        Queue<String> queue = new ArrayDeque<>();
        List<Data> toRemove = new ArrayList<>();
        for (Data d : remainClaus) {
            if (!d.getClaus().contains(Operator.OR.getOperator())) {
                char control = d.getClaus().length() == 1 ? d.getClaus().charAt(0) : d.getClaus().charAt(1);
                boolean success = true;
                for (String s : queue) {
                    if (s.contains(control + "")) {
                        success = false;
                        break;
                    }
                }
                if (success) {
                    queue.add(d.getClaus());
                    toRemove.add(d);
                }
            }
        }
        toRemove.forEach(d -> remainClaus.remove(d));

        // While que is not empty, Pop
        while (queue.size() > 0) {
            if (remainClaus.isEmpty())
                break;

            String queuepop = queue.remove();
            boolean isNegated = queuepop.length() > 1;

            // If negated, check for symbol instead of negated symbol
            String checkedSymbol = isNegated ? queuepop.substring(1) : queuepop;

            //Begin removing
            toRemove.clear();
            remainClaus.stream()
                    .filter(d -> d.getClaus().contains(checkedSymbol))
                    .forEach(d -> {
                        //contains the exact same
                        if ((isNegated && d.getClaus().contains(Operator.NOT.getOperator() + checkedSymbol)) || (!isNegated && !d.getClaus().contains(Operator.NOT.getOperator() + checkedSymbol)))
                            // Clause is proven
                            data.removeDataAtIndex(data.getIndex(d.getClaus()));
                        else {
                            // Clauses are trimmed of symbol
                            if (isNegated) {
                                d.setClaus(d.getClaus().replace(checkedSymbol + Operator.OR.getOperator(), ""));
                                d.setClaus(d.getClaus().replace(Operator.OR.getOperator() + checkedSymbol, ""));
                                d.setClaus(d.getClaus().replace(checkedSymbol, ""));
                            } else {
                                d.setClaus(d.getClaus().replace(Operator.NOT.getOperator() + checkedSymbol + Operator.OR.getOperator(), ""));
                                d.setClaus(d.getClaus().replace(Operator.OR.getOperator() + Operator.NOT.getOperator() + checkedSymbol, ""));
                                d.setClaus(d.getClaus().replace(Operator.NOT.getOperator() + checkedSymbol, ""));
                            }
                            // If it is a contradiction, remove from belief base
                            if (d.getClaus().isEmpty()) {
                                data.removeData(d.getDataInKnowledgeBaseReference());
                                toRemove.add(d);
                            } else if (d.getClaus().length() <= 2) {
                                // Add new symbols to queue, for further reductions
                                char control = d.getClaus().length() == 1 ?
                                        d.getClaus().charAt(0) :
                                        d.getClaus().charAt(1);
                                if (queue.stream().noneMatch(da -> da.contains(control + ""))) {
                                    queue.add(d.getClaus());
                                    toRemove.add(d);
                                }
                            }
                        }
                    });
            toRemove.forEach(d -> remainClaus.remove(d));
        }

        // TODO: Remove this test case
        //remainClaus.add();

        // Find all variables
        String omegaString = "";
        ArrayList<String> clauseVariables = new ArrayList<String>();
        for (int i=0; i < remainClaus.size(); i++){
            // Add string to
            omegaString += remainClaus.get(i).getClaus();
        }

        // Remove all OR's and Not's
        omegaString = omegaString.replace(Operator.OR.getOperator(), "");
        omegaString = omegaString.replace(Operator.NOT.getOperator(), "");

        // Add variables to list, remove copies of variable in string.
        // Outputs list of unique variables
        while(omegaString.length()>0){
            clauseVariables.add(String.valueOf(omegaString.charAt(0)));
            omegaString = omegaString.replace(String.valueOf(omegaString.charAt(0)),"");
            System.out.println(omegaString);
        }

        System.out.println("All variables: "+clauseVariables);

        // Create truth table of variables
        TruthTable truthTable = new TruthTable(clauseVariables);
        truthTable.printTable();

        if (remainClaus.isEmpty()) return;
    }
}
