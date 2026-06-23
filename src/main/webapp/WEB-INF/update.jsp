<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Issue #${issue.id}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header>
    <div class="header-inner">
        <div class="logo">⚙️ Warehouse Issue Tracker</div>
        <span class="tagline">Lenskart — Plant Operations Support</span>
    </div>
</header>

<main>
    <section class="card form-section update-form">

        <div class="update-header">
            <a href="${pageContext.request.contextPath}/issues" class="back-link">← Back to Issues</a>
            <h2>Update Issue #${issue.id}</h2>
        </div>

        <!-- Read-only issue details -->
        <div class="issue-detail-box">
            <div class="detail-row">
                <span class="detail-label">Title</span>
                <span class="detail-value"><strong>${issue.title}</strong></span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Description</span>
                <span class="detail-value">${issue.description}</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Department</span>
                <span class="detail-value">${issue.department}</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Reported By</span>
                <span class="detail-value">${issue.reportedBy}</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Priority</span>
                <span class="detail-value">
                    <span class="badge priority-${issue.priority.toLowerCase()}">${issue.priority}</span>
                </span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Logged At</span>
                <span class="detail-value">${issue.createdAt}</span>
            </div>
        </div>

        <!-- Update form -->
        <form action="${pageContext.request.contextPath}/update" method="post">
            <input type="hidden" name="id" value="${issue.id}">

            <div class="form-group">
                <label>Update Status</label>
                <select name="status">
                    <option value="Open"        ${issue.status == 'Open'        ? 'selected' : ''}>Open</option>
                    <option value="In Progress" ${issue.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                    <option value="Resolved"    ${issue.status == 'Resolved'    ? 'selected' : ''}>Resolved</option>
                </select>
            </div>

            <div class="form-group">
                <label>Resolution Notes / Dev Team Message</label>
                <textarea name="resolutionNotes" rows="4"
                    placeholder="Explain the fix or current status to operations team...">${issue.resolutionNotes}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Save Update</button>
                <a href="${pageContext.request.contextPath}/issues" class="btn btn-secondary">Cancel</a>
            </div>
        </form>

    </section>
</main>

<footer>
    <p>Warehouse Issue Tracker &copy; 2026 &mdash; Built with Java Servlets + JSP + MySQL</p>
</footer>

</body>
</html>
