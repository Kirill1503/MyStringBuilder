package hw_1;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStringBuilder {

    private final StringBuilder stringBuilder;
    private final Deque<Memento> history = new ArrayDeque<>();

    public MyStringBuilder() {
        this.stringBuilder = new StringBuilder();
        save();
    }

    public MyStringBuilder(String string) {
        this.stringBuilder = new StringBuilder(string);
        save();
    }

    private void save() {
        history.push(new Memento(stringBuilder.toString()));
    }

    public void append(String string) {
        save();
        stringBuilder.append(string);
    }


    public void undo() {
        if (history.isEmpty()) {
            throw new IllegalStateException("No states to undo");
        } else {
            Memento last = history.pop();
            stringBuilder.setLength(0);
            stringBuilder.append(last.getState());
        }
    }

    private static class Memento {
        private final String state;

        public Memento(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }

    @Override
    public String toString() {
        return "HW_1.MyStringBuilder = " + stringBuilder;
    }
}
