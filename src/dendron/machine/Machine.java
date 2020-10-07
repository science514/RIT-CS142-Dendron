/*
 * file: Machine.java
 */

package dendron.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import dendron.Errors;

/**
 * An abstraction of a computing machine that reads instructions and executes
 * them. It has an instruction set, a symbol table for variables (instead of
 * general-purpose memory), and a value stack on which calculations are
 * performed.
 *
 * (Everything is static to avoid the need to master the subtleties of nested
 * class instantiation or to pass the symbol table and stack into every
 * instruction when it executes.)
 *
 * THIS CLASS IS INCOMPLETE. The student must add code to it.
 *
 * @author James Heliotis
 * @author YOUR NAME HERE
 */
public class Machine {

    /** Do not instantiate this class. */
    private Machine() {
    }

    public static interface Instruction {
        /**
         * Run this instruction on the Machine, using the Machine's value stack and
         * symbol table.
         */
        void execute();

        /**
         * Show the instruction using text so it can be understood by a person.
         *
         * @return a short string describing what this instruction will do
         */
        @Override
        String toString();
    }

    private static Map<String, Integer> table = null;
    private static Stack<Integer> stack = null;

    /**
     * Reset the Machine to a pristine state.
     *
     * @see Machine#execute
     */
    private static void reset() {
        stack = new Stack<>();
        table = new HashMap<>();
    }

    /**
     * Generate a listing of a program on standard output by calling the toString()
     * method on each instruction contained therein, in order.
     *
     * @param program the list of instructions in the program
     */
    public static void displayInstructions(List<Machine.Instruction> program) {
        System.out.println("\nCompiled code:");
        for (Machine.Instruction instr : program) {
            System.out.println(instr);
        }
        System.out.println();
    }

    /**
     * Run a "compiled" program by executing in order each instruction contained
     * therein. Report on the final size of the stack (should normally be empty) and
     * the contents of the symbol table.
     *
     * @param program a list of Machine instructions
     */
    public static void execute(List<Instruction> program) {
        reset();
        System.out.println("Executing compiled code...");
        for (Instruction instr : program) {
            instr.execute();
        }
        System.out.println("Machine: execution ended with " + stack.size() + " items left on the stack.");
        System.out.println();
        Errors.dump(table);
    }

    /**
     * The ADD instruction
     */
    public static class Add implements Instruction {
        /**
         * Run the microsteps for the ADD instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 + op2);
        }

        /**
         * Show the ADD instruction as plain text.
         *
         * @return "ADD"
         */
        @Override
        public String toString() {
            return "ADD";
        }
    }

    /**
     * The STORE instruction
     */
    public static class Store implements Instruction {
        /** stores name of target variable */
        private String name;

        /**
         * Create a STORE instruction
         *
         * @param ident the name of the target variable
         */
        public Store(String ident) {
            this.name = ident;
        }

        /**
         * Run the microsteps for the STORE instruction.
         */
        @Override
        public void execute() {
            table.put(this.name, stack.pop());
        }

        /**
         * Show the STORE instruction as plain text.
         *
         * @return "STORE" followed by the target variable name
         */
        @Override
        public String toString() {
            return "STORE " + this.name;
        }
    }

    //
    // ENTER YOUR CODE FOR THE OTHER INSTRUCTION CLASSES HERE.
    //

    /**
     * The LOAD instruction
     */
    public static class Load implements Instruction {
        /** stores name of target variable */
        private String name;

        /**
         * Create a LOAD instruction
         *
         * @param ident the name of the target variable
         */
        public Load(String ident) {
            this.name = ident;
        }

        /**
         * Run the microsteps for the LOAD instruction.
         */
        @Override
        public void execute() {
            stack.push(table.get(this.name));
        }

        /**
         * Show the LOAD instruction as plain text.
         *
         * @return "LOAD" followed by the target variable name
         */
        @Override
        public String toString() {
            return "LOAD " + this.name;
        }
    }

    /**
     * The SUB instruction
     */
    public static class Subtract implements Instruction {
        /**
         * Run the microsteps for the SUB instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 - op2);
        }

        /**
         * Show the SUB instruction as plain text.
         *
         * @return "SUB"
         */
        @Override
        public String toString() {
            return "SUB";
        }
    }

    /**
     * The MUL instruction
     */
    public static class Multiply implements Instruction {
        /**
         * Run the microsteps for the MUL instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 * op2);
        }

        /**
         * Show the MUL instruction as plain text.
         *
         * @return "MUL"
         */
        @Override
        public String toString() {
            return "MUL";
        }
    }

    /**
     * The Div instruction
     */
    public static class Divide implements Instruction {
        /**
         * Run the microsteps for the Div instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 / op2);
        }

        /**
         * Show the Div instruction as plain text.
         *
         * @return "Div"
         */
        @Override
        public String toString() {
            return "Div";
        }
    }

    /**
     * The NEG instruction
     */
    public static class Negate implements Instruction {
        /**
         * Run the microsteps for the NEG instruction.
         */
        @Override
        public void execute() {
            int op1 = stack.pop();
            stack.push(-op1);
        }

        /**
         * Show the NEG instruction as plain text.
         *
         * @return "NEG"
         */
        @Override
        public String toString() {
            return "NEG";
        }
    }

    /**
     * The NEG instruction
     */
    public static class SquareRoot implements Instruction {
        /**
         * Run the microsteps for the SQRT instruction.
         */
        @Override
        public void execute() {
            int op1 = stack.pop();
            stack.push((int) Math.sqrt(op1));
        }

        /**
         * Show the SQRT instruction as plain text.
         *
         * @return "SQRT"
         */
        @Override
        public String toString() {
            return "SQRT";
        }
    }

    /**
     * The PUSH instruction
     */
    public static class PushConst implements Instruction {
        /** stores name of target variable */
        private int val;

        /**
         * Create a PUSH instruction
         *
         * @param ident the name of the target variable
         */
        public PushConst(int val) {
            this.val = val;
        }

        /**
         * Run the microsteps for the PUSH instruction.
         */
        @Override
        public void execute() {
            stack.push(this.val);
        }

        /**
         * Show the PUSH instruction as plain text.
         *
         * @return "PUSH" followed by the target variable name
         */
        @Override
        public String toString() {
            return "PUSH " + this.val;
        }
    }

    /**
     * The Print instruction
     */
    public static class Print implements Instruction {

        /**
         * Run the microsteps for the Print instruction.
         */
        @Override
        public void execute() {
            System.out.println("*** " + stack.pop());
        }

        /**
         * Show the Print instruction as plain text.
         *
         * @return "Print" followed by the target variable name
         */
        @Override
        public String toString() {
            return "Print";
        }
    }

}
