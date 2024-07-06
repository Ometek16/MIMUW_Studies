package pl.edu.mimuw;

import java.util.concurrent.atomic.AtomicInteger;

public class Variables {
    private AtomicInteger[] values = new AtomicInteger[26];
    private boolean declared[] = new boolean[26];

    public Variables() {
        for (int i = 0; i < 26; i++) {
            this.declared[i] = false;
        }
    }

    public Variables(Variables other) {
        for (int i = 0; i < 26; i++) {
            this.values[i] = other.values[i];
            this.declared[i] = false;
        }
    }

    public int getVariableValue(char varName) {
        return values[varName - 'a'].get();
    }

    public boolean ifDeclared(char varName) {
        return declared[varName - 'a'];
    }

    public boolean ifLegal(char varName) {
        return values[varName - 'a'] != null;
    }

    public boolean isMacchiato(char varName) {
        return 'a' <= varName && varName <= 'z';
    }

    public void newVariable(char varName, int varValue) {
        this.declared[varName - 'a'] = true;
        this.values[varName - 'a'] = new AtomicInteger(varValue);
    }

    public void setVariable(char varName, int varValue) {
        this.values[varName - 'a'].set(varValue);
    }
}
