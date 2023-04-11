package Model;

import java.util.ArrayList;
import java.util.Collections;

public class Expressions {
    ArrayList<String> list = new ArrayList<>();

    public Expressions(String... list){
        Collections.addAll(this.list, list);
    }

    public void addData(String s){
        this.list.add(s);
    }
}
