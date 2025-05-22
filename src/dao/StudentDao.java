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

    public Student get(String no) {
        Student student = null;

        try (Connection con = getConnection()) {
            String sql = BASE_SQL + " WHERE no = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, no);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                student = postFilter(rs, null);
            }
        } catch (Exception e) {
            System.err.println("Student get() error: " + e.getMessage());
        }

        return student;
    }

    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SQL + " WHERE school_cd = ? AND ent_year = ? AND is_attend = ?");
        boolean hasClassNum = classNum != null && !classNum.trim().isEmpty();

        if (hasClassNum) {
            sql.append(" AND class_num = ?");
        }

        // --- デバッグ出力 ---
        System.out.println("[DEBUG] SQL: " + sql);
        System.out.println("[DEBUG] school_cd: " + school.getCd());
        System.out.println("[DEBUG] ent_year: " + entYear);
        System.out.println("[DEBUG] is_attend: " + isAttend);
        System.out.println("[DEBUG] class_num: " + (hasClassNum ? classNum : "(なし)"));

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
            int count = 0;

            while (rs.next()) {
                Student student = postFilter(rs, school);
                list.add(student);
                count++;
            }

            System.out.println("[DEBUG] 結果件数: " + count);

        } catch (Exception e) {
            System.err.println("Student filter(school, year, class, attend) error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        return list;
    }


    public List<Student> filter(School school, int entYear, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + " WHERE school_cd = ? AND ent_year = ? AND is_attend = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setBoolean(3, isAttend);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Student student = postFilter(rs, school);
                list.add(student);
            }

        } catch (Exception e) {
            System.err.println("Student filter(school, year, attend) error: " + e.getMessage());
        }

        return list;
    }

    public List<Student> filter(School school, boolean isAttend) {
        System.out.println("[DEBUG] filter(School, boolean) 呼び出し: school = " + (school == null ? "null" : school.getCd()) + ", isAttend = " + isAttend);
        List<Student> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SQL + " WHERE is_attend = ?");
        boolean hasSchool = (school != null);

        if (hasSchool) {
            sql.append(" AND school_cd = ?");
        }

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {

            int index = 1;
            st.setBoolean(index++, isAttend);
            if (hasSchool) {
                st.setString(index++, school.getCd());
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Student student = postFilter(rs, school); // school=nullでもpostFilterが動く前提
                list.add(student);
            }

        } catch (Exception e) {
            System.err.println("Student filter(school, attend) error: " + e.getMessage());
        }

        return list;
    }




    public boolean save(Student student) {
        boolean result = false;

        try (Connection con = getConnection()) {
            String checkSql = "SELECT COUNT(*) FROM STUDENT WHERE no = ?";
            try (PreparedStatement checkSt = con.prepareStatement(checkSql)) {
                checkSt.setString(1, student.getNo());
                ResultSet rs = checkSt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    String updateSql = "UPDATE STUDENT SET name = ?, ent_year = ?, class_num = ?, is_attend = ?, school_cd = ? WHERE no = ?";
                    try (PreparedStatement st = con.prepareStatement(updateSql)) {
                        st.setString(1, student.getName());
                        st.setInt(2, student.getEntYear());
                        st.setString(3, student.getClassNum());
                        st.setBoolean(4, student.isAttend());
                        st.setString(5, student.getSchool().getCd());
                        st.setString(6, student.getNo());

                        result = (st.executeUpdate() == 1);
                    }
                } else {
                    String insertSql = "INSERT INTO STUDENT(no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement st = con.prepareStatement(insertSql)) {
                        st.setString(1, student.getNo());
                        st.setString(2, student.getName());
                        st.setInt(3, student.getEntYear());
                        st.setString(4, student.getClassNum());
                        st.setBoolean(5, student.isAttend());
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
            System.err.println("Student postFilter() error: " + e.getMessage());
        }

        return s;
    }

}
