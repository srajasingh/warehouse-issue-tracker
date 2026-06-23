# ⚙️ Warehouse Issue Tracker

A web-based production issue tracking system built to simulate the L1 Support workflow in a warehouse/plant environment. Warehouse supervisors can log technical issues, operations teams can track status, and the core development team gets structured, clear problem reports.

**Tech Stack:** Java · JSP · Servlets · MySQL · JDBC · Apache Tomcat · HTML/CSS

---

## Features

- **Log Issues** — Supervisors submit issues with title, description, priority, and department
- **Track Status** — View all issues filtered by Open / In Progress / Resolved
- **Update & Resolve** — Dev team updates status and adds resolution notes
- **Delete Issues** — Remove closed or duplicate entries
- **Dashboard Stats** — Live count of Open / In Progress / Resolved issues
- **Priority Sorting** — High priority issues surface first automatically

---

## Project Structure

```
warehouse-issue-tracker/
├── src/main/
│   ├── java/com/tracker/
│   │   ├── model/Issue.java          # POJO - issue data model
│   │   ├── dao/IssueDAO.java         # All CRUD operations via JDBC
│   │   ├── servlet/IssueServlet.java # Handles list, add, delete
│   │   ├── servlet/UpdateServlet.java# Handles status update
│   │   └── util/DBConnection.java    # MySQL JDBC connection manager
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── issues.jsp            # Main dashboard
│       │   ├── update.jsp            # Update status form
│       │   └── web.xml               # Deployment descriptor
│       ├── css/style.css             # Stylesheet
│       └── index.jsp                 # Entry point (redirects to /issues)
├── sql/
│   └── schema.sql                    # DB schema + sample data
└── pom.xml                           # Maven build config
```

---

## Setup & Run

### Prerequisites
- Java 11+
- Apache Tomcat 10+
- MySQL 8+
- Maven 3.6+

### Step 1 — Set up the database
```sql
-- In MySQL Workbench or terminal:
source sql/schema.sql
```

### Step 2 — Update DB credentials
Open `src/main/java/com/tracker/util/DBConnection.java` and update:
```java
private static final String DB_USER = "root";   // your MySQL username
private static final String DB_PASS = "root";   // your MySQL password
```

### Step 3 — Build the WAR
```bash
mvn clean package
```

### Step 4 — Deploy to Tomcat
Copy `target/warehouse-issue-tracker.war` to Tomcat's `webapps/` folder, then start Tomcat:
```bash
# Linux/Mac
$CATALINA_HOME/bin/startup.sh

# Windows
%CATALINA_HOME%\bin\startup.bat
```

### Step 5 — Open in browser
```
http://localhost:8080/warehouse-issue-tracker/
```

---

## Database Schema

```sql
CREATE TABLE issues (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(100) NOT NULL,
    description      TEXT,
    priority         ENUM('High', 'Medium', 'Low') DEFAULT 'Medium',
    department       VARCHAR(50),
    reported_by      VARCHAR(100),
    status           ENUM('Open', 'In Progress', 'Resolved') DEFAULT 'Open',
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    resolved_at      TIMESTAMP NULL,
    resolution_notes TEXT
);
```

---

## SDLC Followed

| Phase | Details |
|---|---|
| Requirements | Modelled on real Lenskart L1 support workflow — ops teams logging plant issues |
| Design | MVC architecture: Model (Issue.java) → DAO (IssueDAO.java) → Servlet → JSP View |
| Development | Java Servlets + JSP + JDBC; clean separation of concerns |
| Testing | Manual testing across CRUD flows, edge cases (empty fields, invalid IDs) |
| Deployment | WAR deployment on Apache Tomcat 10 |
| Documentation | README, inline code comments, SQL schema file |

---

## Author

**Sraja Singh**  
B.E. Computer Science, Chandigarh University  
[linkedin.com/in/sraja-singh](https://linkedin.com/in/sraja-singh) · [github.com/srajasingh](https://github.com/srajasingh)
