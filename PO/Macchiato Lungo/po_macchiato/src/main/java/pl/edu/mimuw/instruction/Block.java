package pl.edu.mimuw.instruction;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import pl.edu.mimuw.expression.Expression;

public class Block extends Instruction implements Cloneable {
    private Deque<Instruction> instructions = new ArrayDeque<>();
    private Block parent = null;

    public Block() {
        this.instructionName = "begin block";
    }

    public Block(Block parent) {
        this.instructionName = "begin block";
        this.parent = parent;
    }

    public void addLastInstruction(Instruction instruction) {
        this.instructions.addLast(instruction);
    }

    public void addFirstInstruction(Instruction instruction) {
        this.instructions.addFirst(instruction);
    }

    public Block beginBlock() {
        Block newBlock = new Block(this);
        this.addLastInstruction(newBlock);
        return newBlock;
    }

    public Block endBlock() {
        if (this.parent != null)
            return this.parent;
        return this;
    }

    public Block declare(char varName, Expression expression) {
        this.instructions.add(new Declare(varName, expression));
        return this;
    }

    public Block assign(char varName, Expression expression) {
        this.instructions.add(new Assign(varName, expression));
        return this;
    }

    public Block print(Expression expression) {
        this.instructions.add(new Print(expression));
        return this;
    }

    public Block forBlock(char varName, Expression expression, Block block) {
        this.instructions.add(new ForBlock(varName, expression, block));
        return this;
    }

    public Block ifBlock(Expression expression1, String operation, Expression expression2, Block ifBlock, Block elseBlock) {
        this.instructions.add(new IfBlock(expression1, operation, expression2, ifBlock, elseBlock));
        return this;
    }

    public Block ifBlock(Expression expression1, String operation, Expression expression2, Block ifBlock) {
        this.instructions.add(new IfBlock(expression1, operation, expression2, ifBlock));
        return this;
    }

    public Block declareProcedure(String procedureName, List<Character> arguments, Block block) {
        this.instructions.add(new DeclareProcedure(procedureName, arguments, block));
        return this;
    }

    public Block invokeProcedure(String procedureName, List<Expression> arguments) {
        this.instructions.add(new InvokeProcedure(procedureName, arguments));
        return this;
    }

    public void executeDebug(int displayMode) {
        Program program = new Program(this);
        program.runDebug(displayMode);
    }

    public void executeDebug() {
        Program program = new Program(this);
        program.runDebug();
    }

    public void execute(int displayMode) {
        Program program = new Program(this);
        program.run(displayMode);
    }

    public void execute() {
        Program program = new Program(this);
        program.run();
    }

    @Override
    public void run(Program program) {
        program.newVariables();
        program.newProcedures();

        program.addInstruction(new BlockEnd());

        Iterator<Instruction> reverseIterator = this.instructions.descendingIterator();

        while (reverseIterator.hasNext()) {
            program.addInstruction(reverseIterator.next());
        }
    }

    @Override
    public Block clone() throws CloneNotSupportedException {
        Block cloned = (Block) super.clone();
        cloned.instructions = new ArrayDeque<>(this.instructions);
        return cloned;
    }
}
