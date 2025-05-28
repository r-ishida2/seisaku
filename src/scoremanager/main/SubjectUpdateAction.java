package scoremanager.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログイン中の教員を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        // 教員が所属する学校を取得
        School school = teacher.getSchool();

        // リクエストから教科コードを取得（空なら新規登録）
        String cd = request.getParameter("cd");

        Subject subject = null;
        if (cd != null && !cd.isEmpty()) {
            // 教科コードが指定されていれば既存の教科を取得（編集）
            SubjectDao dao = new SubjectDao();
            subject = dao.get(cd, school);
        }

        if (subject == null) {
            // 新規登録の場合、空のSubjectインスタンスを用意
            subject = new Subject();
            subject.setSchool(school);
        }
        System.out.println(subject.getCd());
        System.out.println(subject.getName());
        // JSPに渡す
        request.setAttribute("subject", subject);

        return "/main/subject_update.jsp";
    }
}

