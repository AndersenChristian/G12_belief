package Model;

public interface IKnowledgeBase{

    void addData(String[] list);
    void addData(String[] list, int value);
    void addData(String s);
    void addData(String s, int value);
    void removeDataAtIndex(int index);
    int getIndex(String s);
    int getSize();
    String getDataAtIndex(int index);
    int getValueAtIndex(int index);
    String[] getAllData();

}
