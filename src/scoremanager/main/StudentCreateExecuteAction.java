package scoremanager.main;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {
	@Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // フォームからのパラメータ取得
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        int entYear = Integer.parseInt(req.getParameter("entYear"));
        String classNum = req.getParameter("classNum");

        // セッションからログイン中の教員を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");  // または "TEACHER"
        if (teacher == null || teacher.getSchool() == null) {
            req.setAttribute("message", "ログイン情報が確認できません。");
            return "/login.jsp";
        }

        // 学校コードと在籍状態を設定
        String schoolCd = teacher.getSchool().getCd();
        boolean isAttend = (entYear <= LocalDate.now().getYear()); // 現在の年以下なら在籍中とする

        // DAOで重複チェック
        StudentDao dao = new StudentDao();
        if (dao.get(no) != null) {
            // 学生番号が重複 → 登録せずにフォームに戻す
            req.setAttribute("message", "学生番号がすでに存在しています。別の番号を入力してください。");
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("entYear", entYear);
            req.setAttribute("classNum", classNum);
            return "/main/student_create.jsp";  // 元のフォームへ戻す
        }

        // Studentインスタンス生成して値を設定
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);

        School school = new School();
        school.setCd(schoolCd);
        student.setSchool(school);

        // 保存処理
        boolean result = dao.save(student);

        if (result) {
            req.setAttribute("message", "生徒情報の登録に成功しました。");
        } else {
            req.setAttribute("message", "生徒情報の登録に失敗しました。");
        }

        return "/main/student_create_done.jsp";
    }
}
