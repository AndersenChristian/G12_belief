package Controller.CNF;

import java.util.ArrayList;
import java.util.Arrays;

public class Imp implements CNF {
    private CNF left;
    private CNF right;

    public Imp(CNF left, CNF right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "IMP(" + left + "," + right + ")";
    }

    @Override
    public CNF convert() {
        ArrayList<CNF> ors = new ArrayList<>(Arrays.asList(new Not(left.convert()), right.convert()));
        return new Or(ors);
    }

    @Override
    public String toSAT() {
        return "(" + left.toSAT() + ")" + "→" + "(" + right.toSAT() + ")";
    }

    @Override
    public String toInputFormat() {
        return left.toSAT() + "=>" + right.toSAT();
    }

    @Override
    public CNF simplify() {
        // cannot simply if
        return this;
    }

    public CNF getLeft() {
        return left;
    }

    public void setLeft(CNF left) {
        this.left = left;
    }

    public CNF getRight() {
        return right;
    }

    public void setRight(CNF right) {
        this.right = right;
    }
}
