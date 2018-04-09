import edu.hm.cs.rs.compiler.toys.base.BaseScanner;

public class MyScanner extends BaseScanner {
    public static String LETTER = "abcdefghijklmnopqrstuvw";
    public static String NUMBER = "0123456789";
    public static String WHITE = " \t\f\n\r";
    MyScanner(){
        //LETTER.replace('p','a');
        start("init");

    }
}
