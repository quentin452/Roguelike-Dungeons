package greymerk.roguelike.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArgumentParser {

    List<String> args;

    public ArgumentParser(String[] args) {
        this.args = new ArrayList<>();
        Collections.addAll(this.args, args);
    }

    public boolean hasEntry(int index) {
        return index < this.args.size();
    }

    public boolean match(int index, String toCompare) {

        if (!this.hasEntry(index)) return false;
        return this.args.get(index).equals(toCompare);
    }

    public String get(int index) {

        if (!this.hasEntry(index)) return null;
        return this.args.get(index);
    }
}
