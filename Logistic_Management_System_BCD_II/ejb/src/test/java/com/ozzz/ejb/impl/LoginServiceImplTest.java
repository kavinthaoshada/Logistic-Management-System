package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.User;
import com.ozzz.ejb.entity.UserType;
import com.ozzz.ejb.impl.LoginServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private LoginServiceImpl loginService;

    private User user;
    private UserType userType;

    @BeforeEach
    void setUp() {
        userType = new UserType();
        userType.setId(1L);
        userType.setName("admin");

        user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setUser_type(userType);
    }

    @Test
    void testLogin_Success() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter("email", "john.doe@gmail.com")).thenReturn(query);
        when(query.setParameter("password", "password")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        String result = loginService.login("john.doe@gmail.com", "password");

        assertThat(result).isEqualTo("admin");
    }

    @Test
    void testLogin_Failure_NoResult() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter("email", "john.doe@gmail.com")).thenReturn(query);
        when(query.setParameter("password", "wrongpassword")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        String result = loginService.login("john.doe@gmail.com", "wrongpassword");

        assertThat(result).isEqualTo("empty");
    }

    @Test
    void testLogin_DifferentEmail() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter("email", "jane.doe@gmail.com")).thenReturn(query);
        when(query.setParameter("password", "password")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        String result = loginService.login("jane.doe@gmail.com", "password");

        assertThat(result).isEqualTo("empty");
    }
}