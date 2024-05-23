package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.ActiveStatus;
import com.ozzz.ejb.entity.Customer;
import com.ozzz.ejb.remote.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomer_id(1L);
        customer.setName("John Doe");
        customer.setAddress("123 Colombo");
        customer.setContact_number("0773454234");
        customer.setEmail("john.doe@gmail.com");
        customer.setStatus(ActiveStatus.ACTIVE);
    }

    @Test
    void testAddCustomer_CustomerExists() {
        TypedQuery<Customer> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Customer.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(customer);

        boolean result = customerService.addCustomer("John Doe", "123 Colombo", "0773454234", "john.doe@gmail.com");

        assertFalse(result);
        verify(em, never()).persist(any(Customer.class));
    }

    @Test
    void testAddCustomer_NewCustomer() {
        TypedQuery<Customer> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Customer.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        boolean result = customerService.addCustomer("John Doe", "123 Main St", "1234567890", "john.doe@example.com");

        assertTrue(result);
        verify(em).persist(any(Customer.class));
    }

    @Test
    void testUpdateCustomerStatus_NullCustomerId() {
        boolean result = customerService.updateCustomerStatus("ACTIVE", null);
        assertFalse(result);
    }

    @Test
    void testUpdateCustomerStatus_InvalidStatus() {
        when(em.find(Customer.class, 1L)).thenReturn(customer);
        boolean result = customerService.updateCustomerStatus("INVALID_STATUS", 1L);
        assertFalse(result);
    }

    @Test
    void testUpdateCustomerStatus_ValidStatus() {
        when(em.find(Customer.class, 1L)).thenReturn(customer);

        boolean result = customerService.updateCustomerStatus("ACTIVE", 1L);

        assertTrue(result);
        verify(em).merge(customer);
    }

    @Test
    void testUpdateCustomerStatus_CustomerNotFound() {
        when(em.find(Customer.class, 1L)).thenReturn(null);

        boolean result = customerService.updateCustomerStatus("ACTIVE", 1L);

        assertFalse(result);
    }

    @Test
    void testGetAllStatus() {
        List<String> statuses = customerService.getAllStatus();
        List<String> expectedStatuses = Arrays.asList("ACTIVE", "PERMANENT_RESTRICTED", "TEMPORARY_RESTRICTED");

        assertThat(statuses).containsExactlyInAnyOrderElementsOf(expectedStatuses);
    }

    @Test
    void testGetCustomers() {
        TypedQuery<Customer> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Customer.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(customer));

        List<Customer> customers = customerService.getCustomers();

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0)).isEqualTo(customer);
    }

    @Test
    void testGetSelectedCustomer_CustomerExists() {
        when(em.find(Customer.class, 1L)).thenReturn(customer);

        Customer result = customerService.getSelectedCustomer(1L);

        assertThat(result).isEqualTo(customer);
    }

    @Test
    void testGetSelectedCustomer_CustomerNotFound() {
        when(em.find(Customer.class, 1L)).thenReturn(null);

        Customer result = customerService.getSelectedCustomer(1L);

        assertThat(result).isNull();
    }

}
