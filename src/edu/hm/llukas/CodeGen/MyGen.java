package edu.hm.llukas.CodeGen;

import edu.hm.cs.rs.compiler.toys.compiler.BaseCodeGenerator;
import edu.hm.cs.rs.compiler.toys.compiler.Code;
import edu.hm.cs.rs.compiler.toys.compiler.Instruction;

public class MyGen extends BaseCodeGenerator {

    public MyGen(Code code) {
        super(code);
        setHtmlOutput(true);
        verbose(true);

        up("output", ($, message) -> code.append(new Instruction("print", message)));

        up("numeral", (node, message) -> code.append(new Instruction("push", node.getIntAttribute(), message)));

        up("div", ($, message) -> code.append(new Instruction("div", message)));

        up("add", ($, message) -> code.append(new Instruction("add", message)));

        up("sub", ($, message) -> code.append(new Instruction("sub", message)));

        up("mult", ($, message) -> code.append(new Instruction("mult", message)));

        up("pot", (node, message) -> {
            int expo = node.getChild(1).getIntAttribute();
            int base = node.getChild(0).getIntAttribute();
            if (expo == 0) {
                code.append(new Instruction("pop", message));
                code.append(new Instruction("pop", message));
                code.append(new Instruction("push", 1, message));
            } else {
                code.append(new Instruction("pop", message));
                for (int i = 1; i < expo; i++) {
                    code.append(new Instruction("push", base, message));
                    code.append(new Instruction("mult", message));
                }
            }
        });
        up("mod", (node, message) -> {
            code.append(new Instruction("dup", -2, message));
            code.append(new Instruction("dup", -2, message));
            code.append(new Instruction("div", message));
            code.append(new Instruction("mult", message));
            code.append(new Instruction("sub", message));


        });

        down("neg", (node, message) -> code.append(new Instruction("push", 0, message)));
        up("neg", (node, message) -> code.append(new Instruction("sub", message)));


    }
}
