package com.mark.designpattern.builderpattern;

/**
 * Author: Mark
 * Date  : 2015/3/24
 * Time  : 17:33
 */
public class BuilderPatternDemo {

    public static void main(String[] args) {
        User user = new User.UserBuilder("1", "Mark", "123456")
                .age(23)
                .address("China")
                .gender("Male")
                .build();
        System.out.println(user);
        StringBuilder builder = new StringBuilder("tmp");
        String s = builder.append("what")
                .append(10)
                .append(true)
                .toString();
    }

}
