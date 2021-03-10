package com.nunu.NUNU;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class UserInfo {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) //id는 PrimaryKey로 지정해주며 자동으로 생성해줌
    public int id;
    public String username;
    public String greeting;
    public String leftSight;
    public String rightSight;
    public String date;


    @Ignore
    public UserInfo(){

    }


    public UserInfo(String username, String leftSight, String rightSight,String date) {
        this.username=username;
        this.leftSight=leftSight;
        this.rightSight=rightSight;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getUsername() {
        return username;
    }

    public  void setUsername(String username) {
        this.username = username;
    }

    public  String getGreeting() {
        return greeting;
    }

    public  void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public  String getLeftSight() {
        return leftSight;
    }

    public  void setLeftSight(String leftSight) {
        this.leftSight = leftSight;
    }

    public  String getRightSight() {
        return rightSight;
    }

    public  void setRightSight(String rightSight) {
        this.rightSight = rightSight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                "name="+username+
                "leftsight="+leftSight+
                "rightSight="+rightSight+
                "date="+date+
                '}';
    }
}