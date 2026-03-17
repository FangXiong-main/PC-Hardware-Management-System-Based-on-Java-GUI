PC 硬件管理系统

项目概述

本项目是基于 Java Swing 开发的桌面端 PC 硬件管理系统，支持对 CPU、GPU、主板、电源、内存、存储设备等核心 PC 硬件的信息管理（增删改查），同时包含用户身份标识与数据本地持久化能力，适用于小型硬件库存管理、个人硬件台账记录等场景。
技术栈

核心语言：Java（JDK 8+）
GUI 框架：Java Swing（桌面界面开发）
数据持久化：Java 对象序列化（本地DataBase.dat文件存储）
基础特性：JavaBean 规范、集合框架（List/Map）、事件监听机制
项目结构

plaintext

GUI_Project4/
└── src/
    └── com/fangxiong/GUI/
        ├── 硬件实体类
        │   ├── CPU.java        // CPU属性封装
        │   ├── GPU.java        // GPU属性封装
        │   ├── MotherBoard.java // 主板属性封装
        │   ├── Power.java      // 电源属性封装
        │   ├── RAM.java        // 内存属性封装
        │   ├── Storage.java    // 存储设备（硬盘/SSD）属性封装
        ├── 用户实体类
        │   └── User.java       // 用户账号、密码、创建时间封装
        └── 核心业务类
            └── PC_Base_Management.java // 主界面+硬件增删改查核心逻辑
核心类说明

1. 实体类（JavaBean）

所有实体类均实现Serializable接口，支持对象序列化存储，遵循 “私有属性 + 全参构造 + Getter/Setter” 的 JavaBean 规范：
表格
类名	核心属性
CPU	品牌、型号、核心数、主频、制程工艺等
GPU	品牌、型号、显存容量、核心频率、接口类型等
MotherBoard	品牌、型号、支持平台（Intel/AMD）、版型（ATX/M-ATX 等）
Power	品牌、型号、额定功率、电源类型（ATX/1U 等）
RAM	品牌、型号、容量、频率、电压
Storage	品牌、型号、存储类型（HDD/SSD）、容量、接口类型等
User	账号（Account）、密码（Password）、创建时间（Assign_Date，Calendar 类型）
2. 核心业务类

PC_Base_Management.java

系统 “中枢模块”，负责：
主窗口（JFrame）、表格（JTable）、按钮等 Swing 组件的创建与布局；
硬件数据的增删改查（CRUD）业务逻辑实现；
表格选中行监听、按钮点击事件处理；
本地DataBase.dat文件的读写（序列化 / 反序列化）；
当前登录用户标识管理。
核心功能

硬件信息管理：支持 CPU、GPU、主板、电源、内存、存储设备的新增、修改、删除、查询展示；
数据持久化：所有硬件 / 用户数据序列化存储到本地DataBase.dat，重启程序后数据不丢失；
用户标识：记录当前登录用户名，区分操作身份；
可视化界面：基于 Swing 实现直观的表格展示、按钮操作、弹窗交互。
运行说明

环境要求

安装 JDK 8 及以上版本；
确保项目中ICON目录（图标资源）路径正确（若使用图标）；
运行目录需有文件读写权限（用于生成 / 读取DataBase.dat）。
启动方式

编译所有 Java 文件：
bash


运行




