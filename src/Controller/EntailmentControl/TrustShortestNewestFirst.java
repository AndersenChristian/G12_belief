package Controller.EntailmentControl;

import Controller.Strategy;
import Model.Data;
import Model.IKnowledgeBase;
import Model.Operator;

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

        // Bruteforce for large clause contradictions
        for (int i=0; i<remainClaus.size(); i++){

            // Check first clause
            String checkClause = remainClaus.get(i).getClaus();

            // Variables to check for
            String charCheckArray[] = checkClause.split("|");

            // Clauses to check for contradictions
            ArrayList<Data> clauseCheckList = new ArrayList<Data>();

            // Strip of negation
            for (int j=0; j< charCheckArray.length; j++){
                charCheckArray[j].replace(Operator.NOT.getOperator(),"");
            }

            // Check for each clause in knowledge base
            for (int j=0; j<remainClaus.size(); j++){

                // Check for all variables
                int variablesMatching=0;
                for (int q=0; q<charCheckArray.length; q++){
                    // If they contain
                    if (remainClaus.get(j).getClaus().contains(charCheckArray[q])){
                        variablesMatching++;
                    }
                }

                // Add if match
                if (variablesMatching == charCheckArray.length){
                    clauseCheckList.add(remainClaus.get(i));
                }
            }

            // Generate all combinations


            // If Clause check list contains all combinations

        }

        if (remainClaus.isEmpty()) return;
    }
}
