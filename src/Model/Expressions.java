package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Expressions implements IExpressions{
    ArrayList<String> list = new ArrayList<>();

    @Override
    public void addData(String[] list) {
        this.list.addAll(List.of(list));
    }

    @Override
    public void addData(String s){
        this.list.add(s);
    }

    @Override
    public void removeDataAtIndex(int index) {
        list.remove(index);
    }

    @Override
    public int getIndex(String s) {
        return IntStream.range(0,list.size()).filter(i -> s.equals(list.get(i))).findFirst().orElse(-1);
    }

    @Override
    public String getDataAtIndex(int index) {
        return list.get(index);
    }

    @Override
    public String[] getAllData() {
        return list.toArray(String[]::new);
    }

}
