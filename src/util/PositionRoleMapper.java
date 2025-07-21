package util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * SIMPLIFIED: Maps employee positions to access levels
 * Removed UserRole dependency to fix compilation issues
 */
public class PositionRoleMapper {
    private static final Logger LOGGER = Logger.getLogger(PositionRoleMapper.class.getName());
    private static final Map<String, String> POSITION_ROLE_MAP = new HashMap<>();
    
    static {
        // Executive Level - C-Suite
        POSITION_ROLE_MAP.put("chief executive officer", "EXECUTIVE");
        POSITION_ROLE_MAP.put("chief operating officer", "EXECUTIVE");
        POSITION_ROLE_MAP.put("chief finance officer", "EXECUTIVE");
        POSITION_ROLE_MAP.put("chief marketing officer", "EXECUTIVE");
        
        // IT Department
        POSITION_ROLE_MAP.put("it operations and systems", "IT_ADMIN");
        
        // HR Department Hierarchy
        POSITION_ROLE_MAP.put("hr manager", "HR_MANAGER");
        POSITION_ROLE_MAP.put("hr team leader", "HR_SPECIALIST");
        POSITION_ROLE_MAP.put("hr rank and file", "HR_ASSISTANT");
        
        // Finance & Accounting Department
        POSITION_ROLE_MAP.put("accounting head", "MANAGER");
        POSITION_ROLE_MAP.put("payroll manager", "PAYROLL_ADMIN");
        POSITION_ROLE_MAP.put("payroll team leader", "PAYROLL_ADMIN");
        POSITION_ROLE_MAP.put("payroll rank and file", "ACCOUNTANT");
        
        // Account Management Department
        POSITION_ROLE_MAP.put("account manager", "MANAGER");
        POSITION_ROLE_MAP.put("account team leader", "TEAM_LEADER");
        POSITION_ROLE_MAP.put("account rank and file", "EMPLOYEE");
        
        // Other Departments - General Employees
        POSITION_ROLE_MAP.put("sales & marketing", "EMPLOYEE");
        POSITION_ROLE_MAP.put("supply chain and logistics", "EMPLOYEE");
        POSITION_ROLE_MAP.put("customer service and relations", "EMPLOYEE");
        
        LOGGER.info("✅ Position-Role mapping initialized with " + POSITION_ROLE_MAP.size() + " mappings");
    }
    
    /**
     * Get role string based on employee position
     * @param position Employee position from database
     * @return Corresponding role string
     */
    public static String getUserRole(String position) {
        if (position == null || position.trim().isEmpty()) {
            LOGGER.warning("⚠️ Empty position provided, defaulting to EMPLOYEE");
            return "EMPLOYEE";
        }
        
        String normalizedPosition = position.toLowerCase().trim();
        String role = POSITION_ROLE_MAP.get(normalizedPosition);
        
        if (role == null) {
            LOGGER.warning("⚠️ Unknown position: '" + position + "', defaulting to EMPLOYEE");
            return "EMPLOYEE";
        }
        
        LOGGER.fine("🎯 Position '" + position + "' mapped to role: " + role);
        return role;
    }
    
    /**
     * Check if position has HR access
     */
    public static boolean hasHRAccess(String position) {
        String role = getUserRole(position);
        return role.contains("HR") || role.equals("EXECUTIVE");
    }
    
    /**
     * Check if position has payroll access
     */
    public static boolean hasPayrollAccess(String position) {
        String role = getUserRole(position);
        return role.contains("PAYROLL") || role.contains("ACCOUNTANT") || role.equals("EXECUTIVE");
    }
    
    /**
     * Check if position has executive access
     */
    public static boolean hasExecutiveAccess(String position) {
        String role = getUserRole(position);
        return role.equals("EXECUTIVE");
    }
    
    /**
     * Check if position can manage employees
     */
    public static boolean canManageEmployees(String position) {
        String role = getUserRole(position);
        return role.contains("MANAGER") || role.contains("HR") || role.equals("EXECUTIVE");
    }
    
    /**
     * Check if position can approve leave requests
     */
    public static boolean canApproveLeave(String position) {
        String role = getUserRole(position);
        return role.contains("MANAGER") || role.contains("HR") || role.contains("TEAM_LEADER") || role.equals("EXECUTIVE");
    }
    
    /**
     * Check if position can access financial data
     */
    public static boolean canAccessFinancialData(String position) {
        String role = getUserRole(position);
        return role.contains("PAYROLL") || role.contains("ACCOUNTANT") || role.equals("EXECUTIVE");
    }
    
    /**
     * Get access level for position
     */
    public static int getAccessLevel(String position) {
        String role = getUserRole(position);
        switch (role) {
            case "EXECUTIVE": return 10;
            case "HR_MANAGER": return 8;
            case "PAYROLL_ADMIN": return 7;
            case "MANAGER": return 6;
            case "HR_SPECIALIST": return 5;
            case "TEAM_LEADER": return 4;
            case "HR_ASSISTANT": return 3;
            case "ACCOUNTANT": return 3;
            default: return 1;
        }
    }
    
    /**
     * Get all mapped positions for debugging
     */
    public static Map<String, String> getAllMappings() {
        return new HashMap<>(POSITION_ROLE_MAP);
    }
    
    /**
     * Check if a position is mapped
     */
    public static boolean isPositionMapped(String position) {
        if (position == null || position.trim().isEmpty()) {
            return false;
        }
        return POSITION_ROLE_MAP.containsKey(position.toLowerCase().trim());
    }
    
    /**
     * Get dashboard type for position
     */
    public static String getDashboardType(String position) {
        String role = getUserRole(position);
        
        if (role.equals("EXECUTIVE")) {
            return "Executive Dashboard";
        } else if (role.contains("HR")) {
            return "HR Dashboard";
        } else if (role.contains("PAYROLL")) {
            return "Payroll Dashboard";
        } else if (role.contains("MANAGER")) {
            return "Management Dashboard";
        } else {
            return "Employee Dashboard";
        }
    }
}