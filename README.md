# ⚙️ Warehouse Issue Tracker

A web-based production issue tracking system built to simulate the L1 Support workflow in a warehouse/plant environment. Warehouse supervisors log technical issues, operations teams track status, and the core development team gets structured, clear problem reports.

**Tech Stack:** Java · JSP · Servlets · MySQL · JDBC · Apache Tomcat · HTML/CSS

---

## 🎥 Live Demo

▶️ **[Watch Demo on Loom](YOUR_LOOM_LINK_HERE)** — 2-min walkthrough: logging an issue, updating status, resolving it

🌐 **[Live App on Railway](YOUR_RAILWAY_URL_HERE)**

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
├── pom.xml
├── Procfile                                    ← Railway deployment config
├── sql/schema.sql                              ← Run this in MySQL first
└── src/main/
    ├── java/com/tracker/
    │   ├── model/Issue.java                    ← POJO data model
    │   ├── dao/IssueDAO.java                   ← All CRUD via JDBC
    │   ├── servlet/IssueServlet.java           ← List, add, delete
    │   ├── servlet/UpdateServlet.java          ← Status update
    │   └── util/DBConnection.java             ← MySQL connection (local + Railway)
    └── webapp/
        ├── WEB-INF/issues.jsp                  ← Main dashboard
        ├── WEB-INF/update.jsp                  ← Update form
        ├── css/style.css
        └── index.jsp
```

---

## Run Locally

### Prerequisites
- Java 11+, Maven 3.6+, MySQL 8+, Apache Tomcat 10+

### Steps
```bash
# 1. Set up database
mysql -u root -p < sql/schema.sql

# 2. Update your MySQL password in:
#    src/main/java/com/tracker/util/DBConnection.java

# 3. Build
mvn clean package

# 4. Copy WAR to Tomcat webapps/ folder, start Tomcat
# Open: http://localhost:8080/warehouse-issue-tracker/
```

---

## Deploy on Railway

### Step 1 — Create Railway account
Go to **railway.app** → Sign up with GitHub

### Step 2 — Create new project
- Click **New Project**
- Select **Deploy from GitHub repo**
- Choose `warehouse-issue-tracker`
- Railway auto-detects Maven and starts building

### Step 3 — Add MySQL database
- Inside your project, click **+ New**
- Select **Database** → **MySQL**
- Railway creates a MySQL instance and auto-injects these env variables into your app:
  `MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`, `MYSQLUSER`, `MYSQLPASSWORD`
- The app's `DBConnection.java` reads these automatically — no manual config needed

### Step 4 — Run schema on Railway MySQL
- Click your MySQL service → **Connect** tab
- Copy the connection string and run it in MySQL Workbench or TablePlus
- Paste and run the contents of `sql/schema.sql`

### Step 5 — Get your live URL
- Click your app service → **Settings** → **Domains** → **Generate Domain**
- Your app is live at `https://your-app-name.up.railway.app`

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
| Requirements | Modelled on real L1 support workflow — ops teams logging plant issues |
| Design | MVC: Model → DAO → Servlet → JSP View |
| Development | Java Servlets + JSP + JDBC; clean separation of concerns |
| Testing | Manual testing across all CRUD flows and edge cases |
| Deployment | WAR on Tomcat locally; Railway cloud deployment |
| Documentation | README, inline comments, SQL schema |

---

## Author

**Sraja Singh** — B.E. Computer Science, Chandigarh University  
[linkedin.com/in/sraja-singh](https://linkedin.com/in/sraja-singh) · [github.com/srajasingh](https://github.com/srajasingh)
