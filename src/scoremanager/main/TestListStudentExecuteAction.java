package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        if (teacher == null) {
            req.setAttribute("error", "ログイン情報が確認できません。");
            return "/error.jsp";
        }

        School school = teacher.getSchool();
        String studentNo = req.getParameter("student_no");

        if (studentNo == null || studentNo.isEmpty()) {
            req.setAttribute("error", "生徒番号が指定されていません。");
            return "/error.jsp";
        }

        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentNo);

        if (student == null) {
            req.setAttribute("error", "指定された生徒が見つかりません。");
            return "/error.jsp";
        }

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = subjectDao.filter(school);

        List<Test> testList = new ArrayList<>();
        TestDao testDao = new TestDao();

        for (Subject subject : subjects) {
            for (int pointNo = 1; pointNo <= 3; pointNo++) {
                List<Test> partial = testDao.filter(
                    student.getEntYear(),
                    student.getClassNum(),
                    subject,
                    pointNo,
                    school
                );
                testList.addAll(partial);
            }
        }

        req.setAttribute("student", student);
        req.setAttribute("testList", testList);

        return "/main/test_list_student.jsp";
    }
}


