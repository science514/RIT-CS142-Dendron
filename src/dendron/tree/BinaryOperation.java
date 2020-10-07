package dendron.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import dendron.Errors;
import dendron.machine.Machine;
import dendron.machine.Machine.Instruction;

public class BinaryOperation implements ExpressionNode {

    public static final String ADD = "+";

    public static final String SUB = "-";

    public static final String MUL = "*";

    public static final String DIV = "/";

    public static final Collection<String> OPERATORS = Collections.unmodifiableSet(new HashSet<String>() {

        private static final long serialVersionUID = -3477982965989043761L;

        {
            add(ADD);
            add(SUB);
            add(MUL);
            add(DIV);
        }
    });

    private String operator;

    private ExpressionNode leftChild;

    private ExpressionNode rightChild;

    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild) {

        if (!OPERATORS.contains(operator)) {
            Errors.report(Errors.Type.ILLEGAL_VALUE, "Operator " + operator + " not valid");
        }

        this.operator = operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public void infixDisplay() {
        System.out.print("(");
        this.leftChild.infixDisplay();
        System.out.print(this.operator);
        this.rightChild.infixDisplay();
        System.out.print(")");

    }

    @Override
    public List<Instruction> emit() {

        List<Instruction> res = new ArrayList<Instruction>();

        res.addAll(this.leftChild.emit());

        res.addAll(this.rightChild.emit());

        switch (this.operator) {
            case ADD: {
                res.add(new Machine.Add());
                break;
            }
            case SUB: {
                res.add(new Machine.Subtract());
                break;
            }
            case MUL: {
                res.add(new Machine.Multiply());
                break;
            }
            case DIV: {
                res.add(new Machine.Divide());
                break;
            }

        }

        return res;
    }

    @Override
    public int evaluate(Map<String, Integer> symTab) {

        if (this.operator.equals(ADD))
            return this.leftChild.evaluate(symTab) + this.rightChild.evaluate(symTab);
        else if (this.operator.equals(SUB))
            return this.leftChild.evaluate(symTab) - this.rightChild.evaluate(symTab);
        else if (this.operator.equals(MUL))
            return this.leftChild.evaluate(symTab) * this.rightChild.evaluate(symTab);
        else if (this.operator.equals(DIV)) {
            if (this.rightChild.evaluate(symTab) == 0) {
                Errors.report(Errors.Type.DIVIDE_BY_ZERO, "Division by 0 is not allowed.");
            }
            return this.leftChild.evaluate(symTab) / this.rightChild.evaluate(symTab);
        } else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, "Operator " + operator + " not valid");
            return 0;
        }

    }

}
