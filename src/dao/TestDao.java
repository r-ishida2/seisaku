package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
    private String baseSql = "SELECT * FROM TEST WHERE 1=1";

    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;

        try (Connection connection = getConnection()) {
            String sql = baseSql + " AND student_no=? AND subject_cd=? AND school_cd=? AND point_no=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, student.getNo());
                stmt.setString(2, subject.getCd());
                stmt.setString(3, school.getCd());
                stmt.setInt(4, no);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    test = postFilter(rs, school).get(0);
                }
            }
        }
        return test;
    }

    public List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rSet.next()) {
            Test test = new Test();
            Student student = new Student();
            Subject subject = new Subject();

            student.setNo(rSet.getString("student_no"));
            subject.setCd(rSet.getString("subject_cd"));

            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setNo(rSet.getInt("point_no"));
            test.setPoint(rSet.getInt("point"));
            test.setClassNum(rSet.getString("class_num")); // 追加（カラムがある前提）

            list.add(test);
        }
        return list;
    }

    public List<Test> filter(int ent_Year, String class_Num, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = baseSql + " AND ent_year=? AND class_num=? AND subject_cd=? AND point_no=? AND school_cd=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, ent_Year);
                stmt.setString(2, class_Num);
                stmt.setString(3, subject.getCd());
                stmt.setInt(4, num);
                stmt.setString(5, school.getCd());

                ResultSet rs = stmt.executeQuery();
                list = postFilter(rs, school);
            }
        }
        return list;
    }

    public boolean save(List<Test> list) throws Exception {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            for (Test test : list) {
                if (!save(test, connection)) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return true;
        }
    }

    public boolean save(Test test, Connection connection) throws Exception {
        String sql = "INSERT INTO TEST(student_no, subject_cd, school_cd, point_no, point, class_num) "
                   + "VALUES(?, ?, ?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE point=?, class_num=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, test.getStudent().getNo());
            stmt.setString(2, test.getSubject().getCd());
            stmt.setString(3, test.getSchool().getCd());
            stmt.setInt(4, test.getNo());
            stmt.setInt(5, test.getPoint());
            stmt.setString(6, test.getClass_Num());

            stmt.setInt(7, test.getPoint());
            stmt.setString(8, test.getClass_Num());

            int result = stmt.executeUpdate();
            return result > 0;
        }
    }
}
