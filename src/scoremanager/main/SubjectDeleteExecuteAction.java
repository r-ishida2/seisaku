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

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");
        School school = teacher.getSchool();

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(school); // School をセット

        SubjectDao dao = new SubjectDao();
        dao.delete(subject);

        return "/main/subject_delete_done.jsp";
    }
}
