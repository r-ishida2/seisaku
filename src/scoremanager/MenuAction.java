package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class MenuAction extends Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // index.jsp に遷移するだけ
        return "index.jsp";
    }

}
