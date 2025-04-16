package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;
public class LogoutAction extends Action {
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session=request.getSession();

		if (session.getAttribute("NAME")!=null) {
			session.removeAttribute("NAME");
			//仮置き
			return ".jsp";
		}
		//jspができるまでの仮置き
		return "error.jsp";
	}
}
