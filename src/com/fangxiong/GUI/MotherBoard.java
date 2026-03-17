package com.fangxiong.GUI;

import java.io.Serializable;

public class MotherBoard implements Serializable
{
    private String MotherBoard_Band;
    private String MotherBoard_Model;
    private String MotherBoard_Platform;
    private String MotherBoard_Shape;

    public MotherBoard(String motherBoard_Band, String motherBoard_Model, String motherBoard_Platform, String motherBoard_Shape) {
        MotherBoard_Band = motherBoard_Band;
        MotherBoard_Model = motherBoard_Model;
        MotherBoard_Platform = motherBoard_Platform;
        MotherBoard_Shape = motherBoard_Shape;
    }

    public String getMotherBoard_Band() {
        return MotherBoard_Band;
    }

    public void setMotherBoard_Band(String motherBoard_Band) {
        MotherBoard_Band = motherBoard_Band;
    }

    public String getMotherBoard_Model() {
        return MotherBoard_Model;
    }

    public void setMotherBoard_Model(String motherBoard_Model) {
        MotherBoard_Model = motherBoard_Model;
    }

    public String getMotherBoard_Platform() {
        return MotherBoard_Platform;
    }

    public void setMotherBoard_Platform(String motherBoard_Platform) {
        MotherBoard_Platform = motherBoard_Platform;
    }

    public String getMotherBoard_Shape() {
        return MotherBoard_Shape;
    }

    public void setMotherBoard_Shape(String motherBoard_Shape) {
        MotherBoard_Shape = motherBoard_Shape;
    }
}
