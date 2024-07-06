package pl.edu.mimuw.instruction;

import java.util.List;
import java.util.stream.Collectors;

public class DeclareProcedure extends Instruction {
    private String procedureName;
    private List<Character> arguments;
    private Block block;

    private final String EXCEPTION_DECLARE = "[Exception] Procedure already declared";
    private final String EXCEPTION_NAME = "[Exception] Invalid procedure name";
    private final String EXCEPTION_ARGUMENTS = "[Exception] Repeating arguments";

    public DeclareProcedure(String procedureName, List<Character> arguments, Block block) {
        this.procedureName = procedureName;
        this.arguments = arguments;
        this.block = block;

        this.instructionName = "def " + procedureName + " ("
                + String.join(", ", arguments.stream().map(Object::toString).collect(Collectors.toList())) + ")";
    }

    @Override
    public void run(Program program) throws Exception {
        Procedures procedures = program.getLastProcedures();

        if (!procedures.isMacchiato(this.procedureName))
            throw new Exception(EXCEPTION_NAME);
        if (procedures.ifDeclared(this.procedureName))
            throw new Exception(EXCEPTION_DECLARE);

        int n = arguments.size();

        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (this.arguments.get(i) == this.arguments.get(j))
                    throw new Exception(EXCEPTION_ARGUMENTS);

        procedures.addProcedure(this.procedureName, this.arguments, this.block);
    }

}
