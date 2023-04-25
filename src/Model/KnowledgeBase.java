package Model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KnowledgeBase implements IKnowledgeBase {
    List<Data> expressions = new ArrayList<>();
    private int size = 0;

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
        s = this.sortClaus(s);
        size++;
        this.expressions.add(new Data(s, value));
    }

    @Override
    public void removeDataAtIndex(int index) {
        size--;
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
        return this.size;
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
    public String toString() {
        return expressions.toString();
    }

    private String sortClaus(String s){
        String[] variables = s.split(Operator.OR.getOperator());
        List<TempData> tempData = new ArrayList<>();
        IntStream.range(0,variables.length-1)
                .forEach(i -> tempData.add(new TempData(variables[i].charAt(variables.length-1),variables.length == 2)));

        tempData.sort(Comparator.comparing(TempData::getC));
        return tempData.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
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
