package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
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
        String sql = "SELECT * FROM TEACHER WHERE ID = ? AND PASSWORD = ?";

        try (
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ) {
            // プレースホルダーに値をセット
            stmt.setString(1, id);
            stmt.setString(2, password);

            // 実行して結果取得
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getString("ID"));
                teacher.setName(rs.getString("NAME"));

                // SCHOOL_CD を取得して School オブジェクトに設定
                School school = new School();
                school.setCd(rs.getString("SCHOOL_CD"));
                teacher.setSchool(school);

                return teacher;
            }

            // 一致しなければ null を返す
            return null;
        }
    }
}
