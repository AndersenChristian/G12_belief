package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TruthTable {

    int variableRows;
    int columns;
    int columnLength;
    boolean[][] table;
    boolean[][] negatedTable;
    List<String> variableList = new ArrayList<>();
    List<clauseColumn> clauseTable = new ArrayList<>();
    List<String> clauseList = new ArrayList<>();
    public TruthTable(List<String> variables){
        variableList = variables;
        variableRows = variables.size();
        columnLength = 1 << variables.size();

        table = new boolean[columnLength][variableRows];
        fillVariableTable();

        negatedTable = new boolean[columnLength][variableRows];
        for (int r = 0; r < columnLength; r++) {
            for (int c = 0; c < variableRows; c++) {
                negatedTable[r][c] = !table[r][c];
            }
        }
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
        String checkClause = getLastClauseColumn().clause;
        int indexToOperate = 0;

        while (checkClause.length() > 0) {
            boolean isNegated=false;
            if (checkClause.charAt(0) == Operator.NOT.getOperator().charAt(0)) {
                isNegated = true;
                for (int i = 0; i < variableRows; i++) {
                    if (variableList.get(i).charAt(0) == checkClause.charAt(1)) {
                        indexToOperate = i;
                        checkClause = checkClause.substring(2);
                        if (checkClause.length()>0){
                            checkClause = checkClause.substring(1);
                        }
                        break;
                    }
                }
            } else {
                for (int i = 0; i < variableRows; i++) {
                    if (variableList.get(i).charAt(0) == checkClause.charAt(0)) {
                        indexToOperate = i;
                        checkClause = checkClause.substring(1);
                        if (checkClause.length()>0){
                            checkClause = checkClause.substring(1);
                        }
                        break;
                    }
                }
            }
            getLastClauseColumn().orWith(indexToOperate, isNegated);
        }
    }

    public clauseColumn getClauseColumn(int index){
        return clauseTable.get(index);
    }

    public clauseColumn getLastClauseColumn(){
        return clauseTable.get(clauseTable.size()-1);
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

        public void setEquals(int columnIndex, boolean isNegated){
            if (isNegated){
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

        public void orWith(int columnIndex, boolean isNegated){
            if (isNegated){
                for (int i = 0; i < column.length; i++) {
                    if (truthTable.negatedTable[i][columnIndex]) {
                        column[i] = true;
                    }
                }
            }
            else {
                for (int i = 0; i < column.length; i++) {
                    if (truthTable.table[i][columnIndex]) {
                        column[i] = true;
                    }
                }
            }
        }
    }
}
