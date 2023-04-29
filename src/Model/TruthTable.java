package Model;

import java.util.Arrays;
import java.util.List;

public class TruthTable {

    int variableRows;
    int columns;
    int columnLength;
    boolean[][] table;
    public TruthTable(List<String> variables){
        variableRows = variables.size();
        columnLength = 1 << variables.size();

        table = new boolean[columnLength][variableRows];
        fillVariableTable();
    }

    private void fillVariableTable(){

        boolean fillValue = true;
        int switchIndex = columnLength/2;
        int switchCount = 0;

        for (int c=0; c < variableRows; c++){
            for (int r=0; r < columnLength; r++){
                table[r][c] = fillValue;
                switchCount++;
                if (switchCount >= switchIndex){
                    fillValue = !fillValue;
                    switchCount=0;
                }
            }
            switchIndex = switchIndex/2;
        }
    }

    public void printTable(){
        String tableString = Arrays.deepToString(table);
        tableString = tableString.replace("]","\n");
        System.out.println(tableString);
    }
}
