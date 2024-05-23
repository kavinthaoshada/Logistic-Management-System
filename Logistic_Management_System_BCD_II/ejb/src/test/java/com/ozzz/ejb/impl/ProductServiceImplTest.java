package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Product;
import com.ozzz.ejb.impl.ProductServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private EntityManager em;

    @InjectMocks
    private ProductServiceImpl productService;

    private OrderToManage order;

    @BeforeEach
    void setUp() {
        order = new OrderToManage();
        order.setOrder_id(1L);
    }

    @Test
    void testAddProductWithValidInputs() {
        when(em.find(OrderToManage.class, order.getOrder_id())).thenReturn(order);

        Product product = productService.addProduct("Test Product", "Test Description", 10, 5.5, order.getOrder_id());

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Test Product");
        assertThat(product.getDescription()).isEqualTo("Test Description");
        assertThat(product.getQuantity()).isEqualTo(10);
        assertThat(product.getTotal_weight()).isEqualTo(5.5);
        assertThat(product.getOrder()).isEqualTo(order);
        verify(em).persist(product);
    }

    @Test
    void testAddProductWithNullDescription() {
        when(em.find(OrderToManage.class, order.getOrder_id())).thenReturn(order);

        Product product = productService.addProduct("Test Product", null, 10, 5.5, order.getOrder_id());

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Test Product");
        assertThat(product.getDescription()).isNull();
        assertThat(product.getQuantity()).isEqualTo(10);
        assertThat(product.getTotal_weight()).isEqualTo(5.5);
        assertThat(product.getOrder()).isEqualTo(order);
        verify(em).persist(product);
    }

    @Test
    void testAddProductWithNonExistentOrderId() {
        when(em.find(OrderToManage.class, order.getOrder_id())).thenReturn(null);

        Product product = productService.addProduct("Test Product", "Test Description", 10, 5.5, order.getOrder_id());

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Test Product");
        assertThat(product.getDescription()).isEqualTo("Test Description");
        assertThat(product.getQuantity()).isEqualTo(10);
        assertThat(product.getTotal_weight()).isEqualTo(5.5);
        assertThat(product.getOrder()).isNull();
        verify(em).persist(product);
    }

    @Test
    void testGetProductsWhenNoProductsExist() {
        TypedQuery<Product> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Product> products = productService.getProducts();

        assertThat(products).isEmpty();
    }

    @Test
    void testGetProductsWhenProductsExist() {
        Product product1 = new Product();
        product1.setName("Product 1");
        Product product2 = new Product();
        product2.setName("Product 2");

        List<Product> productList = List.of(product1, product2);

        TypedQuery<Product> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(productList);

        List<Product> products = productService.getProducts();

        assertThat(products).hasSize(2);
        assertThat(products).containsExactly(product1, product2);
    }
}