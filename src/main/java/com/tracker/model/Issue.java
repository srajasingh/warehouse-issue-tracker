package com.tracker.model;

import java.sql.Timestamp;

/**
 * POJO representing a single warehouse issue record.
 */
public class Issue {

    private int id;
    private String title;
    private String description;
    private String priority;       // High / Medium / Low
    private String department;
    private String reportedBy;
    private String status;         // Open / In Progress / Resolved
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp resolvedAt;
    private String resolutionNotes;

    // ── Constructors ─────────────────────────────────────────────────────────

    public Issue() {}

    public Issue(String title, String description, String priority,
                 String department, String reportedBy) {
        this.title       = title;
        this.description = description;
        this.priority    = priority;
        this.department  = department;
        this.reportedBy  = reportedBy;
        this.status      = "Open";
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public int getId()                        { return id; }
    public void setId(int id)                 { this.id = id; }

    public String getTitle()                  { return title; }
    public void setTitle(String title)        { this.title = title; }

    public String getDescription()            { return description; }
    public void setDescription(String d)      { this.description = d; }

    public String getPriority()               { return priority; }
    public void setPriority(String priority)  { this.priority = priority; }

    public String getDepartment()             { return department; }
    public void setDepartment(String dept)    { this.department = dept; }

    public String getReportedBy()             { return reportedBy; }
    public void setReportedBy(String r)       { this.reportedBy = r; }

    public String getStatus()                 { return status; }
    public void setStatus(String status)      { this.status = status; }

    public Timestamp getCreatedAt()           { return createdAt; }
    public void setCreatedAt(Timestamp t)     { this.createdAt = t; }

    public Timestamp getUpdatedAt()           { return updatedAt; }
    public void setUpdatedAt(Timestamp t)     { this.updatedAt = t; }

    public Timestamp getResolvedAt()          { return resolvedAt; }
    public void setResolvedAt(Timestamp t)    { this.resolvedAt = t; }

    public String getResolutionNotes()        { return resolutionNotes; }
    public void setResolutionNotes(String n)  { this.resolutionNotes = n; }
}
