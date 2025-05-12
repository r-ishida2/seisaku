package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログイン中の教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        if (teacher == null) {
            // 未ログインの場合はログイン画面へリダイレクト
            return "/login.jsp";
        }

        // 教員の所属する学校を取得
        School school = teacher.getSchool();

        // DAOを使って生徒一覧を取得
        StudentDao dao = new StudentDao();
        List<Student> studentList = dao.filter(school, true);  // 出席している生徒一覧を取得

        // リクエストにセット
        request.setAttribute("students", studentList);

        // 表示用のJSPへ遷移
        return "/main/student_list.jsp";
    }
}
