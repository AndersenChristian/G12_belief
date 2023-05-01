package Model;

import Controller.Strategy;

public interface IKnowledgeBase{

    void addData(String[] list);
    void addData(String[] list, int value);
    void addData(String s);
    void addData(String s, int value);
    void removeDataAtIndex(int index);
    void removeData(Data data);
    int getIndex(String s);
    int getSize();
    Data getDataAtIndex(int index);
    int getValueAtIndex(int index);
    Data[] getAllData();
    IKnowledgeBase sort(Strategy strategy);

}
