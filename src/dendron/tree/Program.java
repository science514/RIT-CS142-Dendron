package dendron.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dendron.machine.Machine.Instruction;

public class Program implements ActionNode {

    private ArrayList<ActionNode> nodes;

    public Program() {
        this.nodes = new ArrayList<ActionNode>();
    }

    @Override
    public void infixDisplay() {
        nodes.stream().forEach(n -> {
            n.infixDisplay();
            System.out.println();
        });

    }

    @Override
    public List<Instruction> emit() {
        List<Instruction> res = new ArrayList<Instruction>();

        for (ActionNode n : this.nodes) {
            res.addAll(n.emit());
        }

        return res;
    }

    @Override
    public void execute(Map<String, Integer> symTab) {
        this.nodes.stream().forEach(n -> n.execute(symTab));

    }

    public void addAction(ActionNode newNode) {
        this.nodes.add(newNode);
    }

}
