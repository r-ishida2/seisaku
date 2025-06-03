package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログイン中の先生を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            req.setAttribute("error", "ログイン情報が取得できませんでした");
            return "/view/error.jsp";
        }

        // 先生に紐づいた学校を取得
        School school = teacher.getSchool();

        // 教科一覧を取得
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(school);

        // クラス一覧を取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school);

        // 生徒一覧を取得（入学中の学生のみ）
        StudentDao studentDao = new StudentDao();
        List<Student> studentList = studentDao.filter(school, true); // isAttend = true

        // JSPへ渡す
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("studentList", studentList);

        return "/view/test_regist.jsp";
    }
}


