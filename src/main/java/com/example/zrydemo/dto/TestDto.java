package com.example.zrydemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestDto {
    @JsonProperty("user_name")
    private String userName;
    private String pwd;

    public TestDto() {
    }

    public TestDto(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
