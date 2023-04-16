package Controller.CNF;

public class Atomic extends CNF {
    private char val = ' ';
    private boolean isTrue;

    public Atomic(boolean b, String flag) {
        this.isTrue = b;
    }

    public Atomic(char val) {
        this.val = val;
    }

    @Override
    public String toString() {
        if (val == ' ') {
            return isTrue ? "TRUE" : "FALSE";
        } else {
            //return "\'" + val + "\'";
            return String.valueOf(val);
        }
    }

    @Override
    public CNF convert() {
        return this;
    }

    @Override
    public String toSAT() {
        if (val == ' ') {
            return isTrue ? "TRUE" : "FALSE";
        } else {
            return String.valueOf(val);
        }
    }

    @Override
    public CNF simplify() {
        // cannot simplify atomic
        return this;
    }

    public char getVal() {
        return val;
    }

    public void setVal(char val) {
        this.val = val;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setBool(boolean aTrue) {
        this.isTrue = aTrue;
    }
}
