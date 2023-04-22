package Controller.EntailmentControl;

import Controller.Strategy;
import Model.IKnowledgeBase;
import Model.Operator;

import java.util.*;
import java.util.stream.IntStream;

public class TrustShortestNewestFirst implements IEntailmentCheck {
    Strategy strategy;
    List<Data> remainClaus = new ArrayList<>();

    public TrustShortestNewestFirst(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void removeEntailments(IKnowledgeBase data) {

        // Copy the knowledge base
        IntStream.range(0, data.getSize()) //TODO: check index
                .forEach(i -> remainClaus.add(new Data(i, data.getDataAtIndex(i))));
        Collections.reverse(remainClaus);

        // Add all short elements to a queue
        Queue<String> queue = new ArrayDeque<>();
        List<Data> toRemove = new ArrayList<>();
        for (Data d : remainClaus) {
            if (!d.claus.contains(Operator.OR.getOperator())) {
                char control = d.claus.length() == 1 ? d.claus.charAt(0) : d.claus.charAt(1);
                boolean success = true;
                for (String s : queue) {
                    if (s.contains(control + "")) {
                        success = false;
                        break;
                    }
                }
                if (success) {
                    queue.add(d.claus);
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

            //prøv evt. det her kode
            toRemove.clear();
            remainClaus.stream()
                    .filter(d -> d.claus.contains(checkedSymbol))
                    .forEach(d -> {
                        //contains the exact same
                        if ((isNegated && d.claus.contains(Operator.NOT.getOperator() + checkedSymbol)) || (!isNegated && !d.claus.contains(Operator.NOT.getOperator() + checkedSymbol)))
                            // Clause is proven
                            data.removeDataAtIndex(data.getIndex(d.claus));
                        else {
                            // Clauses are trimmed of symbol
                            if (isNegated) {
                                d.claus = d.claus.replace(checkedSymbol + Operator.OR.getOperator(), "");
                                d.claus = d.claus.replace(Operator.OR.getOperator() + checkedSymbol, "");
                                d.claus = d.claus.replace(checkedSymbol, "");
                            } else {
                                d.claus = d.claus.replace(Operator.NOT.getOperator() + checkedSymbol + Operator.OR.getOperator(), "");
                                d.claus = d.claus.replace(Operator.OR.getOperator() + Operator.NOT.getOperator() + checkedSymbol, "");
                                d.claus = d.claus.replace(Operator.NOT.getOperator() + checkedSymbol, "");
                            }
                            // If it is a contradiction, remove from belief base
                            if (d.claus.isEmpty()) {
                                data.removeDataAtIndex(d.index);
                                toRemove.add(d);
                            } else if (d.claus.length() <= 2) {
                                // Add new symbols to queue, for further reductions
                                char control = d.claus.length() == 1 ?
                                        d.claus.charAt(0) :
                                        d.claus.charAt(1);
                                if (queue.stream().noneMatch(da -> da.contains(control + ""))) {
                                    queue.add(d.claus);
                                    toRemove.add(d);
                                }
                            }
                        }
                    });
            toRemove.forEach(d -> remainClaus.remove(d));
        }

        if (remainClaus.isEmpty()) return;

        //Only things left, are those not as easy to remove.
        toRemove.clear();
        for( int i = remainClaus.size()-1; i >= 0; i--){
            String normal = remainClaus.get(i).claus;
            StringBuilder sb = new StringBuilder();
            int last = 0;
            for(int k = 0; k < normal.length(); k++){
                if(normal.charAt(k) == Operator.OR.getOperator().charAt(0)){
                    if(k-last == 1)
                        sb.append(Operator.NOT.getOperator()).append(normal.charAt(i));
                    else
                        sb.append(normal.charAt(k));
                    last = k;
                }
            }
            String reversed = sb.toString();

            for(int j = i; j >= 0; j--){
                String currentClaus = remainClaus.get(j).claus;
                if(currentClaus.equals(normal))
                    toRemove.add(remainClaus.get(j));
                else if (currentClaus.contains(reversed)) {
                    currentClaus = currentClaus.replace(reversed, "");
                    if (currentClaus.length() == 0) {
                        data.removeDataAtIndex(remainClaus.get(j).index);
                        toRemove.add(remainClaus.get(j));
                    }
                }
            }
        }
    }

    private class Data {
        public int index;
        public String claus;

        public Data(int index, String claus) {
            this.index = index;
            this.claus = claus;
        }

        public String getClaus() {
            return this.claus;
        }
    }
}
