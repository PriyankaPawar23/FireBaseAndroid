package com.android.task_fairshare;

public class Contact {
    private String Name;
    private String Number;
    private String userId;

    public Contact() {
        //empty constructor needed
    }
    public Contact(String Name, String Number, String userId) {
        this.Name = Name;
        this.Number = Number;
        this.userId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
