package com.fangxiong.GUI;

import java.io.Serializable;

public class RAM implements Serializable
{
    private String RAM_Band;
    private String RAM_Model;
    private String RAM_Size;
    private String RAM_Frequency;
    private String RAM_Voltage;

    public RAM(String RAM_Band, String RAM_Model, String RAM_Size, String RAM_Frequency, String RAM_Voltage) {
        this.RAM_Band = RAM_Band;
        this.RAM_Model = RAM_Model;
        this.RAM_Size = RAM_Size;
        this.RAM_Frequency = RAM_Frequency;
        this.RAM_Voltage = RAM_Voltage;
    }

    public String getRAM_Model() {
        return RAM_Model;
    }

    public void setRAM_Model(String RAM_Model) {
        this.RAM_Model = RAM_Model;
    }

    public String getRAM_Band() {
        return RAM_Band;
    }

    public void setRAM_Band(String RAM_Band) {
        this.RAM_Band = RAM_Band;
    }

    public String getRAM_Size() {
        return RAM_Size;
    }

    public void setRAM_Size(String RAM_Size) {
        this.RAM_Size = RAM_Size;
    }

    public String getRAM_Frequency() {
        return RAM_Frequency;
    }

    public void setRAM_Frequency(String RAM_Frequency) {
        this.RAM_Frequency = RAM_Frequency;
    }

    public String getRAM_Voltage() {
        return RAM_Voltage;
    }

    public void setRAM_Voltage(String RAM_Voltage) {
        this.RAM_Voltage = RAM_Voltage;
    }
}
