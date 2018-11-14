package com.example.wesle.olfriends;

public class FindFriends {

    private String Name, Age;


    public FindFriends(String name, String age) {
        Name = name;
        Age = age;
    }

    public FindFriends() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }
}
