package edu.hm.llukas.prae;

import edu.hm.cs.rs.compiler.toys.base.LexicalError;
import edu.hm.cs.rs.compiler.toys.base.Preprocessor;
import edu.hm.cs.rs.compiler.toys.base.Source;

public class CommentOut implements Preprocessor {
    private final Integer zero = 0;
    private final Integer one = 1;
    private final Integer two = 2;
    private final Integer three = 3;
    private final Integer four = 4;


    @Override
    public Source process(Source source) throws LexicalError {
        final Source result = new Source();
        int state = zero;
        while (source.hasMore()) {
            final char c = source.getNextChar();
            if (c == '/' && state == zero) {
                state = one;
            } else if (c == '/' && state == one) {
                state = two;
            } else if (c == '*' && state == one) {
                state = three;
            } else if (c != '/' && state == one) {
                result.append('/');
                result.append(c);
                state = zero;
            } else if (state == two) {
                if (c == '\n') {
                    state = zero;
                    result.append('\n');
                }
            } else if (c == '\n' && state == three) {
                result.append(c);
            } else if (c == '*' && state == three) {
                state = four;
            } else if (state == three) {
            } else if (c == '\n' && state == four) {
                result.append(c);
            } else if (c == '/' && state == four) {
                state = zero;
                result.append(' ');
            } else if (state == four && c != '*') {
                state = three;
            } else if (state == four && c == '*') {
            } else {
                result.append(c);
            }

        }
        if (state == one) {
            result.append('/');
        }
        if (state == four || state == three || state == two) {
            throw new LexicalError();
        }
        return result;
    }
}
