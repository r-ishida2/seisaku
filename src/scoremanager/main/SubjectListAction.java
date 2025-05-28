package scoremanager.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログイン中の教員を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        // 教員が所属する学校を取得
        School school = teacher.getSchool();

        // 学校に紐づく教科一覧を取得
        SubjectDao dao = new SubjectDao();
        List<Subject> list = dao.filter(school);

        // 教科一覧をリクエスト属性にセット
        request.setAttribute("subjects", list);

        // 教科一覧表示用のJSPへ
        return "/main/subject_list.jsp";
    }
}
