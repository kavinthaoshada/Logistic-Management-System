package com.ozzz.ejb.remote;

import com.ozzz.ejb.entity.Product;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProductService {
    public Product addProduct(String name, String description, int qty, double total_weight, Long orderId);
    public List<Product> getProducts();

//    public boolean updateAvailableQuantity(Long product_id, int qty, boolean quantity_increase);
}
