package edu.hm.llukas.generator;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyGen implements LanguageGenerator{
    @Override
    public Stream<String> generate(Stream<String[]> grammar, int uptoLength) {
        List<String[]> gram = grammar.collect(Collectors.toList());
        List<String> words = new ArrayList<>();
        words.add(gram.get(0)[0]);
        boolean finished = false;
        int j = 0;
        while (!finished) {
            finished = finished(words,uptoLength);
            for (int i = j;i<words.size();i++) {
                for (String[] rule : gram) {
                    if (words.get(i).contains(rule[0])) {
                        words.add(words.get(i).replaceFirst(rule[0], rule[1]));
                    }
                }
                j++;
            if(finished(words,uptoLength)){
                   break;
            }
            }
        }
        //words = words.stream().filter(word -> word.contains("[A-Z]")).collect(Collectors.toList());
       // return new HashSet<String>(words).stream();
        return words.stream().filter(word -> word.toLowerCase() == word);
    }
    private boolean finished(List<String> list, int len){
        for (String word: list){
            if(word.replaceAll("[A-Z]","").length() > len){
                return true;
            }
        }
    return false;
    }

    @Override
    public Stream<String[]> parseGrammar(String grammarString) {
        return Stream.of(grammarString.split(String.valueOf(grammarString.charAt(1)))).skip(1).map(s -> s.split((String.valueOf(grammarString.charAt(0)))));
    }
    public static void main(String [] args){
       //System.out.println("HALLo".contains("[A-Z]"));
        //new MyGen().parseGrammar("=;S=();S=(S);S=()S;S=(S)S").forEach(strings -> System.out.println(strings[0]+"->"+strings[1]));
        new MyGen().generate(new MyGen().parseGrammar("=;S=();S=(S);S=()S;S=(S)S"),3).forEach(s -> System.out.println(s));

    }
}
