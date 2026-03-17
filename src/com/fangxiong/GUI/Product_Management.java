package com.fangxiong.GUI;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Product_Management implements Serializable
{
    private ArrayList<String> Band_List=new ArrayList<>();
    private ArrayList<String> CPU_Band_List=new ArrayList<>();
    private ArrayList<String> GPU_Band_List=new ArrayList<>();
    private ArrayList<String> Power_Band_List=new ArrayList<>();
    private ArrayList<String> RAM_Band_List=new ArrayList<>();
    private ArrayList<String> Storage_Band_List=new ArrayList<>();
    private ArrayList<String> MotherBoard_Band_List=new ArrayList<>();

    private Product_Management()
    {

    }

    private int Product_NUM;
    private int CPU_NUM;
    private int GPU_NUM;
    private int Power_NUM;
    private int RAM_NUM;
    private int Storage_NUM;
    private int MotherBoard_NUM;
    private ArrayList<User> User_List=new ArrayList<>();
    private ArrayList<CPU> CPU_Product=new ArrayList<>();
    private ArrayList<GPU> GPU_Product=new ArrayList<>();
    private ArrayList<Power> Power_Product=new ArrayList<>();
    private ArrayList<RAM> RAM_Product=new ArrayList<>();
    private ArrayList<Storage> Storage_Product=new ArrayList<>();
    private ArrayList<MotherBoard> MotherBoard_Product=new ArrayList<>();
    private final String Administrator_Account="FX520";
    private final String Administrator_Password="abc200519P";
    public void Add_Product(String Product,Object o1)
    {
        int det=0;int det_2=0;Product_NUM++;
        switch(Product)
        {
            case "处理器":
                CPU_NUM++;
                CPU_Product.add((CPU)o1);
                for (String s : CPU_Band_List) {
                    if (s.equals(((CPU) o1).getCPU_Band())) {
                        det=1;
                        break;
                    }
                }
                for (String s : Band_List) {
                    if (s.equals(((CPU) o1).getCPU_Band())) {
                        det_2=1;
                        break;
                    }
                }
                if(det==0)
                {
                    CPU_Band_List.add(((CPU) o1).getCPU_Band());
                }
                if(det_2==0)
                {
                    Band_List.add(((CPU) o1).getCPU_Band());
                }
                break;
                case "显卡":
                    GPU_NUM++;
                    GPU_Product.add((GPU)o1);
                    for (String s : GPU_Band_List) {
                        if (s.equals(((GPU) o1).getGPU_Band())) {
                            det=1;
                            break;
                        }
                    }
                    for (String s : Band_List) {
                        if (s.equals(((GPU) o1).getGPU_Band())) {
                            det_2=1;
                            break;
                        }
                    }
                    if(det==0)
                    {
                        GPU_Band_List.add(((GPU) o1).getGPU_Band());
                    }
                    if(det_2==0)
                    {
                        Band_List.add(((GPU) o1).getGPU_Band());
                    }
                    break;
                    case "电源":
                        Power_NUM++;
                        Power_Product.add((Power)o1);
                        for (String s : Power_Band_List) {
                            if (s.equals(((Power) o1).getPower_Band())) {
                                det=1;
                                break;
                            }
                        }
                        for (String s : Band_List) {
                            if (s.equals(((Power) o1).getPower_Band())) {
                                det_2=1;
                                break;
                            }
                        }
                        if(det==0)
                        {
                            Power_Band_List.add(((Power) o1).getPower_Band());
                        }
                        if(det_2==0)
                        {
                            Band_List.add(((Power) o1).getPower_Band());
                        }
                        break;
                        case "内存条":
                            RAM_NUM++;
                            RAM_Product.add((RAM)o1);
                            for (String s : RAM_Band_List) {
                                if (s.equals(((RAM) o1).getRAM_Band())) {
                                    det=1;
                                    break;
                                }
                            }
                            for (String s : Band_List) {
                                if (s.equals(((RAM) o1).getRAM_Band())) {
                                    det_2=1;
                                    break;
                                }
                            }
                            if(det==0)
                            {
                                RAM_Band_List.add(((RAM) o1).getRAM_Band());
                            }
                            if(det_2==0)
                            {
                                Band_List.add(((RAM) o1).getRAM_Band());
                            }
                            break;
                            case "硬盘":
                                Storage_NUM++;
                                Storage_Product.add((Storage)o1);
                                for (String s : Storage_Band_List) {
                                    if (s.equals(((Storage) o1).getStorage_Band())) {
                                        det=1;
                                        break;
                                    }
                                }
                                for (String s : Band_List) {
                                    if (s.equals(((Storage) o1).getStorage_Band())) {
                                        det_2=1;
                                        break;
                                    }
                                }
                                if(det==0)
                                {
                                    Storage_Band_List.add(((Storage) o1).getStorage_Band());
                                }
                                if(det_2==0)
                                {
                                    Band_List.add(((Storage) o1).getStorage_Band());
                                }
                                break;
                                case "主板":
                                    MotherBoard_NUM++;
                                    MotherBoard_Product.add((MotherBoard)o1);
                                    for (String s : MotherBoard_Band_List) {
                                        if (s.equals(((MotherBoard) o1).getMotherBoard_Band())) {
                                            det=1;
                                            break;
                                        }
                                    }
                                    for (String s : Band_List) {
                                        if (s.equals(((MotherBoard) o1).getMotherBoard_Band())) {
                                            det_2=1;
                                            break;
                                        }
                                    }
                                    if(det==0)
                                    {
                                        MotherBoard_Band_List.add(((MotherBoard) o1).getMotherBoard_Band());
                                    }
                                    if(det_2==0)
                                    {
                                        Band_List.add(((MotherBoard) o1).getMotherBoard_Band());
                                    }
                                    break;
        }

    }

    public void Assign_User(String Account,String Password)
    {
        Calendar Assign_Date=Calendar.getInstance();
        User New_User=new User(Account,Password,Assign_Date);
        User_List.add(New_User);
    }

    public boolean Login_System(String Account,String Password)
    {
        for (User user : User_List) {
            if (user.getAccount().equals(Account) && user.getPassword().equals(Password)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> getUser_List() {
        return User_List;
    }

    public void setUser_List(ArrayList<User> user_List) {
        User_List = user_List;
    }

    public ArrayList<String> getBand_List() {
        return Band_List;
    }

    public void setBand_List(ArrayList<String> band_List) {
        Band_List = band_List;
    }

    public ArrayList<String> getCPU_Band_List() {
        return CPU_Band_List;
    }

    public void setCPU_Band_List(ArrayList<String> CPU_Band_List) {
        this.CPU_Band_List = CPU_Band_List;
    }

    public ArrayList<String> getGPU_Band_List() {
        return GPU_Band_List;
    }

    public void setGPU_Band_List(ArrayList<String> GPU_Band_List) {
        this.GPU_Band_List = GPU_Band_List;
    }

    public ArrayList<String> getPower_Band_List() {
        return Power_Band_List;
    }

    public void setPower_Band_List(ArrayList<String> power_Band_List) {
        Power_Band_List = power_Band_List;
    }

    public ArrayList<String> getRAM_Band_List() {
        return RAM_Band_List;
    }

    public void setRAM_Band_List(ArrayList<String> RAM_Band_List) {
        this.RAM_Band_List = RAM_Band_List;
    }

    public ArrayList<String> getStorage_Band_List() {
        return Storage_Band_List;
    }

    public void setStorage_Band_List(ArrayList<String> storage_Band_List) {
        Storage_Band_List = storage_Band_List;
    }

    public ArrayList<String> getMotherBoard_Band_List() {
        return MotherBoard_Band_List;
    }

    public void setMotherBoard_Band_List(ArrayList<String> motherBoard_Band_List) {
        MotherBoard_Band_List = motherBoard_Band_List;
    }

    public int getProduct_NUM() {
        return Product_NUM;
    }

    public void setProduct_NUM(int product_NUM) {
        Product_NUM = product_NUM;
    }

    public int getCPU_NUM() {
        return CPU_NUM;
    }

    public void setCPU_NUM(int CPU_NUM) {
        this.CPU_NUM = CPU_NUM;
    }

    public int getGPU_NUM() {
        return GPU_NUM;
    }

    public void setGPU_NUM(int GPU_NUM) {
        this.GPU_NUM = GPU_NUM;
    }

    public int getPower_NUM() {
        return Power_NUM;
    }

    public void setPower_NUM(int power_NUM) {
        Power_NUM = power_NUM;
    }

    public int getRAM_NUM() {
        return RAM_NUM;
    }

    public void setRAM_NUM(int RAM_NUM) {
        this.RAM_NUM = RAM_NUM;
    }

    public int getStorage_NUM() {
        return Storage_NUM;
    }

    public void setStorage_NUM(int storage_NUM) {
        Storage_NUM = storage_NUM;
    }

    public int getMotherBoard_NUM() {
        return MotherBoard_NUM;
    }

    public void setMotherBoard_NUM(int motherBoard_NUM) {
        MotherBoard_NUM = motherBoard_NUM;
    }

    public ArrayList<MotherBoard> getMotherBoard_Product() {
        return MotherBoard_Product;
    }

    public void setMotherBoard_Product(ArrayList<MotherBoard> motherBoard_Product) {
        MotherBoard_Product = motherBoard_Product;
    }

    public ArrayList<Storage> getStorage_Product() {
        return Storage_Product;
    }

    public void setStorage_Product(ArrayList<Storage> storage_Product) {
        Storage_Product = storage_Product;
    }

    public ArrayList<RAM> getRAM_Product() {
        return RAM_Product;
    }

    public void setRAM_Product(ArrayList<RAM> RAM_Product) {
        this.RAM_Product = RAM_Product;
    }

    public ArrayList<Power> getPower_Product() {
        return Power_Product;
    }

    public void setPower_Product(ArrayList<Power> power_Product) {
        Power_Product = power_Product;
    }

    public ArrayList<GPU> getGPU_Product() {
        return GPU_Product;
    }

    public void setGPU_Product(ArrayList<GPU> GPU_Product) {
        this.GPU_Product = GPU_Product;
    }

    public ArrayList<CPU> getCPU_Product() {
        return CPU_Product;
    }

    public void setCPU_Product(ArrayList<CPU> CPU_Product) {
        this.CPU_Product = CPU_Product;
    }


    public static Product_Management Get_Product_Management()  //采用单例设计
    {
        return new Product_Management();
    }

}
