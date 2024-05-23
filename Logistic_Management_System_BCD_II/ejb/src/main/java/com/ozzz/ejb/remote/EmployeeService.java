package com.ozzz.ejb.remote;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.User;
import com.ozzz.ejb.entity.UserType;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface EmployeeService {
    public List<User> getEmployees();
    public boolean addEmployee(String email, String username, String password, String role, String user_type);
    public boolean updateEmployeeRole(String role, Long employee_id);
    public boolean assignOrdersToManage(Long employee_id, List<OrderToManage> orders);
    public List<UserType> getUserTypes();
}
