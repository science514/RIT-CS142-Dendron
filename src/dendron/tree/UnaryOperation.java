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

public class UnaryOperation implements ExpressionNode {

    public static final String NEG = "_";

    public static final String SQRT = "#";

    public static final Collection<String> OPERATORS = Collections.unmodifiableSet(new HashSet<String>() {

        private static final long serialVersionUID = -5150212521292508822L;

        {
            add(NEG);
            add(SQRT);
        }
    });

    private String operator;
    private ExpressionNode expr;

    public UnaryOperation(String operator, ExpressionNode expr) {
        this.operator = operator;
        this.expr = expr;
    }

    @Override
    public void infixDisplay() {
        System.out.print(this.operator);
        this.expr.infixDisplay();

    }

    @Override
    public List<Instruction> emit() {
        // TODO Auto-generated method stub
        List<Instruction> res = new ArrayList<Instruction>();

        res.addAll(this.expr.emit());

        switch (this.operator) {
            case NEG: {
                res.add(new Machine.Negate());
                break;
            }
            case SQRT: {
                res.add(new Machine.SquareRoot());
                break;
            }
        }

        return res;
    }

    @Override
    public int evaluate(Map<String, Integer> symTab) {

        if (this.operator.equals(NEG))
            return -this.expr.evaluate(symTab);
        else if (this.operator.equals(SQRT))
            return (int) Math.sqrt(this.expr.evaluate(symTab));
        else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, "Operator " + operator + " not valid");
            return 0;
        }
    }

}
