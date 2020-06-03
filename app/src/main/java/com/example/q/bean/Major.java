package com.example.q.bean;

import java.io.Serializable;

public class Major implements Serializable {

    Long majorId;
    String major;

    public Major(Long majorId, String major) {
        this.majorId = majorId;
        this.major = major;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
