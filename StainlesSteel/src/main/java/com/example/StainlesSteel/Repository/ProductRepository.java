package com.example.StainlesSteel.Repository;

import com.example.StainlesSteel.Tables.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
