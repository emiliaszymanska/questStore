package com.company.model;

import java.util.HashMap;
import java.util.Map;

public enum ModuleType {

    PROGRAMMING_BASICS(1),
    JAVA_OOP(2),
    WEB_WITH_SQL(3),
    ADVANCED(4);

    private int id;

    ModuleType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final Map<Integer, ModuleType> lookup = new HashMap<>();

    static {
        for(ModuleType moduleType : ModuleType.values()) {
            lookup.put(moduleType.getId(), moduleType);
        }
    }

    public static ModuleType get(int id) {
        return lookup.get(id);
    }
}
