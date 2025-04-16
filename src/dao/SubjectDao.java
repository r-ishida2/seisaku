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
            String sql = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, subject.getCd());
            stmt.setString(2, subject.getName());
            stmt.setString(3, subject.getSchool().getCd());
            int rows = stmt.executeUpdate();
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
