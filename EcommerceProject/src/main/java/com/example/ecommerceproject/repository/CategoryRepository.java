package com.example.ecommerceproject.repository;

import com.example.ecommerceproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Integer> {
}
