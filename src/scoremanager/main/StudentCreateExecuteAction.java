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
        int ent_Year = Integer.parseInt(req.getParameter("ent_Year"));
        String class_Num = req.getParameter("class_Num");
        boolean is_Attend = Boolean.parseBoolean(req.getParameter("is_Attend"));
        String schoolCd = req.getParameter("schoolCd");

        // Studentインスタンスを生成して値をセット
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEnt_Year(ent_Year);
        student.setClass_Num(class_Num);
        student.setAttend(is_Attend);

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
