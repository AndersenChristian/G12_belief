package Controller.CNF;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Or extends CNF {
    private ArrayList<CNF> phis;

    public Or(ArrayList<CNF> phis) {
        this.phis = phis;
    }

    @Override
    public String toString() {
        String returned = "OR(";
        String sep = ",";
        for (int i = 0; i < phis.size(); i++) {
            returned += (i == phis.size() - 1) ? phis.get(i) + ")" : phis.get(i) + sep;
        }
        return phis.size() == 0 ? returned + ")" : returned;
    }

    @Override
    public CNF convert() {
        ArrayList<CNF> converted = new ArrayList<>();
        for (CNF phi : phis) {
            converted.add(phi.convert());
        }
        return phis.size() == 0 ? new Atomic(false, "") : new Or(converted);
    }

    @Override
    public String toSAT() {
        return phis.size() == 0 ? "FALSE" : phis.stream().map((x) -> "(" + x.toSAT() + ")").collect(Collectors.joining("∨"));
    }

    @Override
    public CNF simplify() {
        ArrayList<CNF> simps = new ArrayList<>();
        for (CNF phi : phis) {
            CNF simp = phi.simplify();
            if (simp instanceof Atomic) {
                if (((Atomic) simp).getVal() == ' ') {
                    if (((Atomic) simp).isTrue()) {
                        return new Atomic(true, "");
                    } else if (!((Atomic) simp).isTrue()) {
                        continue;
                    }
                }
            }
            simps.add(simp);
        }
        return simps.size() == 0 ? new Atomic(false, "") : new Or(simps);
    }

    public ArrayList<CNF> getPhis() {
        return phis;
    }

    public void setPhis(ArrayList<CNF> phis) {
        this.phis = phis;
    }
}
