package com.example.q;

import java.io.Serializable;

    public class Student implements Serializable {
        String name;
        String height;
        String weight;
        String sex;
        String hobby;
        String major;
        String bmi;
        Student(String name,String height,String weight,String sex,String hobby,String major,String bmi){
            this.name = name;
            this.height = height;
            this.weight = weight;
            this.sex = sex;
            this.hobby = hobby;
            this.major = major;
            this.bmi = bmi;
        }

        Student(){
            super();
        }

        public void setname(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public void setheight(String height){
            this.height = height;
        }

        public String getHeight(){
            return height;
        }

        public void setweight(String weight){
            this.weight = weight;
        }

        public String getWeight(){
            return weight;
        }

        public void setsex(String sex){
            this.sex = sex;
        }

        public String getSex(){
            return sex;
        }

        public void sethooby(String hobby){
            this.hobby = hobby;
        }

        public String getHobby(){
            return hobby;
        }

        public void setmajor(String major){
            this.major = major;
        }

        public String getMajor(){
            return major;
        }

        public void setBmi(String bmi){
            this.major = bmi;
        }

        public String getBmi(){
            return bmi;
        }
        public String speak(){
            String info = "姓名："+name+'\n'
                    +"身高："+height+'\n'
                    +"体重："+weight+'\n'
                    +"性别："+sex+'\n'
                    +"爱好："+hobby+'\n'
                    +"专业："+major+'\n'
                    +"体质："+bmi;
            return info;
        }
    }


