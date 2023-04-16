package Controller;

import Controller.CNF.Atomic;
import Controller.CNF.CNF;
import Controller.CNF.NoneTypeE;
import Controller.CNF.Not;
import Controller.CNF.And;
import Controller.CNF.Imp;
import Controller.CNF.Or;
import Controller.CNF.Iff;

import java.io.File;
import java.io.FileNotFoundException;
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

    // helpers for my lovely streams
    static String noParen(String input) {
        int begin = input.indexOf("(");
        return input.substring(begin + 1, input.length() - 1);
    }

    // splits a string at , - ignores those in a paren
    static List<String> splitAt(String input) {
        int parens = 0;
        int index = 0;
        List<String> ret = new ArrayList<>();
        char[] charr = input.toCharArray();
        for (char c : charr) {
            if (c == '(') {
                parens += 1;
            } else if (c == ')') {
                parens -= 1;
            }
            if (parens == 0) {
                if (c == ',') {
                    ret.add(input.substring(0, index));
                    var recur = splitAt(input.substring(index + 1));
                    ret.addAll(recur);
                    break;
                }
            }
            index += 1;
        }
        return ret.size() == 0 ? Collections.singletonList(input) : ret;
    }

    // takes an input string and returns all the terms and subterms
    static List<String> stringTo(String input) {
        List<String> terms = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        terms.add(input);

        if (input.contains("(")) {
            List<String> split = splitAt(noParen(input));
            //String subterm = noParen(input);
            temp.addAll(split);
            for (String s : temp) {
                if (s.length() == 0) {
                } else if (s.length() == 3) {
                    terms.add(s);
                } else {
                    terms.addAll(stringTo(s));
                }
            }
        }
        Set<String> noDupes = new HashSet<>(terms);
        return noDupes.stream().sorted((s1, s2) -> s2.length() - s1.length()).collect(Collectors.toList());
    }

    // i am an idiot
    static <E> BiFunction<CNF, CNF, CNF> arrows(String choice) {
        try {
            if (choice.equals("IFF")) {
                return Iff::new;
            } else if (choice.equals("IMP")) {
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

    static <E> Function<ArrayList<CNF>, CNF> andor(String choice) {
        try {
            if (choice.equals("AND")) {
                return And::new;
            } else if (choice.equals("OR(")) {
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

    private <E> BiFunction<CNF, CNF, CNF> arrowsCNF(String choice) {
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

    private <E> Function<ArrayList<CNF>, CNF> andorCNF(String choice) {
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

    static Map<String, CNF> hash(List<CNF> terms) {
        Map<String, CNF> map = new HashMap<>();
        for (CNF t : terms) {
            map.put(t.toString(), t);
        }
        map.put("TRUE", new Atomic(true, ""));
        map.put("FALSE", new Atomic(false, ""));
        return map;
    }

    //this is where the actual conversion happens
    static List<CNF> toClass(List<String> terms) {
        List<String> possible = Arrays.asList("IFF", "OR", "NOT", "IMP", "AND");
        if (terms.size() > 0) {
            String goal = terms.remove(0);
            List<CNF> converted = toClass(terms);
            var map = hash(converted);
            if (goal.length() == 3) {
                converted.add(0, new Atomic(goal.charAt(1)));
            } else {
                if (goal.equals("TRUE")) {
                    converted.add(0, new Atomic(true, ""));
                } else if (goal.equals("FALSE")) {
                    converted.add(0, new Atomic(false, ""));
                } else {
                    String outer = goal.substring(0, 3);
                    String inner = noParen(goal);
                    if (outer.equals(possible.get(0)) || outer.equals(possible.get(3))) {
                        var constructor = arrows(outer);
                        List<String> matchon = splitAt(inner);
                        String left = matchon.get(0);
                        String right = matchon.get(1);
                        converted.add(0, constructor.apply(map.get(left), map.get(right)));
                    } else if (outer.equals(possible.get(2))) {
                        converted.add(0, new Not(map.get(inner)));
                    } else if (outer.contains(possible.get(1)) || outer.equals(possible.get(4))) {
                        var constructor = andor(outer);
                        List<String> matchon = splitAt(inner);
                        ArrayList<CNF> cnfed = new ArrayList<>();
                        for (String s : matchon) {
                            if (!s.equals("")) {
                                cnfed.add(map.get(s));
                            }
                        }
                        converted.add(0, constructor.apply(cnfed));
                    } else {
                        System.out.println("hate");
                    }
                }
            }
            return converted;
        } else {
            return new ArrayList<>();
        }
    }

    private List<String> getVariables(String input) {
        return Arrays.stream(input.split(possible.toString()))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .toList();
    }

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

    private List<String> stringToCNF(String input) {
        List<String> terms = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        terms.add(input);

        if (possible.stream().anyMatch(input::contains)) {
            List<String> split = getVariables(input);
            //String subterm = noParen(input);
            temp.addAll(split);
            for (String s : temp) {
                if (s.length() == 1) {
                    terms.add(s);
                } else {
                    terms.addAll(stringTo(s));
                }
            }
        }
        Set<String> noDupes = new HashSet<>(terms);
        return noDupes.stream().sorted((s1, s2) -> s2.length() - s1.length()).collect(Collectors.toList());
    }

    private List<CNF> toClassCNF(List<String> terms) {
        if (terms.size() > 0) {
            String goal = terms.remove(0);
            List<CNF> converted = toClassCNF(terms);
            var map = hash(converted);
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
                            var constructor = arrowsCNF(operator);
                            String left = variables.get(0);
                            String right = variables.get(1);
                            converted.add(0, constructor.apply(map.get(left), map.get(right)));
                        }
                        case not -> converted.add(0, new Not(map.get(variables.get(0))));
                        case and, or -> {
                            var constructor = andor(operator);
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
    public List<String> convertToCNF(List<String> input) {
        Stream<String> s = input.stream();
        List<CNF> cnf = s.map(this::stringToCNF)
                .map(this::toClassCNF)
                .map((ls) -> ls.get(0))
                .map(CNF::convert)
                .map(CNF::simplify)
                .toList();


        System.out.println("Before conversion, the sentence is:");
        System.out.println(input);
        System.out.println("Converting this to CNF gives you:");
        System.out.println(cnf);

        return new ArrayList<>();
    }


    // the program itself
    public static void main(String[] args) {
        try {
            File f = new File("src/input.txt");
            Scanner sc = new Scanner(f);
            List<String> phis = new ArrayList<>();
            while (sc.hasNextLine()) {
                phis.add(sc.nextLine());
            }

            // because i love higher order fns :)
            Stream<String> s = phis.stream();
            List<CNF> temp = s.map(CNFController::stringTo)
                    .map(CNFController::toClass)
                    .map((ls) -> ls.get(0))
                    .map(CNF::convert)
                    .map(CNF::simplify)
                    .toList();

            for (int i = 0; i < phis.size(); i++) {
                System.out.println("Before conversion, the sentence is:");
                System.out.println(phis.get(i));
                System.out.println("Converting this to CNF gives you:");
                System.out.println(temp.get(i));
                System.out.println("-".repeat(10));
            }

            // for SAT solver
            System.out.println("*".repeat(20));
            System.out.println("FOR SAT SOLVER");
            Stream<String> s1 = phis.stream();
            s1.map(CNFController::stringTo)
                    .map(CNFController::toClass)
                    .map((ls) -> ls.get(0))
                    .map((x) -> "The input:" + "\n" + x.toSAT() + "\n" + "The CNF conversion:" + "\n" + x.convert().simplify().toSAT() + "\n" + "-".repeat(10))
                    .forEach(System.out::println);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}