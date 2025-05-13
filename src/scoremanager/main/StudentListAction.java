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

        // ====== デバッグ出力（JSPに渡す前に中身を確認） ======
        System.out.println("=== 生徒一覧の中身 ===");
        if (studentList.isEmpty()) {
            System.out.println("生徒データはありません。");
        } else {
            for (Student s : studentList) {
                System.out.println(
                    "学生番号: " + s.getNo() +
                    ", 氏名: " + s.getName() +
                    ", 入学年度: " + s.getEnt_Year() +
                    ", クラス: " + s.getClass_Num() +
                    ", 在学中: " + s.is_Attend() +
                    ", 学校名: " + (s.getSchool() != null ? s.getSchool().getName() : "未設定")
                );
            }
        }
        // ============================================

        // リクエストにセット
        request.setAttribute("students", studentList);

        // 表示用のJSPへ遷移
        return "/main/student_list.jsp";
    }
}
