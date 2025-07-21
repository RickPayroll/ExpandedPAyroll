@@ .. @@
 -- Create credentials table
 -- =============================================
 CREATE TABLE credentials (
     employee_id INT PRIMARY KEY,
     password VARCHAR(255) NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
 );