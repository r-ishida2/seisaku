package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class LoginAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        String id = request.getParameter("ID");
        String password = request.getParameter("PASSWORD");

        TeacherDao dao = new TeacherDao();
        Teacher teacher = dao.login(id, password);

        if (teacher != null) {
            session.setAttribute("user", teacher);
            return "index.jsp";
        } else {
            request.setAttribute("message", "IDまたはパスワードが確認できませんでした");
            return "login.jsp";
        }
    }
}
