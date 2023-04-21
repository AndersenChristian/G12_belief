package Controller;

import Model.IKnowledgeBase;
import Model.Operator;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EntailmentCheck {
    Strategy strategy;
    List<Data> remainClaus = new ArrayList<>();
    public EntailmentCheck(Strategy strategy){
        this.strategy = strategy;
    }

    public void removeEntailments(IKnowledgeBase data){

        // Copy the knowledge base
        IntStream.range(0,data.getSize()) //TODO: check index
                .forEach(i -> remainClaus.add(new Data(i,data.getDataAtIndex(i))));

        // Add all short elements to a queue
        Queue<String> queue = new ArrayDeque<>();
        List<Data> toRemove = new ArrayList<>();
        for(Data d : remainClaus){
            if(!d.claus.contains(Operator.OR.getOperator())){
                char control = d.claus.length() == 1 ? d.claus.charAt(0) : d.claus.charAt(1);
                boolean success = true;
                for(String s : queue){
                    if(s.contains(control + "")){
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
            while(queue.size() > 0){
                if (remainClaus.isEmpty())
                    break;

                String queuepop = queue.remove();
                Boolean isNegated = queuepop.length() > 1;

                // If negated, check for symbol instead of negated symbol
                String checkedSymbol = isNegated ? queuepop.substring(1) : queuepop;

            /*for(Data d : remainClaus.stream().filter(d -> d.claus.contains(checkedSymbol)).toList()){
                //contains the exact same
                if((isNegated && d.claus.contains(Operator.NOT.getOperator() + checkedSymbol)) || (!isNegated && !d.claus.contains(Operator.NOT.getOperator() + checkedSymbol)))
                    // Clause is proven
                    data.removeDataAtIndex(data.getIndex(d.claus));
                else{
                    // Clauses are trimmed of symbol
                    if(isNegated) {
                        d.claus = d.claus.replace(checkedSymbol + Operator.OR.getOperator(), "");
                    }
                    else {
                        d.claus = d.claus.replace(Operator.NOT.getOperator() + checkedSymbol + Operator.OR.getOperator(), "");
                    }
                    // If it is a contradiction, remove from belief base
                    if(d.claus.isEmpty()){
                        data.removeDataAtIndex(d.index);
                        remainClaus.remove(d);
                    } else if(d.claus.length() <= 2){
                        // Add new symbols to queue, for further reductions
                        char control = d.claus.length() == 1 ?
                                d.claus.charAt(0) :
                                d.claus.charAt(1);
                        if(!queue.stream().anyMatch(da -> da.contains(control + ""))){
                            queue.add(d.claus);
                            remainClaus.remove(d);
                        }
                    }
                }
                if(remainClaus.isEmpty()){
                    queue.clear();
                    return;
                }
            }*/

            //prøv evt. det her kode
            toRemove.clear();
            remainClaus.stream()
                    .filter(d -> d.claus.contains(checkedSymbol))
                    .forEach(d ->{
                        //contains the exact same
                        if((isNegated && d.claus.contains(Operator.NOT.getOperator() + checkedSymbol)) || (!isNegated && !d.claus.contains(Operator.NOT.getOperator() + checkedSymbol)))
                            // Clause is proven
                            data.removeDataAtIndex(data.getIndex(d.claus));
                        else{
                            // Clauses are trimmed of symbol
                            if(isNegated) {
                                d.claus = d.claus.replace(checkedSymbol + Operator.OR.getOperator(), "");
                                d.claus = d.claus.replace(Operator.OR.getOperator() + checkedSymbol,"");
                                d.claus = d.claus.replace(checkedSymbol, "");
                            }
                            else {
                                d.claus = d.claus.replace(Operator.NOT.getOperator() + checkedSymbol + Operator.OR.getOperator(), "");
                                d.claus = d.claus.replace(Operator.OR.getOperator() + Operator.NOT.getOperator() + checkedSymbol, "");
                                d.claus = d.claus.replace(Operator.NOT.getOperator() + checkedSymbol, "");
                            }
                            // If it is a contradiction, remove from belief base
                            if(d.claus.isEmpty()){
                                data.removeDataAtIndex(d.index);
                                toRemove.add(d);
                            } else if(d.claus.length() <= 2){
                                // Add new symbols to queue, for further reductions
                                char control = d.claus.length() == 1 ?
                                        d.claus.charAt(0) :
                                        d.claus.charAt(1);
                                if(queue.stream().noneMatch(da -> da.contains(control + ""))){
                                    queue.add(d.claus);
                                    toRemove.add(d);
                                }
                            }
                        }
                    });
            toRemove.forEach(d -> remainClaus.remove(d));

            /*
            * Prøv at kig på det der!!!
            * */
        }
    }

    /*private void singleControl(String[] data){
        int start = 0, end = 0;
        switch(this.strategy){
            case TRUSTS_NEW -> {
                start = data.length;
            }
            case TRUSTS_OLD -> {
                end = data.length;
            }
        }
        List<String> singleClaus = new ArrayList<>();
        for(int i = start; i < end; i++){
            if(!data[i].contains(or+"")){
                singleClaus.add(data[i]);
            }
        }
        for(String claus: singleClaus){
            String reverseClaus = claus.contains(not+"") ? claus.substring(1) : not + claus;

            Arrays.stream(data).forEach(s -> s.contains(reverseClaus));
        }

    }*/

    class Data{
        int index;
        String claus;
        public Data(int index, String claus){
            this.index = index;
            this.claus = claus;
        }

        public String getClaus(){
            return this.claus;
        }
    }
}
