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

public class TestListStudentExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	    System.out.println("=== TestListStudentExecuteAction: execute start ===");

	    HttpSession session = req.getSession();
	    Teacher teacher = (Teacher) session.getAttribute("NAME");

	    if (teacher == null) {
	        System.out.println("ログイン情報が確認できません。");
	        req.setAttribute("error", "ログイン情報が確認できません。");
	        return "/main/test_list_student.jsp";
	    }

	    School school = teacher.getSchool();
	    String studentNo = req.getParameter("student_no");

	    System.out.println("受信パラメータ: student_no=" + studentNo);

	    if (studentNo == null || studentNo.isEmpty()) {
	        System.out.println("生徒番号が空です");
	        req.setAttribute("error", "生徒番号が指定されていません。");
	        return "/main/test_list_student.jsp";
	    }

	    StudentDao studentDao = new StudentDao();
	    Student student = studentDao.get(studentNo);

	    if (student == null) {
	        System.out.println("該当する生徒が見つかりません: " + studentNo);
	        req.setAttribute("error", "指定された生徒が見つかりません。");
	        return "/main/test_list_student.jsp";
	    }

	    System.out.println("生徒取得成功: " + student.getName() + "（" + student.getNo() + "）");

	    SubjectDao subjectDao = new SubjectDao();
	    List<Subject> subjects = subjectDao.filter(school);

	    System.out.println("教科数: " + subjects.size());

	    List<Test> testList = new ArrayList<>();
	    TestDao testDao = new TestDao();

	    for (Subject subject : subjects) {
	        for (int pointNo = 1; pointNo <= 3; pointNo++) {
	            List<Test> partial = testDao.filter(
	                student.getEntYear(),
	                student.getClassNum(),
	                subject,
	                pointNo,
	                school
	            );
	            System.out.println("取得: subject=" + subject.getCd() + ", pointNo=" + pointNo + ", 件数=" + partial.size());
	            testList.addAll(partial);
	        }
	    }

	    System.out.println("総テスト件数: " + testList.size());

	    req.setAttribute("student", student);
	    req.setAttribute("testList", testList);

	    System.out.println("=== TestListStudentExecuteAction: execute end ===");

	    return "/main/test_list_student.jsp";
	}
}
