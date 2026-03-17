package com.fangxiong.GUI;

import java.io.Serializable;

public class Power implements Serializable
{
    private String Power_Band;
    private String Power_Model;
    private String Power_Limit;
    private String Power_Class;//ATX or 1U 服务器电源或标准主机电源

    public Power(String power_Band, String power_Model, String power_Limit, String power_Class) {
        Power_Band = power_Band;
        Power_Model = power_Model;
        Power_Limit = power_Limit;
        Power_Class = power_Class;
    }

    public String getPower_Model() {
        return Power_Model;
    }

    public void setPower_Model(String power_Model) {
        Power_Model = power_Model;
    }

    public String getPower_Band() {
        return Power_Band;
    }

    public void setPower_Band(String power_Band) {
        Power_Band = power_Band;
    }

    public String getPower_Limit() {
        return Power_Limit;
    }

    public void setPower_Limit(String power_Limit) {
        Power_Limit = power_Limit;
    }

    public String getPower_Class() {
        return Power_Class;
    }

    public void setPower_Class(String power_Class) {
        Power_Class = power_Class;
    }
}
