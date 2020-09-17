package com.company.model;

import java.util.HashMap;
import java.util.Map;

public enum LearningModule {

    PROGRAMMING_BASICS(1),
    JAVA_OOP(2),
    WEB_WITH_SQL(3),
    ADVANCED(4);

    private int id;

    LearningModule(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final Map<Integer, LearningModule> lookup = new HashMap<>();

    static {
        for(LearningModule env : LearningModule.values()) {
            lookup.put(env.getId(), env);
        }
    }

    public static LearningModule get(int id) {
        return lookup.get(id);
    }
}
