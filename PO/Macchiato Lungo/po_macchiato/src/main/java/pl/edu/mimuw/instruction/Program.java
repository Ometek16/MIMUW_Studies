package pl.edu.mimuw.instruction;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    private Deque<Instruction> instructions = new ArrayDeque<>();
    private List<Variables> variables = new ArrayList<>();
    private List<Procedures> procedures = new ArrayList<>();
    private int displayMode = 1;

    private final String DEBUGGER_SUCCESS = "[Debugger] Program finished successfully";
    private final String DEBUGGER_INVALID_ARGS = "[Debugger] Invalid arguments";
    private final String DEBUGGER_MODE_CHANGED = "[Debugger] Display mode changed to ";
    private final String DEBUGGER_INVALID_WRITE = "[Debugger] An error occured while writing to the file";
    private final String DEBUGGER_WRITE_SUCCESS = "[Debugger] Dump finished successfully.";
    private final String sep = "===== ===== =====";

    public Program(Block block) {
        this.variables.add(new Variables());
        this.procedures.add(new Procedures());
        this.instructions.add(block);
    }

    public void addInstruction(Instruction instruction) {
        this.instructions.addFirst(instruction);
    }

    public Variables getLastVariables() {
        return this.variables.get(this.variables.size() - 1);
    }

    public Procedures getLastProcedures() {
        return this.procedures.get(this.procedures.size() - 1);
    }

    public void newVariables() {
        this.variables.add(new Variables(getLastVariables()));
    }

    public void newProcedures() {
        this.procedures.add(new Procedures(getLastProcedures()));
    }

    public void removeVariables() {
        this.variables.remove(this.variables.size() - 1);
    }

    public void removeProcedures() {
        this.procedures.remove(this.procedures.size() - 1);
    }

    private int _step() {
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

    private void _display(int layer) {
        if (layer > variables.size() - 2) {
            System.out.println(DEBUGGER_INVALID_ARGS);
            return;
        }

        Variables currentVariables = variables.get(variables.size() - 1 - layer);

        int rows = (displayMode == 0 ? 4 : 7);
        int cols = (displayMode == 0 ? 7 : 4);
        int index = 0;

        System.out.println(sep + " VARIABLES " + sep);

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

    private void _displayMode(int displayMode) {
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

    private void _continue() {
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
            String[] taskList = task.split("(?<!\\\\)\\s+");

            // & EXIT
            if (taskList[0].equals("e")) {
                if (taskList.length != 1) {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                    continue;
                }
                break;
            }
            // & CONTINUE
            else if (taskList[0].equals("c")) {
                if (taskList.length != 1) {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                    continue;
                }
                _continue();
                break;
            }
            // & STEP num
            else if (taskList[0].equals("s")) {
                if (taskList.length != 2) {
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

                if (num <= 0) {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                    continue;
                }

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
            }
            // & DISPLAY num
            else if (taskList[0].equals("d")) {
                if (taskList.length != 2) {
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

                if (num < 0) {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                    continue;
                }

                _display(num);
            }
            // & DUMP file
            else if (taskList[0].equals("m")) {
                if (taskList.length != 2) {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                    continue;
                }

                String filePath = taskList[1].replaceAll("\\\\ ", " ");

                File file = new File(filePath);

                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

                    bufferedWriter.write(sep + " VARIABLES " + sep);
                    bufferedWriter.newLine();

                    Variables currentVariables = this.getLastVariables();

                    int rows = (displayMode == 0 ? 4 : 7);
                    int cols = (displayMode == 0 ? 7 : 4);
                    int index = 0;

                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            if (index < 26) {
                                char varName = (char) ('a' + index);
                                String varValue = (!currentVariables.ifLegal(varName) ? "~"
                                        : Integer.toString(currentVariables.getVariableValue(varName)));

                                String formattedString = String.format("%c: %-14s", varName, varValue);
                                bufferedWriter.write(formattedString);
                            }
                            index++;
                        }
                        bufferedWriter.newLine();
                    }

                    bufferedWriter.write(sep + " PROCEDURES " + sep);
                    bufferedWriter.newLine();

                    Procedures procedures = this.getLastProcedures();
                    HashMap<String, List<Character>> arguments = procedures.getProcedures();

                    for (var entry : arguments.entrySet()) {
                        String instructionName = entry.getKey() + " ("
                                + String.join(", ",
                                        arguments.get(entry.getKey()).stream().map(Object::toString)
                                                .collect(Collectors.toList()))
                                + ")";
                        bufferedWriter.write(instructionName);
                        bufferedWriter.newLine();
                    }

                    bufferedWriter.close();
                    System.out.println(DEBUGGER_WRITE_SUCCESS);
                } catch (IOException e) {
                    System.out.println(DEBUGGER_INVALID_WRITE);
                    continue;
                }
            }
            // & DISPLAY_MODE num
            else if (taskList[0].equals("v")) {
                if (taskList.length != 2) {
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
                _displayMode(num);
            }
            // & LOCAL_DUMP num
            else if (taskList[0].equals("x")) {
                if (taskList.length != 1) {
                    System.out.println(DEBUGGER_INVALID_ARGS);
                    continue;
                }
                for (var instruction : this.instructions) {
                    System.out.println("\t" + instruction);
                }
            }
            // & INVALID
            else {
                System.out.println(DEBUGGER_INVALID_ARGS);
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