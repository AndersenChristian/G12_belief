package Controller.CNF;

public interface CNF {
    // methods here later
    CNF convert();
    String toSAT();
    CNF simplify();
    String toInputFormat();
}


