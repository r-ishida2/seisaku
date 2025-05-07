package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class StudentCreateAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 必要に応じて初期データの取得などを行う（今回は特にない前提）

        // JSPのパスを返すだけ
        return "/view/student/create.jsp";
    }
}
