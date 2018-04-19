package edu.hm.llukas.generator;

import edu.hm.cs.rs.compiler.lab04generator.LanguageGenerator;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyGen implements LanguageGenerator {
    @Override
    public Stream<String> generate(Stream<String[]> grammar, int uptoLengthint) {
        long uptoLength = uptoLengthint;
        List<String[]> gram = grammar.collect(Collectors.toList());
        List<String> words = new ArrayList<>();
        List<String> unfishedWords = new ArrayList<>();
        unfishedWords.add(gram.get(0)[0]);
        boolean finished = false;
        while (!finished && unfishedWords.size() > 0) {
            finished = finished(unfishedWords, uptoLength);

            for (int i = 0; i < unfishedWords.size(); i++) {
                for (String[] rule : gram) {
                    if (unfishedWords.get(i).contains(rule[0])) {
                        String tmp = unfishedWords.get(i).replaceFirst(rule[0], rule[1]);
                        if (tmp.equals(tmp.toLowerCase())) {
                            words.add(unfishedWords.get(i).replaceFirst(rule[0], rule[1]));
                        } else {
                            unfishedWords.add(unfishedWords.get(i).replaceFirst(rule[0], rule[1]));
                        }

                    }
                }
                unfishedWords.remove(0);
                i--;
                System.out.println(unfishedWords.toString());
                System.out.println(words.toString());
                if (finished(unfishedWords, uptoLength) || unfishedWords.size() == 0) {
                    break;
                }
            }
        }
        //words = words.stream().filter(word -> word.contains("[A-Z]")).collect(Collectors.toList());
        // return new HashSet<String>(words).stream();
        words = new ArrayList<>(new HashSet<>(words));
        return words.stream().filter(word -> word.length() <= uptoLength).sorted((a, b)-> {
            if(a.length() > b.length()) {
                return 1;
            } else if (a.length() < b.length()) {
                return -1;
            } else{
                return a.compareTo(b);
            }});
        //filter(word -> word.toLowerCase() == word)
    }

    private boolean finished(List<String> list, long len) {
        for (String word : list) {
            System.out.println(word.length());
            if (word.length() < len+1) {
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
        //System.out.println("HALLo".contains("[A-Z]"));
        //new MyGen().parseGrammar("=;S=();S=(S);S=()S;S=(S)S").forEach(strings -> System.out.println(strings[0]+"->"+strings[1]));
        new MyGen().generate(new MyGen().parseGrammar("=;S=abcd;S=aSQ;cQd=Bccdd;dQ=Qd;bB=bb;cB=Bc"), 20
        ).forEach(s -> System.out.println(s));

    }
}
