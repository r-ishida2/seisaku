package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListSubject;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        System.out.println("=== TestListSubjectExecuteAction: execute start ===");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        if (teacher == null) {
            System.out.println("ログイン情報なし");
            req.setAttribute("error", "ログイン情報が確認できません。");
            return "/main/test_list_subject.jsp";
        }

        School school = teacher.getSchool();
        System.out.println("学校コード: " + school.getCd());

        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");

        System.out.println("受信パラメータ: ent_year=" + entYearStr + ", class_num=" + classNum + ", subject_cd=" + subjectCd);

        if (entYearStr == null || entYearStr.isEmpty() ||
            classNum == null || classNum.isEmpty() ||
            subjectCd == null || subjectCd.isEmpty()) {
            System.out.println("パラメータ不足");
            req.setAttribute("error", "必要なパラメータが不足しています。");
            return "/main/test_list_subject.jsp";
        }

        int entYear;
        try {
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            System.out.println("学年の形式が不正: " + entYearStr);
            req.setAttribute("error", "学年が数値として不正です。");
            return "/main/test_list_subject.jsp";
        }

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        if (subject == null) {
            System.out.println("教科情報取得失敗: subjectCd=" + subjectCd);
            req.setAttribute("error", "指定された教科が見つかりません。");
            return "/main/test_list_subject.jsp";
        }

        System.out.println("教科取得成功: " + subject.getCd());

        TestDao testDao = new TestDao();
        StudentDao studentDao = new StudentDao();

        // 1回目・2回目のテストを取得
        List<Test> testList1 = testDao.filter(entYear, classNum, subject, 1, school);
        System.out.println("1回目テスト件数: " + testList1.size());

        List<Test> testList2 = testDao.filter(entYear, classNum, subject, 2, school);
        System.out.println("2回目テスト件数: " + testList2.size());

        Map<String, TestListSubject> map = new HashMap<>();

        for (Test t : testList1) {
            String studentNo = t.getStudent().getNo();
            Student student = studentDao.get(studentNo);  // ここで学生情報を取得し名前を得る

            TestListSubject bean = new TestListSubject();
            bean.setEntYear(entYear);
            bean.setStudentNo(studentNo);
            bean.setClassNum(classNum);
            bean.setStudentName(student != null ? student.getName() : "不明");
            bean.setPoints(new HashMap<>());
            bean.getPoints().put(1, t.getPoint());
            map.put(studentNo, bean);

            System.out.println("1回目: studentNo=" + studentNo + ", name=" + bean.getStudentName() + ", point=" + t.getPoint());
        }

        for (Test t : testList2) {
            String studentNo = t.getStudent().getNo();
            TestListSubject bean = map.get(studentNo);
            if (bean == null) {
                Student student = studentDao.get(studentNo); // 同様にここでも取得

                bean = new TestListSubject();
                bean.setEntYear(entYear);
                bean.setStudentNo(studentNo);
                bean.setClassNum(classNum);
                bean.setStudentName(student != null ? student.getName() : "不明");
                bean.setPoints(new HashMap<>());
                map.put(studentNo, bean);
            }
            bean.getPoints().put(2, t.getPoint());

            System.out.println("2回目: studentNo=" + studentNo + ", name=" + bean.getStudentName() + ", point=" + t.getPoint());
        }

        List<TestListSubject> resultList = new ArrayList<>(map.values());
        System.out.println("集計後の学生数: " + resultList.size());

        req.setAttribute("testList", resultList);
        req.setAttribute("entYear", entYear);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subject", subject);
        System.out.println("testList size = " + resultList.size());
        for(TestListSubject bean : resultList) {
          System.out.println(bean.getStudentNo() + ": " + bean.getPoints());
        }
        System.out.println("=== TestListSubjectExecuteAction: execute end ===");

        return "/main/test_list_subject.jsp";
    }
}
