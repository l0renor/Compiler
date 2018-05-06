package edu.hm.llukas.generator;

import edu.hm.cs.rs.compiler.lab04generator.LanguageGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MyGen implements LanguageGenerator {
    @Override
    public Stream<String> generate(Stream<String[]> grammar, int uptoLengthint) {
        long uptoLength = uptoLengthint;
        List<String[]> gram = grammar.collect(Collectors.toList());
        List<String> words = new ArrayList<>();
        List<String> unfinischedWords = new ArrayList<>();
        unfinischedWords.add(gram.get(0)[0]);
        boolean finished = false;
        while (!finished) {
            finished = true;
            List<String> tmpList = new ArrayList<>();

            for (int i = 0; i < unfinischedWords.size(); i++) {
                for (String[] rule : gram) {
                    if (unfinischedWords.get(i).contains(rule[0])) {
                        String tmp = unfinischedWords.get(i).replaceFirst(rule[0], rule[1]);
                        if (tmp.toLowerCase().equals(tmp)) {
                            if (tmp.length() <= uptoLength) {

                                words.add(tmp);
                            }
                        } else {
                            if (tmp.length() <= uptoLength) {
                                tmpList.add(tmp);
                            }
                        }

                    }
                }
            }

            unfinischedWords = new ArrayList<>(new HashSet<>(tmpList));
            finished = unfinischedWords.isEmpty();
        }
        words = new ArrayList<>(new HashSet<>(words));
        return words.stream().distinct().sorted(
                Comparator.comparingInt(String::length)
                        .thenComparing(Comparator.naturalOrder())
        );
    }

    private boolean finished(List<String> list, long len) {
        for (String word : list) {
            if (word.length() < len + 2) {
                return false;
            }

        }

        return true;
    }

    @Override
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
        long start = System.currentTimeMillis();
        //System.out.println("HALLo".contains("[A-Z]"));
       // new MyGen().parseGrammar("=;S=();S=(S);S=()S;S=(S)S").forEach(strings -> System.out.println(strings[0] + "->" + strings[1]));
        new MyGen().generate(new MyGen().parseGrammar("-,X-S.,S-S.S,S-nAV,S-VSV,A-VAV,A-=,V-t,V-n"), 10).forEach(
                (x)-> System.out.println(x));
        System.out.println(start-System.currentTimeMillis());

    }
}