javac -d bin src/com/fangxiong/GUI/*.java
运行核心业务类（主入口）：
bash


运行




java com.fangxiong.GUI.PC_Base_Management
代码规范与优化建议

现有规范

所有实体类统一放在com.fangxiong.GUI包，模块化统一；
私有属性封装，通过 Getter/Setter 访问，符合面向对象设计；
序列化统一实现Serializable，保证数据持久化一致性。
优化方向

命名规范：实体类属性当前使用Power_Band（下划线 + 大驼峰），建议改为 Java 标准小驼峰（如powerBrand）；
参数校验：在 Setter 方法 / 构造方法中增加参数非空、格式校验（如功率不能为非数字、容量格式合法）；
异常处理：文件读写（序列化）、界面操作增加 try-catch 异常捕获，提升程序稳定性；
分层设计：拆分PC_Base_Management的 “界面展示” 和 “业务逻辑”，解耦界面与数据操作；
密码安全：User 类中密码明文存储，建议增加 MD5/SHA 加密；
注释补充：核心业务方法、复杂逻辑处补充中文注释，提升可读性。
扩展方向

增加用户登录 / 注册功能，区分管理员 / 普通用户权限；
支持硬件数据导出为 Excel/CSV 格式；
增加硬件参数合法性校验（如 CPU 主频范围、电源功率匹配性）；
替换本地文件存储为 MySQL 数据库，提升数据管理能力；
优化界面美观度（如使用 FlatLaf 等 Swing 美化库）。


# PC Hardware Management System - Project Documentation
## Project Overview
This project is a desktop-based PC hardware management system developed with Java Swing. It supports the management (add, delete, modify, query) of core PC hardware information including CPU, GPU, motherboard, power supply, RAM, and storage devices. It also includes user identity authentication and local data persistence capabilities, making it suitable for scenarios such as small-scale hardware inventory management and personal hardware ledger recording.

## Technology Stack
- **Core Language**: Java (JDK 8+)
- **GUI Framework**: Java Swing (desktop interface development)
- **Data Persistence**: Java Object Serialization (stored in local `DataBase.dat` file)
- **Basic Features**: JavaBean specification, Collection Framework (List/Map), Event Listening Mechanism

## Project Structure
```
GUI_Project4/
└── src/
    └── com/fangxiong/GUI/
        ├── Hardware Entity Classes
        │   ├── CPU.java        // CPU attribute encapsulation
        │   ├── GPU.java        // GPU attribute encapsulation
        │   ├── MotherBoard.java // Motherboard attribute encapsulation
        │   ├── Power.java      // Power supply attribute encapsulation
        │   ├── RAM.java        // RAM attribute encapsulation
        │   ├── Storage.java    // Storage device (HDD/SSD) attribute encapsulation
        ├── User Entity Class
        │   └── User.java       // Encapsulation of user account, password, creation time
        └── Core Business Class
            └── PC_Base_Management.java // Main interface + core logic for hardware CRUD
```

## Core Class Explanation
### 1. Entity Classes (JavaBean)
All entity classes implement the `Serializable` interface to support object serialization storage, and follow the JavaBean specification of "private attributes + full-parameter constructor + Getter/Setter":

| Class Name   | Core Attributes                                                                 |
|--------------|---------------------------------------------------------------------------------|
| CPU          | Brand, Model, Core Count, Frequency, Process Technology, etc.                  |
| GPU          | Brand, Model, Video Memory Capacity, Core Frequency, Interface Type, etc.       |
| MotherBoard  | Brand, Model, Supported Platform (Intel/AMD), Form Factor (ATX/M-ATX, etc.)     |
| Power        | Brand, Model, Rated Power, Power Type (ATX/1U, etc.)                            |
| RAM          | Brand, Model, Capacity, Frequency, Voltage                                      |
| Storage      | Brand, Model, Storage Type (HDD/SSD), Capacity, Interface Type, etc.            |
| User         | Account, Password, Creation Time (Assign_Date, Calendar type)                   |

### 2. Core Business Class
#### PC_Base_Management.java
The "central module" of the system, responsible for:
- Creation and layout of Swing components such as main window (JFrame), table (JTable), and buttons;
- Implementation of CRUD (Create, Read, Update, Delete) business logic for hardware data;
- Listening to table selected rows and handling button click events;
- Reading/writing the local `DataBase.dat` file (serialization/deserialization);
- Management of the current logged-in user identity.

## Core Functions
1. **Hardware Information Management**: Supports adding, modifying, deleting, querying and displaying CPU, GPU, motherboard, power supply, RAM, and storage devices;
2. **Data Persistence**: All hardware/user data is serialized and stored in the local `DataBase.dat` file, ensuring data is not lost after restarting the program;
3. **User Identification**: Records the current logged-in username to distinguish operation identities;
4. **Visual Interface**: Intuitive table display, button operations, and pop-up interaction implemented based on Swing.

## Running Instructions
### Environment Requirements
- JDK 8 or above installed;
- Ensure the path of the `ICON` directory (icon resources) in the project is correct (if icons are used);
- The running directory must have file read/write permissions (for generating/reading `DataBase.dat`).

### Startup Method
1. Compile all Java files:
   ```bash
   javac -d bin src/com/fangxiong/GUI/*.java
   ```
2. Run the core business class (main entry):
   ```bash
   java com.fangxiong.GUI.PC_Base_Management
   ```

## Code Specifications and Optimization Suggestions
### Existing Specifications
- All entity classes are uniformly placed in the `com.fangxiong.GUI` package for modular unification;
- Private attribute encapsulation, accessed through Getter/Setter, conforming to object-oriented design;
- Serialization uniformly implements `Serializable` to ensure consistency of data persistence.

### Optimization Directions
1. **Naming Conventions**: Current entity class attributes use `Power_Band` (underscore + upper camel case); it is recommended to switch to Java standard lower camel case (e.g., `powerBrand`);
2. **Parameter Validation**: Add non-null and format validation for parameters in Setter methods/constructors (e.g., power cannot be non-numeric, capacity format is legal);
3. **Exception Handling**: Add try-catch exception capture for file reading/writing (serialization) and interface operations to improve program stability;
4. **Layered Design**: Split the "interface display" and "business logic" of `PC_Base_Management` to decouple the interface from data operations;
5. **Password Security**: Passwords in the User class are stored in plain text; it is recommended to add MD5/SHA encryption;
6. **Comment Supplement**: Add Chinese/English comments to core business methods and complex logic to improve readability.

## Expansion Directions
1. Add user login/registration functions to distinguish administrator/ordinary user permissions;
2. Support exporting hardware data to Excel/CSV format;
3. Add legality verification of hardware parameters (e.g., CPU frequency range, power supply power matching);
4. Replace local file storage with MySQL database to improve data management capabilities;
5. Optimize interface aesthetics (e.g., use Swing beautification libraries such as FlatLaf).
