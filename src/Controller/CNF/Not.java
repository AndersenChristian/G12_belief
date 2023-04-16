package Controller.CNF;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Not implements CNF {
    private CNF value;

    public Not(CNF value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NOT(" + value + ")";
    }

    @Override
    public CNF convert() {
        return new Not(value.convert());
    }

    @Override
    public String toSAT() {
        return "¬" + "(" + value.toSAT() + ")";
    }

    @Override
    public String toInputFormat() {
        return "~" + value.toSAT();
    }

    @Override
    public CNF simplify() {
        CNF nval;
        if (value instanceof Not) {
            nval = ((Not) value).getValue().simplify();
        } else if (value instanceof And) {
            ArrayList<CNF> ors = new ArrayList<>();
            for (CNF o : ((And) value).getPhis()) {
                CNF negapp = new Not(o).simplify();
                ors.add(negapp);
            }
            nval = new Or(ors);
        } else if (value instanceof Or) {
            ArrayList<CNF> and = new ArrayList<>();
            for (CNF o : ((Or) value).getPhis()) {
                CNF negapp = new Not(o).simplify();
                and.add(negapp);
            }
            nval = new And(and);
        } else if (value instanceof Atomic) {
            if (((Atomic) value).getVal() == ' ') {
                if (((Atomic) value).isTrue()) {
                    nval = new Atomic(false, "");
                } else {
                    nval = new Atomic(true, "");
                }
            } else {
                nval = this;
            }
        } else {
            nval = this;
        }
        return nval;
    }

    public CNF getValue() {
        return value;
    }

    public void setValue(CNF value) {
        this.value = value;
    }
}
