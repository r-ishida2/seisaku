package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 入力パラメータ取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // セッションからログイン中のTeacherを取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        if (teacher == null) {
            return "login.jsp"; // ログインしていない場合はログイン画面へ
        }

        // Subjectのインスタンスを生成・セット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // 保存処理
        boolean result = new SubjectDao().save(subject);

        // リクエスト属性に結果を格納
        request.setAttribute("result", result);

        // 完了画面または一覧画面に遷移（必要に応じて変更）
        return "subject_create_done.jsp";
    }
}

