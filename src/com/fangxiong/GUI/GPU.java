package com.fangxiong.GUI;

import java.io.Serializable;

public class GPU implements Serializable
{
    private String GPU_Band;
    private String GPU_Model;
    private String GPU_Clock;
    private String GPU_Memory;
    private String GPU_Technology;
    private String GPU_TDP;

    public GPU(String GPU_Band, String GPU_Model, String GPU_Memory, String GPU_Clock, String GPU_Technology, String GPU_TDP) {
        this.GPU_Band = GPU_Band;
        this.GPU_Model = GPU_Model;
        this.GPU_Memory = GPU_Memory;
        this.GPU_Clock = GPU_Clock;
        this.GPU_Technology = GPU_Technology;
        this.GPU_TDP = GPU_TDP;
    }

    public String getGPU_Model() {
        return GPU_Model;
    }

    public void setGPU_Model(String GPU_Model) {
        this.GPU_Model = GPU_Model;
    }

    public String getGPU_Band() {
        return GPU_Band;
    }

    public void setGPU_Band(String GPU_Band) {
        this.GPU_Band = GPU_Band;
    }

    public String getGPU_Clock() {
        return GPU_Clock;
    }

    public void setGPU_Clock(String GPU_Clock) {
        this.GPU_Clock = GPU_Clock;
    }

    public String getGPU_Memory() {
        return GPU_Memory;
    }

    public void setGPU_Memory(String GPU_Memory) {
        this.GPU_Memory = GPU_Memory;
    }

    public String getGPU_Technology() {
        return GPU_Technology;
    }

    public void setGPU_Technology(String GPU_Technology) {
        this.GPU_Technology = GPU_Technology;
    }

    public String getGPU_TDP() {
        return GPU_TDP;
    }

    public void setGPU_TDP(String GPU_TDP) {
        this.GPU_TDP = GPU_TDP;
    }

}
