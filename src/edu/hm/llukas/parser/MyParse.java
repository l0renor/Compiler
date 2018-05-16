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
        result += "public class  RDParser" + start+ " {\n";
        result += "String input;\n";
        result += "char lookahead;\n";
        result += "RDParser" + start + "(String input)throws SyntaxErrorException{" +
                "this.input = input;\n" +
                "lookahead = getNextToken();}\n ";
        result += "public static void main(String... args) throws SyntaxErrorException {\n" +
                "    Node parseTree = new RDParser"+ start + "(args[0]).parse();\n" +
                "    System.out.println(parseTree);  // Parsebaum in einer Zeile\n" +
                "    parseTree.prettyPrint();        // Parsebaum gekippt, mehrzeilig\n" +
                "}";
        result += getNodeSourcecode();
        result += getSyntaxErrorExceptionSourcecode();
        result += "char getNextToken() {\n" +
                "        if(input.length() == 0)\n" +
                "            return '$';\n" +
                "        char token = input.charAt(0);\n" +
                "        input = input.substring(1);\n" +
                "        return token;\n" +
                "    }";
        result += "Node terminal(char expected) throws SyntaxErrorException{\n" +
                "        if(lookahead != expected) {\n" +
                "            throw new SyntaxErrorException(\" syntax error: expected \" + expected + \" found \"  + lookahead);\n" +
                "        }\n" +
                "        lookahead = getNextToken();\n" +
                "        return new Node(\"\"+expected,new Node[]{});\n" +
                "    }";
        result += "public Node parse() throws SyntaxErrorException{\n"+
                "return " +start  +"();\n}\n";

        for (String nTerminal: sortedFirst.keySet()){
            result += generateNonTerminalMethod(sortedFirst.get(nTerminal), nTerminal);
        }
        result += "\n}";
        return result;
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



    public String generateNonTerminalMethod(Map<String[], Set<Character>> rules, String nTerminal) {
        String res = "Node " + nTerminal + "()throws SyntaxErrorException{\n";
        res += "Node n = new Node(\""+ nTerminal + "\",new Node[]{});\n";
        for (String[] rule : rules.keySet()) {
            res += generateNonTerminalifBlock(rule,rules.get(rule));
            res += "else ";
        }
        res = res.substring(0,res.length()-5);
        res += "else {throw new SyntaxErrorException(\" syntax error: expected one of (";
        for (Set<Character> expected : rules.values()){
            res += expected.toString();
        }
        res += ") found\"  + lookahead); }";
        res += "\nreturn n;\n}\n";
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
        for (int i = 0; i < ableitung[1].length(); i++) {
            if (Character.isUpperCase(ableitung[1].charAt(i))) {
                res += "n.add("+ ableitung[1].charAt(i) + "());\n";
            } else {
                res += "n.add(terminal(\'" + ableitung[1].charAt(i) + "\'));\n";
            }
        }
        //res += "return n";
        res += "\n}";
        return res;
    }

    public static void main(String[] args) {
        MyParse p = new MyParse();
        //Map<String[], Set<Character>> m = p.generateFist(p.parseGrammar("=,A=B,A=c,B=b"));
        // p.generate("=,A=B,A=c,B=b");
      /*  HashSet<Character> eins = new HashSet<Character>();
        eins.add('(');
        HashSet<Character> zwei = new HashSet<Character>();
        zwei.add('n');
        zwei.add('-');
        Map<String[], Set<Character>> rules = new HashMap<>();
        rules.put(new String[]{"E", "(EOE)"}, eins);
        rules.put(new String[]{"E", "F"}, zwei);
        System.out.println(p.generateNonTerminalMethod(rules,"E"));*/
      System.out.println(p.generate("=,B=C,C=c,B=D,D=d,B=E,E=e,B=F,F=f,B=G,G=g,B=H,H=h,B=I,I=i,B=J,J=j,B=K,K=k,B=L,L=l,B=M,M=m,B=N,N=n,B=O,O=o,B=P,P=p,B=Q,Q=q,B=R,R=r,B=S,S=s,B=T,T=t,B=U,U=u,B=V,V=v,B=W,W=w,B=X,X=x,B=Y,Y=y,B=Z,Z=z"));

    }


}
