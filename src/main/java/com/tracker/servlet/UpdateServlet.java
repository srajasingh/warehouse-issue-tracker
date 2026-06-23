package com.tracker.servlet;

import com.tracker.dao.IssueDAO;
import com.tracker.model.Issue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Handles viewing and updating a single issue's status.
 *   GET  /update?id=X  → show update form for issue X
 *   POST /update       → save new status + resolution notes
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

    private final IssueDAO dao = new IssueDAO();

    // ── GET: show update form ─────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        Issue issue = dao.getIssueById(id);

        if (issue == null) {
            resp.sendRedirect(req.getContextPath() + "/issues");
            return;
        }

        req.setAttribute("issue", issue);
        req.getRequestDispatcher("/WEB-INF/update.jsp").forward(req, resp);
    }

    // ── POST: save update ─────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        int    id              = Integer.parseInt(req.getParameter("id"));
        String status          = req.getParameter("status");
        String resolutionNotes = req.getParameter("resolutionNotes");

        dao.updateIssueStatus(id, status, resolutionNotes);
        resp.sendRedirect(req.getContextPath() + "/issues");
    }
}
