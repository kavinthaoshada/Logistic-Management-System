package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.User;
import com.ozzz.ejb.entity.UserType;
import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private User user;
    private UserType adminType;
    private UserType employeeType;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("john.doe@gmail.com");
        user.setUserName("john.doe");
        user.setPassword("password");
        user.setRole("admin");

        adminType = new UserType();
        adminType.setId(1L);
        adminType.setName("admin");

        employeeType = new UserType();
        employeeType.setId(2L);
        employeeType.setName("employee");
    }

    @Test
    void testGetEmployees() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(user));

        List<User> users = employeeService.getEmployees();

        assertThat(users).hasSize(1);
        assertThat(users.get(0)).isEqualTo(user);
    }

    @Test
    void testAddEmployee_EmployeeExists() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        boolean result = employeeService.addEmployee("john.doe@gmail.com", "john.doe", "password", "admin", "admin");

        assertFalse(result);
        verify(em, never()).persist(any(User.class));
    }

    @Test
    void testAddEmployee_NewEmployee() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        when(em.find(UserType.class, 1L)).thenReturn(adminType);

        boolean result = employeeService.addEmployee("john.doe@gmail.com", "john.doe", "password", "admin", "admin");

        assertTrue(result);
        verify(em).persist(any(User.class));
    }

    @Test
    void testAddEmployee_NewEmployeeWithDifferentUserType() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        when(em.find(UserType.class, 2L)).thenReturn(employeeType);

        boolean result = employeeService.addEmployee("jane.doe@example.com", "jane.doe", "password", "user", "employee");

        assertTrue(result);
        verify(em).persist(any(User.class));
    }

    @Test
    void testUpdateEmployeeRole_NullEmployeeId() {
        boolean result = employeeService.updateEmployeeRole("admin", null);

        assertFalse(result);
    }

    @Test
    void testUpdateEmployeeRole_NullRole() {
        boolean result = employeeService.updateEmployeeRole(null, 1L);

        assertFalse(result);
    }

    @Test
    void testUpdateEmployeeRole_EmployeeNotFound() {
        when(em.find(User.class, 1L)).thenReturn(null);

        boolean result = employeeService.updateEmployeeRole("admin", 1L);

        assertFalse(result);
    }

    @Test
    void testUpdateEmployeeRole_Success() {
        when(em.find(User.class, 1L)).thenReturn(user);

        boolean result = employeeService.updateEmployeeRole("user", 1L);

        assertTrue(result);
        verify(em).merge(user);
    }

    @Test
    void testGetUserTypes() {
        TypedQuery<UserType> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(UserType.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(adminType));

        List<UserType> userTypes = employeeService.getUserTypes();

        assertThat(userTypes).hasSize(1);
        assertThat(userTypes.get(0)).isEqualTo(adminType);
    }

    @Test
    void testAssignOrdersToManage_Success() {
        when(em.find(User.class, 1L)).thenReturn(user);

        boolean result = employeeService.assignOrdersToManage(1L, Collections.emptyList());

        assertTrue(result);
        verify(em).merge(user);
    }

    @Test
    void testAssignOrdersToManage_Exception() {
        when(em.find(User.class, 1L)).thenThrow(new RuntimeException());

        boolean result = employeeService.assignOrdersToManage(1L, Collections.emptyList());

        assertFalse(result);
    }

}