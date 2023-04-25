package Model;

public class Data {
    private String claus;
    private int size;
    private Data dataInKnowledgeBaseReference;

    public Data(String claus, int index){
        this.constructor(claus);
    }

    public Data(Data data){
        dataInKnowledgeBaseReference = data;
        constructor(data.claus);
    }

    private void constructor(String claus){
        this.claus = claus;

        // Count amount of variables
        size = 1;
        for(int i=0; i<claus.length(); i++){
            if (claus.charAt(i) == Operator.OR.getOperator().charAt(0)){
                size++;
            }
        }
    }

    public String getClaus(){
        return this.claus;
    }

    public void setClaus(String claus){
        this.claus = claus;
    }

    public int getSize() {
        return size;
    }

    public Data getDataInKnowledgeBaseReference() {
        return dataInKnowledgeBaseReference;
    }
}
