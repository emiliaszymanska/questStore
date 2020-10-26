package com.company.model.user;

import java.util.Objects;

public abstract class User {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected int typeId;
    protected String phoneNumber;
    protected String email;
    protected String password;
    protected boolean isActive;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setTypeId(int typeId) {
        this.typeId = typeId;
        return this;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }

    public User(Builder<?> builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.typeId = builder.typeId;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.password = builder.password;
        this.isActive = builder.isActive;
    }

    abstract static class Builder<T extends Builder<T>> {
        private int id;
        private String firstName;
        private String lastName;
        private int typeId;
        private String phoneNumber;
        private String email;
        private String password;
        private boolean isActive;

        public T withId(int id) {
            this.id = id;
            return self();
        }

        public T withFirstName(String firstName) {
            this.firstName = firstName;
            return self();
        }

        public T withLastName(String lastName) {
            this.lastName = lastName;
            return self();
        }

        public T withTypeId(int typeId) {
            this.typeId = typeId;
            return self();
        }

        public T withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return self();
        }

        public T withEmail(String email) {
            this.email = email;
            return self();
        }

        public T withPassword(String password) {
            this.password = password;
            return self();
        }

        public T withIsActive(boolean isActive) {
            this.isActive = isActive;
            return self();
        }

        abstract User build();
        protected abstract T self();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                typeId == user.typeId &&
                isActive == user.isActive &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, typeId, phoneNumber, email, password, isActive);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", typeId=" + typeId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
