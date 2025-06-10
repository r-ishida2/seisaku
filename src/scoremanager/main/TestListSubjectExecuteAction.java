package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListSubject;
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

        if (entYearStr == null || classNum == null || subjectCd == null) {
            req.setAttribute("error", "必要なパラメータが不足しています。");
            return "/main/test_list_subject.jsp";
        }

        int entYear;
        try {
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "学年が数値として不正です。");
            return "/main/test_list_subject.jsp";
        }

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        if (subject == null) {
            req.setAttribute("error", "指定された教科が見つかりません。");
            return "/main/test_list_subject.jsp";
        }

        TestDao testDao = new TestDao();

        // 1回目・2回目のテストを取得
        List<Test> testList1 = testDao.filter(entYear, classNum, subject, 1, school);
        List<Test> testList2 = testDao.filter(entYear, classNum, subject, 2, school);

        // 結果を学生番号で統合
        Map<String, TestListSubject> map = new HashMap<>();

        for (Test t : testList1) {
            String studentNo = t.getStudent().getNo();
            TestListSubject bean = new TestListSubject();
            bean.setEntYear(entYear);
            bean.setStudentNo(studentNo);
            bean.setClassNum(classNum);
            bean.setStudentName(""); // 後で必要なら設定
            bean.setPoints(new HashMap<>());
            bean.getPoints().put(1, t.getPoint());
            map.put(studentNo, bean);
        }

        for (Test t : testList2) {
            String studentNo = t.getStudent().getNo();
            TestListSubject bean = map.get(studentNo);
            if (bean == null) {
                bean = new TestListSubject();
                bean.setEntYear(entYear);
                bean.setStudentNo(studentNo);
                bean.setClassNum(classNum);
                bean.setStudentName(""); // 後で必要なら設定
                bean.setPoints(new HashMap<>());
                map.put(studentNo, bean);
            }
            bean.getPoints().put(2, t.getPoint());
        }

        List<TestListSubject> resultList = new ArrayList<>(map.values());

        req.setAttribute("testList", resultList);
        req.setAttribute("entYear", entYear);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subject", subject);

        return "/main/test_list_subject.jsp";
    }
}
