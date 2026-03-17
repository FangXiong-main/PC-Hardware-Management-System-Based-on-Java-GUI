package com.fangxiong.GUI;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.http.WebSocket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PC_Base_Management implements Serializable
{
    private static Product_Management p1;
    private static String Current_User_Name;
    private static final String file_path="DataBase.dat";
    private static final ImageIcon Main_Window_Icon=new ImageIcon("ICON\\icons8-database-administrator-20.png");
    private static final ImageIcon Login_Window_Icon=new ImageIcon("ICON\\icons8-login-50.png");
    private static final ImageIcon Assign_Window_Icon=new ImageIcon("ICON\\icons8-assign-50.png");
    private static final ImageIcon Add_Window_Icon=new ImageIcon("ICON\\icons8-add-50.png");
    private static final ImageIcon User_Window_Icon=new ImageIcon("ICON\\icons8-person-50.png");
    private static final ImageIcon Modify_Password_Window_Icon=new ImageIcon("ICON\\icons8-key-50.png");
    private static final ImageIcon Modify_Product_Window_Icon=new ImageIcon("ICON\\icons8-modify-50.png");

    private static JFrame Main_frame;
    private static JTable Table = new JTable();
    private static final JScrollPane Scroller = new JScrollPane(Table);


    private static int Table_Listener_Row=-1; //主窗口专用
    private static int Modify_SIGN=-1;
    public static void main(String[] args)
    {
        loadFiles();
        Login_Window(true);
    }

    public static void Main_Window(boolean Visible)
    {

        Table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        Main_frame.setIconImage(Main_Window_Icon.getImage());
        Main_frame.setLocation(450,150);
        Main_frame.setResizable(false);
        Main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main_frame.setSize(800, 750);
        Main_frame.setLayout(new BorderLayout());
        JLabel Choose_Product_Sort=new JLabel("");
        JRadioButton Select_CPU=new JRadioButton("处理器");JRadioButton Select_RAM=new JRadioButton("内存条");
        JRadioButton Select_GPU=new JRadioButton("显卡");JRadioButton Select_Power=new JRadioButton("电源");
        JRadioButton Select_Storage=new JRadioButton("硬盘");JRadioButton Select_MotherBoard=new JRadioButton("主板");
        JRadioButton Select_ALL=new JRadioButton("全部");
        ButtonGroup Select_Group=new ButtonGroup();
        Select_Group.add(Select_CPU);Select_Group.add(Select_RAM);Select_Group.add(Select_GPU);
        Select_Group.add(Select_Power);Select_Group.add(Select_Storage);Select_Group.add(Select_MotherBoard);Select_Group.add(Select_ALL);
        JLabel Head_Choose_Product_Class=new JLabel("产品类型：");
        JLabel Head_Choose_Details=new JLabel("品牌：");
        JComboBox<String> Choose_Band = new JComboBox<>();Choose_Band.setBounds(0,0,50,5);
        JLabel Head_Left_Logo=new JLabel(new ImageIcon("ICON\\icons8-database-administrator-50.png"));
        JLabel Head_Left_Text=new JLabel("FX硬件管理系统");
        Head_Left_Text.setFont(new Font("黑体",Font.BOLD,20));
        JButton Current_User=new JButton(new ImageIcon("ICON\\icons8-person-50.png"));Current_User.setActionCommand("User");
        JButton Add_New_Product=new JButton("添加产品");Add_New_Product.setActionCommand("ADD");
        JButton Delete_Product=new JButton("删除产品");Delete_Product.setActionCommand("DELETE");
        JButton Modify_Product=new JButton("修改产品");Modify_Product.setActionCommand("MODIFY");
        JButton Search_Product=new JButton("搜索产品");Search_Product.setActionCommand("SEARCH");
        JPanel Head_Left_Area=new JPanel(new FlowLayout());
        JPanel Head_Area=new JPanel(new BorderLayout());
        JPanel Choose_Area=new JPanel(new GridLayout(2,1));
        JPanel Choose_Product_Button=new JPanel(new FlowLayout());
        JPanel Choose_Details_Button=new JPanel(new FlowLayout());
        JPanel Right_Function_Area=new JPanel(new GridLayout(4,1));
        Right_Function_Area.add(Add_New_Product);Right_Function_Area.add(Delete_Product);
        Right_Function_Area.add(Modify_Product);Right_Function_Area.add(Search_Product);
        Head_Left_Area.add(Head_Left_Logo);Head_Left_Area.add(Head_Left_Text);
        Choose_Product_Button.add(Head_Choose_Product_Class);
        Choose_Product_Button.add(Select_CPU);Choose_Product_Button.add(Select_RAM);Choose_Product_Button.add(Select_GPU);
        Choose_Product_Button.add(Select_Power);Choose_Product_Button.add(Select_Storage);Choose_Product_Button.add(Select_MotherBoard);
        Choose_Product_Button.add(Select_ALL);
        Choose_Area.add(Choose_Product_Button);
        Choose_Details_Button.add(Head_Choose_Details);
        Choose_Details_Button.add(Choose_Band);
        Choose_Area.add(Choose_Details_Button);
        Head_Area.add(Head_Left_Area,BorderLayout.WEST);
        Head_Area.add(Current_User,BorderLayout.EAST);
        Head_Area.add(Choose_Area,BorderLayout.CENTER);
        Main_frame.add(Head_Area,BorderLayout.NORTH);
        Main_frame.add(Scroller,BorderLayout.EAST); //添加带滚动条的表格
        Main_frame.add(Right_Function_Area,BorderLayout.WEST);
        Main_frame.setVisible(Visible);
        JOptionPane.showMessageDialog(null, "选择产品类型即可显示或刷新表格数据！" );
        //表格监听器
        Table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                // 确保事件不是调整值事件
                if (!event.getValueIsAdjusting()) {
                    // 获取选中的行号
                    Table_Listener_Row = Table.getSelectedRow();
                }
            }
        });

        ActionListener Function_Area_Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Temp_Model="";String Temp_Band="";int Search_id=-1;
                int Search_CPU=-1,Search_GPU=-1,Search_RAM=-1,Search_Storage=-1,Search_Power=-1,Search_MotherBoard=-1;
                switch (e.getActionCommand())
                {
                    case "ADD":
                        Add_Product_Window(true);
                        break;
                    case "DELETE":
                        if(Table_Listener_Row!=-1)
                        {
                            Temp_Model=(String) Table.getValueAt(Table_Listener_Row,1);
                            Temp_Band=(String) Table.getValueAt(Table_Listener_Row,0);
                            if(Choose_Product_Sort.getText().equals("全部"))
                            {
                                switch ((String)Table.getValueAt(Table_Listener_Row,2))
                                {
                                    case "处理器":
                                        for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                                            if (p1.getCPU_Product().get(i).getCPU_Model().equals(Temp_Model)&&p1.getCPU_Product().get(i).getCPU_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getCPU_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                                            if (p1.getCPU_Product().get(i).getCPU_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getCPU_Band_List().size(); i++) {
                                                if (p1.getCPU_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getCPU_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "显卡":
                                        for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                                            if (p1.getGPU_Product().get(i).getGPU_Model().equals(Temp_Model)&&p1.getGPU_Product().get(i).getGPU_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getGPU_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                                            if (p1.getGPU_Product().get(i).getGPU_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getGPU_Band_List().size(); i++) {
                                                if (p1.getGPU_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getGPU_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "内存":
                                        for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                                            if (p1.getRAM_Product().get(i).getRAM_Model().equals(Temp_Model)&&p1.getRAM_Product().get(i).getRAM_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getRAM_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                                            if (p1.getRAM_Product().get(i).getRAM_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getRAM_Band_List().size(); i++) {
                                                if (p1.getRAM_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getRAM_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "硬盘":
                                        for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                                            if (p1.getStorage_Product().get(i).getStorage_Model().equals(Temp_Model)&&p1.getStorage_Product().get(i).getStorage_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getStorage_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                                            if (p1.getStorage_Product().get(i).getStorage_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getStorage_Band_List().size(); i++) {
                                                if (p1.getStorage_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getStorage_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "电源":
                                        for (int i = 0; i < p1.getPower_Product().size(); i++) {
                                            if (p1.getPower_Product().get(i).getPower_Model().equals(Temp_Model)&&p1.getPower_Product().get(i).getPower_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getPower_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getPower_Product().size(); i++) {
                                            if (p1.getPower_Product().get(i).getPower_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getPower_Band_List().size(); i++) {
                                                if (p1.getPower_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getPower_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "主板":
                                        for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                                            if (p1.getMotherBoard_Product().get(i).getMotherBoard_Model().equals(Temp_Model)&&p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getMotherBoard_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                                            if (p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getMotherBoard_Band_List().size(); i++) {
                                                if (p1.getMotherBoard_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getMotherBoard_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                }
                            }
                            else
                            {
                                switch (Choose_Product_Sort.getText())
                                {
                                    case "处理器":
                                        for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                                            if (p1.getCPU_Product().get(i).getCPU_Model().equals(Temp_Model)&&p1.getCPU_Product().get(i).getCPU_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getCPU_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                                            if (p1.getCPU_Product().get(i).getCPU_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getCPU_Band_List().size(); i++) {
                                                if (p1.getCPU_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getCPU_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "显卡":
                                        for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                                            if (p1.getGPU_Product().get(i).getGPU_Model().equals(Temp_Model)&&p1.getGPU_Product().get(i).getGPU_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getGPU_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                                            if (p1.getGPU_Product().get(i).getGPU_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getGPU_Band_List().size(); i++) {
                                                if (p1.getGPU_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getGPU_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "内存":
                                        for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                                            if (p1.getRAM_Product().get(i).getRAM_Model().equals(Temp_Model)&&p1.getRAM_Product().get(i).getRAM_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getRAM_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                                            if (p1.getRAM_Product().get(i).getRAM_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getRAM_Band_List().size(); i++) {
                                                if (p1.getRAM_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getRAM_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "硬盘":
                                        for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                                            if (p1.getStorage_Product().get(i).getStorage_Model().equals(Temp_Model)&&p1.getStorage_Product().get(i).getStorage_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getStorage_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                                            if (p1.getStorage_Product().get(i).getStorage_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getStorage_Band_List().size(); i++) {
                                                if (p1.getStorage_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getStorage_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "电源":
                                        for (int i = 0; i < p1.getPower_Product().size(); i++) {
                                            if (p1.getPower_Product().get(i).getPower_Model().equals(Temp_Model)&&p1.getPower_Product().get(i).getPower_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getPower_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getPower_Product().size(); i++) {
                                            if (p1.getPower_Product().get(i).getPower_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getPower_Band_List().size(); i++) {
                                                if (p1.getPower_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getPower_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    case "主板":
                                        for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                                            if (p1.getMotherBoard_Product().get(i).getMotherBoard_Model().equals(Temp_Model)&&p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        p1.getMotherBoard_Product().remove(Search_id);
                                        Search_id = -1;
                                        for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                                            if (p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(Temp_Band)) {
                                                Search_id = i;
                                            }
                                        }
                                        if (Search_id == -1) {
                                            for (int i = 0; i < p1.getMotherBoard_Band_List().size(); i++) {
                                                if (p1.getMotherBoard_Band_List().get(i).equals(Temp_Band)) {
                                                    p1.getMotherBoard_Band_List().remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                }
                            }
                            for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                                if (p1.getCPU_Product().get(i).getCPU_Band().equals(Temp_Band)) {
                                    Search_CPU = i;
                                }
                            }
                            for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                                if (p1.getGPU_Product().get(i).getGPU_Band().equals(Temp_Band)) {
                                    Search_GPU = i;
                                }
                            }
                            for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                                if (p1.getRAM_Product().get(i).getRAM_Band().equals(Temp_Band)) {
                                    Search_RAM = i;
                                }
                            }
                            for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                                if (p1.getStorage_Product().get(i).getStorage_Band().equals(Temp_Band)) {
                                    Search_Storage = i;
                                }
                            }
                            for (int i = 0; i < p1.getPower_Product().size(); i++) {
                                if (p1.getPower_Product().get(i).getPower_Band().equals(Temp_Band)) {
                                    Search_Power = i;
                                }
                            }
                            for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                                if (p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(Temp_Band)) {
                                    Search_MotherBoard = i;
                                }
                            }
                            if(Search_CPU==-1&&Search_GPU==-1&&Search_RAM==-1&&Search_Storage==-1&&Search_Power==-1&&Search_MotherBoard==-1)
                            {
                                for(int i=0;i<p1.getBand_List().size();i++)
                                {
                                    if(p1.getBand_List().get(i).equals(Temp_Band))
                                    {
                                        p1.getBand_List().remove(i);
                                        break;
                                    }
                                }
                            }
                            Table_Listener_Row=-1;
                            saveFiles();
                            JOptionPane.showMessageDialog(null, "删除成功！请选择任意“产品类型”更新表格数据" );
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "请先在表格中选中要删除的产品！" );
                        }
                        break;
                    case "MODIFY":
                        if(Table_Listener_Row!=-1)
                        {
                            if(Choose_Product_Sort.getText().equals("全部"))
                            {
                                Modify_Product_Window((String) Table.getValueAt(Table_Listener_Row,2));
                            }
                            else
                            {
                                switch (Choose_Product_Sort.getText())
                                {
                                    case "处理器":
                                        Modify_Product_Window("处理器");
                                        break;
                                    case "显卡":
                                        Modify_Product_Window("显卡");
                                        break;
                                    case "内存条":
                                        Modify_Product_Window("内存条");
                                        break;
                                    case "硬盘":
                                        Modify_Product_Window("硬盘");
                                        break;
                                    case "电源":
                                        Modify_Product_Window("电源");
                                        break;
                                    case "主板":
                                        Modify_Product_Window("主板");
                                        break;
                                }
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "请先在表格中选中要修改的产品！" );
                        }

                        break;
                    case "SEARCH":
                        Search_Window(true);
                        break;
                    case "User":
                        User_Window(true);
                        break;
                }
            }
        };
        Add_New_Product.addActionListener(Function_Area_Listener);Current_User.addActionListener(Function_Area_Listener);
        Delete_Product.addActionListener(Function_Area_Listener);Modify_Product.addActionListener(Function_Area_Listener);
        Search_Product.addActionListener(Function_Area_Listener);

        ItemListener RadioButtonListener = new ItemListener()          //复选框监听器
        {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JRadioButton temp=(JRadioButton) e.getItem();
                if(temp.isSelected())
                {
                    String Sort=temp.getText();
                    Choose_Product_Sort.setText(Sort);
                    switch(Sort)
                    {
                        case "处理器":
                                Choose_Band.removeAllItems();Choose_Band.addItem("全部");
                                Thread add_Box_1 = new Thread(() -> {
                                    for(int i=0;i<p1.getCPU_Band_List().size();i++)
                                    {
                                        Choose_Band.addItem(p1.getCPU_Band_List().get(i));
                                    }
                                });
                                add_Box_1.start();
                                break;
                        case "显卡":
                            Choose_Band.removeAllItems();Choose_Band.addItem("全部");
                            Thread add_Box_2 = new Thread(() -> {
                                for(int i=0;i<p1.getGPU_Band_List().size();i++)
                                {
                                    Choose_Band.addItem(p1.getGPU_Band_List().get(i));
                                }
                            });
                            add_Box_2.start();
                            break;
                        case "硬盘":
                            Choose_Band.removeAllItems();Choose_Band.addItem("全部");
                            Thread add_Box_3 = new Thread(() -> {
                                for(int i=0;i<p1.getStorage_Band_List().size();i++)
                                {
                                    Choose_Band.addItem(p1.getStorage_Band_List().get(i));
                                }
                            });
                            add_Box_3.start();
                            break;
                        case "主板":
                            Choose_Band.removeAllItems();Choose_Band.addItem("全部");
                            Thread add_Box_4 = new Thread(() -> {
                                for(int i=0;i<p1.getMotherBoard_Band_List().size();i++)
                                {
                                    Choose_Band.addItem(p1.getMotherBoard_Band_List().get(i));
                                }
                            });
                            add_Box_4.start();
                            break;
                        case "电源":
                            Choose_Band.removeAllItems();Choose_Band.addItem("全部");
                            Thread add_Box_5 = new Thread(() -> {
                                for(int i=0;i<p1.getPower_Band_List().size();i++)
                                {
                                    Choose_Band.addItem(p1.getPower_Band_List().get(i));
                                }
                            });
                            add_Box_5.start();
                            break;
                        case "内存条":
                            Choose_Band.removeAllItems();Choose_Band.addItem("全部");
                            Thread add_Box_6 = new Thread(() -> {
                                for(int i=0;i<p1.getRAM_Band_List().size();i++)
                                {
                                    Choose_Band.addItem(p1.getRAM_Band_List().get(i));
                                }
                            });
                            add_Box_6.start();
                            break;
                        case "全部":
                            Choose_Band.removeAllItems();Choose_Band.addItem("全部");
                            Thread add_Box_7 = new Thread(() -> {
                                for(int i=0;i<p1.getBand_List().size();i++)
                                {
                                    Choose_Band.addItem(p1.getBand_List().get(i));
                                }
                            });
                            add_Box_7.start();
                            break;
                    }
                }
            }
        };
        Select_CPU.addItemListener(RadioButtonListener);Select_GPU.addItemListener(RadioButtonListener);
        Select_Power.addItemListener(RadioButtonListener);Select_RAM.addItemListener(RadioButtonListener);
        Select_Storage.addItemListener(RadioButtonListener);Select_MotherBoard.addItemListener(RadioButtonListener);
        Select_ALL.addItemListener(RadioButtonListener);

        //下拉框监听器
        Choose_Band.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    DefaultTableModel model = new DefaultTableModel();
                    switch (Choose_Product_Sort.getText())
                    {
                        case "处理器":
                            model.addColumn("品牌");
                            model.addColumn("型号");
                            model.addColumn("主频");
                            model.addColumn("缓存大小");
                            model.addColumn("核心数量");
                            model.addColumn("线程数量");
                            model.addColumn("制作工艺");
                            model.addColumn("TDP");
                            if(e.getItem().toString().equals("全部"))
                            {
                                for(int i=0;i<p1.getCPU_Product().size();i++)
                                {
                                    model.addRow(new Object[]{p1.getCPU_Product().get(i).getCPU_Band(),p1.getCPU_Product().get(i).getCPU_Model(),p1.getCPU_Product().get(i).getCPU_Clock(),p1.getCPU_Product().get(i).getCPU_Cache(),p1.getCPU_Product().get(i).getCPU_Core(),p1.getCPU_Product().get(i).getCPU_Thread(),p1.getCPU_Product().get(i).getCPU_Technology(),p1.getCPU_Product().get(i).getCPU_TDP()});
                                }
                            }
                            else
                            {
                                for(int i=0;i<p1.getCPU_Product().size();i++)
                                {
                                    if(p1.getCPU_Product().get(i).getCPU_Band().equals(e.getItem().toString()))
                                    {
                                        model.addRow(new Object[]{p1.getCPU_Product().get(i).getCPU_Band(),p1.getCPU_Product().get(i).getCPU_Model(),p1.getCPU_Product().get(i).getCPU_Clock(),p1.getCPU_Product().get(i).getCPU_Cache(),p1.getCPU_Product().get(i).getCPU_Core(),p1.getCPU_Product().get(i).getCPU_Thread(),p1.getCPU_Product().get(i).getCPU_Technology(),p1.getCPU_Product().get(i).getCPU_TDP()});
                                    }
                                }
                            }
                            Table.setModel(model);
                            break;
                        case "显卡":
                            model.addColumn("品牌");
                            model.addColumn("型号");
                            model.addColumn("核心频率");
                            model.addColumn("显存");
                            model.addColumn("制作工艺");
                            model.addColumn("TDP");
                            if(e.getItem().toString().equals("全部"))
                            {
                                for(int i=0;i<p1.getGPU_Product().size();i++)
                                {
                                    model.addRow(new Object[]{p1.getGPU_Product().get(i).getGPU_Band(),p1.getGPU_Product().get(i).getGPU_Model(),p1.getGPU_Product().get(i).getGPU_Clock(),p1.getGPU_Product().get(i).getGPU_Memory(),p1.getGPU_Product().get(i).getGPU_Technology(),p1.getGPU_Product().get(i).getGPU_TDP()});
                                }
                            }
                            else
                            {
                                for(int i=0;i<p1.getGPU_Product().size();i++)
                                {
                                    if(p1.getGPU_Product().get(i).getGPU_Band().equals(e.getItem().toString()))
                                    {
                                        model.addRow(new Object[]{p1.getGPU_Product().get(i).getGPU_Band(),p1.getGPU_Product().get(i).getGPU_Model(),p1.getGPU_Product().get(i).getGPU_Clock(),p1.getGPU_Product().get(i).getGPU_Memory(),p1.getGPU_Product().get(i).getGPU_Technology(),p1.getGPU_Product().get(i).getGPU_TDP()});
                                    }
                                }
                            }
                            Table.setModel(model);
                            break;
                        case "内存条":
                            model.addColumn("品牌");
                            model.addColumn("型号");
                            model.addColumn("内存容量");
                            model.addColumn("内存频率");
                            model.addColumn("电压");
                            if(e.getItem().toString().equals("全部"))
                            {
                                for(int i=0;i<p1.getRAM_Product().size();i++)
                                {
                                    model.addRow(new Object[]{p1.getRAM_Product().get(i).getRAM_Band(),p1.getRAM_Product().get(i).getRAM_Model(),p1.getRAM_Product().get(i).getRAM_Size(),p1.getRAM_Product().get(i).getRAM_Frequency(),p1.getRAM_Product().get(i).getRAM_Voltage()});
                                }
                            }
                            else
                            {
                                for(int i=0;i<p1.getRAM_Product().size();i++)
                                {
                                    if(p1.getRAM_Product().get(i).getRAM_Band().equals(e.getItem().toString()))
                                    {
                                        model.addRow(new Object[]{p1.getRAM_Product().get(i).getRAM_Band(),p1.getRAM_Product().get(i).getRAM_Model(),p1.getRAM_Product().get(i).getRAM_Size(),p1.getRAM_Product().get(i).getRAM_Frequency(),p1.getRAM_Product().get(i).getRAM_Voltage()});
                                    }
                                }
                            }
                            Table.setModel(model);
                            break;
                        case "硬盘":
                            model.addColumn("品牌");
                            model.addColumn("型号");
                            model.addColumn("硬盘类型");
                            model.addColumn("硬盘容量");
                            if(e.getItem().toString().equals("全部"))
                            {
                                for(int i=0;i<p1.getStorage_Product().size();i++)
                                {
                                    model.addRow(new Object[]{p1.getStorage_Product().get(i).getStorage_Band(),p1.getStorage_Product().get(i).getStorage_Model(),p1.getStorage_Product().get(i).getStorage_Method(),p1.getStorage_Product().get(i).getStorage_Capacity()});
                                }
                            }
                            else
                            {
                                for(int i=0;i<p1.getStorage_Product().size();i++)
                                {
                                    if(p1.getStorage_Product().get(i).getStorage_Band().equals(e.getItem().toString()))
                                    {
                                        model.addRow(new Object[]{p1.getStorage_Product().get(i).getStorage_Band(),p1.getStorage_Product().get(i).getStorage_Model(),p1.getStorage_Product().get(i).getStorage_Method(),p1.getStorage_Product().get(i).getStorage_Capacity()});
                                    }
                                }
                            }
                            Table.setModel(model);
                            break;
                        case "电源":
                            model.addColumn("品牌");
                            model.addColumn("型号");
                            model.addColumn("电源功率");
                            model.addColumn("电源规格");
                            if(e.getItem().toString().equals("全部"))
                            {
                                for(int i=0;i<p1.getPower_Product().size();i++)
                                {
                                    model.addRow(new Object[]{p1.getPower_Product().get(i).getPower_Band(),p1.getPower_Product().get(i).getPower_Model(),p1.getPower_Product().get(i).getPower_Limit(),p1.getPower_Product().get(i).getPower_Class()});
                                }
                            }
                            else
                            {
                                for(int i=0;i<p1.getPower_Product().size();i++)
                                {
                                    if(p1.getPower_Product().get(i).getPower_Band().equals(e.getItem().toString()))
                                    {
                                        model.addRow(new Object[]{p1.getPower_Product().get(i).getPower_Band(),p1.getPower_Product().get(i).getPower_Model(),p1.getPower_Product().get(i).getPower_Limit(),p1.getPower_Product().get(i).getPower_Class()});
                                    }
                                }
                            }
                            Table.setModel(model);
                            break;
                        case "主板":
                            model.addColumn("品牌");
                            model.addColumn("型号");
                            model.addColumn("适用平台");
                            model.addColumn("主板规格");
                            if(e.getItem().toString().equals("全部"))
                            {
                                for(int i=0;i<p1.getMotherBoard_Product().size();i++)
                                {
                                    model.addRow(new Object[]{p1.getMotherBoard_Product().get(i).getMotherBoard_Band(),p1.getMotherBoard_Product().get(i).getMotherBoard_Model(),p1.getMotherBoard_Product().get(i).getMotherBoard_Platform(),p1.getMotherBoard_Product().get(i).getMotherBoard_Shape()});
                                }
                            }
                            else
                            {
                                for(int i=0;i<p1.getMotherBoard_Product().size();i++)
                                {
                                    if(p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(e.getItem().toString()))
                                    {
                                        model.addRow(new Object[]{p1.getMotherBoard_Product().get(i).getMotherBoard_Band(),p1.getMotherBoard_Product().get(i).getMotherBoard_Model(),p1.getMotherBoard_Product().get(i).getMotherBoard_Platform(),p1.getMotherBoard_Product().get(i).getMotherBoard_Shape()});
                                    }
                                }
                            }
                            Table.setModel(model);
                            break;
                        case "全部":
                            model.addColumn("品牌");
                            model.addColumn("型号");
                            model.addColumn("产品类型");
                            if(e.getItem().toString().equals("全部")) {
                                for (String s : p1.getBand_List()) {
                                    Thread Search_1 = new Thread(() -> {
                                        for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                                            if (p1.getCPU_Product().get(i).getCPU_Band().equals(s)) {
                                                model.addRow(new Object[]{p1.getCPU_Product().get(i).getCPU_Band(), p1.getCPU_Product().get(i).getCPU_Model(), "处理器"});
                                            }
                                        }
                                    });
                                    Thread Search_2 = new Thread(() -> {
                                        for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                                            if (p1.getGPU_Product().get(i).getGPU_Band().equals(s)) {
                                                model.addRow(new Object[]{p1.getGPU_Product().get(i).getGPU_Band(), p1.getGPU_Product().get(i).getGPU_Model(), "显卡"});
                                            }
                                        }
                                    });
                                    Thread Search_3 = new Thread(() -> {
                                        for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                                            if (p1.getRAM_Product().get(i).getRAM_Band().equals(s)) {
                                                model.addRow(new Object[]{p1.getRAM_Product().get(i).getRAM_Band(), p1.getRAM_Product().get(i).getRAM_Model(), "内存条"});
                                            }
                                        }
                                    });
                                    Thread Search_4 = new Thread(() -> {
                                        for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                                            if (p1.getStorage_Product().get(i).getStorage_Band().equals(s)) {
                                                model.addRow(new Object[]{p1.getStorage_Product().get(i).getStorage_Band(), p1.getStorage_Product().get(i).getStorage_Model(), "硬盘"});
                                            }
                                        }
                                    });
                                    Thread Search_5 = new Thread(() -> {
                                        for (int i = 0; i < p1.getPower_Product().size(); i++) {
                                            if (p1.getPower_Product().get(i).getPower_Band().equals(s)) {
                                                model.addRow(new Object[]{p1.getPower_Product().get(i).getPower_Band(), p1.getPower_Product().get(i).getPower_Model(), "电源"});
                                            }
                                        }
                                    });
                                    Thread Search_6 = new Thread(() -> {
                                        for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                                            if (p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(s)) {
                                                model.addRow(new Object[]{p1.getMotherBoard_Product().get(i).getMotherBoard_Band(), p1.getMotherBoard_Product().get(i).getMotherBoard_Model(), "主板"});
                                            }
                                        }
                                    });
                                    Search_1.start();
                                    Search_2.start();
                                    Search_3.start();
                                    Search_4.start();
                                    Search_5.start();
                                    Search_6.start();//多线程搜索
                                }
                            }
                            else
                            {
                                Thread Search_7=new Thread(()->{
                                    for(int i=0;i<p1.getCPU_Product().size();i++)
                                    {
                                        if(p1.getCPU_Product().get(i).getCPU_Band().equals(e.getItem().toString()))
                                        {
                                            model.addRow(new Object[]{p1.getCPU_Product().get(i).getCPU_Band(),p1.getCPU_Product().get(i).getCPU_Model(),"处理器"});
                                        }
                                    }
                                });
                                Thread Search_8=new Thread(()->{
                                    for(int i=0;i<p1.getGPU_Product().size();i++)
                                    {
                                        if(p1.getGPU_Product().get(i).getGPU_Band().equals(e.getItem().toString()))
                                        {
                                            model.addRow(new Object[]{p1.getGPU_Product().get(i).getGPU_Band(),p1.getGPU_Product().get(i).getGPU_Model(),"显卡"});
                                        }
                                    }
                                });
                                Thread Search_9=new Thread(()->{
                                    for(int i=0;i<p1.getRAM_Product().size();i++)
                                    {
                                        if(p1.getRAM_Product().get(i).getRAM_Band().equals(e.getItem().toString()))
                                        {
                                            model.addRow(new Object[]{p1.getRAM_Product().get(i).getRAM_Band(),p1.getRAM_Product().get(i).getRAM_Model(),"内存条"});
                                        }
                                    }
                                });
                                Thread Search_10=new Thread(()->{
                                    for(int i=0;i<p1.getStorage_Product().size();i++)
                                    {
                                        if(p1.getStorage_Product().get(i).getStorage_Band().equals(e.getItem().toString()))
                                        {
                                            model.addRow(new Object[]{p1.getStorage_Product().get(i).getStorage_Band(),p1.getStorage_Product().get(i).getStorage_Model(),"硬盘"});
                                        }
                                    }
                                });
                                Thread Search_11=new Thread(()->{
                                    for(int i=0;i<p1.getPower_Product().size();i++)
                                    {
                                        if(p1.getPower_Product().get(i).getPower_Band().equals(e.getItem().toString()))
                                        {
                                            model.addRow(new Object[]{p1.getPower_Product().get(i).getPower_Band(),p1.getPower_Product().get(i).getPower_Model(),"电源"});
                                        }
                                    }
                                });
                                Thread Search_12=new Thread(()->{
                                    for(int i=0;i<p1.getMotherBoard_Product().size();i++)
                                    {
                                        if(p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(e.getItem().toString()))
                                        {
                                            model.addRow(new Object[]{p1.getMotherBoard_Product().get(i).getMotherBoard_Band(),p1.getMotherBoard_Product().get(i).getMotherBoard_Model(),"主板"});
                                        }
                                    }
                                });
                                Search_7.start();Search_8.start();Search_9.start();Search_10.start();Search_11.start();Search_12.start();
                            }
                            Table.setModel(model);
                            break;
                    }
                }
            }
        });
    }

    public static void Login_Window(boolean Visible)
    {
        JFrame frame = new JFrame("FX硬件管理系统");
        frame.setIconImage(Login_Window_Icon.getImage());
        frame.setLocation(750,400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());
        JPanel Login_Home = new JPanel(new BorderLayout());
        JPanel Login_Text_Area = new JPanel(new GridLayout(4,1));
        JPanel Account =new JPanel(new FlowLayout());
        JPanel Password =new JPanel(new FlowLayout());
        JPanel BUTTON =new JPanel(new FlowLayout());
        JPanel Login_Head = new JPanel(new FlowLayout());
        JButton Login_Button = new JButton("登录");Login_Button.setActionCommand("Login");
        JButton Assign_Button = new JButton("注册");Assign_Button.setActionCommand("Assign");
        JLabel Login_Icon=new JLabel(new ImageIcon("ICON\\icons8-login-96.png"));
        JLabel EMPTY=new JLabel(" ");JLabel LINE=new JLabel("—————————————");
        JLabel Login_Text=new JLabel("FX硬件管理系统登录");
        Login_Text.setFont(new Font("仿宋",Font.BOLD,17));
        JLabel L1=new JLabel("账户：");L1.setFont(new Font("宋体",Font.BOLD,15));
        JLabel L2=new JLabel("密码：");L2.setFont(new Font("宋体",Font.BOLD,15));
        JTextField User_Account = new JTextField(10);//User.setBounds();
        JPasswordField Pass = new JPasswordField(10);
        User_Account.setPreferredSize(new Dimension (200,25));
        Pass.setPreferredSize(new Dimension (200,25));
        Pass.setEchoChar('*');
        Login_Head.add(EMPTY);
        Login_Head.add(Login_Text);
        Login_Home.add(Login_Head,BorderLayout.NORTH);
        Login_Home.add(Login_Icon, BorderLayout.WEST);
        Account.add(L1);Account.add(User_Account);
        Password.add(L2);Password.add(Pass);
        BUTTON.add(Login_Button);BUTTON.add(Assign_Button);
        Login_Text_Area.add(LINE);
        Login_Text_Area.add(Account);Login_Text_Area.add(Password);Login_Text_Area.add(BUTTON);
        Login_Home.add(Login_Text_Area, BorderLayout.EAST);
        frame.add(Login_Home);
        frame.setVisible(Visible);
        ActionListener Login_ActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand())
                {
                    case "Login":
                        if(p1.Login_System(User_Account.getText(),Pass.getText()))
                        {
                            Current_User_Name=User_Account.getText();
                            JFrame frame_log = new JFrame();
                            JOptionPane.showMessageDialog(frame_log, "登录成功！欢迎您:"+User_Account.getText());
                            frame.dispose();
                            frame_log.dispose();
                            Main_frame=new JFrame("FX硬件管理系统");
                            Main_Window(true);
                        }
                        else
                        {
                            JFrame frame_log = new JFrame();
                            JOptionPane.showMessageDialog(null,"账户或密码错误!", "登陆错误", JOptionPane.ERROR_MESSAGE);
                            frame_log.dispose();
                            User_Account.setText("");Pass.setText("");
                        }
                        break;
                        case "Assign":
                            frame.dispose();
                            Assign_Window(true);
                            break;
                }
            }
        };Login_Button.addActionListener(Login_ActionListener);Assign_Button.addActionListener(Login_ActionListener);
    }

    public static void Assign_Window(boolean Visible)
    {
        JFrame frame_log = new JFrame();
        JFrame frame = new JFrame("注册");
        frame.setIconImage(Assign_Window_Icon.getImage());
        frame.setLocation(750,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        JPanel Assign_Panel = new JPanel(new BorderLayout());
        JPanel Assign_Head = new JPanel(new FlowLayout());
        JPanel Assign_Text_Area = new JPanel(new GridLayout(4,1));
        JPanel Account =new JPanel(new FlowLayout());
        JPanel Password =new JPanel(new FlowLayout());
        JPanel BUTTON =new JPanel(new FlowLayout());
        JButton Assign_Confirm = new JButton("注册");Assign_Confirm.setActionCommand("Confirm");
        JButton Assign_Cancel = new JButton("取消");Assign_Cancel.setActionCommand("Cancel");
        JLabel Logo = new JLabel(new ImageIcon("ICON\\icons8-batch-assign-100.png"));
        JLabel L1=new JLabel("账户：");L1.setFont(new Font("宋体",Font.BOLD,15));
        JLabel L2=new JLabel("密码：");L2.setFont(new Font("宋体",Font.BOLD,15));
        JLabel EMPTY=new JLabel(" ");JLabel LINE=new JLabel("—————————————");
        JLabel Assign_Text=new JLabel("用户注册");
        Assign_Text.setFont(new Font("仿宋",Font.BOLD,17));
        JTextField User_Account = new JTextField(10);
        JTextField Pass = new JTextField(10);
        User_Account.setPreferredSize(new Dimension (200,25));
        Pass.setPreferredSize(new Dimension (200,25));
        Assign_Head.add(EMPTY);Assign_Head.add(Assign_Text);Assign_Head.add(EMPTY);
        Account.add(L1);Account.add(User_Account);Password.add(Pass);Password.add(L2);Password.add(Pass);
        BUTTON.add(Assign_Confirm);BUTTON.add(Assign_Cancel);
        Assign_Text_Area.add(LINE);
        Assign_Text_Area.add(Account);Assign_Text_Area.add(Password);Assign_Text_Area.add(BUTTON);
        Assign_Panel.add(Assign_Head,BorderLayout.NORTH);
        Assign_Panel.add(Assign_Text_Area,BorderLayout.EAST);
        Assign_Panel.add(Logo,BorderLayout.WEST);
        frame.add(Assign_Panel);
        frame.setResizable(false);
        frame.setVisible(Visible);
        JOptionPane.showMessageDialog(frame_log, "账户由4-8位字符组成,密码为6-10位的字符");

        ActionListener Login_ActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand())
                {
                    case "Confirm":
                        if(User_Account.getText().length()>=4&&User_Account.getText().length()<=8)
                        {
                            if(Pass.getText().length()>=6&&Pass.getText().length()<=10)
                            {
                                p1.Assign_User(User_Account.getText(),Pass.getText());
                                saveFiles();
                                JOptionPane.showMessageDialog(frame_log, "注册成功！");
                                frame.dispose();
                                frame_log.dispose();
                                Login_Window(true);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"密码格式错误!", "注册错误", JOptionPane.ERROR_MESSAGE);
                                Pass.setText("");
                            }
                        }
                        else
                        {
                            User_Account.setText("");
                            JOptionPane.showMessageDialog(null,"账户格式错误!", "注册错误", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Cancel":
                        frame_log.dispose();
                        frame.dispose();
                        Login_Window(true);
                }
            }
        };Assign_Confirm.addActionListener(Login_ActionListener);Assign_Cancel.addActionListener(Login_ActionListener);
    }
    public static void Add_Product_Window(boolean Visible)
    {
        JFrame frame = new JFrame("添加产品");
        frame.setIconImage(Add_Window_Icon.getImage());
        frame.setSize(400, 550);
        frame.setLocation(750,400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] Product_Sort={"请选择","处理器","显卡","内存条","硬盘","电源","主板"};
        JTextField Enter_Band=new JTextField(6);
        JTextField Enter_Model=new JTextField(6);
        JTextField Enter_TDP=new JTextField(6);
        JTextField Enter_Frequency=new JTextField(6);
        JTextField Enter_Memory=new JTextField(6);
        JTextField Enter_Power=new JTextField(6);
        JTextField Enter_Technology=new JTextField(6);
        JTextField Enter_Voltage=new JTextField(6);
        JTextField Enter_Platform=new JTextField(6);
        JTextField Enter_Shape=new JTextField(6);
        JComboBox<String> Choose_Add_Sort=new JComboBox<>();
        for(int i=0;i<Product_Sort.length;i++)
        {
            Choose_Add_Sort.addItem(Product_Sort[i]);
        }
        JLabel Sort_SIGN=new JLabel("");
        JLabel Head_Text=new JLabel("添加产品");
        Head_Text.setFont(new Font("微软雅黑",Font.BOLD,15));
        JLabel Band=new JLabel("品牌:");JLabel Model=new JLabel("型号:");
        JLabel TDP=new JLabel("TDP:");JLabel Frequency=new JLabel("频率:");
        JLabel Memory=new JLabel("缓存:");JLabel Power=new JLabel("功率:");
        JLabel Technology=new JLabel("制程:");JLabel Voltage=new JLabel("电压:");
        JLabel Platform=new JLabel("适用平台:");JLabel Shape=new JLabel("规格:");
        JLabel Choose_Sort_Text=new JLabel("产品类型选择:");
        JButton Confirm_Add=new JButton("添加");
        JPanel Head_Area = new JPanel(new GridLayout(2,1));
        JPanel Choose_Sort_Area = new JPanel(new FlowLayout());
        JPanel Enter_Area = new JPanel(new GridLayout(10,1));
        JPanel Enter_Details_Band_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Model_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_TDP_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Frequency_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Memory_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Power_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Technology_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Voltage_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Platform_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Shape_Area = new JPanel(new GridLayout(1,2));
        Choose_Sort_Area.add(Choose_Sort_Text);Choose_Sort_Area.add(Choose_Add_Sort);
        Head_Area.add(Head_Text);Head_Area.add(Choose_Sort_Area);

        Enter_Details_Band_Area.add(Band);Enter_Details_Band_Area.add(Enter_Band);
        Enter_Details_Model_Area.add(Model);Enter_Details_Model_Area.add(Enter_Model);
        Enter_Details_TDP_Area.add(TDP);Enter_Details_TDP_Area.add(Enter_TDP);
        Enter_Details_Frequency_Area.add(Frequency);Enter_Details_Frequency_Area.add(Enter_Frequency);
        Enter_Details_Memory_Area.add(Memory);Enter_Details_Memory_Area.add(Enter_Memory);
        Enter_Details_Power_Area.add(Power);Enter_Details_Power_Area.add(Enter_Power);
        Enter_Details_Technology_Area.add(Technology);Enter_Details_Technology_Area.add(Enter_Technology);
        Enter_Details_Voltage_Area.add(Voltage);Enter_Details_Voltage_Area.add(Enter_Voltage);
        Enter_Details_Platform_Area.add(Platform);Enter_Details_Platform_Area.add(Enter_Platform);
        Enter_Details_Shape_Area.add(Shape);Enter_Details_Shape_Area.add(Enter_Shape);

        Enter_Area.add(Enter_Details_Band_Area);Enter_Area.add(Enter_Details_Model_Area);
        Enter_Area.add(Enter_Details_TDP_Area);Enter_Area.add(Enter_Details_Frequency_Area);
        Enter_Area.add(Enter_Details_Memory_Area);Enter_Area.add(Enter_Details_Power_Area);
        Enter_Area.add(Enter_Details_Technology_Area);Enter_Area.add(Enter_Details_Voltage_Area);
        Enter_Area.add(Enter_Details_Platform_Area);Enter_Area.add(Enter_Details_Shape_Area);

        frame.add(Head_Area,BorderLayout.NORTH);frame.add(Enter_Area,BorderLayout.EAST);
        frame.add(Confirm_Add,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(Visible);
        Choose_Add_Sort.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                Band.setVisible(true);Enter_Band.setVisible(true);
                Model.setVisible(true);Enter_Model.setVisible(true);
                TDP.setVisible(true);Enter_TDP.setVisible(true);
                Frequency.setVisible(true);Enter_Frequency.setVisible(true);
                Memory.setVisible(true);Enter_Memory.setVisible(true);
                Power.setVisible(true);Enter_Power.setVisible(true);
                Technology.setVisible(true);Enter_Technology.setVisible(true);
                Platform.setVisible(true);Enter_Platform.setVisible(true);
                Shape.setVisible(true);Enter_Shape.setVisible(true);
                Voltage.setVisible(true);Enter_Voltage.setVisible(true);
                Sort_SIGN.setText(e.getItem().toString());
                switch (e.getItem().toString())
                {
                    case "处理器":
                        Frequency.setText("主频：");
                        Power.setVisible(false);
                        Enter_Power.setVisible(false);
                        Shape.setText("核心数量：");Voltage.setText("线程数量：");
                        Memory.setText("缓存：");
                        Platform.setVisible(false);Enter_Platform.setVisible(false);
                        break;
                    case "显卡":
                        Frequency.setText("核心频率：");Memory.setText("显存：");
                        Power.setVisible(false);Enter_Power.setVisible(false);
                        Voltage.setVisible(false);Enter_Voltage.setVisible(false);
                        Shape.setVisible(false);Enter_Shape.setVisible(false);
                        Platform.setVisible(false);Enter_Platform.setVisible(false);
                        break;
                    case "内存条":
                        TDP.setVisible(false);Enter_TDP.setVisible(false);
                        Power.setVisible(false);Enter_Power.setVisible(false);
                        Technology.setVisible(false);Enter_Technology.setVisible(false);
                        Voltage.setText("电压：");
                        Shape.setVisible(false);Enter_Shape.setVisible(false);
                        Platform.setVisible(false);Enter_Platform.setVisible(false);
                        Memory.setText("内存容量：");
                        break;
                    case "硬盘":
                        Frequency.setVisible(false);Enter_Frequency.setVisible(false);
                        TDP.setVisible(false);Enter_TDP.setVisible(false);
                        Power.setVisible(false);Enter_Power.setVisible(false);
                        Technology.setVisible(false);Enter_Technology.setVisible(false);
                        Voltage.setVisible(false);Enter_Voltage.setVisible(false);
                        Platform.setVisible(false);Enter_Platform.setVisible(false);
                        Memory.setText("容量：");Shape.setText("硬盘类型：");
                        break;
                    case "电源":
                        Frequency.setVisible(false);Enter_Frequency.setVisible(false);
                        TDP.setVisible(false);Enter_TDP.setVisible(false);
                        Technology.setVisible(false);Enter_Technology.setVisible(false);
                        Voltage.setVisible(false);Enter_Voltage.setVisible(false);
                        Memory.setVisible(false);Enter_Memory.setVisible(false);
                        Platform.setVisible(false);Enter_Platform.setVisible(false);
                        Shape.setText("电源规格：");
                        break;
                    case "主板":
                        Frequency.setVisible(false);Enter_Frequency.setVisible(false);
                        TDP.setVisible(false);Enter_TDP.setVisible(false);
                        Memory.setVisible(false);Enter_Memory.setVisible(false);
                        Technology.setVisible(false);Enter_Technology.setVisible(false);
                        Voltage.setVisible(false);Enter_Voltage.setVisible(false);
                        Power.setVisible(false);Enter_Power.setVisible(false);
                        Shape.setText("主板规格：");
                        break;
                }
            }
        }
        });

        Confirm_Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!(Sort_SIGN.getText().equals("处理器")||Sort_SIGN.getText().equals("显卡")||Sort_SIGN.getText().equals("内存条")||Sort_SIGN.getText().equals("硬盘")||Sort_SIGN.getText().equals("电源")||Sort_SIGN.getText().equals("主板")))
                {
                    JOptionPane.showMessageDialog(null, "请先选择要添加的产品类型！" );
                }
                else
                {
                    switch (Sort_SIGN.getText())
                    {
                        case "处理器":
                            CPU New_CPU=new CPU(Enter_Band.getText(),Enter_Model.getText(),Enter_Frequency.getText(),Enter_Memory.getText(),Enter_Shape.getText(),Enter_Voltage.getText(),Enter_Technology.getText(),Enter_TDP.getText());
                            p1.Add_Product("处理器",New_CPU);
                            break;
                        case "显卡":
                            GPU New_GPU=new GPU(Enter_Band.getText(),Enter_Model.getText(),Enter_Memory.getText(),Enter_Frequency.getText(),Enter_Technology.getText(),Enter_TDP.getText());
                            p1.Add_Product("显卡",New_GPU);
                            break;
                        case "内存条":
                            RAM New_RAM=new RAM(Enter_Band.getText(),Enter_Model.getText(),Enter_Memory.getText(),Enter_Frequency.getText(),Enter_Voltage.getText());
                            p1.Add_Product("内存条",New_RAM);
                            break;
                        case "硬盘":
                            Storage New_Storage=new Storage(Enter_Band.getText(),Enter_Model.getText(),Enter_Shape.getText(),Enter_Memory.getText());
                            p1.Add_Product("硬盘",New_Storage);
                            break;
                        case "电源":
                            Power New_Power=new Power(Enter_Band.getText(),Enter_Model.getText(),Enter_Power.getText(),Enter_Shape.getText());
                            p1.Add_Product("电源",New_Power);
                            break;
                        case "主板":
                            MotherBoard New_MotherBoard=new MotherBoard(Enter_Band.getText(),Enter_Model.getText(),Enter_Platform.getText(),Enter_Shape.getText());
                            p1.Add_Product("主板",New_MotherBoard);
                            break;
                    }
                    saveFiles();
                    JOptionPane.showMessageDialog(null, "添加成功！请选择任意“产品类型”更新表格数据");
                    frame.dispose();
                }
                Enter_Band.setText("");Enter_Model.setText("");Enter_Frequency.setText("");Enter_Memory.setText("");
                Enter_Voltage.setText("");Enter_TDP.setText("");Enter_Power.setText("");Enter_Technology.setText("");
                Enter_Shape.setText("");Enter_Platform.setText("");
            }
        });
    }
    public static void User_Window(boolean Visible)
    {
        int Search_id=0;int Time_days=0;
        for(int i=0;i<p1.getUser_List().size();i++)
        {
            if(p1.getUser_List().get(i).getAccount().equals(Current_User_Name))
            {
                Search_id=i;
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar_now = Calendar.getInstance();calendar_now.getTime();
        JFrame frame = new JFrame("用户");
        frame.setLayout(new BorderLayout());
        frame.setIconImage(User_Window_Icon.getImage());
        frame.setLocation(750,400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(250, 200);
        frame.setResizable(false);
        JButton Modify_Password=new JButton("修改密码");
        JLabel Head_Text=new JLabel("用户信息");Head_Text.setFont(new Font("黑体",Font.BOLD,20));
        JLabel Head_Left_Logo=new JLabel(new ImageIcon("ICON\\icons8-user-64.png"));
        JPanel Head_Area=new JPanel(new GridLayout(1,2));
        JPanel Center_Area=new JPanel(new GridLayout(3,2));
        JLabel NAME_LABEL=new JLabel("          账户名称：");JLabel NAME=new JLabel(Current_User_Name);
        JLabel ASSIGN_TIME_LABEL=new JLabel("          注册时间：");JLabel ASSIGN_TIME=new JLabel(p1.getUser_List().get(Search_id).getAssign_Date().get(Calendar.YEAR)+" 年"+(p1.getUser_List().get(Search_id).getAssign_Date().get(Calendar.MONTH)+1)+" 月"+p1.getUser_List().get(Search_id).getAssign_Date().get(Calendar.DAY_OF_MONTH)+"日");
        JLabel TIME_LABEL=new JLabel("          注册时长：");JLabel TIME=new JLabel(((calendar_now.get(Calendar.YEAR)-p1.getUser_List().get(Search_id).getAssign_Date().get(Calendar.YEAR)))+"年 "+(calendar_now.get(Calendar.MONTH)-(p1.getUser_List().get(Search_id).getAssign_Date().get(Calendar.MONTH)))+"月 "+((calendar_now.get(Calendar.DAY_OF_MONTH)-p1.getUser_List().get(Search_id).getAssign_Date().get(Calendar.DAY_OF_MONTH)))+"日");
        Head_Area.add(Head_Left_Logo);Head_Area.add(Head_Text);
        Center_Area.add(NAME_LABEL);Center_Area.add(NAME);
        Center_Area.add(ASSIGN_TIME_LABEL);Center_Area.add(ASSIGN_TIME);
        Center_Area.add(TIME_LABEL);Center_Area.add(TIME);
        frame.add(Head_Area,BorderLayout.NORTH);
        frame.add(Center_Area,BorderLayout.CENTER);
        frame.add(Modify_Password,BorderLayout.SOUTH);
        frame.setVisible(Visible);
        Modify_Password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Modify_Password_Window(true);
            }
        });
    }

    public static void Modify_Password_Window(boolean Visible)
    {
        JFrame frame = new JFrame("");
        frame.setLayout(new BorderLayout());
        frame.setIconImage(Modify_Password_Window_Icon.getImage());
        frame.setLocation(750,400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(250, 200);
        frame.setResizable(false);
        JLabel Head_Logo=new JLabel(new ImageIcon("ICON\\icons8-key-64.png"));
        JLabel Head_Text=new JLabel("修改登录密码");Head_Text.setFont(new Font("黑体",Font.BOLD,20));
        JLabel Text_1=new JLabel("输入密码：");
        JLabel Text_2=new JLabel("再次输入：");
        JButton Confirm_Password=new JButton("确认修改");
        JTextField First_Enter=new JTextField(8);
        JTextField Second_Enter=new JTextField(8);
        JPanel Head_Area=new JPanel(new GridLayout(1,2));
        JPanel Center_Area=new JPanel(new GridLayout(2,2));
        Head_Area.add(Head_Logo);Head_Area.add(Head_Text);
        Center_Area.add(Text_1);Center_Area.add(First_Enter);
        Center_Area.add(Text_2);Center_Area.add(Second_Enter);
        frame.add(Head_Area,BorderLayout.NORTH);
        frame.add(Center_Area,BorderLayout.CENTER);
        frame.add(Confirm_Password,BorderLayout.SOUTH);
        frame.setVisible(Visible);
        JOptionPane.showMessageDialog(null, "密码为6-10位的字符");
        Confirm_Password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Search_id=-1;
                if(First_Enter.getText().length()>=6&&First_Enter.getText().length()<=6)
                {
                    if(First_Enter.getText().equals(Second_Enter.getText()))
                    {
                        for(int i=0;i<p1.getUser_List().size();i++)
                        {
                            if(p1.getUser_List().get(i).getAccount().equals(Current_User_Name))
                            {
                                Search_id=i;
                            }
                        }
                        p1.getUser_List().get(Search_id).setPassword(First_Enter.getText());
                        saveFiles();
                        JOptionPane.showMessageDialog(null, "修改成功，请重新登录!");
                        frame.dispose();
                        Main_frame.dispose();
                        Login_Window(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"两次输入的密码不相同!", "验证错误", JOptionPane.ERROR_MESSAGE);
                        First_Enter.setText("");Second_Enter.setText("");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"密码格式错误!", "格式错误", JOptionPane.ERROR_MESSAGE);
                    First_Enter.setText("");Second_Enter.setText("");
                }
            }
        });
    }
    public static void Modify_Product_Window(String Product_Sort)
    {
        JFrame frame = new JFrame("");
        frame.setIconImage(Modify_Product_Window_Icon.getImage());
        frame.setSize(180, 320);
        frame.setLocation(750,400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextField Enter_Band=new JTextField(6);
        JTextField Enter_Model=new JTextField(6);
        JTextField Enter_TDP=new JTextField(6);
        JTextField Enter_Frequency=new JTextField(6);
        JTextField Enter_Memory=new JTextField(6);
        JTextField Enter_Power=new JTextField(6);
        JTextField Enter_Technology=new JTextField(6);
        JTextField Enter_Voltage=new JTextField(6);
        JTextField Enter_Platform=new JTextField(6);
        JTextField Enter_Shape=new JTextField(6);
        JLabel Head_Text=new JLabel("修改产品信息");
        Head_Text.setFont(new Font("微软雅黑",Font.BOLD,15));
        JLabel Band=new JLabel("品牌:");JLabel Model=new JLabel("型号:");
        JLabel TDP=new JLabel("TDP:");JLabel Frequency=new JLabel("频率:");
        JLabel Memory=new JLabel("缓存:");JLabel Power=new JLabel("功率:");
        JLabel Technology=new JLabel("制程:");JLabel Voltage=new JLabel("电压:");
        JLabel Platform=new JLabel("适用平台:");JLabel Shape=new JLabel("规格:");
        JButton Confirm_Add=new JButton("修改");
        JPanel Head_Area = new JPanel(new GridLayout(2,1));
        JPanel Choose_Sort_Area = new JPanel(new FlowLayout());
        JPanel Enter_Area = new JPanel(new GridLayout(10,1));
        JPanel Enter_Details_Band_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Model_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_TDP_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Frequency_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Memory_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Power_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Technology_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Voltage_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Platform_Area = new JPanel(new GridLayout(1,2));
        JPanel Enter_Details_Shape_Area = new JPanel(new GridLayout(1,2));
        Head_Area.add(Head_Text);Head_Area.add(Choose_Sort_Area);

        Enter_Details_Band_Area.add(Band);Enter_Details_Band_Area.add(Enter_Band);
        Enter_Details_Model_Area.add(Model);Enter_Details_Model_Area.add(Enter_Model);
        Enter_Details_TDP_Area.add(TDP);Enter_Details_TDP_Area.add(Enter_TDP);
        Enter_Details_Frequency_Area.add(Frequency);Enter_Details_Frequency_Area.add(Enter_Frequency);
        Enter_Details_Memory_Area.add(Memory);Enter_Details_Memory_Area.add(Enter_Memory);
        Enter_Details_Power_Area.add(Power);Enter_Details_Power_Area.add(Enter_Power);
        Enter_Details_Technology_Area.add(Technology);Enter_Details_Technology_Area.add(Enter_Technology);
        Enter_Details_Voltage_Area.add(Voltage);Enter_Details_Voltage_Area.add(Enter_Voltage);
        Enter_Details_Platform_Area.add(Platform);Enter_Details_Platform_Area.add(Enter_Platform);
        Enter_Details_Shape_Area.add(Shape);Enter_Details_Shape_Area.add(Enter_Shape);

        Enter_Area.add(Enter_Details_Band_Area);Enter_Area.add(Enter_Details_Model_Area);
        Enter_Area.add(Enter_Details_TDP_Area);Enter_Area.add(Enter_Details_Frequency_Area);
        Enter_Area.add(Enter_Details_Memory_Area);Enter_Area.add(Enter_Details_Power_Area);
        Enter_Area.add(Enter_Details_Technology_Area);Enter_Area.add(Enter_Details_Voltage_Area);
        Enter_Area.add(Enter_Details_Platform_Area);Enter_Area.add(Enter_Details_Shape_Area);

        Band.setVisible(true);Enter_Band.setVisible(true);
        Model.setVisible(true);Enter_Model.setVisible(true);
        TDP.setVisible(true);Enter_TDP.setVisible(true);
        Frequency.setVisible(true);Enter_Frequency.setVisible(true);
        Memory.setVisible(true);Enter_Memory.setVisible(true);
        Power.setVisible(true);Enter_Power.setVisible(true);
        Technology.setVisible(true);Enter_Technology.setVisible(true);
        Platform.setVisible(true);Enter_Platform.setVisible(true);
        Shape.setVisible(true);Enter_Shape.setVisible(true);
        Voltage.setVisible(true);Enter_Voltage.setVisible(true);
        switch (Product_Sort)
        {
            case "处理器":
                Frequency.setText("主频：");
                Power.setVisible(false);
                Enter_Power.setVisible(false);
                Shape.setText("核心数量：");Voltage.setText("线程数量：");
                Memory.setText("缓存：");
                Platform.setVisible(false);Enter_Platform.setVisible(false);
                break;
            case "显卡":
                Frequency.setText("核心频率：");Memory.setText("显存：");
                Power.setVisible(false);Enter_Power.setVisible(false);
                Voltage.setVisible(false);Enter_Voltage.setVisible(false);
                Shape.setVisible(false);Enter_Shape.setVisible(false);
                Platform.setVisible(false);Enter_Platform.setVisible(false);
                break;
            case "内存条":
                TDP.setVisible(false);Enter_TDP.setVisible(false);
                Power.setVisible(false);Enter_Power.setVisible(false);
                Technology.setVisible(false);Enter_Technology.setVisible(false);
                Voltage.setText("电压：");
                Shape.setVisible(false);Enter_Shape.setVisible(false);
                Platform.setVisible(false);Enter_Platform.setVisible(false);
                Memory.setText("内存容量：");
                break;
            case "硬盘":
                Frequency.setVisible(false);Enter_Frequency.setVisible(false);
                TDP.setVisible(false);Enter_TDP.setVisible(false);
                Power.setVisible(false);Enter_Power.setVisible(false);
                Technology.setVisible(false);Enter_Technology.setVisible(false);
                Voltage.setVisible(false);Enter_Voltage.setVisible(false);
                Platform.setVisible(false);Enter_Platform.setVisible(false);
                Memory.setText("容量：");Shape.setText("硬盘类型：");
                break;
            case "电源":
                Frequency.setVisible(false);Enter_Frequency.setVisible(false);
                TDP.setVisible(false);Enter_TDP.setVisible(false);
                Technology.setVisible(false);Enter_Technology.setVisible(false);
                Voltage.setVisible(false);Enter_Voltage.setVisible(false);
                Memory.setVisible(false);Enter_Memory.setVisible(false);
                Platform.setVisible(false);Enter_Platform.setVisible(false);
                Shape.setText("电源规格：");
                break;
            case "主板":
                Frequency.setVisible(false);Enter_Frequency.setVisible(false);
                TDP.setVisible(false);Enter_TDP.setVisible(false);
                Memory.setVisible(false);Enter_Memory.setVisible(false);
                Technology.setVisible(false);Enter_Technology.setVisible(false);
                Voltage.setVisible(false);Enter_Voltage.setVisible(false);
                Power.setVisible(false);Enter_Power.setVisible(false);
                Shape.setText("主板规格：");
                break;
        }

        frame.add(Head_Area,BorderLayout.NORTH);frame.add(Enter_Area,BorderLayout.EAST);
        frame.add(Confirm_Add,BorderLayout.SOUTH);
        frame.setVisible(true);



        Confirm_Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String Temp_Model="";String Temp_Band="";int Search_id=-1;
                int Search_CPU=-1,Search_GPU=-1,Search_RAM=-1,Search_Storage=-1,Search_Power=-1,Search_MotherBoard=-1;
                if(Table_Listener_Row!=-1)
                {
                    Temp_Model=(String) Table.getValueAt(Table_Listener_Row,1);
                    Temp_Band=(String)Table.getValueAt(Table_Listener_Row,0);
                    switch (Product_Sort)
                    {
                        case "处理器":
                            if(!Objects.equals(Enter_Band.getText(), "")&&!Objects.equals(Enter_Model.getText(), "")&& !Objects.equals(Enter_Frequency.getText(), "")&& !Objects.equals(Enter_Memory.getText(), "")&& !Objects.equals(Enter_Technology.getText(), "")&& !Objects.equals(Enter_TDP.getText(), ""))
                            {
                                CPU New_CPU=new CPU(Enter_Band.getText(),Enter_Model.getText(),Enter_Frequency.getText(),Enter_Memory.getText(),Enter_Shape.getText(),Enter_Voltage.getText(),Enter_Technology.getText(),Enter_TDP.getText());
                                p1.Add_Product("处理器",New_CPU);
                                Modify_SIGN=1;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "检测到有未填写信息！","警告" ,JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        case "显卡":
                            if(!Objects.equals(Enter_Band.getText(), "")&&!Objects.equals(Enter_Model.getText(), "")&& !Objects.equals(Enter_Frequency.getText(), "")&& !Objects.equals(Enter_Memory.getText(), "")&& !Objects.equals(Enter_Technology.getText(), "")&& !Objects.equals(Enter_TDP.getText(), ""))
                            {
                                GPU New_GPU=new GPU(Enter_Band.getText(),Enter_Model.getText(),Enter_Memory.getText(),Enter_Frequency.getText(),Enter_Technology.getText(),Enter_TDP.getText());
                                p1.Add_Product("显卡",New_GPU);
                                Modify_SIGN=1;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "检测到有未填写信息！","警告" ,JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        case "内存条":
                            if(!Objects.equals(Enter_Band.getText(), "")&&!Objects.equals(Enter_Model.getText(), "")&& !Objects.equals(Enter_Frequency.getText(), "")&& !Objects.equals(Enter_Memory.getText(), "")&&!Objects.equals(Enter_Voltage.getText(), ""))
                            {
                                RAM New_RAM=new RAM(Enter_Band.getText(),Enter_Model.getText(),Enter_Memory.getText(),Enter_Frequency.getText(),Enter_Voltage.getText());
                                p1.Add_Product("内存条",New_RAM);
                                Modify_SIGN=1;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "检测到有未填写信息！", "警告", JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        case "硬盘":
                            if(!Objects.equals(Enter_Band.getText(), "")&&!Objects.equals(Enter_Model.getText(), "")&& !Objects.equals(Enter_Memory.getText(), "")&& !Objects.equals(Enter_Shape.getText(), ""))
                            {
                                Storage New_Storage=new Storage(Enter_Band.getText(),Enter_Model.getText(),Enter_Shape.getText(),Enter_Memory.getText());
                                p1.Add_Product("硬盘",New_Storage);
                                Modify_SIGN=1;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "检测到有未填写信息！","警告" ,JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        case "电源":
                            if(!Objects.equals(Enter_Band.getText(), "")&&!Objects.equals(Enter_Model.getText(), "")&& !Objects.equals(Enter_Power.getText(), "")&& !Objects.equals(Enter_Shape.getText(), ""))
                            {
                                Power New_Power=new Power(Enter_Band.getText(),Enter_Model.getText(),Enter_Power.getText(),Enter_Shape.getText());
                                p1.Add_Product("电源",New_Power);
                                Modify_SIGN=1;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "检测到有未填写信息！","警告" ,JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        case "主板":
                            if(!Objects.equals(Enter_Band.getText(), "")&&!Objects.equals(Enter_Model.getText(), "")&& !Objects.equals(Enter_Platform.getText(), "")&& !Objects.equals(Enter_Shape.getText(), ""))
                            {
                                MotherBoard New_MotherBoard=new MotherBoard(Enter_Band.getText(),Enter_Model.getText(),Enter_Platform.getText(),Enter_Shape.getText());
                                p1.Add_Product("主板",New_MotherBoard);
                                Modify_SIGN=1;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "检测到有未填写信息！","警告" ,JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                    }
                    switch (Product_Sort)
                    {
                        case "处理器":
                            for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                                if (p1.getCPU_Product().get(i).getCPU_Model().equals(Temp_Model)&&p1.getCPU_Product().get(i).getCPU_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            p1.getCPU_Product().remove(Search_id);
                            Search_id = -1;
                            for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                                if (p1.getCPU_Product().get(i).getCPU_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            if (Search_id == -1) {
                                for (int i = 0; i < p1.getCPU_Band_List().size(); i++) {
                                    if (p1.getCPU_Band_List().get(i).equals(Temp_Band)) {
                                        p1.getCPU_Band_List().remove(i);
                                        break;
                                    }
                                }
                            }
                            break;
                        case "显卡":
                            for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                                if (p1.getGPU_Product().get(i).getGPU_Model().equals(Temp_Model)&&p1.getGPU_Product().get(i).getGPU_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            p1.getGPU_Product().remove(Search_id);
                            Search_id = -1;
                            for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                                if (p1.getGPU_Product().get(i).getGPU_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            if (Search_id == -1) {
                                for (int i = 0; i < p1.getGPU_Band_List().size(); i++) {
                                    if (p1.getGPU_Band_List().get(i).equals(Temp_Band)) {
                                        p1.getGPU_Band_List().remove(i);
                                        break;
                                    }
                                }
                            }
                            break;
                        case "内存":
                            for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                                if (p1.getRAM_Product().get(i).getRAM_Model().equals(Temp_Model)&&p1.getRAM_Product().get(i).getRAM_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            p1.getRAM_Product().remove(Search_id);
                            Search_id = -1;
                            for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                                if (p1.getRAM_Product().get(i).getRAM_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            if (Search_id == -1) {
                                for (int i = 0; i < p1.getRAM_Band_List().size(); i++) {
                                    if (p1.getRAM_Band_List().get(i).equals(Temp_Band)) {
                                        p1.getRAM_Band_List().remove(i);
                                        break;
                                    }
                                }
                            }
                            break;
                        case "硬盘":
                            for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                                if (p1.getStorage_Product().get(i).getStorage_Model().equals(Temp_Model)&&p1.getStorage_Product().get(i).getStorage_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            p1.getStorage_Product().remove(Search_id);
                            Search_id = -1;
                            for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                                if (p1.getStorage_Product().get(i).getStorage_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            if (Search_id == -1) {
                                for (int i = 0; i < p1.getStorage_Band_List().size(); i++) {
                                    if (p1.getStorage_Band_List().get(i).equals(Temp_Band)) {
                                        p1.getStorage_Band_List().remove(i);
                                        break;
                                    }
                                }
                            }
                            break;
                        case "电源":
                            for (int i = 0; i < p1.getPower_Product().size(); i++) {
                                if (p1.getPower_Product().get(i).getPower_Model().equals(Temp_Model)&&p1.getPower_Product().get(i).getPower_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            p1.getPower_Product().remove(Search_id);
                            Search_id = -1;
                            for (int i = 0; i < p1.getPower_Product().size(); i++) {
                                if (p1.getPower_Product().get(i).getPower_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            if (Search_id == -1) {
                                for (int i = 0; i < p1.getPower_Band_List().size(); i++) {
                                    if (p1.getPower_Band_List().get(i).equals(Temp_Band)) {
                                        p1.getPower_Band_List().remove(i);
                                        break;
                                    }
                                }
                            }
                            break;
                        case "主板":
                            for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                                if (p1.getMotherBoard_Product().get(i).getMotherBoard_Model().equals(Temp_Model)&&p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            p1.getMotherBoard_Product().remove(Search_id);
                            Search_id = -1;
                            for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                                if (p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(Temp_Band)) {
                                    Search_id = i;
                                }
                            }
                            if (Search_id == -1) {
                                for (int i = 0; i < p1.getMotherBoard_Band_List().size(); i++) {
                                    if (p1.getMotherBoard_Band_List().get(i).equals(Temp_Band)) {
                                        p1.getMotherBoard_Band_List().remove(i);
                                        break;
                                    }
                                }
                            }
                            break;
                    }
                    for (int i = 0; i < p1.getCPU_Product().size(); i++) {
                        if (p1.getCPU_Product().get(i).getCPU_Band().equals(Temp_Band)) {
                            Search_CPU = i;
                        }
                    }
                    for (int i = 0; i < p1.getGPU_Product().size(); i++) {
                        if (p1.getGPU_Product().get(i).getGPU_Band().equals(Temp_Band)) {
                            Search_GPU = i;
                        }
                    }
                    for (int i = 0; i < p1.getRAM_Product().size(); i++) {
                        if (p1.getRAM_Product().get(i).getRAM_Band().equals(Temp_Band)) {
                            Search_RAM = i;
                        }
                    }
                    for (int i = 0; i < p1.getStorage_Product().size(); i++) {
                        if (p1.getStorage_Product().get(i).getStorage_Band().equals(Temp_Band)) {
                            Search_Storage = i;
                        }
                    }
                    for (int i = 0; i < p1.getPower_Product().size(); i++) {
                        if (p1.getPower_Product().get(i).getPower_Band().equals(Temp_Band)) {
                            Search_Power = i;
                        }
                    }
                    for (int i = 0; i < p1.getMotherBoard_Product().size(); i++) {
                        if (p1.getMotherBoard_Product().get(i).getMotherBoard_Band().equals(Temp_Band)) {
                            Search_MotherBoard = i;
                        }
                    }
                    if (Search_CPU == -1 && Search_GPU == -1 && Search_RAM == -1 && Search_Storage == -1 && Search_Power == -1 && Search_MotherBoard == -1) {
                        for (int i = 0; i < p1.getBand_List().size(); i++) {
                            if (p1.getBand_List().get(i).equals(Temp_Band)) {
                                p1.getBand_List().remove(i);
                                break;
                            }
                        }
                    }
                    Table_Listener_Row = -1;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "请先在表格中选中要修改的产品！" );
                }
                if(Modify_SIGN!=-1)
                {
                    saveFiles();
                    Modify_SIGN=-1;
                    JOptionPane.showMessageDialog(null, "修改成功！请选择任意“产品类型”更新表格数据");
                    frame.dispose();
                }
                else
                {
                    loadFiles();
                }
                Enter_Band.setText("");Enter_Model.setText("");Enter_Frequency.setText("");Enter_Memory.setText("");
                Enter_Voltage.setText("");Enter_TDP.setText("");Enter_Power.setText("");Enter_Technology.setText("");
                Enter_Shape.setText("");Enter_Platform.setText("");
            }
        });
    }
    public static void Search_Window(boolean Visible)
    {
        JTable Search_Table = new JTable();
        Search_Table.setColumnSelectionAllowed(false);Search_Table.setRowSelectionAllowed(false); //防止误删
        JScrollPane Search_Scroller = new JScrollPane(Search_Table);
        Search_Table.setPreferredScrollableViewportSize(new Dimension(500, 450));
        JFrame frame = new JFrame("搜索产品");
        //frame.setIconImage(Add_Window_Icon.getImage());
        frame.setSize(500, 350);
        frame.setLocation(580,400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] Sort_List={"请选择","处理器","显卡","内存","硬盘","电源","主板"};
        JLabel SORT_SIGN=new JLabel("");
        JLabel Head_Text=new JLabel("搜索产品信息");Head_Text.setFont(new Font("微软雅黑",Font.BOLD,15));
        JLabel Head_Logo=new JLabel("");
        JLabel Text_1=new JLabel("请输入产品型号：");
        JLabel Text_2=new JLabel("请选择产品类型：");
        JComboBox<String> Select_Sort=new JComboBox<>(Sort_List);
        JTextField Enter_Model=new JTextField(10);
        JButton Search_Button=new JButton("搜索");
        JPanel Head_Area=new JPanel(new GridLayout(3,1));
        JPanel Head_Logo_Area=new JPanel(new GridLayout(1,2));
        JPanel Enter_Area=new JPanel(new FlowLayout());
        JPanel Select_Sort_Area=new JPanel(new FlowLayout());
        Head_Logo_Area.add(Head_Logo);Head_Logo_Area.add(Head_Text);
        Enter_Area.add(Text_1);Enter_Area.add(Enter_Model);
        Select_Sort_Area.add(Text_2);Select_Sort_Area.add(Select_Sort);
        Head_Area.add(Head_Logo_Area);
        Head_Area.add(Select_Sort_Area);
        Head_Area.add(Enter_Area);
        frame.add(Head_Area,BorderLayout.NORTH);
        frame.add(Search_Scroller,BorderLayout.CENTER); //将滚动表格面板添加到窗口面板上
        frame.add(Search_Button,BorderLayout.SOUTH);
        frame.setVisible(Visible);
        Search_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Search_Ans=0;
                if(SORT_SIGN.getText().equals("请选择"))
                {
                    JOptionPane.showMessageDialog(null,"请先选择要查询的产品类别！");
                }
                else
                {
                    DefaultTableModel model = new DefaultTableModel();
                    if(Enter_Model.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"请输入产品型号！");
                    }
                    else
                    {
                        switch (SORT_SIGN.getText())
                        {
                            case "处理器":
                                model.addColumn("品牌");
                                model.addColumn("型号");
                                model.addColumn("主频");
                                model.addColumn("缓存大小");
                                model.addColumn("核心数量");
                                model.addColumn("线程数量");
                                model.addColumn("制作工艺");
                                model.addColumn("TDP");
                                for(int i=0;i<p1.getCPU_Product().size();i++)
                                {
                                    if(p1.getCPU_Product().get(i).getCPU_Model().equals(Enter_Model.getText()))
                                    {
                                        Search_Ans++;
                                        model.addRow(new Object[]{p1.getCPU_Product().get(i).getCPU_Band(),p1.getCPU_Product().get(i).getCPU_Model(),p1.getCPU_Product().get(i).getCPU_Clock(),p1.getCPU_Product().get(i).getCPU_Cache(),p1.getCPU_Product().get(i).getCPU_Core(),p1.getCPU_Product().get(i).getCPU_Thread(),p1.getCPU_Product().get(i).getCPU_Technology(),p1.getCPU_Product().get(i).getCPU_TDP()});
                                    }
                                }
                                Search_Table.setModel(model);
                                break;
                            case "显卡":
                                model.addColumn("品牌");
                                model.addColumn("型号");
                                model.addColumn("核心频率");
                                model.addColumn("显存");
                                model.addColumn("制作工艺");
                                model.addColumn("TDP");
                                for(int i=0;i<p1.getGPU_Product().size();i++)
                                {
                                    if(p1.getGPU_Product().get(i).getGPU_Model().equals(Enter_Model.getText()))
                                    {
                                        Search_Ans++;
                                        model.addRow(new Object[]{p1.getGPU_Product().get(i).getGPU_Band(),p1.getGPU_Product().get(i).getGPU_Model(),p1.getGPU_Product().get(i).getGPU_Clock(),p1.getGPU_Product().get(i).getGPU_Memory(),p1.getGPU_Product().get(i).getGPU_Technology(),p1.getGPU_Product().get(i).getGPU_TDP()});
                                    }
                                }
                                Search_Table.setModel(model);
                                break;
                            case "内存条":
                                model.addColumn("品牌");
                                model.addColumn("型号");
                                model.addColumn("内存容量");
                                model.addColumn("内存频率");
                                model.addColumn("电压");
                                for(int i=0;i<p1.getRAM_Product().size();i++)
                                {
                                    if(p1.getRAM_Product().get(i).getRAM_Model().equals(Enter_Model.getText()))
                                    {
                                        Search_Ans++;
                                        model.addRow(new Object[]{p1.getRAM_Product().get(i).getRAM_Band(),p1.getRAM_Product().get(i).getRAM_Model(),p1.getRAM_Product().get(i).getRAM_Size(),p1.getRAM_Product().get(i).getRAM_Frequency(),p1.getRAM_Product().get(i).getRAM_Voltage()});
                                    }
                                }
                                Search_Table.setModel(model);
                                break;
                            case "硬盘":
                                model.addColumn("品牌");
                                model.addColumn("型号");
                                model.addColumn("硬盘类型");
                                model.addColumn("硬盘容量");
                                for(int i=0;i<p1.getStorage_Product().size();i++)
                                {
                                    if(p1.getStorage_Product().get(i).getStorage_Model().equals(Enter_Model.getText()))
                                    {
                                        Search_Ans++;
                                        model.addRow(new Object[]{p1.getStorage_Product().get(i).getStorage_Band(),p1.getStorage_Product().get(i).getStorage_Model(),p1.getStorage_Product().get(i).getStorage_Method(),p1.getStorage_Product().get(i).getStorage_Capacity()});
                                    }
                                }
                                Search_Table.setModel(model);
                                break;
                            case "电源":
                                model.addColumn("品牌");
                                model.addColumn("型号");
                                model.addColumn("电源功率");
                                model.addColumn("电源规格");
                                for(int i=0;i<p1.getPower_Product().size();i++)
                                {
                                    if(p1.getPower_Product().get(i).getPower_Model().equals(Enter_Model.getText()))
                                    {
                                        Search_Ans++;
                                        model.addRow(new Object[]{p1.getPower_Product().get(i).getPower_Band(),p1.getPower_Product().get(i).getPower_Model(),p1.getPower_Product().get(i).getPower_Limit(),p1.getPower_Product().get(i).getPower_Class()});
                                    }
                                }
                                Search_Table.setModel(model);
                                break;
                            case "主板":
                                model.addColumn("品牌");
                                model.addColumn("型号");
                                model.addColumn("适用平台");
                                model.addColumn("主板规格");
                                for(int i=0;i<p1.getMotherBoard_Product().size();i++)
                                {
                                    if(p1.getMotherBoard_Product().get(i).getMotherBoard_Model().equals(Enter_Model.getText()))
                                    {
                                        Search_Ans++;
                                        model.addRow(new Object[]{p1.getMotherBoard_Product().get(i).getMotherBoard_Band(),p1.getMotherBoard_Product().get(i).getMotherBoard_Model(),p1.getMotherBoard_Product().get(i).getMotherBoard_Platform(),p1.getMotherBoard_Product().get(i).getMotherBoard_Shape()});
                                    }
                                }
                                Search_Table.setModel(model);
                                break;
                        }
                        if(Search_Ans==0)
                        {
                            JOptionPane.showMessageDialog(null,"未搜索到相关产品信息！");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"共搜索到"+Search_Ans+"个产品。");
                        }
                    }
                }
            }
        });
        Select_Sort.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    SORT_SIGN.setText(e.getItem().toString());
                }
            }
        });
    }

    //加载数据文件
    public static void loadFiles() {
        try (FileInputStream fileIn = new FileInputStream(file_path);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            p1 = (Product_Management) in.readObject();
        } catch (IOException i) {
            p1=Product_Management.Get_Product_Management();
            saveFiles();
        } catch (ClassNotFoundException c) {
            JOptionPane.showMessageDialog(null, "操作错误！" + c.getMessage());
        }
    }
    public static void saveFiles() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path))) {
            oos.writeObject(p1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "操作错误！" + e.getMessage());
        }
    }

}
