package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao {

    // SQL文（共通部分）だけ返す
    public String baseSql() {
        return "SELECT s.name AS subject_name, s.cd AS subject_cd, t.no, t.point " +
               "FROM test t JOIN subject s ON t.subject_cd = s.cd AND t.school_cd = s.school_cd " +
               "WHERE t.school_cd = ? AND t.classnum = ? AND t.student_no = ? ORDER BY t.no";
    }

    // SQL実行後のResultSetをBeanに変換する（仮にSet<ResultSet>が結果リストとする）
    public List<TestListStudent> postFilter(ResultSet rs, Student student, School school) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        while (rs.next()) {
            TestListStudent bean = new TestListStudent();
            bean.setSubjectName(rs.getString("subject_name"));
            bean.setSubjectCd(rs.getString("subject_cd"));
            bean.setNum(rs.getInt("no"));
            bean.setPoint(rs.getInt("point"));
            list.add(bean);
        }

        return list;
    }

    // 学生情報を使ってフィルター（DB接続含む）
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list;

        String sql = baseSql();

        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database?serverTimezone=JST", "your_user", "your_password");
            PreparedStatement st = con.prepareStatement(sql)
        ) {
            st.setString(1, student.getSchool().getCd());
            st.setString(2, student.getClass_Num());
            st.setString(3, student.getNo());

            ResultSet rs = st.executeQuery();
            list = postFilter(rs, student, student.getSchool());
        }

        return list;
    }
}
