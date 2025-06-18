package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
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
        Teacher teacher = (Teacher) request.getSession().getAttribute("NAME");

        if (teacher == null) {
            return "login.jsp"; // ログインしていない場合はログイン画面へ
        }
        
        // 科目コードが3桁かチェック
        if (cd == null || cd.length() != 3) {
        	request.setAttribute("message", "科目コードは3文字で入力してください。");
        	request.setAttribute("cd", cd);
        	request.setAttribute("name", name);
        	return "/main/subject_create.jsp";
        }
        
     // 科目コードが1〜3文字かつ半角英数字かチェック
        if (cd == null || !cd.matches("^[\\p{Alnum}]{1,3}$")) {
            request.setAttribute("message", "科目コードは半角で入力してください。");
            request.setAttribute("cd", cd);
            request.setAttribute("name", name);
            return "/main/subject_create.jsp";
        }


        School school = teacher.getSchool();
        SubjectDao dao = new SubjectDao();

        // 重複チェック
        Subject existing = dao.get(cd, school);
        if (existing != null) {
            request.setAttribute("message", "科目コードが重複しています。");
            request.setAttribute("cd", cd);
            request.setAttribute("name", name);
            return "/main/subject_create.jsp";
        }

        // Subjectのインスタンスを生成・セット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        // 保存処理
        boolean result = dao.save(subject);

        if (result) {
            request.setAttribute("message", "科目情報の登録に成功しました。");
        } else {
            request.setAttribute("message", "科目情報の登録に失敗しました。");
        }

        return "/main/subject_create_done.jsp";
    }
}
