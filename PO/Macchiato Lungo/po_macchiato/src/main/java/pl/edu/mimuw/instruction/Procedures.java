package pl.edu.mimuw.instruction;

import java.util.HashMap;
import java.util.List;

public class Procedures {
    private HashMap<String, Block> blocks;
    private HashMap<String, List<Character>> arguments;
    private HashMap<String, Boolean> declared;

    public Procedures() {
        this.blocks = new HashMap<>();
        this.arguments = new HashMap<>();
        this.declared = new HashMap<>();
    }

    public Procedures(Procedures other) {
        this.blocks = new HashMap<>(other.blocks);
        this.arguments = new HashMap<>(other.arguments);
        this.declared = new HashMap<>(other.declared);
        for (var elem : this.declared.entrySet()) {
            this.declared.put(elem.getKey(), false);
        }
    }

    public boolean ifDeclared(String procedureName) {
        if (!ifLegal(procedureName))
            return false;
        return this.declared.get(procedureName);
    }

    public boolean ifLegal(String procedureName) {
        return this.declared.containsKey(procedureName);
    }

    public boolean isMacchiato(String procedureName) {
        for (var letter : procedureName.toCharArray())
            if (!('a' <= letter && letter <= 'z'))
                return false;
        return true;
    }

    public Block getBlock(String procedureName) {
        return this.blocks.get(procedureName);
    }

    public List<Character> getArguments(String procedureName) {
        return this.arguments.get(procedureName);
    }

    public void addProcedure(String procedureName, List<Character> arguments, Block block) {
        this.arguments.put(procedureName, arguments);
        this.blocks.put(procedureName, block);
        this.declared.put(procedureName, true);
    }

    public HashMap<String, List<Character>> getProcedures() {
        return this.arguments;
    }
}
