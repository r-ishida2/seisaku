package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    // SQLのベース文
    public String baseSql() {
        return "SELECT STUDENT.ENT_YEAR, STUDENT.NO, STUDENT.NAME, STUDENT.CLASS_NUM, TEST.SUBJECT_CD, TEST.POINT FROM STUDENT LEFT JOIN TEST ON STUDENT.NO = TEST.STUDENT_NO WHERE STUDENT.SCHOOL_CD = ? AND STUDENT.IS_ATTEND = TRUE";
    }

    // 指定の生徒リストでフィルタ
    public List<TestListSubject> postFilter(ResultSet rSet, List<Student> students) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        Map<String, Student> studentMap = new HashMap<>();
        for (Student s : students) {
            studentMap.put(s.getNo(), s);
        }

        Map<String, TestListSubject> resultMap = new HashMap<>();

        while (rSet.next()) {
            String studentNo = rSet.getString("NO");

            if (!studentMap.containsKey(studentNo)) {
                continue;
            }

            TestListSubject bean = resultMap.get(studentNo);
            if (bean == null) {
                Student stu = studentMap.get(studentNo);
                bean = new TestListSubject();
                bean.setEntYear(rSet.getInt("ENT_YEAR"));
                bean.setStudentNo(studentNo);
                bean.setStudentName(stu.getName());
                bean.setClassNum(stu.getClass_Num());
                bean.setPoints(new HashMap<>());
                resultMap.put(studentNo, bean);
                list.add(bean);
            }

            int subjectCd = rSet.getInt("SUBJECT_CD");
            int point = rSet.getInt("POINT");
            if (!rSet.wasNull()) {
                bean.getPoints().put(subjectCd, point);
            }
        }

        return list;
    }

    // 学生リストをもとに成績一覧を取得
    public List<TestListSubject> filter(List<Student> students) throws Exception {
        if (students.isEmpty()) return new ArrayList<>();

        School school = students.get(0).getSchool();
        String sql = baseSql();

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, school.getCd());

            try (ResultSet rSet = stmt.executeQuery()) {
                return postFilter(rSet, students);
            }
        }
    }
}
