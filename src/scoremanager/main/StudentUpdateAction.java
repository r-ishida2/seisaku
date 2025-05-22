package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // リクエストから生徒番号を取得
        String no = request.getParameter("no");

        // 生徒情報を取得
        StudentDao dao = new StudentDao();
        Student student = dao.get(no);

        // 生徒が見つからない場合はエラーページへ（または一覧に戻す）
        if (student == null) {
            request.setAttribute("message", "指定された生徒は見つかりませんでした。");
            return "/main/student_list.jsp";
        }

        // 生徒情報をリクエストにセット
        request.setAttribute("student", student);

        // 更新フォームへ遷移
        return "/main/student_update.jsp";
    }
}

