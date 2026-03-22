package net.parksy.dbmulti.secondary.service;

import lombok.RequiredArgsConstructor;
import net.parksy.dbmulti.secondary.entity.Product;
import net.parksy.dbmulti.secondary.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional("secondaryTransactionManager")
    public Product createProduct(String name, Double price) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .build();
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }
}
