package pl.edu.mimuw.instruction;

import java.util.List;
import java.util.stream.Collectors;

import pl.edu.mimuw.expression.Expression;

public class InvokeProcedure extends Instruction {
    private String procedureName;
    private List<Expression> arguments;


    private final String EXCEPTION_DECLARE = "[Exception] Procedure not declared";
    private final String EXCEPTION_NAME = "[Exception] Invalid procedure name";
    private final String EXCEPTION_ARGUMENTS = "[Exception] Invalid number of arguments";

    public InvokeProcedure(String procedureName, List<Expression> arguments) {
        this.procedureName = procedureName;
        this.arguments = arguments;

        this.instructionName = "call " + procedureName + " ("
                + String.join(", ", arguments.stream().map(Object::toString).collect(Collectors.toList())) + ")";
    }

    @Override
    public void run(Program program) throws Exception {
        Procedures procedures = program.getLastProcedures();

        if (!procedures.isMacchiato(this.procedureName))
            throw new Exception(EXCEPTION_NAME);
        if (!procedures.ifLegal(this.procedureName))
            throw new Exception(EXCEPTION_DECLARE);
        if (this.arguments.size() != procedures.getArguments(this.procedureName).size())
            throw new Exception(EXCEPTION_ARGUMENTS);

        Block currentBlock = procedures.getBlock(this.procedureName).clone();
        List<Character> arguments = procedures.getArguments(this.procedureName);
        int n = arguments.size();

        for (int i = n - 1; i >= 0; i--)
            currentBlock.addFirstInstruction(new Declare(arguments.get(i), this.arguments.get(i)));

        program.addInstruction(currentBlock);
    }
}
