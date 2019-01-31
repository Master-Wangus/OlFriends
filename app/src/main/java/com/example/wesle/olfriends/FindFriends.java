package com.example.wesle.olfriends;


public class FindFriends {

    private String Name, Age, profileimage;


    public FindFriends(String name, String age, String image) {
        Name = name;
        Age = age;
        profileimage = image;
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

    public String getprofileimage(){return profileimage;}

    public void setprofileimage(String image){profileimage = image;}

}
