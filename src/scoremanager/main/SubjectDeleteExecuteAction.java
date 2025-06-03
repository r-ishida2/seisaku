package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        String cd = req.getParameter("cd");

        System.out.println("=== SubjectDeleteExecuteAction ===");
        System.out.println("受け取ったcd: " + cd);

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");
        if (teacher == null) {
            System.out.println("teacherがnullです");
        } else {
            System.out.println("teacher: " + teacher.getName());
        }

        School school = teacher.getSchool();
        if (school == null) {
            System.out.println("schoolがnullです");
        } else {
            System.out.println("school_cd: " + school.getCd());
        }

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(school); // School をセット

        System.out.println("削除対象のSubject: cd = " + subject.getCd() + ", school_cd = " + subject.getSchool().getCd());

        SubjectDao dao = new SubjectDao();
        boolean result = dao.delete(subject);

        System.out.println("削除成功したか？: " + result);
        System.out.println("=== SubjectDeleteExecuteAction END ===");

        return "/main/subject_delete_done.jsp";
    }
}
