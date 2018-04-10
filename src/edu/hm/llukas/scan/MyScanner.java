package edu.hm.llukas.scan;

import edu.hm.cs.rs.compiler.toys.base.BaseScanner;

public class MyScanner extends BaseScanner {
    private static String LETTER = "abcdefghijklmnopqrstuvwxyz";
    private static String NUMBER = "0123456789";
    private static String WHITE = " \t\f\n\r";
    MyScanner(){
        start("start");
        //whitespace
        transition("start",WHITE,"whitespace");
        transition("whitespace",WHITE,"whitespace");
        acceptAndIgnore("whitespace");
        //assign
        transition("start",':',"double");
        transition("double",'=',"assign");
        accept("assign",true);
        //semicolon
        transition("start",';',"semicolon");
        accept("semicolon",true);
        //close
        transition("start",')',"close");
        accept("close",true);
        //open
        transition("start",'(',"open");
        accept("open",true);
        //mod
        transition("start",'%',"mod");
        accept("mod",true);
        //sub
        transition("start",'-',"sub");
        accept("sub",true);
        //add
        transition("start",'+',"add");
        accept("add",true);
        //numeral
        transition("start",NUMBER,"numeral");
        transition("numeral",NUMBER,"numeral");
        accept("numeral",true);
        //identifier
        transition("start",except(LETTER,'p'),"identifier");
        transition("identifier",LETTER,"identifier");
        transition("identifier",NUMBER,"identifier");
        accept("identifier",true);
        //print
        transition("start",'p',"p");
        transition("p",'r',"r");
        transition("p",except(LETTER,'r'),"identifier");
        transition("p",NUMBER,"identifier");
        transition("r",'i',"i");
        transition("r",except(LETTER,'i'),"identifier");
        transition("r",NUMBER,"identifier");
        transition("i",'n',"n");
        transition("i",except(LETTER,'n'),"identifier");
        transition("i",NUMBER,"identifier");
        transition("n",'t',"print");
        transition("n",except(LETTER,'t'),"identifier");
        transition("t",NUMBER,"identifier");
        accept("print",true);
        transition("print",LETTER,"identifier");
        transition("print",NUMBER,"identifier");

    }

    private String except(String s,char c){
        final StringBuilder sb = new StringBuilder(s);
        sb.deleteCharAt(s.indexOf(c));
        return sb.toString();
    }
}
