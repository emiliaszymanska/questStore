package com.company.model;

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
}
