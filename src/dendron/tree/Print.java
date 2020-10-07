package dendron.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dendron.machine.Machine;
import dendron.machine.Machine.Instruction;

public class Print implements ActionNode {

    private ExpressionNode printee;

    public Print(ExpressionNode printee) {
        this.printee = printee;
    }

    @Override
    public void infixDisplay() {
        System.out.print("print ");
        this.printee.infixDisplay();

    }

    @Override
    public List<Instruction> emit() {
        List<Instruction> res = new ArrayList<Instruction>();
        res.addAll(this.printee.emit());
        res.add(new Machine.Print());
        return res;
    }

    @Override
    public void execute(Map<String, Integer> symTab) {
        System.out.println("=== " + this.printee.evaluate(symTab));

    }

}
