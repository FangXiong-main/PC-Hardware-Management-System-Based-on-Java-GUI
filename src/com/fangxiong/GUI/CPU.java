package com.fangxiong.GUI;

import java.io.Serializable;

public class CPU implements Serializable
{
    private String CPU_Band;
    private String CPU_Model;
    private String CPU_Clock;
    private String CPU_Cache;
    private String CPU_Core;
    private String CPU_Thread;
    private String CPU_Technology;
    private String CPU_TDP;

    public CPU(String CPU_Band, String CPU_Model, String CPU_Clock, String CPU_Cache, String CPU_Core, String CPU_Thread, String CPU_Technology, String CPU_TDP) {
        this.CPU_Band = CPU_Band;
        this.CPU_Model = CPU_Model;
        this.CPU_Clock = CPU_Clock;
        this.CPU_Cache = CPU_Cache;
        this.CPU_Core = CPU_Core;
        this.CPU_Thread = CPU_Thread;
        this.CPU_Technology = CPU_Technology;
        this.CPU_TDP = CPU_TDP;
    }

    public String getCPU_Model() {
        return CPU_Model;
    }

    public void setCPU_Model(String CPU_Model) {
        this.CPU_Model = CPU_Model;
    }

    public String getCPU_Band() {
        return CPU_Band;
    }

    public void setCPU_Band(String CPU_Band) {
        this.CPU_Band = CPU_Band;
    }

    public String getCPU_Clock() {
        return CPU_Clock;
    }

    public void setCPU_Clock(String CPU_Clock) {
        this.CPU_Clock = CPU_Clock;
    }

    public String getCPU_Core() {
        return CPU_Core;
    }

    public void setCPU_Core(String CPU_Core) {
        this.CPU_Core = CPU_Core;
    }

    public String getCPU_Cache() {
        return CPU_Cache;
    }

    public void setCPU_Cache(String CPU_Cache) {
        this.CPU_Cache = CPU_Cache;
    }

    public String getCPU_Thread() {
        return CPU_Thread;
    }

    public void setCPU_Thread(String CPU_Thread) {
        this.CPU_Thread = CPU_Thread;
    }

    public String getCPU_Technology() {
        return CPU_Technology;
    }

    public void setCPU_Technology(String CPU_Technology) {
        this.CPU_Technology = CPU_Technology;
    }

    public String getCPU_TDP() {
        return CPU_TDP;
    }

    public void setCPU_TDP(String CPU_TDP) {
        this.CPU_TDP = CPU_TDP;
    }
}
