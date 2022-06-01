package net.xzh.mq.domain;

import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = 412718673804689450L;
    
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
