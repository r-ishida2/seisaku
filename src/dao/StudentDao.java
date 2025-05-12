package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    private final String BASE_SQL = "SELECT * FROM STUDENT WHERE ";

    // 学生1人取得
    public Student get(String no) {
        Student student = null;

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE no = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, no);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                student = postFilter(rs, null); // 学校情報はnullでも可
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }

    // 学生一覧取得（学校 + 年度 + クラス + 在籍）
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + "school_cd = ? AND ent_year = ? AND class_num = ? AND is_attend = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setBoolean(4, isAttend);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(postFilter(rs, school));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 学生一覧取得（学校 + 年度 + 在籍）
    public List<Student> filter(School school, int entYear, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + "school_cd = ? AND ent_year = ? AND is_attend = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setBoolean(3, isAttend);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(postFilter(rs, school));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 学生一覧取得（学校 + 在籍）
    public List<Student> filter(School school, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + "school_cd = ? AND is_attend = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());
            st.setBoolean(2, isAttend);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(postFilter(rs, school));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 学生情報保存（REPLACE INTO = INSERT or UPDATE）
    public boolean save(Student student) {
        boolean result = false;

        try (Connection con = getConnection()) {
            String sql = "REPLACE INTO STUDENT(no, name, ent_year, class_num, is_attend, school_cd) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, student.getNo());
            st.setString(2, student.getName());
            st.setInt(3, student.getEntYear());
            st.setString(4, student.getClassNum());
            st.setBoolean(5, student.isAttend());
            st.setString(6, student.getSchool().getCd());

            int count = st.executeUpdate();
            result = (count == 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ResultSet → Student オブジェクト変換処理
    private Student postFilter(ResultSet rs, School school) {
        Student s = new Student();
        try {
            s.setNo(rs.getString("no"));
            s.setName(rs.getString("name"));
            s.setEntYear(rs.getInt("ent_year"));
            s.setClassNum(rs.getString("class_num"));
            s.setAttend(rs.getBoolean("is_attend"));

            if (school != null) {
                s.setSchool(school);
            } else {
                School sch = new School();
                sch.setCd(rs.getString("school_cd"));
                s.setSchool(sch);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
}
