package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    private final String BASE_SQL = "SELECT * FROM STUDENT";

    // 学生1人取得
    public Student get(String no) {
        Student student = null;

        try (Connection con = getConnection()) {
            String sql = BASE_SQL + " WHERE no = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, no);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                student = postFilter(rs, null); // 学校情報はnullでも可
            }
        } catch (Exception e) {
            System.err.println("Student get() error: " + e.getMessage());
        }

        return student;
    }

    // 学生一覧取得（学校 + 年度 + クラス + 在籍）※classNumが空でもOK
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SQL + " WHERE school_cd = ? AND ent_year = ? AND is_attend = ?");
        boolean hasClassNum = classNum != null && !classNum.trim().isEmpty();

        if (hasClassNum) {
            sql.append(" AND class_num = ?");
        }

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {

            int index = 1;
            st.setString(index++, school.getCd());
            st.setInt(index++, entYear);
            st.setBoolean(index++, isAttend);
            if (hasClassNum) {
                st.setString(index, classNum);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Student student = postFilter(rs, school);
                list.add(student);
                // デバッグ用に生徒情報を出力
                System.out.println("デバッグ - 学生番号: " + student.getNo() +
                                   ", 氏名: " + student.getName() +
                                   ", 入学年度: " + student.getEntYear() +
                                   ", クラス: " + student.getClassNum() +
                                   ", 在学中: " + student.is_Attend());
            }

        } catch (Exception e) {
            System.err.println("Student filter(school, year, class, attend) error: " + e.getMessage());
        }

        return list;
    }

    // 学生一覧取得（学校 + 年度 + 在籍）
    public List<Student> filter(School school, int ent_Year, boolean is_Attend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + " WHERE school_cd = ? AND ent_year = ? AND is_attend = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());
            st.setInt(2, ent_Year);
            st.setBoolean(3, is_Attend);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Student student = postFilter(rs, school);
                list.add(student);
                // デバッグ用に生徒情報を出力
                System.out.println("デバッグ - 学生番号: " + student.getNo() +
                                   ", 氏名: " + student.getName() +
                                   ", 入学年度: " + student.getEntYear() +
                                   ", クラス: " + student.getClassNum() +
                                   ", 在学中: " + student.is_Attend());
            }

        } catch (Exception e) {
            System.err.println("Student filter(school, year, attend) error: " + e.getMessage());
        }

        return list;
    }

    // 学生一覧取得（学校 + 在籍）
    public List<Student> filter(School school, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + " WHERE school_cd = ? AND is_attend = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());
            st.setBoolean(2, isAttend);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Student student = postFilter(rs, school);
                list.add(student);
                // デバッグ用に生徒情報を出力
                System.out.println("デバッグ - 学生番号: " + student.getNo() +
                                   ", 氏名: " + student.getName() +
                                   ", 入学年度: " + student.getEntYear() +
                                   ", クラス: " + student.getClassNum() +
                                   ", 在学中: " + student.is_Attend());
            }

        } catch (Exception e) {
            System.err.println("Student filter(school, attend) error: " + e.getMessage());
        }

        return list;
    }

    // 学生情報保存（INSERT or UPDATE）
    public boolean save(Student student) {
        boolean result = false;

        try (Connection con = getConnection()) {
            // まず存在確認
            String checkSql = "SELECT COUNT(*) FROM STUDENT WHERE no = ?";
            try (PreparedStatement checkSt = con.prepareStatement(checkSql)) {
                checkSt.setString(1, student.getNo());
                ResultSet rs = checkSt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    // UPDATE
                    String updateSql = "UPDATE STUDENT SET name = ?, ent_year = ?, class_num = ?, is_attend = ?, school_cd = ? WHERE no = ?";
                    try (PreparedStatement st = con.prepareStatement(updateSql)) {
                        st.setString(1, student.getName());
                        st.setInt(2, student.getEntYear());
                        st.setString(3, student.getClassNum());
                        st.setBoolean(4, student.is_Attend());
                        st.setString(5, student.getSchool().getCd());
                        st.setString(6, student.getNo());

                        result = (st.executeUpdate() == 1);
                    }
                } else {
                    // INSERT
                    String insertSql = "INSERT INTO STUDENT(no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement st = con.prepareStatement(insertSql)) {
                        st.setString(1, student.getNo());
                        st.setString(2, student.getName());
                        st.setInt(3, student.getEntYear());
                        st.setString(4, student.getClassNum());
                        st.setBoolean(5, student.is_Attend());
                        st.setString(6, student.getSchool().getCd());

                        result = (st.executeUpdate() == 1);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Student save() error: " + e.getMessage());
        }

        return result;
    }

    // ResultSet → Student オブジェクト変換処理
    private Student postFilter(ResultSet rs, School school) {
        Student s = new Student();
        try {
            s.setNo(rs.getString("no"));
            s.setName(rs.getString("name"));
            s.setEntYear(rs.getInt("entyear"));
            s.setClassNum(rs.getString("classnum"));
            s.setAttend(rs.getBoolean("isattend"));

            if (school != null) {
                s.setSchool(school);
            } else {
                School sch = new School();
                sch.setCd(rs.getString("school_cd"));
                s.setSchool(sch);
            }

            // デバッグ用にStudentオブジェクトの内容を出力
            System.out.println("デバッグ - 学生番号: " + s.getNo() +
                               ", 氏名: " + s.getName() +
                               ", 入学年度: " + s.getEntYear() +
                               ", クラス: " + s.getClassNum() +
                               ", 在学中: " + s.is_Attend());

        } catch (Exception e) {
            System.err.println("Student postFilter() error: " + e.getMessage());
        }

        return s;
    }
}
