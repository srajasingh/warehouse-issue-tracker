<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Warehouse Issue Tracker</title>
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

    <!-- ── STATS CARDS ──────────────────────────────────────────────── -->
    <section class="stats">
        <div class="card stat-card open">
            <div class="stat-num">${totalOpen}</div>
            <div class="stat-label">Open</div>
        </div>
        <div class="card stat-card inprogress">
            <div class="stat-num">${totalInProgress}</div>
            <div class="stat-label">In Progress</div>
        </div>
        <div class="card stat-card resolved">
            <div class="stat-num">${totalResolved}</div>
            <div class="stat-label">Resolved</div>
        </div>
    </section>

    <!-- ── LOG NEW ISSUE FORM ────────────────────────────────────────── -->
    <section class="card form-section">
        <h2>Log New Issue</h2>
        <form action="${pageContext.request.contextPath}/issues" method="post">
            <input type="hidden" name="action" value="add">
            <div class="form-grid">
                <div class="form-group">
                    <label>Issue Title *</label>
                    <input type="text" name="title" placeholder="e.g. Barcode scanner not reading" required>
                </div>
                <div class="form-group">
                    <label>Reported By *</label>
                    <input type="text" name="reportedBy" placeholder="Supervisor name" required>
                </div>
                <div class="form-group">
                    <label>Department</label>
                    <select name="department">
                        <option value="Inbound">Inbound</option>
                        <option value="Dispatch">Dispatch</option>
                        <option value="Inventory">Inventory</option>
                        <option value="Packing">Packing</option>
                        <option value="IT">IT</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Priority</label>
                    <select name="priority">
                        <option value="High">🔴 High</option>
                        <option value="Medium" selected>🟡 Medium</option>
                        <option value="Low">🟢 Low</option>
                    </select>
                </div>
                <div class="form-group full-width">
                    <label>Description</label>
                    <textarea name="description" rows="3"
                        placeholder="Describe the issue in detail so the core dev team can understand..."></textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Submit Issue</button>
        </form>
    </section>

    <!-- ── FILTER TABS ───────────────────────────────────────────────── -->
    <section class="filter-bar">
        <span class="filter-label">Filter:</span>
        <a href="?filter=All"         class="filter-btn ${currentFilter == 'All'         ? 'active' : ''}">All</a>
        <a href="?filter=Open"        class="filter-btn ${currentFilter == 'Open'        ? 'active' : ''}">Open</a>
        <a href="?filter=In Progress" class="filter-btn ${currentFilter == 'In Progress' ? 'active' : ''}">In Progress</a>
        <a href="?filter=Resolved"    class="filter-btn ${currentFilter == 'Resolved'    ? 'active' : ''}">Resolved</a>
    </section>

    <!-- ── ISSUES TABLE ──────────────────────────────────────────────── -->
    <section class="card table-section">
        <c:choose>
            <c:when test="${empty issues}">
                <p class="empty-msg">No issues found for this filter.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Department</th>
                            <th>Reported By</th>
                            <th>Priority</th>
                            <th>Status</th>
                            <th>Logged At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="issue" items="${issues}">
                        <tr>
                            <td>${issue.id}</td>
                            <td class="title-cell">
                                <strong>${issue.title}</strong>
                                <c:if test="${not empty issue.description}">
                                    <p class="desc">${issue.description}</p>
                                </c:if>
                            </td>
                            <td>${issue.department}</td>
                            <td>${issue.reportedBy}</td>
                            <td>
                                <span class="badge priority-${issue.priority.toLowerCase()}">
                                    ${issue.priority}
                                </span>
                            </td>
                            <td>
                                <span class="badge status-${issue.status.toLowerCase().replace(' ', '-')}">
                                    ${issue.status}
                                </span>
                            </td>
                            <td class="date-cell">${issue.createdAt}</td>
                            <td class="action-cell">
                                <a href="${pageContext.request.contextPath}/update?id=${issue.id}"
                                   class="btn btn-sm btn-update">Update</a>
                                <form action="${pageContext.request.contextPath}/issues"
                                      method="post" style="display:inline"
                                      onsubmit="return confirm('Delete this issue?')">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${issue.id}">
                                    <button type="submit" class="btn btn-sm btn-delete">Delete</button>
                                </form>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>

</main>

<footer>
    <p>Warehouse Issue Tracker &copy; 2026 &mdash; Built with Java Servlets + JSP + MySQL</p>
</footer>

</body>
</html>
