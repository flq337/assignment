package com.example.q.bean;

import java.io.Serializable;

public class Student implements Serializable {
    Long id;
    String name;
    Float height;
    Float weight;
    String sex;
    String hobby;
    String major;
    String bmi;

    public Student(Long id, String name, Float height, Float weight, String sex, String hobby, String major, String bmi) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.hobby = hobby;
        this.major = major;
        this.bmi = bmi;
    }

    Student() {
        super();
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setsex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void sethooby(String hobby) {
        this.hobby = hobby;
    }

    public String getHobby() {
        return hobby;
    }

    public void setmajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setBmi(String bmi) {
        this.major = bmi;
    }

    public String getBmi() {
        return bmi;
    }

    public String speak() {
        return "id：" + id + '\n' +
                "姓名：" + name + '\n'
                + "身高：" + height + '\n'
                + "体重：" + weight + '\n'
                + "性别：" + sex + '\n'
                + "爱好：" + hobby + '\n'
                + "专业：" + major + '\n'
                + "体质：" + bmi;
    }


}


