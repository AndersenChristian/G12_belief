package Model;

import Controller.Strategy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KnowledgeBase implements IKnowledgeBase {
    List<Data> expressions = new ArrayList<>();

    public KnowledgeBase(){}


    /**
     * Used to copy the object
     *
     * @param data
     */
    public KnowledgeBase(IKnowledgeBase data){
        Arrays.stream(data.getAllData())
                .forEach(d -> expressions.add(new Data(d)));
    }

    @Override
    public void addData(String[] list) {
        addData(list,1);
    }

    @Override
    public void addData(String[] list, int value) {
        Arrays.stream(list)
                .forEach(s -> this.addData(s,value));
    }

    @Override
    public void addData(String s){
        addData(s, 1); //set 1 as default value
    }

    @Override
    public void addData(String s, int value){
        //s = this.sortClaus(s);
        this.expressions.add(new Data(s.replaceAll(" ", ""), value));
    }


    @Override
    public void removeDataAtIndex(int index) {
        expressions.remove(index);
    }

    @Override
    public void removeData(Data data) {
        expressions.remove(data);
    }

    @Override
    public int getIndex(String s) {
        return IntStream.range(0, expressions.size())
                .filter(i -> s.equals(expressions.get(i)))
                .findFirst()
                .orElse(-1);
    }

    @Override
    public int getSize() {
        return expressions.size();
    }

    @Override
    public Data getDataAtIndex(int index) {
        return expressions.get(index);
    }

    @Override
    public int getValueAtIndex(int index) {
        return 0;
    }

    @Override
    public Data[] getAllData() {
        return expressions.toArray(Data[]::new);
    }

    @Override
    public IKnowledgeBase sort(Strategy strategy) {
        KnowledgeBase out = new KnowledgeBase();
        for (Data d : expressions){
            out.addData(d);
        }
        switch (strategy){
            case TRUST_NEW -> {
                Collections.reverse(out.getExpressions());
            }
            case TRUST_OLD -> {
                //DO nothing
            }
            case TRUST_LONG -> {
                //Collections.sort(out.getExpressions(),Comparator.comparingInt(Data::getSize));
                out.getExpressions().sort(Comparator.comparingInt(Data::getSize).reversed());
            }
            case TRUST_SHORT -> {
                out.getExpressions().sort(Comparator.comparingInt(Data::getSize));
            }
        }
        return out;
    }

    public void addData(Data d){
        expressions.add(d);
    }

    public List<Data> getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        expressions.forEach(s -> out.append(s.toString()).append("\n"));
        return out.toString();
    }

    private String sortClaus(String in){
        String[] variables = in.split("[" + Operator.OR.getOperator() + "]");
        List<TempData> tempData = new ArrayList<>();
        Arrays.stream(variables).forEach(s -> tempData.add(new TempData(s.charAt(s.length()-1),s.length() == 2)));

        tempData.sort(Comparator.comparing(TempData::getC));
        String out = tempData.stream()
                .map(o -> o.toString() + Operator.OR.getOperator())
                .collect(Collectors.joining());
        return out.substring(0, out.length()-1);

    }

    private class TempData{
        char c;
        boolean isNegated;
        public TempData(char c, boolean isNegated){
            this.c = c;
            this.isNegated = isNegated;
        }

        public char getC(){
            return this.c;
        }

        @Override
        public String toString(){
            return isNegated ? Operator.NOT.getOperator() + c: String.valueOf(c);
        }
    }

}
