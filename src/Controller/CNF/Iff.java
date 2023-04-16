package Controller.CNF;

import java.util.ArrayList;
import java.util.Arrays;

public class Iff implements CNF {
    private CNF left;
    private CNF right;
    // same as its recent parent

    public Iff(CNF left, CNF right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "IFF(" + left + "," + right + ")";
    }

    @Override
    public CNF convert() {
        Imp nleft = new Imp(left, right);
        Imp nright = new Imp(right, left);
        ArrayList<CNF> ands = new ArrayList<>(Arrays.asList(nleft.convert(), nright.convert()));
        return new And(ands);
    }

    @Override
    public String toSAT() {
        return "(" + left.toSAT() + ")" + "↔" + "(" + right.toSAT() + ")";
    }

    @Override
    public CNF simplify() {
        // cannot simply if
        return null;
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
