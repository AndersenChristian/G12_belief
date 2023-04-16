package Controller;

import Controller.CNF.Atomic;
import Controller.CNF.CNF;
import Controller.CNF.NoneTypeE;
import Controller.CNF.Not;
import Controller.CNF.And;
import Controller.CNF.Imp;
import Controller.CNF.Or;
import Controller.CNF.Iff;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CNFController {

    private final String iff = "<=>";
    private final String imp = "=>";
    private final String not = "~";
    private final String or = "|";
    private final String and = "&";
    private final List<String> possible = Arrays.asList(iff, or, not, imp, and);

    private BiFunction<CNF, CNF, CNF> generatorArrows(String choice) {
        try {
            if (choice.equals(iff)) {
                return Iff::new;
            } else if (choice.equals(imp)) {
                return Imp::new;
            } else {
                throw new NoneTypeE();
            }
        } catch (NoneTypeE e) {
            e.printStackTrace();
            System.out.println("Invalid rule inputted");
            return null;
        }
    }

    private Function<ArrayList<CNF>, CNF> generatorAndOr(String choice) {
        try {
            if (choice.equals(and)) {
                return And::new;
            } else if (choice.equals(or)) {
                return Or::new;
            } else {
                throw new NoneTypeE();
            }
        } catch (NoneTypeE e) {
            e.printStackTrace();
            System.out.println("Invalid rule inputted");
            return null;
        }
    }

    /**
     * Method stores the input terms in a hashmap
     * @param terms the logic expression ie. ~p => q
     * @return map of logical terms
     */
    private Map<String, CNF> storeTermsInHash(List<CNF> terms) {
        Map<String, CNF> map = new HashMap<>();
        for (CNF t : terms) {
            map.put(t.toString(), t);
        }
        map.put("TRUE", new Atomic(true, ""));
        map.put("FALSE", new Atomic(false, ""));
        return map;
    }

    /**
     * Method retrieves the variables from a logical expressions
     * @param input the logic expression ie. ~p => q
     * @return list of variables eg. [p, q]
     */
    private List<String> getVariables(String input) {
        return Arrays.stream(input.split(possible.toString()))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .toList();
    }

    /**
     * Method retrieves the operators from a logical expressions
     * @param input the logic expression ie. ~p => q
     * @return list of operators eg. [~, =>]
     */
    private String getOperator(String input) {
        String regex = possible.stream()
                .map(s -> "\\" + s)
                .collect(Collectors.joining("|", "[^", "]+"));
        return Arrays.stream(input.split(regex))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .findFirst()
                .get();
    }

    /**
     * Takes the input logical expression, splits it into terms and returns a sorted list.
     * @param input the logical expression
     * @return a list of sorted terms
     */
    private List<String> toListOfSortedTerms(String input) {
        List<String> terms = new ArrayList<>();
        terms.add(input);

        List<String> variables = getVariables(input);
        List<String> temp = new ArrayList<>(variables);
        for (String s : temp) {
            if (s.length() == 1) {
                terms.add(s);
            } else {
                terms.addAll(toListOfSortedTerms(s));
            }
        }
        Set<String> noDupes = new HashSet<>(terms);
        return noDupes.stream().sorted((s1, s2) -> s2.length() - s1.length()).collect(Collectors.toList());
    }

    /**
     * Creates the objects of the corresponding input terms.
     * @param terms list of terms
     * @return converted list of CNF-objects
     */
    private List<CNF> toClass(List<String> terms) {
        if (terms.size() > 0) {
            String goal = terms.remove(0);
            List<CNF> converted = toClass(terms);
            var map = storeTermsInHash(converted);
            if (goal.length() == 1) {
                converted.add(0, new Atomic(goal.charAt(0)));
            } else {
                if (goal.equals("TRUE")) {
                    converted.add(0, new Atomic(true, ""));
                } else if (goal.equals("FALSE")) {
                    converted.add(0, new Atomic(false, ""));
                } else {
                    String operator = getOperator(goal);
                    List<String> variables = getVariables(goal);
                    switch (operator) {
                        case iff, imp -> {
                            var constructor = generatorArrows(operator);
                            String left = variables.get(0);
                            String right = variables.get(1);
                            converted.add(0, constructor.apply(map.get(left), map.get(right)));
                        }
                        case not -> converted.add(0, new Not(map.get(variables.get(0))));
                        case and, or -> {
                            var constructor = generatorAndOr(operator);
                            ArrayList<CNF> cnfed = new ArrayList<>();
                            for (String s : variables) {
                                cnfed.add(map.get(s));
                            }
                            converted.add(0, constructor.apply(cnfed));
                        }
                        default -> System.out.println("hate");
                    }
                }
            }
            return converted;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Controller method to convert an input to CNF
     * @param input a logical expression
     * @return List of CNF statements
     */
    public List<CNF> convertToCNF(List<String> input) {
        Stream<String> s = input.stream();
        return s.map(this::toListOfSortedTerms)
                .map(this::toClass)
                .map((ls) -> ls.get(0))
                .map(CNF::convert)
                .map(CNF::simplify)
                .toList();
    }
}
