package scoremanager.main;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 必要に応じて初期データの取得などを行う（今回は特にない前提）
    	HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");
        School school = teacher.getSchool();
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNums = classNumDao.filter(school);
        req.setAttribute("classNums", classNums);
        int currentYear = Year.now().getValue();  // 例：2025
        List<String> entYears = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            entYears.add(String.valueOf(i));
        }
        req.setAttribute("entYears", entYears);

        // JSPのパスを返すだけ
        return "/main/student_create.jsp";
    }
}
