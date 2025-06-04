package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        if (teacher == null) {
            req.setAttribute("error", "ログインしていません。");
            return "/main/error.jsp";
        }

        School school = teacher.getSchool();

        // 教科一覧取得
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(school);

        // 生徒一覧取得（在学中のみ）
        StudentDao studentDao = new StudentDao();
        List<Student> studentList = studentDao.filter(school, true);

        // JSP に渡す
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("studentList", studentList);

        return "/main/test_list.jsp";
    }
}

