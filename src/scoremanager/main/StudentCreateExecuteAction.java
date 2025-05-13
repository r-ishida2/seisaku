package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // フォームからのパラメータを取得
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        int entYear = Integer.parseInt(req.getParameter("entYear"));
        String classNum = req.getParameter("classNum");
        boolean isAttend = Boolean.parseBoolean(req.getParameter("isAttend"));
        String schoolCd = req.getParameter("Cd");

        // Studentインスタンスを生成して値をセット
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);

        // Schoolを生成し、Studentに設定
        School school = new School();
        school.setCd(schoolCd);
        student.setSchool(school);

        // DAOで保存処理を実行
        StudentDao dao = new StudentDao();
        boolean result = dao.save(student);

        // 結果をリクエストにセット
        if (result) {
            req.setAttribute("message", "生徒情報の登録に成功しました。");
        } else {
            req.setAttribute("message", "生徒情報の登録に失敗しました。");
        }

        // 戻り値でJSPのパスを返す
        return "/view/student/result.jsp";
    }
}
