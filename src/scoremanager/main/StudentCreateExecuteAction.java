package scoremanager.main;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {



        // フォームからのパラメータ取得
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String entYearStr = req.getParameter("entYear");
        String classNum = req.getParameter("classNum");

        // セッションからログイン中の教員を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME"); // または "TEACHER"
        if (teacher == null || teacher.getSchool() == null) {
            req.setAttribute("message", "ログイン情報が確認できません。");
            return "/login.jsp";
        }

		School schools = teacher.getSchool();
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNums = classNumDao.filter(schools);
        req.setAttribute("classNums", classNums);
        int currentYear = Year.now().getValue();  // 例：2025
        List<String> entYears = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            entYears.add(String.valueOf(i));
        }
        req.setAttribute("entYears", entYears);

        // 入学年度が未入力ならエラーメッセージを出して戻る
        if (entYearStr == null || entYearStr.isEmpty()) {
            req.setAttribute("message", "入学年度を選択してください。");
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("classNum", classNum);
            return "/main/student_create.jsp";
        }

        int entYear = Integer.parseInt(entYearStr);
        String schoolCd = teacher.getSchool().getCd();
        boolean isAttend = (entYear <= LocalDate.now().getYear());

        // 学生番号の形式チェック（半角数字のみ）
        if (no == null || !no.matches("^[0-9]+$")) {
            req.setAttribute("message", "学生番号は半角数字のみで入力してください。");
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("entYear", entYearStr);
            req.setAttribute("classNum", classNum);
            return "/main/student_create.jsp";
        }

        // 学生番号の重複チェック
        StudentDao dao = new StudentDao();
        if (dao.get(no) != null) {
            req.setAttribute("message", "学生番号が重複しています。");
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("entYear", entYearStr);
            req.setAttribute("classNum", classNum);
            return "/main/student_create.jsp";
        }

        // Studentインスタンスを生成して値を設定
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
