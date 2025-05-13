package bean;

public class Student {
    private String no;         // 学生番号（文字列）
    private String name;       // 氏名
    private int entYear;    // 入学年度（文字列）
    private String classNum;      // クラス番号
    private boolean isAttend;  // 在学フラグ
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

    public int getEntYear() {
        return entYear;
    }
    public void setEntYear(int ent_Year) {
        this.entYear = ent_Year;
    }

    public String getClassNum() {
        return classNum;
    }
    public void setClassNum(String class_Num) {
        this.classNum = class_Num;
    }

    public boolean is_Attend() {
        return isAttend;
    }
    public void setAttend(boolean is_Attend) {
        this.isAttend = is_Attend;
    }

    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
}
