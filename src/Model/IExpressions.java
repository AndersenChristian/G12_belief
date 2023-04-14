package Model;

public interface IExpressions {
    void addData(String[] list);
    void addData(String s);
    void removeDataAtIndex(int index);
    int getIndex(String s);
    String getDataAtIndex(int index);
    String[] getAllData();

}
