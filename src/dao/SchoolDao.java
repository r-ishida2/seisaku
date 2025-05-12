package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;

public class SchoolDao extends Dao {

    /**
     * 学校コードから School を1件取得する
     */
    public School get(String cd) throws Exception {
        School school = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM school WHERE school_cd = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cd);
            rs = stmt.executeQuery();

            if (rs.next()) {
                school = new School();
                school.setCd(rs.getString("school_cd"));
                school.setName(rs.getString("name"));
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        }

        return school;
    }
}
