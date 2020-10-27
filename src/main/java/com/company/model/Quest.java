package com.company.model;

import java.util.Objects;

public class Quest {

    private int id;
    private String name;
    private String description;
    private int reward;
    private int experience;
    private QuestType type;

    public Quest(int id, String name, String description, int reward, int experience, QuestType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.experience = experience;
        this.type = type;
    }
  
    public Quest() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getReward() {
        return reward;
    }

    public int getExperience() {
        return experience;
    }

    public QuestType getType() {
        return type;
    }

    public Quest setId(int id) {
        this.id = id;
        return this;
    }

    public Quest setName(String name) {
        this.name = name;
        return this;
    }

    public Quest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Quest setReward(int reward) {
        this.reward = reward;
        return this;
    }

    public Quest setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public Quest setType(QuestType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                ", experience=" + experience +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Quest quest = (Quest) o;
        return this.id == quest.id &&
                this.reward == quest.reward &&
                this.experience == quest.experience &&
                Objects.equals(this.name, quest.name) &&
                Objects.equals(this.description, quest.description) &&
                this.type == quest.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.description, this.reward, this.experience, this.type);
    }
}
