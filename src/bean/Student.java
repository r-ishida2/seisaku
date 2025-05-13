package bean;

public class Student {
    private String no;         // 学生番号（文字列）
    private String name;       // 氏名
    private int ent_Year;    // 入学年度（文字列）
    private String class_Num;      // クラス番号
    private boolean is_Attend;  // 在学フラグ
    private School school;     // 所属学校

    // ゲッターとセッター
    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getEnt_Year() {
        return ent_Year;
    }
    public void setEnt_Year(int ent_Year) {
        this.ent_Year = ent_Year;
    }

    public String getClass_Num() {
        return class_Num;
    }
    public void setClass_Num(String class_Num) {
        this.class_Num = class_Num;
    }

    public boolean is_Attend() {
        return is_Attend;
    }
    public void setAttend(boolean is_Attend) {
        this.is_Attend = is_Attend;
    }

    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
}
