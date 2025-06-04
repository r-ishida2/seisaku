package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        if (teacher == null) {
            req.setAttribute("error", "ログイン情報が確認できません。");
            return "/main/test_list_subject.jsp";
        }

        School school = teacher.getSchool();

        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String pointNoStr = req.getParameter("point_no");

        if (entYearStr == null || classNum == null || subjectCd == null || pointNoStr == null) {
            req.setAttribute("error", "必要なパラメータが不足しています。");
            return "/main/test_list_subject.jsp";
        }

        int entYear;
        int pointNo;
        try {
            entYear = Integer.parseInt(entYearStr);
            pointNo = Integer.parseInt(pointNoStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "学年または回数が数値として不正です。");
            return "/main/test_list_subject.jsp";
        }

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        if (subject == null) {
            req.setAttribute("error", "指定された教科が見つかりません。");
            return "/main/test_list_subject.jsp";
        }

        TestDao testDao = new TestDao();
        List<Test> testList = testDao.filter(entYear, classNum, subject, pointNo, school);

        req.setAttribute("testList", testList);
        req.setAttribute("entYear", entYear);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subject", subject);
        req.setAttribute("pointNo", pointNo);

        return "/main/test_list_subject.jsp";
    }
}

