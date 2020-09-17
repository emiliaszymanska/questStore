package com.company.model.user;

import com.company.model.LearningModule;

public class Student extends User {

    private LearningModule learningModule;
    private int experienceLevel;

    public LearningModule getLearningModule() {
        return learningModule;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setLearningModule(LearningModule learningModule) {
        this.learningModule = learningModule;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Student(User.Builder<?> builder) {
        super(builder);
    }

    private Student(Builder builder) {
        super(builder);
        this.learningModule = builder.learningModule;
        this.experienceLevel = builder.experienceLevel;
    }

    public static class Builder extends User.Builder<Builder> {
        private LearningModule learningModule;
        private int experienceLevel;

        public Builder() {
        }

        public LearningModule getLearningModule() {
            return learningModule;
        }

        public int getExperienceLevel() {
            return experienceLevel;
        }

        public Builder withLearningModule(LearningModule learningModule) {
            this.learningModule = learningModule;
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
                ", learningModule=" + learningModule +
                '}';
    }
}
