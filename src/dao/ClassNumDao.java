package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    // 特定の class_num を持つ ClassNum を取得
    public ClassNum get(String class_num, School school) throws Exception {
        ClassNum classNum = null;
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM class_num WHERE class_num = ? AND school_cd = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, class_num);
            stmt.setString(2, school.getCd());
            rs = stmt.executeQuery();

            if (rs.next()) {
                classNum = new ClassNum();
                classNum.setClass_num(rs.getString("class_num"));
                classNum.setSchool(school);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return classNum;
    }

    // 指定された学校に属するクラス番号一覧を取得
    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT class_num FROM class_num WHERE school_cd = ? ORDER BY class_num";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, school.getCd());
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("class_num"));
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ClassNum を新規登録
    public boolean save(ClassNum classNum) throws Exception {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        int result = 0;

        try {
            String sql = "INSERT INTO class_num (class_num, school_cd) VALUES (?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, classNum.getClass_num());
            stmt.setString(2, classNum.getSchool().getCd());

            result = stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return result == 1;
    }

    // 既存の ClassNum のクラス番号を変更
    public boolean save(ClassNum classNum, String newClassNum) throws Exception {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        int result = 0;

        try {
            String sql = "UPDATE class_num SET class_num = ? WHERE class_num = ? AND school_cd = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, newClassNum);                       // 新しいクラス番号
            stmt.setString(2, classNum.getClass_num());           // 現在のクラス番号
            stmt.setString(3, classNum.getSchool().getCd());      // 学校コード

            result = stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return result == 1;
    }
}