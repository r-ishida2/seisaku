package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {
    @Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String cd = req.getParameter("cd");

        // セッションからログイン中の教師を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");
        School school = teacher.getSchool();

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(cd, school); // School オブジェクトを渡す

        req.setAttribute("subject", subject);
        return "/main/subject_delete.jsp";
    }
}
