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

        if (teacher == null) {
            System.out.println("DEBUG: セッションから教員情報が取得できませんでした。");
            return "/login.jsp";  // 未ログインと判断
        }

        System.out.println("DEBUG: 教員名: " + teacher.getName());

        // 教員が所属する学校を取得
        School school = teacher.getSchool();

        if (school == null) {
            System.out.println("DEBUG: 教員に紐づく学校情報が null です。");
            request.setAttribute("error", "学校情報が取得できませんでした。");
            return "/main/subject_list.jsp";
        }

        System.out.println("DEBUG: 学校コード: " + school.getCd() + ", 学校名: " + school.getName());

        // 学校に紐づく教科一覧を取得
        SubjectDao dao = new SubjectDao();
        List<Subject> list = dao.filter(school);

        if (list == null || list.isEmpty()) {
            System.out.println("DEBUG: 科目リストが空です。");
        } else {
            System.out.println("DEBUG: 科目リスト取得成功。件数: " + list.size());
            for (Subject s : list) {
                System.out.println(" - 科目コード: " + s.getCd() + ", 科目名: " + s.getName());
            }
        }

        // 教科一覧をリクエスト属性にセット
        request.setAttribute("subjects", list);

        // 教科一覧表示用のJSPへ
        return "/main/subject_list.jsp";
    }
}
