package com.tracker.servlet;

import com.tracker.dao.IssueDAO;
import com.tracker.model.Issue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Main servlet — handles:
 *   GET  /issues          → show all issues (with optional status filter)
 *   POST /issues?action=add    → add new issue
 *   POST /issues?action=delete → delete issue by id
 */
@WebServlet("/issues")
public class IssueServlet extends HttpServlet {

    private final IssueDAO dao = new IssueDAO();

    // ── GET: list issues ──────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String filter = req.getParameter("filter"); // Open / In Progress / Resolved / null

        List<Issue> issues;
        if (filter != null && !filter.isEmpty() && !filter.equals("All")) {
            issues = dao.getIssuesByStatus(filter);
        } else {
            issues = dao.getAllIssues();
        }

        // Stats for summary cards
        req.setAttribute("totalOpen",       dao.countByStatus("Open"));
        req.setAttribute("totalInProgress", dao.countByStatus("In Progress"));
        req.setAttribute("totalResolved",   dao.countByStatus("Resolved"));
        req.setAttribute("issues",          issues);
        req.setAttribute("currentFilter",   filter != null ? filter : "All");

        req.getRequestDispatcher("/WEB-INF/issues.jsp").forward(req, resp);
    }

    // ── POST: add or delete ───────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            Issue issue = new Issue(
                req.getParameter("title"),
                req.getParameter("description"),
                req.getParameter("priority"),
                req.getParameter("department"),
                req.getParameter("reportedBy")
            );
            dao.addIssue(issue);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.deleteIssue(id);
        }

        resp.sendRedirect(req.getContextPath() + "/issues");
    }
}
