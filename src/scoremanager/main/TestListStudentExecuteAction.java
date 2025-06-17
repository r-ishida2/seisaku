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

public class TestListStudentExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession sessions = req.getSession();
	    Teacher teachers = (Teacher) sessions.getAttribute("NAME");

		School schools = teachers.getSchool();
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNums = classNumDao.filter(schools);
        req.setAttribute("classNums", classNums);

        int currentYear = Year.now().getValue();  // 例：2025
        List<String> entYears = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            entYears.add(String.valueOf(i));
        }
        req.setAttribute("entYears", entYears);

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(teachers.getSchool());
        req.setAttribute("subjectList", subjectList);

	    System.out.println("=== TestListStudentExecuteAction: execute start ===");

	    HttpSession session = req.getSession();
	    Teacher teacher = (Teacher) session.getAttribute("NAME");

	    if (teacher == null) {
	        System.out.println("[エラー] ログイン情報が確認できません");
	        req.setAttribute("error", "ログイン情報が確認できません。");
	        return "/main/test_list_student.jsp";
	    }

	    School school = teacher.getSchool();
	    String studentNo = req.getParameter("student_no");
	    System.out.println("受信パラメータ: student_no=" + studentNo);

	    if (studentNo == null || studentNo.isEmpty()) {
	        System.out.println("[エラー] 生徒番号が指定されていません");
	        req.setAttribute("error", "生徒番号が指定されていません。");
	        return "/main/test_list_student.jsp";
	    }

	    StudentDao studentDao = new StudentDao();
	    Student student = studentDao.get(studentNo);

	    if (student == null) {
	        System.out.println("[エラー] 該当する生徒が見つかりません: " + studentNo);
	        req.setAttribute("error", "指定された生徒が見つかりません。");
	        return "/main/test_list_student.jsp";
	    }

	    System.out.println("[OK] 生徒取得成功: " + student.getName() + "（" + student.getNo() + "）");

	    List<Subject> subjects = subjectDao.filter(school);
	    System.out.println("取得した科目数: " + subjects.size());

	    List<Test> testList = new ArrayList<>();
	    TestDao testDao = new TestDao();

	    for (Subject subject : subjects) {
	        System.out.println("---- 科目: " + subject.getCd() + " / " + subject.getName());

	        for (int pointNo = 1; pointNo <= 3; pointNo++) {
	            List<Test> partial = testDao.filter(
	                student.getEntYear(),
	                student.getClassNum(),
	                subject,
	                pointNo,
	                school
	            );
	            System.out.println("  → 回数: " + pointNo + " 件数: " + partial.size());

	            for (Test test : partial) {
	                // === 修正ポイント: student番号で一致するものだけ追加 ===
	                if (test.getStudent().getNo().equals(student.getNo())) {
	                    test.setSubject(subject);  // 科目名表示用にセット
	                    testList.add(test);
	                    System.out.println("    - 点数: " + test.getPoint() + " 科目: " + test.getSubject().getName() + " 回数: " + test.getNo());
	                }
	            }
	        }
	    }

	    System.out.println("総テスト件数: " + testList.size());

	    req.setAttribute("student", student);
	    req.setAttribute("testList", testList);

	    System.out.println("=== TestListStudentExecuteAction: execute end ===");

	    return "/main/test_list_student.jsp";
	}
}
