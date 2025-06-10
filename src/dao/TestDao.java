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
            String sql = baseSql + " AND student_no=? AND subject_cd=? AND school_cd=? AND no=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, student.getNo());
                stmt.setString(2, subject.getCd());
                stmt.setString(3, school.getCd());
                stmt.setInt(4, no);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    List<Test> resultList = new ArrayList<>();
                    resultList.add(buildTestFromResultSet(rs, school));
                    while (rs.next()) {
                        resultList.add(buildTestFromResultSet(rs, school));
                    }
                    if (!resultList.isEmpty()) {
                        test = resultList.get(0);
                    }
                }
            }
        }
        return test;
    }

    public List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        try {
            if (rSet.getRow() != 0) {
                list.add(buildTestFromResultSet(rSet, school));
            }
        } catch (Exception e) {
            // getRow()未対応ドライバを考慮して無視
        }

        while (rSet.next()) {
            list.add(buildTestFromResultSet(rSet, school));
        }

        return list;
    }

    private Test buildTestFromResultSet(ResultSet rSet, School school) throws Exception {
        Test test = new Test();
        Student student = new Student();
        Subject subject = new Subject();

        student.setNo(rSet.getString("student_no"));
        subject.setCd(rSet.getString("subject_cd"));

        test.setStudent(student);
        test.setSubject(subject);
        test.setSchool(school);
        test.setNo(rSet.getInt("no"));
        test.setPoint(rSet.getInt("point"));
        test.setClassNum(rSet.getString("class_num"));

        return test;
    }

    public List<Test> filter(int ent_Year, String class_Num, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        try (Connection connection = getConnection()) {
        	// filter() 内の SQL
        	String sql =
        	    "SELECT t.* FROM TEST t " +
        	    "JOIN STUDENT s ON t.student_no = s.no AND t.school_cd = s.school_cd " +
        	    "WHERE s.ent_year = ? AND t.class_num = ? AND t.subject_cd = ? AND t.no = ? AND t.school_cd = ?";


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
    	String selectSql = "SELECT COUNT(*) FROM TEST WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?";
    	String insertSql = "INSERT INTO TEST(student_no, subject_cd, school_cd, no, point, class_num) VALUES(?, ?, ?, ?, ?, ?)";
    	String updateSql = "UPDATE TEST SET point=?, class_num=? WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?";


        try (
            PreparedStatement selectStmt = connection.prepareStatement(selectSql);
        ) {
            selectStmt.setString(1, test.getStudent().getNo());
            selectStmt.setString(2, test.getSubject().getCd());
            selectStmt.setString(3, test.getSchool().getCd());
            selectStmt.setInt(4, test.getNo());

            ResultSet rs = selectStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, test.getPoint());
                    updateStmt.setString(2, test.getClass_Num());
                    updateStmt.setString(3, test.getStudent().getNo());
                    updateStmt.setString(4, test.getSubject().getCd());
                    updateStmt.setString(5, test.getSchool().getCd());
                    updateStmt.setInt(6, test.getNo());
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setString(1, test.getStudent().getNo());
                    insertStmt.setString(2, test.getSubject().getCd());
                    insertStmt.setString(3, test.getSchool().getCd());
                    insertStmt.setInt(4, test.getNo());
                    insertStmt.setInt(5, test.getPoint());
                    insertStmt.setString(6, test.getClass_Num());
                    return insertStmt.executeUpdate() > 0;
                }
            }
        }
    }
}
