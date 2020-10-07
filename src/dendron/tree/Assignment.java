package dendron.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dendron.machine.Machine;
import dendron.machine.Machine.Instruction;

public class Assignment implements ActionNode {

    private String ident;
    private ExpressionNode rhs;

    public Assignment(String ident, ExpressionNode rhs) {
        this.ident = ident;
        this.rhs = rhs;
    }

    @Override
    public void infixDisplay() {
        System.out.print(this.ident);
        System.out.print(":=");
        this.rhs.infixDisplay();

    }

    @Override
    public List<Instruction> emit() {
        List<Instruction> res = new ArrayList<Instruction>();

        res.addAll(this.rhs.emit());
        res.add(new Machine.Store(this.ident));
        return res;
    }

    @Override
    public void execute(Map<String, Integer> symTab) {
        symTab.put(this.ident, this.rhs.evaluate(symTab));

    }

}
