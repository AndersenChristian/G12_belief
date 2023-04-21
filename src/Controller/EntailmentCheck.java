package Controller;

import Model.IKnowledgeBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

class EntailmentCheck {
    Strategy strategy;
    List<String> removed = new ArrayList<>();
    char or = '|', not = 'n';
    public EntailmentCheck(Strategy strategy){
        this.strategy = strategy;
    }

    public List<String> removeEntailments(IKnowledgeBase data){
        String[] dataCopy = data.getAllData();
        removed.clear();
        singleControl(dataCopy);

        return removed;
    }

    private void singleControl(String[] data){
        int start = 0, end = 0;
        switch(this.strategy){
            case TRUSTS_NEW -> {
                start = data.length;
            }
            case THRUST_OLD -> {
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

    }

    private void bruteForce(){

    }
}
