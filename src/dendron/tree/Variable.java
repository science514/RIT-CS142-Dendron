package dendron.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dendron.Errors;
import dendron.machine.Machine;
import dendron.machine.Machine.Instruction;

public class Variable implements ExpressionNode {

    private String name;

    public Variable(String name) {

        this.name = name;

    }

    @Override
    public void infixDisplay() {
        System.out.print(this.name);

    }

    @Override
    public List<Instruction> emit() {
        // TODO Auto-generated method stub
        List<Instruction> res = new ArrayList<Instruction>();
        res.add(new Machine.Load(this.name));

        return res;
    }

    @Override
    public int evaluate(Map<String, Integer> symTab) {

        if (symTab.get(this.name) == null) {
            Errors.report(Errors.Type.UNINITIALIZED, this.name + " is not initialized.");
        } else {
            return symTab.get(this.name);
        }

        return -1;
    }

}
