package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void removeData(String s) throws Exception {
        if (s == null) throw new NullPointerException();
        for(String data: this.list){
            if(data.equals(s)){
                list.remove(data);
                return;
            }
        }
        throw new NoSuchFieldException();
    }

    @Override
    public void removeDataAt(int index) throws Exception {

    }
}
