package net.parksy.dbmulti.reporting.service;

import lombok.RequiredArgsConstructor;
import net.parksy.dbmulti.reporting.entity.Product;
import net.parksy.dbmulti.reporting.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional("reportingTransactionManager")
    public Product createProduct(String name, Double price) {
        Product product = Product.builder().name(name).price(price).build();
        return productRepository.save(product);
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
