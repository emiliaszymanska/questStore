package com.company.model.user;

import com.company.model.ModuleType;

public class Student extends User {

    private ModuleType moduleType;
    private int experienceLevel;
    private int balance;

    public ModuleType getModuleType() {
        return moduleType;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public int getBalance() {
        return balance;
    }

    public Student setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
        return this;
    }

    public Student setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public Student setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public Student(User.Builder<?> builder) {
        super(builder);
    }

    private Student(Builder builder) {
        super(builder);
        this.moduleType = builder.moduleType;
        this.experienceLevel = builder.experienceLevel;
        this.balance = builder.balance;
    }

    public static class Builder extends User.Builder<Builder> {
        public int balance;
        private ModuleType moduleType;
        private int experienceLevel;

        public Builder() {
        }

        public int getBalance() {
            return balance;
        }

        public ModuleType getModuleType() {
            return moduleType;
        }

        public int getExperienceLevel() {
            return experienceLevel;
        }

        public Builder withBalance(int balance) {
            this.balance = balance;
            return this;
        }

        public Builder withModuleType(ModuleType moduleType) {
            this.moduleType = moduleType;
            return this;
        }

        public Builder withExperienceLevel(int experienceLevel) {
            this.experienceLevel = experienceLevel;
            return this;
        }

        @Override
        public Student build() {
            return new Student(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", typeId=" + typeId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", experienceLevel=" + experienceLevel +
                ", moduleType=" + moduleType +
                '}';
    }
}
