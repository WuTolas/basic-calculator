package com.wutolas.memory;

import java.util.HashMap;
import java.util.Map;

public class BasicMemory implements Memory {

    private Map<String, Integer> memory = new HashMap<>();

    public BasicMemory() {
        char a;
        for (a = 'a'; a <= 'z'; a++) {
            memory.put(String.valueOf(a), 0);
        }
        memory.put("$", 0);
    }

    @Override
    public int get(String character) {
        return this.memory.get(character);
    }

    @Override
    public int set(String character, int value) {
        this.memory.replace(character, value);
        return value;
    }
}
