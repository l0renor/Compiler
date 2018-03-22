package edu.hm.llukas.prae;

import edu.hm.cs.rs.compiler.toys.base.LexicalError;
import edu.hm.cs.rs.compiler.toys.base.Preprocessor;
import edu.hm.cs.rs.compiler.toys.base.Source;

public class CommentOut implements Preprocessor {
    Source source;

    @Override
    public Source process(Source source) throws LexicalError {
        Source result = new Source();
        int state = 0;
        while (source.hasMore()) {
            char c = source.getNextChar();
            if (c == '/' && state == 0) {
                state = 1;
            }else if(c == '/' && state == 1){
                state = 2;
            }else if(c == '*' && state == 1 ){
                state = 3;
            }else if(c != '/' && state == 1){
                result.append('/');
                result.append(c);
                state = 0;
            }else if(state == 2){
                if(c == '\n'){
                    state = 0;
                    result.append('\n');
                }
            }else if(c == '\n' && state == 3) {
                result.append(c);
            }
            else if(c == '*' && state == 3) {
                state = 4;
            }else if(state == 3){}
            else if(c == '/' && state == 4) {
                state = 0;
                result.append(' ');
            }else if (state == 4){
                if(c == '*'){


                }else {
                    state = 3;
                }
            }
            else  {
                result.append(c);
            }

        }
        if(state == 4 || state == 3){
            throw new LexicalError();
        }
        if(state == 1){
            result.append('/');
        }
        return result;
    }
}
