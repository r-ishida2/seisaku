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
            // セッションからログイン教師を取得
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("NAME");
            if (teacher == null) {
                req.setAttribute("error", "ログイン情報が取得できません");
                return "/error.jsp";
            }
            School school = teacher.getSchool();

            // リクエストパラメータ取得＆trim
            String subjectCd = req.getParameter("subject_cd");
            String classNum = req.getParameter("class_num");
            String noStr = req.getParameter("no");

            if (subjectCd != null) subjectCd = subjectCd.trim();
            if (classNum != null) classNum = classNum.trim();
            if (noStr != null) noStr = noStr.trim();

            String[] studentNos = req.getParameterValues("noList");
            String[] points = req.getParameterValues("pointList");

            // 入力チェック
            if (subjectCd == null || subjectCd.isEmpty() ||
                classNum == null || classNum.isEmpty() ||
                noStr == null || noStr.isEmpty() ||
                studentNos == null || points == null ||
                studentNos.length != points.length) {
                req.setAttribute("error", "すべての項目を正しく入力してください");
                return "/error.jsp";
            }

            int no = 0;
            try {
                no = Integer.parseInt(noStr);
            } catch (NumberFormatException e) {
                req.setAttribute("error", "回数の形式が不正です");
                return "/error.jsp";
            }

            // 科目取得
            SubjectDao subjectDao = new SubjectDao();
            Subject subject = subjectDao.get(subjectCd, school);
            if (subject == null) {
                req.setAttribute("error", "指定された科目が存在しません");
                return "/error.jsp";
            }

            StudentDao studentDao = new StudentDao();
            List<Test> testList = new ArrayList<>();

            for (int i = 0; i < studentNos.length; i++) {
                String studentNo = studentNos[i].trim();       // ← trim追加
                String pointStr = points[i].trim();            // ← trim追加
                int point = 0;
                try {
                    point = Integer.parseInt(pointStr);
                } catch (NumberFormatException e) {
                    req.setAttribute("error", "点数は数値で入力してください (学生番号:" + studentNo + ")");
                    return "/error.jsp";
                }

                Student student = studentDao.get(studentNo);
                if (student == null) {
                    req.setAttribute("error", "学生番号 " + studentNo + " の生徒情報が存在しません");
                    return "/error.jsp";
                }

                Test test = new Test();
                test.setStudent(student);
                test.setSchool(school);
                test.setSubject(subject);
                test.setClassNum(classNum);  // classNum も trim済み
                test.setNo(no);
                test.setPoint(point);

                testList.add(test);
            }

            // 登録処理（UPDATE or INSERT自動判定）
            TestDao testDao = new TestDao();
            boolean success = testDao.save(testList);

            if (!success) {
                req.setAttribute("error", "成績登録に失敗しました");
                return "/error.jsp";
            }

            return "/main/test_regist_done.jsp";

        } catch (Exception e) {
            req.setAttribute("error", "例外が発生しました: " + e.getMessage());
            return "/error.jsp";
        }
    }
}
