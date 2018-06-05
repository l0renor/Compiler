package edu.hm.llukas.ast;

import edu.hm.cs.rs.compiler.toys.base.SemanticError;
import edu.hm.cs.rs.compiler.toys.base.Tree;
import edu.hm.cs.rs.compiler.toys.base.TreeWalker;
import edu.hm.cs.rs.compiler.toys.compiler.ASTGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class myAST extends TreeWalker implements ASTGenerator {
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
    final String IDENTIFIER = "identifier";
    final String NUMERAL = "numeral";
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
    public final Stack<Tree> treeStack = new Stack<Tree>();

    public myAST() {
        up(NUMERAL, node -> {
            treeStack.push(node);
        });
        up(IDENTIFIER, node -> {
            treeStack.push(node);
        });
        up(OUTPUT, node -> {
            if(node.getChild(1).numberOfChildren() ==1){
                return;
            }
            Tree astElement = treeStack.pop();
            Tree out = new Tree("output");
            out.addChild(astElement);

            treeStack.push(out);
        });

        up(ADDSUB, node -> {
            Tree node2 = new Tree(node.getChild(0).getType());
            Tree op1 = treeStack.pop();
            Tree op2 = treeStack.pop();
            node2.addChild(op1);
            node2.addChild(op2);

            treeStack.push(node2);
        });

        up(POINTOP, node -> {
            Tree node2 = new Tree(node.getChild(0).getType());
            Tree op1 = treeStack.pop();
            Tree op2 = treeStack.pop();
            node2.addChild(op1);
            node2.addChild(op2);
            treeStack.push(node2);
        });

        up(POW2, node -> {

            if (node.numberOfChildren() != 0 && node.getChild(0).getType().equals(POT)) {
                Tree node2 = new Tree(POT);
                Tree op1 = treeStack.pop();
                Tree op2 = treeStack.pop();
                node2.addChild(op2);
                node2.addChild(op1);
                treeStack.push(node2);
            }

        });

        up(Factor, node -> {

            if (node.getChild(0).getType().equals(SUB)) {
                Tree node2 = new Tree("neg");
                Tree op1 = treeStack.pop();
                node2.addChild(op1);
                treeStack.push(node2);
            }

        });

        up(ASSIGNMENT, node -> {
            Tree id = treeStack.pop();
            Tree expression = treeStack.pop();
            Tree node2 = new Tree("assignment");
            node2.addChild(expression);
            node2.addChild(id);
            treeStack.push(node2);


        });
        up(PROGRAM, node -> {
            if (node.getParent() != null) {
                return;
            }

            Tree node2 = new Tree("program");

            List<Tree> children = new ArrayList<>();
            int size = treeStack.size();
            for (int i = 0; i < size; i++) {
                children.add(treeStack.pop());
            }
            for (Tree child : children) {
                node2.add1stChild(child);
            }
            treeStack.push(node2);


        });


    }


    @Override
    public Tree analyze(Tree tree) throws SemanticError {
        walk(tree);
        return treeStack.peek();
    }
}
