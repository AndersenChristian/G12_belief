package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class KnowledgeBase implements IKnowledgeBase {
    ArrayList<String> expressions = new ArrayList<>();
    private int size = 0;

    @Override
    public void addData(String[] list) {
        size += list.length;
        this.expressions.addAll(List.of(list));
    }

    @Override
    public void addData(String s){
        size++;
        this.expressions.add(s);
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
        return expressions.get(index);
    }

    @Override
    public String[] getAllData() {
        return expressions.toArray(String[]::new);
    }

    @Override
    public String toString() {
        return expressions.toString();
    }

}
