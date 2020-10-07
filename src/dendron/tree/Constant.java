package dendron.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dendron.machine.Machine;
import dendron.machine.Machine.Instruction;

public class Constant implements ExpressionNode {

    private int value;

    public Constant(int value) {
        this.value = value;
    }

    @Override
    public void infixDisplay() {
        System.out.print(this.value);

    }

    @Override
    public List<Instruction> emit() {
        List<Instruction> res = new ArrayList<Instruction>();

        res.add(new Machine.PushConst(this.value));

        return res;
    }

    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return this.value;
    }

}
