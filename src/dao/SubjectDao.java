package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    public Subject get(String cd, School school) {
        Subject subject = null;
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cd);
            stmt.setString(2, school.getCd());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                subject = new Subject();
                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));
                subject.setSchool(school);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

    public List<Subject> filter(School school) {
        List<Subject> list = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, school.getCd());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));
                subject.setSchool(school);
                list.add(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean save(Subject subject) {
        try (Connection connection = getConnection()) {
            // まず、レコードの存在確認
            String checkSql = "SELECT COUNT(*) FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setString(1, subject.getCd());
            checkStmt.setString(2, subject.getSchool().getCd());
            ResultSet rs = checkStmt.executeQuery();

            boolean exists = false;
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

            int rows = 0;
            if (exists) {
                // 存在するならUPDATE
                String updateSql = "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setString(1, subject.getName());
                updateStmt.setString(2, subject.getCd());
                updateStmt.setString(3, subject.getSchool().getCd());
                rows = updateStmt.executeUpdate();
            } else {
                // 存在しなければINSERT
                String insertSql = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setString(1, subject.getCd());
                insertStmt.setString(2, subject.getName());
                insertStmt.setString(3, subject.getSchool().getCd());
                rows = insertStmt.executeUpdate();
            }

            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Subject subject) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, subject.getCd());
            stmt.setString(2, subject.getSchool().getCd());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
