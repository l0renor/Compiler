package edu.hm.llukas.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyGen implements LanguageGenerator{
    @Override
    public Stream<String> generate(Stream<String[]> grammar, int uptoLength) {
        List<String[]> gram = grammar.collect(Collectors.toList());
        List<String> words = new ArrayList<>();
        words.add(gram.get(0)[0]);
        while (true) {
            boolean finished = true;
            for (int i = 0;i<words.size();i++) {
                for (String[] rule : gram) {
                    if (words.get(i).contains(rule[0])) {
                        words.add(words.get(i).replaceFirst(rule[0], rule[1]));
                    }
                }
            }
            for (String word: words) {
                if (word.length() < uptoLength && word.equals(word.toLowerCase())){
                    finished = false;
                }
            }
            if (finished) {break;}
        }
        return words.stream();
    }

    @Override
    public Stream<String[]> parseGrammar(String grammarString) {
        return Stream.of(grammarString.split(String.valueOf(grammarString.charAt(1)))).skip(1).map(s -> s.split((String.valueOf(grammarString.charAt(0)))));
    }
    public static void main(String [] args){
        //System.out.println(new MyGen().parseGrammar("=;S=();S=(S);S=()S;S=(S)S"));
        new MyGen().parseGrammar("=;S=();S=(S);S=()S;S=(S)S").forEach(strings -> System.out.println(strings[0]+"->"+strings[1]));
        new MyGen().generate(new MyGen().parseGrammar("=;S=();S=(S);S=()S;S=(S)S"),5).forEach(s -> System.out.println(s));

    }
}
