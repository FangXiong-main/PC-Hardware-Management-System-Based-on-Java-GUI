package com.fangxiong.GUI;

import java.io.Serializable;

public class Storage implements Serializable
{
    private String Storage_Band;
    private String Storage_Model;
    private String Storage_Method; //HDD or SSD
    private String Storage_Capacity;

    public Storage(String storage_Band, String storage_Model, String storage_Method, String storage_Capacity) {
        Storage_Band = storage_Band;
        Storage_Model = storage_Model;
        Storage_Method = storage_Method;
        Storage_Capacity = storage_Capacity;
    }

    public String getStorage_Model() {
        return Storage_Model;
    }

    public void setStorage_Model(String storage_Model) {
        Storage_Model = storage_Model;
    }

    public String getStorage_Band() {
        return Storage_Band;
    }

    public void setStorage_Band(String storage_Band) {
        Storage_Band = storage_Band;
    }

    public String getStorage_Method() {
        return Storage_Method;
    }

    public void setStorage_Method(String storage_Method) {
        Storage_Method = storage_Method;
    }

    public String getStorage_Capacity() {
        return Storage_Capacity;
    }

    public void setStorage_Capacity(String storage_Capacity) {
        Storage_Capacity = storage_Capacity;
    }
}
