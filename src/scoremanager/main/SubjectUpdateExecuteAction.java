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

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ログイン中の教員情報から学校情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");
        School school = teacher.getSchool();

        // フォームからパラメータを取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // Subject オブジェクトを生成してデータをセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        // データベースに保存処理
        SubjectDao dao = new SubjectDao();
        boolean success = dao.save(subject); // 登録 or 更新

        // 成功可否をリクエストにセット（必要なら）
        if (success) {
            // 成功 → 一覧ページにリダイレクト or メッセージ表示
            request.setAttribute("message", "科目情報を更新しました。");
            return "/main/subject_update_done.jsp";
        } else {
            // 失敗 → メッセージ付きでフォームに戻る
            request.setAttribute("message", "科目情報の更新に失敗しました。");
            request.setAttribute("subject", subject);
            return "/main/subject_update.jsp";
        }
    }
}

