/*
 * file: ParseTree.java
 */

package dendron.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dendron.Errors;
import dendron.machine.Machine;

/**
 * Operations that are done on a Dendron code parse tree.
 *
 * THIS CLASS IS UNIMPLEMENTED. All methods are stubbed out.
 *
 * @author YOUR NAME HERE
 */
public class ParseTree {

    private int curr = 0;

    private Program prog = new Program();

    /**
     * Parse the entire list of program tokens. The program is a sequence of actions
     * (statements), each of which modifies something in the program's set of
     * variables. The resulting parse tree is stored internally.
     *
     * @param program the token list (Strings)
     */
    public ParseTree(List<String> program) {

        while (curr < program.size()) {
            if (isCurrValid(program).equals("@") || isCurrValid(program).equals(":=")) {
                prog.addAction(parseAction(program));
            } else {
                Errors.report(Errors.Type.EXTRA_TOKENS,
                        isCurrValid(program) + " is not allowed here. End of statement reached.");
            }
        }
    }

    /**
     * Parse the next action (statement) in the list. (This method is not required,
     * just suggested.)
     *
     * @param program the list of tokens
     * @return a parse tree for the action
     */
    private ActionNode parseAction(List<String> program) {

        if (isCurrValid(program).equals(":=")) {
            this.accept(program);
            return parseAssignment(program);
        } else if (isCurrValid(program).equals("@")) {
            this.accept(program);
            return parsePrint(program);
        } else {
            return null;
        }

    }

    private Print parsePrint(List<String> program) {
        return new Print(parseExpr(program));
    }

    private Assignment parseAssignment(List<String> program) {

        if (!isVariable(program)) {
            Errors.report(Errors.Type.ILLEGAL_VALUE, isCurrValid(program) + " is not a valid identifier.");
        }
        String ident = this.accept(program);

        ExpressionNode rhs = null;

        rhs = this.parseExpr(program);

        Assignment ret = new Assignment(ident, rhs);

        return ret;
    }

    private boolean isVariable(List<String> program) {
        return isCurrValid(program).matches("^[a-zA-Z].*");
    }

    private boolean isConstant(List<String> program) {
        return isCurrValid(program).matches("[-+]?\\d+");
    }

    private Variable parseVariable(List<String> program) {

        return new Variable(this.accept(program));
    }

    private Constant parseConstant(List<String> program) {

        return new Constant(Integer.parseInt(this.accept(program)));
    }

    private BinaryOperation parseBinaryOperator(List<String> program, String opString) {
        this.accept(program);
        return new BinaryOperation(opString, parseExpr(program), parseExpr(program));
    }

    private UnaryOperation parseUnaryOperator(List<String> program, String opString) {
        this.accept(program);
        return new UnaryOperation(opString, parseExpr(program));
    }

    /**
     * Parse the next expression in the list. (This method is not required, just
     * suggested.)
     *
     * @param program the list of tokens
     * @return a parse tree for this expression
     */
    private ExpressionNode parseExpr(List<String> program) {

        String opString = isCurrValid(program);
        if (BinaryOperation.OPERATORS.contains(opString)) {
            return parseBinaryOperator(program, opString);
        } else if (isVariable(program)) {
            return parseVariable(program);
        } else if (isConstant(program)) {
            return parseConstant(program);
        } else if (UnaryOperation.OPERATORS.contains(opString)) {
            return parseUnaryOperator(program, opString);
        } else {
            return null;
        }

    }

    private String accept(List<String> program) {
        isCurrValid(program);
        return program.get(curr++);
    }

    private String isCurrValid(List<String> program) {
        if (curr >= program.size()) {
            Errors.report(Errors.Type.PREMATURE_END, "Unexpected end of input");
            return null;
        } else {
            return program.get(curr);
        }
    }

    /**
     * Print the program the tree represents in a more typical infix style, and with
     * one statement per line.
     *
     * @see dendron.tree.ActionNode#infixDisplay()
     */
    public void displayProgram() {
        this.prog.infixDisplay();
    }

    /**
     * Run the program represented by the tree directly
     *
     * @see dendron.tree.ActionNode#execute(Map)
     */
    public void interpret() {
        Map<String, Integer> table = new HashMap<String, Integer>();

        prog.execute(table);
    }

    /**
     * Build the list of machine instructions for the program represented by the
     * tree.
     *
     * @return the Machine.Instruction list
     * @see Machine.Instruction#execute()
     */
    public List<Machine.Instruction> compile() {

        return this.prog.emit();
    }

}
