-- ============================================================
-- Warehouse Issue Tracker - Database Schema
-- Run this in MySQL before starting the application
-- ============================================================

CREATE DATABASE IF NOT EXISTS warehouse_tracker;
USE warehouse_tracker;

CREATE TABLE IF NOT EXISTS issues (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(100)  NOT NULL,
    description     TEXT,
    priority        ENUM('High', 'Medium', 'Low') DEFAULT 'Medium',
    department      VARCHAR(50),
    reported_by     VARCHAR(100),
    status          ENUM('Open', 'In Progress', 'Resolved') DEFAULT 'Open',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    resolved_at     TIMESTAMP NULL,
    resolution_notes TEXT
);

-- Sample data so the app looks populated on first run
INSERT INTO issues (title, description, priority, department, reported_by, status) VALUES
('Barcode scanner not reading', 'Scanner at Gate 2 fails to read QR codes on incoming shipments', 'High', 'Inbound', 'Ravi Kumar', 'Open'),
('Order sync delay', 'Orders placed after 6 PM are not syncing to dispatch queue until next morning', 'High', 'Dispatch', 'Anita Sharma', 'In Progress'),
('Inventory count mismatch', 'System shows 240 units of SKU-4421 but physical count is 198', 'Medium', 'Inventory', 'Mohit Singh', 'Open'),
('Label printer offline', 'Printer at packing station 3 is showing offline since morning shift', 'Low', 'Packing', 'Priya Nair', 'Resolved');
