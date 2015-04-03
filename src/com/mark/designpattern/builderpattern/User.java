package com.mark.designpattern.builderpattern;

/**
 * Author: Mark
 * Date  : 2015/3/24
 * Time  : 16:31
 */
public class User {


    private final String id;        // required
    private final String username;  // required
    private final String password;  // required
    private String name;      // optional
    private int age;          // optional
    private String gender;    // optional
    private String phone;     // optional
    private String address;   // optional


    private User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.name = builder.name;
        this.age = builder.age;
        this.gender = builder.gender;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class UserBuilder {
        private final String id;
        private final String username;
        private final String password;
        private String name;
        private int age;
        private String gender;
        private String phone;
        private String address;

        public UserBuilder(String id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }


    }
}
