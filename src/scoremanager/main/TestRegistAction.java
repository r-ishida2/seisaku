package scoremanager.main;

import java.time.Year;
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
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("[DEBUG] TestRegistAction: 処理開始");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        if (teacher == null) {
            System.out.println("[ERROR] セッションに教師情報がありません");
            req.setAttribute("error", "ログイン情報が取得できませんでした");
            return "/view/error.jsp";
        }

        School school = teacher.getSchool();
        System.out.println("[DEBUG] ログイン教師名: " + teacher.getName());
        System.out.println("[DEBUG] 学校コード: " + school.getCd());

        // リクエストパラメータ取得
        String entYearStr = req.getParameter("entYear");
        String classNum = req.getParameter("classNum");
        String subjectCd = req.getParameter("subject_cd");
        String noStr = req.getParameter("no");

        System.out.println("[DEBUG] リクエストパラメータ - ent_year: " + entYearStr);
        System.out.println("[DEBUG] リクエストパラメータ - class_num: " + classNum);
        System.out.println("[DEBUG] リクエストパラメータ - subject_cd: " + subjectCd);
        System.out.println("[DEBUG] リクエストパラメータ - no: " + noStr);

        req.setAttribute("ent_year", entYearStr);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("no", noStr);

        int entYear = 0;
        try {
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            System.err.println("[WARN] ent_year の数値変換に失敗: " + entYearStr);
        }

        // 各種データ取得
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(school);
        System.out.println("[DEBUG] 科目数: " + subjectList.size());

        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school);
        System.out.println("[DEBUG] クラス数: " + classNumList.size());

        StudentDao studentDao = new StudentDao();
        List<Student> studentList = studentDao.filter(school, entYear, classNum, true);
        System.out.println("[DEBUG] 学生数（該当条件）: " + studentList.size());

        // Testリスト生成
        List<Test> testList = new ArrayList<>();
        if (subjectCd != null && !subjectCd.isEmpty() && noStr != null && !noStr.isEmpty()) {
            try {
                int no = Integer.parseInt(noStr);

                Subject subject = new Subject();
                subject.setCd(subjectCd);

                TestDao testDao = new TestDao();

                for (Student student : studentList) {
                    Test test = new Test();
                    test.setStudent(student);
                    test.setSchool(school);
                    test.setClassNum(student.getClassNum());
                    test.setSubject(subject);
                    test.setNo(no);

                    // 既に登録されたテスト情報があるか確認
                    Test existingTest = testDao.get(student, subject, school, no);
                    if (existingTest != null) {
                        test.setPoint(existingTest.getPoint());
                    } else {
                        test.setPoint(0); // 未登録なら初期値0
                    }

                    testList.add(test);
                }

                System.out.println("[DEBUG] Testリスト作成数: " + testList.size());
            } catch (NumberFormatException e) {
                System.err.println("[WARN] no の数値変換に失敗: " + noStr);
            }
        } else {
            System.out.println("[DEBUG] Testリスト作成スキップ（subject_cd または no が未入力）");
        }

        int currentYear = Year.now().getValue();
        List<String> entYears = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            entYears.add(String.valueOf(i));
        }
        req.setAttribute("entYears", entYears);

        // JSP へ渡す
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("studentList", studentList);
        req.setAttribute("tests", testList);

        return "/main/test_regist.jsp";
    }
}
