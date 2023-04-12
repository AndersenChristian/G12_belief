package Model;

public interface IExpressions {
    void addData(String[] list);
    void addData(String s);
    void removeData(String s) throws Exception;
    void removeDataAt(int index) throws Exception;

}
