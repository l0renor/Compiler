package edu.hm.llukas.parser;


import edu.hm.cs.rs.compiler.lab06rdparsergenerator.RDParserGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyParsealt implements RDParserGenerator {

    private Map<String[], Set<Character>> generateFist(Stream<String[]> grammaStream) {
        List<String[]> gramma = grammaStream.collect(Collectors.toList());
        Map<String[], Set<Character>> first = new HashMap<>();
        boolean changed = false;
        for (String[] rule : gramma) {
            first.put(rule, new HashSet<Character>());
        }
        do {
            changed = false;
            for (String[] rule : gramma) {
                char firstChar = rule[1].charAt(0);
                if (!Character.isUpperCase(firstChar)) {
                    changed = first.get(rule).add(firstChar);
                } else {
                    for (String[] compareRule : first.keySet()) {
                        if (compareRule[0].charAt(0) == (firstChar)) {

                            changed = first.get(rule).addAll(first.get(compareRule));
                        }
                    }

                }
            }

        } while (changed);
        return first;
    }

    @Override
    public String generate(String grammar) {
        Map<String[], Set<Character>> first = generateFist(parseGrammar(grammar));
        Map<String, Map<String[], Set<Character>>> sortedFirst = new HashMap<>();
        for (String[] rule : first.keySet()) {
            Set<Character> s = first.get(rule);
            if (sortedFirst.containsKey(rule[0])) {
                sortedFirst.get(rule[0]).put(rule, first.get(rule));
            } else {
                sortedFirst.put(rule[0], new HashMap<>());
                sortedFirst.get(rule[0]).put(rule, first.get(rule));
            }
        }
        char start = grammar.charAt(2);
        String result = "import java.util.*; \n";
        result += "public class RDParser" + start + " {\n";
        result += "public static void main(String... args) throws SyntaxErrorException {\n" +
                "    Node parseTree = new RDParserS().parse(args[0]);\n" +
                "    System.out.println(parseTree);  // Parsebaum in einer Zeile\n" +
                "    parseTree.prettyPrint();        // Parsebaum gekippt, mehrzeilig\n" +
                "}";
        result += getNodeSourcecode();
        result += getSyntaxErrorExceptionSourcecode();
        result += "public Node parse(String grammar){" +
                "grammar = grammar + \"$\";" +
                "private char lookahead = getNextToken();" +
                "";

        result += "boolean terminal(char expected) {\n" +
                "        if(lookahead != expected) {\n" +
                "            System.out.println(\"syntax error: expected \" + expected + \"; found \" + lookahead);\n" +
                "            return false;\n" +
                "        }\n" +
                "        lookahead = getNextToken();\n" +
                "        return true;\n" +
                "    }";
        for (String[] rule : first.keySet()) {
            for (Character c : first.get(rule)) {

            }
        }

        return null;
    }


    public Stream<String[]> parseGrammar(String grammarString) {
        return Stream.of(grammarString.split(String.valueOf(grammarString.charAt(1)))).skip(1).map(s -> s.split((String.valueOf(grammarString.charAt(0))))).map(strings -> {
            if (strings.length == 1) {
                return new String[]{strings[0], ""};
            } else {
                return strings;
            }
        });
    }

    public static void main(String[] args) {
        MyParsealt p = new MyParsealt();
        //Map<String[], Set<Character>> m = p.generateFist(p.parseGrammar("=,A=B,A=c,B=b"));
        // p.generate("=,A=B,A=c,B=b");
        HashSet<Character> eins = new HashSet<Character>();
        eins.add('(');
        HashSet<Character> zwei = new HashSet<Character>();
        zwei.add('n');
        zwei.add('-');
        Map<String[], Set<Character>> rules = new HashMap<>();
        rules.put(new String[]{"E", "(EOE)"}, eins);
        rules.put(new String[]{"E", "F"}, zwei);
        System.out.println(p.generateNonTerminalMethod(rules,"E"));

    }

    public String generateNonTerminalMethod(Map<String[], Set<Character>> rules, String nTerminal) {
        String res = "boolean" + nTerminal + "(){\n";
        for (String[] rule : rules.keySet()) {
            res += generateNonTerminalifBlock(rule,rules.get(rule));
        }
        res += "return false";
        return res;
    }

    public String generateNonTerminalifBlock(String[] ableitung, Set<Character> first) {
        String res = "if( ";
        for (char c : first) {
            res += "lookahead == \'" + c + "\'";
            res += "||";

        }
        res = res.substring(0, res.length() - 2);
        res += "){\n";
        res += "return ";
        for (int i = 0; i < ableitung[1].length(); i++) {
            if (Character.isUpperCase(ableitung[1].charAt(i))) {
                res += ableitung[1].charAt(i) + "()";
            } else {
                res += "terminal(\'" + ableitung[1].charAt(i) + "\')";
            }
            res += "&&";
        }
        res = res.substring(0, res.length() - 2);
        res += ";\n}\n";
        return res;
    }


}
