package scoremanager.main;

import java.util.ArrayList;
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

        // リクエストパラメータの取得
        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String isAttendStr = request.getParameter("is_attend");
     // デバッグ出力（JSPから送信された値の確認）
        System.out.println("DEBUG: リクエストパラメータ");
        System.out.println(" - ent_year   = " + entYearStr);
        System.out.println(" - class_num  = " + classNum);
        System.out.println(" - is_attend  = " + isAttendStr);

        List<Student> students;
        StudentDao dao = new StudentDao();

        if ((entYearStr == null || entYearStr.trim().isEmpty()) &&
        	    classNum != null && !classNum.trim().isEmpty()) {

        	    // 全件取得（在籍・退学）
        	    students = new ArrayList<>();
        	    students.addAll(dao.filter(school, true));
        	    students.addAll(dao.filter(school, false));

        	    // エラーメッセージを設定
        	    request.setAttribute("error", "クラスを指定する場合は入学年度も指定してください");

        	    // 入力値を保持
        	    request.setAttribute("ent_year", "");
        	    request.setAttribute("class_num", classNum.trim());
        	    request.setAttribute("is_attend", isAttendStr != null ? isAttendStr : "");

        	    // 一覧画面に戻る
        	    request.setAttribute("students", students);
        	    return "/main/student_list.jsp";
        	}

        // 入力値の存在・妥当性チェック
        boolean hasEntYear = entYearStr != null && !entYearStr.trim().isEmpty();
        boolean hasIsAttend = isAttendStr != null && !isAttendStr.trim().isEmpty();

        if (hasEntYear && hasIsAttend) {
            try {
                int entYear = Integer.parseInt(entYearStr.trim());
                boolean isAttend = isAttendStr.equals("true") || isAttendStr.equals("1");

                if (classNum != null && !classNum.trim().isEmpty()) {
                    System.out.println("DEBUG: filter(school, entYear, classNum, isAttend) 呼び出し");
                    students = dao.filter(school, entYear, classNum.trim(), isAttend);
                } else {
                    System.out.println("DEBUG: filter(school, entYear, isAttend) 呼び出し");
                    students = dao.filter(school, entYear, isAttend);
                }

                // 検索条件をフォームに保持
                request.setAttribute("ent_year", entYearStr.trim());
                request.setAttribute("class_num", classNum != null ? classNum.trim() : "");
                request.setAttribute("is_attend", isAttendStr);

            } catch (NumberFormatException e) {
                System.out.println("DEBUG: NumberFormatException → filter(school, true) 呼び出し");
                students = dao.filter(school, true);

                // フォーム状態を保持（ent_yearは空）
                request.setAttribute("ent_year", "");
                request.setAttribute("class_num", classNum != null ? classNum.trim() : "");
                request.setAttribute("is_attend", "true");
            }
        } else {
            // 初期表示または不完全な条件
            System.out.println("DEBUG: 初期表示 → filter(school, true) + filter(school, false) 呼び出し");
            students = new ArrayList<>();
            students.addAll(dao.filter(school, true));  // 在籍中
            students.addAll(dao.filter(school, false)); // 退学済み

            // 検索条件を空で保持
            request.setAttribute("ent_year", entYearStr != null ? entYearStr.trim() : "");
            request.setAttribute("class_num", classNum != null ? classNum.trim() : "");
            request.setAttribute("is_attend", isAttendStr != null ? isAttendStr : "");
        }

        // 結果をリクエスト属性にセット
        request.setAttribute("students", students);

        // 学生一覧画面へ遷移
        return "/main/student_list.jsp";
    }
}
