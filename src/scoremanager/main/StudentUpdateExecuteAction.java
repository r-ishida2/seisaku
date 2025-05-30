package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // フォームから送られたパラメータを取得
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        int entYear = Integer.parseInt(request.getParameter("entYear"));
        String classNum = request.getParameter("classNum");

        // hiddenとcheckbox両方あるので、"true" を含んでいれば在籍とみなす
        String[] isAttendParams = request.getParameterValues("isAttend");
        boolean isAttend = false;
        if (isAttendParams != null) {
            for (String val : isAttendParams) {
                if ("true".equals(val)) {
                    isAttend = true;
                    break;
                }
            }
        }

        // セッションから所属学校を取得（ログインユーザーの学校）
        HttpSession session = request.getSession();
        bean.Teacher teacher = (bean.Teacher) session.getAttribute("NAME");
        School school = teacher.getSchool();

        // Studentオブジェクト作成
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(school);

        // DAOで保存
        StudentDao dao = new StudentDao();
        boolean success = dao.save(student);

        if (success) {
            // 成功 → 一覧ページにリダイレクト or メッセージ表示
            request.setAttribute("message", "生徒情報を更新しました。");
            return "/main/student_update_done.jsp";
        } else {
            // 失敗 → メッセージ付きでフォームに戻る
            request.setAttribute("message", "生徒情報の更新に失敗しました。");
            request.setAttribute("student", student);
            return "/main/student_update.jsp";
        }
    }
}
