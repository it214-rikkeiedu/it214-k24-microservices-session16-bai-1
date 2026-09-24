package com.example.flashsale.repository;

public interface ProductRepository {
    Integer findPriceById(String productId);
    void updatePrice(String productId, Integer newPrice);
}