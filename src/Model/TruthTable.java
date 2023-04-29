package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TruthTable {

    int variableRows;
    int columns;
    int columnLength;
    boolean[][] table;
    List<clauseColumn> clauseTable = new ArrayList<>();
    List<String> clauseList = new ArrayList<>();
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
        for (int i = 0; i < clauseTable.size(); i++) {
            System.out.println(clauseTable.get(i).clause+ ": " + Arrays.toString(clauseTable.get(i).column));
        }

    }

    public void addClause(String clause){
        clauseTable.add(new clauseColumn(clause, columnLength, this));
    }

    public clauseColumn getClauseColumn(int index){
        return clauseTable.get(index);
    }

    private class clauseColumn {
        TruthTable truthTable;
        String clause;
        boolean[] column;

        public clauseColumn(String clause, int length, TruthTable truthTable){
            this.clause = clause;
            this.truthTable = truthTable;
            this.column = new boolean[length];
        }

        public void setEquals(int columnIndex, boolean useClauseTable){
            if (useClauseTable){
                //TODO
            }
            else {
                for (int i = 0; i < column.length; i++) {
                    column[i] = truthTable.table[columnIndex][i];
                }
            }
        }

        public void negate(){
            for (int i = 0; i < column.length; i++){
                column[i] = !column[i];
            }
        }

        public void orWith(int columnIndex, boolean useClauseTable){
            if (useClauseTable){
                //TODO
            }
            else {
                for (int i = 0; i < column.length; i++) {
                    if (truthTable.table[columnIndex][i]) {
                        column[i] = true;
                    }
                }
            }
        }
    }
}
