package com.company.model.user;

import com.company.model.ModuleType;
import com.company.model.Quest;
import com.company.model.Wallet;
import java.util.List;

public class Student extends User {

    private ModuleType moduleType;
    private Wallet wallet;
    private int experienceLevel;
    private List<Quest> questList;

    public ModuleType getModuleType() {
        return moduleType;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Student(User.Builder<?> builder) {
        super(builder);
    }

    private Student(Builder builder) {
        super(builder);
        this.moduleType = builder.moduleType;
        this.experienceLevel = builder.experienceLevel;
    }

    public static class Builder extends User.Builder<Builder> {
        private ModuleType moduleType;
        private int experienceLevel;

        public Builder() {
        }

        public ModuleType getModuleType() {
            return moduleType;
        }

        public int getExperienceLevel() {
            return experienceLevel;
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
