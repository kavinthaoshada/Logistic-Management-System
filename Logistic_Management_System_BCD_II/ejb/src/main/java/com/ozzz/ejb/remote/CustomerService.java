package com.ozzz.ejb.remote;

import com.ozzz.ejb.entity.Customer;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface CustomerService {
    public boolean addCustomer(String name, String address, String contact_number, String email);
    public boolean updateCustomerStatus(String status, Long customer_id);
    public List<Customer> getCustomers();
    public Customer getSelectedCustomer(Long customer_id);
    public List<String> getAllStatus();
}
