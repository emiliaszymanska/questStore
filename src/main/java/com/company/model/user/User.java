package com.company.model.user;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.isActive = active;
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
