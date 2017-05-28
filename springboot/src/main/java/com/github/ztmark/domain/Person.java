package com.github.ztmark.domain;

import java.io.Serializable;

/**
 * Author: Mark
 * Date  : 2017/5/27
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 8947784372779385015L;

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
