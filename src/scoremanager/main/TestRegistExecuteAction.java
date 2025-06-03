package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            // ログイン情報から学校取得
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            if (teacher == null) {
                req.setAttribute("error", "ログイン情報が取得できません");
                return "/view/error.jsp";
            }

            School school = teacher.getSchool();

            // パラメータ取得
            String subjectCd = req.getParameter("subject_cd");
            String classNum = req.getParameter("class_num");
            String[] studentNos = req.getParameterValues("no");
            String[] points = req.getParameterValues("point");

            if (subjectCd == null || classNum == null || studentNos == null || points == null) {
                req.setAttribute("error", "すべての項目を入力してください");
                return "/view/error.jsp";
            }

            // 教科取得
            SubjectDao subjectDao = new SubjectDao();
            Subject subject = subjectDao.get(subjectCd, school);

            // 生徒情報と点数を Test に詰める
            List<Test> testList = new ArrayList<>();
            StudentDao studentDao = new StudentDao();

            for (int i = 0; i < studentNos.length; i++) {
                String no = studentNos[i];
                int point = Integer.parseInt(points[i]);

                Student student = studentDao.get(no);

                Test test = new Test();
                test.setStudent(student);
                test.setSchool(school);
                test.setSubject(subject);
                test.setClassNum(classNum);
                test.setPoint(point);

                testList.add(test);
            }

            // 登録処理
            TestDao testDao = new TestDao();
            boolean success = testDao.save(testList);

            if (!success) {
                req.setAttribute("error", "登録に失敗しました");
                return "/view/error.jsp";
            }

            return "/view/test_regist_done.jsp";

        } catch (Exception e) {
            req.setAttribute("error", "例外が発生しました: " + e.getMessage());
            return "/view/error.jsp";
        }
    }
}

