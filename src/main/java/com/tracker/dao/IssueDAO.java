package com.tracker.dao;

import com.tracker.model.Issue;
import com.tracker.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object — all CRUD operations for the issues table.
 */
public class IssueDAO {

    // ── CREATE ───────────────────────────────────────────────────────────────

    public boolean addIssue(Issue issue) {
        String sql = "INSERT INTO issues (title, description, priority, department, reported_by) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, issue.getTitle());
            ps.setString(2, issue.getDescription());
            ps.setString(3, issue.getPriority());
            ps.setString(4, issue.getDepartment());
            ps.setString(5, issue.getReportedBy());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── READ ALL ─────────────────────────────────────────────────────────────

    public List<Issue> getAllIssues() {
        List<Issue> list = new ArrayList<>();
        String sql = "SELECT * FROM issues ORDER BY " +
                     "FIELD(priority,'High','Medium','Low'), created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ── READ BY ID ───────────────────────────────────────────────────────────

    public Issue getIssueById(int id) {
        String sql = "SELECT * FROM issues WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ── UPDATE STATUS ────────────────────────────────────────────────────────

    public boolean updateIssueStatus(int id, String status, String resolutionNotes) {
        String sql = "UPDATE issues SET status = ?, resolution_notes = ?, " +
                     "resolved_at = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, resolutionNotes);
            ps.setTimestamp(3, status.equals("Resolved") ? new Timestamp(System.currentTimeMillis()) : null);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    public boolean deleteIssue(int id) {
        String sql = "DELETE FROM issues WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── FILTER BY STATUS ─────────────────────────────────────────────────────

    public List<Issue> getIssuesByStatus(String status) {
        List<Issue> list = new ArrayList<>();
        String sql = "SELECT * FROM issues WHERE status = ? " +
                     "ORDER BY FIELD(priority,'High','Medium','Low'), created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ── STATS FOR DASHBOARD ──────────────────────────────────────────────────

    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM issues WHERE status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ── ROW MAPPER ───────────────────────────────────────────────────────────

    private Issue mapRow(ResultSet rs) throws SQLException {
        Issue issue = new Issue();
        issue.setId(rs.getInt("id"));
        issue.setTitle(rs.getString("title"));
        issue.setDescription(rs.getString("description"));
        issue.setPriority(rs.getString("priority"));
        issue.setDepartment(rs.getString("department"));
        issue.setReportedBy(rs.getString("reported_by"));
        issue.setStatus(rs.getString("status"));
        issue.setCreatedAt(rs.getTimestamp("created_at"));
        issue.setUpdatedAt(rs.getTimestamp("updated_at"));
        issue.setResolvedAt(rs.getTimestamp("resolved_at"));
        issue.setResolutionNotes(rs.getString("resolution_notes"));
        return issue;
    }
}
