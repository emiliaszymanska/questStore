package com.company.model;

import java.util.HashMap;
import java.util.Map;

public enum ArtifactType {

    RELATED_TO_STUDENTS(1),
    RELATED_TO_MENTORS(2),
    RELATED_TO_THE_TEACHING(3);

    private int id;

    ArtifactType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final Map<Integer, ArtifactType> lookup = new HashMap<>();

    static {
        for (ArtifactType env : ArtifactType.values()) {
            lookup.put(env.getId(), env);
        }
    }

    public static ArtifactType get(int id) {
        return lookup.get(id);
    }
}
