package edu.hm.llukas.parser;


import edu.hm.cs.rs.compiler.lab06rdparsergenerator.RDParserGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyParse implements RDParserGenerator {

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
                        if (compareRule[0].charAt(0)==(firstChar)) {

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
        MyParse p = new MyParse();
        Map<String[], Set<Character>> m = p.generateFist(p.parseGrammar("=,A=B,B=b"));
        int i = 0;
        i++;

    }
}
