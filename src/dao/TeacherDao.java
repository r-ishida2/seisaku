package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Teacher;

public class TeacherDao extends Dao {

    /**
     * 👨‍🏫 教員ログイン処理
     *
     * @param id 教員ID
     * @param password パスワード
     * @return 一致すればTeacherオブジェクトを返す。不一致ならnull。
     * @throws Exception SQLや接続時のエラー
     */
    public Teacher login(String id, String password) throws Exception {
        // SQL：IDとパスワードで教員を検索

        try (
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM TEACHER WHERE ID = ? AND PASSWORD = ?");
        ) {
            // プレースホルダーに値をセット
            stmt.setString(1, id);
            stmt.setString(2, password);

            // 実行して結果取得
            ResultSet rs = stmt.executeQuery();

            // 一致する教員がいればTeacherオブジェクトにして返す
            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getString("ID"));
                teacher.setName(rs.getString("NAME"));
                // 他にも必要ならセット
                return teacher;
            }

            // 一致しなければnullを返す
            return null;
        }
    }
}
