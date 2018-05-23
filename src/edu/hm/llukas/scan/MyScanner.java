package edu.hm.llukas.scan;

import edu.hm.cs.rs.compiler.toys.base.BaseScanner;

public class MyScanner extends BaseScanner {
    private static String LETTER = "abcdefghijklmnopqrstuvwxyz";
    private static String NUMBER = "0123456789";
    private static String WHITE = " \t\f\n\r";
    public MyScanner(){
        start("start");
        //whitespace
        transition("start",WHITE,"whitespace");
        transition("whitespace",WHITE,"whitespace");
        acceptAndIgnore("whitespace");
        //assign
        transition("start",':',"double");
        transition("double",'=',"assign");
        accept("assign",false);
        //semicolon
        transition("start",';',"semicolon");
        accept("semicolon",false);
        //close
        transition("start",')',"close");
        accept("close",false);
        //mult
        transition("start",'*',"mult");
        accept("mult",false);
        //potenz
        transition("mult",'*',"pot");
        accept("pot",false);
        //close
        transition("start",'/',"div");
        accept("div",false);
        //open
        transition("start",'(',"open");
        accept("open",false);
        //mod
        transition("start",'%',"mod");
        accept("mod",false);
        //sub
        transition("start",'-',"sub");
        accept("sub",false);
        //add
        transition("start",'+',"add");
        accept("add",false);
        //num
        transition("start",NUMBER,"num");
        transition("num",NUMBER,"num");
        accept("num",true);
        //id
        transition("start",except(LETTER,'p'),"id");
        transition("id",LETTER,"id");
        transition("id",NUMBER,"id");
        accept("id",true);
        //print
        transition("start",'p',"p");
        transition("p",'r',"r");
        transition("p",except(LETTER,'r'),"id");
        transition("p",NUMBER,"id");
        transition("r",'i',"i");
        transition("r",except(LETTER,'i'),"id");
        transition("r",NUMBER,"id");
        transition("i",'n',"n");
        transition("i",except(LETTER,'n'),"id");
        transition("i",NUMBER,"id");
        transition("n",'t',"print");
        transition("n",except(LETTER,'t'),"id");
        transition("n",NUMBER,"id");
        accept("print",false);
        transition("print",LETTER,"id");
        transition("print",NUMBER,"id");
        accept("p","id");
        accept("r","id");
        accept("i","id");
        accept("n","id");

    }

    private String except(String s,char c){
        final StringBuilder sb = new StringBuilder(s);
        sb.deleteCharAt(s.indexOf(c));
        return sb.toString();
    }
}
