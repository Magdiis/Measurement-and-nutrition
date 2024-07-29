package com.example.ea_project.domain.product;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public Product createProduct(Product product){
        return repository.save(product);
    }

    public Optional<Product> getProductById(Long id){
        return repository.findById(id);
    }

}
