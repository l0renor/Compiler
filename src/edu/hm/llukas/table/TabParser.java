package edu.hm.llukas.table;

import edu.hm.cs.rs.compiler.toys.base.LL1TableParser;
import edu.hm.cs.rs.compiler.toys.base.SyntaxError;
import edu.hm.cs.rs.compiler.toys.base.TokenStream;
import edu.hm.cs.rs.compiler.toys.base.Tree;

public class TabParser extends LL1TableParser {

    public TabParser() {
        final String PROGRAM = "Programm";
        final String STATEMENT = "Statement";
        final String OUTPUT = "Output";
        final String PBODY = "Pbody";
        final String ASSIGNMENT = "Assignment";
        final String EXPRESSION = "Expression";
        final String EXPRESSION2 = "Expression2";
        final String ADDSUB = "Addsub";
        final String TERM = "Term";
        final String TERM2 = "Term2";
        final String POINTOP = "Pointop";
        final String Factor = "Factor";
        final String EMPTY = "";
        final String PRINT = "print";
        final String ASSIGN = "assign";
        final String IDENTIFIER = "id";
        final String NUMERAL = "num";
        final String ADD = "add";
        final String SUB = "sub";
        final String DIV = "div";
        final String MULT = "mult";
        final String MOD = "mod";
        final String OPEN = "open";
        final String CLOSE = "close";
        final String SEMICOLON = "semicolon";
        final String POT = "pot";
        final String POW = "Pow";
        final String POW2 = "Pow2";


        setHtmlTrace(true);
        verbose(true);

        matrix("$", PROGRAM, "");
        matrix(PRINT, PROGRAM, STATEMENT + " " + PROGRAM);
        matrix(IDENTIFIER, PROGRAM, STATEMENT + " " + PROGRAM);
        matrix(SEMICOLON, PROGRAM, STATEMENT + " " + PROGRAM);
        matrix(PRINT, STATEMENT, OUTPUT);
        matrix(IDENTIFIER, STATEMENT, ASSIGNMENT);
        matrix(SEMICOLON, STATEMENT, SEMICOLON);
        matrix(PRINT, OUTPUT, PRINT + " " + PBODY);
        matrix(SEMICOLON, PBODY, SEMICOLON);
        matrix(OPEN, PBODY, EXPRESSION + " " + SEMICOLON);
        matrix(ADD, PBODY, EXPRESSION + " " + SEMICOLON);
        matrix(SUB, PBODY, EXPRESSION + " " + SEMICOLON);
        matrix(IDENTIFIER, PBODY, EXPRESSION + " " + SEMICOLON);
        matrix(NUMERAL, PBODY, EXPRESSION + " " + SEMICOLON);
        matrix(IDENTIFIER, ASSIGNMENT, IDENTIFIER + " " + ASSIGN + " " + EXPRESSION + " " + SEMICOLON);
        matrix(OPEN, EXPRESSION, TERM + " " + EXPRESSION2);
        matrix(ADD, EXPRESSION, TERM + " " + EXPRESSION2);
        matrix(SUB, EXPRESSION, TERM + " " + EXPRESSION2);
        matrix(IDENTIFIER, EXPRESSION, TERM + " " + EXPRESSION2);
        matrix(NUMERAL, EXPRESSION, TERM + " " + EXPRESSION2);
        matrix(ADD, EXPRESSION2, ADDSUB + " " + EXPRESSION2);
        matrix(SUB, EXPRESSION2, ADDSUB + " " + EXPRESSION2);
        matrix(SEMICOLON, EXPRESSION2, EMPTY);
        matrix(ADD, ADDSUB, ADD + " " + TERM);
        matrix(SUB, ADDSUB, SUB + " " + TERM);
        matrix(OPEN,TERM,POW +" "+ TERM2);
        matrix(ADD,TERM,POW +" "+ TERM2);
        matrix(SUB,TERM,POW +" "+ TERM2);
        matrix(IDENTIFIER,TERM,POW +" "+ TERM2);
        matrix(NUMERAL,TERM,POW +" "+ TERM2);
        matrix(MULT,TERM2,POINTOP + " " + TERM2);
        matrix(DIV,TERM2,POINTOP + " " + TERM2);
        matrix(MOD,TERM2,POINTOP + " " + TERM2);
        matrix(SEMICOLON,TERM2,EMPTY);
        matrix(MULT,POINTOP,MULT + " " + POW);
        matrix(DIV,POINTOP,DIV + " " + POW);
        matrix(MOD,POINTOP,MOD + " " + POW);
        matrix(OPEN,POW,Factor + " " + POW2);
        matrix(ADD,POW,Factor + " " + POW2);
        matrix(SUB,POW,Factor + " " + POW2);
        matrix(IDENTIFIER,POW,Factor + " " + POW2);
        matrix(NUMERAL,POW,Factor + " " + POW2);
        matrix(MULT,POW2,EMPTY);
        matrix(DIV,POW2,EMPTY);
        matrix(MOD,POW2,EMPTY);
        matrix(OPEN,POW2,EMPTY);
        matrix(SEMICOLON,POW2,EMPTY);
        matrix(POT,POW2,POT + " " + Factor + POW2);
        matrix(OPEN,Factor, OPEN + " "+ EXPRESSION + " " + CLOSE);
        matrix(ADD,Factor, ADD +" " + Factor);
        matrix(SUB,Factor, SUB +" " + Factor);
        matrix(IDENTIFIER,Factor, IDENTIFIER);
        matrix(NUMERAL,Factor, NUMERAL);





    }
}
