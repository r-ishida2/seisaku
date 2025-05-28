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

        if (teacher == null) {
            System.out.println("[DEBUG] セッションに教員が存在しません。");
            return "/login.jsp"; // 必要ならログイン画面などにリダイレクト
        }

        System.out.println("[DEBUG] 教員ID: " + teacher.getId());
        System.out.println("[DEBUG] 教員名: " + teacher.getName());

        // 教員が所属する学校を取得
        School school = teacher.getSchool();
        if (school != null) {
            System.out.println("[DEBUG] 学校コード: " + school.getCd());
        } else {
            System.out.println("[DEBUG] 学校情報がnullです。");
        }

        // リクエストから教科コードを取得
        String cd = request.getParameter("cd");
        System.out.println("[DEBUG] リクエストから取得した教科コード: " + cd);

        Subject subject = null;
        if (cd != null && !cd.isEmpty()) {
            // 教科コードが指定されていれば既存の教科を取得（編集）
            SubjectDao dao = new SubjectDao();
            subject = dao.get(cd, school);
            System.out.println("[DEBUG] 既存教科取得: " + (subject != null ? "成功" : "失敗"));
        }

        // 教科情報の確認
        System.out.println("[DEBUG] 教科コード: " + subject.getCd());
        System.out.println("[DEBUG] 教科名: " + subject.getName());

        // JSPに渡す
        request.setAttribute("subject", subject);

        return "/main/subject_update.jsp";
    }
}
