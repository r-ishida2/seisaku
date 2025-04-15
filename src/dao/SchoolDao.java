package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SchoolDao extends Dao {

    /**
     * 指定された学校コードに紐づくクラス番号一覧を取得するメソッド
     */
    public List<String> findClassesBySchool(String schoolCd) throws Exception {
        List<String> classList = new ArrayList<>();

        // DB接続
        Connection con = getConnection();

        // SQL
        String sql = "SELECT CLASS_NUM FROM SCHOOL WHERE SCHOOL_CD = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, schoolCd);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            classList.add(rs.getString("CLASS_NUM"));
        }

        return classList;
    }

    /**
     * SCHOOL テーブルに登録されているすべての学校コードを取得するメソッド
     */
    public List<String> findAllSchools() throws Exception {
        List<String> schoolList = new ArrayList<>();

        Connection con = getConnection();
        String sql = "SELECT DISTINCT SCHOOL_CD FROM SCHOOL";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            schoolList.add(rs.getString("SCHOOL_CD"));
        }

        return schoolList;
    }
}
