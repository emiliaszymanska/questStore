package com.company.model;

public class Artifact {

    private int id;
    private String name;
    private String description;
    private int price;
    private ArtifactType type;
    private boolean isGroup;

    public Artifact(int id, String name, String description, int price, ArtifactType type, boolean isGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.isGroup = isGroup;
    }

    public Artifact() {
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

    public int getPrice() {
        return price;
    }

    public ArtifactType getType() {
        return type;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public Artifact setId(int id) {
        this.id = id;
        return this;
    }

    public Artifact setName(String name) {
        this.name = name;
        return this;
    }

    public Artifact setDescription(String description) {
        this.description = description;
        return this;
    }

    public Artifact setPrice(int price) {
        this.price = price;
        return this;
    }

    public Artifact setType(ArtifactType type) {
        this.type = type;
        return this;
    }

    public Artifact setGroup(boolean group) {
        isGroup = group;
        return this;
    }

    @Override
    public String toString() {
        return "Artifact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", isGroup=" + isGroup +
                '}';

    }
}
