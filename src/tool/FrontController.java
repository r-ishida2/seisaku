package tool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * フロントコントローラ (Front Controller)
 * すべてのリクエストを一元管理し、適切なアクションクラスを実行する
 */
@WebServlet(urlPatterns={"*.action"}) // .actionで終わるURLをこのサーブレットで処理
public class FrontController extends HttpServlet {

    /**
     * POSTリクエストの処理
     * - リクエストのパスを取得し、対応するアクションクラスを動的にロード
     * - executeメソッドを実行し、戻り値のURLへフォワード
     */
    public void doPost(
        HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
    	System.out.println("Frontcontroller!");
        PrintWriter out = response.getWriter();
        try {
            // ① リクエストされたURLのパスを取得
            String path = request.getServletPath().substring(1);
            // 例: "/chapter23/Search.action" → "chapter23/Search.action"

            // ② パスをアクションクラス名の形式に変換
            String name = path.replace(".a", "A").replace('/', '.');
            // 例: "chapter23/Search.action" → "chapter23.SearchAction"

            // ③ アクションクラスのインスタンスを動的に生成
            Action action = (Action)Class.forName(name).
                getDeclaredConstructor().newInstance();
            // クラスを動的ロードし、コンストラクタを呼び出してインスタンスを作成

            // ④ executeメソッドを実行し、フォワード先のURLを取得
            String url = action.execute(request, response);
            // 例: "/searchResult.jsp" など

            // ⑤ 指定されたURLへフォワード
            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(out); // エラー発生時はスタックトレースを出力
        }
    }

    /**
     * GETリクエストの処理
     * - doPostメソッドを呼び出して、POSTリクエストと同じ処理を実行
     */
    public void doGet(
        HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        doPost(request, response); // GETリクエストもPOSTと同様に処理
    }
}
