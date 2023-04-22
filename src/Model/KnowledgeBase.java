package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        size += list.length;
        Arrays.stream(list).forEach(s -> expressions.add(new Data(s, value)));
    }

    @Override
    public void addData(String s){
        addData(s, 1); //set 1 as default value
    }

    @Override
    public void addData(String s, int value){
        size++;
        this.expressions.add(new Data(s, value));
    }

    @Override
    public void removeDataAtIndex(int index) {
        size--;
        expressions.remove(index);
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
    public String getDataAtIndex(int index) {
        return expressions.get(index).claus;
    }

    @Override
    public int getValueAtIndex(int index) {
        return 0;
    }

    @Override
    public String[] getAllData() {
        return expressions.stream().map(Data::getClaus).toArray(String[]::new);
    }

    @Override
    public String toString() {
        return expressions.toString();
    }


    class Data{
        public String claus;
        public int value;
        public Data(String claus, int value){
            this.claus = claus;
            this.value = value;
        }
        public String getClaus(){
            return this.claus;
        }
    }

}
