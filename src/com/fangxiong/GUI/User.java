package com.fangxiong.GUI;

import java.io.Serializable;
import java.util.Calendar;

public class User implements Serializable
{
    private String Account;
    private String Password;
    private Calendar Assign_Date;

    public User(String account, String password, Calendar assign_Date) {
        Account = account;
        Password = password;
        Assign_Date = assign_Date;
    }

    public Calendar getAssign_Date() {
        return Assign_Date;
    }

    public void setAssign_Date(Calendar assign_Date) {
        Assign_Date = assign_Date;
    }

    public String getAccount() {
        return Account;
    }


    public String getPassword() {
        return Password;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
