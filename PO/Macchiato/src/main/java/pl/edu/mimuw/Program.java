package pl.edu.mimuw;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Program {
    private Deque<Instruction> instructions = new ArrayDeque<>();
    private List<Variables> variables = new ArrayList<>();
    private int displayMode = 1;

    private final String DEBUGGER_SUCCESS = "[Debugger] Program finished successfully";
    private final String DEBBUGER_TOO_FEW_ARGS = "[Debugger] Too few arguments";
    private final String DEBBUGER_TOO_MANY_ARGS = "[Debugger] Too many arguments";
    private final String DEBUGGER_INVALID_ARGS = "[Debugger] Invalid arguments";
    private final String DEBUGGER_TOO_LARGE_LAYER = "[Debugger] Requested layer number is too large";
    private final String DEBUGGER_MODE_CHANGED = "[Debugger] Display mode changed to ";

    public Program(Block block) {
        this.instructions.add(block);
        variables.add(new Variables());
    }

    public void addInstruction(Instruction instruction) {
        this.instructions.addFirst(instruction);
    }

    public Variables getLastVariables() {
        return variables.get(variables.size() - 1);
    }

    public void newVariables() {
        variables.add(new Variables(variables.get(variables.size() - 1)));
    }

    public void deleteVariables() {
        variables.remove(variables.size() - 1);
    }

    public boolean multiEquals(String base, String... other) {
        for (String elem : other) {
            if (base.equals(elem))
                return true;
        }
        return false;
    }

    public int _step() {
        Instruction currentInstruction = instructions.getFirst();
        instructions.removeFirst();

        try {
            currentInstruction.run(this);
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(currentInstruction.toString());
            _display(0);
            return 1;
        }
    }

    public void _display(int layer) {
        if (layer > variables.size() - 2) {
            System.out.println(DEBUGGER_TOO_LARGE_LAYER);
            return;
        }

        Variables currentVariables = variables.get(variables.size() - 1 - layer);

        int rows = (displayMode == 0 ? 4 : 7);
        int cols = (displayMode == 0 ? 7 : 4);
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index < 26) {
                    char varName = (char) ('a' + index);
                    String varValue = (!currentVariables.ifLegal(varName) ? "~"
                        : Integer.toString(currentVariables.getVariableValue(varName)));

                    System.out.printf("%c: %-14s", varName, varValue);
                }
                index++;
            }
            System.out.println();
        }
    }

    public void _displayMode(int displayMode) {
        if (displayMode == 0) {
            this.displayMode = 0;
            System.out.println(DEBUGGER_MODE_CHANGED + "fullscreen");
        } else if (displayMode == 1) {
            this.displayMode = 1;
            System.out.println(DEBUGGER_MODE_CHANGED + "terminal");
        } else {
            System.out.println(DEBUGGER_INVALID_ARGS);
        }
    }

    public void _continue() {
        int res = 0;

        while (!instructions.isEmpty()) {
            res = _step();
            if (res == 1)
                break;
        }

        if (res == 0)
            System.out.println(DEBUGGER_SUCCESS);
    }

    public void run() {
        int res = 0;
        
        while (!instructions.isEmpty()) {
            res = _step();
            if (res == 1)
                break;
        }
    }

    public void runDebug() {
        Scanner scanner = new Scanner(System.in);

        while (!instructions.isEmpty()) {
            String task = scanner.nextLine();
            String[] taskList = task.split("\\s+");

            if (taskList.length == 1) {
                if (multiEquals(taskList[0], "e")) { // ~ Exit
                    break;
                } else if (multiEquals(taskList[0], "c")) { // ~ Continue
                    _continue();
                    break;
                } else if (multiEquals(taskList[0], "s", "d", "m")) {
                    System.out.println(DEBBUGER_TOO_FEW_ARGS);
                } else {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                }
            } else if (taskList.length == 2) {
                if (multiEquals(taskList[0], "e", "c")) {
                    System.out.println(DEBBUGER_TOO_MANY_ARGS);
                    continue;
                }
                if (!multiEquals(taskList[0], "s", "d", "m")) {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                    continue;
                }

                int num;
                try {
                    num = Integer.parseInt(taskList[1]);
                } catch (NumberFormatException e) {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                    continue;
                }

                if (multiEquals(taskList[0], "s")) { // ~ Step
                    int res = 0;
                    for (int i = 0; i < num; i++) {
                        if (instructions.isEmpty())
                            break;
                        res = _step();
                        if (res == 1)
                            break;
                    }
                    
                    if (res == 1)
                        break;
                    else if (instructions.isEmpty())
                        System.out.println(DEBUGGER_SUCCESS);
                    else
                        System.out.println(instructions.getFirst().toString());
                } else if (multiEquals(taskList[0], "d")) { // ~ Display
                    _display(num);
                } else if (multiEquals(taskList[0], "m")) { // ~ Display Mode
                    _displayMode(num);
                }
            } else {
                System.out.println(DEBBUGER_TOO_MANY_ARGS);
            }
        }

        scanner.close();
    }

    public void run(int displayMode) {
        _displayMode(displayMode);
        run();
    }

    public void runDebug(int displayMode) {
        _displayMode(displayMode);
        runDebug();
    }
}