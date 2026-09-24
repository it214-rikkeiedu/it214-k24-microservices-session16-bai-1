package com.example.flashsale.service;

import com.example.flashsale.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductPriceService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductPriceService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(value = "product_prices", key = "#productId", unless = "#result == null")
    public Integer getProductPrice(String productId) {
        validateProductId(productId);
        return productRepository.findPriceById(productId);
    }

    @CacheEvict(value = "product_prices", key = "#productId")
    public void updateProductPrice(String productId, Integer newPrice) {
        validateProductId(productId);
        if (newPrice == null || newPrice < 0) {
            throw new IllegalArgumentException("New price must be non-null and greater than or equal to 0.");
        }
        productRepository.updatePrice(productId, newPrice);
    }

    private void validateProductId(String productId) {
        if (!StringUtils.hasText(productId)) {
            throw new IllegalArgumentException("Product ID cannot be null, empty or blank.");
        }
    }
}