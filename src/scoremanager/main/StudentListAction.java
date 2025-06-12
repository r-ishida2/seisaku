package scoremanager.main;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("NAME");

        if (teacher == null) {
            return "/login.jsp";
        }

        School school = teacher.getSchool();

        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNums = classNumDao.filter(school);
        request.setAttribute("classNums", classNums);
        int currentYear = Year.now().getValue();  // 例：2025
        List<String> entYears = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            entYears.add(String.valueOf(i));
        }
        request.setAttribute("entYears", entYears);

        String[] isAttendValues = request.getParameterValues("is_attend");
        Boolean isAttend = null;
        String isAttendStr = null;
        if (isAttendValues != null) {
            for (String v : isAttendValues) {
                if ("true".equals(v)) {
                    isAttend = true;
                    isAttendStr = "true";
                    break;
                } else if ("false".equals(v)) {
                    isAttend = false;
                    isAttendStr = "false";
                }
            }
        }

        System.out.println("DEBUG: リクエストパラメータ");
        System.out.println(" - ent_year   = " + entYearStr);
        System.out.println(" - class_num  = " + classNum);
        System.out.println(" - is_attend  = " + isAttendStr);

        List<Student> students;
        StudentDao dao = new StudentDao();

        boolean hasEntYear = entYearStr != null && !entYearStr.trim().isEmpty();
        boolean hasIsAttend = isAttendStr != null && !isAttendStr.trim().isEmpty();

        if (!hasEntYear && classNum != null && !classNum.trim().isEmpty()) {
            students = new ArrayList<>();
            students.addAll(dao.filter(school, true));
            students.addAll(dao.filter(school, false));

            students.sort(Comparator.comparing(Student::getNo)); // ← ソート追加

            request.setAttribute("error", "クラスを指定する場合は入学年度も指定してください");
            request.setAttribute("ent_year", "");
            request.setAttribute("class_num", classNum.trim());
            request.setAttribute("is_attend", isAttendStr);
            request.setAttribute("students", students);
            return "/main/student_list.jsp";
        }

        if (!hasEntYear && !hasClassNum(classNum) && Boolean.FALSE.equals(isAttend)) {
            students = new ArrayList<>();
            students.addAll(dao.filter(school, true));
            students.addAll(dao.filter(school, false));

            students.sort(Comparator.comparing(Student::getNo)); // ← ソート追加

            request.setAttribute("ent_year", "");
            request.setAttribute("class_num", "");
            request.setAttribute("is_attend", isAttendStr);
            request.setAttribute("students", students);
            return "/main/student_list.jsp";
        }

        if (hasEntYear && hasIsAttend) {
            try {
                int entYear = Integer.parseInt(entYearStr.trim());

                if (hasClassNum(classNum)) {
                    System.out.println("DEBUG: filter(school, entYear, classNum, isAttend) 呼び出し");
                    students = dao.filter(school, entYear, classNum.trim(), isAttend);
                } else {
                    System.out.println("DEBUG: filter(school, entYear, isAttend) 呼び出し");
                    students = dao.filter(school, entYear, isAttend);
                }

                request.setAttribute("ent_year", entYearStr.trim());
                request.setAttribute("class_num", hasClassNum(classNum) ? classNum.trim() : "");
                request.setAttribute("is_attend", isAttendStr);

            } catch (NumberFormatException e) {
                System.out.println("DEBUG: NumberFormatException → filter(school, true)");
                students = dao.filter(school, true);
                request.setAttribute("ent_year", "");
                request.setAttribute("class_num", hasClassNum(classNum) ? classNum.trim() : "");
                request.setAttribute("is_attend", "true");
            }

        } else if (hasIsAttend) {
            System.out.println("DEBUG: filter(school, isAttend) 呼び出し");
            students = dao.filter(school, isAttend);

            request.setAttribute("ent_year", "");
            request.setAttribute("class_num", hasClassNum(classNum) ? classNum.trim() : "");
            request.setAttribute("is_attend", isAttendStr);

        } else {
            System.out.println("DEBUG: 初期表示 → 全件取得");
            students = new ArrayList<>();
            students.addAll(dao.filter(school, true));
            students.addAll(dao.filter(school, false));

            request.setAttribute("ent_year", "");
            request.setAttribute("class_num", hasClassNum(classNum) ? classNum.trim() : "");
            request.setAttribute("is_attend", isAttendStr);
        }

        // ★ 全体の最後でソート（どの分岐でもここを通る）
        students.sort(Comparator.comparing(Student::getNo));

        request.setAttribute("students", students);
        return "/main/student_list.jsp";
    }

    private boolean hasClassNum(String classNum) {
        return classNum != null && !classNum.trim().isEmpty();
    }
}
